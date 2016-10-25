package uk.co.neo9.apps.accounts.wiptracker;

import java.util.Calendar;

public class WIPTrackerFileConstants {

	
	public static String getMonthlySpreadsheetFileDetails(int year, int javaMonthID){
		
		String fileName = null;
		
		switch (year) {
			case 2009: fileName = getMonthlySpreadsheetFileDetails2009(javaMonthID);
				break;
			case 2010: fileName = getMonthlySpreadsheetFileDetails2010(javaMonthID);
				break;
			case 2011: fileName = getMonthlySpreadsheetFileDetails2011(javaMonthID);
				break;
			case 2012: fileName = getMonthlySpreadsheetFileDetails2012(javaMonthID);
				break;
		}
		
		return fileName;
	}
	
	
	
	private static String getMonthlySpreadsheetFileDetails2009(int javaMonthID){
	
		String fileName = null;
		
		switch (javaMonthID) {
		case Calendar.JANUARY: fileName = "WIP Tracker - 2009.01 - January 2009.xls"; break;
		case Calendar.FEBRUARY: fileName = "WIP Tracker - 2009.02 - February 2009.xls"; break;
		case Calendar.MARCH: fileName = "WIP Tracker - 2009.03 - March 2009.xls"; break;
		case Calendar.APRIL: fileName = "WIP Tracker - 2009.04 - April 2009.xls"; break;
		case Calendar.MAY: fileName = "WIP Tracker - 2009.05 - May 2009.xls"; break;
		case Calendar.JUNE: fileName = "WIP Tracker - 2009.06 - June 2009.xls"; break;
		case Calendar.JULY: fileName = "WIP Tracker - 2009.07 - July 2009.xls"; break;
		case Calendar.AUGUST: fileName = "WIP Tracker - 2009.08 - August 2009.xls"; break;
		case Calendar.SEPTEMBER: fileName = "WIP Tracker - 2009.09 - September 2009.xls"; break;
		case Calendar.OCTOBER: fileName = "WIP Tracker - 2009.10 - October 2009.xls"; break;
		case Calendar.NOVEMBER: fileName = "WIP Tracker - 2009.11 - November 2009.xls"; break;
		case Calendar.DECEMBER: fileName = "WIP Tracker - 2009.12 - December 2009.xls"; break;
		default:
			break;
		}

		return fileName;
	}	
	
	
	
	
	private static String getMonthlySpreadsheetFileDetails2010(int javaMonthID){
	
		String fileName = null;
		
		switch (javaMonthID) {
		case Calendar.JANUARY: fileName = "WIP Tracker - 2010.01 - January 2010.xls"; break;
		case Calendar.FEBRUARY: fileName = "WIP Tracker - 2010.02 - February 2010.xls"; break;
		case Calendar.MARCH: fileName = "WIP Tracker - 2010.03 - March 2010.xls"; break;
		case Calendar.APRIL: fileName = "WIP Tracker - 2010.04 - April 2010.xls"; break;
		case Calendar.MAY: fileName = "WIP Tracker - 2010.05 - May 2010.xls"; break;
		case Calendar.JUNE: fileName = "WIP Tracker - 2010.06 - June 2010.xls"; break;
		case Calendar.JULY: fileName = "WIP Tracker - 2010.07 - July 2010.xls"; break;
		case Calendar.AUGUST: fileName = "WIP Tracker - 2010.08 - August 2010.xls"; break;
		case Calendar.SEPTEMBER: fileName = "WIP Tracker - 2010.09 - September 2010.xls"; break;
		case Calendar.OCTOBER: fileName = "WIP Tracker - 2010.10 - October 2010.xls"; break;
		case Calendar.NOVEMBER: fileName = "WIP Tracker - 2010.11 - November 2010.xls"; break;
		case Calendar.DECEMBER: fileName = "WIP Tracker - 2010.12 - December 2010.xls"; break;
		default:
			break;
		}

		return fileName;
	}	
	
	private static String getMonthlySpreadsheetFileDetails2011(int javaMonthID){
		
		String fileName = null;
		
		switch (javaMonthID) {
		case Calendar.JANUARY: fileName = "WIP Tracker - 2011.01 - January 2011.xls"; break;
		case Calendar.FEBRUARY: fileName = "WIP Tracker - 2011.02 - February 2011.xls"; break;
		case Calendar.MARCH: fileName = "WIP Tracker - 2011.03 - March 2011.xls"; break;
		case Calendar.APRIL: fileName = "WIP Tracker - 2011.04 - April 2011.xls"; break;
		case Calendar.MAY: fileName = "WIP Tracker - 2011.05 - May 2011.xls"; break;
		case Calendar.JUNE: fileName = "WIP Tracker - 2011.06 - June 2011.xls"; break;
		case Calendar.JULY: fileName = "WIP Tracker - 2011.07 - July 2011.xls"; break;
		case Calendar.AUGUST: fileName = "WIP Tracker - 2011.08 - August 2011.xls"; break;
		case Calendar.SEPTEMBER: fileName = "WIP Tracker - 2011.09 - September 2011.xls"; break;
		case Calendar.OCTOBER: fileName = "WIP Tracker - 2011.10 - October 2011.xls"; break;
		case Calendar.NOVEMBER: fileName = "WIP Tracker - 2011.11 - November 2011.xls"; break;
		case Calendar.DECEMBER: fileName = "WIP Tracker - 2011.12 - December 2011.xls"; break;
		default:
			break;
		}

		return fileName;
	}	
	
	
	private static String getMonthlySpreadsheetFileDetails2012(int javaMonthID){
	
		String fileName = null;
		
		switch (javaMonthID) {
		case Calendar.JANUARY: fileName = "WIP Tracker - 2012.01 - January 2012.xls"; break;
		case Calendar.FEBRUARY: fileName = "WIP Tracker - 2012.02 - February 2012.xls"; break;
		case Calendar.MARCH: fileName = "WIP Tracker - 2012.03 - March 2012.xls"; break;
		case Calendar.APRIL: fileName = "WIP Tracker - 2012.04 - April 2012.xls"; break;
		case Calendar.MAY: fileName = "WIP Tracker - 2012.05 - May 2012.xls"; break;
		case Calendar.JUNE: fileName = "WIP Tracker - 2012.06 - June 2012.xls"; break;
		case Calendar.JULY: fileName = "WIP Tracker - 2012.07 - July 2012.xls"; break;
		case Calendar.AUGUST: fileName = "WIP Tracker - 2012.08 - August 2012.xls"; break;
		case Calendar.SEPTEMBER: fileName = "WIP Tracker - 2012.09 - September 2012.xls"; break;
		case Calendar.OCTOBER: fileName = "WIP Tracker - 2012.10 - October 2012.xls"; break;
		case Calendar.NOVEMBER: fileName = "WIP Tracker - 2012.11 - November 2012.xls"; break;
		case Calendar.DECEMBER: fileName = "WIP Tracker - 2012.12 - December 2012.xls"; break;
		default:
			break;
		}

		return fileName;
	}	
	
	
}
