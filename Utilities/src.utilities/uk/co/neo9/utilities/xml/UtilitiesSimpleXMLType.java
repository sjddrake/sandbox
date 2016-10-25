/*
 * Created on 12-Dec-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities.xml;

import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.UtilitiesTabulator;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UtilitiesSimpleXMLType extends UtilitiesAbstractXMLType {
	public String outputDefinitionTree(int pIndentLevel, boolean pIncludeSimpleTypes) {
		
		String lAttributes = super.outputAttributeDefinintion();
		
		int lIndent = pIndentLevel; //pIndentLevel+1
		
		String lTreeText;
		lTreeText = UtilitiesTabulator.addDottedTabs(lIndent)+  "<" + tag + "/>"; 
		if (lAttributes.equals("")) {
			lTreeText = lTreeText + CommonConstants.NEWLINE;
		} else {
		
			lTreeText = lTreeText + " - " + lAttributes + CommonConstants.NEWLINE;
		}
		
		// System.out.println(lTreeText);
		
		return lTreeText;
		
	}

}
