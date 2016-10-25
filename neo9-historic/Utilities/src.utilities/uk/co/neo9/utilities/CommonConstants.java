package uk.co.neo9.utilities;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Insert the type's description here.
 * Creation date: (24/10/2001 12:00:13)
 * @author: Administrator
 */
public final class CommonConstants {
	
	//2011-07-26 public static String NEWLINE = "\r\n";
	public static String NEWLINE = System.getProperty("line.separator"); //2011-07-26
	public static String STRING_QUOTES = "\"";
	public static String DOUBLE_QUOTES = STRING_QUOTES;
	public static String CHAR_QUOTES = "'";
	public static String SINGLE_QUOTES = CHAR_QUOTES;
	
	public final static int INDEX_OF_NOT_FOUND = -1;
	public final static int UNDEFINED = -1;
	
	public static String FILE_PATH_DELIMITER = "\\";
	
	// ====================== MONTH NAMES =============================
	
	public final static Map<Integer ,String> monthNamesFull =  new HashMap<Integer, String>();
	public final static Map<Integer ,String> monthNamesShort =  new HashMap<Integer, String>();
	
	public final static String MONTH_NAME_FULL_JANUARY= "January";
	public final static String MONTH_NAME_FULL_FEBRUARY= "February";
	public final static String MONTH_NAME_FULL_MARCH= "March";
	public final static String MONTH_NAME_FULL_APRIL= "April";
	public final static String MONTH_NAME_FULL_MAY= "May";
	public final static String MONTH_NAME_FULL_JUNE= "June";
	public final static String MONTH_NAME_FULL_JULY= "July";
	public final static String MONTH_NAME_FULL_AUGUST= "August";
	public final static String MONTH_NAME_FULL_SEPTEMBER= "September";
	public final static String MONTH_NAME_FULL_OCTOBER= "October";
	public final static String MONTH_NAME_FULL_NOVEMBER= "November";
	public final static String MONTH_NAME_FULL_DECEMBER= "December";	
	
	public final static String MONTH_NAME_SHORT_JANUARY= "Jan";
	public final static String MONTH_NAME_SHORT_FEBRUARY= "Feb";
	public final static String MONTH_NAME_SHORT_MARCH= "Mar";
	public final static String MONTH_NAME_SHORT_APRIL= "Apr";
	public final static String MONTH_NAME_SHORT_MAY= "May";
	public final static String MONTH_NAME_SHORT_JUNE= "Jun";
	public final static String MONTH_NAME_SHORT_JULY= "Jul";
	public final static String MONTH_NAME_SHORT_AUGUST= "Aug";
	public final static String MONTH_NAME_SHORT_SEPTEMBER= "Sep";
	public final static String MONTH_NAME_SHORT_OCTOBER= "Oct";
	public final static String MONTH_NAME_SHORT_NOVEMBER= "Nov";
	public final static String MONTH_NAME_SHORT_DECEMBER= "Dec";	
	
	
	static {
		monthNamesFull.put(Calendar.JANUARY,MONTH_NAME_FULL_JANUARY.toLowerCase());
		monthNamesFull.put(Calendar.FEBRUARY,MONTH_NAME_FULL_FEBRUARY.toLowerCase());
		monthNamesFull.put(Calendar.MARCH,MONTH_NAME_FULL_MARCH.toLowerCase());
		monthNamesFull.put(Calendar.APRIL,MONTH_NAME_FULL_APRIL.toLowerCase());
		monthNamesFull.put(Calendar.MAY,MONTH_NAME_FULL_MAY.toLowerCase());
		monthNamesFull.put(Calendar.JUNE,MONTH_NAME_FULL_JUNE.toLowerCase());
		monthNamesFull.put(Calendar.JULY,MONTH_NAME_FULL_JULY.toLowerCase());
		monthNamesFull.put(Calendar.AUGUST,MONTH_NAME_FULL_AUGUST.toLowerCase());
		monthNamesFull.put(Calendar.SEPTEMBER,MONTH_NAME_FULL_SEPTEMBER.toLowerCase());
		monthNamesFull.put(Calendar.OCTOBER,MONTH_NAME_FULL_OCTOBER.toLowerCase());
		monthNamesFull.put(Calendar.NOVEMBER,MONTH_NAME_FULL_NOVEMBER.toLowerCase());
		monthNamesFull.put(Calendar.DECEMBER,MONTH_NAME_FULL_DECEMBER.toLowerCase());
		
		
		monthNamesShort.put(Calendar.JANUARY,MONTH_NAME_SHORT_JANUARY.toLowerCase());
		monthNamesShort.put(Calendar.FEBRUARY,MONTH_NAME_SHORT_FEBRUARY.toLowerCase());
		monthNamesShort.put(Calendar.MARCH,MONTH_NAME_SHORT_MARCH.toLowerCase());
		monthNamesShort.put(Calendar.APRIL,MONTH_NAME_SHORT_APRIL.toLowerCase());
		monthNamesShort.put(Calendar.MAY,MONTH_NAME_SHORT_MAY.toLowerCase());
		monthNamesShort.put(Calendar.JUNE,MONTH_NAME_SHORT_JUNE.toLowerCase());
		monthNamesShort.put(Calendar.JULY,MONTH_NAME_SHORT_JULY.toLowerCase());
		monthNamesShort.put(Calendar.AUGUST,MONTH_NAME_SHORT_AUGUST.toLowerCase());
		monthNamesShort.put(Calendar.SEPTEMBER,MONTH_NAME_SHORT_SEPTEMBER.toLowerCase());
		monthNamesShort.put(Calendar.OCTOBER,MONTH_NAME_SHORT_OCTOBER.toLowerCase());
		monthNamesShort.put(Calendar.NOVEMBER,MONTH_NAME_SHORT_NOVEMBER.toLowerCase());
		monthNamesShort.put(Calendar.DECEMBER,MONTH_NAME_SHORT_DECEMBER.toLowerCase());

	}

	
	
