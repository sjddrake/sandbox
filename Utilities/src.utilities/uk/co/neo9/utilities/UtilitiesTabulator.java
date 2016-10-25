/*
 * Created on 14-Nov-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UtilitiesTabulator {
	
	public static String addTabs(int pNoOfTabs){
		
		StringBuffer lTabs = new StringBuffer();
		for (int i = 1; i < pNoOfTabs+1; i++) {
			lTabs.append("\t");
		}
		
		return lTabs.toString();
	}

	public static String addDottedTabs(int pNoOfTabs){
		
		StringBuffer lTabs = new StringBuffer();
		for (int i = 1; i < pNoOfTabs+1; i++) {
			lTabs.append(".");
			lTabs.append("\t");
		}
		
		return lTabs.toString();
	}

	public static String indentBlock(String pTextBlock, int pNoOfTabs){

		/*
		 * for every logical line in the text block
		 * indent it by pNoOfTabs
		 * 
		 */
		
		return pTextBlock;
	}


}
