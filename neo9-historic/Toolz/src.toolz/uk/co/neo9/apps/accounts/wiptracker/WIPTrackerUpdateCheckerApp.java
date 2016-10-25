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

import uk.co.neo9.utilities.UtilsBaseObject;
import uk.co.neo9.utilities.file.FileServer;
import uk.co.neo9.utilities.misc.UtilitiesGridComparator;
import uk.co.neo9.utilities.misc.UtilitiesGridContainer;
import uk.co.neo9.utilities.misc.UtilitiesGridComparator.UtilitiesGridComparatorResult;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

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
public class WIPTrackerUpdateCheckerApp extends UtilsBaseObject{	
	
	
	protected WIPTrackerUpdateCheckerComponent engine = new WIPTrackerUpdateCheckerComponent();

	public static void main(String[] args) {
		
		WIPTrackerUpdateCheckerApp checker = new WIPTrackerUpdateCheckerApp();
		checker.go(args);
		
	}
	

	
	protected void go(String[] mainArgs){
		
		// logging control
		boolean logWorkingOut = false;
		
		// the main spready filename
		int year = 2010;	
		String workingFolder = "//.host/Shared Folders/Dropbox/Kathie and Simon/accounts/_ WIP/";
		String fileName = "Joint Tracker - " + year + ".xls";
		String filedetails = workingFolder+fileName;
		
		// get the months to check
		int[] monthsToCheck = getMonthsToProcess();
	//	int[] monthsToCheck = {Calendar.DECEMBER};
		engine.setLogWorkingOut(logWorkingOut);
		
		String results = engine.performCheck(filedetails, year, monthsToCheck);
		
		System.out.println(results);
		
	}
	
	
	protected int[] getMonthsToProcess(){
		int noOfMonthsInAYear = 12;
		int[] allMonthsInTheYear = new int[noOfMonthsInAYear];
		for (int i = 0; i < noOfMonthsInAYear; i++) {
			allMonthsInTheYear[i] = i;
		}
		return allMonthsInTheYear;
	}
	
	
	
//	private void writeDebugFile(String output, String filename) {
//		
//		String targetFolder = "C:/temp";
//		String fileDetails = targetFolder+"/"+filename;
//		
//		log("");
//		log("Writing out results now to > "+fileDetails);
//
//		try {
//			FileServer.writeTextFile(fileDetails,output);
//		} catch (IOException e) {
//			e.printStackTrace();
//			log("Error - unable to write output file!");
//			System.exit(-1);
//		}
//		
//		log(".................................. done!");
//	}
	
	
}
