package uk.co.neo9.apps.accounts.wiptracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.math.BigDecimal;

import uk.co.neo9.Neo9EnvironmentConstants;
import uk.co.neo9.test.Neo9TestingConstants;
import uk.co.neo9.utilities.file.FileServer;

public class WIPTrackerAnalyserComponent extends WIPTrackerTotalsGeneratorComponent {

//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//
//		WIPTrackerAnalyserComponent analyser = new WIPTrackerAnalyserComponent();
//		analyser.go(args);
//
//	}

	
	protected void go(String[] mainArgs){
		
//		// this makes it re-entrant when the naff launching framework caches the instance!
//		initialise();
//		
//		// get the command line details set
//		readMainArgs(mainArgs);
		
		// Scooby - naff!
		getCategoryNames();
		
		super.checkedMode = false;
		

		// load in the wiptrackers - excel worksheets
		int year = 2012;	
		List<String> filenames = getTrackerFilenames(year); //getTestTrackerFilenames();
		List<WIPTrackerContainer> trackers = loadWIPTrackers(filenames);

		/**
		 * Trackers - a List of WIPTracker container, one per Month
		 * WIPTrackerContainer - has a hash-map holding the WIPTrackerCategoryContainers, one per category,
		 * keyed by its catname, that holds the daily totals for that cat for that month. It also knows
		 * its own category name.
		 */

		
		boolean analyseByCategory = true;  // <-------------------- NOTE!!!
		List<List<Object>> sameCategoriesFromTrackers = null;
		if (analyseByCategory) {
			
			// pickout a category from each tracker
		//	String category = WIPTrackerConstants.CATVALUE_KIDZ;
			String category = WIPTrackerConstants.CATVALUE_PETROL;
		//	String category = WIPTrackerConstants.CATVALUE_BILLS;
		//	String category = WIPTrackerConstants.CATVALUE_BILLS_ANNUAL;
		//	String category = WIPTrackerConstants.CATVALUE_CAR_MAINTNNS;
		//	String category = WIPTrackerConstants.CATVALUE_HOLIDAY_SAVING;
		//	String category = WIPTrackerConstants.CATVALUE_LOANS;
			sameCategoriesFromTrackers = new ArrayList<List<Object>>();
			for (WIPTrackerContainer tracker : trackers) { // 12 trackers, one for each month
				WIPTrackerCategoryContainer bills = tracker.getCategory(category); // single category for the month in the loop
				
				// START - may 2011
				// ... for the analyser, we want a total for the month for all payments for a single reason
				// so for bills, say, all payments to life assurance but, as both Kath and I have it,
				// that means that there are more than one payment to the same 'where'
				// => we have to do some totaling here, just like for the WIP total matrix
				List<WIPTrackerTotalsModel> listWithDuplicates = bills.getTotalsInCategory();
				List<WIPTrackerTotalsModel> depudeList = dedupeByWhereField(listWithDuplicates);
				// Not sure whether if this is right for the data domain but will do for now - ie, ok for bills
				// END - may 2011
				
				List<Object> typeUnsafety = new ArrayList<Object>();
				typeUnsafety.addAll(depudeList); // get the transactions from this category in this month
				sameCategoriesFromTrackers.add(typeUnsafety); // at this point can have same payment twice 
			}
			
		} else {
			
			// 2012 - trying to get out the big payments
			sameCategoriesFromTrackers = new ArrayList<List<Object>>();
			for (WIPTrackerContainer tracker : trackers) { // 12 trackers, one for each month
				tracker.filterTransactions(WIPTrackerTotalsModel.FID_AMOUNT,new BigDecimal(100));
				
	
			}
			
		}
		
		
		
		/**
		 * sameCategoriesFromTrackers
		 * 
		 * - now have an ArrayList of 12 ArrayLists, one per month
		 * - each monthly list contains the daily total transaction models for the same category
		 * - we know the month by position only (maybe improve this with container re-use????)
		 * 
		 * NOTE: there are a different number of transaction totals per month & the data set is NOT
		 * padded out... ie, we only have objects for the dates they occured on, so we have 'missing'
		 * dates in the data set. This is important to realise for loading the grid!!!
		 * 
		 */
		
		
//too strongly typed
//
//		// now we have all the months loaded, we can analyse them!
//		for (WIPTrackerCategoryContainer categoryTransactions : sameCategoriesFromTrackers) {
//			ArrayList<WIPTrackerTotalsModel> transactions = categoryTransactions.getTotalsInCategory();
//			for (Iterator<WIPTrackerTotalsModel> iter = transactions.iterator(); iter.hasNext();) {
//				WIPTrackerTotalsModel transaction = iter.next();
//				System.out.println(transaction.getWhere()+" - "+transaction.getDescription());
//			}
//			System.out.println("-------- next tracker ------------");
//		}
//		
		
		
		
		// now load the grid form the list of lists OR adapt grid interface to take the container

//scooby - 12 June - messed this up - doh!		
		WIPTrackerTotalsGrid grid = new WIPTrackerTotalsGrid(WIPTrackerTotalsGrid.MODE_MONTHLY_ANALYSIS);
//		List<String> yAxisLabels = buildAxisLabels_MONTHS();
//		grid.setYAxisLabels(yAxisLabels );
		List<String> xAxisLabels = buildAxisLabels_MONTHS(); // <--- scooby - really would like to subsume this into the grid sub-type
		grid.setXAxisLabels(xAxisLabels );
		grid.outputAxisLabels = true;
		grid.loadGrid(sameCategoriesFromTrackers);
//scooby - 12 June - messed this up - doh!		
		
		// output!
		String output = grid.outputGrid();
		
		String outputFileDetails = Neo9EnvironmentConstants.DROPBOX_FOLDER+"Kathie and Simon/Accounts/_ WIP/wipTrackerAnalyserOUT.csv";
//		String outputFileDetails = Neo9EnvironmentConstants.ROOT_TEST_FOLDER+"WIPTracker/Analyser/wipTrackerAnalyserOUT.csv";
//		String outputFileDetails = "c:/Temp/wipTrackerAnalyserOUT.csv";
		
		try {
			FileServer.writeTextFile(outputFileDetails, output);
		} catch (IOException e) {
			System.err.println("Failed to write output file!");
			e.printStackTrace();
		}
		System.out.println(output);
	}		
	

