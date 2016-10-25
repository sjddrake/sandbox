package uk.co.neo9.apps.accounts.wiptracker;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import uk.co.neo9.test.Neo9TestingConstants;
import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.UtilitiesComparatorPlusAdapter;

public class WIPTrackerTotalsGeneratorComponent extends WIPTrackerBaseComponent {
	
	
	protected WIPTrackerRulesHelper helper = new WIPTrackerRulesHelper();
	
	private List<String> categoryNames = null;
	private List<String> catNames_RegularSpending = null;
	private List<String> catNames_PeaksTroughs = null;
	private List<String> catNames_SavingPots = null;
	private List<String> catNames_Unknown = null;

	protected boolean useLoanCategory = false;

	protected Date sampleDateFromPeriod = null;
	protected boolean checkedMode = true;
	protected boolean validData = false;
	
	protected String wipTrackerFileName = null;
	protected String outputFileName = null;
	protected String workingFolder = null;

	
	
	private boolean logWorkingOut = true;
	public void setLogWorkingOut(boolean onOrOff){
		this.logWorkingOut = onOrOff;
		this.helper.logWorkingOut = onOrOff;
	}
	
	public void log(String message){
		
		if (logWorkingOut){
			super.log(message);
		}
	}
	
	
	// I think this is just used by sub-components
	protected List<WIPTrackerContainer> loadWIPTrackers(List<String> wipTrackerFileNames){	
		
		List<WIPTrackerContainer> trackers = new ArrayList<WIPTrackerContainer>();
		
		for (Iterator<String> iter = wipTrackerFileNames.iterator(); iter.hasNext();) {
			String filename = iter.next();
			WIPTrackerContainer wipTrackerContainer = loadWIPTrackerTransactions(filename);
			trackers.add(wipTrackerContainer);
		}
		
		return trackers;
	}
	
	
	protected WIPTrackerTotalsGrid loadWIPTotalsIntoGrid(String worksheetFilename){

//		 START COMMENT		
//		 This block is replicated in the method loadWIPTrackerTransactions BUT
//		 that can't be used because the intermedtiate variables are used in the
//		 'debug' at the end of this method!!!!		
		
		Sheet sheet = loadWIPTrackerSheet(worksheetFilename);
		
		
		// make sure we're using the correct type of worksheet!
		int worksheetType = determineSheetFormat(sheet);
//		if (WIPTrackerConstants.WKSHT_TYPE_STANDARD != worksheetType) {
//			throw new IllegalArgumentException("The worksheet is not in the STANDARD format: "+worksheetFilename);
//		}
		
		// extract the models from the worksheet
		List<WIPTrackerTotalsModel> worksheetAsModels = extractModelsFromSheet(sheet, worksheetType);
		validateModels(worksheetAsModels);
		List<WIPTrackerTotalsModel> uncollatedModels = filterToValidModels(worksheetAsModels);

		// THis is NAFF!!!! Need to properly design the INVALID idea cos this is another level of it!!!
		checkJointCategories(uncollatedModels, getCategoryNames());
		
		
		// get the WIP models collated by category
		WIPTrackerTotalsComparator comparator = new WIPTrackerTotalsComparator();
		comparator.setFieldToUseId(WIPTrackerTotalsModel.FID_CATEGORY_CASE_INSENSITIVE);
		List<WIPTrackerCategoryContainer> collatedModels = collateItems(uncollatedModels, comparator);

// END COMMENT		
		
		/**
		 * CollatedModels - This is a List of WIPTrackerCategoryContainer.
		 * There is 1 container per category: bills, food, etc
		 * The container knows its category & contains a list of transaction total models
		 * AT THIS POINT the categories can have MULTIPLE transaction models for the same category & day
		 */
		
		// we now have a list of lists where each sub-list
		// is all the transactions in a category from the spready
		// - but there could be more than one transaction per
		// category per day and we need daily totals...		
		
		// for each category
		Map allCatTotalsForThePeriodMap = new HashMap();
		for (Iterator<WIPTrackerCategoryContainer> iter = collatedModels.iterator(); iter.hasNext();) {
			WIPTrackerCategoryContainer categorisedModels = iter.next();
			List<WIPTrackerTotalsModel> categoryTotals = produceDailyTotals(categorisedModels.getTotalsInCategory());
			
			
			// and add the totals to a hash table for processing later
			/**
			 * This adds the individual daily total model to a hash-map, keyed by category & date.
			 * So, for example, there's a model keyed with: "bills|02-Nov". This hash map can be
			 * built up with all daily total models for all categories in this way. The map is a
			 * convenient way to uniquely & instantly locate a daily total for a category.
			 * NOTE: As these models have already been totalled per day, this key WILL BE unique! :-)
			 */			
			addCatTotalsToMap(categoryTotals,allCatTotalsForThePeriodMap);
			
		}

		
		/**
		 * Now the LIST of category transaction total models are totalled by day
		 * In other words, for days that had multiple transactions, the multiples
		 * have now been amalgamated into a single, total model for the given cat/day
		 */
		
		// GRID-CONTAINER
		List<List<Object>> gridLoader = createGridLoader(allCatTotalsForThePeriodMap);
		WIPTrackerTotalsGrid grid = new WIPTrackerTotalsGrid(WIPTrackerTotalsGrid.MODE_DAILY_CAT_TOTALS);
		List<String> yAxisLabels = buildYAxisLabels();
		grid.setYAxisLabels(yAxisLabels );
		List<String> xAxisLabels = buildXAxisLabels();
		grid.setXAxisLabels(xAxisLabels );		
		grid.loadGrid(gridLoader);
		String output = grid.outputGrid();
		log(output);
		
		
		
		
		// give some helpful output too
		List<WIPTrackerTotalsModel> ignoredModels = helper.filterModels(worksheetAsModels, WIPTrackerRulesHelper.FILTERBY_IGNORED);
		int noOfIgnoredRows = ignoredModels.size();
		if (noOfIgnoredRows > 0) {
			log("");
			log("The following "+noOfIgnoredRows+" rows were ignored:");
			for (Iterator iterator = ignoredModels.iterator(); iterator.hasNext();) {
				WIPTrackerTotalsModel model = (WIPTrackerTotalsModel) iterator.next();
				log("row: "+model.getRowNumber()+", owner: "+model.getOwner()+", cat: "+model.getCategory());
			}			
		}

		List invalidModels = helper.filterModels(worksheetAsModels,WIPTrackerRulesHelper.FILTERBY_INCLUDED_BUT_INVALID);
		
		int noOfInvalidModels = invalidModels.size();
		int noOfValidModels = uncollatedModels.size();
		int totalNoOfModels = worksheetAsModels.size();
		
		log("");
		log("Total No of Rows = "+totalNoOfModels);
		log("No of Valid Rows = "+noOfValidModels);
		log("No of Invalid Rows = "+noOfInvalidModels);
		log("No of Ignored Rows = "+noOfIgnoredRows);
		int checkTotal = noOfValidModels+noOfInvalidModels+noOfIgnoredRows;
		log("Totals check (should equal "+totalNoOfModels+") = "+checkTotal);		
		
		
		
		return grid;
	}
	
	
	
	
	
	
	private List<List<Object>> createGridLoader(List<WIPTrackerCategoryContainer> collatedModels) {
		
		List<List<Object>> dataLoader = new ArrayList<List<Object>>();
		
		for (Iterator iter = collatedModels.iterator(); iter.hasNext();) {
			WIPTrackerCategoryContainer category = (WIPTrackerCategoryContainer) iter.next();
			List<Object> data = createGridLoaderRow(category);
			dataLoader.add(data);
		}
		
		return dataLoader;
		
	}



