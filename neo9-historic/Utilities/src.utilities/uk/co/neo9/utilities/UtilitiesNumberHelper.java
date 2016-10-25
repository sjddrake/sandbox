package uk.co.neo9.utilities;

public class UtilitiesNumberHelper {
	
	public static boolean isEvenNumber(int numberToTest){
		boolean isEven = false;
		
		if (numberToTest == 0) {
			isEven = true;
		} else {
			int remainder = numberToTest % 2; 
			if (remainder == 0) {
				isEven = true;
			}
		}
		
		return isEven;
	}
}
