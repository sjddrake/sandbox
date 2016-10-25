package uk.co.neo9.apps.accounts;


public class CCBFPScreenCommand extends CreditCardBFPBaseCommand {

	public CCBFPScreenCommand() {
		super();
		setInputFileType(AccountsDataModel.IFT_CC_SCREEN_SCRAPE);
	}
}
