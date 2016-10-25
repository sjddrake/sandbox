package uk.co.neo9.apps.accounts.wiptracker;

import java.util.ArrayList;

public class WIPTrackerCategoryContainer {

	private ArrayList<WIPTrackerTotalsModel> totalsInCategory = new ArrayList<WIPTrackerTotalsModel>();
	private String category = null;
	private boolean enforceHomogonous = true;
	
	
	public boolean isEnforceHomogonous() {
		return enforceHomogonous;
	}


	public void setEnforceHomogonous(boolean enforceHomogonous) {
		this.enforceHomogonous = enforceHomogonous;
	}


	public void clearModels() {
		totalsInCategory.clear();
	}
	
	
	public void addModel(WIPTrackerTotalsModel model) {
		
		if (enforceHomogonous) {
			applyHomogonousCheck(model);
		}
		
		this.totalsInCategory.add(model);
	}
	
	
	private void applyHomogonousCheck(WIPTrackerTotalsModel model) {
		
		if (model == null) {
			throw new IllegalArgumentException("Null model in applyHomogonousCheck");
			
		} else {
			
			// need to setup the category if it's not already been done
			if (this.category == null || this.category.trim().length() == 0) {
				this.category = model.getCategory().toUpperCase();
			}
			
			String modelCat = model.getCategory();
			if (modelCat == null || modelCat.trim().length() == 0) {
				throw new IllegalArgumentException("Model with no category value being added to a category container!");
			}
			if (!this.category.equals(modelCat.toUpperCase())) {
				StringBuffer buf = new StringBuffer();
				buf.append("Mismatching category in applyHomogonousCheck. Cat container is ");
				buf.append(this.category);
				buf.append(" but model has cat value: ");
				buf.append(modelCat);
				throw new IllegalArgumentException(buf.toString());
			}
		}
		
	}
	
	
	

	public boolean isCategoryDefined(boolean enforce) {
		
		boolean isDefined = isCategoryDefined();
		
		if (enforce == true && isDefined == false) {
			throw new IllegalArgumentException("No category defined for category container");
		}
		
		return isDefined;
	}
	
	
	public boolean isCategoryDefined() {
		boolean isDefined = true;
		if (this.category == null || this.category.trim().length() == 0) {
			isDefined = false;
		}
		return isDefined;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public ArrayList<WIPTrackerTotalsModel> getTotalsInCategory() {
		return totalsInCategory;
	}
	public void setTotalsInCategory(
			ArrayList<WIPTrackerTotalsModel> totalsInCategory) {
		this.totalsInCategory = totalsInCategory;
	}
	
	
}
