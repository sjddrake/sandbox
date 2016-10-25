package uk.co.neo9.apps.accounts;

import uk.co.neo9.test.Neo9TestingConstants;
import junit.framework.TestCase;

public class AccountsDataBarclaysCSVExportTest extends TestCase {
	
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
		
		String testline = " ,08/05/2012,20-76-89 60338109,-18.00,PAYMENT,BFI IMAX              REF 124 7736027651 BCC";
		AdobeScrapeTestParams params = new AdobeScrapeTestParams(testline);
		executeAdobeScrapeTest(params);
	}

	
	public void test_AdobeScrape_Cheque() throws Exception {
		
		String testline = "100351,30/04/2012,20-48-42 90518697,-18,CHQ,100351";
		AdobeScrapeTestParams params = new AdobeScrapeTestParams(testline);
		executeAdobeScrapeTest(params);
	}	

	public void test_AdobeScrape_DD() throws Exception {
		
		String testline = " ,23/04/2012,20-48-42 90518697,-22.25,DIRECTDEBIT,T-MOBILE              M84515401367218446 DDR";
		AdobeScrapeTestParams params = new AdobeScrapeTestParams(testline);
		executeAdobeScrapeTest(params);
	}		
	
	
	public void test_AdobeScrape_Cash() throws Exception {
		
		String testline = " ,16/04/2012,20-48-42 90518697,-30,CASH,TESCO                 TESCO ADDLESTONE      ";
		AdobeScrapeTestParams params = new AdobeScrapeTestParams(testline);
		executeAdobeScrapeTest(params);
	}		
	

	public void test_AdobeScrape_DirectDeposit() throws Exception {
		
		String testline = " ,12/04/2012,20-48-42 90518697,87.22,DIRECTDEP,K Drake               J'S EARS           BGC";
		AdobeScrapeTestParams params = new AdobeScrapeTestParams(testline);
		executeAdobeScrapeTest(params);
	}
	
	
//	public void test_AdobeScrape_Refund() throws Exception {
//		
//		String testline = "09 JAN 11 JAN 00440436 MONSOON/ACCZ LTD STAINES1264 GBR 25.00 -";
//		AdobeScrapeTestParams params = new AdobeScrapeTestParams(testline);
//		executeAdobeScrapeTest(params);
//	}
	
	private void executeAdobeScrapeTest(AdobeScrapeTestParams params) throws Exception {
		
		String rootfolder = "/Users/simondrake/Dropbox/Kathie and Simon/accounts";
		String chequesLookupFile = rootfolder+"/_ WIP/cheques.xls";
		ChequeDetailsLookup.setAccountsLookupFileName(chequesLookupFile);
		
		String testline = params.textLine;
		AccountsDataBarclaysCSVExport model = new AccountsDataBarclaysCSVExport();
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
