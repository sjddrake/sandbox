package uk.co.neo9.apps.mealplanner;

import java.util.ArrayList;

import uk.co.neo9.utilities.UtilsBaseObject;

public class RecipeBOSelectionModel extends UtilsBaseObject {

	private RecipeBO recipe = null;
	private boolean selected = false;
	private int index = 0;

//	================= HELPER METHODS =========================

	public void dump(){


	}


//	 ================= GETTERS & SETTERS =========================

	public String getName() {

		if (recipe!=null){
			return recipe.getName();
		} else {
			return null;
		}
	}

	public void setRecipeBO(RecipeBO recipe) {
		this.recipe = recipe;
	}

	public boolean getSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	public RecipeBO getRecipe() {
		return recipe;
	}
}
