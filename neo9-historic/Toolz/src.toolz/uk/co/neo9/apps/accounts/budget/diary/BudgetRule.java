package uk.co.neo9.apps.accounts.budget.diary;

import java.util.Calendar;

import uk.co.neo9.apps.accounts.budget.model.DiarisedItem;

public class BudgetRule extends DiarisedItem{

	private boolean isRecurring = false;
	private int timeUnit = Calendar.YEAR; // should have a safer default
	private Integer amount;
	
	public void makeRecurring() {
		this.isRecurring = true;
	}
	
	public void setYearly() {
		this.timeUnit = Calendar.YEAR;
	}
	
	public void setMonthly() {
		this.timeUnit = Calendar.MONTH;
	}
	
	
	public boolean isYearly() {
		return (this.timeUnit == Calendar.YEAR);
	}
	
	public boolean isMonthly() {
		return (this.timeUnit == Calendar.MONTH);
	}	
	
	
	public static BudgetRule buildYearlyRule() {
		BudgetRule rule = new BudgetRule();
		rule.setYearly();
		return rule;
	}
	
	public static BudgetRule buildMonthlyRule() {
		BudgetRule rule = new BudgetRule();
		rule.setMonthly();
		return rule;
	}	
	
	
	//
	// Getters & Setters
	//
	
	public boolean isRecurring() {
		return isRecurring;
	}
//	public void setRecurring(boolean isRecurring) {
//		this.isRecurring = isRecurring;
//	}
//	public int getTimeUnit() {
//		return timeUnit;
//	}
//	public void setTimeUnit(int timeUnit) {
//		this.timeUnit = timeUnit;
//	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public void setAmount(int amount) {
		this.setAmount(new Integer(amount));
	}

	public Integer getAmount() {
		return this.amount;
	}	
}
