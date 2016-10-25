package uk.co.neo9.utilities;

/**
 * Insert the type's description here.
 * Creation date: (11/03/2002 14:15:36)
 * @author: Guy Wilson
 */
public class UtilitiesAbstractSortKey {
/**
 * AbstractSortKey constructor comment.
 */
public UtilitiesAbstractSortKey() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (11/03/2002 14:20:11)
 * @return java.lang.String
 */
public String getKeyAttribute(Object pItem) {
	return null;
}
/***************************************************************************

	Description :	This method should return TRUE if the two items are
					deemed to be equal. Sub-classes can override this
					method.


	--- Revision History ---

	Date		Who	Description
	-----------	---	-----------------------------------------------------
	24-Aug-2000	GLW	Created the method.

***************************************************************************/

public boolean isEqualTo(Object pItem1, Object pItem2) {
	String lItem1 = getKeyAttribute(pItem1);
	String lItem2 = getKeyAttribute(pItem2);

	boolean lIsEqual;

	if (lItem1 != null && lItem2 != null) {
	    lIsEqual = lItem1.equals(lItem2);
	}
	else {
		if (lItem1 == null && lItem2 == null) {
			lIsEqual = true;
		}
		else {
			lIsEqual = false;
		}
	}

	return lIsEqual;
}
/***************************************************************************

	Description :	This method should return TRUE if pItem1 is deemed to be
					greater than pItem2. Sub-classes can override this
					method.


	--- Revision History ---

	Date		Who	Description
	-----------	---	-----------------------------------------------------
	24-Aug-2000	GLW	Created the method.

***************************************************************************/

public boolean isGreaterThan(Object pItem1, Object pItem2) {
	String lItem1 = getKeyAttribute(pItem1);
	String lItem2 = getKeyAttribute(pItem2);

	boolean lIsGreaterThan;

	if (lItem1 != null && lItem2 != null) {
	    if (lItem1.compareToIgnoreCase(lItem2) > 0)
		    lIsGreaterThan = true;
		else
			lIsGreaterThan = false;
	}
	else {
	    lIsGreaterThan = false;
	}

	return lIsGreaterThan;
}
/***************************************************************************

	Description :	This method should return TRUE if pItem1 is deemed to be
					less than pItem2. Sub-classes must override this
					method.


	--- Revision History ---

	Date		Who	Description
	-----------	---	-----------------------------------------------------
	24-Aug-2000	GLW	Created the method.

***************************************************************************/

public boolean isLessThan(Object pItem1, Object pItem2) {
	String lItem1 = getKeyAttribute(pItem1);
	String lItem2 = getKeyAttribute(pItem2);

	boolean lIsLessThan;

	if (lItem1 != null && lItem2 != null) {
	    if (lItem1.compareToIgnoreCase(lItem2) < 0)
		    lIsLessThan = true;
		else
			lIsLessThan = false;
	}
	else {
	    lIsLessThan = false;
	}

	return lIsLessThan;
}
}
