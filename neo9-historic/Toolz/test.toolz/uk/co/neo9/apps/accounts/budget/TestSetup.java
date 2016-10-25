package uk.co.neo9.apps.accounts.budget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.co.neo9.apps.accounts.budget.diary.BudgetDiaryBuilder;
import uk.co.neo9.apps.accounts.budget.diary.BudgetOutputBuilder;
import uk.co.neo9.apps.accounts.budget.diary.BudgetRule;
import uk.co.neo9.apps.accounts.budget.model.BudgetCategory;
import uk.co.neo9.apps.accounts.budget.model.BudgetItem;
import uk.co.neo9.apps.accounts.budget.model.DiaryPointId;


public class TestSetup {
	

	public static void main(String[] args) {
		
		final int FOCUS_YEAR = 2014;
		final String BUDGET_NAME = "Essentials";

		// setup a year
		BudgetDiaryBuilder builder = BudgetDiaryBuilder.getBuilder(BUDGET_NAME,FOCUS_YEAR);
		
		
		// get a budget rule
		BudgetRule budgetRule = BudgetRule.buildMonthlyRule();
		budgetRule.setAmount(120);
		DiaryPointId pointId = new DiaryPointId();
		pointId.setMonth(Calendar.MARCH);
		pointId.setYear(FOCUS_YEAR); // !!!!! not sure about having to set the year too!!!
		budgetRule.setPointId(pointId);
		
		
		// apply rule to budget
		builder.addRule(budgetRule);
		

		
		// get a budget rule
		 budgetRule = BudgetRule.buildMonthlyRule();
		budgetRule.setAmount(73);
		 pointId = new DiaryPointId();
		pointId.setMonth(Calendar.JUNE);
		pointId.setYear(FOCUS_YEAR); // !!!!! not sure about having to set the year too!!!
		budgetRule.setPointId(pointId);
		
		
		// apply rule to budget
		builder.addRule(budgetRule);
		
		
		// get a budget rule
		budgetRule = BudgetRule.buildYearlyRule();
		budgetRule.setAmount(10);
//		 pointId = new DiaryPointId();
//		pointId.setMonth(Calendar.MARCH);
//		pointId.setYear(FOCUS_YEAR); // !!!!! not sure about having to set the year too!!!
//		budgetRule.setPointId(pointId);
		
		
		// apply rule to budget
		builder.addRule(budgetRule);		
		
		
		
		// get the budget!
		BudgetCategory budget2014 = builder.buildBudget();
		
		
		// output the budget
		String budgetOutput = BudgetOutputBuilder.outputBudgetAsCSV(budget2014);
		String csvHeader = BudgetOutputBuilder.outputBudgetCSVHeader();
		
		System.out.println(csvHeader);
		System.out.println(budgetOutput);
	}




	
}
