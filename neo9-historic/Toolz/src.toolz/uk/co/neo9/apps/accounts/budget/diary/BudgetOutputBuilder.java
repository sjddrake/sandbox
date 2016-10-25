package uk.co.neo9.apps.accounts.budget.diary;

import java.util.Collection;
import java.util.List;

import uk.co.neo9.apps.accounts.budget.model.BudgetCategory;
import uk.co.neo9.apps.accounts.budget.model.BudgetItem;

public class BudgetOutputBuilder {
	
	public static String outputBudgetAsCSV(BudgetCategory budget) {
		
		Integer budgetTotal = budget.getCombinedMonthlyTotal();
		
		List<BudgetItem> monthlyBudgetItems = budget.getMonthlyItems();
		

		StringBuilder row2 = new StringBuilder();
	
		row2.append(budget.getBudgetName());
		row2.append(BudgetDiaryCommonConstants.COMMA);
		
		for (BudgetItem budgetItem : monthlyBudgetItems) {
			row2.append(budgetItem.getAmount());
			row2.append(BudgetDiaryCommonConstants.COMMA);
		}
		row2.append(budgetTotal);
		
		return row2.toString();
	}

	
	public static String outputBudgetCSVHeader() {
		
		// this version just o/p's the months in the year
		// ... make it more flexible in the future
		
		Collection<String> monthNames = BudgetDiaryCommonConstants.getAllMonthNamesShort();
		StringBuilder header = new StringBuilder("Category");
		for (String monthName : monthNames) {
			header.append(BudgetDiaryCommonConstants.COMMA);
			header.append(monthName);
		}
		header.append(BudgetDiaryCommonConstants.COMMA);
		header.append("Total");
		
		return header.toString();
	}
	
	
}
