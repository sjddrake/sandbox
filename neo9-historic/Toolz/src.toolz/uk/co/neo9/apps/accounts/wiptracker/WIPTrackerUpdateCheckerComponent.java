package uk.co.neo9.apps.accounts.wiptracker;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.UtilitiesTextHelper;
import uk.co.neo9.utilities.file.FileServer;
import uk.co.neo9.utilities.misc.UtilitiesGridComparator;
import uk.co.neo9.utilities.misc.UtilitiesGridContainer;

/**
 * @author Administrator
 *
 *	This one reads the WIPTracker main spreadsheet and compares
 *	what's in it against a re-calculated baseline... in other
 *	words, this checks if I have to re-populated any of the
 *	month sheets.
 *
 *	Approach
 *	
 *	- read in the monthly sheets from the main tracker as GRIDS
 *	- read each WIPTrackerTotals monthly spreadsheet as GRIDS
 *	- use the GRID compare functionality to spot changes
 *	- output in some suitable form!
 *
 */
public class WIPTrackerUpdateCheckerComponent extends WIPTrackerAnalyserComponent {	
	
	
	protected static Map<String, String> categoryLookup = new HashMap<String,String>();
	
	static {
		
		categoryLookup.put(WIPTrackerConstants.CATLABEL_BILLS.toUpperCase(), WIPTrackerConstants.CATVALUE_BILLS);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_BILLS_ANNUAL.toUpperCase(), WIPTrackerConstants.CATVALUE_BILLS_ANNUAL);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_BP_SAVING.toUpperCase(), WIPTrackerConstants.CATVALUE_BP_SAVING);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_CAR_MAINTNNS.toUpperCase(), WIPTrackerConstants.CATVALUE_CAR_MAINTNNS);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_DIY.toUpperCase(), WIPTrackerConstants.CATVALUE_DIY);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_ENTS.toUpperCase(), WIPTrackerConstants.CATVALUE_ENTS);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_FOOD.toUpperCase(), WIPTrackerConstants.CATVALUE_FOOD);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_HOLIDAY_SAVING.toUpperCase(), WIPTrackerConstants.CATVALUE_HOLIDAY_SAVING);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_HOMEWARES.toUpperCase(), WIPTrackerConstants.CATVALUE_HOMEWARES);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_HOUSE_MAINTNNS.toUpperCase(), WIPTrackerConstants.CATVALUE_HOUSE_MAINTNNS);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_KIDS_BIRTHDAY.toUpperCase(), WIPTrackerConstants.CATVALUE_KIDS_BIRTHDAY);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_KIDZ.toUpperCase(), WIPTrackerConstants.CATVALUE_KIDZ);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_MEDICAL.toUpperCase(), WIPTrackerConstants.CATVALUE_MEDICAL);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_PETROL.toUpperCase(), WIPTrackerConstants.CATVALUE_PETROL);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_PETS.toUpperCase(), WIPTrackerConstants.CATVALUE_PETS);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_PRESENTS.toUpperCase(), WIPTrackerConstants.CATVALUE_PRESENTS);		
		categoryLookup.put(WIPTrackerConstants.CATLABEL_SCHOOL_PHOTOS.toUpperCase(), WIPTrackerConstants.CATVALUE_SCHOOL_PHOTOS);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_SUNDRIES.toUpperCase(), WIPTrackerConstants.CATVALUE_SUNDRIES);
		categoryLookup.put(WIPTrackerConstants.CATLABEL_TOYS.toUpperCase(), WIPTrackerConstants.CATVALUE_TOYS);		
		categoryLookup.put(WIPTrackerConstants.CATLABEL_TRAVEL.toUpperCase(), WIPTrackerConstants.CATVALUE_TRAVEL);		
		categoryLookup.put(WIPTrackerConstants.CATLABEL_UNKNOWN.toUpperCase(), WIPTrackerConstants.CATVALUE_UNKNOWN);
	}
	
	
	
	

	public static void main(String[] args) {
		
		WIPTrackerUpdateCheckerComponent checker = new WIPTrackerUpdateCheckerComponent();
		checker.go(args);
		
	}
	

	
	protected void setSampleDate(int month){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH,month);
		Date sampleDate = cal.getTime();
		sampleDateFromPeriod = sampleDate;	
	}
	

	protected String performCheck(String workBookFileDetails, int year, int[] monthsToCheck){	
		
		boolean useCheckedMode = false; // this allows us to use unknown as a category... naff!
		initialise(useCheckedMode);

		
		// load the workbook
		Workbook wipTrackerWorkbook = loadWorkbook(workBookFileDetails);
		
		
		// now loop around the 12 months
		StringBuilder buff = new StringBuilder();
		int result;
		int noOfMonthsToCheck = monthsToCheck.length;
		for (int i = 0; i < noOfMonthsToCheck; i++) {
			int monthID = monthsToCheck[i];
			result = compareMainSheetToMonthlySheet(wipTrackerWorkbook, year, monthID);
			buff.append(CommonConstants.decodeMonthID_English(i));
			if (result == 0) {
				buff.append(": no differences found.");
			} else {
				buff.append(": the monthly sheet is out of step with the WIPTracker for this month.");
			}
			buff.append(CommonConstants.NEWLINE);
		}
		
		return buff.toString();
	}
	
	

	
	
	
	protected int compareMainSheetToMonthlySheet(Workbook wipTrackerWorkbook, int year, int sheetID){
		
		boolean dumpOn = false;
		String currentMonthName = CommonConstants.decodeMonthID_English(sheetID);
		
		System.out.println("processing... sheet #"+sheetID+" - "+currentMonthName);
		
		setSampleDate(sheetID);
		
		Sheet sheet = loadMonthlyWorksheetFromMainSpready(wipTrackerWorkbook, sheetID);
		List values = extractValuesFromSheet(sheet, sheetID);
		
		WIPTrackerTotalsGrid mainGrid = new WIPTrackerTotalsGrid(WIPTrackerTotalsGrid.MODE_DAILY_CAT_TOTALS);

		List<String> yAxisLabels = buildYAxisLabels();
		List<String> xAxisLabels = buildXAxisLabels();
		
		mainGrid.setYAxisLabels(yAxisLabels );
		mainGrid.setXAxisLabels(xAxisLabels );
		mainGrid.outputAxisLabels = true;
		mainGrid.loadGrid(values); 
		
		
		// load the other version to compare
		String filedetails = getMonthlySpreadsheetFileDetails(year, sheetID);
		UtilitiesGridContainer compareThisOne = loadWIPTotalsIntoGrid(filedetails);
		
		
		UtilitiesGridComparator comparator = new UtilitiesGridComparator();
		int result = comparator.compare(mainGrid, compareThisOne);
		
		
		if (dumpOn) {
			System.out.println("= = = = = = = = = = = = = = = = = = = = = =");
			System.out.println("= = = = = = = = = = = = = = = = = = = = = =");
			System.out.println("= = = = = = = = = = = = = = = = = = = = = =");
			System.out.println("= = = = = = = = = = = = = = = = = = = = = =");
			
			String mainGridDump = mainGrid.dumpGrid();
			writeDebugFile(mainGridDump, "mainGrid.csv");
			System.out.println(mainGridDump);
			
			System.out.println("= = = = = = = = = = = = = = = = = = = = = =");
			System.out.println("= = = = = = = = = = = = = = = = = = = = = =");
			System.out.println("= = = = = = = = = = = = = = = = = = = = = =");
			System.out.println("= = = = = = = = = = = = = = = = = = = = = =");
			
			String otherGridDump = compareThisOne.dumpGrid();
			writeDebugFile(otherGridDump, "otherGridDump.csv");
			System.out.println(otherGridDump);
			
			System.out.println("= = = = = = = = = = = = = = = = = = = = = =");
			System.out.println("= = = = = = = = = = = = = = = = = = = = = =");
			System.out.println("= = = = = = = = = = = = = = = = = = = = = =");
			System.out.println("= = = = = = = = = = = = = = = = = = = = = =");
		}		
		
		
		if (result != 0) {
			String output = comparator.outputResults(true);
			StringBuilder buff = new StringBuilder();
			buff.append("Comparison-");
			buff.append(sheetID);
			buff.append("-");
			buff.append(currentMonthName);
			buff.append(".csv");
			writeDebugFile(output, buff.toString());
			
			System.out.println("Sheet #"+sheetID+" - "+currentMonthName+" - UPDATE IS REQUIRED - see comparison output");
			
		} else {
			
			System.out.println("Sheet #"+sheetID+" - "+currentMonthName+" - NO UPDATE REQUIRED");
		}
		
		return result;
	}
	

	private void dumpListOfListOfStrings(List<List<WIPTrackerTotalsModel>> values) {
		
		for (Iterator iter = values.iterator(); iter.hasNext();) {
			List<WIPTrackerTotalsModel> category = (List<WIPTrackerTotalsModel>) iter.next();
			//System.out.print(category.get(0));
			for (Iterator iterator = category.iterator(); iterator.hasNext();) {
				WIPTrackerTotalsModel value = (WIPTrackerTotalsModel) iterator.next();
				WIPTrackerCellWrapper wrapper = new WIPTrackerCellWrapper(value);
				wrapper.xAxisDataSetID = WIPTrackerCellWrapper.DATA_SET_ID_DATE_DAY_AND_MONTH;
				wrapper.xAxisDataSetID = WIPTrackerCellWrapper.DATA_SET_ID_CATEGORY;
				System.out.print(wrapper.getOutput());
			}
			System.out.println();
		}
		
	}


	protected Sheet loadMonthlyWorksheetFromMainSpready(Workbook wipTrackerWorkbook, int month) {
		

		
		// open the main spreadsheet
		int workSheetIndex = determineMainSpreadyWorksheetIndex(month);
		Sheet workSheet = wipTrackerWorkbook.getSheet(workSheetIndex);
		
		return workSheet;
		
	}
	

	private int determineMainSpreadyWorksheetIndex(int month) {
		final int MONTHLY_OFFSET = 5;
		int workSheetIndex = month + MONTHLY_OFFSET;
		return workSheetIndex;
	}
	
	
	
	
	protected List extractValuesFromSheet(Sheet monthlySheet, int monthIndex){
		
		/**
		 * Algorithm
		 * 
		 * > OUTER LOOP
		 * > hard-coded starting cell... first cat name
		 * > INNER LOOP
		 * > work across the columns (dates) to pick out the values until 31 (end)
		 * > END INNER LOOP after 31
		 * > move down a row to next cat name (if blank ignore and move down again)
		 * > END INNER LOOP on final cat name (hardcoded)
		 * 
		 * ... as the values are read, either put them into the grid or a grid-loading pre-form
		 */
		
		// TRY PRE_GRID FORM OF an array list of string array lists
		List dmz = new ArrayList<List<Object>>();
		
		int noOfRows = monthlySheet.getRows();
		
		boolean entriesExist = true;
		int rowIndex = 10;
		while (entriesExist) {
			
			// get a row 
//			List<String> category = new ArrayList<String>();
			List<WIPTrackerTotalsModel> category = extractCategoryRow(rowIndex,monthlySheet, monthIndex);
		
			if ((category != null && category.size() > 0)) 	
			{
				dmz.add(category);
			}else {
				if (true){ // is logging on?
					log("Row does not contain amount values - row index = "+rowIndex);
				}

			}
			
			// increment count & do safety check
			rowIndex++;
			if (rowIndex > noOfRows-1){
				// log("ERROR - Stopped an infinite loop happening!!!");
				entriesExist = false;
			}
		}
		
		return dmz;
	}	



	protected List<WIPTrackerTotalsModel> extractCategoryRow(int rowIndex, Sheet monthlySheet, int monthIndex) {
			
			// this method simply turns the excel data into models 
			List<WIPTrackerTotalsModel> categoryAmounts = new ArrayList<WIPTrackerTotalsModel>();
			String catName = null;
			boolean entriesExist = true;
			int colIndex = 2;
			int dateCount = 1;
			while (entriesExist) {
				
				Cell cell = monthlySheet.getCell(colIndex,rowIndex); 
				
				if ((cell != null)) 
				{
					
					if (colIndex == 2) {
						catName = cell == null ? null : cell.getContents(); 
						boolean validCategory = WIPTrackerRulesHelper.checkForValidCatLabel(catName);
						if (validCategory) {
							catName = categoryLookup.get(catName.toUpperCase());
							colIndex = 4;
						} else {			
							entriesExist = false;
						}
					} else {
						String amountValue = cell == null ? "" : cell.getContents();  
						if (amountValue.length() > 0) {
							BigDecimal amount = helper.convertValueToBigDecimal(amountValue);
							Date date = helper.convertValueToDate(dateCount, monthIndex);
							WIPTrackerTotalsModel model;
							model = new WIPTrackerTotalsModel(amount,catName,date);
							categoryAmounts.add(model);
						}		
						dateCount++;
						colIndex++;
					}
				

//					if (true){ // is logging on?
//						log("Date value is missing - this must be the end of the data set - row index = "+rowIndex);
//					}
					
				}else {
					if (true){ // is logging on?
						log("Value cell is missing - this must be the end of the data set - row index = "+rowIndex);
					}
					entriesExist = false;
				}
				
				
				// increment count & do safety check
				if (colIndex > 34){
					// log("ERROR - Stopped an infinite loop happening!!!");
					entriesExist = false;
				}
			}
			
			return categoryAmounts;
		}

	
	
	
	
	private void writeDebugFile(String output, String filename) {
		
		String targetFolder = "C:/temp";
		String fileDetails = targetFolder+"/"+filename;
		
		log("");
		log("Writing out results now to > "+fileDetails);

		try {
			FileServer.writeTextFile(fileDetails,output);
		} catch (IOException e) {
			e.printStackTrace();
			log("Error - unable to write output file!");
			System.exit(-1);
		}
		
		log(".................................. done!");
	}
	
	
}
