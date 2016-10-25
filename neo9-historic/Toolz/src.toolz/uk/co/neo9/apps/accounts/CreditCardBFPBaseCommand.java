package uk.co.neo9.apps.accounts;

import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.file.BFPCommandBase;

public class CreditCardBFPBaseCommand extends BFPCommandBase {

	private String targetFolder = null;
	private int inputFileType = -1;
	
		
	public CreditCardBFPBaseCommand() {
		super();
	}


	public void execute() {

		System.out.println(getTargetFileDetails());
		
		AccountsDataReader reader = new AccountsDataReader();
		reader.processCommand(this);
		
		
	}
	
	
	public String getTargetFileDetails() {
		String targetFileDetails = targetFolder + "/" + getInputFileName();
		return targetFileDetails;
	}
	
	public String getInputFileName() {
		return getCommandInput();
	}
	
	
	public void setInputFileName(String fileName){
		setCommandInput(fileName);
	}
	
	
	public String getTargetFolder() {
		return targetFolder;
	}

	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}


	public int getInputFileType() {
		return inputFileType;
	}


	public void setInputFileType(int inputFileType) {
		this.inputFileType = inputFileType;
	}
	
	@Override
	public String toString(){
		StringBuilder buff =  new StringBuilder(super.toString());
		buff.append(CommonConstants.NEWLINE);
		buff.append("- inputFileType: "+getInputFileType());
		return buff.toString();
	}


	public boolean getUseExtendedFieldsInTracker() {
		// hardcoding to use the sub-cat field
		return true;
	}
}
