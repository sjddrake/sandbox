package uk.co.neo9.apps.accounts.budget.diary;

import java.util.ArrayList;
import java.util.List;

import uk.co.neo9.apps.accounts.budget.model.BudgetCategory;
import uk.co.neo9.apps.accounts.budget.model.BudgetCategoryImpl;
import uk.co.neo9.apps.accounts.budget.model.BudgetItem;
import uk.co.neo9.apps.accounts.budget.model.BudgetItemImpl;
import uk.co.neo9.apps.accounts.budget.model.DiaryPointId;

public class BudgetDiaryBuilder {

	
	private int budgetYear = 0; // start with year for now but would be good to make flexible
	private String budgetName = "";
	
	private List<BudgetRule> rules = new ArrayList<BudgetRule>();
	
	// hide the default
	private BudgetDiaryBuilder() {
		super();
	}
	
	// use this one for the builder factory
	private BudgetDiaryBuilder(String budgetName, int year) {
		super();
		this.budgetYear = year;
		this.budgetName = budgetName;
	}
	
	
	public static BudgetDiaryBuilder getBuilder(String budgetName, int year) {
		
		return new BudgetDiaryBuilder(budgetName, year);
		
	}
	
	
	public BudgetDiaryBuilder addRule(BudgetRule rule) {
		this.rules.add(rule);
		return this;
	}
	
	
	public BudgetCategory buildBudget() {
		
		
		// check we have enough info to perform the build
		validateForBuild();
		
		// build the budget!
		// - 12 months first
		// List<BudgetItem> months = buildYear(budgetYear);
		BudgetCategoryImpl budget = new BudgetCategoryImpl(budgetName, budgetYear);
		// budget.addMonthlyTotals(months);
		
		// apply the rules, starting with the annual ones
		List<BudgetRule> monthlyRules = new ArrayList<BudgetRule>();
		for (BudgetRule rule : rules) {
			if (rule.isYearly()) {
				// apply the rules to the budget
				applyRuleToBudgetItems(budget, rule);
			} else {
				monthlyRules.add(rule);
			}
		}
		
		// now apply any monthly rules
		for (BudgetRule monthlyRule : monthlyRules) {
			applyRuleToBudgetItems(budget, monthlyRule);
		}
		
		
		// reset this builder in case code calls it again
		resetBuilder();
		
		return budget.getImmutableVersion();
	}
	
	
	

	private void resetBuilder() {
		this.budgetYear = 0;
		this.rules.clear();
	}

	
	
	private void validateForBuild() {
		// TODO Auto-generated method stub
		
	}

	private void applyRuleToBudgetItems(BudgetCategoryImpl budget, BudgetRule budgetRule) {
	
		// apply rules in order
		// - recurring monthly first
		// - non-recurring monthly
		// - year 
		
		if (budgetRule.isMonthly()) {
			
			if (budgetRule.isRecurring()) {
				
				// apply the amount in the rule to every month in the budget
				budget.setMonthlyTotals(budgetRule.getAmount());
				
				
			} else {
				// find the applicable month in the budget and apply the amount to it
				DiaryPointId monthPoint = new DiaryPointId(budgetYear, budgetRule.getMonth()); //TODO this is not v. flexible
				budget.setMonthlyTotal(budgetRule.getAmount(), monthPoint);
				
				
			}
		} else {
			
			// apply the amount straight to the budget total
			budget.setBudgetTotal(budgetRule.getAmount());
			
			// reset all the months based on the total
			budget.spreadTotalAcrossMonths();
			
		}
		
		
	}

	
	private List<BudgetItem> buildYear(int year) {
		
		List<BudgetItem> items = new ArrayList<BudgetItem>();
		for (int i = 0; i < 12; i++) {
			BudgetItemImpl item = new BudgetItemImpl(year,i,new Integer(0));
			items.add(item);
		}
		
		return items;
	}	
	
	
	
	public void setMonthlyTotals(List<BudgetItemImpl> months, Integer amount) {
	

		for (BudgetItemImpl month : months) {
			month.setAmount(amount);
		}
	}
	
//	public void setMonthlyTotal(Integer amount, int monthPointId) {
//		
//		BudgetItem item = monthlyTotals.get(new Integer(monthPointId));
//		item.setAmount(amount);
//		
//	}	
	
}
