/*
 * Created on 05-Dec-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities.xml;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class XMLAbstractBuddy implements IXMLBuddy {
	
		
	public Vector attributes = new Vector();
	public String tag = new String();
	public String value = new String();		

	public IXMLDataObject getDataObject(){
	
		final String UTILS_METHOD_NAME = " getDataObject() ";
		System.out.println(this.getClass() + UTILS_METHOD_NAME + "called but not overriden!");
	
		return null;
	}


	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddy#setValue(java.lang.String)
	 */
	public void setValue(String pValue) {
		
		value = new String(pValue);
	
	}
	

	public void setName(String pName){
		
		tag = pName.trim();	
		
	}
	

	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddy#addChildByBuddy(uk.co.neo9.utilities.IXMLBuddy)
	 */
	public void addChildByBuddy(IXMLBuddy pBuddy) {
		
		final String UTILS_METHOD_NAME = " addChildByBuddy() ";
		System.out.println(this.getClass() + UTILS_METHOD_NAME + "called but not overriden!");


	}

	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddy#addChild(uk.co.neo9.utilities.IXMLDataObject)
	 */
	public void addChild(IXMLDataObject pDataObject) {
		
		final String UTILS_METHOD_NAME = " addChild() ";
		System.out.println(this.getClass() + UTILS_METHOD_NAME + "called but not overriden!");


	}

	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddy#addAttribute(uk.co.neo9.utilities.IXMLDataObject)
	 */
	public void addAttribute(IXMLDataObject pDataObject) {
		
		final String UTILS_METHOD_NAME = " addAttribute() ";
		System.out.println(this.getClass() + UTILS_METHOD_NAME + "called but not overriden!");


	}

	public void addAttributes(Vector pAttributes){
	
		attributes = pAttributes;
	
	}


	public String getName() {
		
		return tag;
		
	}


	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddy#getValue()
	 */
	public String getValue() {
		
		return value;
	}


	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddy#getParameterValues()
	 */
	public Vector getParameterValues(String pParameterName) {
		
		Vector lAttributeValues = new Vector();
		
		for (Iterator iter = attributes.iterator(); iter.hasNext();) {
			XMLAttributeDefinition element = (XMLAttributeDefinition) iter.next();
			String lName = element.getName();
			if (lName.equals(pParameterName)) lAttributeValues.add(element.getValue());
		}
		
		return lAttributeValues;
	}

}
