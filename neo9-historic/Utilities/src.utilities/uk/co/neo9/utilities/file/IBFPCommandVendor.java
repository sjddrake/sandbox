package uk.co.neo9.utilities.file;

import java.util.List;

public interface IBFPCommandVendor {

	public IBFPCommand getCommand(String fileDetails);
	public List createCommandList(List commandInputs);
}
