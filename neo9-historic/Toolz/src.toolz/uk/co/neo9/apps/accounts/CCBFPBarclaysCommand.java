package uk.co.neo9.apps.accounts;


public class CCBFPBarclaysCommand extends CreditCardBFPBaseCommand {

	public CCBFPBarclaysCommand() {
		super();
		setInputFileType(AccountsDataModel.IFT_BARCLAYS_SCREEN_SCRAPE);
	}
}
