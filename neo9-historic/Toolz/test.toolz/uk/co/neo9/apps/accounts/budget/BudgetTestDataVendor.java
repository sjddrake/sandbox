package uk.co.neo9.apps.accounts.budget;

import org.joda.time.DateTime;

import uk.co.neo9.apps.accounts.budget.diary.BudgetDiaryBuilder;
import uk.co.neo9.apps.accounts.budget.diary.BudgetRule;
import uk.co.neo9.apps.accounts.budget.model.DiaryPointId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class BudgetTestDataVendor {


//    public static void main(String[] args) {
//
//       // generateReportPeriodSQLStaticData();
//    }

	
	
	
	public static BudgetDiaryBuilder getBuilderForBills(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"Bills",1805);
	}
	
	public static BudgetDiaryBuilder getBuilderForFood(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"Food",580);
	}
	
	public static BudgetDiaryBuilder getBuilderForHomewares(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"Homewares",10);
	}
	
	public static BudgetDiaryBuilder getBuilderForPetrol(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"Petrol",100);
	}
	
	public static BudgetDiaryBuilder getBuilderForMisc(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"Misc",40);
	}
	
	public static BudgetDiaryBuilder getBuilderForTravel(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"Travel",10);
	}
	
	public static BudgetDiaryBuilder getBuilderForHouseMaint(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"HouseMaint",30);
	}
	
	public static BudgetDiaryBuilder getBuilderForCarMaint(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"CarMaint",50);
	}
	
	public static BudgetDiaryBuilder getBuilderForBillsAnnual(final int focusYear) {
		return buildBudgetYearlyTotal(focusYear,"BillsAnnual",2304);
	}
	
	public static BudgetDiaryBuilder getBuilderForMedical(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"Medical",15);
	}
	
	public static BudgetDiaryBuilder getBuilderForDIY(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"DIY",40);
	}
	
	public static BudgetDiaryBuilder getBuilderForEnts(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"Ents",90);
	}
	
	public static BudgetDiaryBuilder getBuilderForToys(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"Toys",10);
	}
	
	public static BudgetDiaryBuilder getBuilderForPresents(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"Presents",20);
	}
	
	public static BudgetDiaryBuilder getBuilderForKidz(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"Kidz",100);
	}
	
	public static BudgetDiaryBuilder getBuilderForKidsBirthday(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"KidsBirthday",20);
	}
	
	public static BudgetDiaryBuilder getBuilderForSavingBP(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"SavingBP",50);
	}
	
	public static BudgetDiaryBuilder getBuilderForSavingHol(final int focusYear) {
		return buildBudgetMonthlyTotal(focusYear,"SavingHol",150);
	}
	
	
	
	
	
	
	
	
	public static BudgetDiaryBuilder buildBudgetYearlyTotal(final int focusYear, final String budgetName, int amount) {
		
		final String BUDGET_NAME = budgetName;

		// setup a year
		BudgetDiaryBuilder builder = BudgetDiaryBuilder.getBuilder(BUDGET_NAME,focusYear);

		// get a budget rule
		BudgetRule budgetRule = BudgetRule.buildYearlyRule();
		budgetRule.setAmount(amount);

		
		// apply rule to budget
		builder.addRule(budgetRule);		
		
		
		return builder;
	}		
	
	public static BudgetDiaryBuilder buildBudgetMonthlyTotal(final int focusYear, final String budgetName, int amount) {
		
		final String BUDGET_NAME = budgetName;

		// setup a year
		BudgetDiaryBuilder builder = BudgetDiaryBuilder.getBuilder(BUDGET_NAME,focusYear);

		// get a budget rule
		BudgetRule budgetRule = BudgetRule.buildMonthlyRule();
		budgetRule.setAmount(amount);
		budgetRule.makeRecurring();

		
		// apply rule to budget
		builder.addRule(budgetRule);		
		
		
		return builder;
	}		
	
	
	
	public static BudgetDiaryBuilder buildBudgetForSingleMonth(final int focusYear, final String budgetName, int amount) {
		
		final String BUDGET_NAME = budgetName;

		// setup a year
		BudgetDiaryBuilder builder = BudgetDiaryBuilder.getBuilder(BUDGET_NAME,focusYear);

		// get a budget rule
		BudgetRule budgetRule = BudgetRule.buildMonthlyRule();
		budgetRule.setAmount(amount);

		
		// apply rule to budget
		builder.addRule(budgetRule);		
		
		
		return builder;
	}		
	
	
	
	
	public static BudgetDiaryBuilder buildBudgetExample(final int focusYear) {
		
		final String BUDGET_NAME = "Example";

		// setup a year
		BudgetDiaryBuilder builder = BudgetDiaryBuilder.getBuilder(BUDGET_NAME,focusYear);
		
		
		// get a budget rule
		BudgetRule budgetRule = BudgetRule.buildMonthlyRule();
		budgetRule.setAmount(120);
		DiaryPointId pointId = new DiaryPointId();
		pointId.setMonth(Calendar.MARCH);
		pointId.setYear(focusYear); // !!!!! not sure about having to set the year too!!!
		budgetRule.setPointId(pointId);
		
		
		// apply rule to budget
		builder.addRule(budgetRule);

		
		// get a budget rule
		 budgetRule = BudgetRule.buildMonthlyRule();
		budgetRule.setAmount(73);
		 pointId = new DiaryPointId();
		pointId.setMonth(Calendar.JUNE);
		pointId.setYear(focusYear); // !!!!! not sure about having to set the year too!!!
		budgetRule.setPointId(pointId);
		
		
		// apply rule to budget
		builder.addRule(budgetRule);
		
		
		// get a budget rule
		budgetRule = BudgetRule.buildYearlyRule();
		budgetRule.setAmount(10);

		
		// apply rule to budget
		builder.addRule(budgetRule);		
		
		
		return builder;
	}	
	
	

