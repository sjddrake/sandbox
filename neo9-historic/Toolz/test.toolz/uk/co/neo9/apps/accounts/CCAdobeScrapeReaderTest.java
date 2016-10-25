package uk.co.neo9.apps.accounts;

import uk.co.neo9.test.Neo9TestingConstants;
import junit.framework.TestCase;

public class CCAdobeScrapeReaderTest extends TestCase {
	
/*
	public void test_FullReader() throws Exception {
		
		CreditCardDataReader reader = new CreditCardDataReader();
		
		String accountsDataFileName = "cc - adobe scrape.txt";
		String workingFolder = Neo9TestingConstants.ROOT_TEST_FOLDER+"accounts/";
		
//		CCBFPBarclaysCommand command = new CCBFPBarclaysCommand();
//		command.setTargetFolder(workingFolder);
//		command.setInputFileName(accountsDataFileName);
//		
//		reader.processCommand(command);
		
		String[] args = {"SINGLEMODE",accountsDataFileName,"output.csv",workingFolder,"aDobe"};
		
		reader.go(args);	
	}
*/
	
	public void test_AdobeScrape_Long() throws Exception {
		
		String testline = "23 JAN 24 JAN 38184407 INMOBILIARIA ALCANADA ALCUDIA 074 399.25 EUR EXCHANGE RATE 1.168867 341.57";
		AdobeScrapeTestParams params = new AdobeScrapeTestParams(testline);
		executeAdobeScrapeTest(params);
	}

	public void test_AdobeScrape_Refund() throws Exception {
		
		String testline = "09 JAN 11 JAN 00440436 MONSOON/ACCZ LTD STAINES1264 GBR 25.00 -";
		AdobeScrapeTestParams params = new AdobeScrapeTestParams(testline);
		executeAdobeScrapeTest(params);
	}
	
	private void executeAdobeScrapeTest(AdobeScrapeTestParams params) throws Exception {
		
		String testline = params.textLine;
		AccountsDataTescoAdobeScrape model = new AccountsDataTescoAdobeScrape();
		model.addDataLine(testline);
		model.processData();
		
		OutputParameterModel outputModel = new OutputParameterModel();
		String output = model.output(outputModel );
		
		System.out.println(output);
		
	}
	
	
	private class AdobeScrapeTestParams {
		
		String textLine;
		
		public AdobeScrapeTestParams(){
			
		}

		public AdobeScrapeTestParams(String text){
			textLine = text;
		}		
		
	}
}
