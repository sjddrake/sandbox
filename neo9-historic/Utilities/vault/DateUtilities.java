/*
 * Created on 06-Apr-06
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.lloydstsb.chordiant.coa.helpers;



import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author ct011763 Kuldip Bajwa & Aaron Devaney
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DateUtilities {
 
	public DateUtilities() {
		super();		
	}
	
	public static Calendar convertDateToCalendar(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
			
		calendar.setTime(date);
			
		return calendar;		
	}


   	//	Default Minimum Fractional digits
	private static final int MIN_DIGITS = 2;
  
   	// Default Maximum Fractional digits
   	private static final int MAX_DIGITS = 2;
  
	// Default date format
    private static final String DATEFORMAT = "dd/MM/yyyy";
	  
	/**
	 * Method to format a date object to a string of supplied format
	 * If the format is null, the default MM-dd-yyyy is assumed
	 * Returns null if the format string is invalid
	 * @param <b>date</b>The date object to be formatted
	 * @param <b>format</b> The date format
	 * @return <b>String</b> The string representation of date
	 */
	public static String format( Date date, String format ) {		 
		if(ValidationHelper.isEmpty(format)) {
			format = DATEFORMAT;
		}
		   
		SimpleDateFormat sdf = new SimpleDateFormat( format );
		   
		return sdf.format( date );		  		
	
	}
	  
	/**
	 * Method to round a double value to the given precision
	 * @param <b>val</b> The double to be rounded
	 * @param <b>precision</b> Rounding precision
	 * @return <b>double</b> The rounded value
	 */
	 public static double round( double val, int precision ) {   
		 // Multiply by 10 to the power of precision and add 0.5 for rounding up
		 // Take the nearest integer smaller than this value
		 val = Math.floor( val * Math.pow( 10, precision ) + 0.5 );
	    
		 // Divide it by 10**precision to get the rounded value
		 return val / Math.pow( 10, precision );
	   }
	  
	   /**
		* Method to format a double value to a string 
		* @param <b>value</b>The date object to be formatted
		* @param <b>minDigits</b>The date object to be formatted
		* @param <b>maxDigits</b>The date object to be formatted  
		* @return <b>String</b> The string representation of the double value passed
		*/
	   public static String format( double value, int minDigits, int maxDigits ) {
	    
		 // Number format class to format the values
		 NumberFormat numFormat = NumberFormat.getInstance();
		 numFormat.setMinimumFractionDigits( minDigits );
		 numFormat.setMaximumFractionDigits( maxDigits );
	
		 return numFormat.format( value );
	   }
	  
	   /**
		* This method reads a properties file which is passed as
		* the parameter to it and load it into a java Properties 
		* object and returns it.
		* @param <b>file</b> Represents the .properties file
		* @return <b>Properties</b>The properties object
		* @throws <b>IOException</b> The exception this method can throw
		*/
	   public static Properties loadParams( String file ) throws IOException {
	    
		 // Loads a ResourceBundle and creates Properties from it
		 Properties prop = new Properties();
		 ResourceBundle bundle = ResourceBundle.getBundle( file );
	   
		 Enumeration enum = bundle.getKeys();
		 String key = null;
	  
		 while( enum.hasMoreElements() ) {
		   key = (String)enum.nextElement();
		   prop.put( key, bundle.getObject( key ) );
		 }
		 
		 return prop;
	   }
	  
	   /**
		* Method to parse a date string in the format MM-dd-yyyy to a Date object
		* Returns null if the input string does not match the format
		* @param <b>dateString</b> The string to be converted to a Date 
		* @return <b>Date</b>The parsed date object
		*/
	   public static Date parse( String dateString ) throws ParseException {
		 return parse( dateString, null );
	   }
	  
	   /**
		* Method to parse a date string in the supplied format to a Date object.
		* If the format is null, the default MM-dd-yyyy is assumed
		* Returns null if the input string does not match the format
		* @param <b>dateString</b> The string to be converted to a Date 
		* @param <b>formatString</b> The format of the string
		* @return <b>Date</b> The Date object of the string parsed
		*/
	   public static Date parse( String dateString, String formatString ) throws ParseException {
		
		   if( formatString == null ) {
			 formatString = DATEFORMAT;
		   }
		   
		   SimpleDateFormat sdf = new SimpleDateFormat( formatString );
		   
		   return sdf.parse( dateString );
		 
	   }
	  
	   /**
		* Method to format a date object to a MM-dd-yyyy string
		* @param <b>date</b>The date object to be formatted
		* @return <b>String</b> String representation of date
		*/
	   public static String format( Date date ) {
		 return format( date, null );
	   }
	  
	   /**
		* Method to format a double value to a string 
		* @param <b>value</b>The date object to be formatted
		* @return <b>String</b> The string representation of the double value 
		*/
	   public static String format( double value ) {
			// Number format class to format the values
			NumberFormat numFormat = NumberFormat.getInstance();
			numFormat.setMinimumFractionDigits( MIN_DIGITS );
			numFormat.setMaximumFractionDigits( MAX_DIGITS );
		 	
			return numFormat.format( value );
	   }
	   
	   
	/**
	 * **************************************************************
	 * Calculate the age by subtracting the dateOfBirth from the current 
	 * date and return the number of years (Rounded down) 	 
	 * **************************************************************
	 * **************************************************************
	 * @param	dateOfBirth
	 * @return	Integer
	 * 
	 * @throws 	TBD
	 * **************************************************************
	 */ 	 	
	public static Integer getAge(Date dateOfBirth) {			
		Calendar currentDate = Calendar.getInstance();
	
		Calendar dob = Calendar.getInstance();
	
		dob.setTime(dateOfBirth);
	
		/*
		 * Get age based on year
		 */ 
		int age = currentDate.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
	
		dob.add(Calendar.YEAR, age);
	
		/*
		 * If this year's birthday has not happened yet, subtract one from age
		 */ 
		if(currentDate.before(dob)) {
			age--;
		}
	
		return new Integer(age);		
	
	}
	
	public static String getTime(String format) {
		if(ValidationHelper.isEmpty(format)) {
			format = "HHmmss";
		}
	
		DateFormat dF = new SimpleDateFormat(format);
	
		Date date = new Date();
	
		return dF.format(date);
	}

	public static String getDate(String format) {
		if(ValidationHelper.isEmpty(format)) {
			format = "ddMMyyyy";
		}
	
		DateFormat dF = new SimpleDateFormat(format);
	
		Date date = new Date();
	
		return dF.format(date);
	}
 
 	/**
 	 * 
 	 * @param utilDate
 	 * @return
 	 */
 	public static java.sql.Date toSQLDate(Date utilDate) {
 		
 		java.sql.Date sqlDate = null;
 		if (utilDate!=null) {
			sqlDate = new java.sql.Date(utilDate.getTime());	
 		}
		return sqlDate;
 	}

	/**
	 * 
	 * @param sqlDate
	 * @return
	 */
	public static Date toUtilDate(java.sql.Date sqlDate) {
 		
		Date utilDate = null;
		if (sqlDate!=null) {
			utilDate = new Date(sqlDate.getTime());	
		}
		return utilDate;
	}

	/*
	 * 	This needs to go into a common helper class
	 * 
	 */
	public static int calculateDurationInMonths(Date startDate) {

		int duration_m = 0; //	duration in years
		Calendar start_m = null; //	date of birth
		Calendar today_m = null; //	today

		start_m = Calendar.getInstance();
		start_m.setTime(startDate);

		// Create a calendar object with today's date
		today_m = Calendar.getInstance();

		// Get duration based on month
		duration_m =
			(today_m.get(Calendar.MONTH) - start_m.get(Calendar.MONTH))
				+ (12 * (today_m.get(Calendar.YEAR) - start_m.get(Calendar.YEAR)));

		return duration_m;
	}
 	
}//end of class
