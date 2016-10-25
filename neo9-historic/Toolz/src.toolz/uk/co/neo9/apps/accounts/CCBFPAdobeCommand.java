package uk.co.neo9.apps.accounts;


public class CCBFPAdobeCommand extends CreditCardBFPBaseCommand {

	public CCBFPAdobeCommand() {
		super();
		setInputFileType(AccountsDataModel.IFT_CC_ADOBE_SCRAPE);
	}

}
