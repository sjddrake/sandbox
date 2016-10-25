package uk.co.neo9.apps.mealplanner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import uk.co.neo9.utilities.UtilsBaseObject;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MealPlannerDBManager extends UtilsBaseObject{
	
	
	public ArrayList recipes = null;
	public Hashtable ingredientCatsTable = null;

	
	public static void main(String[] args) {

		String lfilename = "D:/simonz/My Documents/Home/Menus/Recipe Ingredients.xls";


		MealPlannerDBManager dbManager = new MealPlannerDBManager();
		dbManager.loadRecipes(lfilename);
		
		// dummy test selection
		MenuBO menu = new MenuBO();
		menu.addMeal((RecipeBO)(dbManager.recipes.get(0)));
		menu.addMeal((RecipeBO)(dbManager.recipes.get(1)));
		menu.addMeal((RecipeBO)(dbManager.recipes.get(2)));
		menu.addMeal((RecipeBO)(dbManager.recipes.get(3)));
		test_outputSelection(menu, dbManager.ingredientCatsTable);
		
		
		
//		try {
//			FileServer.writeTextFile("d:/temp/clients_update.sql",entries);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
/**
 * 		This loads recipes in from the Exel spreadsheet.
 * 		The format of spreadsheet is:
 * 
 * 		- worksheet 0 = recipe list
 * 			- recipe names start at B3
 * 			- recipe names are contiguous (so first blank cell = end)
 * 
 * 		- worksheet 1 = TEMPLATE worksheet
 * 			- template is ignored by the load
 * 
 *		- worksheets 2 -> n-2 = recipe worksheets
 *			- ingredients start at cell A2
 *			- ingredients end at first empty cell
 *
 *		- worksheet n-1 = look up lists
 */
	
	public void loadRecipes(String pFilename){
		
		String lfilename = "D:/simonz/My Documents/Home/Menus/Recipe Ingredients.xls";
		if (pFilename != null){
			lfilename = pFilename;
		}
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(new File(lfilename));
		} catch (BiffException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} 
		
		// first load in the lookup data worksheet - the lsat one
		int noOfSheets = workbook.getNumberOfSheets();
		log("Number of sheets in workbook: " + noOfSheets);
		Sheet lookupSheet = workbook.getSheet(noOfSheets-1); 
		Hashtable ingredientLookupTable = loadIngredientLookup(lookupSheet);
		log("Loaded ingredient lookups");
		// spit it out
//		for (Iterator iter = ingredientLookups.iterator(); iter.hasNext();) {
//			IngredientLookup lookup = (IngredientLookup) iter.next();
//			System.out.println(lookup.getName()+ "-"+lookup.getCode()); 
//		}		
		
		ingredientCatsTable = loadIngredientCategories(lookupSheet);
		log("Loaded ingredient categories");
		
		
		// now load in the defined recipes
		Sheet recipeListSheet = workbook.getSheet(0); 
		ArrayList entries = loadRecipeList(recipeListSheet);
		if (entries == null){
			entries = new ArrayList();
		}
		int noOfRecipes = entries.size();
		log("Loaded recipe list; number of recipies: "+noOfRecipes);
		if (noOfSheets == noOfRecipes+3){
			log("number of sheets and recipes tallies");
		} else {
			log("Expected "+noOfRecipes+3+" sheets but only read "+noOfSheets);
		}
		
		// spit it out
//		for (Iterator iter = entries.iterator(); iter.hasNext();) {
//			RecipeBO recipe = (RecipeBO) iter.next();
//			System.out.println(recipe.getName()+ + recipe.getIndex()); 
//		}

		// now load the recipe's ingredients!!!!
		final int recipeSheetsOffset = 2;
		for (Iterator iter = entries.iterator(); iter.hasNext();) {
			// get the worksheet for the recipe
			RecipeBO recipe = (RecipeBO) iter.next();
			Sheet recipeSheet = workbook.getSheet(recipeSheetsOffset+recipe.getIndex()); 
			
			// load the ingredients in from the worksheet
//			ArrayList ingredients = loadIngredients(recipeSheet, ingredientLookupTable);
//			recipe.addAllIngredient(ingredients);
			loadIngredients(recipe, recipeSheet, ingredientLookupTable);
		}
				
		this.recipes = entries;
	}
	
	
	
	private static void test_outputSelection(MenuBO selectedMeals, Hashtable catLookup) {
		
//		for (Iterator iter = selectedMeals.iterator(); iter.hasNext();) {
//			RecipeBO recipe = (RecipeBO) iter.next();
//			
//		}
		
		selectedMeals.output(catLookup);
		
	}

	private static ArrayList loadIngredients(RecipeBO recipe, Sheet recipeSheet, Hashtable ingredientLookup) {

		// a quick check that this is the right sheet for the recipe
		String recipeSheetName = recipeSheet.getName();
		UtilsBaseObject.staticLog("loading ingredients for recipe sheet: "+recipeSheetName);
		if ((recipe.getName() != null) && (recipe.getName().trim().length() > 0) &&
			(recipeSheetName != null) && (recipeSheetName.trim().length() > 0)) {
			if (!recipeSheetName.equalsIgnoreCase(recipe.getName())){
				//TODO some proper exception raising and handling!
				UtilsBaseObject.staticLog("Mix up between the recipe list and the order of the pages!");
				return null;
			}
			
		}
		
		int noOfRows = recipeSheet.getRows();
		
		ArrayList entries = new ArrayList();
		boolean entriesExist = true;
		if (noOfRows<2){
			entriesExist = false;
		}
		int i = 1;
		while (entriesExist) {
			
			Cell ingredientNameCell = recipeSheet.getCell(0, i); 
			Cell ingredientAmountCell = recipeSheet.getCell(1, i); 
			Cell ingredientUnitCell = recipeSheet.getCell(2, i); 

			if (ingredientNameCell != null) {
				
				String ingredientName = ingredientNameCell.getContents(); 
				String ingredientAmount = null;
				String ingredientUnit = null;
				
				if (ingredientAmountCell != null){
					ingredientAmount = ingredientAmountCell.getContents(); 
				}

				if (ingredientUnitCell != null){
					ingredientUnit = ingredientUnitCell.getContents(); 
				}
				
				
				if (ingredientName != null && ingredientName.trim().length() > 0) {
					
					Integer category = (Integer)ingredientLookup.get(ingredientName);
					
					IngredientBO ingredient = new IngredientBO();
					ingredient.setName(ingredientName);
					ingredient.setAmount(ingredientAmount);
					ingredient.setUnit(ingredientUnit);
					ingredient.setIndex(i-2);
					
					if (category != null){
						ingredient.setCategory(category.intValue());
					}
					
					//ingredient.dump();
					entries.add(ingredient);
				} else {
					entriesExist = false;
				}
				
			}else {
				entriesExist = false;
			}
			
			
			// increment count & do safety check
			i++;
			if (i>noOfRows-1){
				//System.out.println("Stopped an infinite loop happening!!!");
				entriesExist = false;
			}
			
		}
		
		// add them to the recipe now
		recipe.addAllIngredient(entries);
		
		// old style
		return entries;

	}

	private static ArrayList loadRecipeList(Sheet recipeListSheet) {
		
		int noOfRows = recipeListSheet.getRows();
		
		ArrayList entries = new ArrayList();
		boolean entriesExist = true;
		int i = 2;
		while (entriesExist) {
			
			Cell recipeNameCell = recipeListSheet.getCell(1,i); 
			Cell recipeStatusCell = recipeListSheet.getCell(2, i); 

			if ((recipeNameCell != null)&&(recipeStatusCell != null)) {
				
				String recipeName = recipeNameCell.getContents(); // recipe name
				String recipeStatus = recipeStatusCell.getContents(); // recipe status
	
				
				if (recipeName != null && recipeName.trim().length() > 0) {
					RecipeBO recipe = new RecipeBO();
					recipe.setName(recipeName);
					recipe.setStatus(recipeStatus);
					recipe.setIndex(i-2);
					entries.add(recipe);
				} else {
					entriesExist = false;
				}
				
			}else {
				entriesExist = false;
			}
			
			
			// increment count & do safety check
			i++;
			if (i>noOfRows-1){
				//System.out.println("Stopped an infinite loop happening!!!");
				entriesExist = false;
			}
			
		}
		return entries;
	}




	private static Hashtable loadIngredientLookup(Sheet typeSheet) {

		Hashtable lookup = new Hashtable();
		
		int noOfRows = typeSheet.getRows();
		
		ArrayList entries = new ArrayList();
		boolean entriesExist = true;
		if (noOfRows<2){
			entriesExist = false;
		}
		int i = 1;
		while (entriesExist) {
			
			Cell ingredientNameCell = typeSheet.getCell(0, i); 
			Cell ingredientCodeCell = typeSheet.getCell(2, i); 

			if (ingredientNameCell != null) {
				
				String ingredientName = ingredientNameCell.getContents(); 
				Integer ingredientCode = null;

				
				if (ingredientCodeCell != null){
					String ingredientCodeString = ingredientCodeCell.getContents(); 
					ingredientCode = new Integer(ingredientCodeString);
				}


				if (ingredientName != null && ingredientName.trim().length() > 0) {
					//IngredientLookup ingredient = new IngredientLookup();
					//ingredient.setName(ingredientName);
					//ingredient.setCode(ingredientCode.intValue());
					//ingredient.dump();
					lookup.put(ingredientName,ingredientCode);
				} else {
					entriesExist = false;
				}
				
			}else {
				entriesExist = false;
			}
			
			
			// increment count & do safety check
			i++;
			if (i>noOfRows-1){
				//System.out.println("Stopped an infinite loop happening!!!");
				entriesExist = false;
			}
			
		}
		return lookup;

	}


	private static Hashtable loadIngredientCategories(Sheet typeSheet) {
		
		Hashtable lookup = new Hashtable();
		
		int noOfRows = typeSheet.getRows();
		
		ArrayList entries = new ArrayList();
		boolean entriesExist = true;
		if (noOfRows<2){
			entriesExist = false;
		}
		int i = 1;
		while (entriesExist) {
			
			Cell ingredientNameCell = typeSheet.getCell(10, i); 
			Cell ingredientCodeCell = typeSheet.getCell(11, i); 

			if (ingredientNameCell != null) {
				
				String ingredientName = ingredientNameCell.getContents(); 
				Integer ingredientCode = null;

				
				if (ingredientCodeCell != null){
					String ingredientCodeString = ingredientCodeCell.getContents();
					if (ingredientCodeString != null && ingredientCodeString.trim().length() > 0) {	 
						ingredientCode = new Integer(ingredientCodeString);
					}
				}

				if ((ingredientCode != null) && (ingredientName != null && ingredientName.trim().length() > 0)) {
					//IngredientLookup ingredient = new IngredientLookup();
					//ingredient.setName(ingredientName);
					//ingredient.setCode(ingredientCode.intValue());
					//ingredient.dump();
					lookup.put(ingredientCode,ingredientName);
				} else {
					entriesExist = false;
				}
				
			}else {
				entriesExist = false;
			}
			
			
			// increment count & do safety check
			i++;
			if (i>noOfRows-1){
				//System.out.println("Stopped an infinite loop happening!!!");
				entriesExist = false;
			}
			
		}
		return lookup;

	}









	
//	private static String formatv1(String stringa1, String stringb2) {
//		StringBuffer buf = new StringBuffer();
//		buf.append(stringa1); 
//		buf.append(" - ");
//		buf.append(stringb2);
//		buf.append(" - ");
//		buf.append(translate(stringb2));
//		return buf.toString();
//	}	
//	
//	private static String formatv2(String stringa1, String stringb2) {
//		StringBuffer buf = new StringBuffer();
//		buf.append(stringa1); 
//		buf.append(",");
//		buf.append(translate(stringb2));
//		return buf.toString();
//	}	
//	
//	private static String formatv3(String stringa1, String stringb2) {
//		StringBuffer buf = new StringBuffer();
//		buf.append("update client ");
//		buf.append("set emp_id = "); 
//		buf.append(translate(stringb2));
//		buf.append(" where client_code = '"); 
//		buf.append(stringa1);
//		buf.append("';"); 
//		return buf.toString();
//	}		
//	
//	private static void output(String stringa1, String stringb2) {
//		System.out.print(stringa1); 
//		System.out.print(" - ");
//		System.out.print(stringb2);
//		System.out.print(" - ");
//		System.out.println(translate(stringb2));
//	}
//
//	private static String translate(String stringb2) {
//		String x = "";
//		
//		if (stringb2!=null){
//			if (stringb2.equalsIgnoreCase("DG")) x = "4";
//			else if (stringb2.equalsIgnoreCase("DH")) x = "5";
//			else if (stringb2.equalsIgnoreCase("LJ")) x = "9";
//			else if (stringb2.equalsIgnoreCase("PW")) x = "10";
//			else if (stringb2.equalsIgnoreCase("RH")) x = "6";
//			else if (stringb2.equalsIgnoreCase("RP")) x = "8";
//		}
//		
//		return x;
//	}

	
	
}
