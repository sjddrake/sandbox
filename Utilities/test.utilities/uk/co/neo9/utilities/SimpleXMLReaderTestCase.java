/*
 * Created on 31-May-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities;

import java.util.ArrayList;

import uk.co.neo9.utilities.SimpleXMLReader;

import junit.framework.TestCase;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SimpleXMLReaderTestCase extends TestCase {

	ArrayList testLines = new ArrayList();

	/**
	 * Constructor for SimpleXMLReaderTest.
	 * @param arg0
	 */
	public SimpleXMLReaderTestCase(String arg0) {
		super(arg0);
	}

//	public static void main(String[] args) {
//		junit.swingui.TestRunner.run(SimpleXMLReaderTest.class);
//	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		
		testLines.add("<root>");
		testLines.add("<one>ONE</one>");
		testLines.add("<two>TWO</two>");
		testLines.add("<three>THREE</three>");
		testLines.add("<empty></empty>");
		testLines.add("<MixedCase>mIXED cASE tags UseD</MixedCase>");
		testLines.add("<sentence>This sentence contains lots of words.</sentence>");
		testLines.add("<spaced>  this should be trimmed  </spaced>");
		testLines.add("</root>");
		
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}


	public void TEMPLATE_testGetField_() {
		
//		String lTestTag = "empty";
//		String lFieldValue = SimpleXMLReader.getField(testLines,lTestTag);
//		System.out.println("The value of xml field "+lTestTag+" is: "+lFieldValue);
		
	}

	public void testGetField_MixedCase() {
		
		String lTestTag = "MixedCase";
		String lFieldValue = SimpleXMLReader.getField(testLines,lTestTag);
		System.out.println("The value of xml field "+lTestTag+" is: "+lFieldValue);
		
	}	

	public void testGetField_WrongCase() {
		
		String lTestTag = "MoreMixedCase";
		String lLineToFind = "<"+lTestTag+">"+"this proves its case insensitive"+"</"+lTestTag+">";
		testLines.add(lLineToFind);
		String lWrongTag = "mOREmIXEDcASE";
		String lFieldValue = SimpleXMLReader.getField(testLines,lWrongTag);
		System.out.println("The value of xml field "+lTestTag+" is: "+lFieldValue);
		
	}	


	public void testGetField_sentence() {
		
		String lTestTag = "sentence";
		String lFieldValue = SimpleXMLReader.getField(testLines,lTestTag);
		System.out.println("The value of xml field "+lTestTag+" is: "+lFieldValue);
		
	}

	public void testGetField_spaced() {
		
		String lTestTag = "spaced";
		String lFieldValue = SimpleXMLReader.getField(testLines,lTestTag);
		System.out.println("The value of xml field "+lTestTag+" is: "+lFieldValue);
		
	}

	public void testGetField_one() {
		
		String lTestTag = "one";
		String lFieldValue = SimpleXMLReader.getField(testLines,lTestTag);
		System.out.println("The value of xml field "+lTestTag+" is: "+lFieldValue);
		
	}

	public void testGetField_three() {
		
		String lTestTag = "three";
		String lFieldValue = SimpleXMLReader.getField(testLines,lTestTag);
		System.out.println("The value of xml field "+lTestTag+" is: "+lFieldValue);
		
	}

	public void testGetField_noTag() {
		
		String lTestTag = null;
		String lFieldValue = SimpleXMLReader.getField(testLines,lTestTag);
		System.out.println("The value of xml field "+lTestTag+" is: "+lFieldValue);
		
	}

	public void testGetField_missing() {
		
		String lTestTag = "missing";
		String lFieldValue = SimpleXMLReader.getField(testLines,lTestTag);
		System.out.println("The value of xml field "+lTestTag+" is: "+lFieldValue);
		
	}
	
	public void testGetField_emptyField() {
		
		String lTestTag = "empty";
		String lFieldValue = SimpleXMLReader.getField(testLines,lTestTag);
		System.out.println("The value of xml field "+lTestTag+" is: "+lFieldValue);
		
	}	

}
