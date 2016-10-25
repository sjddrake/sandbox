/*
 * Created on 31-May-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SimpleXMLReader {

/*
 * This class parses an array list of strings where
 * a line can contain a data value in XML notation. 
 * It will ignore everything else in the array list.
 * 
 * IT DOESE NOT YET SUPPORT
 * :
 * 		 - NESTED FIELDS
 * 		 - LINE SPANNING FIELDS
 * 		 - MULTIPLE OCCURENCES
 */


	private final static java.lang.String START_OPENING_TAG = "<";
	private final static java.lang.String START_CLOSING_TAG = "</";
	private final static java.lang.String END_TAG = ">";


	private static String buildTag(String pElementName, boolean pOpen) {

		// build the tag text
		StringBuffer lTag = new StringBuffer();
		if (pOpen) {
			lTag.append(START_OPENING_TAG);
		} else {
			lTag.append(START_CLOSING_TAG);
		}
		lTag.append(pElementName);
		lTag.append(END_TAG);

		return lTag.toString();
	}

	public static String getField(Collection pXMLLines, String pElementName){
		
		// robustness checks
		if (pElementName == null) return null;
		if (pXMLLines == null) return null;
		
		String lFieldValue = null;
		
		// first build the XML tags for string searching
		String lUpperedElementName = pElementName.toUpperCase();
		final String lOpeningTag = buildTag(lUpperedElementName,true);
		final String lClosingTag = buildTag(lUpperedElementName,false);
		
		// now search for the line that contains the field
		for (Iterator iter = pXMLLines.iterator(); iter.hasNext();) {
			
			String lLine = (String) iter.next();
			String lUpperedLine = lLine.toUpperCase();
			int lFieldValueStartIndex = lUpperedLine.indexOf(lOpeningTag);
			if (lFieldValueStartIndex!=-1){
				// found the field in this line
				int lFieldValueEndIndex = lUpperedLine.indexOf(lClosingTag);
				if (lFieldValueEndIndex!=-1){
					lFieldValue 
						= lLine.substring(
							lFieldValueStartIndex+lOpeningTag.length(),
							lFieldValueEndIndex);
					break;
				}
			}
			
		}
		
		if (lFieldValue != null){
			lFieldValue = lFieldValue.trim();
			if (lFieldValue.length() == 0){
				return null;
			} else {
				return lFieldValue;
			}
		} else{
			return null; 
		}
	}

}
