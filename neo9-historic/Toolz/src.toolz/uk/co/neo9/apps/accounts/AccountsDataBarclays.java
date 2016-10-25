package uk.co.neo9.apps.accounts;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class AccountsDataBarclays extends AccountsDataModel implements AccountsToolNarrativeConstants{

	protected String _Line2 = null;
	protected String _Line3 = null;
	
	public void addDataLine(String lDataLine) {	
		if (_Line1 == null) _Line1 = lDataLine;
		else if (_Line2 == null) _Line2 = lDataLine;
		else if (_Line3 == null) _Line3 = lDataLine;
	}
	/**
	 * 
	 */
	public void dump() {
		// Auto-generated method stub
		System.out.println(_Line1);
		System.out.println(_Line2);
		System.out.println(_Line3);
		
	}

	public void processData(){
		
		// for the first line, split into tokens based on space
		// 1st last and penultimate are date, balance & transaction amount
		// all other tokens describe the transaction so they should be
		// rebuilt into a single, space delimited string
		
		// lines 2 & 3:
		// if we have 3 lines, are interested in line 2 as the description
		// if we have 2 lines, its a funds transfer, look up account number! ;-)
		

		
		extractFirstLineData();
		
		processAmount();
		
		extractFurtherLineData();
		
		setupDateFromData();
		
		// new for 2011
		lookupChequeDetails();
		
		setProcessed(true);
	}
	
	protected void lookupChequeDetails() {
		// Use the lookup mechanism to see if we can get details of what the cheque was
		if (Cheque.equals(this.getMethod())) {
			// see if we can get a description from the account no
			String chequeNo = this._Description;
			if (chequeNo != null) {
				String description = ChequeDetailsLookup.chequeLookup(chequeNo);
				if (description != null) {
					this._Description = chequeNo + " - " + description;
				}
			}
		}
	}
	
	
	protected void setupDateFromData() {
		
		// THIS DOESN'T WORK FOR THE FORMAT - needs to be cleverer!
//		Date dateObj = new Date(this._DateText);
//		this.date = dateObj;
		

//		System.out.println(dateObj);
	}
	
	/**
	 * 
	 */
	private void extractFurtherLineData() {
		// Auto-generated method stub

		// lines 2 & 3:
		// if we have 3 lines, are interested in line 2 as the description
		// if we have 2 lines, its a funds transfer, look up account number! ;-)
		
		if (_Line3 != null ){
			
			this._Description = _Line2;
			this._Reference = _Line3;
					
		} else {
			
			this._Description = AccountDetailsLookup.accountLookup(_Line2);
		}
	}

	private void extractFirstLineData (){

		// for the first line, split into tokens based on space
		// 1st last and penultimate are date, balance & transaction amount
		// all other tokens describe the transaction so they should be
		// rebuilt into a single, space delimited string
				
		
		StringTokenizer s = new StringTokenizer(_Line1," \t",false);
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
		this._DateText = (String) lTokens.get(0);
		this._Balance = (String) lTokens.get(lNoOfTokens-1);
		this._Amount = (String) lTokens.get(lNoOfTokens-2);
		
		StringBuffer lTransactionType = new StringBuffer();
		for (int i = 1; i < lNoOfTokens-2; i++) {
			lTransactionType.append(((String)(lTokens.get(i))).trim());
			lTransactionType.append(" ");
		}
		
		this._TransactionType = lTransactionType.toString().trim();
		
		
		// set the method based on the transaction type
		this._method = determineMethodFromTransactionType(this._TransactionType);
		
		
		// default the owner in too...
		if (DD.equals(this._method)) {
			this._Owner = Joint;
		} else if (Cash.equals(this._method)) {
			this._Owner = Cash;
		} else {
			this._Owner = Unknown;
		}
		
	}
	
	
	
	private String determineMethodFromTransactionType(String transactionType) {
		String method = "";
		
		// get safety checks out of the way
		if (transactionType == null) {
			return method;
		} else if (transactionType.trim().length() == 0) {
			return method;
		}
		
		
		// simple comparison
		String trimmedTransType = transactionType.trim();
		if (trimmedTransType.equalsIgnoreCase(Direct_Debit)) {
			method = DD;
		} else if (trimmedTransType.equalsIgnoreCase(Bill_Payment)) {
			method = DD;
		}  else if (trimmedTransType.equalsIgnoreCase(Standing_Order)) {
			method = DD;
		}  else if (trimmedTransType.equalsIgnoreCase(Card_Purchase)) {
			method = Direct;
		}  else if (trimmedTransType.equalsIgnoreCase(Cash_Withdrawal)) {
			method = Cash;
		}  else if (trimmedTransType.equalsIgnoreCase(Cheque)) {
			method = Cheque;
		}  else if (trimmedTransType.equalsIgnoreCase(Counter_Credit)) {
			method = Paid_In;
		}  else if (trimmedTransType.equalsIgnoreCase(Remittance)) {
			method = Paid_In;
		}  else {
			method = trimmedTransType;
		}
		
		return method;
	}



	
	

}