	protected List<String> buildAxisLabels_MONTHS() {
		List<String> axisLabels = new ArrayList<String>();
		
		axisLabels.add("JAN");
		axisLabels.add("FEB");
		axisLabels.add("MAR");
		axisLabels.add("APR");
		axisLabels.add("MAY");
		axisLabels.add("JUN");
		axisLabels.add("JUL");
		axisLabels.add("AUG");
		axisLabels.add("SEP");
		axisLabels.add("OCT");
		axisLabels.add("NOV");
		axisLabels.add("DEC");
		
		return axisLabels;
	}

// moved to super class	
//
//	protected List<WIPTrackerContainer> loadWIPTrackers(List<String> wipTrackerFileNames){	
//		
//		List<WIPTrackerContainer> trackers = new ArrayList<WIPTrackerContainer>();
//		
//		for (Iterator<String> iter = wipTrackerFileNames.iterator(); iter.hasNext();) {
//			String filename = iter.next();
//			WIPTrackerContainer wipTrackerContainer = loadWIPTrackerTransactions(filename);
//			trackers.add(wipTrackerContainer);
//		}
//		
//		return trackers;
//	}	
//	
	

	
	private List<String> getTestTrackerFilenames()  {
		
		List<String> filenames = new ArrayList<String>();
		
		String workingFolder = Neo9TestingConstants.ROOT_TEST_FOLDER+"/WIPTracker/";

		filenames.add(workingFolder+"WIP Tracker Test 1.xls");
		filenames.add(workingFolder+"WIP Tracker Test 3.xls");
		filenames.add(workingFolder+"WIP Tracker Test 5.xls");
		return filenames;
	}
	
}
