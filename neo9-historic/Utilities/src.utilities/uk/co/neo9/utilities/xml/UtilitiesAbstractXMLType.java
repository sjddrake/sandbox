/*
 * Created on 12-Dec-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities.xml;

import java.util.Iterator;


/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UtilitiesAbstractXMLType extends XMLAbstractBuddy implements IXMLDataObject {
		

	public String outputDefinitionTree(int pIndentLevel, boolean pIncludeSimpleTypes) {
		
		return null;
		
	}


	public String outputAttributeDefinintion() {
		
		String lOutput = new String();
		
		if (attributes != null){
			
			for (Iterator iter = attributes.iterator(); iter.hasNext();) {
				XMLAttributeDefinition element = (XMLAttributeDefinition) iter.next();
				lOutput = lOutput + element.outputAttributeDefinintion() + ", ";
			}
		}
		
		
		return lOutput;
		
	}	
		

	
	public IXMLDataObject getDataObject(){
	
		return this;
	}
		


}
