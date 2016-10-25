package uk.co.neo9.utilities.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import uk.co.neo9.utilities.CommonConstants;

public class BFPApplicationBase {
	
	private IBFPCommandVendor commandVendor = null;
	private String targetFolder = null;

	public BFPApplicationBase() {
		super();
	}

	
	public BFPApplicationBase(IBFPCommandVendor commandVendor) {
		super();
		this.commandVendor = commandVendor;
	}




	public void go(){
		
		// first build up the list of input details
		// used to create the 'command' instances
		List commandInputs = determineCommandInputs();
		
		// create a command for each of the items listed
		List commands = createCommandList(commandInputs);
		
		// process the commands
		processCommands(commands);
		
		
		// output what we've done
		StringBuilder buff = new StringBuilder();
		buff.append("Succesfully processed the following files:");
		buff.append(CommonConstants.NEWLINE);
		for (Object instructions : commandInputs) {
			buff.append("> "+instructions + CommonConstants.NEWLINE);
		}
		buff.append(CommonConstants.NEWLINE);
		
		buff.append("Using the following commands:");
		buff.append(CommonConstants.NEWLINE);
		for (Object command : commands) {
			buff.append("> "+command + CommonConstants.NEWLINE);
		}
		
		System.out.println(buff);
	}
	
	
	protected List determineCommandInputs(){
		// default behaviour is to use the file names as inputs
		return listTargetDirectoryContents();
	}
	
	
	protected List listTargetDirectoryContents(){
		String[] fileList = FileServer.listFolderContents(getTargetFolder());
		List contents = Arrays.asList(fileList);
		return contents;
	}
	
	protected List createCommandList(List commandInputs){
		IBFPCommandVendor vendor = getCommandVendor();
		return vendor.createCommandList(commandInputs);
	}
	
	protected void processCommands(List commands){
		for (Iterator iter = commands.iterator(); iter.hasNext();) {
			IBFPCommand command = (IBFPCommand) iter.next();
			command.execute();
		}
	}
	
	
	// ================= getters & setters ====================
	
	public IBFPCommandVendor getCommandVendor() {
		return commandVendor;
	}

	public void setCommandVendor(IBFPCommandVendor commandVendor) {
		this.commandVendor = commandVendor;
	}


	public String getTargetFolder() {
		return targetFolder;
	}


	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}
}
