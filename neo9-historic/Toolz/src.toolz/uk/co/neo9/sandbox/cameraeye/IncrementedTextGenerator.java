package uk.co.neo9.sandbox.cameraeye;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class IncrementedTextGenerator {
	
	private NumberFormat formatter = null;
	private IncrementedTextModel model = null;
		
	
	public IncrementedTextGenerator(IncrementedTextModel inputs) {
		super();
		this.model = inputs;
		resetFormatter(inputs.getPrecision());
	}
	
	
	private void resetFormatter(int noOfDigits) {

		StringBuffer seedFormater = new StringBuffer();
		for (int i = 0; i < noOfDigits; i++) {
			seedFormater.append("0");
		}
		formatter = new DecimalFormat(seedFormater.toString());

	}

	
	
	public String generateNext(){

		StringBuffer buf = new StringBuffer();
		
		// setup the base text at the start if the number is trailing
		boolean isTrailingNumber = this.model.isTrailingNumber();
		String baseText = this.model.getBaseText();
		String delimiter = this.model.getDelimiter();
		if (isTrailingNumber == true) {
			buf.append(baseText);
			if (delimiter != null && delimiter.length() > 0) {
				buf.append(delimiter);
			}
		}
		
		// generate the number text
		String nextNumberText = generateNextNumberText();
		buf.append(nextNumberText);
		
		
		// setup the base text at the end if the number is leading
		if (isTrailingNumber == false) {
			buf.append(baseText);
			if (delimiter != null && delimiter.length() > 0) {
				buf.append(delimiter);
			}
		}
		
		
		return buf.toString();
		
	}
	
	
	protected String generateNextNumberText() {
		
		int number = this.model.getSeedNumber();
		String numberText = formatter.format(number);
		
		this.model.incrementSeedNumber();
		
		return numberText;
		
	}



}
