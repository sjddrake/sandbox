package uk.co.neo9.apps.accounts;

import uk.co.neo9.Neo9EnvironmentConstants;

public class AccountsBatchProcessorRunner {
	

	final static String rootfolder = Neo9EnvironmentConstants.DROPBOX_FOLDER+"Kathie and Simon/accounts";
	
	public static void main(String[] args) {
		
		
		String chequesLookupFile = rootfolder+"/_ WIP/cheques.xls";
		ChequeDetailsLookup.setAccountsLookupFileName(chequesLookupFile);
		
		
//		CreditCardBatchProcessor.main(getArgsForTescoCC1());

		CreditCardBatchProcessor.main(getArgsForCombinedBatch());
	}

	
	private static String[] getArgsForTescoCC1(){

		
		String[] readerArgs = {rootfolder+"\\temp\\ccBatch"};
		       
		return readerArgs;
	}	
	

	private static String[] getArgsForBarclaysCSVExport(){
		

		// String rootfolder = "/Users/simondrake/Dropbox/Kathie and Simon/accounts";
		String[] readerArgs = {rootfolder+"/temp/barclaysExport"};
		
		return readerArgs;
	}	

	private static String[] getArgsForCombinedBatch(){
		

		// String rootfolder = "/Users/simondrake/Dropbox/Kathie and Simon/accounts";
//		String[] readerArgs = {rootfolder+"/temp/2012"};
//		String[] readerArgs = {rootfolder+"/temp/test"};
//		String[] readerArgs = {rootfolder+"/temp/temp"};		
//		String[] readerArgs = {rootfolder+"/Accounts - 2012/07. Jul 2012"};	
		
		String[] readerArgs = {rootfolder+"/_ WIP/_Transactions"};
		
		return readerArgs;
	}	
}
