package uk.co.neo9.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Started creating this but there's more code to harvest in the CAO 
 * DateHelper class in the VAULT... go look!!!!!!!!!!!
 * 
 * @author Simon
 *
 */


public class UtilitiesDateHelper {

	protected final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	protected final static SimpleDateFormat TIME_ONLY = new SimpleDateFormat("HH:mm"); 
	
	
	private final static SimpleDateFormat excelDateFormat = new SimpleDateFormat("dd/MM/yyyy"); //("dd/MMM/yyyy");
	private final static SimpleDateFormat openOfficeDateFormat = new SimpleDateFormat("dd MM yyyy");
	private final static SimpleDateFormat barclaysExportDateFormat = new SimpleDateFormat("dd-MMM-yy");
	
	public static String formatDate(Date pDate) {

		/** THERE ARE BETTER VERSIONS IN OTHER TOOLZ APPS so harvest them!  **/

		// null check
		if (pDate == null) return "";

		// the thing
		return SIMPLE_DATE_FORMAT.format(pDate);
	}


	public static String extractTimeFromDate(Date dateTime) { 

		String timeOnly = "";
		
		if (dateTime != null) {
			timeOnly = TIME_ONLY.format(dateTime);
		}
		
		return timeOnly;
	}
	
	
	public static Date convertValueToDate(String dateValue){
		
		Date date = null;		
		boolean parsedOk = false;
		
		try {
			date = excelDateFormat.parse(dateValue);
			parsedOk = true;
		} catch (ParseException e) {
			// do nothing
		}
		
		
		if (parsedOk == false) {
			try {
				date = openOfficeDateFormat.parse(dateValue);
				parsedOk = true;
			} catch (ParseException e) {
				// do nothing
			}
		}
		
		if (parsedOk == false) {
			try {
				date = barclaysExportDateFormat.parse(dateValue);
				parsedOk = true;
			} catch (ParseException e) {
				// do nothing
			}
		}
		
		
//		if (parsedOk == false) {; 
//			log("ERROR - unable to form date object from input: "+dateValue);
//			System.exit(-1);
//		}
//		
		return date;
	}
}
