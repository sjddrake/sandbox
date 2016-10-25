package uk.co.neo9.apps.accounts;


public class CCBFPCreditCardCSVExportCommand extends CreditCardBFPBaseCommand {

//	private int focusAccountId;
	
	public CCBFPCreditCardCSVExportCommand() {
		super();
		setInputFileType(AccountsDataModel.IFT_CC_CSV_EXPORT);
	}

//	
//	public void setInputFileName(String fileName){
//		super.setInputFileName(fileName);
//		initFocusAccountFromInputFilename();
//	}
//	
//	
//	private void initFocusAccountFromInputFilename() {
//		System.out.println("initFocusAccountFromInputFilename() has not been fully coded yet!!!");
//		// intent is to interrogate the filename and set the type from that
//		// see CreditCardBFPCommandVendor.getCommand()
//		setFocusAccountId(AccountsDataModel.ACC_ID_JOINT_CURRENT);
//	}
//
//
//	public void setFocusAccountId(int focusAccountId) {
//		this.focusAccountId = focusAccountId;
//	}
//
//	public int getFocusAccountId() {
//		return focusAccountId;
//	}
//	
	
}
