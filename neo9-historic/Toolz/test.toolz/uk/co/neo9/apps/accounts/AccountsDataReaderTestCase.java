package uk.co.neo9.apps.accounts;

import org.junit.Test;

import uk.co.neo9.test.Neo9TestingConstants;

public class AccountsDataReaderTestCase {

//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//
////		 testSingleFile();
////		 testBatchMode();
//		 testCommandInput();
//	}
	
	@Test
	public void testSingleFile() {

		AccountsDataReader reader = new AccountsDataReader();
		
		String accountsDataFileName = "TestMonth.txt";
		String processedAccountsFileName = "TestMonth.csv";
		String workingFolder = Neo9TestingConstants.ROOT_TEST_FOLDER+"accounts";
		
		String[] testArgs = {accountsDataFileName,processedAccountsFileName,workingFolder};
		
		reader.go(testArgs);

	}
	
	@Test
	public void testBatchMode() {

		AccountsDataReader reader = new AccountsDataReader();
		
		String mode = AccountsDataReader.MODE_BATCH;
		String accountsDataFileName = "TestBatch.txt";
		String processedAccountsFileName = "DUMMY_VALUE_NOT_NEEDED";
		String workingFolder = Neo9TestingConstants.ROOT_TEST_FOLDER+"accounts";
		
		String[] testArgs = {mode,accountsDataFileName,processedAccountsFileName,workingFolder};
		
		reader.go(testArgs);

	}

	@Test
	public void testCommandInput() {

		AccountsDataReader reader = new AccountsDataReader();
		
		String accountsDataFileName = "barclays joint account data.txt";
		String workingFolder = Neo9TestingConstants.ROOT_TEST_FOLDER+"accounts/ccbatch";
		
		CCBFPBarclaysCommand command = new CCBFPBarclaysCommand();
		command.setTargetFolder(workingFolder);
		command.setInputFileName(accountsDataFileName);
		
		reader.processCommand(command);

	}
	
	

	
}
