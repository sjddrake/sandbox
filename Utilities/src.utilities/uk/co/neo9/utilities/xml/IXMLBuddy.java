/*
 * Created on 17-Sep-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities.xml;

import java.util.Vector;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IXMLBuddy {
	
	public void setValue(String pValue);
	public void setName(String pName);
	public String getName();
	public String getValue();
	// this add is for the XSDGen stuff as a shortcut
	// --> should just make the XSDGen buddy implement the data interfcace too!
	public void addChildByBuddy(IXMLBuddy pBuddy);
	public void addChild(IXMLDataObject pDataObject);
	public void addAttribute(IXMLDataObject pDataObject);
	public void addAttributes(Vector pAttributes);
	public Vector getParameterValues(String pParameterName);
	public IXMLDataObject getDataObject();

}
