package uk.co.neo9.apps.accounts.budget;



import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.co.neo9.apps.accounts.budget.diary.BudgetDiaryBuilder;
import uk.co.neo9.apps.accounts.budget.diary.BudgetOutputBuilder;
import uk.co.neo9.apps.accounts.budget.load.BudgetLoadParams;
import uk.co.neo9.apps.accounts.budget.load.BudgetLoader;
import uk.co.neo9.apps.accounts.budget.model.BudgetCategory;


public class Test2014Budget {

	final static int FOCUS_YEAR = 2014;
	
	
	
	//@Test
	public void test_January2014_bills() {
		
		// get the budget
		BudgetDiaryBuilder builder = BudgetTestDataVendor.getBuilderForBills(FOCUS_YEAR);
		BudgetCategory budget2014 = builder.buildBudget();
				
	
		
		// assert correctness
	
		
		
		// output the budget
		String budgetOutput = BudgetOutputBuilder.outputBudgetAsCSV(budget2014);
		String csvHeader = BudgetOutputBuilder.outputBudgetCSVHeader();
		
		System.out.println(csvHeader);
		System.out.println(budgetOutput);		

	}

	
	@Test
	public void test_Annual() {
		
		// get the budget
		BudgetDiaryBuilder builder = BudgetTestDataVendor.getBuilderForBillsAnnual(FOCUS_YEAR);
		BudgetCategory budget2014 = builder.buildBudget();
				
	
		
		// assert correctness
	
		
		
		// output the budget
		String budgetOutput = BudgetOutputBuilder.outputBudgetAsCSV(budget2014);
		String csvHeader = BudgetOutputBuilder.outputBudgetCSVHeader();
		
		System.out.println(csvHeader);
		System.out.println(budgetOutput);		

	}	
	
	
	
	@Test
	public void test_2014_fullBudget() {
		
		
		int focusYear = FOCUS_YEAR;
		
		// get the budget categories from the builder
		List<BudgetCategory> categories = new ArrayList<BudgetCategory>();
		BudgetDiaryBuilder builder = BudgetTestDataVendor.getBuilderForBills(focusYear);
		BudgetCategory bills = builder.buildBudget();
		categories.add(bills);
	
		// less verbose!
		categories.add(BudgetTestDataVendor.getBuilderForFood(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForBillsAnnual(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForHomewares(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForPetrol(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForMisc(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForTravel(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForHouseMaint(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForCarMaint(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForMedical(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForDIY(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForEnts(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForToys(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForPresents(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForKidz(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForKidsBirthday(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForSavingBP(focusYear).buildBudget());
		categories.add(BudgetTestDataVendor.getBuilderForSavingHol(focusYear).buildBudget());

		
		
		// assert correctness
	
		
		
		// output the budget

		
		System.out.println(BudgetOutputBuilder.outputBudgetCSVHeader());
		for (BudgetCategory budgetCategory : categories) {
			System.out.println(BudgetOutputBuilder.outputBudgetAsCSV(budgetCategory));
		}
			

	}
	
	
	@Test
	public void testLoadfromCSV() {
		
		final String file = "/Users/mole/SwapZones/Dropbox/dev/test/Budget/2014-Budget.csv";
		BudgetLoadParams params = new BudgetLoadParams(file);
		new BudgetLoader().loadBudget(params );
		
	}
	
}
