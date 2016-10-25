/*
 * Created on 09-Jan-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities;

import uk.co.neo9.utilities.UtilitiesTextHelper;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Test_UtilitiesTextHelper {

	public static void main(String[] args) {
		
		Test_UtilitiesTextHelper t = new Test_UtilitiesTextHelper();
		t.go();
	}
	
	public void go(){
		
		String lTestText = "This has one %1 forte style marker in it.";
		String lMarker = "%1";
		String lValue = "<marker succesfully replaced>";
		String lResult = null;
		
		lResult = UtilitiesTextHelper.replaceMarker(lTestText,lMarker,lValue);
		
		System.out.println(lResult);
		
		
		//----------------------------------------
		
		
		lTestText = "AddressSearchResultBOImpl";
		lMarker = "BOImpl";
	
		
		lResult = UtilitiesTextHelper.replaceMarker(lTestText,lMarker,null);
		
		System.out.println(lResult);
		
		//----------------------------------------
		
		lTestText = "AddressSearchResultBOImpl";
		lMarker = "BO";
	
		
		lResult = UtilitiesTextHelper.replaceMarker(lTestText,lMarker,null);
		
		System.out.println(lResult);		
		
		
		//----------------------------------------
		
		lTestText = "AddressSearchResultBOImpl";
		lMarker = "Impl";
	
		
		lResult = UtilitiesTextHelper.deleteMarker(lTestText,lMarker);
		
		System.out.println(lResult);	
			
		
		//----------------------------------------
		
		String lUpperedFirstLetterText = "AddressSearchResultBOImpl";
		
		lResult = UtilitiesTextHelper.lowerFirstCharacter(lUpperedFirstLetterText);
		
		System.out.println(lResult);
		
		
		
		//----------------------------------------
		
	
		lResult = UtilitiesTextHelper.lowerFirstCharacter(null);
		
		System.out.println(lResult);					
						
	}
}
