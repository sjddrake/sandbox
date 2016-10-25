package uk.co.neo9.apps.accounts;


public class CCBFPTabbedCommand extends CreditCardBFPBaseCommand {

	public CCBFPTabbedCommand() {
		super();
		setInputFileType(AccountsDataModel.IFT_CC_TABBED);
	}
}