	private List<Object> createGridLoaderRow(WIPTrackerCategoryContainer category) {
		List<Object> row = new ArrayList<Object>();
		row.addAll(category.getTotalsInCategory());
		return row;
	}



	protected List<String> buildXAxisLabels() { // TODO can this go in the wrapper???
		List<String> axisLabels = new ArrayList<String>();
//scooby		axisLabels.add("Category");
		List datesInPeriod = getDatesInCurrentPeriod(sampleDateFromPeriod);
		for (Iterator iter = datesInPeriod.iterator(); iter.hasNext();) {
			Date date = (Date) iter.next();
			axisLabels.add(WIPTrackerRulesHelper.formatDate_DayAndMonth(date));	
		}
		return axisLabels;
	}	
	
	
	protected List<String> buildYAxisLabels() {
		List<String> axisLabels = null;
		axisLabels = getCategoryNames();
		return axisLabels;
	}		
	
	protected List<Object> createGridLoaderRow(String categoryName, Map catTotalsForThePeriod) {
		
		List<Object> data = new ArrayList<Object>();

		// get all the dates for the applicable month and then
		// loop through the dates per category
		List datesInPeriod = getDatesInCurrentPeriod(sampleDateFromPeriod);
		
		for (Iterator iterator = datesInPeriod.iterator(); iterator.hasNext();) {
			Date date = (Date) iterator.next();
			String hashKey = helper.generateCatTotalHashKey(categoryName,date);
			WIPTrackerTotalsModel dailyCatTotal = null;
			dailyCatTotal = (WIPTrackerTotalsModel)catTotalsForThePeriod.get(hashKey);
			if (dailyCatTotal != null) {
				data.add(dailyCatTotal);
			} else {
// don't think we need to do this after all				data.add(UtilitiesGridContainerNullCell.getInstance());
			}
			
		}
		
		return data;
	}
	
	
	protected List<List<Object>> createGridLoader(Map catTotalsForThePeriod) {
		
		List<List<Object>> dataLoader = new ArrayList<List<Object>>();
		
		// List<String> catNames = WIPTrackerRulesHelper.getAllCategoryNames();
		List<String> catNames = getCategoryNames();
		for (Iterator iter = catNames.iterator(); iter.hasNext();) {
			String catName = (String) iter.next();
			List<Object> data = createGridLoaderRow(catName, catTotalsForThePeriod);
			dataLoader.add(data);
		}
		
		return dataLoader;
		
	}



