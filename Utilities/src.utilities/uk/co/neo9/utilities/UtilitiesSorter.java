package uk.co.neo9.utilities;

/**
 * Insert the type's description here.
 * Creation date: (07/03/2002 11:43:33)
 * @author: Administrator
 */
public final class UtilitiesSorter  {
/**
 * UtilitiesSorter constructor comment.
 */
public UtilitiesSorter() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (07/03/2002 12:33:43)
 */
boolean _Compare(String pIndexedText, String pItemText, boolean pAscending) {

	boolean lResult = false;
	
	int lCompareResult = 0;
	lCompareResult = pIndexedText.compareToIgnoreCase(pItemText);

	if (pAscending) {
		if (lCompareResult > 0) {
			lResult = true;
		}
	} else {
		if (lCompareResult < 0) {
			lResult = true;
		}
	}


	return lResult;
}
/**
 * Insert the method's description here.
 * Creation date: (07/03/2002 11:46:09)
 * @param pList java.util.Vector
 * @param pSortCriteria int
 * @param pAscending boolean


 ======================= ORIGINAL TOOL CODE =======================


if pList.Items < 2 then
	return;
end if;

lItem : object;
lIndex : integer;

//	Loop through all of the items except the first one
for lLoop in 2 to plist.Items do

	lItem = plist[ lLoop ];
	lIndex = lLoop - 1;

	//	Move the current item into its correct place within the list.
	while ( lIndex >= 1 ) AND
			( self._Compare(pIndexedObject = SortableItem(plist[ lIndex ]),
							pItemObject = SortableItem(lItem),
							pAscending = pAscending,
							pSortCriteria = pSortCriteria) = TRUE ) do

		plist.ReplaceRow( lIndex + 1, (Object)(plist[ lIndex ]) );
		lIndex = lIndex - 1;
		
	end while;

	plist.ReplaceRow( lIndex + 1, (Object)(lItem) ); 
	
end for;

 
 ======================= ORIGINAL TOOL CODE =======================
 
 */
public void DazzaSort(java.util.Vector pList, int pSortCriteria, boolean pAscending) {



	
	
	
	
	
	}
/**
 * Insert the method's description here.
 * Creation date: (07/03/2002 12:14:59)


 ======================= ORIGINAL TOOL CODE =======================

 
if pTextList.Items < 2 then
	return;
end if;

lItem : TextData;
lIndex : integer;

//	Loop through all of the items except the first one
for lLoop in 2 to pTextList.Items do

	lItem = pTextList[ lLoop ];
	lIndex = lLoop - 1;

	//	Move the current item into its correct place within the list.
	while ( lIndex >= 1 ) AND
			( self._Compare(pIndexedText = pTextList[ lIndex ],
							pItemText = lItem,
							pAscending = pAscending) = TRUE ) do

		pTextList.ReplaceRow( lIndex + 1, (Object)(pTextList[ lIndex ]) );
		lIndex = lIndex - 1;
		
	end while;

	pTextList.ReplaceRow( lIndex + 1, (Object)(lItem) ); 
	
end for;


 ======================= ORIGINAL TOOL CODE =======================

 
 */
public void DazzaTextSort(java.util.Vector pTextList, boolean pAscending) {

	// no need to sort it if there's only 1!
	if (pTextList.size() < 2) {
		return;
	};

	String lItem;
	int lIndex;
	
	for (int lLoop=1; lLoop<pTextList.size(); lLoop++) {

		lItem = ((String)(pTextList.elementAt(lLoop)));
		lIndex = lLoop - 1;


		while (( lIndex >= 1 ) && 
						( this._Compare(((String)(pTextList.elementAt(lIndex))),
										lItem,
										pAscending) == true )){


			String lSwap;
			lSwap = ((String)(pTextList.remove(lIndex)));
			pTextList.add(lIndex+1,lSwap);
			lIndex = lIndex - 1;
		
		}
		
		String lMove;
		lMove = ((String)(pTextList.remove(lIndex+1)));
		pTextList.add(lIndex+1,lItem);
		

	}
	
	
}
}
