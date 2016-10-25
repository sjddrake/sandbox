package uk.co.neo9.apps.accounts;


public class CCBFPCashScrapBookCommand extends CreditCardBFPBaseCommand {

	public CCBFPCashScrapBookCommand() {
		super();
		setInputFileType(AccountsDataModel.IFT_CASH_SCRAPBOOK);
	}
}
