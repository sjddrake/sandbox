import java.io.FileWriter;
import java.io.IOException;

import junit.framework.TestCase;


public class FileChangeCheckerTest extends TestCase {
	
	
	private final String testFileRootFolder = "D:/Test/fileserver/";
	private final String testFileName1 = "FileChangeCheckerTestFile1.txt";
	private final String testFileName2 = "FileChangeCheckerTestFile2.txt";

	public FileChangeCheckerTest(String name) {
		super(name);
	}

	
	public void test_instanceMode_modification() {
		
		// get the test file details
		String fileDetails = getTestFileDetails1();
		
		// create the checker instance
		FileChangeChecker checker = new FileChangeChecker(fileDetails);
		
		try {
			// do a check before any changes occur
			boolean hasChanged = checker.checkFileChanged();
			assertFalse(hasChanged);
			
			// re-write the test file and then check it has changed
			setupTestFile(fileDetails);
			//--> delay();
			hasChanged = checker.checkFileChanged();
			assertTrue(hasChanged);
			
			// check file again and make sure it hasn't changed
			hasChanged = checker.checkFileChanged();
			assertFalse(hasChanged);
			
			// and a final time!
			hasChanged = checker.checkFileChanged();
			assertFalse(hasChanged);
			
			
		} catch (FileChangeCheckerException e) {
			
			fail(e.getMessage());
			
		}
		
	}
	
	
	public void test_staticMode_modification() {
		
		// get the test file details
		String fileDetails1 = getTestFileDetails1();
		String fileDetails2 = getTestFileDetails1();
		
		
		try {
			// do a check before any changes occur
			boolean has1Changed = FileChangeChecker.checkFileChanged(fileDetails1);
			assertFalse(has1Changed);
			boolean has2Changed = FileChangeChecker.checkFileChanged(fileDetails2);
			assertFalse(has2Changed);
			
			// re-write one of the test files and then check for changes
			setupTestFile(fileDetails1);
			//--> delay();
			has1Changed = FileChangeChecker.checkFileChanged(fileDetails1);
			assertTrue(has1Changed);
			has2Changed = FileChangeChecker.checkFileChanged(fileDetails2);
			assertFalse(has2Changed);
			
			
			// check again for surety
			has1Changed = FileChangeChecker.checkFileChanged(fileDetails1);
			assertFalse(has1Changed);
			has2Changed = FileChangeChecker.checkFileChanged(fileDetails2);
			assertFalse(has2Changed);
			
		} catch (FileChangeCheckerException e) {

			fail(e.getMessage());
		}
		
	}	

	
	public void test_instanceMode_nullFileDetails() {
		String fileDetails = null;
		doInstanceValidationTest(fileDetails, "");
	}
	
	
	public void test_instanceMode_emptyFileDetails() {
		String fileDetails = "";
		doInstanceValidationTest(fileDetails, "");
	}

	public void test_instanceMode_rubbishFileDetails() {
		String fileDetails = "rubbish";
		doInstanceValidationTest(fileDetails, "");
	}	
	
	public void test_staticMode_nullFileDetails() {
		String fileDetails = null;
		doStaticValidationTest(fileDetails, "");
	}
	
	
	public void test_staticMode_emptyFileDetails() {
		String fileDetails = "";
		doStaticValidationTest(fileDetails, "");
	}

	public void test_staticMode_rubbishFileDetails() {
		String fileDetails = "rubbish";
		doStaticValidationTest(fileDetails, "");
	}
	
	
	private void doStaticValidationTest(String fileDetails, String errorFlavour) {
		
		boolean validationExceptionCaught = false;
		try {
			FileChangeChecker.checkFileChanged(fileDetails);
		
		} catch (FileChangeCheckerException e) {
			//TODO test the error for the correct flavour
			validationExceptionCaught = true;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail();
		}
		
		if (validationExceptionCaught == false) {
			// shouldn't get here!
			fail();
		}		
		
	}

	private void doInstanceValidationTest(String fileDetails, String errorFlavour) {
		
		boolean validationExceptionCaught = false;
		FileChangeChecker checker = new FileChangeChecker(fileDetails);
		try {
			checker.checkFileChanged();
		
		} catch (FileChangeCheckerException e) {
			//TODO test the error for the correct flavour
			validationExceptionCaught = true;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail();
		}
		
		if (validationExceptionCaught == false) {
			// shouldn't get here!
			fail();
		}		
		
	}
	
	private void delay() {
		int max = 1000000000;
		int i = 0;
		for (int j = 0; j < max; j++) {
			//System.out.print(i++);
			i++;
		}
		i = 0;
		for (int j = 0; j < max; j++) {
			//System.out.print(i++);
			i++;
		}
		i = 0;
		for (int j = 0; j < max; j++) {
			//System.out.print(i++);
			i++;
		}
	}


	private String getTestFileDetails1(){
		String fileDetails = testFileRootFolder + testFileName1;
		return fileDetails;
	}
	
	private String getTestFileDetails2(){
		String fileDetails = testFileRootFolder + testFileName2;
		return fileDetails;
	}	
	
	private void setupTestFile(String fileDetails){
		
		// setup a file writer
		FileWriter fw = null;
		try {
			
			fw  = new FileWriter(fileDetails);
			fw.write("This is the test file for the FileChangeCheckerTest JUnit.");
			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}

	}
	
	protected void setUp() throws Exception {
		setupTestFile(getTestFileDetails1());
		setupTestFile(getTestFileDetails2());
	}

	protected void tearDown() throws Exception {
	}

}
