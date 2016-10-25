package uk.co.neo9.apps.accounts.budget.model;

import java.math.BigDecimal;

public class BudgetItemImmutable extends DiarisedItem implements BudgetItem {
	
	protected Integer amount;

	public BudgetItemImmutable(int year, int month, Integer amount) {
		super(year, month);
		this.amount = amount;
	}

	public Integer getAmount() {
		return amount;
	}


}