	public static List<String> getAllMonthNamesFull() {
		
		List<String> monthNames;
		monthNames = (List<String>) monthNamesFull.values();
		
		return monthNames;
	}

	
	public static List<String> getAllMonthNamesShort() {
		
		List<String> monthNames;
		monthNames = (List<String>) monthNamesShort.values();
		
		return monthNames;
	}
	
	
	
	
	
	public static String decodeMonthID_English(int month){
		
		/*
		 * Can't do this because the contents of the hash maps are all lower case:
		 * 
		 * monthName = monthNamesFull.get(month);
		 * 
		 */
		
		
		String monthName = "";
		
		switch (month) {
		case Calendar.JANUARY: monthName= MONTH_NAME_FULL_JANUARY; break;
		case Calendar.FEBRUARY: monthName= MONTH_NAME_FULL_FEBRUARY; break;
		case Calendar.MARCH: monthName= MONTH_NAME_FULL_MARCH; break;
		case Calendar.APRIL: monthName= MONTH_NAME_FULL_APRIL; break;
		case Calendar.MAY: monthName= MONTH_NAME_FULL_MAY; break;
		case Calendar.JUNE: monthName= MONTH_NAME_FULL_JUNE; break;
		case Calendar.JULY: monthName= MONTH_NAME_FULL_JULY; break;
		case Calendar.AUGUST: monthName= MONTH_NAME_FULL_AUGUST; break;
		case Calendar.SEPTEMBER: monthName= MONTH_NAME_FULL_SEPTEMBER; break;
		case Calendar.OCTOBER: monthName= MONTH_NAME_FULL_OCTOBER; break;
		case Calendar.NOVEMBER: monthName= MONTH_NAME_FULL_NOVEMBER; break;
		case Calendar.DECEMBER: monthName= MONTH_NAME_FULL_DECEMBER; break;
		default:
			break;
		}
		
		
		return monthName;
	}
	
	
	public static String decodeMonthID_EnglishShort(int month){
		
		
		/*
		 * Can't do this because the contents of the hash maps are all lower case:
		 * 
		 * monthName = monthNamesFull.get(month);
		 * 
		 */		
		
		String monthName = "";
		
		switch (month) {
		case Calendar.JANUARY: monthName= MONTH_NAME_SHORT_JANUARY; break;
		case Calendar.FEBRUARY: monthName= MONTH_NAME_SHORT_FEBRUARY; break;
		case Calendar.MARCH: monthName= MONTH_NAME_SHORT_MARCH; break;
		case Calendar.APRIL: monthName= MONTH_NAME_SHORT_APRIL; break;
		case Calendar.MAY: monthName= MONTH_NAME_SHORT_MAY; break;
		case Calendar.JUNE: monthName= MONTH_NAME_SHORT_JUNE; break;
		case Calendar.JULY: monthName= MONTH_NAME_SHORT_JULY; break;
		case Calendar.AUGUST: monthName= MONTH_NAME_SHORT_AUGUST; break;
		case Calendar.SEPTEMBER: monthName= MONTH_NAME_SHORT_SEPTEMBER; break;
		case Calendar.OCTOBER: monthName= MONTH_NAME_SHORT_OCTOBER; break;
		case Calendar.NOVEMBER: monthName= MONTH_NAME_SHORT_NOVEMBER; break;
		case Calendar.DECEMBER: monthName= MONTH_NAME_SHORT_DECEMBER; break;
		default:
			break;
		}
		

		return monthName;
	}	
	
	
	public static int getMonthIdFromName(String monthName){
		
		int monthId = UNDEFINED;
		
		if (monthName == null || monthName.trim().length() == 0) {
			return monthId;
		}
		
		boolean foundFull = monthNamesFull.containsValue(monthName.toLowerCase());
		if (foundFull) {

			Set<Entry<Integer, String>> entries = monthNamesFull.entrySet();
			for (Entry<Integer, String> entry : entries) {
				foundFull = entry.getValue().equalsIgnoreCase(monthName);
				if (foundFull) {
					monthId = entry.getKey();
					break;
				}
			}

		}
		
		boolean foundShort = false;
		if (foundFull == false) {
			foundShort = monthNamesShort.containsValue(monthName.toLowerCase());
		}
		if (foundShort) {

			Set<Entry<Integer, String>> entries = monthNamesShort.entrySet();
			for (Entry<Integer, String> entry : entries) {
				foundShort = entry.getValue().equalsIgnoreCase(monthName);
				if (foundShort) {
					monthId = entry.getKey();
					break;
				}
			}

		}		
		
		
		return monthId;
	}
	
	
}
