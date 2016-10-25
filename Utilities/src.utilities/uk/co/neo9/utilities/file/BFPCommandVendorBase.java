package uk.co.neo9.utilities.file;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BFPCommandVendorBase implements IBFPCommandVendor {
	
	public List createCommandList(List commandInputs){
		
		List commands = new ArrayList();
		
		for (Iterator iter = commandInputs.iterator(); iter.hasNext();) {
			String fileName = (String) iter.next();
			IBFPCommand command = getCommand(fileName);
			if (command != null) {
				commands.add(command);
			}
		}
		
		return commands;
	}
	
	public IBFPCommand getCommand(String commandInput){
		return null;
	}

}
