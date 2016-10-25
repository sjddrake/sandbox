package uk.co.neo9.apps.accounts.budget.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Budget {

	protected Map<String, BudgetCategory> categories = new HashMap<String, BudgetCategory>();
	protected int year;	
	
	
	public void addCategory(BudgetCategory budgetCategory) {
		
		//TODO validation!
		
		categories.put(budgetCategory.getBudgetName(), budgetCategory);
		
	}
	
	
	
	public BudgetCategory getCategory(String categoryName) {
		return categories.get(categoryName);
	}
	
	
	public List<BudgetCategory> getAllCategories() {
		
		Collection<String> itemIds = categories.keySet();
		
		Object[] itemIdsArray = itemIds.toArray();
		Arrays.sort(itemIdsArray);
		
		List<BudgetCategory> items = new ArrayList<BudgetCategory>();
		
		for (int i = 0; i < itemIdsArray.length; i++) {
			String itemId = (String) itemIdsArray[i];
			BudgetCategory item = categories.get(itemId);
			items.add(item);
		}
		
		return items;
		
	}
	
}
