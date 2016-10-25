/*
 * Created on 03-Oct-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.sandbox.coautils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.file.FileServer;

/**
 * @author ct039314
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KYCLogReader implements KYCLoggerConstants, KYCLoggerHeaderConstants{

	private int logFileLineCount = 0;
	private final static int BUFFER_SIZE = 500; //30;
	private final static int CHUNK_SIZE = 300;
	

	FileServer logFileServer = new FileServer();
	Collection subjectPartyIDs = new ArrayList();
	ArrayList lineBuffer = new ArrayList();
	boolean logFileReadComplete = false;
	
	/* DESIGN
	 * 
	 * - read a session worth of lines in (hope it's contiguous!)
	 * - keep the lines in an array list
	 * - give the list to a container
	 * - container identifies the session line and gets the session ref
	 * - container stores the lines per party and tag
	 * - cotnainer identifies if the KYC was captured
	 * - container identifies if the KYC update was successful
	 * 
	 */
	
	

	public static void main(String[] args) {
		
		
//		final String logFileDetails = "D:/Test/coaUtils/KYC/KYC - clone 1 - partial.htm";
//		final String logFileDetails = "C:/Simonz/_COA/_Implementation/Logs/Clone 2/KYC - clone 2 - partial.htm";
//		final String logFileDetails = "C:/Simonz/_COA/_Implementation/Logs/Clone 3/KYC - clone 3 - partial.htm";
//		final String logFileDetails = "C:/Simonz/_COA/_Implementation/Logs/Clone 4/KYC - clone 4 - partial.htm";
		
		final String logFileDetails = "C:/Temp/spliced.log";
		
		KYCLogReader reader = new KYCLogReader();
		reader.go(logFileDetails);
		

	}




	public void getReady(String logFileDetails){
	
		Hashtable memory = new Hashtable();
	
		// need to initialise the file reader
		logFileServer.initialiseForReadByLine(logFileDetails);
		String line = logFileServer.readFileByLine();
		while (line != null){
	
			if (line.indexOf(LLI_KYCSessionV1) != -1){
				Integer partyID = extractPartyIdFromLine(line);
				if (partyID != null) {
					if (!memory.contains(partyID)) {
						subjectPartyIDs.add(partyID);
						memory.put(partyID,partyID);
						// System.out.println(partyID);
					}
				}
			}
			
			line = logFileServer.readFileByLine();
		}
	
	
	}
	
	