//    private static void generateReportPeriodSQLStaticData() {
//
//        List<ReportPeriodEntries> reportPeriods = getTestReportInstances();
//        for (ReportPeriodEntries period : reportPeriods) {
//            String sqlInsertStatement = generateSQLInsertFromReportPeriodEntries(period);
//            System.out.println(sqlInsertStatement);
//        }
//
//    }


//    private static String generateSQLInsertFromReportPeriodEntries(ReportPeriodEntries period) {
//
//        final String sqlStatementTemplate = "insert into finance.PERIOD " +
//                "([name], [periodStart], [periodEnd], [creationDate], [modifiedDate]) values " +
//                "('%s', '%s', '%s', GETDATE(), GETDATE()); ";
//
//        final String dateFormatString = "%1$tY-%1$tm-%1$te";
//
//        Object[] args = {period.getName(),
//                String.format(dateFormatString, period.getStartDate()),
//                String.format(dateFormatString, period.getEndDate())};
//
//        String sqlStatement = String.format(sqlStatementTemplate, args);
//
//        return sqlStatement;
//    }


//    public static List<ReportPeriodEntries> getTestReportInstances() {
//
//        final int numberOfYearsCovered = 6;
//        final int baseYear = 2010;
//        List<ReportPeriodEntries> reports = generateTestReportInstances(baseYear, numberOfYearsCovered);
//
//        return reports;
//
//    }


//    private static List<ReportPeriodEntries> generateTestReportInstances(int baseYear, int numberOfYearsCovered) {
//
//        List<ReportPeriodEntries> reports = new ArrayList<ReportPeriodEntries>();
//
//        for (int i = 0; i < numberOfYearsCovered + 1; i++) {
//            for (int month = 1; month < 13; month++) {
//                int year = baseYear + i;
//                ReportPeriodEntries report = createReportInstance(year, month);
//                reports.add(report);
//            }
//        }
//
//        return reports;
//
//    }


//    private static ReportPeriodEntries createReportInstance(int year, int month) {
//
//        Period entity = new Period();
//        long id = (year * 100) + month;
//        entity.setId(id);
//        entity.setPeriodStart(createFirstDayInMonthDate(year, month));
//        entity.setPeriodEnd(createLastDayInMonthDate(year, month));
//        entity.setName(TestDataVendor.buildDefaultReportName(year, month));
//
//        ReportPeriodEntries report = new ReportPeriodEntries(entity);
//        return report;
//    }


//    private static Date createLastDayInMonthDate(int year, int month) {
//        DateTime date = new DateTime(year, month, 1, 0, 0, 0, 0);
//        date = date.dayOfMonth().withMaximumValue();
//        return date.toDate();
//    }
//
//    private static Date createFirstDayInMonthDate(int year, int month) {
//        DateTime date = new DateTime(year, month, 1, 0, 0, 0, 0);
//        date = date.dayOfMonth().withMinimumValue();
//        return date.toDate();
//    }


//    public static String buildDefaultReportName(int year, int month) {
//
//        // sort any dodgy months!
//        while (month > 12) {
//            year++;
//            month = month - 12;
//        }
//
//        final boolean useZerobasedMonthIndex = false;
//        String monthName = BudgetTestCommonConstants.decodeMonthID_English(month, useZerobasedMonthIndex);
//        return monthName + " " + (year);
//    }


//    public static Payment getTestPayment(int scenario, int step) {
//
//
//        Payment payment = TestPaymentDataVendor.getTestPayment(scenario, step);
//
//
//        return payment;
//    }


    public static Date getTestDate(int year, int month, int day) {
        DateTime date = new DateTime(year, month, day, 0, 0, 0, 0);
        return date.toDate();
    }

}
