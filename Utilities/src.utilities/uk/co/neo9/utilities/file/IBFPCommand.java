package uk.co.neo9.utilities.file;

public interface IBFPCommand {
	public String getCommandInput();
	public void setCommandInput(String commandInput);
	public void execute();
}
