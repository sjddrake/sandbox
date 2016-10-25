package uk.co.neo9.utilities;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import uk.co.neo9.test.Neo9TestingConstants;
import uk.co.neo9.utilities.file.CSVGenericDataContainer;
import uk.co.neo9.utilities.file.FileServer;
import junit.framework.TestCase;

public class CSVGenericDataContainerTest extends TestCase {

	public CSVGenericDataContainerTest(String arg0) {
		super(arg0);
	}
	
	
	public void test_loadGenericContainerFromFile_Headers(){
		String fileDetails = Neo9TestingConstants.ROOT_TEST_FOLDER+"csv/accountsWithHeaders1.csv";
		loadGenericContainerFromFile(fileDetails,true);
	}
	
	public void test_loadGenericContainerFromFile_NoHeaders(){
		 String fileDetails = Neo9TestingConstants.ROOT_TEST_FOLDER+"csv/contactsNoHeaders1.csv";
		loadGenericContainerFromFile(fileDetails,false);
	}
	
//	public void test_loadGenericContainerFromMACData(){
//		 String fileDetails = "C:/Simonz/_Work/Product Table Maintenance App/Integration & 3 Brand World/HBOS MAC Codes.csv";
//		loadGenericContainerFromFile(fileDetails,true);
//	}
	
	private void loadGenericContainerFromFile(String fileDetails, boolean hasHeaders){
		
		// this more of a harnes than a test! ;-)
		
		Vector<String> rows = null;
		try {
			rows = FileServer.readCSVFile(fileDetails);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Couldn't load the test file: "+fileDetails);
		}
		
		boolean flagFirstRowAsHeaders = hasHeaders;
		CSVGenericDataContainer container = new CSVGenericDataContainer();
		for (Iterator iter = rows.iterator(); iter.hasNext();) {
			Vector fields = (Vector) iter.next();
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
	
	

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
