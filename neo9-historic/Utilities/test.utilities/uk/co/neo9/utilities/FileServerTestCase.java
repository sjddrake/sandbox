package uk.co.neo9.utilities;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import uk.co.neo9.utilities.file.FileServer;



public class FileServerTestCase extends junit.framework.TestCase {
	
	
	private final String DEFAULT_TEST_SUB_FOLDER = "fileserver/";
	private final String DEFAULT_TEST_TEXT_FILE = "testTextFile.txt";
	private final String DEFAULT_TEST_PRPTS_FILE = "testPropertiesFile.txt";
	
	public FileServerTestCase(String arg0) {
		super(arg0);
	}

//	public static void main(String[] args) {
//		junit.swingui.TestRunner.run(FileServerTest.class);
//	}
	
	
	public void test_ReadTextFile_one() {
		
		final String TEST_NAME = "test_ReadTextFile_one";
		
		final String lTestFileDetails = getDefaultTestTextFileDetails();
		final String[] lTestLines = {"line one", "line two", "line three"};
		String lTestText = TestHelper.buildTestFileText(lTestLines);
		String lRemashed = null;
		
		try {		
			FileServer.writeTextFile(lTestFileDetails,lTestText);
			Vector lFieldValues = FileServer.readTextFile(lTestFileDetails);
			lRemashed = TestHelper.buildTestFileText(lFieldValues);
		} catch (Exception e) {
			System.err.println("Got a file error while executing test "+TEST_NAME);
			e.printStackTrace();
		}
		
		
		outputResults(TEST_NAME,lTestText, lRemashed);
	}
	

	
	
	public void test_ReadTextFile_two() {
		
		final String TEST_NAME = "test_ReadTextFile_two";
		
		final String lTestFileDetails = getDefaultTestTextFileDetails();
		final String[] lTestLines = {"line one", "", "line three"};
		String lTestText = TestHelper.buildTestFileText(lTestLines);
		String lRemashed = null;
		
		try {		
			FileServer.writeTextFile(lTestFileDetails,lTestText);
			Vector lFieldValues = FileServer.readTextFile(lTestFileDetails);
			lRemashed = TestHelper.buildTestFileText(lFieldValues);
		} catch (Exception e) {
			System.err.println("Got a file error while executing test "+TEST_NAME);
			e.printStackTrace();
		}
		
		
		outputResults(TEST_NAME,lTestText, lRemashed);
	}	
	
	private void outputResults(String TEST_NAME, String lTestCSVLine, String lRemashed){
		
		if (lTestCSVLine.equals(lRemashed)) {
			System.out.println(TEST_NAME + " - worked :-)");
		} else {
			System.out.println(TEST_NAME + " - failed!!!!");
			System.out.println("> ORIGINAL: "+lTestCSVLine);
			System.out.println("> REMASHED: "+lRemashed);
			assertTrue(false);
		}		
	}	
	
	
	
	public void test_ReadCSVFile_one() {
		
		final String TEST_NAME = "test_ReadCSVFile_one";
		
		final String lTestFileDetails = getDefaultTestTextFileDetails();
		final String[] lTestLines = {"line one", "line two", "line three"};
		String lTestText = TestHelper.buildTestFileText(lTestLines);
		String lRemashed = null;
		
		try {		
			FileServer.writeTextFile(lTestFileDetails,lTestText);
			Vector csvLines = FileServer.readCSVFile(lTestFileDetails);
			lRemashed = TestHelper.rebuildTestFileText(csvLines);
		} catch (Exception e) {
			System.err.println("Got a file error while executing test "+TEST_NAME);
			e.printStackTrace();
		}
		
		
		outputResults(TEST_NAME,lTestText, lRemashed);
	}	

	
	public void test_ReadCSVFile_two() {
		
		final String TEST_NAME = "test_ReadCSVFile_two";
		
		final String lTestFileDetails = getDefaultTestTextFileDetails();
		//String csvField = '"'+""+'"';
		final String[] lTestLines = {"line one", "line2 field 1,line2 field 2,line2 field 3", "line three"};
		String lTestText = TestHelper.buildTestFileText(lTestLines);
		String lRemashed = null;
		
		try {		
			FileServer.writeTextFile(lTestFileDetails,lTestText);
			Vector csvLines = FileServer.readCSVFile(lTestFileDetails);
			lRemashed = TestHelper.rebuildTestFileText(csvLines);
		} catch (Exception e) {
			System.err.println("Got a file error while executing test "+TEST_NAME);
			e.printStackTrace();
		}
		
		
		outputResults(TEST_NAME,lTestText, lRemashed);
	}	



