/*
 * Created on 30-Jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UtilitiesGenericResultDataSet {

	private ArrayList data = new ArrayList();
	
	public void add(UtilitiesGenericResult pResult){
		data.add(pResult);
	}

	public String dump(){
		
		StringBuffer lOutput = new StringBuffer();		
		
		for (Iterator iter = data.iterator(); iter.hasNext();) {
			UtilitiesGenericResult lResult;
			lResult = (UtilitiesGenericResult) iter.next();
			lOutput.append(lResult.dump());
		}
		
		return lOutput.toString();
	}


	public ArrayList getAll(){
		return data;
	}

	public UtilitiesGenericResult getData(int pIdentifier) {
		
		UtilitiesGenericResult lValue = null;
		
		// get the field from the identifier
		for (Iterator iter = data.iterator(); iter.hasNext();) {
			UtilitiesGenericResult lResult;
			lResult = (UtilitiesGenericResult) iter.next();
			if (lResult.getIdentifier() == pIdentifier){
				lValue = lResult;
				break;
			}
		}		
		
		return lValue;
	}

	public String getValue(int pIdentifier) {
		
		String lTextValue = null;
//		
//		// get the field from the identifier
//		for (Iterator iter = data.iterator(); iter.hasNext();) {
//			UtilitiesGenericResult lResult;
//			lResult = (UtilitiesGenericResult) iter.next();
//			if (lResult.getIdentifier() != null){
//				if (lResult.getIdentifier().equalsIgnoreCase(pIdentifier)){
//					lValue = lResult.getTextValue();
//					break;
//				}
//			}
//		}		
		
		
		UtilitiesGenericResult lValue = null;
		lValue = getData(pIdentifier);
		
		if (lValue != null) {
			lTextValue = lValue.getTextValue();
		}
		
		return lTextValue;
	}


	public void updateValue(int pIdentifier, String pValue) {
		
		UtilitiesGenericResult lData = null;
		lData = getData(pIdentifier);
		
		if (lData != null) {
			lData.setTextValue(pValue);
		} else {
			// this shouldn't be allowed!
		}
		
		return;
	}

	public UtilitiesGenericResult remove(int pIdentifier) {

		//
		//	This method removes the field specified
		//	in pIdentifiers from the result data 
		//		
		
		UtilitiesGenericResult lData = null;
		lData = getData(pIdentifier);
		
		if (lData != null){
			data.remove(lData);
		}
		
		return lData;
	}
	
	
	public void remove(int[] pIdentifiers) {
		
		//
		//	This method REMOVES the fields specified
		//	in pIdentifiers from the result data 
		//		
		
		for (int i = 0; i < pIdentifiers.length; i++) {
			remove(pIdentifiers[i]);
		}
		
		return;
	}	
	

	public void trim(int[] pIdentifiers) {
		
		//
		//	This method LIMITS the result data to
		//	the fields specified in pIdentifiers 
		//		
		
		ArrayList lTrimmedFields = new ArrayList();
		UtilitiesGenericResult lField = null;
		for (int i = 0; i < pIdentifiers.length; i++) {
			lField = remove(pIdentifiers[i]);
			if (lField != null){
				lTrimmedFields.add(lField);
			}
		}
		
		// replace the internal list with the trimmed list
		this.data.clear();
		this.data = lTrimmedFields;
		
		return;
	}	


}
