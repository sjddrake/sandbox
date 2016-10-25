/*
 * Created on 15-Jan-04
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
public class XMLSimpleBuddyFactory implements IXMLBuddyFactory {

	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddyFactory#getXMLBuddy(uk.co.neo9.utilities.IXMLBuddyFactoryContext)
	 */
	public IXMLBuddy getXMLBuddy(IXMLBuddyFactoryContext pContext) {
		
		// the standard factory will just use the tag name
		// to determine the buddy to return - so call it
		return getXMLBuddy(pContext.getKey());
	}

	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddyFactory#getXMLBuddy(java.lang.String)
	 */
	public IXMLBuddy getXMLBuddy(String pTag) {
		
		return null;
	}

}
