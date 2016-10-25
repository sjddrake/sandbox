package uk.co.neo9.utilities.misc;

public class UtilitiesGridContainerNullCell implements
		UtilitiesGridContainerCellWrapperI {
	
	protected static UtilitiesGridContainerNullCell nullCell = new UtilitiesGridContainerNullCell();
	
	public static UtilitiesGridContainerNullCell getInstance(){
		return nullCell;
	}
	

	public String getOutput() {
		return "";
	}

	public boolean wrapCellModel(Object cellModel) {
		return true;
	}

	@Override
	public String toString() {
		return getOutput();
	}


	public String getXAxisValue() {
		return "";
	}


	public String getYAxisValue() {
		return "";
	}

	
	
}
