package uk.co.neo9.apps.accounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

public class AccountsDataModelComparatorTestCase extends TestCase {

	public AccountsDataModelComparatorTestCase(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testCompare_alreadyOrdered_two() {
						
		String[] testDates = {"27 NOV 2008","14 DEC 2008"};
		String correctOrder = "1,2";
		
		boolean result = doTheTest(testDates, correctOrder);
		
		if (result == false) {
			fail("wrong order in result"); 
		}
	}

	public final void testCompare_unOrdered_two() {
		
		String[] testDates = {"14 DEC 2008","27 NOV 2008"};
		String correctOrder = "2,1";
		
		boolean result = doTheTest(testDates, correctOrder);
		
		if (result == false) {
			fail("wrong order in result"); 
		}
	}
	
	
	public final void testCompare_unOrdered_four() {
		
		String[] testDates = {"14 DEC 2008","27 NOV 2008", "09 DEC 2008", "09 APR 2008"};
		String correctOrder = "4,2,3,1";
		
		boolean result = doTheTest(testDates, correctOrder);
		
		if (result == false) {
			fail("wrong order in result"); 
		}
	}
	
	private boolean doTheTest(String[] testDates, String correctOrder){
		
		List datesList = getTestData(testDates);
		
		Collections.sort(datesList, new AccountsDataModelComparator());
		
		boolean result = checkResult(datesList,correctOrder);
		
		return result;
		
	}
	
	
	private List getTestData(String[] testDates){
		
		List datesList = new ArrayList();
		
		for (int i = 0; i < testDates.length; i++) {
			
			String dateText = testDates[i];
			
			AccountsDataModel model = new AccountsDataModel();
			model._DateText = dateText;
			model._Reference = new Integer(i+1).toString();
			model.setupDateFromData();
			
			datesList.add(model);
		}
		
		return datesList;
		
	}
	
	
	private boolean checkResult(List result, String correctOrder){
		
		boolean pass = false;
		
		StringBuffer buf = new StringBuffer();
		for (Iterator iter = result.iterator(); iter.hasNext();) {
			AccountsDataModel model = (AccountsDataModel) iter.next();
			buf.append(model._Reference);
			if (iter.hasNext()) {
				buf.append(",");
			}
		}
		
		if (correctOrder.compareTo(buf.toString()) == 0) {
			pass=true;
		}
		
		return pass;
	}
	
}
