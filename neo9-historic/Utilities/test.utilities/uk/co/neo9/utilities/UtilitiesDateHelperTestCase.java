package uk.co.neo9.utilities;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

public class UtilitiesDateHelperTestCase extends TestCase {
	
	
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
	public void test_extractTimeFromDate() {

		Date testDate = getTestDate();

		String timeOnly = UtilitiesDateHelper.extractTimeFromDate(testDate);
		
		System.out.println(timeOnly);
		
		assertEquals("23:45", timeOnly);
		
	}
	
	
	private Date getTestDate() {
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(2011, Calendar.SEPTEMBER, 8, 23, 45);
		
		Date testDate = cal.getTime();
		
		return testDate;
		
	}

}
