package uk.co.neo9.sandbox.endpointutils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import uk.co.neo9.test.Neo9TestingConstants;
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
							 + "p17255dev155.was61DTb_E23.COAServerDevTst64.coaapplication.py";
		
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
		
		// convert the lines into someting more meanigful
		
		// output a report
		outputSimpleLines(endpointLines);
	}

	private void outputSimpleLines(List endpointLines) {
		for (Iterator iter = endpointLines.iterator(); iter.hasNext();) {
			String line = (String) iter.next();
			System.out.println(line);
		}
		
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
		// TODO Auto-generated method stub
		return true;
	}
	

}