	/**
	 * This method:
	 * 
	 * 		> reads a WIPTracker worksheet
	 * 		> converts each line to a transaction model
	 * 		> categorises the models by category
	 * 		> returns a List of the category model containers
	 * 
	 * @param wipTrackerFileName
	 * @return
	 */
	protected WIPTrackerContainer loadWIPTrackerTransactions(String wipTrackerFileName){	
		
		Sheet sheet = loadWIPTrackerSheet(wipTrackerFileName);
		
		// make sure we're using the correct type of worksheet!
		int worksheetType = determineSheetFormat(sheet);
//		if (WIPTrackerConstants.WKSHT_TYPE_STANDARD != worksheetType) {
//			throw new IllegalArgumentException("The worksheet is not in the STANDARD format: "+wipTrackerFileName);
//		}
		
		// extract the models from the worksheet
		List<WIPTrackerTotalsModel> worksheetAsModels = extractModelsFromSheet(sheet,worksheetType);
		validateModels(worksheetAsModels);
		List<WIPTrackerTotalsModel> uncollatedModels = filterToValidModels(worksheetAsModels);

		// THis is NAFF!!!! Need to properly design the INVALID idea cos this is another level of it!!!
//		checkJointCategories(uncollatedModels, categoryNames);
		
		
		// get the WIP models collated by category
		WIPTrackerTotalsComparator comparator = new WIPTrackerTotalsComparator();
		comparator.setFieldToUseId(WIPTrackerTotalsModel.FID_CATEGORY_CASE_INSENSITIVE);
		List<WIPTrackerCategoryContainer> categorisedModels = collateItems(uncollatedModels, comparator);
		
		// and put the categories of models into an outer container
		WIPTrackerContainer wipTrackerContainer = new WIPTrackerContainer();
		for (WIPTrackerCategoryContainer wipTrackerCategoryContainer : categorisedModels) {
			wipTrackerContainer.addCategory(wipTrackerCategoryContainer);
		}
		
		
		return  wipTrackerContainer;

		
	}		
	
	
	private void checkJointCategories(List<WIPTrackerTotalsModel> worksheetAsModels,
									  List<String> validCategoryNames) {

		StringBuffer buf = new StringBuffer("Exiting because the following Joint items have invalid categories:");
		boolean invalidCategoriesFound = false;
		for (Iterator<WIPTrackerTotalsModel> iterator = worksheetAsModels.iterator(); iterator.hasNext();) {
			WIPTrackerTotalsModel model = (WIPTrackerTotalsModel) iterator.next();
			boolean isValidCatMember = model.isCategoryMember(validCategoryNames);
			if (isValidCatMember == false) {
				invalidCategoriesFound = true;
				buf.append(CommonConstants.NEWLINE);
				buf.append("\t> row "+ model.getRowNumber() + " - category = "+ model.getCategory());
			}
		}
		
		
		if (checkedMode && invalidCategoriesFound) {
			System.err.println(buf);
			System.exit(-1);
		}
	}
	
	
	protected void initialise(boolean useCheckedMode) {

		sampleDateFromPeriod = null;
		checkedMode = useCheckedMode;
		validData = false;
		
		wipTrackerFileName = null;
		outputFileName = null;
		workingFolder = null;
		
		
		// Scooby - naff! ... and as it is lazily instantiated, why bother calling it now?
		// getCategoryNames();
		
	}



// 
//	TODO How to I get this one out to the helper??!??! 
	private List filterToValidModels(List worksheetAsModels) {
		List models = new ArrayList();
		for (Iterator iterator = worksheetAsModels.iterator(); iterator.hasNext();) {
			WIPTrackerTotalsModel model = (WIPTrackerTotalsModel) iterator.next();
			if (checkedMode) {
				if (model.getStats().isValid()) {
					models.add(model);
				}
			} else {
				if (model.getStats().isIncluded()) {
					models.add(model);
				}
			}
		}
		return models;
	}






