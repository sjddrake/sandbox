package uk.co.neo9.apps.accounts;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class AccountsDataTescoCSVExport extends AccountsDataTescoBase {


//	public void processData(){ 
//		
//		// there is only one line!
//		extractFirstLineData();
//		
//		setProcessed(true);
//	}
	
	/*
	 * The format of the data line is:
	 * 
	 *	 Transaction Date, Posting Date, Billing Amount, Merchant, Merchant City , Merchant State , Merchant Zip , Reference Number , Debit/Credit Flag , SICMCC Code
	 *	 21/10/2012,22/10/2012,�3.95,"COSTA COFFEE","STAINES",,TW18 4WB,55541962296544000080771,D,5812 
	 * 
	 */	
	protected void extractFirstLineData(){

		// for the first line, split into tokens based on space
		// first three elements are the date, last is the transaction
		// amount. All the in betweens are the transaction description
				
		
		final StringTokenizer s = new StringTokenizer(_Line1,",\"",false);
		String lToken = null;
		final ArrayList lTokens = new ArrayList();
		while (s.hasMoreTokens()) {
			lToken = s.nextToken();
			
			// they've gone and added tabs in now so strip 'em out here
			lToken = lToken.trim();
			
			lTokens.add(lToken);
		}		
		
		// assign the tokens
		final int lNoOfTokens = lTokens.size();
		
		if (lNoOfTokens < 4){
			return;
		}
		
//		// reconstruct the date string
//		final StringBuffer dateBuf = new StringBuffer();
//		dateBuf.append(lTokens.get(0));
//		dateBuf.append(" ");
//		dateBuf.append(lTokens.get(1));
//		final String year = getDefaultYear();
//		if (year != null) {
//			dateBuf.append(" ");
//			dateBuf.append(year);			
//		}

		
		// this._DateText = (String) lTokens.get(0); .... there are two dates to choose from!
		this._DateText = (String) lTokens.get(1);
		
		// now the amount ... last token unless it's negative, then last two tokens
		String amountText = (String) lTokens.get(2);
//		if ("-".equals(amountText)) {
//			amountText = amountText + (String) lTokens.get(lNoOfTokens-2);
//		}
		// amountText = "�" + amountText;
		this._Amount = amountText;
		
		this._Description = (String) lTokens.get(3);
		
		// and all the rest is the source
		// ... start from the applicable token
//		int skippedDateIndex = 2;
//		int skippedGarbageIndex = 3;
//		int startFromHereIndex = skippedDateIndex+skippedGarbageIndex;
		
		int startFromHereIndex = 3;
		// int endHere = lNoOfTokens-1;
		int endHere = 7;
		final StringBuffer sourceBuf = new StringBuffer();
		for (int i = startFromHereIndex; i < endHere; i++) {
			sourceBuf.append(((String)(lTokens.get(i))).trim());
			sourceBuf.append(" ");
		}
		this._Reference = sourceBuf.toString().trim();
		
		// tidy up description
		if (this._Description.lastIndexOf("GBR") == this._Description.length()-3){
			this._Description = this._Description.substring(0, this._Description.length()-3);
		}
			
		// tidy up date
		setupDateFromData();
	}
	

}
