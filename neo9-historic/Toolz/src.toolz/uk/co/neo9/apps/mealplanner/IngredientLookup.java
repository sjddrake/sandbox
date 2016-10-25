package uk.co.neo9.apps.mealplanner;

import uk.co.neo9.utilities.UtilsBaseObject;

public class IngredientLookup extends UtilsBaseObject {

	private String name = null;
	private int code = 0;
	
	
// ================= GETTERS & SETTERS =========================
	
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * @param i
	 */
	public void setCode(int i) {
		code = i;
	}

//	 ================= HELPERS =========================


	public void dump(){
		
		dumpField("name",name);
		dumpField("code",code);

	}		


}
