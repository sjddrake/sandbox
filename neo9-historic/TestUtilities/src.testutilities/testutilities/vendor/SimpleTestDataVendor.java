package testutilities.vendor;

import java.util.ArrayList;
import java.util.List;

public class SimpleTestDataVendor {
	
	
	public static List<String> getRandomStrings(int count){
		
		
		List<String> strings = new ArrayList<String>();
		
		
		for (int i = 0; i < count; i++) {
			strings.add("random"+1);
		}
		
		
		return strings;
	}

}
