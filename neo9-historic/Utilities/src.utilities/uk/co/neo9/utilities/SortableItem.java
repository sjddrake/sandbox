package uk.co.neo9.utilities;

/**
 * Insert the type's description here.
 * Creation date: (07/03/2002 11:31:18)
 * @author: Administrator
 */
public interface SortableItem {
/**
 * Insert the method's description here.
 * Creation date: (07/03/2002 11:31:35)
 */
boolean sortGreaterThan(SortableItem pCompareTo, int pSortCriteria);
/**
 * Insert the method's description here.
 * Creation date: (07/03/2002 11:35:28)
 */
boolean sortLessThan(SortableItem pCompareTo, int pSortCriteria);
}
