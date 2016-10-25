package uk.co.neo9.apps.accounts.budget.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BudgetCategoryImmutable implements BudgetCategory {

	protected Map<Integer, BudgetItem> monthlyTotals = new HashMap<Integer, BudgetItem>();
	protected Integer total; // NOTE - decided to store pence not pounds... far simpler!
	protected int year;
	protected String budgetName;

	protected BudgetCategoryImmutable() {
		super();
	}
	
	
	public BudgetCategoryImmutable(int year, 
						   List<BudgetItem> monthlyImmutable,
						   Integer total, 
						   String budgetName) {

		
		super();
		this.year = year;
		this.total = total;
		this.budgetName = budgetName;
		
		for (BudgetItem budgetItem : monthlyImmutable) {
			this.monthlyTotals.put(new Integer(budgetItem.getMonthPointId()), budgetItem);
		}
		
	}



	public Integer getCombinedMonthlyTotal() {
		
		Collection<BudgetItem> months = monthlyTotals.values();
		
		int combinedTotal = 0;
		for (BudgetItem month : months) {
			if (month.getAmount() != null) {
				combinedTotal = combinedTotal + month.getAmount().intValue();
			}
		}
		
		return new Integer(combinedTotal);
	}
	


	public List<BudgetItem> getMonthlyItems() {
		
		Collection<Integer> itemIds = monthlyTotals.keySet();
		
		Object[] itemIdsArray = itemIds.toArray();
		Arrays.sort(itemIdsArray);
		
		List<BudgetItem> monthlyItems = new ArrayList<BudgetItem>();
		
		for (int i = 0; i < itemIdsArray.length; i++) {
			Integer itemId = (Integer) itemIdsArray[i];
			BudgetItem monthlyTotal = monthlyTotals.get(itemId);
			monthlyItems.add(monthlyTotal);
		}
		
		return monthlyItems;
	}


	@Override
	public String getBudgetName() {
		return this.budgetName;
	}
	
}
