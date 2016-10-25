/***********************************************************************
* This source code is the property of Lloyds TSB Group PLC.
* All Rights Reserved.
*
* Author(s): LTSB generated code
*
* Date: Auto Generated
*
***********************************************************************/
package	uk.co.neo9.apps.accounts; 

import uk.co.neo9.apps.accounts.wiptracker.WIPTrackerCategoryContainerTest;
import uk.co.neo9.apps.accounts.wiptracker.WIPTrackerTotalsGeneratorTest;
import uk.co.neo9.apps.accounts.wiptracker.WIPTrackerExtendedWorksheetTest;
import junit.framework.*;

public final class WIPTrackerTotalsTestSuite{
	
	/**                                                                    
	* Hide default constructor
	*/
	public WIPTrackerTotalsTestSuite(){
		//Hide constructor
	}
	 		
	/**                                                                    
	* Default main method
	*
	* @param args Input params
	*/
//	public static void main(String[] args) {
//		junit.textui.TestRunner.run(WIPTrackerTotalsTestSuite.class);
//	}

	/**                                                                    
	* Construct the TestSuite
	*
	* @return Test Newly created TestSuite
	*/
	public static Test suite() {

		TestSuite suite =
			new TestSuite("Test for WIP Tracker Totals Generator Application");
		//$JUnit-BEGIN$
						
			suite.addTest(
				new TestSuite(WIPTrackerTotalsComparatorTest.class));
							
			suite.addTest(
				new TestSuite(WIPTrackerTotalsGeneratorTest.class));
						
			suite.addTest(
					new TestSuite(WIPTrackerCategoryContainerTest.class));
	
			suite.addTest(
					new TestSuite(WIPTrackerExtendedWorksheetTest.class));			
		//$JUnit-END$
		return suite;
	}
}	

