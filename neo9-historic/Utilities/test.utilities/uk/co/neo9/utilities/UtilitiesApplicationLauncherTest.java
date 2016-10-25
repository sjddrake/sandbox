package uk.co.neo9.utilities;

import junit.framework.TestCase;

public class UtilitiesApplicationLauncherTest extends TestCase {

	
	public void test1() {
		
		/*
		 * Not sure the approach yet either:
		 * 
		 * 1) Central launcher, apps register with that
		 * 2) launcher module is contained by app's static main
		 * 
		 * 
		 * ... this test case is for prototyping the approach
		 * 
		 *  - TRY 2) first!
		 * 
		 */
		
		UtilitiesLaunchableTestApp.main(null);
		
		
		
	}

	
	public void test_withArguments() {

		String[] args = {"C:/temp","batchMode","yes"};
		
		UtilitiesLaunchableTestApp.main(args);
		
		
		
	}
	
}
