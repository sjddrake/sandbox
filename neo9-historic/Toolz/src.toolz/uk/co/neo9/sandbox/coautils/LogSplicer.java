/*
 * Created on 04-Oct-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.sandbox.coautils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import uk.co.neo9.test.Neo9TestingConstants;
import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.file.FileServer;

/**
 * @author Simon
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LogSplicer {

	ArrayList logFileReaders = new ArrayList();
	LogBuffer buffer = new LogBuffer();
	private Date lowerBoundaryDate = null;
	private Date upperBoundaryDate = null;


	public static void main(String[] args) {
		

		
		
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
	}
	
	
	public void go(String listFileDetails){
		
		
		// get the log files details
		Vector logFileList = null;
		try {
			logFileList = FileServer.readTextFile(listFileDetails);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		// intiailise the log file readers
		int count = 0;
		for (Iterator iter = logFileList.iterator(); iter.hasNext();) {
			count++;
			String logFileDetails = (String) iter.next();
			LogFileReader reader = new LogFileReader(logFileDetails,"log#"+count);
			logFileReaders.add(reader);
		}
		
		// process the files
		//for (Iterator iter = logFileReaders.iterator(); iter.hasNext();) {
		boolean notDone = true;
		boolean foundANull = false;
		int noOfLogReaders = logFileReaders.size()-1;
		int index = 0;
		int nullCount = 0;
		int monitorCount = 0;
		while (notDone) {
			LogFileReader logFileServer = (LogFileReader)logFileReaders.get(index);
			LogLine logline = readLogLine(logFileServer);
			if (logline != null){
				addLogLineToBuffers(logline);
				nullCount = 0;
			} else {
				nullCount++;
			}
			if (index == noOfLogReaders ){
				index = 0;
			} else {
				index++;
			}
			
			
			if (monitorCount == 100){
				buffer.report();
				buffer.cleanBuckets();
				monitorCount = 0;
			} else {
				monitorCount++;
			}			
			
			if (nullCount == noOfLogReaders){
				notDone = false;
			}
		}
		
		
		// now it's processed, output it
		// setup a file writer
		FileWriter fw = null;
		try {
			fw  = new FileWriter("c:/temp/spliced.log");
			
			ArrayList logLines = buffer.getResidue(true);
			for (Iterator iter = logLines.iterator(); iter.hasNext();) {
				LogLine logLine = (LogLine) iter.next();
				fw.write(logLine.exportText());
				fw.write(CommonConstants.NEWLINE);
				fw.flush();
			}
			
			
//			// write the data
//			for (int i=0;i<buffer.buckets.size();i++) {
//		
//				Bucket bucket = buffer.buckets.get(i);
//				fw.write((String)(textToSave.elementAt(i)));
//				fw.write("\r\n");
//				fw.flush();
//			}
	
			fw.close();		
			
					
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		return;
	}
	
	/**
	 * @param logline
	 */
	private void addLogLineToBuffers(LogLine logline) {
		// ScoobyDo could get rid of this, perhaps???
		//System.out.println(logline.text.get(0));
		
		buffer.addLogLine(logline);
		
	}


	/**
	 * @param logFileServer
	 * @return
	 */
	private LogLine readLogLine(LogFileReader logFileServer) {
		LogLine logline = null;
		boolean notDone = true;
		while (notDone){
		
			String line = logFileServer.readFileByLine();
			if (line != null){
				
				Date date = COAUtilities.extractLogDate(line);
				if (date != null && includedDate(date)) { // ignore all NON-dated lines
					logline = new LogLine();
					logline.text.add(line);
					logline.timeStamp = date;
					notDone = false;
				}
				
				// ScoobyDo could cope with stack traces if we needed to 
				// here by adding to the 'text' array while subsequent
				// lines did NOT have dates BUT for the current use, the
				// logs dont wrap so who cares! ;-)
				
			} else {
				notDone = false;
			}
			
		}
		return logline;
	}


	/**
	 * @param date
	 * @return
	 */
	private boolean includedDate(Date date) {
		boolean include = false;

		if (date.after(lowerBoundaryDate) && date.before(upperBoundaryDate)){
			include = true;
		}

		return include;
	}


	public void setBoundaryDates(Date from, Date to){
		lowerBoundaryDate = from;
		upperBoundaryDate = to;
	}

	public void setBoundaryDates(Date from){
		setBoundaryDates(from, new Date());
	}


//
//	 -------------------------- LogFileReader -------------------------------
//

	public class LogFileReader {
		
		private final static String DELIMITER = " ";
		
		private FileServer reader = null;
		public String logTag = null;
		
		private LogFileReader(){}
		
		public LogFileReader(String fileDetails, String tag){
			reader = new FileServer();
			reader.initialiseForReadByLine(fileDetails);
			logTag = tag;
		}
		
		public String readFileByLine(){
			String line = reader.readFileByLine();
			if (line != null){
				line = logTag+DELIMITER+line;
			}
			
			return line;
		}
		
	}
		
	
}
