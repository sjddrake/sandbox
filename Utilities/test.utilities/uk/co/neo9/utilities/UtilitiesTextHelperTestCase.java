package uk.co.neo9.utilities;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

public class UtilitiesTextHelperTestCase extends TestCase {
	
	
//	public static void main(String[] args) {
//		
//		UtilitiesTextHelperTest testInstance = new UtilitiesTextHelperTest();
//		testInstance.test_extractTimeFromDate();
//	}
	

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	

	
	@Test
	public void test_flattenText() {
		
		String matchName = "Knockout Round 1 Match #1";
		String flattenedResult = UtilitiesTextHelper.flattenText(matchName).toString();
		assertEquals("KnockoutRound1Match#1", flattenedResult);
		
	}
	


	
	private Date getTestDate() {
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(2011, Calendar.SEPTEMBER, 8, 23, 45);
		
		Date testDate = cal.getTime();
		
		return testDate;
		
	}
	
	
	@Test
	public void testExtractMonthFromText_id() {
		
		String text = "06 accounts June 2011.txt"; 
		int monthId = UtilitiesTextHelper.extractMonthIdFromText(text);
		
		assertEquals(Calendar.JUNE, monthId);
		
	}
	
	@Test
	public void testExtractMonthFromText_name() {
		
		String text = "06 accounts apr 2011.txt"; 
		boolean fullName = true;
		String monthName = UtilitiesTextHelper.extractMonthNameFromText(text,fullName);
		assertEquals(CommonConstants.MONTH_NAME_FULL_APRIL, monthName);
		
		fullName = false;
		monthName = UtilitiesTextHelper.extractMonthNameFromText(text,fullName);
		assertEquals(CommonConstants.MONTH_NAME_SHORT_APRIL, monthName);
		
	}
	
	
	@Test
	public void testFormatPositionAsText() {
		
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			String txt = UtilitiesTextHelper.formatPositionAsText(i);
			System.out.println(txt);
			buff.append(txt);
		}
		assertEquals("1st2nd3rd4th5th6th7th8th9th", buff.toString());
	}
}
