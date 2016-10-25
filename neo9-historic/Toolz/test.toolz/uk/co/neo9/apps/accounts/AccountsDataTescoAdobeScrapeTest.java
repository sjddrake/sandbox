package uk.co.neo9.apps.accounts;

import junit.framework.TestCase;

public class AccountsDataTescoAdobeScrapeTest extends TestCase {

	public void testExtractFirstLineData() {

		String trackerParamId = OutputParameterModel.getTrackerParamId();
		OutputParameterModel trackerOM = OutputParameterModel.getParams(trackerParamId);
		System.out.println(trackerOM.getOutputCSVHeader());
		
		
		AccountsDataTescoAdobeScrape model = new AccountsDataTescoAdobeScrape();
		
		model._Line1 = "01 SEP 02 SEP 09447700 STAGECOACH SOUTH W       ADLSTONE 4735GBR 12.80";
		
		model.extractFirstLineData();
		
		boolean extended = false;
		String output = model.buildTrackerOutputText(extended);
		
		System.out.println(output);
		
	}

}
