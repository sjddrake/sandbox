/*
 * Created on 10-Sep-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.sandbox.coautils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import uk.co.neo9.test.Neo9TestingConstants;
import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.file.FileServer;
import uk.co.neo9.utilities.file.RelationalFileHelper;



/**
 * @author ct039314
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DailyStatsSQLGenerator implements DailyStatsStatusConstants {

	private final static String MODE_SQL = "sql";
	private final static String MODE_FORMAT = "format";	
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
	private final static String LINE_IDENTIFIER = "X->,";
	private final static String LINE_IDENTIFIER_CONCAT = "'X->,'||";

	public static void main(String[] args) {
		
		String mode = MODE_SQL;
		String inputFileDetails = null;
		String outputFileDetails = null;
		
		boolean argsSet = false;
		if (args != null && args.length > 0){
			mode = args[0];
			inputFileDetails = args[1];
			outputFileDetails = args[2];
			argsSet = true;
		} 
		
		
		DailyStatsSQLGenerator g = new DailyStatsSQLGenerator();
		String output = null;
		if (MODE_FORMAT.equals(mode)){
			if (!argsSet){
				inputFileDetails = Neo9TestingConstants.ROOT_TEST_FOLDER + "coaUtils/dailyTotals_unformated.csv";
				outputFileDetails = Neo9TestingConstants.ROOT_TEST_FOLDER + "coaUtils/dailyTotals.csv";				
			}
			output = g.go_formatter(inputFileDetails,outputFileDetails);
		} else if (MODE_SQL.equals(mode)){
			if (!argsSet){
				outputFileDetails = Neo9TestingConstants.ROOT_TEST_FOLDER + "coaUtils/";				
			}
			output = g.go_sqlGeneration(outputFileDetails);
		}
		
		System.out.println(output);
	

		
	}
	
	private String go_formatter(String inputFileDetails, String outputFileDetails) {

		// read the file in as lines
		Vector resultLines = null;
		try {
			resultLines = FileServer.readTextFile(inputFileDetails);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		// process the log files
		ArrayList dates = new ArrayList();
		Map map = new Hashtable();
		for (Iterator iter = resultLines.iterator(); iter.hasNext();) {
			String line = (String) iter.next();
			StatsTotalsDetail detail = processResultsLine(line);
			if (detail != null) {
				dates.add(detail.date);
				map.put(detail.getMapKey(),detail);		
			}
		}		
		
		// rationalise the processed details
		ArrayList processedResults = rationaliseStatsTotals(map, dates);
		
		// format the status results
		String output = formatStatusResults(processedResults);
		
		
		// make a destination file name from the passed in one
		StringBuffer fileNameBuf = new StringBuffer();
		StringBuffer fileExtBuf = new StringBuffer();
		FileServer.breakdownFileName(outputFileDetails,fileNameBuf,fileExtBuf);		
		try {
			outputFileDetails = fileNameBuf.toString() + "_technical." + fileExtBuf.toString();
			FileServer.writeTextFile(outputFileDetails,output);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}		

		// now produce a summary view that means something! ;-)
		output = formatLogicalResults(processedResults);
		try {
			outputFileDetails = fileNameBuf.toString() + "_business." + fileExtBuf.toString();
			FileServer.writeTextFile(outputFileDetails,output);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}	
		
		return output;
		
	}

	
	
	private ArrayList rationaliseStatsTotals(Map statsTotalsMap, ArrayList dateKeys) {
		
		// aim of this method is to insert zero count records into
		// the set because the stats don't include them
		ArrayList rationalisedStats = new ArrayList();
		StatsTotalsDetail utility = new StatsTotalsDetail();
		
		
		
		
		String previousDateKey = null;
		for (Iterator iter = dateKeys.iterator(); iter.hasNext();) {
			String dateKey = (String) iter.next();
			
			if (!dateKey.equals(previousDateKey)){
				previousDateKey = dateKey;

				StatsTotalsResult statsResult = new StatsTotalsResult();
				statsResult.date = dateKey;
				rationalisedStats.add(statsResult);
				
				ArrayList statusCodes = getStatusCodes();
				for (Iterator iterator = statusCodes.iterator(); iterator.hasNext();) {
					String statusCode = (String) iterator.next();
					
					// get the count detail for this date & this status code
					StatsTotalsDetail thisDetail = null;
					thisDetail = (StatsTotalsDetail)statsTotalsMap.get(utility.getMapKey(dateKey,statusCode));
					
					// if the count detail is null, create a new zero count instance
					if (thisDetail == null) {
						thisDetail = new StatsTotalsDetail();
						thisDetail.status = statusCode;
						thisDetail.date = dateKey;
					} // else use the returned one
					
					// add the statusDetail to the output
					statsResult.add(thisDetail);
				}
			}
		}
		
		
		// finally sort the output ... if we still need to
		
		return rationalisedStats;
	}

	private ArrayList getStatusCodes() {
		ArrayList codes  = new ArrayList(NO_OF_STATUS_CODES);
		for (int i = 0; i < NO_OF_STATUS_CODES; i++) {
			codes.add(getStatusCode(i));
		}
		return codes;
	}
	

	private String getStatusCode(int codeIndex) {
		String code = null;
		
		switch (codeIndex) {
		case INDEX_PROCESSED: code = PROCESSED;	break;
		case INDEX_CREATED: code = CREATED;	break;
		case INDEX_CONFIRMED: code = CONFIRMED;	break;
		case INDEX_CANCELLED: code = CANCELLED;	break;
		case INDEX_SCHEDULED: code = SCHEDULED;	break;
		case INDEX_FAILED: code = FAILED;	break;
		case INDEX_EXCEPTION: code = EXCEPTION;	break;
		case INDEX_EXECUTING: code = EXECUTING;	break;
		case INDEX_TMS: code = TMS;	break;
		default: code = "decode failed"; break;
		}
		
		return code;
	}

	private String formatStatusResults(ArrayList results) {
		
		
		// =================== CSV HEADER =======================
		StringBuffer buf = new StringBuffer();
		buf.append("Date");
		buf.append(",");		
		for (Iterator iter = getStatusCodes().iterator(); iter.hasNext();) {
			String code = (String) iter.next();
			buf.append(code);
			if (iter.hasNext()){
				buf.append(",");
			}			
		}
		buf.append(CommonConstants.NEWLINE);
		
		
		// ====================== the totals ==========================
		for (Iterator iter = results.iterator(); iter.hasNext();) {
			StatsTotalsResult result = (StatsTotalsResult) iter.next();
			buf.append(result.date);
			buf.append(",");
			List statusCounts = Arrays.asList(result.statusCounts);
			for (Iterator iterator = statusCounts.iterator(); iterator.hasNext();) {
				StatsTotalsDetail detail = (StatsTotalsDetail) iterator.next();
				buf.append(detail.count);
				if (iterator.hasNext()){
					buf.append(",");
				}
			}
			buf.append(CommonConstants.NEWLINE);
		}
		
		
		return buf.toString();
	}
	

	private String formatLogicalResults(ArrayList results) {


		
		// =================== CSV HEADER =======================
		StringBuffer buf = new StringBuffer();
		buf.append("Date");
		buf.append(",");	
		
		buf.append("STPd OK");
		buf.append(",");

		buf.append("New Pendings");
		buf.append(",");
		
		buf.append("Cancelled Pendings");
		buf.append(",");
		
		buf.append("Exceptions (Chains)");
		buf.append(",");
		
		buf.append("Failed STPs");
		
		buf.append(CommonConstants.NEWLINE);
		
		
		// ====================== the totals ==========================
		for (Iterator iter = results.iterator(); iter.hasNext();) {
			
			StatsTotalsResult result = (StatsTotalsResult) iter.next();
			
			buf.append(result.date);
			buf.append(",");
			
			buf.append(result.getCount(INDEX_PROCESSED));
			buf.append(",");
	
			buf.append(result.getCount(INDEX_SCHEDULED));
			buf.append(",");
			
			buf.append(result.getCount(INDEX_CANCELLED));
			buf.append(",");
			
			buf.append(result.getCount(INDEX_EXCEPTION));
			buf.append(",");
			
			buf.append(result.getCount(INDEX_SPECIAL_ERROR_TOTAL));
			
			buf.append(CommonConstants.NEWLINE);
		}
		
		
		return buf.toString();
	}
	
	

	private StatsTotalsDetail processResultsLine(String line) {
		
		StatsTotalsDetail details = null;
		if (line.startsWith(LINE_IDENTIFIER)){
			// System.out.println(line);
			
			Vector lineFields = FileServer.extractCSVFields(line);
			
			details = new StatsTotalsDetail();
			details.count = new Integer((String)lineFields.get(2)).intValue();
			details.date = (String)lineFields.get(3);
			details.status = (String)lineFields.get(1);
			
			//System.out.println(lineFields.get(1)+" "+lineFields.get(2)+" "+lineFields.get(3));
			
		}
		
		return details;
		
	}

	public String go_sqlGeneration(String outputFileDetails){
		
		StringBuffer dailyStatsBuf = new StringBuffer();
		dailyStatsBuf.append("spool C:\\Simonz\\_COA\\_Implementation\\reports\\dailyTotals_unformated.csv");
		dailyStatsBuf.append(CommonConstants.NEWLINE);
		dailyStatsBuf.append(CommonConstants.NEWLINE);


		StringBuffer channelStatsBuf = new StringBuffer();
		channelStatsBuf.append("spool C:\\Simonz\\_COA\\_Implementation\\reports\\channelTotals.csv");
		channelStatsBuf.append(CommonConstants.NEWLINE);
		channelStatsBuf.append(CommonConstants.NEWLINE);

		
		Date startingDate = null;
		Date endDate = null;
		Date todaysDate = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.set(2007,7,30);
		startingDate = cal.getTime();

		boolean notDoneYet = true;
		while(notDoneYet){
			
			cal.add(Calendar.DATE,1);
			endDate = cal.getTime();
			
			String dailyStatsSQL = generateSQLScript_DailyStats(startingDate,endDate);
			dailyStatsBuf.append(dailyStatsSQL);
			
			String channelStatsSQL = generateSQLScript_ChannelStats(startingDate,endDate);
			channelStatsBuf.append(channelStatsSQL);			
			
			if (startingDate.before(todaysDate)){
				startingDate = endDate;
			} else {
				notDoneYet = false;
			}
			
		}
		
		dailyStatsBuf.append(CommonConstants.NEWLINE);
		dailyStatsBuf.append("spool off");
		dailyStatsBuf.append(CommonConstants.NEWLINE);

		channelStatsBuf.append(CommonConstants.NEWLINE);
		channelStatsBuf.append("spool off");
		channelStatsBuf.append(CommonConstants.NEWLINE);		
		
		try {
			FileServer.writeTextFile(outputFileDetails+"dailyTotals.sql",dailyStatsBuf.toString());
			FileServer.writeTextFile(outputFileDetails+"channelTotals.sql",channelStatsBuf.toString());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		
		return dailyStatsBuf.toString();	// well, this breaks now I've got TWO scripts generated! :-)
	}


	private String generateSQLScript_ChannelStats(Date startingDate, Date endDate) {
	
		StringBuffer buf = new StringBuffer();
		
		
		
		String startDateStr = sdf.format(startingDate);
		String endDateStr = sdf.format(endDate);
		

		buf.append("select ");
//		buf.append(LINE_IDENTIFIER_CONCAT);
		buf.append("channel||','");
		buf.append(CommonConstants.NEWLINE);
		buf.append("||count(*)||',' as chanel_counts");
		buf.append(CommonConstants.NEWLINE);
		buf.append("from address_change_sessions");
		buf.append(CommonConstants.NEWLINE);
		buf.append("where id > 50");
		buf.append(CommonConstants.NEWLINE);
		buf.append("and date_started between TO_DATE('" + startDateStr + "','DD/MM/YYYY')");
		buf.append(CommonConstants.NEWLINE);
		buf.append("and TO_DATE('" + endDateStr + "','DD/MM/YYYY')");
		buf.append(CommonConstants.NEWLINE);
		buf.append("group by channel;");
		buf.append(CommonConstants.NEWLINE);
		buf.append("");
		buf.append("");
//		buf.append("");
//		buf.append("select count(*) as Todays_Total");
//		buf.append(CommonConstants.NEWLINE);
//		buf.append("from address_change_sessions");
//		buf.append(CommonConstants.NEWLINE);
//		buf.append("where id > 50");
//		buf.append(CommonConstants.NEWLINE);
//		buf.append("and date_started between TO_DATE('" + startDateStr + "','DD/MM/YYYY')");
//		buf.append(CommonConstants.NEWLINE);
//		buf.append("and TO_DATE('" + endDateStr + "','DD/MM/YYYY');");
//		buf.append(CommonConstants.NEWLINE);
//		buf.append("");

		
		return buf.toString();
	}

	private String generateSQLScript_DailyStats(Date startingDate, Date endDate) {
	
		StringBuffer buf = new StringBuffer();
		
		
		
		String startDateStr = sdf.format(startingDate);
		String endDateStr = sdf.format(endDate);
		

		buf.append("select ");
		buf.append(LINE_IDENTIFIER_CONCAT);
		buf.append("status||',' as status");
		buf.append(CommonConstants.NEWLINE);
		buf.append(",count(*)||',' as count");
		buf.append(CommonConstants.NEWLINE);
		buf.append(", max(date_started) as created");
		buf.append(CommonConstants.NEWLINE);
		buf.append("from address_change_sessions");
		buf.append(CommonConstants.NEWLINE);
		buf.append("where id > 50");
		buf.append(CommonConstants.NEWLINE);
		buf.append("and date_started between TO_DATE('" + startDateStr + "','DD/MM/YYYY')");
		buf.append(CommonConstants.NEWLINE);
		buf.append("and TO_DATE('" + endDateStr + "','DD/MM/YYYY')");
		buf.append(CommonConstants.NEWLINE);
		buf.append("group by status;");
		buf.append(CommonConstants.NEWLINE);
		buf.append("");
		buf.append("");
//		buf.append("");
//		buf.append("select count(*) as Todays_Total");
//		buf.append(CommonConstants.NEWLINE);
//		buf.append("from address_change_sessions");
//		buf.append(CommonConstants.NEWLINE);
//		buf.append("where id > 50");
//		buf.append(CommonConstants.NEWLINE);
//		buf.append("and date_started between TO_DATE('" + startDateStr + "','DD/MM/YYYY')");
//		buf.append(CommonConstants.NEWLINE);
//		buf.append("and TO_DATE('" + endDateStr + "','DD/MM/YYYY');");
//		buf.append(CommonConstants.NEWLINE);
//		buf.append("");

		
		return buf.toString();
	}
	
	
	private class StatsTotalsDetail {
		
		String status = null;
		int count = 0;
		String date = null;
		
		public String getMapKey(String pDate, String pStatus){
			return pDate+"+"+pStatus;
		}
		
		public String getMapKey(){
			return getMapKey(this.date, this.status);
		}
		
	}
	
	private class StatsTotalsResult {
		StatsTotalsDetail[] statusCounts = new StatsTotalsDetail[NO_OF_STATUS_CODES];
		String date = null;
		
		public void add(StatsTotalsDetail thisDetail) {
			String status = thisDetail.status;
			if (PROCESSED.equals(status)){
				statusCounts[INDEX_PROCESSED] = thisDetail;
			} else if (CREATED.equals(status)){
				statusCounts[INDEX_CREATED] = thisDetail;
			} else if (CONFIRMED.equals(status)){
				statusCounts[INDEX_CONFIRMED] = thisDetail;
			} else if (EXECUTING.equals(status)){
				statusCounts[INDEX_EXECUTING] = thisDetail;
			} else if (SCHEDULED.equals(status)){
				statusCounts[INDEX_SCHEDULED] = thisDetail;
			} else if (CANCELLED.equals(status)){
				statusCounts[INDEX_CANCELLED] = thisDetail;
			} else if (FAILED.equals(status)){
				statusCounts[INDEX_FAILED] = thisDetail;
			} else if (EXCEPTION.equals(status)){
				statusCounts[INDEX_EXCEPTION] = thisDetail;
			} else if (TMS.equals(status)){
				statusCounts[INDEX_TMS] = thisDetail;
			} else {
				System.err.println("Unable to allocate the status count detail");
			}
		}
		
		
		
		public int getCount(int statusIndex) {

			int count = 0;
			
			if (statusIndex < NO_OF_STATUS_CODES){
				count = statusCounts[statusIndex].count;
			} else if (statusIndex == INDEX_SPECIAL_ERROR_TOTAL){
				
					count = statusCounts[INDEX_CREATED].count +
							statusCounts[INDEX_CONFIRMED].count +
							statusCounts[INDEX_EXECUTING].count +
							statusCounts[INDEX_TMS].count +
							statusCounts[INDEX_FAILED].count;
				
			} else {
				System.err.println("Unable to identify the status count total");
			}
			
			return count;
		}		
		
	}
}
