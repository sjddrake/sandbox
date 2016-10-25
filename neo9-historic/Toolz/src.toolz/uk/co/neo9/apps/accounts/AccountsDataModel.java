package uk.co.neo9.apps.accounts;

import java.util.Date;

import uk.co.neo9.utilities.UtilitiesDateHelper;


public class AccountsDataModel {
	
	public static final int ACC_ID_JOINT_CURRENT = 10;	
	public static final int ACC_ID_JOINT_SAVINGS = 20;	
	public static final int ACC_ID_SIMON_CURRENT = 30;	
	public static final int ACC_ID_SIMON_SAVINGS = 40;
	
	public static final int IFT_BARCLAYS_SCREEN_SCRAPE = 100;
	public static final int IFT_BARCLAYS_CSV_EXPORT = 500;
	public static final int IFT_CC_SCREEN_SCRAPE = 200;
	public static final int IFT_CC_ADOBE_SCRAPE = 300;
	public static final int IFT_CC_CHROME_SCRAPE = 350;
	public static final int IFT_CC_TABBED = 400;
	public static final int IFT_CC_CSV_EXPORT= 600;
	public static final int IFT_CASH_SCRAPBOOK = 700;
	
	public static final String OWNER_SIMON = "Simon";
	public static final String OWNER_KATHIE = "Kathie";
	public static final String OWNER_JOINT = "Joint";
	
	protected String defaultYear = null;
	protected Date date = null;
	
	protected String _DateText = null;
	protected String _Amount = null;
	protected String _Balance = null;
	protected String _TransactionType = null;
	protected String _Description = null;
	protected String _Reference = null;
	
	protected String _PlusOrMinus = null;
	
	protected String _Line1 = null;
	private boolean _processed = false;
	
	
	// added these for use with the tracker spreadsheets
	protected String _method = null;
	protected String _Owner = null;
	
	public boolean isDefined() {
		
		if (_Line1 == null || _Line1.equals("")) return false; else return true;
	}


	public void addDataLine(String lDataLine) {	
		if (_Line1 == null) _Line1 = lDataLine;
	}
	
	
	public void processData(){
		// the subclass must do this
		_processed = true;
	}
	
	protected void setupDateFromData() {
		
		// expects date string to have a valid representation
		if (this._DateText != null) {
			
			String dateValue = this._DateText.trim();
			
			if (dateValue.length() > 0) {
				try {
					Date dateObj = UtilitiesDateHelper.convertValueToDate(dateValue);
					this.date = dateObj;
				} catch (Exception e) {
					// If it doesn't work, don't worry about it!!
				}
			}
		
		}
	}
	
	
	
	protected void processOwner(String ownerText) {
		
		if (ownerText!=null) {
			if (ownerText.toLowerCase().startsWith("si")) {
				this._Owner = OWNER_SIMON;
				
			} else if (ownerText.toLowerCase().startsWith("ka")) {
				this._Owner = OWNER_KATHIE;				
				
			} else if (ownerText.toLowerCase().startsWith("j")) {
				this._Owner = OWNER_JOINT;				
				
			} else {
				this._Owner = ownerText;
			}
		}	
	}
	
	
	protected void processAmount() {
		// Auto-generated method stub
		
		if (_Amount != null){
			
			if (_Amount.startsWith("-")){
				this._PlusOrMinus = "-";
				_Amount = _Amount.substring(1);
			} else {
				this._PlusOrMinus = "+";
			}
			
		}
		
	}


	public String output(OutputParameterModel outputModel) {
		
		if (_processed  == false){
			processData();
		}
		
		// an overrideable get-out clause
		if (this.includeInOuput(outputModel) == false) {
			return null;
		}
		
		String outputText = null;
//		if (OutputParameterModel.ID_STATEMENT.equals(outputModel.getOutputParamId())) {
//			outputText = buildStatementsOutputText();
//		} else {
//			outputText = buildTrackerOutputText();
//		}
		if (OutputParameterModel.getStatementParamId().equals(outputModel.getOutputParamId())) {
			outputText = buildStatementsOutputText();
		} else if (OutputParameterModel.getTrackerParamId().equals(outputModel.getOutputParamId())) {
			outputText = buildTrackerOutputText(true);
		} else if (OutputParameterModel.getTrackerExtendedParamId().equals(outputModel.getOutputParamId())) {
			outputText = buildTrackerOutputText(true);
		} else {
			outputText = buildTrackerOutputText(false);
		}
		
		
	
		return outputText;

	}

	// override this to be able to filter our transactions based on the transaction data
	public boolean includeInOuput(OutputParameterModel outputModel) {
		return true;
	}


	protected String buildStatementsOutputText() {
		
		StringBuffer lOutput = new StringBuffer();
		lOutput.append(formatField(this._DateText));
		lOutput.append(",");
		lOutput.append(formatField(""));
		lOutput.append(",");
		
		lOutput.append(formatField(this._Amount));
		lOutput.append(",");
		lOutput.append(formatField(""));
		lOutput.append(",");		
		
		lOutput.append(formatField(this._Description));
		lOutput.append(",");
		lOutput.append(formatField(""));
		lOutput.append(",");	
	
		lOutput.append(formatField(this._PlusOrMinus));
		lOutput.append(",");		
		lOutput.append(formatField(this._Balance));
		lOutput.append(",");
		lOutput.append(formatField(this._method));
		lOutput.append(",");
		lOutput.append(formatField(this._Reference));
	
		//System.out.println(lOutput);
		return lOutput.toString();		
	}
	
	protected String buildTrackerOutputText(boolean extended) {
		
		StringBuffer lOutput = new StringBuffer();
		lOutput.append(formatField("")); // empty column
		lOutput.append(",");
		lOutput.append(formatField(this._DateText)); // date
		lOutput.append(",");
		lOutput.append(formatField("")); // empty column
		lOutput.append(",");			
		lOutput.append(formatField(this._Amount)); // amount
		lOutput.append(",");	
		lOutput.append(formatField(this._Description)); // where
		lOutput.append(",");
		String methodText = this._method;
		if (methodText == null || methodText.trim().length() == 0) {
			methodText = this._TransactionType;
		}
		lOutput.append(formatField(methodText)); // method
		lOutput.append(",");	
		lOutput.append(formatField("")); // description - empty column
		lOutput.append(",");
		lOutput.append(formatField("")); // category - empty column
		lOutput.append(",");
		if (extended) {
			lOutput.append(formatField("")); // sub-cat - empty column
			lOutput.append(",");
		}
		lOutput.append(formatField(this._Owner)); // owner - empty column
		lOutput.append(",");		
		lOutput.append(formatField("")); // paid - empty column
		lOutput.append(",");
		
		//System.out.println(lOutput);
		return lOutput.toString();		
	}
	
	
	/**
	 * @param string
	 * @return
	 */
	 protected String formatField(String pFieldValue) {
		
		StringBuffer buf = new StringBuffer(); 
		buf.append("\"");
		if (pFieldValue != null) {
			buf.append(pFieldValue);
		}
		buf.append("\"");
		return buf.toString();
	}


	public boolean isProcessed() {
		return _processed;
	}


	public void setProcessed(boolean _processed) {
		this._processed = _processed;
	}


	public String getDefaultYear() {
		return defaultYear;
	}


	public void setDefaultYear(String defaultYear) {
		this.defaultYear = defaultYear;
	}


	public String getMethod() {
		return _method;
	}


	public void setMethod(String method) {
		this._method = method;
	}
	
}
