/*
 * Created on 05-Dec-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities.xml;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class XMLAttributeDefinition 
				extends XMLAbstractBuddy 
				implements IXMLDataObject{
	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddy#getName()
	 */
	public String getName() {
		
		return name;
	}
	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddy#getValue()
	 */
	public String getValue() {
		
		return value;
	}
	
	public String name = new String();
	public String value = new String();
				

	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddy#setName(java.lang.String)
	 */
	public void setName(String pName) {
		
		name = pName;
	}
	public IXMLDataObject getDataObject(){
	
		return this;
	}
	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddy#setValue(java.lang.String)
	 */
	public void setValue(String pValue) {
		
		value = new String(pValue);
	
	}
	
	
	public String outputAttributeDefinintion() {
		
		String lOutput = name + " = " + value;

		return lOutput;
	}
	
	

	
}
