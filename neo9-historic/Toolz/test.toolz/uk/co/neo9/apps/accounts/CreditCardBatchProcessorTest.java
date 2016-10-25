package uk.co.neo9.apps.accounts;

import uk.co.neo9.test.Neo9TestingConstants;

public class CreditCardBatchProcessorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		runKosherBatch2008();
		runKosherBatch2009();
		
//		testBatch();
//		testJointAccountDataCommand();
//		testCCBFPScreenCommand();
//		testCCBFPAdobeCommand();
		
//		testBlankLineAtEndOfScreenScrape();
	}
	
	
	public static void runKosherBatch2008() {
		String targetFolder = "D:\\simonz\\My Documents\\Accounts\\2008\\TescosCCBatch";
		CreditCardBatchProcessor.runBatchProcessor(targetFolder);
	}
	
	public static void runKosherBatch2009() {
		String targetFolder = "D:\\simonz\\My Documents\\Accounts\\2009\\TescosCCBatch";
		CreditCardBatchProcessor.runBatchProcessor(targetFolder);
	}
	
	public static void testBatch() {
		String targetFolder = Neo9TestingConstants.ROOT_TEST_FOLDER+"accounts/ccBatch";
		CreditCardBatchProcessor.runBatchProcessor(targetFolder);
	}

	public static void testCCBFPAdobeCommand() {
		
		String accountsDataFileName = "Tescos CC December 2008 - adobe scrape.txt";
//		String accountsDataFileName = "test - adobe scrape.txt";
		String workingFolder = Neo9TestingConstants.ROOT_TEST_FOLDER+"accounts/ccbatch";
		
		CCBFPAdobeCommand command = new CCBFPAdobeCommand();
		command.setTargetFolder(workingFolder);
		command.setInputFileName(accountsDataFileName);

		command.execute();
	}
	
	public static void testCCBFPScreenCommand() {
		
		String accountsDataFileName = "tesco cc may 2008 - screen scrape.txt";
		String workingFolder = Neo9TestingConstants.ROOT_TEST_FOLDER+"accounts/ccbatch";
		
		CCBFPScreenCommand command = new CCBFPScreenCommand();
		command.setTargetFolder(workingFolder);
		command.setInputFileName(accountsDataFileName);

		command.execute();
	}
	
	public static void testBlankLineAtEndOfScreenScrape() {
		
		String accountsDataFileName = "blank line - screen scrape.txt";
		String workingFolder = Neo9TestingConstants.ROOT_TEST_FOLDER+"accounts/ccbatch";
		
		CCBFPScreenCommand command = new CCBFPScreenCommand();
		command.setTargetFolder(workingFolder);
		command.setInputFileName(accountsDataFileName);

		command.execute();
	}
	
	public static void testJointAccountDataCommand() {
			
		String accountsDataFileName = "barclays joint account data.txt";
		String workingFolder = Neo9TestingConstants.ROOT_TEST_FOLDER+"accounts/ccbatch";
		
		CCBFPBarclaysCommand command = new CCBFPBarclaysCommand();
		command.setTargetFolder(workingFolder);
		command.setInputFileName(accountsDataFileName);

		command.execute();
	}
}
