package uk.co.neo9.apps.accounts.wiptracker;

import java.util.Calendar;


public class WIPTrackerTotalsGenertorQuickLaunch {
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String checkedMode = "No";
		
		int yearToUse = 2012;
		int monthToUse = Calendar.MARCH; // set this to choose the file!
		
		String wipTrackerFileName = WIPTrackerFileConstants.getMonthlySpreadsheetFileDetails(yearToUse, monthToUse);
		
		// this launcher simply inserts it's own hardcoded args if none are used
		String[] appArgs;
		
		boolean launchApp = false;
		if (args == null || args.length == 0) {
			
			System.out.println("Using hardccoded args to launch app...");
			System.out.println("(to see app args list, run with the ? character as the only arg)");
			
			String workingFolder = "D:/ZZ - Swap Zone/_ DropBoxes/jmcgDropBox/My Dropbox/Kathie and Simon/accounts/_ WIP/";
			workingFolder = workingFolder+yearToUse;
	
			String[] hardcodedArgs = {wipTrackerFileName,"out.csv",workingFolder,checkedMode};
			
			appArgs = hardcodedArgs;
			launchApp = true;
			
		} else if (args.length == 1) {
			if ("?".equals(args[0])) {
				appArgs = null;
				launchApp = true;
			} else {
				System.out.println("Launching app with passed in argument");
				appArgs = args;
				launchApp = true;
			}
			
		} else {
			
			System.out.println("Launching app with passed in args");
			appArgs = args;
			launchApp = true;
		}
		
		if (launchApp) {
			WIPTrackerTotalsGeneratorApp.main(appArgs);
		}		

	}

}
