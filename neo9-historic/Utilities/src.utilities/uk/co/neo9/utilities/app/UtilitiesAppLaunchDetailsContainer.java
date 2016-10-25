package uk.co.neo9.utilities.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UtilitiesAppLaunchDetailsContainer {
	
	String quickLaunchFolder = null;
	String quickLaunchFile = null;
	String argumentsHelpText = null;
	List<UtilitiesAppLaunchDetails> arguments = new ArrayList<UtilitiesAppLaunchDetails>();



	public List<UtilitiesAppLaunchDetails> getArguments() {
		return arguments;
	}

	public void clearArguments() {
		this.arguments.clear();
	}
	
	public void addArgument(UtilitiesAppLaunchDetails argument) {
		this.arguments.add(argument);
	}
	

	public String getArgumentsHelpText() {
		return argumentsHelpText;
	}

	public void setArgumentsHelpText(String argumentsHelpText) {
		this.argumentsHelpText = argumentsHelpText;
	}


	public boolean validateQuickLaunchArgs(){
		
		//TODO
		
		return true;
	}

	public String getQuickLaunchFileDetails() {
		
		// extract the relevant details
		initialiseQuickLaunch();
		
		// TODO Don't yet know all the combinations!!!
		
		
		String filedetails = quickLaunchFolder+"/"+quickLaunchFile;
		
		return filedetails;
	}

	private void initialiseQuickLaunch() {
		
		String fullFileDetails = null;
		for (Iterator iterator = arguments.iterator(); iterator.hasNext();) {
			UtilitiesAppLaunchDetails arg = (UtilitiesAppLaunchDetails) iterator.next();
			if (arg.isQuickLaunch()) {
				if (arg.getType() == UtilitiesAppLaunchDetails.TYPE_FOLDER) {
					this.quickLaunchFolder = arg.getValue();
				} else if (arg.getType() == UtilitiesAppLaunchDetails.TYPE_FILENAME_ONLY) {
					this.quickLaunchFile = arg.getValue();
				} else if (arg.getType() == UtilitiesAppLaunchDetails.TYPE_FULL_FILE_DETAILS) {
					fullFileDetails = arg.getValue();
				} 
			}
		}
		
		
		// TODO this scenario needs catering for
		if (fullFileDetails != null) {
			// check we haven't already got values in - INVALID USE!!!
			// then split into filename & folder
		}

	}



}
