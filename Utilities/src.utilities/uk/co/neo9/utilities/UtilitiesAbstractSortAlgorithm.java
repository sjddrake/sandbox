package uk.co.neo9.utilities;

import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (11/03/2002 16:26:15)
 * @author: Guy Wilson
 */
public class UtilitiesAbstractSortAlgorithm {
/**
 * AbstractSortAlgorithm constructor comment.
 */
public UtilitiesAbstractSortAlgorithm() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (11/03/2002 16:34:51)
 * @param pArrayToSort java.lang.Object[]
 * @param pSortKey SortAlgorithms.AbstractSortKey
 */
public ArrayList sort(ArrayList pArrayToSort, UtilitiesAbstractSortKey pSortKey) {
	return null;
}
/**
 * Insert the method's description here.
 * Creation date: (11/03/2002 16:34:51)
 * @param pArrayToSort java.lang.Object[]
 * @param pSortKey SortAlgorithms.AbstractSortKey
 */
public Vector sort(Vector pVectorToSort, UtilitiesAbstractSortKey pSortKey) {

	// convert the to sort into a List Type
	ArrayList lList = new ArrayList(pVectorToSort);
	
	// call the proper sort
	sort(lList, pSortKey);

	// copy the details back over
	pVectorToSort.clear();
	pVectorToSort.addAll(lList);
	
	return pVectorToSort;
}
}
