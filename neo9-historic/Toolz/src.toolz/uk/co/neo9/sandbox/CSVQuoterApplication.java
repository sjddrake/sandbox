package uk.co.neo9.sandbox;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import uk.co.neo9.utilities.file.CSVGenericDataContainer;
import uk.co.neo9.utilities.file.FileServer;

public class CSVQuoterApplication {

	/**
	 * This application reads in a CSV application and puts ' " 's around its fields
	 */
	public static void main(String[] args) {
		
		String fileDetails = "C:/Simonz/_Work/Product Table Maintenance App/Integration & 3 Brand World/Combined HBOS MAC Codes.csv";
		
		CSVQuoterApplication app = new CSVQuoterApplication();
		app.go(fileDetails,true);

	}

	private void go(String fileDetails, boolean hasHeaders) {
		
		// this more of a harness than a test! ;-)
		
		List<List<String>> rows = null;
		try {
			rows = FileServer.readCSVFile(fileDetails);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Couldn't load the test file: "+fileDetails);
		}
		
		boolean flagFirstRowAsHeaders = hasHeaders;
		CSVGenericDataContainer container = new CSVGenericDataContainer();
		for (Iterator<List<String>> iter = rows.iterator(); iter.hasNext();) {
			List<String> fields = iter.next();
			if (flagFirstRowAsHeaders) {
				container.addHeaders(fields);
				flagFirstRowAsHeaders = false;
			} else {
				container.addRow(fields);
			}
			
		}
		
		String output = container.dump();
		System.out.println(output);
	}

}