	private List<String> buildCategoryTotalsCSVFormat(Map catTotalsForThePeriod) {

		// need to write out lines in CSV format with date
		// as columns, category as rows... including blanks
		// for the dates with no category
		
		// so...
		
		// get all the dates for the applicable month and then
		// loop through the dates per category
		List datesInPeriod = getDatesInCurrentPeriod(sampleDateFromPeriod);
		
		// set up the header row for the output
		List<String> outputLines = new ArrayList<String>();
		StringBuffer headerLine = new StringBuffer();
		headerLine.append("Category");
		headerLine.append(",");		
		StringBuffer emptyLine = new StringBuffer();
		emptyLine.append(",");
		for (Iterator iter = datesInPeriod.iterator(); iter.hasNext();) {
			Date date = (Date) iter.next();
			headerLine.append(helper.formatDate_DayAndMonth(date));	
			headerLine.append(",");	
			emptyLine.append(",");
		}
		emptyLine.append("END");
		headerLine.append("END");
		outputLines.add(headerLine.toString());
		
		// build up the lines to write out and then write them to the file
		// - category block, by category block
		List catNames = catNames_RegularSpending; // Scooby - this is naff as its lazily instantiated!
		buildCategoryBlockForCSVFormat(catTotalsForThePeriod, datesInPeriod, catNames, outputLines);
		outputLines.add(emptyLine.toString());
		
		catNames = catNames_PeaksTroughs; // Scooby - this is naff as its lazily instantiated!
		buildCategoryBlockForCSVFormat(catTotalsForThePeriod, datesInPeriod, catNames, outputLines);
		outputLines.add(emptyLine.toString());
		
		catNames = catNames_SavingPots; // Scooby - this is naff as its lazily instantiated!
		buildCategoryBlockForCSVFormat(catTotalsForThePeriod, datesInPeriod, catNames, outputLines);
		outputLines.add(emptyLine.toString());
		
		catNames = catNames_Unknown; // Scooby - this is naff as its lazily instantiated!
		buildCategoryBlockForCSVFormat(catTotalsForThePeriod, datesInPeriod, catNames, outputLines);
		outputLines.add(emptyLine.toString());
		
		return outputLines;
		
	}


	
	private void buildCategoryBlockForCSVFormat(Map catTotalsForThePeriod, List datesInPeriod, List blockCatNames, List<String> outputLines) {
		for (Iterator iter = blockCatNames.iterator(); iter.hasNext();) {
			String categoryName = (String) iter.next();
			StringBuffer outputRow = new StringBuffer();
			outputRow.append(categoryName);
			outputRow.append(",");
			for (Iterator iterator = datesInPeriod.iterator(); iterator.hasNext();) {
				Date date = (Date) iterator.next();
				String hashKey = helper.generateCatTotalHashKey(categoryName,date);
				WIPTrackerTotalsModel dailyCatTotal = null;
				dailyCatTotal = (WIPTrackerTotalsModel)catTotalsForThePeriod.get(hashKey);
				if (dailyCatTotal != null) {
					outputRow.append(dailyCatTotal.getAmount());
				}
				outputRow.append(",");
			}
			
			
			outputRow.append("END");
			
			// instead of 'END' could put a row total
			StringBuffer rowTotal = new StringBuffer("=sum(");
			// ... what to put in here???
			//SoocybDO - this is a future enhancement... though it would be a good verification test!
			rowTotal.append(")");
			
			outputLines.add(outputRow.toString());
		}
	}


	
	public int getMonthForDate(Date theDate){
		int month = 0;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		month = cal.get(Calendar.MONTH);
		
		return month;
	}
	
	

