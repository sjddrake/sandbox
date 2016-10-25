package uk.co.neo9.apps.accounts.budget.load;

import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.dozer.CsvDozerBeanReader;
import org.supercsv.io.dozer.ICsvDozerBeanReader;
import org.supercsv.prefs.CsvPreference;

import uk.co.neo9.apps.accounts.budget.model.Budget;
import uk.co.neo9.apps.accounts.budget.model.BudgetCategoryImpl;
import uk.co.neo9.apps.accounts.budget.model.DiaryPointId;

public class BudgetLoader {

	
	/*
	 * Not gone as neatly as I would have liked. Should be able to map straight to the 
	 * Budget Category but I dont know it it will handle Maps... for now can go via
	 * an intermediary... then read up and see if SuperCSV withe Dozer will handle maps
	 * ... they should do!
	 * 
	 */
	
	
	private static final String[] CSV_DOZER_FIELD_MAPPING = new String[] { 
        "budgetName",                          
        "monthlyTotals[0]", 
        "monthlyTotals[1]",
        "monthlyTotals[2]", 
        "monthlyTotals[3]",
        "monthlyTotals[4]", 
        "monthlyTotals[5]",
        "monthlyTotals[6]", 
        "monthlyTotals[7]",      
        "monthlyTotals[8]", 
        "monthlyTotals[9]",
        "monthlyTotals[10]", 
        "monthlyTotals[11]",
        "yearlyTotal"
	};			
	
	
	public Budget loadBudget(BudgetLoadParams params) {
		
		// can have all sorts of variation from here but actually for now its just CSV files!
		List<BudgetLoadModel> loadModels = null;
		try {
			loadModels =  loadBudgetModelFromCSV(params.getFileNameAndPath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null; //TODO scooby!
		}
		
		
		// get the year from the file name
		//TODO
		int year = 2014;
		
		// convert the load model into a budget
		return convertLoadModel(loadModels, year);
	}

	

	
	
	private Budget convertLoadModel(List<BudgetLoadModel> loadModels, int year) {
		// TODO Auto-generated method stub
		
		for (BudgetLoadModel budgetLoadModel : loadModels) {
			// each load model is a row from the CSV and a BudgetCategory from the domain
			BudgetCategoryImpl cat = new BudgetCategoryImpl(budgetLoadModel.getBudgetName(), year);
			List<String> monthlyTotals = budgetLoadModel.getMonthlyTotals();
			for (int monthIndex = 0; monthIndex < monthlyTotals.size(); monthIndex++) {
				String monthlyTotal = monthlyTotals.get(monthIndex);
				DiaryPointId monthPointId = new DiaryPointId(year, monthIndex);
				cat.setMonthlyTotal(new Integer(monthlyTotal), monthPointId); 
			}
			
		}
		
		return null;
	}





	/**
	 * An example of reading using CsvDozerBeanReader.
	 */
	private List<BudgetLoadModel> loadBudgetModelFromCSV(String fileNameAndLocation) throws Exception {
	        	
//	It would appear there is a bug in the library because this was producing Strings not ints!	
//		
//	        final CellProcessor[] processors = new CellProcessor[] { 
//	                null, 				// Budget Name
//	                new ParseInt(),              // Jan
//	                new ParseInt(),               // Feb
//	                new ParseInt(),               // Mar
//	                new ParseInt(),               // Apr
//	                new ParseInt(),               // May
//	                new ParseInt(),               // Jun
//	                new ParseInt(),               // Jul
//	                new ParseInt(),               // Augw
//	                new ParseInt(),               // Sept
//	                new ParseInt(),               // Oct
//	                new ParseInt(),               // Nov
//	                new ParseInt(),               // Dec
//	                new ParseInt()                // Yearly Total
//	        };
	        
	        
	        final CellProcessor[] processors = new CellProcessor[] { 
	                null, 				// Budget Name
	                null,              // Jan
	                null,               // Feb
	                null,               // Mar
	                null,               // Apr
	                null,               // May
	                null,               // Jun
	                null,               // Jul
	                null,               // Augw
	                null,               // Sept
	                null,               // Oct
	                null,               // Nov
	                null,               // Dec
	                null                // Yearly Total
	        };	        
	        
	        
	        ICsvDozerBeanReader beanReader = null;
	        List<BudgetLoadModel> loadModels = new ArrayList<BudgetLoadModel>();
	        try {
	                beanReader = new CsvDozerBeanReader(new FileReader(fileNameAndLocation), CsvPreference.STANDARD_PREFERENCE);
	                
	                beanReader.getHeader(true); // ignore the header
	                beanReader.configureBeanMapping(BudgetLoadModel.class, CSV_DOZER_FIELD_MAPPING);
	                
	                BudgetLoadModel loadModel;
	                while( (loadModel = beanReader.read(BudgetLoadModel.class, processors)) != null ) {
	                	
	                	loadModels.add(loadModel);
	                    System.out.println(String.format("lineNo=%s, rowNo=%s, processed ok!", 
	                    								  beanReader.getLineNumber(),
	                    								  beanReader.getRowNumber()));
	                }
	                
	        }
	        finally {
	                if( beanReader != null ) {
	                        beanReader.close();
	                }
	        }
	        
	        
			return loadModels;
	}	
	
	
}
