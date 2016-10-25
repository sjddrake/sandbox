package uk.co.neo9.utilities;

/**
 * Insert the type's description here.
 * Creation date: (12/03/2002 15:49:04)
 * @author: Guy Wilson
 */
public class UtilitiesStringSortKey extends UtilitiesAbstractSortKey {
/**
 * StringSortKey constructor comment.
 */
public UtilitiesStringSortKey() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (12/03/2002 15:52:03)
 * @return java.lang.String
 * @param pItem java.lang.Object
 */
public String getKeyAttribute(Object pItem) {
	return (String)(pItem);
}
}
