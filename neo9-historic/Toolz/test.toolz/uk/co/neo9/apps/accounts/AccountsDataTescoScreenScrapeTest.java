package uk.co.neo9.apps.accounts;

import junit.framework.TestCase;

public class AccountsDataTescoScreenScrapeTest extends TestCase {

	public AccountsDataTescoScreenScrapeTest(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void test_extractFirstLineData_1(){
		
		String trackerParamId = OutputParameterModel.getTrackerParamId();
		OutputParameterModel trackerOM = OutputParameterModel.getParams(trackerParamId);
		System.out.println(trackerOM.getOutputCSVHeader());
		
		
		AccountsDataTescoScreenScrape model = new AccountsDataTescoScreenScrape();
		
		model._Line1 = "21 May 2008  CLARKS SHOE DEPT. £14.00 ";
		
		model.extractFirstLineData();
		
		boolean extended = false;
		String output = model.buildTrackerOutputText(extended);
		
		System.out.println(output);
		
	}

}
