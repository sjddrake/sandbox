package uk.co.neo9.sandbox.coautils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import uk.co.neo9.apps.accounts.AccountsSpreadsheetReader.AccountDetailsDTO;
import uk.co.neo9.test.Neo9TestingConstants;
import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.UtilitiesTextHelper;
import uk.co.neo9.utilities.file.FileServer;


public class COALogReader implements COALogReaderProcessStepConstants, COALogReaderSQLScriptConstants, DailyStatsStatusConstants{
	
	private final static String MODE_LOGS = "logs";
	private final static String MODE_STATUS = "status";

	
	final int goLiveIDStartPoint = 50;
	final int goLiveIDEndPoint = 1999999999;
	
	private static Date lowerBoundaryDate = null;
	private static Date upperBoundaryDate = null;
	
	/**
	 * 
	 * STILL TO DO:
	 * 
	 * 	- extract the reference number
	 *  - use refrerence number to pre-filter out the test sessions
	 *  - store the references against the STEP FAILURES
	 *  - generate an SQL script from the stored refernces
	 * 
	 * 
	 */
	public static void main(String[] args) {
		
		String mode = MODE_LOGS;
		String inputFileDetails = Neo9TestingConstants.ROOT_TEST_FOLDER + "coaUtils/logsToRead.txt";
		String upperBoundaryDateStr = "2050-01-01";
		String lowerBoundaryDateStr = "2007-01-01";
		
		if (args != null ){

			switch (args.length) {
				case 4: 
					upperBoundaryDateStr = args[3];
				case 3: 
					lowerBoundaryDateStr = args[2];
				case 2: 
					inputFileDetails = args[1];
				case 1: 
					mode = args[0];
					break;
				default: break;
			}

		}

		COALogReader logReader = new COALogReader();
		if (MODE_LOGS.equals(mode)){
			
			Calendar cal = Calendar.getInstance();
			COAUtilities.setDateFromYYYYMMDD(cal,upperBoundaryDateStr);
			COAUtilities.setTimeFromHHMMSS(cal,"00:00:00");
			upperBoundaryDate = cal.getTime();
			
			COAUtilities.setDateFromYYYYMMDD(cal,lowerBoundaryDateStr);
			COAUtilities.setTimeFromHHMMSS(cal,"00:00:00");
			lowerBoundaryDate = cal.getTime();	
			
			logReader.goLogReader(inputFileDetails);
			
		} else if (MODE_STATUS.equals(mode)){
			
			logReader.goStatusReader(inputFileDetails);
		
		} else {
			System.out.println("use: 'mode' 'input file' 'lower date range' 'upper date range'");
			System.out.println("Where mode can be: 'logs' / 'status' / ?");
			System.out.println("");
			System.out.println("e.g.> logs D:/Test/coaUtils/logsToRead.txt 2007-09-02 2007-09-04");
		}
	}
	
	
	public void goLogReader(String listFileDetails){
		
		COALogReaderStats stats = new COALogReaderStats();
		
		// get the log files details
		Vector logFiles = null;
		try {
			logFiles = FileServer.readTextFile(listFileDetails);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		// process the log files
		for (Iterator iter = logFiles.iterator(); iter.hasNext();) {
			String logFileDetails = (String) iter.next();
			processLogFile(logFileDetails, stats);
			
		}
		
		outputStats(stats);
		
		listSessionDetailsByStep(stats);
		
		generateSQLByStep(stats);
		
		return;
	}
	
	public void goStatusReader(String listFileDetails){
		
		COALogReaderStats stats = new COALogReaderStats();
		
		// get the log files details
		Vector logFiles = null;
		try {
			logFiles = FileServer.readTextFile(listFileDetails);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		// process the log files
		for (Iterator iter = logFiles.iterator(); iter.hasNext();) {
			String logFileDetails = (String) iter.next();
			processStatusFile(logFileDetails, stats);
			
		}


		
		return;
	}


	private void listSessionDetailsByStep(COALogReaderStats stats) {

		String output = stats.listSessionDetailsByStep();
		System.out.println(output);
		
	}

	
	private void generateSQLByStep(COALogReaderStats stats) {

		String output = stats.generateSQLByStep();
		System.out.println(output);
		
	}	

	private void outputStats(COALogReaderStats stats) {
		
		System.out.println("number of failed steps: "+stats.getNoOfFailedSteps());
		
		String output = stats.outputFailedStepDetails();
		System.out.println(output);
		
	}

	
	private void processStatusFile(String logFileDetails, COALogReaderStats stats) {

		// read in the logfiles lines
		Vector lines = null;
		try {
			lines = FileServer.readTextFile(logFileDetails);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		ArrayList results = new ArrayList();
		for (Iterator iter = lines.iterator(); iter.hasNext();) {
			String line = (String) iter.next();
			COALogStatusDetails details = processStatusLine(line);
			if (details != null){
				results.add(details);
			}
			// System.out.println(line);
		}
		
		// output the status details
		String output = outputStatus(results);
		System.out.println(output);
		
		return;
		
	}

	private String outputStatus(ArrayList results) {
		StringBuffer text = new StringBuffer();

		int totalCount = 0;
		for (Iterator iter = results.iterator(); iter.hasNext();) {
			COALogStatusDetails details = (COALogStatusDetails) iter.next();
			totalCount = totalCount+details.count;
		}		
		text.append("Total live sessions to date - ");
		text.append(totalCount);
		text.append(CommonConstants.NEWLINE);
		text.append(CommonConstants.NEWLINE);
		
		
		COALogStatusDetails schDetails = getDetailsForStatusCode(results, SCHEDULED);
		COALogStatusDetails proDetails = getDetailsForStatusCode(results, PROCESSED);
		COALogStatusDetails canDetails = getDetailsForStatusCode(results, CANCELLED);
		COALogStatusDetails cnfDetails = getDetailsForStatusCode(results, CONFIRMED);
		COALogStatusDetails fldDetails = getDetailsForStatusCode(results, FAILED);
		COALogStatusDetails xptDetails = getDetailsForStatusCode(results, EXCEPTION);
		COALogStatusDetails tmsDetails = getDetailsForStatusCode(results, TMS);

		
		text.append("Total successful STPd - ");
		text.append(proDetails.count);
		text.append(CommonConstants.NEWLINE);	
		
		text.append("Outstanding pendings - ");
		text.append(schDetails.count);
		text.append(CommonConstants.NEWLINE);		

		
		text.append("Total Handoffs - ");
		text.append(fldDetails.count+xptDetails.count+tmsDetails.count);
		text.append(" (");
		text.append(fldDetails.count);
		text.append(" of which are STP errors)");
		text.append(CommonConstants.NEWLINE);		
		
		
		text.append("Total unhandled errors - ");
		text.append(canDetails.count+cnfDetails.count);
		text.append(CommonConstants.NEWLINE);			
		
		
		text.append(CommonConstants.NEWLINE);
		text.append("Percentage of errors to successful sessions is: ");
		int totalErrors = canDetails.count+cnfDetails.count+fldDetails.count+tmsDetails.count;
		long ratio = totalErrors/totalCount;
		text.append("< use this figure to calculate it: "+totalErrors+" >");
		text.append(CommonConstants.NEWLINE);		
		
		return text.toString();
	}


	private COALogStatusDetails getDetailsForStatusCode(ArrayList results, String code) {
		COALogStatusDetails theDetails = null;
		for (Iterator iter = results.iterator(); iter.hasNext();) {
			COALogStatusDetails details = (COALogStatusDetails) iter.next();
			if (details.statusCode.equals(code)){
				theDetails = details;
			}
		}
		
		return theDetails;
	}


	private COALogStatusDetails processStatusLine(String line) {

		// first determine if this line contains data
		COALogStatusDetails details = null;
		if (testForValidStatusLine(line)){
			
			// strip out the pertinent data
			details = new COALogStatusDetails();
			int firstOffset = line.indexOf(',');
			details.statusCode = line.substring(0,firstOffset);
			firstOffset = firstOffset+2; // to get passed the first comma and the next space
			int secondOffset = line.indexOf(',',firstOffset);
			String countStr = line.substring(firstOffset,secondOffset);
			details.count = Integer.valueOf(countStr).intValue();
		}

		return details;
	}


	private boolean testForValidStatusLine(String line) {
		boolean pass = false;
		if (line.indexOf(',') == 3) {
			pass = true;
		}
		return pass;
	}


	private void processLogFile(String logFileDetails, COALogReaderStats stats) {

		// read in the logfiles lines
		Vector lines = null;
		try {
			lines = FileServer.readTextFile(logFileDetails);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		for (Iterator iter = lines.iterator(); iter.hasNext();) {
			String line = (String) iter.next();
			processLogFileLine(line, stats);
		}
		
	}


	private void processLogFileLine(String line, COALogReaderStats stats) {
		
//		System.out.println(line);
	
		if (filterLogFileLine(line)){
			// System.out.println(line);
		
			
			// get the step name
			int offset = line.indexOf("STEP: ")+6;
			String stepName = line.substring(offset);
			
			// get the reference number
			offset = line.indexOf("REF: ")+5;
			StringBuffer reference = new StringBuffer();
			boolean notFinished = true;
			while (notFinished) {
				char c = line.charAt(offset);
				if (Character.isDigit(c)){
					reference.append(c);
				} else {
					notFinished = false;
				}
				offset++;
			}
			
			// get the date
			Date date = null;
			date = COAUtilities.extractLogDate(line);
			
			// only interested in sessions that were created after go-live
			Integer referenceID = Integer.valueOf(reference.toString());
			// if (referenceID.intValue() > logChangeIDCutOff) {
			if (filterLogLine(line,stepName,referenceID,date)) {

				System.out.println(line);
				
				// now pass the step to the stats to record the occurence of that step
				COALogReaderParams params = new COALogReaderParams();
				params.stepName = stepName;
				params.referenceID = referenceID.intValue();
				params.date = date;
				stats.recordStepFailure(params);
				
			}
			
		}
	
	}



	private boolean filterLogFileLine(String line) {

		boolean useThisLine = false;
		
		// System.out.println(line);
	
		if (line.indexOf("FAILED:    STEP:") != -1){
			if (line.indexOf("STP Process F") == -1){
				useThisLine = true;
			}
		}
	
		return useThisLine;
	}
	

	private boolean filterLogLine(String line, String stepName, Integer referenceID, Date date) {
		boolean include = false;
		
//		final int goLiveIDCutOff = 50;
//		final int logChangeIDCutOff = 5856;
		
		if (date.after(lowerBoundaryDate) && date.before(upperBoundaryDate)){
			include = true;
		}

		return include;
	}

	
	private class COALogReaderStats extends Object{
		
		private final static int NO_OF_STEPS = 16;
		
		private ArrayList[] stepStats = null;
		private final String[] stepNames = {STEP_NAME_UPDATE_SUBJECT_PARTY_DETAILS, 	
											STEP_NAME_UPDATE_EMAIL, 			
											STEP_NAME_MOVING_WITH_PARTY, 			
											STEP_NAME_MOVING_WITH_PARTIES, 		
											STEP_NAME_CORRESPONDENCE_DETAILS_CHANGE, 	
											STEP_NAME_CORRESPONDENCE_DETAILS_CHANGES, 	
											STEP_NAME_GENERATE_LETTERS, 			
											STEP_NAME_SET_CBS_INDICATORS, 		
											STEP_NAME_GENERATE_CONTACT_NOTE, 		
											STEP_NAME_ADDRES_PRTOECTION,
											STEP_NAME_HANDOFF_EXCEPTION,
											STEP_NAME_HANDOFF_PENDING_EXCEPTION,
											STEP_NAME_HANDOFF_STP_FAILURE,
											STEP_NAME_PENDING_WOKEN_UP,
											STEP_NAME_PENDING_VALIDATION,
											STEP_NAME_CHECK_FOR_CHAINS};
		
		
//		private int noOfFailedSteps = 0;

		
		
		public COALogReaderStats() {
			super();

			// set up the arrays
			int maxStep = NO_OF_STEPS+1; // 1 for the unrecognised ones!
			stepStats = new ArrayList[maxStep];
			for (int i = 0; i < maxStep; i++) {
				stepStats[i] = new ArrayList();				
			}
		}			
		
		
		public int getNoOfFailedSteps() {
			int stepCount = 0;
			for (int i = 0; i < stepStats.length; i++) {
				stepCount = stepCount + stepStats[i].size();
			}
			return stepCount;
		}

		public String outputFailedStepDetails() {
			StringBuffer text = new StringBuffer();
			
			for (int i = 0; i < stepNames.length; i++) {
				int stepCount = stepStats[i].size();
				if (stepCount > 0) {
					text.append("Step - "+stepNames[i]+ " - "+stepCount);
					text.append(CommonConstants.NEWLINE);
				}
			}
			
			// and the bucket
			text.append("Step - " + "other steps" + " - "+stepStats[NO_OF_STEPS].size());
			text.append(CommonConstants.NEWLINE);
			
			return text.toString();
		}


		
		public String generateSQLByStep() {
			StringBuffer text = new StringBuffer();
			
			int chunkSize = 20;
			
			for (int i = 0; i < stepNames.length; i++) {
				
				int stepCount = stepStats[i].size();//noOfSteps[i];
				if (stepCount > 0) {		
										
					ArrayList statDetails = stepStats[i];
					String stepName = stepNames[i];

					text.append(" ========= ");
					text.append(stepName);
					text.append(" ========= ");
					text.append(CommonConstants.NEWLINE);
					
					String sql = sqlScriptForStep(stepName);
					
					ArrayList formattedDetails = listSessionReferencesForStep(stepName, statDetails, chunkSize);
					for (Iterator iter = formattedDetails.iterator(); iter.hasNext();) {
						String formattedDetail = (String) iter.next();
						text.append(sql);
						text.append(formattedDetail);
						text.append(");");
						text.append(CommonConstants.NEWLINE);	
						text.append(CommonConstants.NEWLINE);
					}

				}
			}
			
			// and the bucket
			ArrayList statDetails = stepStats[NO_OF_STEPS];
			String stepName = "Other";
			String sql = sqlScriptForStep(stepName);

			text.append(" ========= ");
			text.append(stepName);
			text.append(" ========= ");
			text.append(CommonConstants.NEWLINE);
			
			ArrayList formattedDetails = listSessionReferencesForStep(stepName, statDetails, chunkSize);
			for (Iterator iter = formattedDetails.iterator(); iter.hasNext();) {
				String formattedDetail = (String) iter.next();
				text.append(sql);
				text.append(formattedDetail);
				text.append(");");
				text.append(CommonConstants.NEWLINE);	
				text.append(CommonConstants.NEWLINE);
			}

			text.append(CommonConstants.NEWLINE);	
			text.append(" ................. end of sql script ...............");
			text.append(CommonConstants.NEWLINE);
			
			return text.toString();
		}		
				
		
		
		private String sqlScriptForStep(String stepName) {
			String sql = "some SQL (";
			
			if (stepName.equals(COALogReaderProcessStepConstants.STEP_NAME_UPDATE_SUBJECT_PARTY_DETAILS)){
				return COALogReaderSQLScriptConstants.SQL_UPDATE_PARTY;
			}
			
			return sql;
		}


		public String listSessionDetailsByStep() {
			StringBuffer text = new StringBuffer();
			
			int chunkSize = 999999;
			
			for (int i = 0; i < stepNames.length; i++) {
				
				int stepCount = stepStats[i].size();
				if (stepCount > 0) {		
										
					ArrayList statDetails = stepStats[i];
					String stepName = stepNames[i];

					text.append(" ========= ");
					text.append(stepName);
					text.append(" ========= ");
					text.append(CommonConstants.NEWLINE);
					
					
					ArrayList formattedDetails = listSessionReferencesForStep(stepName, statDetails, chunkSize);
					for (Iterator iter = formattedDetails.iterator(); iter.hasNext();) {
						String formattedDetail = (String) iter.next();
						text.append(formattedDetail);
						text.append(CommonConstants.NEWLINE);	
					}

				}
			}
			
			// and the bucket
			ArrayList statDetails = stepStats[NO_OF_STEPS];
			String stepName = "Other";

			text.append(" ========= ");
			text.append(stepName);
			text.append(" ========= ");
			text.append(CommonConstants.NEWLINE);
			
			ArrayList formattedDetails = listSessionReferencesForStep(stepName, statDetails, chunkSize);
			for (Iterator iter = formattedDetails.iterator(); iter.hasNext();) {
				String formattedDetail = (String) iter.next();
				text.append(formattedDetail);
				text.append(CommonConstants.NEWLINE);	
			}
			
			return text.toString();
		}		
		

		private ArrayList listSessionReferencesForStep(String stepName, ArrayList statDetails, int chunkSize){
			
			// if the chunk is huge, it means we want a column of ids
			// 
			// if the chunk is manageable, we want a row
			boolean formatAsColumns = false;
			if (chunkSize > 100) {
				formatAsColumns = true;
			}
			
			// first order the stat details
			COALogReaderDetailsComparator comparator = new COALogReaderDetailsComparator();
			Collections.sort(statDetails,comparator);			
			ArrayList chunks = new ArrayList();

			Iterator iter = statDetails.iterator();
			boolean haveMoreChunks = true;
			while (haveMoreChunks){
			
				int count = 0;
				StringBuffer text = new StringBuffer();
				
				for (; (iter.hasNext() && count < chunkSize);) {
					COALogReaderDetails details = (COALogReaderDetails) iter.next();
					text.append(details.referenceID);
					text.append(",");
					if (formatAsColumns){
						text.append(CommonConstants.NEWLINE);	
					}					
					count++;
				}
				
				// strip off the final comma
				int textLength = text.length();
				if (textLength > 0) {
					int lastCommaOffset = textLength-1;
					while (lastCommaOffset > 0){
						char lastChar = text.charAt(lastCommaOffset);
						if (Character.isDigit(lastChar)) {
							// text.delete(lastCommaOffset,textLength);
							text.deleteCharAt(lastCommaOffset+1);
							lastCommaOffset = 0; // this will exit the loop
						} else {
							lastCommaOffset--;
						}
					}
				}
				
				chunks.add(text.toString());
				text = null;
				
				if (!iter.hasNext()){
					haveMoreChunks = false;
				}
			}
			
			return chunks;
		}
		
		
		public void recordStepFailure(COALogReaderParams params) {
			
			String stepName = params.stepName;
			int referenceID = params.referenceID;
			
			boolean foundAndSet = false;
			for (int i = 0; (i < stepNames.length && !foundAndSet); i++) {
				String thisName = stepNames[i];
				if (stepName.indexOf(thisName) > -1){
					// don't record all
					if (STEP_INDEX_CORRESPONDENCE_DETAILS_CHANGES != i){
						//noOfSteps[i]=noOfSteps[i]+1;
						COALogReaderDetails stepStat = new COALogReaderDetails();
						stepStat.referenceID = referenceID;
						stepStats[i].add(stepStat);
						// incrementNoOfFailedSteps();
					}
					foundAndSet = true;
					break;
				}
			}
			
			if (!foundAndSet){
				COALogReaderDetails stepStat = new COALogReaderDetails();
				stepStat.referenceID = referenceID;
				stepStats[stepNames.length].add(stepStat);
			}
			
		}

//		public void setNoOfFailedSteps(int noOfFailedSteps) {
//			this.noOfFailedSteps = noOfFailedSteps;
//		}
//
//		public void incrementNoOfFailedSteps() {
//			setNoOfFailedSteps(this.noOfFailedSteps+1);
//		}


	}
	
	private class COALogReaderParams {
		String stepName = null;
		int referenceID = 0;
		String partyID = null;
		Date date = null;
	}
	
	private class COALogReaderDetails {
		String partyID = null;
		int referenceID = 0;
		String dateTimeStamp = null;
	}


	private class COALogStatusDetails {
		int count = 0;
		String statusCode = null;
	}	
	
	public class COALogReaderDetailsComparator implements Comparator{

		public int compare(Object o1, Object o2) {
			int result = 0;
			COALogReaderDetails details1 = (COALogReaderDetails)o1;
			COALogReaderDetails details2 = (COALogReaderDetails)o2;
			
			result = compare(details1,details2,"reference");
			
			return result;
		}

		
		public int compare(COALogReaderDetails details1, COALogReaderDetails details2, String fieldName) {
			int result = 0;

			if (fieldName.equals("partyID")){
				result = details1.partyID.compareTo(details2.partyID);
			} else if (fieldName.equals("reference")){
				Integer ref1 = new Integer(details1.referenceID);
				Integer ref2 = new Integer(details2.referenceID);
				result = ref1.compareTo(ref2);
			} else if (fieldName.equals("dateTimeStamp")){
				result = details1.dateTimeStamp.compareTo(details2.dateTimeStamp);
			} 
			
			return result;
		}		
		
	}
}
