/*
 * Created on 05-Oct-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.sandbox.coautils;

import java.util.Calendar;
import java.util.Date;

import uk.co.neo9.test.Neo9TestingConstants;

/**
 * @author Simon
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RunKYCLogReader {

	public static void main(String[] args) {
		

		// ---- start by splicing the log files
		
		String inputFileDetails = Neo9TestingConstants.ROOT_TEST_FOLDER + "coaUtils/kyc/logsToRead.txt";
		String upperBoundaryDateStr = "2050-01-01";
		String lowerBoundaryDateStr = "2007-10-04";


		Calendar cal = Calendar.getInstance();
		COAUtilities.setDateFromYYYYMMDD(cal,upperBoundaryDateStr);
		COAUtilities.setTimeFromHHMMSS(cal,"00:00:00");
		Date toDate = cal.getTime();
			
		COAUtilities.setDateFromYYYYMMDD(cal,lowerBoundaryDateStr);
		COAUtilities.setTimeFromHHMMSS(cal,"00:00:00");
		Date fromDate = cal.getTime();	
		
		

		LogSplicer shuffle = new LogSplicer();
		shuffle.setBoundaryDates(fromDate,toDate);
		shuffle.go(inputFileDetails);
		
		
		// -- now read the spliced file
		final String logFileDetails = "C:/Temp/spliced.log";
		KYCLogReader reader = new KYCLogReader();
		reader.go(logFileDetails);
		
		
	}
}
