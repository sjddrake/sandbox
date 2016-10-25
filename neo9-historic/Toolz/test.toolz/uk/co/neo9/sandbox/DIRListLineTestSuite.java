/***********************************************************************
* This source code is the property of Lloyds TSB Group PLC.
* All Rights Reserved.
*
* Author(s): LTSB generated code
*
* Date: Auto Generated
*
***********************************************************************/
package	uk.co.neo9.sandbox; 

import junit.framework.*;

public final class DIRListLineTestSuite{
	
	/**                                                                    
	* Hide default constructor
	*/
	public DIRListLineTestSuite(){
		//Hide constructor
	}
	 		
	/**                                                                    
	* Default main method
	*
	* @param args Input params
	*/
//	public static void main(String[] args) {
//		junit.textui.TestRunner.run(DIRListLineTestSuite.class);
//	}

	/**                                                                    
	* Construct the TestSuite
	*
	* @return Test Newly created TestSuite
	*/
	public static Test suite() {

		TestSuite suite =
			new TestSuite("Test for DIR List Line Application");
		//$JUnit-BEGIN$
						
			suite.addTest(new TestSuite(ArchiveComparisonApplicationTestCase.class));
			suite.addTest(new TestSuite(DIRListLineComparatorTestCase.class));
			// no test!!! suite.addTest(new TestSuite(DIRListLineHelperTestCase.class));
			suite.addTest(new TestSuite(DIRListLineMatchTestCase.class));
			suite.addTest(new TestSuite(DIRListLineTestCase.class));
		//$JUnit-END$
		return suite;
	}
}	

