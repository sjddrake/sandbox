package uk.co.neo9.sandbox.sqlutils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import uk.co.neo9.test.Neo9TestingConstants;
import uk.co.neo9.utilities.file.CSVGenericDataContainer;
import uk.co.neo9.utilities.file.CSVMappingTransform;
import uk.co.neo9.utilities.file.FileServer;
import junit.framework.TestCase;

public class SQLGenerationDataContainerTest extends TestCase {

	
	public void test_loadGenericContainerFromMACData(){
		String fileDetails = Neo9TestingConstants.ROOT_TEST_FOLDER+"sqlutils/MAC_CODES.csv";
		loadSQLContainerFromTransformFile(fileDetails,true);
	}
	
//	public void test_loadGenericContainerFromDecodeData(){
//		 String fileDetails = Neo9TestingConstants.ROOT_TEST_FOLDER+"sqlutils/CodeDecode.csv";
//		loadSQLContainerFromOneToOneFile(fileDetails,true);
//	}
	
//	public void test_loadGenericContainerFromDecodeData(){
//		 String fileDetails = "C:/Simonz/_Work/Product Table Maintenance App/Integration & 3 Brand World/Current Decode Values in DevTst63.csv";
//		loadSQLContainerFromOneToOneFile(fileDetails,true);
//	}
	
	private void loadSQLContainerFromTransformFile(String fileDetails, boolean hasHeaders){
		
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
		SQLGenerationDataContainer container = new SQLGenerationDataContainer();
		
		SQLGenerationCSVBuddy buddy = null;
		buddy = new SQLGenerationCSVBuddy();
		buddy.setTableName("PRODUCT_LOOKUP_DATA");
		container.setBuddy(buddy);

		List<CSVMappingTransform> transforms = getMACCodeTransforms();
		buddy.setTransforms(transforms);

		List<String> columnNames = getMACCodeScriptColumnNames();
		container.addHeaders(columnNames);
		buddy.setColumnNames(columnNames);
		
		
		for (Iterator iter = rows.iterator(); iter.hasNext();) {
			Vector fields = (Vector) iter.next();
			if (flagFirstRowAsHeaders) {
				// Don't use the file's headers for this one
				flagFirstRowAsHeaders = false;
			} else {
				container.addRow(fields);
			}
			
		}
		
		String output = container.generateSQLScript();
		System.out.println(output);
	}
	
	
	


	private void loadSQLContainerFromOneToOneFile(String fileDetails, boolean hasHeaders){
		
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
		SQLGenerationDataContainer container = new SQLGenerationDataContainer();
		
		SQLGenerationCSVBuddy buddy = null;
		buddy = new SQLGenerationCSVBuddy();
		buddy.setTableName("PRODUCT_LOOKUP_CODE_DECODE");
		container.setBuddy(buddy);

		
		for (Iterator iter = rows.iterator(); iter.hasNext();) {
			Vector fields = (Vector) iter.next();
			if (flagFirstRowAsHeaders) {
				container.addHeaders(fields);
				buddy.setColumnNames(fields);
				flagFirstRowAsHeaders = false;
			} else {
				container.addRow(fields);
			}
			
		}
		
		String output = container.generateSQLScript();
		System.out.println(output);
	}
	
	
	

	private List<CSVMappingTransform> getMACCodeTransforms() {
		
		final String effectiveDate = "11/01/2011 00:00:00";
		final String DELETED = "0";
		final String USERID = "HBOS2011";
		final String EDITOR_FLAG = "0";
		
		List<CSVMappingTransform> transforms = new ArrayList<CSVMappingTransform>();
		
		transforms.add(new CSVMappingTransform(0)); // MAC_CODE -> MAC_CODE
		transforms.add(new CSVMappingTransform(1)); // ACQUIRE_PRODUCT_CODE -> ACQUIRE_PRODUCT_CODE
		transforms.add(new CSVMappingTransform(2)); // DESCRIPTION -> DESCRIPTION
		transforms.add(new CSVMappingTransform(3)); // ORG -> ORG
		transforms.add(new CSVMappingTransform(4)); // LOGO -> LOGO
		// transforms.add(new CSVMappingTransform(0)); ... skipping LOGO2
		transforms.add(new CSVMappingTransform(5)); // PCTID -> PCTID
		transforms.add(new CSVMappingTransform(6)); // OFFER_CODE1 -> OFFER_CODE1
		transforms.add(new CSVMappingTransform(7)); // OFFER_CODE2 -> OFFER_CODE2
		transforms.add(new CSVMappingTransform(8)); // OFFER_CODE3 -> OFFER_CODE3
		transforms.add(new CSVMappingTransform(9,effectiveDate,CSVMappingTransform.TYPE_DATE)); // EFFECTIVE_DATE
		transforms.add(new CSVMappingTransform(10,DELETED)); // DELETED
		transforms.add(new CSVMappingTransform(11,USERID)); // USERID
		transforms.add(new CSVMappingTransform(12,EDITOR_FLAG)); // EDITOR_FLAG

		
		return transforms;
	}

	
	private List<String> getMACCodeScriptColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		
		columnNames.add("MAC_CODE");
		columnNames.add("ACQUIRE_PRODUCT_CODE");
		columnNames.add("DESCRIPTION");
		columnNames.add("ORG");
		columnNames.add("LOGO");
		columnNames.add("PCTID");
		columnNames.add("OFFER_CODE1");
		columnNames.add("OFFER_CODE2");
		columnNames.add("OFFER_CODE3");
		columnNames.add("EFFECTIVE_DATE");
		columnNames.add("DELETED");
		columnNames.add("USERID");
		columnNames.add("EDITOR_FLAG");
		
		return columnNames;
	}	
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
}
