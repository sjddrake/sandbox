package uk.co.neo9.sandbox;

public class DIRListLineHelper {

	private static DIRListLineComparator comparator = new DIRListLineComparator();
	
	public static int compareDIRListLines(DIRListLine line1, DIRListLine line2){
		
		int result = comparator.compare(line1, 
										line2, 
										DIRListLine.FIELD_ID_TEXT_TO_MATCH);

		return result;
	}
	
}
