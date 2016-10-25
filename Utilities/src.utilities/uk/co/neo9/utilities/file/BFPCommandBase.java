package uk.co.neo9.utilities.file;

import uk.co.neo9.utilities.CommonConstants;

public class BFPCommandBase implements IBFPCommand {

	private String commandInput = null;

	public String getCommandInput() {
		return commandInput;
	}

	public void setCommandInput(String commandInput) {
		this.commandInput = commandInput;
	}

	public void execute() {

		System.out.println(getCommandInput());
	}
	
	@Override
	public String toString(){
		return "> Command instance: " + this.getClass().getSimpleName()
				+ CommonConstants.NEWLINE
		 		+ "- commandInput: "+getCommandInput();
	}
}
