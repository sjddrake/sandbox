package uk.co.neo9.sandbox.endpointutils;

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

public class PythonEndpointReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PythonEndpointReader reader = new PythonEndpointReader();
		reader.go();

	}
	
	public void go() {
		
		String filedetails = Neo9TestingConstants.ROOT_TEST_FOLDER
		 + "/LTSB/endpoints/"
		 //+ "p17255dev155.was61DTb_E23.COAServerDevTst64.coaapplication.py";
		 + "p17250dev150.was61DTb_A03.S4UASServerDevTst04.s4uasapplication.py";
		processPythonFile(filedetails);
	}
	
	
	
	public void processPythonFile(String filedetails) {
		
		// first read in the python file as lines of text
		List lines = null;
		try {
			lines = FileServer.readTextFile(filedetails);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// extrract the endpoint lines from the file
		List endpointLines = extractEndpointLines(lines);
		SandboxCheats.outputSimpleLines(endpointLines);
		
		System.out.println("\n\n ======================== \n\n");
		
		// convert the lines into someting more meanigful
		List endpoints = transformEndpointLines(endpointLines);
		
		// output a report
		outputEndpoints(endpoints);
	}

	
	
	
	private void outputEndpoints(List endpoints) {
		
		// first sort the endpoints by name
		Collections.sort(endpoints,new EndpointDetailsComparator());
		
		for (Iterator iter = endpoints.iterator(); iter.hasNext();) {
			EndpointDetails endpoint = (EndpointDetails) iter.next();
			String csvOutput = formatEndpointAsCSV(endpoint);
			System.out.println(csvOutput);
		}
	}

	private String formatEndpointAsCSV(EndpointDetails endpoint) {
		StringBuffer buff = new StringBuffer();
//		buff.append("urlName_");
//		buff.append(endpoint.urlIndex);
//		buff.append(" ");
		buff.append(endpoint.endpointName);
		buff.append(" ");
		buff.append(endpoint.endpointURL);
		buff.append(" ");
		return buff.toString();
	}

	private List transformEndpointLines(List endpointLines) {
		
		// simple validation
		int noOfLines = endpointLines.size();
		if (noOfLines == 0) {
			return null;
			
		}
		
//		 could do an even check but leaving it to the MO for now
		int noOfPairs = noOfLines/2;
		
		// Transform the raw lines of the python file into message objects
		String urlNameLine = null;
		String urlEndpointLine = null;
		List endpointMOs = new ArrayList();
		for (int i = 0; i < noOfPairs; i++) {
			int index = i*2;
			urlNameLine = (String) endpointLines.get(index);
			urlEndpointLine = (String) endpointLines.get(index+1);
			EndpointDetails details = new EndpointDetails();
			boolean success = details.extractDetails(urlNameLine,urlEndpointLine);
			if (success) {
				endpointMOs.add(details);
			} else {
				System.out.println("failed to extract the following lines:");
				System.out.println(urlNameLine);
				System.out.println(urlEndpointLine);
				System.out.println("-------------------");
			}
		}
		
		return endpointMOs;
	}



	private List extractEndpointLines(List lines) {
		
		List extractedLines = new ArrayList();
		
		// simply loop through and pick out lines that follow the standard naming
		for (Iterator iter = lines.iterator(); iter.hasNext();) {
			String line = (String) iter.next();
			boolean includeLine = checkLineForExtraction(line);
			if (includeLine) {
				extractedLines.add(line);
			}
		}
		
		return extractedLines;
	}

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
			
			/* 	 DEBUG 	 */
			if (urlIndex == 100) {
				boolean x = true;
			}
			/* 	 DEBUG 	 */
			
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
		
	}
	
}
