package uk.co.neo9.apps.mealplanner;

import java.util.ArrayList;

import uk.co.neo9.utilities.UtilsBaseObject;

public class RecipeBO extends UtilsBaseObject {

	private String name = null;
	private String status = null;
	private int index = 0;
	private ArrayList ingredients = new ArrayList();

//	================= HELPER METHODS =========================	
	
	public void dump(){
		
		
	}
	
	
//	 ================= GETTERS & SETTERS =========================
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
		
	public ArrayList getIngredients() {
		return ingredients;
	}

	public void addIngredient(IngredientBO ingredient) {
		this.ingredients.add(ingredient);
	}
	
	public void addAllIngredient(ArrayList ingredients) {
		this.ingredients.addAll(ingredients);
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
