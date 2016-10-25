package uk.co.neo9.utilities;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;


public class CommonConstantsTestCase {
	
	
	@Test
	public void test_getMonthIdFromName(){
		
		int monthId = CommonConstants.getMonthIdFromName(null);
		assertEquals(CommonConstants.UNDEFINED,monthId);
		
		monthId = CommonConstants.getMonthIdFromName("");
		assertEquals(CommonConstants.UNDEFINED,monthId);
		
		monthId = CommonConstants.getMonthIdFromName("Dont be sully");
		assertEquals(CommonConstants.UNDEFINED,monthId);
		
		monthId = CommonConstants.getMonthIdFromName("mar");
		assertEquals(Calendar.MARCH,monthId);
		
		
		monthId = CommonConstants.getMonthIdFromName("Apr");
		assertEquals(Calendar.APRIL,monthId);
		
		monthId = CommonConstants.getMonthIdFromName("January");
		assertEquals(Calendar.JANUARY,monthId);
		
		
		monthId = CommonConstants.getMonthIdFromName("jUlY");
		assertEquals(Calendar.JULY,monthId);
		
		monthId = CommonConstants.getMonthIdFromName("OCT");
		assertEquals(Calendar.OCTOBER,monthId);
		
		monthId = CommonConstants.getMonthIdFromName("deCEMber");
		assertEquals(Calendar.DECEMBER,monthId);
		
		monthId = CommonConstants.getMonthIdFromName("fEB");
		assertEquals(Calendar.FEBRUARY,monthId);
		
	}

}