	public void test_ReadCSVFile_three() {
		
		final String TEST_NAME = "test_ReadCSVFile_three";
		
		final String lTestFileDetails = getDefaultTestTextFileDetails();
		String[] fields = {"line2 field 1","line2 field 2","line2 field 3"};
		String line2 = TestHelper.buildCSVLine(fields,false);
		final String[] lTestLines = {"line one", line2, "line three"};
		String lTestText = TestHelper.buildTestFileText(lTestLines);
		String lRemashed = null;
		
		try {		
			FileServer.writeTextFile(lTestFileDetails,lTestText);
			Vector csvLines = FileServer.readCSVFile(lTestFileDetails);
			lRemashed = TestHelper.rebuildTestFileText(csvLines);
		} catch (Exception e) {
			System.err.println("Got a file error while executing test "+TEST_NAME);
			e.printStackTrace();
		}
		
		
		outputResults(TEST_NAME,lTestText, lRemashed);
	}	

	public void test_ReadCSVFile_four() {
		
		final String TEST_NAME = "test_ReadCSVFile_four";
		
		final String lTestFileDetails = getDefaultTestTextFileDetails();
		String csvField = "ll2f2 with " +'"'+ '"'+"embedded" +'"'+ '"'+ " quotes in.";
		String[] fields = {"line2 field 1",csvField,"line2 field 3"};
		String line2 = TestHelper.buildCSVLine(fields,false);
		System.out.println(line2);
		final String[] lTestLines = {"line one", line2, "line three"};
		String lTestText = TestHelper.buildTestFileText(lTestLines);
		String lRemashed = null;
		
		try {		
			FileServer.writeTextFile(lTestFileDetails,lTestText);
			Vector csvLines = FileServer.readCSVFile(lTestFileDetails);
			lRemashed = TestHelper.rebuildTestFileText(csvLines);
		} catch (Exception e) {
			System.err.println("Got a file error while executing test "+TEST_NAME);
			e.printStackTrace();
		}
		
		
		outputResults(TEST_NAME,lTestText, lRemashed);
	}	
	
	
	public void test_ReadCSVFile_five() {
		
		final String TEST_NAME = "test_ReadCSVFile_five";
		
		final String lTestFileDetails = getDefaultTestTextFileDetails();
		String csvField = "l2f2: free text..."+"\r\n"+"...seperated by a NEWLINE :l2f2";
		String[] fields = {"line2 field 1",csvField,"line2 field 3"};
		String line2 = TestHelper.buildCSVLine(fields,false);
		System.out.println(line2);
		final String[] lTestLines = {"line one", line2, "line three"};
		String lTestText = TestHelper.buildTestFileText(lTestLines);
		String lRemashed = null;
		
		try {		
			FileServer.writeTextFile(lTestFileDetails,lTestText);
			Vector csvLines = FileServer.readCSVFile(lTestFileDetails);
			lRemashed = TestHelper.rebuildTestFileText(csvLines);
		} catch (Exception e) {
			System.err.println("Got a file error while executing test "+TEST_NAME);
			e.printStackTrace();
		}
		
		
		outputResults(TEST_NAME,lTestText, lRemashed);
	}
	
	
	public void test_getFileNameFromFileDetails_doubleSlash() {
		String fileDetails = "Z:\\photos - 5GB!!!!\\edits\\Xmas Firdge Magnets\\originals\\not using\\PICT4055.JPG";
		String expectedResult = "PICT4055.JPG";
		String actualResult = FileServer.getFileNameFromFileDetails(fileDetails);
		
		assertEquals("filenames should match",expectedResult,actualResult);
		
	}

	public void test_getFileNameFromFileDetails_forwardSlash() {
		String fileDetails = "Z:/photos - 5GB!!!!/edits/Xmas Firdge Magnets/originals/not using/PICT4055.JPG";
		String expectedResult = "PICT4055.JPG";
		String actualResult = FileServer.getFileNameFromFileDetails(fileDetails);
		
		assertEquals("filenames should match",expectedResult,actualResult);
		
	}
	
	
	public void test_loadProperties_success(){
		
		String fileDetails = getDefaultTestPropertiesFileDetails();
		Properties props = null;
		try {
			props = FileServer.loadProperties(fileDetails);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Test failed with an IO Exception");
		}
		
		assertNotNull(props);
		
		// Enumeration<Object> properties = props.elements();
		Enumeration<Object> keys = props.keys();
		
		while (keys.hasMoreElements()) {
			String keyValue = (String) keys.nextElement();
			String propertyValue = props.getProperty(keyValue);
			System.out.println(keyValue+" - "+propertyValue);
			
		}
		
	}
	
	
	
	// ====================================== HELPER METHODS ===================================	
	
	
	private String getDefaultTestTextFileDetails(){
		return TestHelper.getDefaultTestFolderRoot()+DEFAULT_TEST_SUB_FOLDER+DEFAULT_TEST_TEXT_FILE;
	}

	private String getDefaultTestPropertiesFileDetails(){
		return TestHelper.getDefaultTestFolderRoot()+DEFAULT_TEST_SUB_FOLDER+DEFAULT_TEST_PRPTS_FILE;
	}	
	

}
