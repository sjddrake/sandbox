/*
 * Created on 31-May-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;
import uk.co.neo9.utilities.file.CSVHelper;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CSVHelperTestCase extends TestCase {


	/**
	 * Constructor for SimpleXMLReaderTest.
	 * @param arg0
	 */
	public CSVHelperTestCase(String arg0) {
		super(arg0);
	}

//	public static void main(String[] args) {
//		junit.swingui.TestRunner.run(CSVHelperTest.class);
//	}



	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}



//	public void testGetField_DetermineNextField1() {
//
//	final String TEST_NAME = "testGetField_DetermineNextField1";
//	
//	String[] fields = {"one","two","three","four","five"};
//	int endOfPreviousField = 18;
//	int startOfNextField = endOfPreviousField+2;
//	final String lTestCSVLine = buildCSVLine(fields,true);
//	int result = FileServer.determineStartOfNextField(lTestCSVLine,endOfPreviousField,true);
//	assertTrue(result==startOfNextField);
//	
//	
//	// outputResults(TEST_NAME,lTestCSVLine, lRemashed);
//	
//}	
//	
//	
//	
//	public void testGetField_DetermineNextField2() {
//
//		final String TEST_NAME = "testGetField_DetermineNextField2";
//		
//		String[] fields = {"one","two",'"'+"free text field"+'"',"four","five"};
//		final String lTestCSVLine = buildCSVLine(fields,false);
//		int endOfPreviousField = 24;
//		int startOfNextField = endOfPreviousField+2;
//		int result = FileServer.determineStartOfNextField(lTestCSVLine,endOfPreviousField,true);
//		assertTrue(result==startOfNextField);
//		
//		
//		// outputResults(TEST_NAME,lTestCSVLine, lRemashed);
//		
//	}	
		

	public void testGetField_Simple3Fields() {
		
		final String TEST_NAME = "testGetField_Simple3Fields";
		
		final String lTestCSVLine = "one,two,three";
		Vector lFieldValues = CSVHelper.extractCSVFields(lTestCSVLine);
		final String lRemashed = TestHelper.buildCSVLine(lFieldValues,false);
		
		outputResults(TEST_NAME,lTestCSVLine, lRemashed);
		
	}


	public void testGetField_EmptyFirstField() {
		
		final String TEST_NAME = "testGetField_EmptyFirstField";
		
		final String lTestCSVLine = ",two,three";
		Vector lFieldValues = CSVHelper.extractCSVFields(lTestCSVLine);
		final String lRemashed = TestHelper.buildCSVLine(lFieldValues,false);
		
		outputResults(TEST_NAME,lTestCSVLine, lRemashed);
	}
	
	
	public void testGetField_EmptyMiddleField() {
		
		final String TEST_NAME = "testGetField_EmptyMiddleField";
		
		final String lTestCSVLine = "one,two,,four,five";
		Vector lFieldValues = CSVHelper.extractCSVFields(lTestCSVLine);
		final String lRemashed = TestHelper.buildCSVLine(lFieldValues,false);
		
		outputResults(TEST_NAME,lTestCSVLine, lRemashed);
	}

	public void testGetField_EmptyFinalField() {
		
		final String TEST_NAME = "testGetField_EmptyFinalField";
		
		final String lTestCSVLine = "one,two,three,";
		Vector lFieldValues = CSVHelper.extractCSVFields(lTestCSVLine);
		final String lRemashed = TestHelper.buildCSVLine(lFieldValues,false);
		
		outputResults(TEST_NAME,lTestCSVLine, lRemashed);
	}

	
	public void testGetField_QuotedFieldsSomeEmpty() {
		
		final String TEST_NAME = "testGetField_QuotedFieldsSomeEmpty";
		
		final String lTestCSVLine = "\"\",\"Date\",\"\",\"Amount\",\"Memo\",\"Subcategory\",\"\",\"\",\"Unknown\",\"\",";
		Vector lFieldValues = CSVHelper.extractCSVFields(lTestCSVLine);
		final String lRemashed = TestHelper.buildCSVLine(lFieldValues,false);
		
		outputResults(TEST_NAME,lTestCSVLine, lRemashed);
	}	

	public void testGetField_AllEmptyFields() {
		
		final String TEST_NAME = "testGetField_AllEmptyFields";
		
		final String lTestCSVLine = ",,,";
		Vector lFieldValues = CSVHelper.extractCSVFields(lTestCSVLine);
		final String lRemashed = TestHelper.buildCSVLine(lFieldValues,false);
		
		outputResults(TEST_NAME,lTestCSVLine, lRemashed);
	}

	public void testGetField_FreeText1() {
		
		final String TEST_NAME = "testGetField_FreeText1";
		
		String[] fields = {"one","two","three","four","five"};
		final String lTestCSVLine = TestHelper.buildCSVLine(fields,true);
		Vector lFieldValues = CSVHelper.extractCSVFields(lTestCSVLine);
		final String lRemashed = TestHelper.buildCSVLine(lFieldValues,true);
		
		outputResults(TEST_NAME,lTestCSVLine, lRemashed);
	}

	public void testGetField_FreeText2() { 
		
		final String TEST_NAME = "testGetField_FreeText2";
		
		String[] fields = {"one","two","free text, seperated by a comma","four","five"};
		final String lTestCSVLine = TestHelper.buildCSVLine(fields,true);		
		Vector lFieldValues = CSVHelper.extractCSVFields(lTestCSVLine);
		final String lRemashed = TestHelper.buildCSVLine(lFieldValues,true);
		
		outputResults(TEST_NAME,lTestCSVLine, lRemashed);
	}	
	
	public void testGetField_FreeText3() { 
		
		final String TEST_NAME = "testGetField_FreeText3";
		
		String[] fields = {"one","two","free text..."+"\r\n"+" ...seperated by a NEWLINE","four","five"};
		final String lTestCSVLine = TestHelper.buildCSVLine(fields,true);		
		Vector lFieldValues = CSVHelper.extractCSVFields(lTestCSVLine);
		final String lRemashed = TestHelper.buildCSVLine(lFieldValues,true);
		
		outputResults(TEST_NAME,lTestCSVLine, lRemashed);
	}


	public void testGetField_MixedText1() {
		
		final String TEST_NAME = "testGetField_MixedText1";
		
		String[] fields = {"one","two",'"'+"free text field"+'"',"four","five"};
	//	String[] fields = {"1","2",'"'+"f r e e"+'"',"4","5"};
		final String lTestCSVLine = TestHelper.buildCSVLine(fields,false);
		Vector lFieldValues = CSVHelper.extractCSVFields(lTestCSVLine);
		String field = (String)lFieldValues.remove(2);
		field = '"'+field+'"';
		lFieldValues.add(2,field);
		final String lRemashed = TestHelper.buildCSVLine(lFieldValues,false);
		
		outputResults(TEST_NAME,lTestCSVLine, lRemashed);
	}	
	
	
	public void testGetField_EmbbededQuotes1() {
		
		final String TEST_NAME = "testGetField_EmbbededQuotes1";
		
		String x = '"'+"free text with "+'"'+'"'+"double-quotes"+'"'+'"'+" in"+'"';
		
		String[] fields = {"one","two",x,"four","five"};
		final String lTestCSVLine = TestHelper.buildCSVLine(fields,false);
		Vector lFieldValues = CSVHelper.extractCSVFields(lTestCSVLine);
		String field = (String)lFieldValues.remove(2);
		field = '"'+field+'"';
		lFieldValues.add(2,field);
		final String lRemashed = TestHelper.buildCSVLine(lFieldValues,false);
		
		outputResults(TEST_NAME,lTestCSVLine, lRemashed);
	}	
		

	public void testGetField_EmbbededQuotes2() {
		
		final String TEST_NAME = "testGetField_EmbbededQuotes2";
		
		String x = '"'+"free text with "+'"'+'"'+"double-quotes"+'"'+'"'+" in"+'"';
		
		String[] fields = {"one","two",x};
		final String lTestCSVLine = TestHelper.buildCSVLine(fields,false);
		Vector lFieldValues = CSVHelper.extractCSVFields(lTestCSVLine);
		String field = (String)lFieldValues.remove(2);
		field = '"'+field+'"';
		lFieldValues.add(2,field);
		final String lRemashed = TestHelper.buildCSVLine(lFieldValues,false);
		
		outputResults(TEST_NAME,lTestCSVLine, lRemashed);
	}	
	
// =========================== helper methods =======================	
	


	private void outputResults(String TEST_NAME, String lTestCSVLine, String lRemashed){
		
		if (lTestCSVLine.equals(lRemashed)) {
			System.out.println(TEST_NAME + " - worked :-)");
		} else {
			System.out.println(TEST_NAME + " - failed!!!!");
			System.out.println("> ORIGINAL: "+lTestCSVLine);
			System.out.println("> REMASHED: "+lRemashed);
			assertTrue("see sysout for details of test results for: "+TEST_NAME,false);
		}		
	}



}
