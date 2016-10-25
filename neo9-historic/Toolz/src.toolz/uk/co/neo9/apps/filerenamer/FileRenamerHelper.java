package uk.co.neo9.apps.filerenamer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.co.neo9.utilities.UtilitiesTextHelper;

public class FileRenamerHelper {
	
	private final static SimpleDateFormat FILE_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
	private final static SimpleDateFormat FILE_DATE_REVERSE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public static String reformatCameraPhoneFileName(String pFileName) {
		
		// null check
		if (pFileName == null) return "";
		
		// the thing
		StringBuffer reformatedName = new StringBuffer();
		
		List<String> dateElements = UtilitiesTextHelper.tokenizeString(pFileName, "-_");
		
		// quick check that we get the expected elements
		if (dateElements.size() != 4) {
			System.err.println("Unexpected file name format: "+pFileName);
			return null;
		}

		
		try {
			Integer day, month, year;
			day = new Integer((String)dateElements.get(0));
			month = new Integer((String)dateElements.get(1));
			year = new Integer((String)dateElements.get(2));

			Calendar cal = Calendar.getInstance();
			cal.set(2000+year.intValue(),month.intValue()-1,day.intValue());
			
			Date fileDate = cal.getTime();
			String formattedFileDate = FileRenamerHelper.reverseFormatDate(fileDate);
			
			reformatedName.append(formattedFileDate);
			reformatedName.append("_");
			reformatedName.append((String)dateElements.get(3));
					
		} catch (Exception e) {
			System.err.println("Couldn't extract date elements from filename: "+pFileName);
		}
		
		// now recreate the date in
		

		
		return reformatedName.toString();
	}			
	
	
	
	
	public static String formatDate(Date pDate) {
		
		// null check
		if (pDate == null) return "";
		
		// the thing
		return FILE_DATE_FORMAT.format(pDate);
	}		

	public static String reverseFormatDate(Date pDate) {
		
		// null check
		if (pDate == null) return "";
		
		// the thing
		return FILE_DATE_REVERSE_FORMAT.format(pDate);
	}		

}
