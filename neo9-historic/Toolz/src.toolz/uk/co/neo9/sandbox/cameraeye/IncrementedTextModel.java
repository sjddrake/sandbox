package uk.co.neo9.sandbox.cameraeye;


public class IncrementedTextModel {
	

	private boolean trailingNumber = true;
	private String baseText = null;
	private String delimiter = null;
	private int seedNumber = 0;
	private int precision = 1;
	
	
	public IncrementedTextModel(String text, int noOfDigits, int number) {
		super();
		baseText = text;
		seedNumber = number;
		setPrecision(noOfDigits);
		
	}
	
	public String getBaseText() {
		return baseText;
	}

	public void setBaseText(String baseText) {
		this.baseText = baseText;
	}


	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int noOfDigits) {
		precision = noOfDigits;
	}

	public int getSeedNumber() {
		return seedNumber;
	}

	public void setSeedNumber(int seedNumber) {
		this.seedNumber = seedNumber;
	}

	public void incrementSeedNumber() {
		this.seedNumber++;
	}

	public boolean isTrailingNumber() {
		return trailingNumber;
	}

	public void setTrailingNumber(boolean trailingNumber) {
		this.trailingNumber = trailingNumber;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

}
