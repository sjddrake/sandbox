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
public interface IXMLBuddyFactory {

	public IXMLBuddy getXMLBuddy(IXMLBuddyFactoryContext pContext);
	public IXMLBuddy getXMLBuddy(String pTag);

}
