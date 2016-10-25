package uk.co.neo9.apps.accounts.budget.model;

import java.math.BigDecimal;
import java.util.List;


public interface BudgetCategory {

	public Integer getCombinedMonthlyTotal();
	public List<BudgetItem> getMonthlyItems();
	public String getBudgetName();
	
}