	protected List getDatesInCurrentPeriod(Date dateInSampleMonth) {
		
		int sampleMonth = getMonthForDate(dateInSampleMonth);
		
		List dates = new ArrayList();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH,sampleMonth);
		cal.set(Calendar.DATE,1);
		int safetyCheck = 0;
		boolean keepGoing = true;
		do {
			// get the date as a string
			Date theDate = cal.getTime();
			dates.add(theDate);	
			
			// prepare for the next iteration
			cal.add(Calendar.DATE,1);
			if (cal.get(Calendar.MONTH) != sampleMonth) {
				keepGoing = false; // but stop when we've hit a new month
			}
			
			// put on the breaks... just in case!
			safetyCheck++;
			if (safetyCheck > 32) {
				keepGoing = false;
				log("EEROR - shouldn't have got to here!");
			}
		} while (keepGoing);
		
		
		return dates;
	}



	protected List<String> getCategoryNames() {
		// ScoobyDO - best to change this into a load from a seperate file
		
		if (categoryNames == null) {
			
			// sort the cats first
			catNames_RegularSpending = new ArrayList<String>(); 
			catNames_RegularSpending.add(WIPTrackerConstants.CATVALUE_BILLS);
	//		if (useLoanCategory) {
				catNames_RegularSpending.add(WIPTrackerConstants.CATVALUE_LOANS);
	//		}
			catNames_RegularSpending.add(WIPTrackerConstants.CATVALUE_FOOD);
			catNames_RegularSpending.add(WIPTrackerConstants.CATVALUE_HOMEWARES);
			catNames_RegularSpending.add(WIPTrackerConstants.CATVALUE_PETROL);
			catNames_RegularSpending.add(WIPTrackerConstants.CATVALUE_SUNDRIES);
			catNames_RegularSpending.add(WIPTrackerConstants.CATVALUE_TRAVEL);
			catNames_RegularSpending.add(WIPTrackerConstants.CATVALUE_PETS);
			
			catNames_PeaksTroughs = new ArrayList<String>(); 
			catNames_PeaksTroughs.add(WIPTrackerConstants.CATVALUE_HOUSE_MAINTNNS);
			catNames_PeaksTroughs.add(WIPTrackerConstants.CATVALUE_CAR_MAINTNNS);
			catNames_PeaksTroughs.add(WIPTrackerConstants.CATVALUE_BILLS_ANNUAL);
			catNames_PeaksTroughs.add(WIPTrackerConstants.CATVALUE_MEDICAL);
			catNames_PeaksTroughs.add(WIPTrackerConstants.CATVALUE_DIY);
			
			catNames_SavingPots = new ArrayList<String>(); 
			catNames_SavingPots.add(WIPTrackerConstants.CATVALUE_ENTS);
			catNames_SavingPots.add(WIPTrackerConstants.CATVALUE_TOYS);
			catNames_SavingPots.add(WIPTrackerConstants.CATVALUE_PRESENTS);
			catNames_SavingPots.add(WIPTrackerConstants.CATVALUE_SCHOOL_PHOTOS);
			catNames_SavingPots.add(WIPTrackerConstants.CATVALUE_KIDZ);
			catNames_SavingPots.add(WIPTrackerConstants.CATVALUE_KIDS_BIRTHDAY);
			catNames_SavingPots.add(WIPTrackerConstants.CATVALUE_BP_SAVING);
			catNames_SavingPots.add(WIPTrackerConstants.CATVALUE_HOLIDAY_SAVING);	
			
			catNames_Unknown = new ArrayList<String>(); 
			catNames_Unknown.add(WIPTrackerConstants.CATVALUE_UNKNOWN);
			
			// now get a structure of all of them
			categoryNames = new ArrayList<String>(); 
			categoryNames.addAll(catNames_RegularSpending);
			categoryNames.addAll(catNames_PeaksTroughs);
			categoryNames.addAll(catNames_SavingPots);
			
			if (checkedMode == false) {
				categoryNames.addAll(catNames_Unknown);
			}
		}
		
		
		return categoryNames;
	}


	/**
	 * This adds the individual daily total model to a hash-map, keyed by category & date.
	 * So, for example, there's a model keyed with: "bills|02-Nov". This hash map can be
	 * built up with all daily total models for all categories in this way. The map is a
	 * convenient way to uniquely & instantly locate a daily total for a category.
	 * NOTE: As these models have already been totalled per day, this key WILL BE unique! :-)
	 */	
	private void addCatTotalsToMap(List categoryTotals, Map catTotalsForThePeriod) {

		for (Iterator iter = categoryTotals.iterator(); iter.hasNext();) {
			WIPTrackerTotalsModel catTotalModel = (WIPTrackerTotalsModel) iter.next();
			String mapKey = helper.generateCatTotalHashKey(catTotalModel);
			boolean checkNotAlreadyUsed = catTotalsForThePeriod.containsKey(mapKey);
			if (checkNotAlreadyUsed) {
				log("Error - duplicate category daily total - "+mapKey);
				System.exit(-1);
			}
			catTotalsForThePeriod.put(mapKey, catTotalModel);
		}
		
	}





	protected List<WIPTrackerTotalsModel> validateModels(List<WIPTrackerTotalsModel> models){
		
		// this method identifies the models to calculate the spending totals from
		// - it also reports on all the ones skipped
		
		
		List<WIPTrackerTotalsModel> validModels = new ArrayList<WIPTrackerTotalsModel>();
		List<WIPTrackerTotalsModel> invalidModels = new ArrayList<WIPTrackerTotalsModel>();
		StringBuffer badRowIndexes = new StringBuffer();
		StringBuffer ignoredRowIndexes = new StringBuffer();
		boolean abortBadData = false;
		boolean entriesExist = true;
		int index = 0;
		
		
		// process the models
		Iterator allModelsIterator = models.iterator();
		while (allModelsIterator.hasNext()) {
			
			WIPTrackerTotalsModel model = (WIPTrackerTotalsModel) allModelsIterator.next();

			if (helper.isModelIncluded(model,!checkedMode)) {
				if (helper.isModelValid(model,!checkedMode)) {
					
					// we can use this transaction - keep the model
					validModels.add(model);	
					
					// need to keep a sample date for the output
					// capture it now
					if (this.sampleDateFromPeriod == null) {
						this.sampleDateFromPeriod = model.getDate();
					}
					
				} else {
					abortBadData = true;
					invalidModels.add(model);
					badRowIndexes.append(model.getRowNumber()+",");
				}	
			} else {
				invalidModels.add(model);
				ignoredRowIndexes.append(model.getRowNumber()+",");
			}
			

		}
		
		// some stats to log out for checking
		if (true){ // ie is logging switched on! ;-)
			BigDecimal validTotal = helper .totalModelAmount(validModels);
			BigDecimal invalidTotal = helper.totalModelAmount(invalidModels);
			
			log("Valid entries total = "+validTotal);
			log("Invalid entries total = "+invalidTotal);
			log("Gives a combined total = "+validTotal.add(invalidTotal));
		}
		
		
		// dangerous to process incomplete data so exit!
		log("WARNING - the following rows were ignored: "+ignoredRowIndexes);
		if (abortBadData) {
			log("ERORR - some bad data in rows: "+badRowIndexes);
			if (checkedMode  == true) {
				log("Exiting - turn off 'Checked Mode' to write the output regardless.");
				System.exit(-1);
			} else {
				
				// not sure about this - thinking of setting the category of 
				// bad data models to unknown here - that way we'll get 
				// it written out as a category... 
				
				//TODO dodgy!!!!
				for (Iterator iterator = models.iterator(); iterator.hasNext();) {
					WIPTrackerTotalsModel model = (WIPTrackerTotalsModel) iterator.next();
					if (model.getStats().getUnknownScore() == 1) {
						model.setCategory("unknown");
					}
				}
			}
		}
		
		log(CommonConstants.NEWLINE);
		
		// need to record whether there is any data to process
		if (sampleDateFromPeriod != null && validModels.size() > 0) {
			this.validData  = true;
		}
		
		
		// going to attempt to set the sampleDate anyway because I now what to o/p totals
		getSampleDateFromLoadedData(models);
		
		
		// NOTE !!!! This only returns the valid models - old functionality
		return validModels;
		
	}
	
	
	private void getSampleDateFromLoadedData(List<WIPTrackerTotalsModel> models) {
		Date aDate = null;
		for (WIPTrackerTotalsModel model : models) {
			aDate = model.getDate();
			if (aDate != null) {
				sampleDateFromPeriod = aDate;
				break;
			}
		}
		
	}

	protected int determineSheetFormat(Sheet transactionsSheet){
		
		// interpret the 'type' of worksheet format from the header row
		int type = WIPTrackerConstants.WKSHT_TYPE_UNDEFINED;
		
		// get the first line from the worksheet
		String headerMash = extractHeaderRowMashFromWorksheet(transactionsSheet);
		log(headerMash);
		
		if (WIPTrackerConstants.HDRMASH_STANDARD.startsWith(headerMash)) {
			type = WIPTrackerConstants.WKSHT_TYPE_STANDARD;
		} else if (WIPTrackerConstants.HDRMASH_EXTENDED.startsWith(headerMash)) {
			type = WIPTrackerConstants.WKSHT_TYPE_EXTENDED;
		}
		
		return type;
	}
	
	
	
	protected String extractHeaderRowMashFromWorksheet(Sheet transactionsSheet){
		
		// this method gets a mash of the header row so that we can use it
		// to identify the format of the worksheet
		String headerMash = "";
		
		int noOfRows = transactionsSheet.getRows();
		
		if (noOfRows > 2) {
			
			int noOfCols = transactionsSheet.getColumns();
			
			StringBuffer buf = new StringBuffer();
			for (int colIndex = 0; colIndex < noOfCols; colIndex++) {
				Cell cell = transactionsSheet.getCell(colIndex,0);
				String cellValue = cell == null ? "" : cell.getContents();
				if (cellValue.trim().length() > 0) {
					buf.append(cellValue);
				}
				buf.append("/");
			}
			headerMash = buf.toString();
		}		
		
		return headerMash;
	}
	
	
	protected List<WIPTrackerTotalsModel> extractModelsFromSheet(Sheet transactionsSheet, int worksheetType){
		
		boolean extended = false;
		if (WIPTrackerConstants.WKSHT_TYPE_EXTENDED == worksheetType) {
			extended = true;
		}
		
		// this method simply turns the excel data into models 
		
		int noOfRows = transactionsSheet.getRows();
		
		List<WIPTrackerTotalsModel> models = new ArrayList<WIPTrackerTotalsModel>();
		boolean entriesExist = true;
		int rowIndex = 2;
		while (entriesExist) {
			
			Cell firstCell = transactionsSheet.getCell(0,rowIndex); // Oct 2012 - quick and dirty way to load expected payments
			Cell dateCell = transactionsSheet.getCell(1,rowIndex); 
			Cell amountCell = transactionsSheet.getCell(3, rowIndex); 
			Cell whereCell = transactionsSheet.getCell(4, rowIndex);
			Cell descriptionCell = transactionsSheet.getCell(6, rowIndex);
			Cell categoryCell = transactionsSheet.getCell(7, rowIndex);
			
			Cell subCatCell = null;
			Cell ownerCell = null;
			if (extended == false) {
				ownerCell = transactionsSheet.getCell(8, rowIndex);
			} else {
				subCatCell = transactionsSheet.getCell(8, rowIndex);
				ownerCell = transactionsSheet.getCell(9, rowIndex);
			}
			
			
			if ((dateCell != null)) 
			{
				// get the contents as Strings
				String firstValue = firstCell == null ? null : firstCell.getContents();
				String dateValue = dateCell == null ? null : dateCell.getContents(); 
				String amountValue = amountCell == null ? null : amountCell.getContents(); 
				String categoryValue = categoryCell == null ? null : categoryCell.getContents(); 
				String ownerValue = ownerCell == null ? null : ownerCell.getContents(); 
				String whereValue = whereCell == null ? null : whereCell.getContents(); 
				String descriptionValue = descriptionCell == null ? null : descriptionCell.getContents(); 
				
				String subCatValue = null;
				if (extended) {
					subCatValue = subCatCell == null ? null : subCatCell.getContents(); 
				}
				
				// transform the contents into models
				if (dateValue != null && dateValue.trim().length() > 0) {
					
					WIPTrackerTotalsModel model = new WIPTrackerTotalsModel();
					model.setCustomField1(firstValue);
					model.setExtended(extended);
					model.setRowNumber(rowIndex+1);
					model.setCategory(categoryValue);
					model.setOwner(ownerValue);
					model.setWhere(whereValue);
					model.setDescription(descriptionValue);
					if (amountValue != null && amountValue.length() > 0) {
						model.setAmount(helper.convertValueToBigDecimal(amountValue));
					}
					if (dateValue != null && dateValue.length() > 0) {
						model.setDate(helper.convertValueToDate(dateValue));
					}
					model.setSubCat(subCatValue);
					
					models.add(model);	
						
					
				} else {
					if (true){ // is logging on?
						log("Date value is missing - this must be the end of the data set - row index = "+rowIndex);
					}
					entriesExist = false;
				}
				
			}else {
				if (true){ // is logging on?
					log("Date cell is missing - this must be the end of the data set - row index = "+rowIndex);
				}
				entriesExist = false;
			}
			
			
			// increment count & do safety check
			rowIndex++;
			if (rowIndex>noOfRows-1){
				// log("ERROR - Stopped an infinite loop happening!!!");
				entriesExist = false;
			}
		}
		
		return models;
	}	
	
	

	protected Sheet loadWIPTrackerSheet(String pFilename){
		
		String lfilename = Neo9TestingConstants.ROOT_TEST_FOLDER+"/WIPTracker/WIP Tracker Test 1.xls";
		if (pFilename != null){
			lfilename = pFilename;
		}
		
		Workbook wipTrackerWorkbook = loadWorkbook(lfilename);
		
		// now get the applicable worksheet and return it
		Sheet sheet = wipTrackerWorkbook.getSheet(0); 
		log("Loaded worksheet: " + sheet.getName());
		return sheet;
	}

	
	protected Workbook loadWorkbook(String pFilename){
		
		log("Loading spreadsheet: "+pFilename);
		
		// first load the spreadsheet
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(new File(pFilename));
		} catch (BiffException e) {
			log("Load failed - BiffException - exiting!");
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			log("Load failed - IOException - exiting!");
			e.printStackTrace();
			System.exit(-1);
		} 
		
		
		int noOfSheets = workbook.getNumberOfSheets();
		log("Number of sheets in workbook: " + noOfSheets);
		
		return workbook;
	}	
	
	
	/**
	 * The passed in list is collated and then any duplicates are processed to produce
	 * a single entry. An example of this would be to total up any transactions present
	 * that occured on the same day
	 * 
	 * SCOOBYDO - wonder if I can make this generic as well??? Some kind of PP point?
	 * 
	 * @param items
	 * @param comparator
	 * @return
	 */
	protected List<WIPTrackerTotalsModel> produceDailyTotals(List<WIPTrackerTotalsModel> categorisedModels){
		
		List<WIPTrackerTotalsModel> dailyTotalModels = new ArrayList<WIPTrackerTotalsModel>();
		
		// use the comparator/collator approach to get the transactions per day
		WIPTrackerTotalsComparator comparator = new WIPTrackerTotalsComparator();
		comparator.setFieldToUseId(WIPTrackerTotalsModel.FID_DATE);
		List<WIPTrackerCategoryContainer> datedModels = collateItems(categorisedModels, comparator);
		
		for (Iterator<WIPTrackerCategoryContainer> iterator = datedModels.iterator(); iterator.hasNext();) {
			WIPTrackerCategoryContainer oneDaysModels = iterator.next();
			WIPTrackerTotalsModel oneDaysTotalModel = null;
			oneDaysTotalModel = helper.totalUpOneDaysModels(oneDaysModels.getTotalsInCategory());
			dailyTotalModels.add(oneDaysTotalModel);
		}
		
		return dailyTotalModels;
	}
	
	


	protected List<WIPTrackerTotalsModel> dedupeByWhereField(List<WIPTrackerTotalsModel> categorisedModels){
		
		List<WIPTrackerTotalsModel> dailyTotalModels = new ArrayList<WIPTrackerTotalsModel>();
		
		// use the comparator/collator approach to get the transactions per day
		WIPTrackerTotalsComparator comparator = new WIPTrackerTotalsComparator();
		comparator.setFieldToUseId(WIPTrackerTotalsModel.FID_WHERE);
		List<WIPTrackerCategoryContainer> datedModels = collateItems(categorisedModels, comparator);
		
		for (Iterator<WIPTrackerCategoryContainer> iterator = datedModels.iterator(); iterator.hasNext();) {
			WIPTrackerCategoryContainer oneDaysModels = iterator.next();
			WIPTrackerTotalsModel oneDaysTotalModel = null;
			oneDaysTotalModel = helper.totalUpOneDaysModels(oneDaysModels.getTotalsInCategory());
			dailyTotalModels.add(oneDaysTotalModel);
		}
		
		return dailyTotalModels;
	}	




	protected List<WIPTrackerCategoryContainer> collateItems(List<WIPTrackerTotalsModel> items, Comparator comparator){
		
		// null safety check
		if (items == null) {
			throw new IllegalArgumentException("Null value passed as list of items to collate.");
		}
		
		if (comparator == null) {
			throw new IllegalArgumentException("Null comparator passed in.");
		}		
		
		// log out some useful stuff
		if (true) { // should be checking the logging is on!
			
			log("No of items to collate = "+items.size());
			log("Collating with comparator: "+comparator.getClass().getName());
			if (comparator instanceof UtilitiesComparatorPlusAdapter) {
				int fieldToUse = ((UtilitiesComparatorPlusAdapter)(comparator)).getFieldToUseId();
				String fieldName = WIPTrackerTotalsModel.decodeFID(fieldToUse);
				log("Collating on field: "+fieldName);
			} 

		}
		
		
		// need to order the input but don't want to 
		// produce side effects so use a different list first
		List<WIPTrackerTotalsModel> orderedItems = new ArrayList<WIPTrackerTotalsModel>();
		orderedItems.addAll(items);
		Collections.sort(orderedItems,comparator);
		
		
		// now do the collation
		List<WIPTrackerCategoryContainer> collatedItems = new ArrayList<WIPTrackerCategoryContainer>();
//		List<Object> similarItems = new ArrayList<Object>();
		WIPTrackerCategoryContainer similarItems = new WIPTrackerCategoryContainer();
		WIPTrackerTotalsModel previousItem = null;
//		Map matchingItems = null;
		for (Iterator<WIPTrackerTotalsModel> iter = orderedItems.iterator(); iter.hasNext();) {
			WIPTrackerTotalsModel item = iter.next();
			
			if (previousItem == null) {
				// first time through
				similarItems.addModel(item);
				previousItem = item;
				
			} else {
				// subsequent iterations
				if (comparator.compare(item,previousItem) == 0) {
					// this needs adding to the same 'category' as the previous one
					similarItems.addModel(item);
					previousItem = item;
				} else {
					// we've started a new 'category'
					collatedItems.add(similarItems);
					similarItems = new WIPTrackerCategoryContainer();
					similarItems.addModel(item);
					previousItem = item;
				}

			}
			
			// catch the final iteration
			if (iter.hasNext() == false){
				collatedItems.add(similarItems);
			}
			
		}
		
		log("No of lists produced from collation: "+collatedItems.size());
		
		return collatedItems;
	}	
	
	
	protected List collateItems(List<Comparable> items){
		
		// null safety check
		if (items == null) {
			throw new IllegalArgumentException("Null value passed as list of items to collate.");
		}
		
		// need to order the input but don't want to 
		// produce side effects so use a different list first
		List<Comparable> orderedItems = new ArrayList<Comparable>();
		orderedItems.addAll(items);
		Collections.sort(orderedItems);
		
		
		// now do the collation
		List collatedItems = new ArrayList();
		List<Comparable> similarItems = new ArrayList<Comparable>();
		Comparable previousItem = null;
		Map matchingItems = null;
		for (Iterator iter = orderedItems.iterator(); iter.hasNext();) {
			Comparable item = (Comparable) iter.next();
			
			if (previousItem == null) {
				// first time through
				similarItems.add(item);
				previousItem = item;
				
			} else {
				// subsequent iterations
				if (item.compareTo(previousItem) == 0) {
					// this needs adding to the same 'category' as the previous one
					similarItems.add(item);
					previousItem = item;
				} else {
					// we've started a new 'category'
					collatedItems.add(similarItems);
					similarItems = new ArrayList<Comparable>();
					similarItems.add(item);
					previousItem = item;
				}

			}
			
			// catch the final iteration
			if (iter.hasNext() == false){
				collatedItems.add(similarItems);
			}
			
		}
		
		
		return collatedItems;
	}

	
	
}
