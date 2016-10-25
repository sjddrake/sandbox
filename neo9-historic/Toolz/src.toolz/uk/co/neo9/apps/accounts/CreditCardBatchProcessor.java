package uk.co.neo9.apps.accounts;

import uk.co.neo9.utilities.file.BFPApplicationBase;
import uk.co.neo9.utilities.file.IBFPCommandVendor;

public class CreditCardBatchProcessor extends BFPApplicationBase {

	public CreditCardBatchProcessor(IBFPCommandVendor commandVendor) {
		super(commandVendor);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// get the working folder
		if (args == null || args.length == 0 || args.length > 1) {
			displayArgsInstructions();
			System.exit(1);
		}
		
		String targetFolder = args[0];
		runBatchProcessor(targetFolder);

	}
	
	
	
	protected static void runBatchProcessor(String targetFolder){
		CreditCardBFPCommandVendor vendor = new CreditCardBFPCommandVendor();
		vendor.setTargetFolder(targetFolder);
		
		CreditCardBatchProcessor bfp = new CreditCardBatchProcessor(vendor);
		bfp.setTargetFolder(targetFolder);
		
		bfp.go();
	}
	

	private static void displayArgsInstructions(){
		System.out.println("Please run with args being: ");
		System.out.println("	- working folder");
	
	}
	
}
