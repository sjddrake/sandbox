package uk.co.neo9.utilities;

import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (11/03/2002 16:47:15)
 * @author: Guy Wilson
 */
public class UtilitiesQuickSort extends UtilitiesAbstractSortAlgorithm {
/**
 * QuickSort constructor comment.
 */
public UtilitiesQuickSort() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (12/03/2002 15:28:10)
 * @param pArrayToSort com.sun.java.util.collections.ArrayList
 * @param pSortKey SortAlgorithms.AbstractSortKey
 * @param pStartIndex int
 * @param pEndIndex int
 */
private void _quickSort(ArrayList pArray, UtilitiesAbstractSortKey pSortKey, int pStartIndex, int pEndIndex) {
/***************************************************************************

	Description :	This method actually does the sort, this is a recursive
					method.
					
					A quick overview of how the QuickSort algorithm works :
					
					1)	A pivot element is chosen, typically this should be
						in the centre of the unsorted array.
					2)	A left and a right index are moved from either end
						of the unsorted array towards the pivot (centre)
						element. 
					3)	Any elements which are greater than the pivot 
						element and reside before (to the left of) the 
						pivot element are swapped to the right side of 
						the pivot element. Similarly, any elements which 
						are less than the pivot element and reside after 
						(to the right of) the pivot element are swapped 
						to the left side of the pivot element.
					4)	Steps 2 & 3 are repeated until all elements are
						at least on the correct side of the pivot element
						if not necessarily in the correct order.
					5)	The algorithm is called recursively for both parts 
						of the array (elements less than the pivot element
						and elements greater than the pivot element).


	--- Revision History ---

	Date		Who	Description
	-----------	---	-----------------------------------------------------
	29-Aug-2000	GLW	Created the method.

***************************************************************************/

if (pStartIndex < pEndIndex) {
	/*
	** Choose a pivot element, the most efficient choice
	** is the centre of the the start/end range...
	*/
	Object lPivotElement;
	lPivotElement = pArray.get(pStartIndex + ((pEndIndex - pStartIndex) / 2));
	
	/*
	** Initially set the left and right indices...
	*/
	int lLeftIndex = pStartIndex;
	int lRightIndex = pEndIndex;
	
	/*
	** The main sort loop...
	*/
	while (lLeftIndex < lRightIndex) {
	
		/*
		** Moving right, find the first element that is
		** greater then or equal to the pivot element...
		*/
		while (pSortKey.isLessThan(
				pArray.get(lLeftIndex), 
				lPivotElement ) &&
			  	lLeftIndex <= pEndIndex)
		{
			lLeftIndex++;
		}
	
		/*
		** Moving left, find the first element that is
		** less then or equal to the pivot element...
		*/
		while (pSortKey.isGreaterThan(
				pArray.get(lRightIndex), 
				lPivotElement ) &&
			  lRightIndex >= pStartIndex)
		{
			lRightIndex--;
		};
	
		/*
		** If the left and right indices have not crossed each other, 
		** swap the elements. Remember, that at this point, the left
		** index points to an element that is >= the pivot element and
		** the right index points to an element that is <= the pivot
		** element...
		*/
		if (lLeftIndex <= lRightIndex) {
			_swap(
				pArray, 
				lLeftIndex, 
				lRightIndex );
			
			lLeftIndex++;
			lRightIndex--;
		}
	}

	/*
	** At this point all elements are at least on their correct side
	** of the pivot element, now sort each of the two halfs in the
	** same way...
	*/
	_quickSort(
		pArray, 
		pSortKey, 
		pStartIndex, 
		lRightIndex );

	_quickSort(
		pArray, 
		pSortKey, 
		lLeftIndex, 
		pEndIndex );
}
}
/**
 * Insert the method's description here.
 * Creation date: (12/03/2002 15:39:24)
 * @param pArray java.util.ArrayList
 * @param pIndex1 int
 * @param pIndex2 int
 */
private void _swap(ArrayList pArray, int pIndex1, int pIndex2) {
/***************************************************************************

	Description :	This method swaps the two array items passed in.


	--- Revision History ---

	Date		Who	Description
	-----------	---	-----------------------------------------------------
	29-Aug-2000	GLW	Created the method.

***************************************************************************/

Object lTemporaryObject = pArray.get(pIndex1);

pArray.set(pIndex1, pArray.get(pIndex2));
pArray.set(pIndex2, lTemporaryObject);
}
/**
 * Insert the method's description here.
 * Creation date: (12/03/2002 15:26:21)
 * @param pArrayToSort java.util.ArrayList
 * @param pSortKey SortAlgorithms.AbstractSortKey
 */
public ArrayList sort(java.util.ArrayList pArrayToSort, UtilitiesAbstractSortKey pSortKey) {
	_quickSort(
		pArrayToSort, 
		pSortKey, 
		0, 
		pArrayToSort.size() - 1 );

	return pArrayToSort;
}
/**
 * Insert the method's description here.
 * Creation date: (21/03/2002 14:21:09)
 */
public void stringSort(ArrayList pList) {

	UtilitiesStringSortKey lStringSortKey = new UtilitiesStringSortKey();
	this.sort(pList, lStringSortKey);
}
}
