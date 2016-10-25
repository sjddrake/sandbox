package uk.co.neo9.apps.accounts.budget.load;

import java.util.List;

public class BudgetLoadModel {

	private int yearlyTotal;
	private int year;
	private String budgetName;
	private List<String> monthlyTotals;
	
	
	public int getYearlyTotal() {
		return yearlyTotal;
	}
	public void setYearlyTotal(int yearlyTotal) {
		this.yearlyTotal = yearlyTotal;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getBudgetName() {
		return budgetName;
	}
	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}
	public List<String> getMonthlyTotals() {
		return monthlyTotals;
	}
	public void setMonthlyTotals(List<String> monthlyTotals) {
		this.monthlyTotals = monthlyTotals;
	}

	
}
