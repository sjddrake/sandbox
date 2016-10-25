package uk.co.neo9.apps.accounts;

import java.util.List;

import uk.co.neo9.utilities.file.CSVHelper;

public class AccountsDataCash extends AccountsDataModel implements AccountsToolNarrativeConstants{


	public void processData(){
		
		
		extractLineData();
		
//		processAmount();
//		
//		extractFurtherLineData();
//		
//		setupDateFromData();
//		
//		// new for 2011
//		lookupChequeDetails();
		
		setProcessed(true);
	}

	private void extractLineData() {
		// System.out.println(this._Line1);
		
		
		String csvLine = this._Line1;
		List<String> fields = CSVHelper.extractCSVFields(csvLine );
		
		if (fields.size() < 4) {
			throw new IllegalArgumentException("Cash file needs at least 4 CSV fields in the line; failed this one > "+csvLine);
		}
		
		// expecting this in the form DD/MM
		this._DateText = fields.get(0);
		
		// reconstruct the date string
		final String year = getDefaultYear();
		if (year != null) {
			this._DateText = this._DateText+"/";
			this._DateText = this._DateText+year;	
		}
		
		
		String owner = fields.get(1);
		processOwner(owner);
		
		
		this._Amount = fields.get(2);
		
		StringBuilder buff = new StringBuilder();
		int totalNoOfFields = fields.size();
		for (int i = 3; i < totalNoOfFields; i++) {
			if (buff.length() > 0) {
				buff.append(" ");
			}
			buff.append(fields.get(i));
		}
		this._Description = buff.toString();
		
		
		// post processing
		processAmount();
		setupDateFromData();
		
		// defaults
		this._method = "cash";
	}
}
