package uk.co.neo9.apps.accounts;

import uk.co.neo9.apps.accounts.wiptracker.WIPTrackerTotalsComparator;
import uk.co.neo9.apps.accounts.wiptracker.WIPTrackerTotalsModel;
import junit.framework.TestCase;

public class WIPTrackerTotalsComparatorTest extends TestCase {

	WIPTrackerTotalsComparator comparator = new WIPTrackerTotalsComparator();
	
	public static void main(String[] args) {
	}

	/*
	 * Test method for 'uk.co.neo9.apps.accounts.WIPTrackerTotalsComparator.initialiseFieldToUse()'
	 */
	public void test_InitialiseFieldToUse() {

		int expectedFieldToUse = WIPTrackerTotalsModel.FID_CATEGORY;
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



}
