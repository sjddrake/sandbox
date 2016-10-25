package uk.co.neo9.utilities.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import uk.co.neo9.utilities.CommonConstants;

public class UtilitiesGridComparator implements Comparator {

	private List<UtilitiesGridComparatorResult> results = new ArrayList<UtilitiesGridComparatorResult>();;

	public int compare(Object arg0, Object arg1) {
		int result = compare((UtilitiesGridContainer)arg0, (UtilitiesGridContainer)arg1);
		return result;
	}

	
	public int compare(UtilitiesGridContainer grid1, UtilitiesGridContainer grid2) {
		
		// first get all the keys
		List<String> allKeys = getAllKeys(grid1, grid2);
		
		// now use the keys to compare the elements, and keep track of the results
		List<UtilitiesGridComparatorResult> resultDetails = new ArrayList<UtilitiesGridComparatorResult>();
		for (Iterator iter = allKeys.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Object grid1Value = grid1.getCellValue(key);
			Object grid2Value = grid2.getCellValue(key);
			
			UtilitiesGridComparatorResult details = null;
			String keyCatagories = grid1.decodeKey(key);
			details = compareCellValues(keyCatagories, grid1Value, grid2Value);
			resultDetails.add(details);
		}
		
		int result = 0;
		for (Iterator iter = resultDetails.iterator(); iter.hasNext();) {
			UtilitiesGridComparatorResult detail = (UtilitiesGridComparatorResult) iter.next();
			if (detail.matched == false) {
				result = -1;
			}
		}
		
		this.results = resultDetails;
		
		return result;
	}


	protected UtilitiesGridComparatorResult compareCellValues(String key, Object grid1Value, Object grid2Value) {
		
		boolean same = false;
		UtilitiesGridComparatorResult result = new UtilitiesGridComparatorResult();
		result.key = key;
		
		if (grid1Value == null) {
			
			if (grid2Value == null) {
				same = true;
			} else {
				result.grid2Value = grid2Value.toString();
			}
			
		} else {
			
			result.grid1Value = grid1Value.toString();
			
			if (grid2Value != null) {
				
				result.grid2Value = grid2Value.toString();
				
				// both grid-values are not null - compare them!
				String grid1ValueStr = grid1Value.toString();
				String grid2ValueStr = grid2Value.toString();
				int match = grid1ValueStr.compareTo(grid2ValueStr);
				if (match == 0) {
					same = true;
				}
				
			} 
			
		}
		
		result.matched = same;
		
		return result;
		
	}


	protected List<String> getAllKeys(UtilitiesGridContainer grid1, UtilitiesGridContainer grid2) {
		
		// get the keys from the grids
		Set<String> keys1 = grid1.getKeys();
		Set<String> keys2 = grid2.getKeys();
		
		// amalgamate the keys into one list
		List<String> allKeys = new ArrayList<String>();
		allKeys.addAll(keys1);
		for (Iterator iter = keys2.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if (keys1.contains(key) == false) {
				allKeys.add(key);
			}
		}
		
		Collections.sort(allKeys);
		
		return allKeys;
	}
	
	
	
	public class UtilitiesGridComparatorResult 
	{
		public String key;
		public String grid1Value;
		public String grid2Value;
		public boolean matched = false;	
	}



	public List<UtilitiesGridComparatorResult> getResults() {
		return results;
	}


	public void dumpResults() {

		System.out.println(outputResults());
	}
	
	
	public String outputResults() {
		return outputResults(false);
	}
	
	public String outputResults(boolean onlyMismatches) {

		StringBuilder buff = new StringBuilder();
		buff.append("key,matched,grid1,grid2");
		buff.append(CommonConstants.NEWLINE);
		for (Iterator iter = this.results.iterator(); iter.hasNext();) {
			UtilitiesGridComparatorResult details = (UtilitiesGridComparatorResult) iter.next();
			boolean writeOutThisOne = true;
			if (onlyMismatches && details.matched) {
				writeOutThisOne = false;
			}
			if (writeOutThisOne) {
				buff.append(details.key);
				buff.append(",");
				buff.append(details.matched);
				buff.append(",");
				buff.append(details.grid1Value);
				buff.append(",");
				buff.append(details.grid2Value);
				buff.append(CommonConstants.NEWLINE);
			}			
		}
		
		return buff.toString();
		
	}
	
	
}
