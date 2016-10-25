package uk.co.neo9.apps.accounts;

import uk.co.neo9.utilities.file.BFPCommandVendorBase;
import uk.co.neo9.utilities.file.IBFPCommand;

public class CreditCardBFPCommandVendor extends BFPCommandVendorBase {
	
	private final static String CC_CSV_EXPORT = "CC EXPORT";
	private final static String ADOBE_SCRAPE = "ADOBE SCRAPE";
	private final static String CHROME_PDF_SCRAPE = "CHROME PDF SCRAPE";
	private final static String SCREEN_SCRAPE = "SCREEN SCRAPE";
	private final static String TABBED = "TABBED";
	private final static String BARCLAYS = "BARCLAYS";
	private final static String EXPORT = "EXPORT";
	private final static String JOINT = "JOINT";
	private final static String ACCOUNT = "ACCOUNT";
	private final static String CASH = "CASH";
	
	private String targetFolder = null;

	public IBFPCommand getCommand(String fileName) {

		CreditCardBFPBaseCommand command = null;
		
		if (fileName != null) {
	
			String inputFileNameText = fileName.toUpperCase();
			
			if (inputFileNameText.startsWith("TRAK") || inputFileNameText.startsWith("PROCESSED")) {
				// ignore these ones
				
			} else if (inputFileNameText.indexOf(ADOBE_SCRAPE) != -1) {
				CCBFPAdobeCommand adobe = new CCBFPAdobeCommand();
				command = adobe;
				
			} else if (inputFileNameText.indexOf(SCREEN_SCRAPE) != -1) {
				CCBFPScreenCommand screen = new CCBFPScreenCommand();
				command = screen;
				
			} else if (inputFileNameText.indexOf(CHROME_PDF_SCRAPE) != -1) {
				CCBFPChromePDFCommand screen = new CCBFPChromePDFCommand();
				command = screen;
										
			} else if (inputFileNameText.indexOf(TABBED) != -1) {
				CCBFPTabbedCommand screen = new CCBFPTabbedCommand();
				command = screen;
				
			} else if ((inputFileNameText.indexOf(BARCLAYS) != -1) && (inputFileNameText.indexOf(EXPORT) != -1)) {
				BarclaysCSVExportCommand barclaysCSV = new BarclaysCSVExportCommand();
				int accountID = determineAccountIDFromFileDetails(inputFileNameText);
				barclaysCSV.setFocusAccountId(accountID); 
				command = barclaysCSV;
				
			} else if ((inputFileNameText.indexOf(JOINT) != -1) && (inputFileNameText.indexOf(ACCOUNT) != -1)) {
				CCBFPBarclaysCommand barclays = new CCBFPBarclaysCommand();
				command = barclays;
				
			} else if (inputFileNameText.indexOf(CC_CSV_EXPORT) != -1) {
				CCBFPCreditCardCSVExportCommand adobe = new CCBFPCreditCardCSVExportCommand();
				command = adobe;
				
			} else if (inputFileNameText.indexOf(CASH) != -1) {
				CCBFPCashScrapBookCommand adobe = new CCBFPCashScrapBookCommand();
				command = adobe;
				
			} 
		}
		
		if (command != null) {
			command.setCommandInput(fileName);
			command.setTargetFolder(targetFolder);
		}
		
		return command;
	}
	
	
	private int determineAccountIDFromFileDetails(String fileDetails){
		int accountID = 0;
		// TODO should determine this from the filename!!!
		accountID = AccountsDataModel.ACC_ID_JOINT_CURRENT;
		return accountID;
	}
	
	
	public String getTargetFolder() {
		return targetFolder;
	}


	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}
}
