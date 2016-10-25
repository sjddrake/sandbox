package uk.co.neo9.apps.accounts.budget.model;


public class BudgetItemImpl extends BudgetItemImmutable implements BudgetItem {
	
	public BudgetItemImpl(int year, int month, Integer amount) {
		super(year, month, amount);
	}

	public void setAmount(Integer amount) {
		super.amount = amount;
	}
	
	
	public BudgetItem getImmutableVersion() {
	    return new BudgetItemImmutable( this.getYear(),
	                                    this.getMonth(),
	                                    this.getAmount());
	}
}
