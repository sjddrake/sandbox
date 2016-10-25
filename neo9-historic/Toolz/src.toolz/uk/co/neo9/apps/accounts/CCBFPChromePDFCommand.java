package uk.co.neo9.apps.accounts;


public class CCBFPChromePDFCommand extends CreditCardBFPBaseCommand {

	public CCBFPChromePDFCommand() {
		super();
		setInputFileType(AccountsDataModel.IFT_CC_CHROME_SCRAPE);
	}
}
