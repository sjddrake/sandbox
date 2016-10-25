/*
 * Created on 05-Oct-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.sandbox.coautils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.co.neo9.utilities.UtilitiesTextHelper;

/**
 * @author Simon
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class COAUtilities {

	public static Date extractLogDate(String line) {
		
		Date date = null;
		try {			
			
			Calendar cal = Calendar.getInstance();
			
			int startOffset = line.indexOf("] ")+2;
			int endOffset = startOffset+10;
			String dateStr = line.substring(startOffset,endOffset);
			
			COAUtilities.setDateFromYYYYMMDD(cal,dateStr);
			
			startOffset = endOffset+1;
			endOffset = startOffset+8;
			String timeStr = line.substring(startOffset,endOffset);
		
			COAUtilities.setTimeFromHHMMSS(cal,timeStr);
			
			date = cal.getTime();	
			
		} catch (Exception e) {
			System.err.println("unable to extract date from line > "+line);
		}
		return date;
	}


	public static void setDateFromYYYYMMDD(Calendar cal, String dateStr){

		List<String> datePortions = UtilitiesTextHelper.tokenizeString(dateStr,"-");
		Integer year = Integer.valueOf((String)datePortions.get(0));
		Integer month = Integer.valueOf((String)datePortions.get(1));
		Integer day = Integer.valueOf((String)datePortions.get(2));

		cal.set(year.intValue(),month.intValue()-1,day.intValue());
	}
	

	
	public static void setTimeFromHHMMSS(Calendar cal, String timeStr){

		List<String> timePortions = UtilitiesTextHelper.tokenizeString(timeStr,":");
		
		Integer hour = Integer.valueOf((String)timePortions.get(0));
		Integer minute = Integer.valueOf((String)timePortions.get(1));
		Integer seconds = Integer.valueOf((String)timePortions.get(2));

		cal.set(Calendar.HOUR,hour.intValue());
		cal.set(Calendar.MINUTE,minute.intValue());
		cal.set(Calendar.SECOND,seconds.intValue());
		
	}

}
