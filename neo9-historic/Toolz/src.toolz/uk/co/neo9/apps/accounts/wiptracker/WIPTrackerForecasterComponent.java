package uk.co.neo9.apps.accounts.wiptracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.neo9.Neo9EnvironmentConstants;
import uk.co.neo9.test.Neo9TestingConstants;
import uk.co.neo9.utilities.file.FileServer;

public class WIPTrackerForecasterComponent extends WIPTrackerTotalsGeneratorComponent {

	
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
		
		
		// quick temp test of the id idea
		String category = WIPTrackerConstants.CATVALUE_BILLS;
		WIPTrackerContainer container = trackers.get(0);
		WIPTrackerCategoryContainer catContainer = container.getCategory(category);
		ArrayList<WIPTrackerTotalsModel> totals = catContainer.getTotalsInCategory();
		for (WIPTrackerTotalsModel model : totals) {
			String id = model.getUniqueIdentifier();
			System.out.println(id);
		}
		
		
		// now load the expected values
		String expectedValuesFilename = getExpectedValuesSpreadsheetFileDetails(2012);
		WIPTrackerContainer wipTrackerContainer = loadWIPTrackerTransactions(expectedValuesFilename);
		
		
		wipTrackerContainer.getClass();
		
		// assign the expected values to their respective entries
		
		
		
		// perform the magic!
		
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

	
	private List<String> getTestTrackerFilenames()  {
		
		List<String> filenames = new ArrayList<String>();
		
		String workingFolder = Neo9TestingConstants.ROOT_TEST_FOLDER+"/WIPTracker/";

		filenames.add(workingFolder+"WIP Tracker Test 1.xls");
		filenames.add(workingFolder+"WIP Tracker Test 3.xls");
		filenames.add(workingFolder+"WIP Tracker Test 5.xls");
		return filenames;
	}
	
}
