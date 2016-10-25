package uk.co.neo9.apps.accounts;

import java.math.BigDecimal;
import java.util.Date;

import uk.co.neo9.apps.accounts.wiptracker.WIPTrackerCategoryContainer;
import uk.co.neo9.apps.accounts.wiptracker.WIPTrackerTotalsModel;

public class WIPTrackerTestHelper {

	
	public static WIPTrackerTotalsModel createTestModel(String description) {
		WIPTrackerTotalsModel model = new WIPTrackerTotalsModel();
		
		model.setDescription(description);
		
		return model;
	}
	
	
	public static WIPTrackerTotalsModel createTestModel(String catgory, int amount, Date date) {
		
		WIPTrackerTotalsModel model = new WIPTrackerTotalsModel();
		model.setCategory(catgory);
		model.setAmount(new BigDecimal(amount));
		model.setDate(date);
		
		return model;
	}
	
	public static WIPTrackerTotalsModel createTestModel(String catgory, BigDecimal amount, Date date) {
		
		WIPTrackerTotalsModel model = new WIPTrackerTotalsModel();
		model.setCategory(catgory);
		model.setAmount(amount);
		model.setDate(date);
		
		return model;
	}
	
	public static WIPTrackerTotalsModel createTestModel(String catgory, int amount, Date date, String where) {
		
		WIPTrackerTotalsModel model = createTestModel(catgory, amount, date);
		model.setWhere(where);
		
		return model;
	}

	public static WIPTrackerTotalsModel createTestModel(String catgory, BigDecimal amount, Date date, String where) {
		
		WIPTrackerTotalsModel model = createTestModel(catgory, amount, date);
		model.setWhere(where);
		
		return model;
	}
	/*
	 * Spready template for generating the values - TAB delimited
	 * 
	 * model = createTestModel(category, new BigDecimal("	£11.18	"), new Date("	01/03/2010	"), "	ROYAL SUNALLIANCE	");
	 * 
	 *  
	 */
	public static WIPTrackerCategoryContainer createTestCategoryContainer(boolean homogenous) {
		
		WIPTrackerCategoryContainer catContainer = new WIPTrackerCategoryContainer();
		
		WIPTrackerTotalsModel model = null;
		String category = "bills";
		
		model = createTestModel(category, new BigDecimal("11.18"), new Date("01/03/2010"), "ROYAL SUNALLIANCE");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("18.90"), new Date("01/03/2010"), "LEGAL & GEN MI C/L");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("12.12"), new Date("01/03/2010"), "TV LICENCE MBP");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("7.75"), new Date("04/03/2010"), "WHICH");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("104.81"), new Date("09/03/2010"), "BARCLAYS PRTNR FIN");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("93.33"), new Date("10/03/2010"), "SCOTTISH PROVIDENT");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("8.60"), new Date("11/03/2010"), "LEGAL & GEN MI C/L");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("39.84"), new Date("11/03/2010"), "VIRGIN MEDIA PYMTS");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("10.50"), new Date("15/03/2010"), "VIRGIN MOBILE");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("113.00"), new Date("15/03/2010"), "E.ON");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("53.36"), new Date("15/03/2010"), "BLACK HORSE");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("20.00"), new Date("15/03/2010"), "PLATINUM M/C");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("198.60"), new Date("16/03/2010"), "SCOTTISH PROVIDENT");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("810.96"), new Date("16/03/2010"), "MLS P02 WOOLWICH");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("10.00"), new Date("24/03/2010"), "ST ANNE CHERTSEY");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("11.18"), new Date("30/03/2010"), "ROYAL SUNALLIANCE");
		catContainer.addModel(model);
		model = createTestModel(category, new BigDecimal("18.90"), new Date("31/03/2010"), "LEGAL & GEN MI C/L");
		catContainer.addModel(model);

	
		
		return catContainer;
		
	}
}
