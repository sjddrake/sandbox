package uk.co.neo9.apps.accounts;

// NOTE!!!
//
// use AccountsBatchProcessorRunner ... that does everything now! (I think!)
//
//

@Deprecated 
public class AccountsDataReaderBatchModeRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	//	AccountsDataReader.main(getArgsForJointAccount1());
	//	AccountsDataReader.main(getArgsForTescoCC1());
		AccountsDataReader.main(null);
	}

	private static String[] getArgsForTescoCC1(){
		
		String rootfolder = "D:\\ZZ - Swap Zone\\_ DropBoxes\\jmcgDropBox\\My Dropbox\\Kathie and Simon\\accounts";
		
		String[] readerArgs = {"BATCHMODE",
		"list.txt",
		"dummy",
		rootfolder+"\\temp\\ccBatch"};
		       
		return readerArgs;
	}	
	
	private static String[] getArgsForJointAccount1(){
		
		String rootfolder = "D:\\ZZ - Swap Zone\\_ DropBoxes\\jmcgDropBox\\My Dropbox\\Kathie and Simon\\accounts";
		
		String[] readerArgs = {"BATCHMODE",
		"joint acc batch list.txt",
		"dummy",
		rootfolder+"\\temp\\OctJointAccBatch",
		rootfolder+"\\temp\\OctJointAccBatch\\AccountsLookupData.txt"};
		       
		return readerArgs;
	}
	
	
	private static String[] getArgsForJointAccount_MonthIn2012(){
		
		String rootfolder = "D:\\ZZ - Swap Zone\\_ DropBoxes\\jmcgDropBox\\My Dropbox\\Kathie and Simon\\accounts";
		
		String[] readerArgs = {"BATCHMODE",
		"joint acc batch list.txt",
		"dummy",
		rootfolder+"\\temp\\OctJointAccBatch",
		rootfolder+"\\temp\\OctJointAccBatch\\AccountsLookupData.txt"};
		       
		return readerArgs;
	}
}
