package uk.co.neo9.sandbox.cameraeye;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

public class IncrementedTextGeneratorTestCase extends TestCase {

	public static void main(String[] args) {
	}

	
	private IncrementedTextGenerator getTestGenerator(String baseText, int noOfDigits, int startingNo) {
		
		IncrementedTextModel model = null;
		model = new IncrementedTextModel(baseText,noOfDigits,startingNo);
		
		IncrementedTextGenerator gen = null;
		gen = new IncrementedTextGenerator(model);
		return gen;
	}
	
	private IncrementedTextGenerator getTestGenerator(String baseText, int noOfDigits, int startingNo, boolean isTrailing) {
		
		IncrementedTextModel model = null;
		model = new IncrementedTextModel(baseText,noOfDigits,startingNo);
		model.setTrailingNumber(false);
		
		IncrementedTextGenerator gen = null;
		gen = new IncrementedTextGenerator(model);
		return gen;
	}	
	
	private IncrementedTextGenerator getTestGenerator1() {
		IncrementedTextGenerator gen = null;
		gen = getTestGenerator("DCS",3,23);
		return gen;
	}
	
	
//	public void test_constructor() {
//		
//		IncrementedTextGenerator gen = null;
//		
//		gen = new IncrementedTextGenerator("DCS",3,23);
//		
//		int precision = gen.getPrecision();
//		
//		assertTrue("expected a precion of 1000, got: "+precision,1000==precision);
//		
//	}
	
	
	public void test_generateNumberText1() {
		
		
		IncrementedTextGenerator gen = getTestGenerator1();
		String actualValue = gen.generateNextNumberText();
		
		String expectedValue = "023";
		assertEquals(expectedValue,actualValue);
		
	}
	
	
	public void test_generateText_TrailingNo_Many() {
		
		// put the expected text together
		String[] testData = {"DCF000034",
							 "DCF000035",
							 "DCF000036",
							 "DCF000037",
							 "DCF000038",
							 "DCF000039",
							 "DCF000040"};
		
		String expectedResult = mashItems(testData);
		
		IncrementedTextGenerator gen = null;
		gen = getTestGenerator("DCF",6,34);

		ArrayList returnedText = new ArrayList();
		for (int i = 0; i < 7; i++) {
			String generatedText = gen.generateNext();
			returnedText.add(generatedText);
		}
		String actualResult = mashItems(returnedText);

		assertEquals(expectedResult,actualResult);
		
	}
	
	
	public void test_generateText_LeadingNo_Many() {
		
		// put the expected text together
		String[] testData = {"000034XRV",
							 "000035XRV",
							 "000036XRV",
							 "000037XRV",
							 "000038XRV",
							 "000039XRV",
							 "000040XRV"};
		
		String expectedResult = mashItems(testData);
		
		IncrementedTextGenerator gen = null;
		gen = getTestGenerator("XRV",6,34,false);

		ArrayList returnedText = new ArrayList();
		for (int i = 0; i < 7; i++) {
			String generatedText = gen.generateNext();
			returnedText.add(generatedText);
		}
		String actualResult = mashItems(returnedText);

		assertEquals(expectedResult,actualResult);
		
	}
	
	
	private String mashItems(List items) {
		
		if (items == null) {
			return "null-items";
		}
		
		StringBuffer buf = new StringBuffer();
		for (Iterator iter = items.iterator(); iter.hasNext();) {
			Object item = (Object) iter.next();
			buf.append(item.toString());
			buf.append("|");
		}
		
		return buf.toString();
	}
	
	
	private String mashItems(Object[] items) {
		
		if (items == null) {
			return "null-items";
		}
		
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < items.length; i++) {
			Object item = items[i];
			buf.append(item.toString());
			buf.append("|");
		}
		
		return buf.toString();
	}
}
