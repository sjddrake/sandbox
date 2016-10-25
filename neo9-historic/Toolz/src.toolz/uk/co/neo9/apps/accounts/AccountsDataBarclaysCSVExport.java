package uk.co.neo9.apps.accounts;

import java.util.List;

import uk.co.neo9.utilities.UtilitiesTextHelper;
import uk.co.neo9.utilities.file.CSVHelper;

public class AccountsDataBarclaysCSVExport extends AccountsDataModel implements AccountsToolNarrativeConstants{

	protected String _Line2 = null;
	protected String _Line3 = null;
	
	// export data contains the details of the account that the transaction is exported from
	protected String _accountNoAndSortcode = null; 
	
	// export data has this in a seperate field
	protected String _transactionChequeNo = null;
	
	// exort has its own nomenclature
	protected String _exportSubCat = null; 
	
	
	private int focusAccountId = 0;
	public void setFocusAccountId(int focusAccountId) {
		this.focusAccountId = focusAccountId;
	}

	public int getFocusAccountId() {
		return focusAccountId;
	}
	
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
	
	@Override
	public boolean includeInOuput(OutputParameterModel outputModel) {
		
		boolean include = false;
		
		if (getFocusAccountId() != 0) {
			int transactionAccountID = determineAccountForThisTransaction();
			if (transactionAccountID == getFocusAccountId()) {
				include = true;
			}
		} else {
			include = true;
		}

		return include;
	}
	
	
	private int determineAccountForThisTransaction() {
		// TODO I could adapt the AccountDetailsLookup class to store the ID
		// as well as the description that I use but as it has a hash map
		// of simple string, this would have to chagne to be a value object.
		// Also it then questions whether it ought to hold two hash maps of
		// the value object, one keyed on acc no and one keyed on ID
		
		// For now, hard code this sucker!! :-) 2012
		final String JOINT_BARCLAYS_ACC_NO = "90518697";
		final String SIMONZ_BARCLAYS_ACC_NO = "60338109";
		
		
		int accountID = 0;
		
		String accountDetails = this._accountNoAndSortcode;
		if (accountDetails != null && accountDetails.trim().length() > 0) {

			if (accountDetails.indexOf(JOINT_BARCLAYS_ACC_NO) != -1) {
				accountID = AccountsDataModel.ACC_ID_JOINT_CURRENT;
				
			} else if (accountDetails.indexOf(SIMONZ_BARCLAYS_ACC_NO) != -1) {
				accountID = AccountsDataModel.ACC_ID_SIMON_CURRENT;
				
			}
		}
		
		return accountID;
	}

	protected void lookupChequeDetails() {
		// Use the lookup mechanism to see if we can get details of what the cheque was
		if (Cheque.equals(this.getMethod())) {
			// see if we can get a description from the account no
			String chequeNo = this._transactionChequeNo;
			if (chequeNo != null) {
				String description = ChequeDetailsLookup.chequeLookup(chequeNo);
				if (description != null) {
					this._Description = chequeNo + " - " + description;
				}
			}
		}
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
					
		} else if (_Line2 != null ){
			
			this._Description = AccountDetailsLookup.accountLookup(_Line2);
		}
	}

	private void extractFirstLineData (){

				
		//  ,08/05/2012,20-76-89 60338109,-18.00,PAYMENT,BFI IMAX              REF 124 7736027651 BCC
		
		
		List<String> lTokens = CSVHelper.extractCSVFields(_Line1);	
		
		
		// assign the tokens
		int lNoOfTokens = lTokens.size();
		this._accountNoAndSortcode = lTokens.get(2); // not currently used in output but used for filtering
		this._transactionChequeNo = lTokens.get(0);
		this._DateText = lTokens.get(1);
		// this._Balance =  lTokens.get(lNoOfTokens-1); <--------- no balance in exports!!!
		this._Amount = lTokens.get(3);
		
		this._exportSubCat = lTokens.get(4);
		
		
		this._Line2 = UtilitiesTextHelper.removePaddingInText(lTokens.get(5)); // known as 'memo' in the export.... will further process this
		

		
		
		// set the method based on the transaction type
		this._method = determineMethodFromExportSubCat(this._exportSubCat);
		
		
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



	private String determineMethodFromExportSubCat(String subCat) {
		String method = "";
		
		// get safety checks out of the way
		if (subCat == null) {
			return method;
		} else if (subCat.trim().length() == 0) {
			return method;
		}
		
		
		// simple comparison
		String trimmedTransType = subCat.trim();
		if (trimmedTransType.equalsIgnoreCase(DIRECT_DEBIT)) {
			method = DD;
		} else if (trimmedTransType.equalsIgnoreCase(PAYMENT)) {
			method = Direct;
		}  else if (trimmedTransType.equalsIgnoreCase(REPEAT_PAYMENT)) {
			method = DD;
		}  else if (trimmedTransType.equalsIgnoreCase(STANDING_ORDER)) {
			method = DD;
		}  else if (trimmedTransType.equalsIgnoreCase(Card_Purchase)) {
			method = Direct;
		}  else if (trimmedTransType.equalsIgnoreCase(CASH)) {
			method = Cash;
		}  else if (trimmedTransType.equalsIgnoreCase(CHEQUE)) {
			method = Cheque;
		}  else if (trimmedTransType.equalsIgnoreCase(DIRECT_DEPOSIT)) {
			method = Paid_In;
		}  else if (trimmedTransType.equalsIgnoreCase(Remittance)) {
			method = Paid_In;
		}  else {
			method = trimmedTransType;
		}
		
		return method;
	}	
	

}
