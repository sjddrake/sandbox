package uk.co.neo9.apps.mealplanner;

import uk.co.neo9.utilities.UtilsBaseObject;

public class IngredientBO extends UtilsBaseObject {

	private String name = null;
	private String amount = null;
	private String unit = null;
	private String status = null;
	private String description = null;
	private int category = 0;
	private int index = 0;
	
	
// ================= GETTERS & SETTERS =========================
	
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}


//	 ================= HELPERS =========================



	public void dump(){
		
		dumpField("name",name);
		dumpField("amount",amount);
		dumpField("unit",unit);
		dumpField("category",category);
		
	}
}
