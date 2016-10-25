/**
 * Source File: CreditCardDataReader.java
 * 
 * Copyright (c) JMCG Systems LTD 2009
 *
 * @author Simon
 * 
 * Description: 
 *
 * 		TODO add a description here
 * 
 * Revision History
 * 
 * 3 Aug 2009	Simon		Initial Creation
 *
 */
package uk.co.neo9.apps.accounts;

/**
 * TODO <put a one line description here>
 *
 * TODO <OPTIONAL: flesh out the one liner here>
 *
 * Created: 3 Aug 2009
 *
 * @author Simon
 **/
public class CreditCardDataReader extends AccountsDataReader {

	public static void main(String[] args) {
		
		AccountsDataReader a = new CreditCardDataReader();
		a.go(args);
		
	}
	
	// overriden to handle the credit card extra inputs
	protected boolean readMainArgs(String[] argsIN) {
		
		// first check there are some args
		if (argsIN == null || argsIN.length == 0) {
			displayArgsInstructions();
			System.exit(1);
		}
		
		boolean success = true;
		setCreditCardReaderMode(argsIN[argsIN.length-1]);
		
		
		if (success) {
			
			// need to strip off the last arg
			String[] superArgs = new String[argsIN.length-1];
			for (int i = 0; i < argsIN.length-1; i++) {
				String arg = argsIN[i];
				superArgs[i] = arg;
			}
			
			success = super.readMainArgs(superArgs);
		}
		
		return success;
	}

	
	private void setCreditCardReaderMode(String arg) {
		
		// get the type string
		if (arg.equalsIgnoreCase("adobe")) {
			this.setInputFileType(AccountsDataModel.IFT_CC_ADOBE_SCRAPE);
		} else if (arg.equalsIgnoreCase("screen")) {
			this.setInputFileType(AccountsDataModel.IFT_CC_SCREEN_SCRAPE);
		} else if (arg.equalsIgnoreCase("chrome")) {
			this.setInputFileType(AccountsDataModel.IFT_CC_CHROME_SCRAPE);
		} else {
			displayArgsInstructions();
			System.exit(1);
		}

	}
	
	
	protected void displayArgsInstructions(){
		System.out.println("This Credit Card Data reader needs the following additional argument LAST: ");
		System.out.println("	- type: ADOBE or SCREEN");
		System.out.println("");
		System.out.println("... as well as the standard reader args as described next...");
		System.out.println("");
	
		super.displayArgsInstructions();
	}
	
}
