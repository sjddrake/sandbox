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
public class UtilitiesGenericResultCollection {

	private ArrayList results = new ArrayList();
	
	public void add(UtilitiesGenericResultDataSet pResult){
		results.add(pResult);
	}

	public UtilitiesGenericResultDataSet get(){
		return null;
	}

	public ArrayList getAll(){
		return results;
	}

	public String dump(){
		
		StringBuffer lOutput = new StringBuffer();		
		
		for (Iterator iter = results.iterator(); iter.hasNext();) {
			UtilitiesGenericResultDataSet lResult;
			lResult = (UtilitiesGenericResultDataSet) iter.next();
			lOutput.append(lResult.dump());
		}
		
		return lOutput.toString();
	}


	public void trimData(int[] pIdentifiers) {
		
		for (Iterator iter = results.iterator(); iter.hasNext();) {
		
			UtilitiesGenericResultDataSet element = (UtilitiesGenericResultDataSet) iter.next();
			element.remove(pIdentifiers);
			
		}
		
		return;
	}	

}
