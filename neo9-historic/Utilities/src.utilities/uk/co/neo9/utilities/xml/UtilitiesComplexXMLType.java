/*
 * Created on 12-Dec-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities.xml;

import java.util.Vector;

import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.UtilitiesTabulator;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UtilitiesComplexXMLType extends UtilitiesAbstractXMLType {
	
	
	public Vector children = new Vector();	
	
	
	public String outputDefinitionTree(int pIndentLevel, boolean pIncludeSimpleTypes) {

		String lAttributes = super.outputAttributeDefinintion();
		
		String lTreeText;
		lTreeText = UtilitiesTabulator.addDottedTabs(pIndentLevel) + "<" + tag + ">";
		if (lAttributes.equals("")) {
			lTreeText = lTreeText + CommonConstants.NEWLINE;
		} else {
		
			lTreeText = lTreeText + " - " + lAttributes + CommonConstants.NEWLINE;
		}
		
		if (this.children != null) {
	
			String lAttributeText;
			for(int i = 0; i < children.size(); i++) {
	
				lAttributeText = ((UtilitiesAbstractXMLType)(children.elementAt(i))).outputDefinitionTree(pIndentLevel+1,pIncludeSimpleTypes);
				lTreeText = lTreeText + lAttributeText;
			}
		
		}	
		
		lTreeText = lTreeText + UtilitiesTabulator.addDottedTabs(pIndentLevel) + "</" + tag + ">";
		lTreeText = lTreeText + CommonConstants.NEWLINE;
		
		/*if (!XSDGenGlobals.isGlobalsSet) System.out.println(lTreeText);*/
		
		return lTreeText;
		
	}
	public void addChild(IXMLDataObject pDataObject){
		
		children.add(pDataObject);
	}
	


}
