package uk.co.neo9.utilities;

import junit.framework.TestCase;

public class UtilitiesComparatorPlusAdapterTestCase extends TestCase {

	
	int DEFAULT_FIELD_TO_USE = 10;
	
	UtilitiesComparatorPlusAdapter comparator = new AdapterImpl();
	
	public static void main(String[] args) {
	}

	/*
	 * Test method for 'uk.co.neo9.apps.accounts.WIPTrackerTotalsComparator.initialiseFieldToUse()'
	 */
	public void test_InitialiseFieldToUse() {

		int expectedFieldToUse = DEFAULT_FIELD_TO_USE;
		comparator.initialiseFieldToUse();
		int fieldToUse = comparator.getFieldToUseId();
		assertEquals("fieldToUse - initialised",expectedFieldToUse,fieldToUse);
		
		int testFeidlToUse = 666;
		comparator.setFieldToUseId(testFeidlToUse);
		fieldToUse = comparator.getFieldToUseId();
		assertEquals("fieldToUse - overwritten",testFeidlToUse,fieldToUse);
		
		comparator.initialiseFieldToUse();
		fieldToUse = comparator.getFieldToUseId();
		assertEquals("fieldToUse - re-initialised",expectedFieldToUse,fieldToUse);
	}


	/*
	 * Test method for 'uk.co.neo9.apps.accounts.WIPTrackerTotalsComparator.compare(Object, Object)'
	 */
	public void test_CompareObjectObject() {

	}

	/*
	 * Test method for 'uk.co.neo9.apps.accounts.WIPTrackerTotalsComparator.compare(Object, Object, String)'
	 */
	public void test_CompareObjectObjectString() {

	}

	/*
	 * Test method for 'uk.co.neo9.apps.accounts.WIPTrackerTotalsComparator.compareFields(Date, Date)'
	 */
	public void test_CompareFieldsDateDate() {

	}

	/*
	 * Test method for 'uk.co.neo9.apps.accounts.WIPTrackerTotalsComparator.compareFields(int, int)'
	 */
	public void test_CompareFieldsIntInt_1() {
		int lower = 1;
		int higher = 12;
		
		int result = comparator.compareFields(lower,higher);
		assertTrue("lower is less than higher",lower < higher);
		assertTrue("lower is less than higher",result < 0);
		
		result = comparator.compareFields(higher, lower);
		assertTrue("higher is NOT less than lower",(higher < lower) == false);
		assertTrue("higher is NOT less than lower",result > 0);		
	}

	
	public void test_CompareFieldsIntInt_2() {
		
		int int0 = 12;
		int int1 = 12;
		
		int result = comparator.compareFields(int0,int1);
		assertTrue("test data is equal",int0 == int1);
		assertTrue("comparator did NOT find them equal",result == 0);
		

	}	
	
	
	public void test_CompareFieldsIntInt_3() {
		int lower = -5;
		int higher = 12;
		
		int result = comparator.compareFields(lower,higher);
		assertTrue("lower is less than higher",lower < higher);
		assertTrue("lower is less than higher",result < 0);
		
		result = comparator.compareFields(higher, lower);
		assertTrue("higher is NOT less than lower",(higher < lower) == false);
		assertTrue("higher is NOT less than lower",result > 0);		
	}	

	public void test_CompareFieldsIntInt_4() {
		int lower = -15;
		int higher = -12;
		
		int result = comparator.compareFields(lower,higher);
		assertTrue("lower is less than higher",lower < higher);
		assertTrue("lower is less than higher",result < 0);
		
		result = comparator.compareFields(higher, lower);
		assertTrue("higher is NOT less than lower",(higher < lower) == false);
		assertTrue("higher is NOT less than lower",result > 0);		
	}		
	
	/*
	 * Test method for 'uk.co.neo9.apps.accounts.WIPTrackerTotalsComparator.compareFields(String, String)'
	 */
	public void test_CompareFieldsStringString() {

	}

	/*
	 * Test method for 'uk.co.neo9.apps.accounts.WIPTrackerTotalsComparator.compareForNullValues(Object, Object)'
	 */
	public void test_CompareForNullValues() {

	}

	
	private class AdapterImpl extends UtilitiesComparatorPlusAdapter {

		@Override
		public int compare(Object o1, Object o2, int fieldToUse) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void initialiseFieldToUse() {
			setFieldToUseId(DEFAULT_FIELD_TO_USE);
			
		}
		
	}
	
}
