package uk.co.neo9.apps.accounts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import uk.co.neo9.utilities.file.FileServer;

public class AccountDetailsLookup {

	private static HashMap accountsLookup = new HashMap();
	private static ArrayList accountKeys = new ArrayList();

	private static String accountsLookupFileName = null; 
	private static boolean isLoaded = false;
	
	public static String accountLookup(String pAccountNo) {
		
		if (isLoaded == false) {
			loadAccountsLookup();
		}
		
		String lAccountName = null;
		for (Iterator iter = accountKeys.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if (pAccountNo.indexOf(key) != -1){
				lAccountName = (String) accountsLookup.get(key);
				break;
			}
		}
		
		if (lAccountName == null) {
			lAccountName  = pAccountNo;
		}
	
		return lAccountName;
	}
	
	

	public static void loadAccountsLookup(String fileDetails) {
		setAccountsLookupFileName(fileDetails);
		loadAccountsLookup();
	}
	
	public static void loadAccountsLookup() {
		
		// load in the accounts lookup data
		List accounts = readAccountsDataFile();
		for (Iterator iter = accounts.iterator(); iter.hasNext();) {
			String data = (String) iter.next();
			int delimiter = data.indexOf(',');
			if (delimiter != -1) {
				String accountNo = data.substring(0,delimiter);
				String accountName = data.substring(delimiter+1);
				accountsLookup.put(accountNo,accountName);
				accountKeys.add(accountNo);
			}
		}
		
	}
	
	
	private static List readAccountsDataFile() {
		List data = null;
		if (accountsLookupFileName != null) {
			try {
				data = FileServer.readTextFile(accountsLookupFileName);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			
		} else {
			data = new ArrayList(); // default lookups
			data.add("60338109,My Current Account");
			data.add("80777692,My eSavings Account");
			data.add("90518697,joint current account");	
			data.add("20915203,joint clearing account");
			data.add("60128600,joint savings account");
			data.add("20826855,Neo 9 Current Account");	
			data.add("10282804,Neo 9 Low Interest Account");
			data.add("70561584,Joint Easy Saver Account");
		}
		return data;
	}


	public static String getAccountsLookupFileName() {
		return accountsLookupFileName;
	}


	public static void setAccountsLookupFileName(String fileName) {
		accountsLookupFileName = fileName;
	}
}
