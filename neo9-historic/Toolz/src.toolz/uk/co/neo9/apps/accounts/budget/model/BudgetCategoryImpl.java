package uk.co.neo9.apps.accounts.budget.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class BudgetCategoryImpl extends BudgetCategoryImmutable implements BudgetCategory {
	
	
	public BudgetCategoryImpl(String budgetName, int year) {
		super();
		super.year = year;
		super.budgetName = budgetName;
		List<BudgetItemImpl> months = buildYear(year);
		for (BudgetItemImpl budgetItem : months) {
			super.monthlyTotals.put(new Integer(budgetItem.getMonthPointId()), budgetItem);
		}
	}
	
	

	public void addMonthlyTotals(List<BudgetItem> totals){
		for (BudgetItem item : totals) {
			monthlyTotals.put(item.getMonthPointId(), item);
		}
	}

	public void setBudgetTotal(Integer total) {
		this.total = total;
	}

	
	
	private List<BudgetItemImpl> buildYear(int year) {
		
		List<BudgetItemImpl> items = new ArrayList<BudgetItemImpl>();
		for (int i = 0; i < 12; i++) {
			BudgetItemImpl item = new BudgetItemImpl(year,i,new Integer(0));
			items.add(item);
		}
		
		return items;
	}		
	
	public void setMonthlyTotals(Integer amount) {
		
		Collection<BudgetItem> months = monthlyTotals.values();
		
		for (BudgetItem month : months) {
			((BudgetItemImpl) month).setAmount(amount);
		}
	}

	public void setMonthlyTotal(Integer amount, DiaryPointId monthPointId) {
		
		BudgetItem item = monthlyTotals.get(monthPointId.getMonthPointId());
		((BudgetItemImpl) item).setAmount(amount);
		
	}

	
//	public Integer getCombinedMonthlyTotal() {
//		
//		Collection<BudgetItem> months = monthlyTotals.values();
//		
//		Integer combinedTotal = new Integer(0);
//		for (BudgetItem month : months) {
//			if (month.getAmount() != null) {
//				combinedTotal = combinedTotal.add(month.getAmount());
//			}
//		}
//		
//		return combinedTotal;
//	}
	
	
	public void updateBudgetTotalFromMonths() {
		
		// NOTE! This overwrites existing value
		// ... which actually makes sense
		this.total = getCombinedMonthlyTotal();

	}

	public void spreadTotalAcrossMonths() {
		
		Collection<BudgetItem> months = monthlyTotals.values();
		int noOfMonths = monthlyTotals.size();
		
		int yearlyTotal = this.total.intValue();
		int monthlyAmount = calculateMonthlyAmountFromTotal(yearlyTotal, noOfMonths);

		// need to check for the yearly amount being less than the spread
		if (monthlyAmount > 0) {
			
			BudgetItemImpl firstMonth = null;
			for (BudgetItem month : months) {
				((BudgetItemImpl) month)
						.setAmount(new Integer(monthlyAmount));
				if (firstMonth == null) {
					firstMonth = (BudgetItemImpl) month;
				}
			}
			
			// this is really naff so revisit... I'm sure I evened the curve out with the Which code
			// could make a decision about how to apply the remainder... and that decision could
			// reference central config! ;-)
			if (yearlyTotal % monthlyAmount != 0) {
				firstMonth.setAmount(new Integer(monthlyAmount
						+ (yearlyTotal % monthlyAmount)));
			}
			
		} else {
			// again, there's a decision here... one that perhaps should not be made in a single category in isolation?
			// ie - if we have odd amounts, perhaps we should spread them evenly across different months for diff categories?
			Collection<BudgetItem> splitTotalAcross = monthlyTotals.values();
			Iterator<BudgetItem> splitTotalAcrossIterator = splitTotalAcross.iterator();
			for (int i = 0; i < yearlyTotal; i++) {
				if (splitTotalAcrossIterator.hasNext()) {
					BudgetItemImpl next = (BudgetItemImpl) splitTotalAcrossIterator.next();
					next.setAmount(new Integer(1));
				}
			}
		}
		
	}

	
	private int calculateMonthlyAmountFromTotal(int totalAmount, int spread) {
		
		// int noOfFull = totalAmount / spread;
		return totalAmount / spread;
	}


	public BudgetCategory getImmutableVersion() {
		
		// get immutable versions of the BudgetItems
		List<BudgetItem> monthlyItems = this.getMonthlyItems();
		List<BudgetItem> monthlyImmutable = new ArrayList<BudgetItem>();
		for (BudgetItem budgetItem : monthlyItems) {
			BudgetItem immutabel = ((BudgetItemImpl)budgetItem).getImmutableVersion();
			monthlyImmutable.add(immutabel);
		}
		
		BudgetCategory immutable = new BudgetCategoryImmutable(super.year, monthlyImmutable, super.total, super.budgetName);
		
		return immutable;
	}


	
}
