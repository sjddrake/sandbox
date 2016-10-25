package uk.co.neo9.apps.accounts.wiptracker;

import uk.co.neo9.utilities.UtilitiesComparatorPlusAdapter;

public class WIPTrackerTotalsComparator extends UtilitiesComparatorPlusAdapter{

	
//    protected final int BEFORE = -1;
//    protected final int EQUAL = 0;
//    protected final int AFTER = 1;
//    protected final int COMPARE_FIELDS = 999;
	
//  SCOOBYDO we should be able to move to super
//  SCOOBYDO work out good default initialisation strategy
//  SCOOBYDO create an interface as well as an adapter
//    SCOOBYDO chagne the field identity to int... think about tying in utils base object
    // with a field ID to field name conversion method... would help with the dump methods to?
    // SCOOBYDO The utils base object shouls also allow easy tie in to log4j but not rely on it :-)

    
	
	public int compare(Object o1, Object o2, int fieldToUse) {

	    int result = EQUAL;
	    
	    WIPTrackerTotalsModel model0 = (WIPTrackerTotalsModel)o1;
	    WIPTrackerTotalsModel model1 = (WIPTrackerTotalsModel)o2;
	    
	    // do obvious initial checks - null
	    // SCOOBYDO refactor this out too!
	    result = compareForNullValues(o1,o2);
	    if (result != COMPARE_FIELDS) {
			return result;
		}
	    
	    // compare by field now
	    
	    //... the comparison implementation is based on the field chosen and its type
	    int fidToUse = getFieldToUseId();
		switch (fidToUse) {
		case WIPTrackerTotalsModel.FID_AMOUNT: 
			result = compareFields(model0.getAmount(),model1.getAmount());
			break;
		case WIPTrackerTotalsModel.FID_CATEGORY: 
			result = compareFields(model0.getCategory(),model1.getCategory());
			break;
		case WIPTrackerTotalsModel.FID_CATEGORY_CASE_INSENSITIVE: 
			result = compareStringFieldsCaseInsensitive(model0.getCategory(),model1.getCategory());
			break;
		case WIPTrackerTotalsModel.FID_DATE:
			result = compareFields(model0.getDate(),model1.getDate());
			break;
		case WIPTrackerTotalsModel.FID_WHERE:
			result = compareFields(model0.getWhere(),model1.getWhere());
			break;
		default:
			break;
		}
	    
		return result;
	}


	
	public void initialiseFieldToUse() {
		setFieldToUseId(WIPTrackerTotalsModel.FID_CATEGORY);
		
	}


}
