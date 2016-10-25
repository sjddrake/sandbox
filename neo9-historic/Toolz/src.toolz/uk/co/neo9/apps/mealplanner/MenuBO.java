package uk.co.neo9.apps.mealplanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;

import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.UtilsBaseObject;
import uk.co.neo9.utilities.xml.UtilitiesComplexXMLType;

public class MenuBO extends UtilsBaseObject {

//	private String name = null;
//	private String status = null;
//	private int index = 0;
	private ArrayList meals = new ArrayList(); // not sure if we'll need this
	private ArrayList shoppingList = new ArrayList();
	private ShoppingListComparator comparator = new ShoppingListComparator();


	
//	 ================= GETTERS & SETTERS =========================
		
	public ArrayList getMeals() {
		return meals;
	}

//	public void setMeals(ArrayList meals) {
//		this.meals = meals;
//	}
	
	public void addMeal(RecipeBO recipe) {
		this.meals.add(recipe); // not sure if we'll need this
		addToShoppingList(recipe);
	}

	public void addMeals(ArrayList recipes) {
		for (Iterator iter = recipes.iterator(); iter.hasNext();) {
			RecipeBO recipe = (RecipeBO) iter.next();
			this.meals.add(recipe); // not sure if we'll need this
			addToShoppingList(recipe);			
		}
	}	
	
	private void addToShoppingList(RecipeBO recipe) {
		// want to collate the ingredients 
		ShoppingListItem item = null;
		for (Iterator iter = recipe.getIngredients().iterator(); iter.hasNext();) {
			IngredientBO ingredient = (IngredientBO) iter.next();
			item = new ShoppingListItem(recipe, ingredient);
			this.shoppingList.add(item);
		}
		
		// re-order the list
		Collections.sort(this.shoppingList, comparator);
	}
	
	public String output(Hashtable ingredientCategoryLookup) {
		
		StringBuffer output = new StringBuffer();
		
		int currentCategory = 0;
		String categoryDesc = null;
		for (Iterator iter = this.shoppingList.iterator(); iter.hasNext();) {
			ShoppingListItem item = (ShoppingListItem) iter.next();
			
			if (currentCategory != item.ingredient.getCategory()){
				output.append(CommonConstants.NEWLINE);
				currentCategory = item.ingredient.getCategory();
				categoryDesc = (String)ingredientCategoryLookup.get(new Integer(currentCategory));
				output.append("------ "+categoryDesc+" ------");
				output.append(CommonConstants.NEWLINE);
				output.append(CommonConstants.NEWLINE);
			}
			
			
			
			StringBuffer buf = new StringBuffer();
			buf.append(item.ingredient.getName());
			// buf.append("\t");
			buf.append(" - ");
			buf.append(item.ingredient.getAmount());
			buf.append(" ");
			buf.append(item.ingredient.getUnit());
			buf.append(" (");
			buf.append(item.recipeName);
			buf.append(")");
			
			output.append(buf);
			output.append(CommonConstants.NEWLINE);
		}
		
		return output.toString();
	}
	
	
//	=================================== inner class =======================
	
	private class ShoppingListItem {
		IngredientBO ingredient = null;
		String recipeName = null;

		private ShoppingListItem(){
			// hidding this constructor so that it can't be used
		}
		
		public ShoppingListItem(RecipeBO recipe, IngredientBO ingredient){
			this.ingredient = ingredient;
			this.recipeName = recipe.getName();
		}
	}
	
	
// ============================== comparator ==============================
	
	private class ShoppingListComparator implements Comparator {

		public int compare(Object o1, Object o2) {
				
			int result = 0;
			ShoppingListItem item1 = (ShoppingListItem)(o1);
			ShoppingListItem item2 = (ShoppingListItem)(o2);
			
			if ((item1 != null) && (item1.ingredient != null)) {
				
				if ((item2 != null) && (item2.ingredient != null)) {
					
					// first compare on category, then on name
					result = item1.ingredient.getCategory() - item2.ingredient.getCategory();
					if (result < 0){
						result = -1;
					} else if (result > 0){
						result = 1;
					} else {
						result = item1.ingredient.getName().compareToIgnoreCase(item2.ingredient.getName());
					}
				}			
			}
			
			return result;
		}
		
	}
}
