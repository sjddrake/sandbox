package uk.co.neo9.apps.accounts.wiptracker;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class WIPTrackerContainer {

	
	private Map<String, WIPTrackerCategoryContainer> categorisedTotals 
				= new Hashtable<String, WIPTrackerCategoryContainer>();
	
	
	
	public void addCategory(WIPTrackerCategoryContainer categoryContainer){
		
		// first check it has a category
		categoryContainer.isCategoryDefined(true);
		
		// see if the category is already in the table
		String cat = categoryContainer.getCategory().toUpperCase();
		boolean exists = categorisedTotals.containsKey(cat);
		
		if (exists) {
			throw new InvalidParameterException("Trying to add a category that already exists - cat:"+cat);
		}
		
		categorisedTotals.put(cat,categoryContainer);
		
	}
	
	public WIPTrackerCategoryContainer getCategory(String category) {
		WIPTrackerCategoryContainer container = categorisedTotals.get(category.toUpperCase());
		if (container == null) {
			container = new WIPTrackerCategoryContainer();
			container.setCategory(category);
		}
		return container;
	}


	public void filterTransactions(int filterFieldId, Object filterValue) {
		
//	Nov 2012 - wanting to introduce more flexible fitering such as a transaction amount but don't 
//				want to get too carried away right now!
//		
//		// TODO Auto-generated method stub
//		Set<Entry<String, WIPTrackerCategoryContainer>> transactions = categorisedTotals.entrySet();
//		for (Entry<String, WIPTrackerCategoryContainer> entry : transactions) {
//			transactionsContainer = entry.getValue();
//		}
		
		
	}
	
}
