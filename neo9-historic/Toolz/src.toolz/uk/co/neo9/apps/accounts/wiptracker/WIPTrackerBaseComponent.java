package uk.co.neo9.apps.accounts.wiptracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.co.neo9.Neo9EnvironmentConstants;
import uk.co.neo9.utilities.UtilsBaseObject;

public class WIPTrackerBaseComponent extends UtilsBaseObject {

	
	protected List<String> getTrackerFilenames(int year)  {
		
		List<String> filenames = new ArrayList<String>();

		int noOfMonthsInAYear = 12;
		for (int i = 0; i < noOfMonthsInAYear; i++) {
			filenames.add(getMonthlySpreadsheetFileDetails(year, i));
		}

		return filenames;
	}
	
	
	protected String getMonthlySpreadsheetFileDetails(int year, int sheetID){
		
		System.out.println("WIPTrackerBaseComponent: Find this text to change the root folder for the spreadsheet files!");

		
//		String workingFolder = "D:/ZZ - Swap Zone/_ Dropboxes/jmcgDropBox/My Dropbox/Kathie and Simon/accounts/_ WIP/2010/";
		// String workingFolder = "//.host/Shared Folders/Dropbox/Kathie and Simon/accounts/_ WIP/2010/";

		
			
		String workingFolder = Neo9EnvironmentConstants.DROPBOX_FOLDER+"/Kathie and Simon/accounts/_ WIP/"+year+"/";
		String fileName = null;
		fileName = WIPTrackerFileConstants.getMonthlySpreadsheetFileDetails(year, sheetID);

		
		String filedetails = workingFolder+fileName;
		
		return filedetails;
	}	
	
	
	protected String getExpectedValuesSpreadsheetFileDetails(int year){
		
		System.out.println("WIPTrackerBaseComponent: Find this text to change the root folder for the spreadsheet files!");

		
//		String workingFolder = "D:/ZZ - Swap Zone/_ Dropboxes/jmcgDropBox/My Dropbox/Kathie and Simon/accounts/_ WIP/2010/";
		// String workingFolder = "//.host/Shared Folders/Dropbox/Kathie and Simon/accounts/_ WIP/2010/";
	
		String workingFolder = Neo9EnvironmentConstants.DROPBOX_FOLDER+"/Kathie and Simon/accounts/_ WIP/"+year+"/";
		String fileName = null;
		fileName = "ExpectedPayments2012.xls";

		
		String filedetails = workingFolder+fileName;
		
		return filedetails;
	}
}
