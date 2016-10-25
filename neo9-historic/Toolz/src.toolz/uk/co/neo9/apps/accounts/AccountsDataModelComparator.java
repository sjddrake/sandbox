package uk.co.neo9.apps.accounts;

import java.util.Comparator;

public class AccountsDataModelComparator implements Comparator {
	
	public boolean reverseDateOrder = false;
	
	
	public AccountsDataModelComparator() {
		super();
	}

	public AccountsDataModelComparator(boolean useReverseDateOrder) {
		super();
		reverseDateOrder = useReverseDateOrder;
	}
	
	public int compare(Object o1, Object o2) {

		int result = 0;

		// do some type checks here?
		AccountsDataModel details1 = (AccountsDataModel)o1;
		if (details1.isProcessed() == false) {
			details1.processData();
		}
		
		AccountsDataModel details2 = (AccountsDataModel)o2;
		if (details2.isProcessed() == false) {
			details2.processData();
		}
		
		if ((details1.date != null) && (details2.date != null)){
			if (reverseDateOrder) {
				result = details2.date.compareTo(details1.date);
			} else {
				result = details1.date.compareTo(details2.date);
			}
		}
		
		return result;
	}

}
