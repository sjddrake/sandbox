package uk.co.neo9.apps.filerenamer;

import uk.co.neo9.apps.filerenamer.BespokeFileRenamerPlugIn;
import uk.co.neo9.apps.filerenamer.FileRenamerHelper;
import junit.framework.TestCase;

public class FileRenamerHelperTestCase extends TestCase {

	public FileRenamerHelperTestCase(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void test_one(){
		
		// this is for testing the formatting methods
		String lTestFilename = "22-04-06_1444";
		
		String reformatted = FileRenamerHelper.reformatCameraPhoneFileName(lTestFilename);
		System.out.println(reformatted);
		
		assertEquals("2006-04-22_1444", reformatted);
	}

	public void test_two(){
		
		// this is for testing the formatting methods
		String lTestFilename = "22-04-06_1444";
		
		String reformatted = FileRenamerHelper.reformatCameraPhoneFileName(lTestFilename);
		System.out.println(reformatted);
		
		assertEquals("MOBL 2006-08-12_2011", reformatted);
	}	

}
