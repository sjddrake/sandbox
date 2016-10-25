package uk.co.neo9.sandbox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import uk.co.neo9.sandbox.cheats.SandboxCheats;
import uk.co.neo9.test.Neo9TestingConstants;
import uk.co.neo9.utilities.UtilitiesTextHelper;
import uk.co.neo9.utilities.file.FileServer;

public class TextLineDeDuplicator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TextLineDeDuplicator dedup = new TextLineDeDuplicator();
		dedup.go();

	}
	
	public void go() {
		
//		String filedetails = Neo9TestingConstants.ROOT_TEST_FOLDER
//		 + "/LTSB/endpoints/";
		
		String filedetails = "O:/Shared Documents/YF Changeset/si duplicates.txt";
		processTextFile(filedetails);
	}
	
	
	
	public void processTextFile(String filedetails) {
		
		// first read in the python file as lines of text
		List lines = null;
		try {
			lines = FileServer.readTextFile(filedetails);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// extrract the endpoint lines from the file
		List endpointLines = UtilitiesTextHelper.deduplicateLines(lines,true);
		SandboxCheats.outputSimpleLines(endpointLines);
		
		System.out.println("\n\n ======================== \n\n");
		

		// save the output
		String outputFileDetails = "C:/temp/dedupedLines.txt";
		try {
			FileServer.writeTextFile(outputFileDetails, endpointLines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	






/*
	private boolean checkLineForExtraction(String line) {
		
		// simple checkd first
		if (line != null) {
			line = line.trim();
		} else {
			return false;
		}
		
		if (line.length() == 0) {
			return false;
		}
		
		
		
		// 'extract' if the line starts with either:
		// urlName_
		// urlDestination_
		

		
		boolean extract = false;
		if (line.startsWith("#") == false) {
			
			if (line.startsWith("urlName_") == true) {
				extract = true;
			}
			
			else if (line.startsWith("urlDestination_") == true) {
				extract = true;
			}
			
		}
		
		
		return extract;
	}
	
	
	public class EndpointDetails {
		
		int urlIndex = 0;
		String endpointName = null;
		String endpointURL = null;
		String rawUrlName = null;
		String rawUrlDestination = null;
		
		
		public boolean extractDetails(String urlName, String urlDestination){
			
			// some simple validation
			if (urlName == null || urlDestination == null) {
				return false;
			}			
			
			if (urlName.trim().length() == 0 || urlDestination.trim().length() == 0) {
				return false;
			}	
			
			// now extract the details
			boolean success = true;
			
			// retain references to the raw file lines
			rawUrlName = urlName;
			rawUrlDestination = urlDestination;
			
			// get the 'number' of the endpoint
			String nameIndex = UtilitiesTextHelper.getNumberFromText(urlName);
			String destinationIndex = UtilitiesTextHelper.getNumberFromText(urlDestination);
			
			if (nameIndex.equalsIgnoreCase(destinationIndex) == false){
				return false;
			} else {
				urlIndex = new Integer(nameIndex).intValue();
			}
			
			 	 DEBUG 	 
			if (urlIndex == 100) {
				boolean x = true;
			}
			 	 DEBUG 	 
			
			// get the endpoint details
			endpointName = extractQuotedStringValue(urlName);
			endpointURL = extractQuotedStringValue(urlDestination);
			
			return success;
			
		}
		
		
		private String extractQuotedStringValue(String quotedString) {
			String value = null;
			
			List tokens = UtilitiesTextHelper.tokenizeString(quotedString,"\"");
			
			// expect to get back 2 tokens, the second is value we want
			int noOfTokens = tokens.size();
			if (noOfTokens >= 2) {
				value = (String)tokens.get(1);
			} else {
				
				// TODO else what?!??!
			}
			
			return value;
		}


		public void dump() {
			
			System.out.println("urlIndex: "+urlIndex);
			System.out.println("endpointName: "+endpointName);
			System.out.println("endpointURL: "+endpointURL);
			
		}
		
	}
	

	
	public class EndpointDetailsComparator implements Comparator {

		public int compare(Object arg0, Object arg1) {
			EndpointDetails ep0 = (EndpointDetails)arg0;
			EndpointDetails ep1 = (EndpointDetails)arg1;
			
			int result = ep0.endpointName.compareToIgnoreCase(ep1.endpointName);
			
			return result;
		}
		
	}*/
	
}
