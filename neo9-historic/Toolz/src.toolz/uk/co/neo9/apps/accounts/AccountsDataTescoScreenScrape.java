package uk.co.neo9.apps.accounts;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class AccountsDataTescoScreenScrape extends AccountsDataTescoBase {


//	public void processData(){ 
//		
//		// there is only one line!
//		extractFirstLineData();
//		
//		setupDateFromData();
//		
//		setProcessed(true);
//	}
	
	/*
	 * The format of the data line is:
	 * 
	 * 	 21 May 2008  CLARKS SHOE DEPT. £14.00 
	 * 
	 */	
	protected void extractFirstLineData(){

		// for the first line, split into tokens based on space
		// first three elements are the date, last is the transaction
		// amount. All the in betweens are the transaction description
				
		
		StringTokenizer s = new StringTokenizer(_Line1," ",false);
		String lToken = null;
		ArrayList lTokens = new ArrayList();
		while (s.hasMoreTokens()) {
			lToken = s.nextToken();
			
			// they've gone and added tabs in now so strip 'em out here
			lToken = lToken.trim();
			
			lTokens.add(lToken);
		}		
		
		// assign the tokens
		int lNoOfTokens = lTokens.size();
		
		if (lNoOfTokens < 4){
			return;
		}
		
		// reconstruct the date string
		StringBuffer dateBuf = new StringBuffer();
		dateBuf.append(lTokens.get(0));
		dateBuf.append(" ");
		dateBuf.append(lTokens.get(1));
		dateBuf.append(" ");
		dateBuf.append(lTokens.get(2));
		
		this._DateText = dateBuf.toString();
		
		// now the amount (last token)
		this._Amount = (String) lTokens.get(lNoOfTokens-1);
		
		// and all the rest is the source
		StringBuffer sourceBuf = new StringBuffer();
		for (int i = 3; i < lNoOfTokens-1; i++) {
			sourceBuf.append(((String)(lTokens.get(i))).trim());
			sourceBuf.append(" ");
		}
		this._Description = sourceBuf.toString().trim();
		
		// setup the defaults
		this._Owner = "Unknown";
		this._method = "CC";
			
	}
}
