package uk.co.neo9.apps.accounts;

import java.util.Date;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class AccountsDataTescoBase extends AccountsDataModel {


	public void processData(){ 
		
		// there is only one line!
		extractFirstLineData();
		
		setupDateFromData();
		
		// processAmount(); - no, we want it to all be '-' - see default method
		
		setCreditCardDefaults();
		
		setProcessed(true);
	}

	private void setCreditCardDefaults() {
		
		this._TransactionType = "Credit Card";
		this._PlusOrMinus = "-";
		
	}

	protected void extractFirstLineData(){
		// sub classes need to do this
	}

}
