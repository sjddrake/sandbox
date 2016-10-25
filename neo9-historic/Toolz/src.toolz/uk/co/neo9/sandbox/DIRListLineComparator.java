/**
 * 
 */
package uk.co.neo9.sandbox;

import uk.co.neo9.utilities.UtilitiesComparatorPlusAdapter;

/**
 * @author Simon
 *
 */
public class DIRListLineComparator extends UtilitiesComparatorPlusAdapter {

	public DIRListLineComparator() {
		super();
	}	
	
	public DIRListLineComparator(int pFieldToUse) {
		super(pFieldToUse);
	}

	public int compare(Object o1, Object o2, int fieldID) 
	{
	int result = 0;

	// do some type checks here?
	DIRListLine details1 = (DIRListLine)o1;
	DIRListLine details2 = (DIRListLine)o2;
	
	
	if (fieldID == DIRListLine.FIELD_ID_TEXT_TO_MATCH){
		result = details1.getTextToMatch().compareToIgnoreCase(details2.getTextToMatch());
	
	} else if (fieldID == DIRListLine.FIELD_ID_LINE_TEXT){
		result = details1.getLineText().compareToIgnoreCase(details2.getLineText());
	
	} else if (fieldID == DIRListLine.FIELD_ID_FILE_NAME){
		result = details1.getFileName().compareToIgnoreCase(details2.getFileName());
	} 
	
	// the String comparator gives a score, we just want 1/-1/0
	
	if (result != 0) {
		result = (result < 0) ? -1 : 1;
	}
	
	return result;
}

public void initialiseFieldToUse() {
	setFieldToUseId(DIRListLine.FIELD_ID_TEXT_TO_MATCH);
	
}
}
