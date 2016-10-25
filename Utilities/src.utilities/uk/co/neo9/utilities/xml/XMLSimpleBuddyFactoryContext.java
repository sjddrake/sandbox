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
public class XMLSimpleBuddyFactoryContext
	implements IXMLBuddyFactoryContext {
		
		public String key = new String();
		private boolean isComplex = false;

	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddyFactoryContext#getKey()
	 */
	public String getKey() {
		// Auto-generated method stub
		return key;
	}

	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddyFactoryContext#setKey(java.lang.String)
	 */
	public void setKey(String pKey) {
		// Auto-generated method stub
		key = new String(pKey);

	}

	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddyFactoryContext#getIsComplex()
	 */
	public boolean getIsComplex() {
		return isComplex;
	}

	/* (non-Javadoc)
	 * @see uk.co.neo9.utilities.IXMLBuddyFactoryContext#setIsComplex(boolean)
	 */
	public void setIsComplex(boolean pIsComplex) {
		isComplex = pIsComplex;
		
	}

}
