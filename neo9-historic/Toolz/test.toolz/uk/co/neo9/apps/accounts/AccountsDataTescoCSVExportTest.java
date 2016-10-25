package uk.co.neo9.apps.accounts;

import junit.framework.TestCase;

public class AccountsDataTescoCSVExportTest extends TestCase {

	/*
	 * The format of the data line is:
	 * 
	 *	 Transaction Date, Posting Date, Billing Amount, Merchant, Merchant City , Merchant State , Merchant Zip , Reference Number , Debit/Credit Flag , SICMCC Code
	 *	 21/10/2012,22/10/2012,£3.95,"COSTA COFFEE","STAINES",,TW18 4WB,55541962296544000080771,D,5812 
	 * 
	 */	
	
	
	public void testExtractFirstLineData() {

		String trackerParamId = OutputParameterModel.getTrackerParamId();
		OutputParameterModel trackerOM = OutputParameterModel.getParams(trackerParamId);
		System.out.println(trackerOM.getOutputCSVHeader());
		
		
		AccountsDataTescoCSVExport model = new AccountsDataTescoCSVExport();
		
		model._Line1 = "21/10/2012,22/10/2012,£3.95,COSTA COFFEE,STAINES,,TW18 4WB,55541962296544000080771,D,5812 ";
		
		model.extractFirstLineData();
		
		boolean extended = false;
		String output = model.buildTrackerOutputText(extended);
		
		System.out.println(output);
		
	}

}