//	public void go(){
//
//		// need to initialise the file reader
//		getReady();
//		logFileServer.initialiseForReadByLine("C:/Simonz/_COA/_Implementation/Logs/Clone 1/KYC - clone 1 - partial.htm");
//
//
//		boolean notDone = true;
//		String nextLine = getNextLine();
//		if (nextLine == null){
//			notDone = false;
//		}
//		
//		/*
//		 * 	LOAD UP THE LOG LINES
//		 * 
//		 */
//
//		ArrayList sessionContainers = new ArrayList();
//		ArrayList sessionLines = null;
//		while (notDone) {
//			
//			//System.out.println(nextLine);
//			
//			if (isFirstLineOfSession(nextLine)){
//				if (sessionLines != null) {
//					KYCLogContainer container = new KYCLogContainer();
//					container.addLogLines(sessionLines);
//					sessionContainers.add(container);
//				}
//				sessionLines = new ArrayList();
//			}
//			sessionLines.add(nextLine);
//			
//			
//			
//			
//			
//			nextLine = getNextLine();
//			if (nextLine == null){
//				notDone = false;
//			}			
//		}
//		
//		/*
//		 * 	OUTPUT THE LOG LINES
//		 * 
//		 */		
//		
//		for (Iterator iter = sessionContainers.iterator(); iter.hasNext();) {
//			KYCLogContainer session = (KYCLogContainer) iter.next();
//			
////			String subjectLog = session.exportSubjectLog();
////			System.out.println(subjectLog);
//			
//			if (session.didKYCUpdateFail){
//				System.out.println("session failed on F073: "+session.reference);
//			}
//			
//			
//		}
//		
//		
//		return;
//
//	}



	public void go(String logFileDetails){

		// need to initialise the file reader
		getReady(logFileDetails);
		initialiseBufferedRead(logFileDetails);

		
		/*
		 * 	LOAD UP THE LOG LINES
		 * 
		 */
		 
		 Integer debugPartyID = new Integer(1434121042);

		ArrayList sessionContainers = new ArrayList();
		
		String nextLine = null;
		for (Iterator iter = subjectPartyIDs.iterator(); iter.hasNext();) {

			Integer partyID = (Integer) iter.next();
			
			/* debug */
			if (debugPartyID.equals(partyID)){
				boolean b = true;
			}
			
			nextLine = getNextLine(partyID);
			ArrayList sessionLines = new ArrayList();
			//System.out.println(nextLine);
			
//			/* debug */
//			if (nextLine == null){
//				System.out.println("failed to find this party id in the buffer, "+partyID+ ", buffer count = "+lineBuffer.size());
//			} else {
//				System.out.println("FOUND in the buffer, "+partyID+ ", buffer count = "+lineBuffer.size());
//			}
//			/* debug */
			
			while (nextLine != null) {

				sessionLines.add(nextLine);			
				nextLine = getNextLine(partyID);
				if (nextLine == null){
					KYCLogContainer container = new KYCLogContainer();
					container.addLogLines(sessionLines);
					sessionContainers.add(container);			
				}
			}
			
			
					
		}
		
		/*
		 * 	OUTPUT THE LOG LINES
		 * 
		 */		
		FileWriter fw = null;
		try {
			fw  = new FileWriter("c:/temp/KYCFailLog.csv");
					 
		 
		 
		
			for (Iterator iter = sessionContainers.iterator(); iter.hasNext();) {
				KYCLogContainer session = (KYCLogContainer) iter.next();
				
	//			String subjectLog = session.exportSubjectLog();
	//			System.out.println(subjectLog);
				
				if (session.didKYCUpdateFail){
				//	System.out.println("session failed on F073, "+session.reference);
					String export = session.exportSubjectLog(true); // reverse the output until I fix the log splicer bug
					System.out.println(export);
					fw.write(export);
					fw.flush();
				}
				
			}
	
	
	//		for (Iterator iter = sessionContainers.iterator(); iter.hasNext();) {
	//			KYCLogContainer session = (KYCLogContainer) iter.next();
	//			
	//			System.out.println(session.subjectPartyID+": no of logged lines = "+session.subjectPartylogLines.size());
	//			
	//		}		
		

			fw.close();		
				
							
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
				
		return;

	}


	
	/**
	 * 
	 */
	private void initialiseBufferedRead(String logFileDetails) {
		logFileServer.initialiseForReadByLine(logFileDetails);
		logFileLineCount = 0;
		String line = logFileServer.readFileByLine(); // NOTE: this gets rid of the initial HTML line - dodgy!!! :-)
		logFileReadComplete = false;
		for (int i = 0; (i < BUFFER_SIZE && logFileReadComplete==false); i++) {
			line = logFileServer.readFileByLine();
			if (line != null) {
				lineBuffer.add(line);
				logFileLineCount++;
			} else {
				logFileReadComplete = true;
			}
		}
		
	}




	/**
	 * @param nextLine
	 * @return
	 */
	private boolean isFirstLineOfSession(String nextLine) {
		boolean retVal = false;
		
		if (nextLine.indexOf(MESSAGE_KYCLogStartMarker) != -1){
			retVal = true;
		}
		
		return retVal;
	}


	/**
	 * this method buffers the reading of the file so that 
	 * a session can be built up
	 */
	protected String getNextLine(Integer partyID) {
		
		// look for the id in the file line
		String line = null;
		int bufferSize = lineBuffer.size();
		for (int i = 0; (i < bufferSize && line == null); i++) {
			String bufferedLine = (String) lineBuffer.get(i);
			Integer thisLinesPartyID = extractPartyIdFromLine(bufferedLine);
			if (partyID.equals(thisLinesPartyID)){
				line = bufferedLine;
				lineBuffer.remove(i);
			}
		}
		
		if (logFileReadComplete == false) {
			if (line != null) { // found a match so replace the line
				String newLine = logFileServer.readFileByLine();
				if (newLine != null) {
					lineBuffer.add(newLine);
					logFileLineCount++;
				} else {
					logFileReadComplete = true;
				}
			} else {
				
				// read more lines in an attempt to find the match
				String newLine = null;
				for (int i = 0; (i < CHUNK_SIZE && line == null && !logFileReadComplete); i++) {
					newLine = logFileServer.readFileByLine();
					if (newLine != null) {
						logFileLineCount++;
	
						Integer thisLinesPartyID = extractPartyIdFromLine(newLine);
						if (partyID.equals(thisLinesPartyID)){
							line = newLine;
						} else {
							lineBuffer.add(newLine);
						}
	
					} else {
						logFileReadComplete = true;
					}						
				}
			
				
			}
		}
		return line;
	}


	public void test(){
		String testLine = "KYC Thread[ngine.Transports : 1] 2007-10-03 12:07:26,014 F075RspAudV1 - PARTY: 175802674 -> ,03102007 120709,02102007 143053,03102007 120709,03102007 120709,03102007 120709,02102007 150329,03102007 120709,02102007 150329,";
//		int offset = testLine.indexOf("PARTY: ");
//		String partyID = getNumberFromText(testLine,offset+7);
//		System.out.println(partyID);

		Integer partyID = extractPartyIdFromLine(testLine);
		System.out.println(partyID);

	}
	
	
	public static Integer extractPartyIdFromLine(String line){
		
		boolean decodeFailed = false;
		Integer partyID = null;
		int offset = line.indexOf(MESSAGE_PARTY);
		if (offset > -1 && offset+MESSAGE_PARTY.length()+1 < line.length()){
			String partyIDStr = getNumberFromText(line,offset+MESSAGE_PARTY.length());
			try {
				partyID = new Integer(partyIDStr);
			} catch (Exception e) {
				decodeFailed = true;
			}
		}
		
		/* debug */
		Integer debugID = new Integer(16665378);
		if (debugID.equals(partyID)){
			boolean n= true;
		}
		/* debug */
		
//		if (partyID == null){
//			if (decodeFailed) {
//				System.err.println("Failed to DECODE the party id in this line.");
//			} else {
//				System.err.println("Failed to extract the party id from this line.");
//			}
//		}
	
		return partyID;
	}
	


	public static Integer extractSessionRefFromLine(String line){
		
		boolean decodeFailed = false;
		Integer ref = null;
		int offset = line.indexOf(MESSAGE_REF);
		if (offset > -1 && offset+MESSAGE_REF.length()+1 < line.length()){
			String refStr = getNumberFromText(line,offset+MESSAGE_REF.length());
			try {
				ref = new Integer(refStr);
			} catch (Exception e) {
				decodeFailed = true;
			}
		}
		
		if (ref == null){
			if (decodeFailed) {
				System.err.println("Failed to DECODE the reference in this line.");
			} else {
				System.err.println("Failed to extract the reference from this line.");
			}
		}
	
		return ref;
	}


	private String extractTagFromLine(String line){
		String tag = null;
			
		// find the PARTY text and work back
		boolean decodeFailed = false;
		String testText = MESSAGE_HYPHEN+MESSAGE_PARTY;
		int offset = line.indexOf(testText);
		if (offset > -1 && offset-testText.length()+1 > 0){
				
			tag = getWordFromTextBackwards(line,offset-1);
		}

		return tag;
	}
	
	private static String getNumberFromText(String text, int offset){
		StringBuffer number = new StringBuffer();
		boolean notFinished = true;
		while (notFinished && offset < text.length()) {
			char c = text.charAt(offset);
			if (Character.isDigit(c)){
				number.append(c);
			} else {
				notFinished = false;
			}
			offset++;
		}		
		return number.toString();
	}


	private static String getWordFromTextBackwards(String text, int offset){
		StringBuffer number = new StringBuffer();
		boolean notFinished = true;
		while (notFinished && offset > -1) {
			char c = text.charAt(offset);
			if (Character.isLetterOrDigit(c)){
				number.insert(0,c);
			} else {
				notFinished = false;
			}
			offset--;
		}		
		return number.toString();
	}



	
	
// =========================== 	KYCLogContainer ===========================
	
	
	public class KYCLogContainer {
		
		private final static char COMMA = ',';
		private final static char DOT = '.';
		private final static char HYPHEN = '-';
		
		Integer reference = null;
		Integer subjectPartyID = null;
		// Hashtable logLines = new Hashtable();
		ArrayList subjectPartylogLines = new ArrayList();
		boolean wasKYCCaptured = false;
		boolean didKYCUpdateFail = false;
		boolean wasSuccessfulDecode = false;

		/**
		 * @param sessionLines
		 */
		public void addLogLines(ArrayList sessionLines) {
			
			// process the lines
			ArrayList kycLogLines = new ArrayList();
			for (Iterator iter = sessionLines.iterator(); iter.hasNext();) {
				String line = (String) iter.next();
				String tag = extractTagFromLine(line);
				
				// ignore the start marker
				if (!LLI_KYCLogStartMarker.equals(tag)){
					Integer partyID = extractPartyIdFromLine(line);
					// logLines.put(tag+partyID,line);
					KYCLogLine logLine = new KYCLogLine();
					logLine.partyID = partyID;
					logLine.tag = tag;
					logLine.text = preFormatLogLine(line);
					kycLogLines.add(logLine);
					
					// if this line identifies the session, remember the details
					if (LLI_KYCSessionV1.equals(tag)){
						subjectPartyID = partyID;
						reference = extractSessionRefFromLine(line);
					}
				}
			}
			
			// now normalise the loglines by party
			if (subjectPartyID != null){

				for (Iterator iter = kycLogLines.iterator(); iter.hasNext();) {
					KYCLogLine logLine = (KYCLogLine) iter.next();
					if (subjectPartyID.equals(logLine.partyID)){
						subjectPartylogLines.add(logLine);
					} else {
						//ScoobyDo not sure about doing anything with these
					}
				}
				wasSuccessfulDecode = true;
			} else {
				subjectPartylogLines = 	kycLogLines;
			}
			 
			// now set a few MI details
			wasKYCCaptured = determineKYCCaptured();
			didKYCUpdateFail = determineKYCUpdateFailed();
		}



		private String preFormatLogLine(String line) {
			StringBuffer buf = new StringBuffer(line);
			
			// take out the first comma cos it messes with the CSV aspect of the line
			boolean notFoundYet = true;
			for (int i = 0; (i < line.length() && notFoundYet); i++) {
				char thisChar = buf.charAt(i);
				if (thisChar == COMMA) {
					buf.setCharAt(i,DOT);
					notFoundYet = false;
				}
			}
			
			return buf.toString();
		}



		private boolean determineKYCUpdateFailed() {
			boolean retVal = false;
			
			// get the correct log line
//			String key = LLI_F073SuccessV1+subjectPartyID;
//			String requiredLogLine = (String)logLines.get(key);
			
			String requiredLogLineText = null;
			for (Iterator iter = subjectPartylogLines.iterator(); iter.hasNext();) {
				KYCLogLine logLine = (KYCLogLine) iter.next();
				if (LLI_F073SuccessV1.equals(logLine.tag)){
					requiredLogLineText = logLine.text;	
				}
			}
			
			
			if (requiredLogLineText!=null){
				if (requiredLogLineText.indexOf(MESSAGE_F073_FAIL) != -1){
					retVal = true;
				}
			} else {
				// System.err.println("Missing LLI_F073SuccessV1 tag line.");
			}
			
			return retVal;
			
		}


		private boolean determineKYCCaptured() {
			boolean retVal = false;
			
			// get the correct log line
//			String key = LLI_KYCRequiredV1+subjectPartyID;
//			String requiredLogLine = (String)logLines.get(key);

			String requiredLogLineText = null;
			for (Iterator iter = subjectPartylogLines.iterator(); iter.hasNext();) {
				KYCLogLine logLine = (KYCLogLine) iter.next();
				if (LLI_KYCRequiredV1.equals(logLine.tag)){
					requiredLogLineText = logLine.text;	
				}
			}
			
			if (requiredLogLineText!=null){
				if (requiredLogLineText.indexOf(MESSAGE_KYC_REQUIRED) != -1){
					retVal = true;
				}
			} else {
				System.err.println("Missing KYCRequiredV1 tag line.");
			}
			
			return retVal;
		}
		
		
		
		public String exportSubjectLog(boolean reverse){

			ArrayList bufList = new ArrayList();
			for (Iterator iter = subjectPartylogLines.iterator(); iter.hasNext();) {
				KYCLogLine logLine = (KYCLogLine) iter.next();
				
				if (includeLineInExport(logLine)){		
					bufList.add(lookupCSVHeaders(logLine));
					bufList.add(CommonConstants.NEWLINE);
					bufList.add(logLine.text);
					bufList.add(CommonConstants.NEWLINE);
				}
				
			}
			
			StringBuffer buf = new StringBuffer();
			
			buf.append(getLoglineByTag(LLI_KYCSessionV1));
			buf.append(CommonConstants.NEWLINE);
			
			if (reverse) {
				for (Iterator iter = bufList.iterator(); iter.hasNext();) {
						buf.insert(0,iter.next());
				}
			} else {
				for (Iterator iter = bufList.iterator(); iter.hasNext();) {
						buf.append(iter.next());
				}
			}

			
			return buf.toString();
		}


		/**
		 * @param LLI_KYCSessionV1
		 * @return
		 */
		private String getLoglineByTag(String tag) {
			String retVal = null;

			for (Iterator iter = subjectPartylogLines.iterator(); (iter.hasNext() && retVal == null);) {
				KYCLogLine logLine = (KYCLogLine) iter.next();
				
				if (tag.equals(logLine.tag)){
					retVal = logLine.text;
				}
				
			}

			return retVal;
		}



		/**
		 * @param logLine
		 * @return
		 */
		private String lookupCSVHeaders(KYCLogLine logLine) {
			String retVal = null;
			
			if (LLI_F061RespAuditV1.equals(logLine.tag)) {
				retVal = LLH_F061RespAuditV1;
			} else if (LLI_F061ResponseV1.equals(logLine.tag)) {
				retVal = LLH_F061ResponseV1;
			} else if (LLI_F062ReqAuditV1.equals(logLine.tag)) {
				retVal = LLH_F062ReqAuditV1;
			} else if (LLI_F062RequestV1.equals(logLine.tag)) {
				retVal = LLH_F062RequestV1;
			} else if (LLI_F062RespAuditV1.equals(logLine.tag)) {
				retVal = LLH_F062RespAuditV1;
			} else if (LLI_F062ResponseAttributes.equals(logLine.tag)) {
				retVal = LLH_F062ResponseAttributes;
			} else if (LLI_F073CallV1.equals(logLine.tag)) {
				retVal = LLH_F073CallV1;
			} else if (LLI_F073ReqAuditV1.equals(logLine.tag)) {
				retVal = LLH_F073ReqAuditV1;
			} else if (LLI_F073RequestAttributes.equals(logLine.tag)) {
				retVal = LLH_F073RequestAttributes;
			} else if (LLI_F073RequestV1.equals(logLine.tag)) {
				retVal = LLH_F073RequestV1;
			} else if (LLI_F073SuccessV1.equals(logLine.tag)) {
				retVal = LLH_F073SuccessV1;
			} else if (LLI_F075RespAuditV1.equals(logLine.tag)) {
				retVal = LLH_F075RespAuditV1;
			} else if (LLI_KYCChangesV1.equals(logLine.tag)) {
				retVal = LLH_KYCChangesV1;
			} else if (LLI_KYCLogStartMarker.equals(logLine.tag)) {
				retVal = LLH_KYCLogStartMarker;
			} else if (LLI_KYCRequiredV1.equals(logLine.tag)) {
				retVal = LLH_KYCRequiredV1;
			} else if (LLI_KYCSessionV1.equals(logLine.tag)) {
				retVal = LLH_KYCSessionV1;
			} else {
				retVal = "couldn't find header for tag: "+logLine.tag;
			}
			
			return retVal;
		}



		private boolean includeLineInExport(KYCLogLine logLine) {
			boolean dontInclude = false; 
			
			String tag = logLine.tag;
			if (LLI_F073CallV1.equals(tag)) {
				dontInclude = true;
			} else if (LLI_F073RequestV1.equals(tag)){
				dontInclude = true;
			} else if (LLI_F073SuccessV1.equals(tag)){
				dontInclude = true;
			} else if (LLI_KYCRequiredV1.equals(tag)){
				dontInclude = true;
			} else if (LLI_KYCSessionV1.equals(tag)){
				dontInclude = true;
			} else if (LLI_KYCLogStartMarker.equals(tag)){
				dontInclude = true;
			}
			
			return !dontInclude;
		}

	}

	public class KYCLogLine {
		
		String text = null;
		String tag = null;
		Integer partyID = null;
			
	}

}
