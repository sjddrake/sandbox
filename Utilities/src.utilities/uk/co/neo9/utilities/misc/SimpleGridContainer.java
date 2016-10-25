package uk.co.neo9.utilities.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimpleGridContainer {
	
    private static String NEWLINE = System.getProperty("line.separator");
	
	private Map<String, Long> grid = new Hashtable<String, Long>();
	
	private Set<String> xAxisValues = new HashSet<String>();
	private Set<String> yAxisValues = new HashSet<String>();
	
	private String valueKey;
	private String xAxisKey;
	private String yAxisKey;
	private String[] xAxisKeys;

	public SimpleGridContainer(String yAxisKey) {
		super();
		this.yAxisKey = yAxisKey;
	}

	
	public SimpleGridContainer() {
		super();
	}


	public boolean add(String xAxisValue, String yAxisValue, Long value) {
		
		boolean valueAppendedToExistingEntry = false;
		
		if (value != null) {
			
			xAxisValues.add(xAxisValue);
			yAxisValues.add(yAxisValue);
			
			String hashKey = formHashKey(xAxisValue, yAxisValue);
			Object object = grid.get(hashKey);
			
			if (object != null) {
				
				Long existingValue = (Long) object;
				Long totalValue = existingValue + value;
				grid.put(hashKey,totalValue);
				logout("added new value of "+value+" to existing value of "+existingValue+" for "+hashKey);
				
				valueAppendedToExistingEntry = true;
				
			} else {

				grid.put(hashKey,value);
				logout("added new value of "+value+" to grid for the first time for "+hashKey);
			}
			
		} else {
			logout("No value in result set for key: "+valueKey);
		}
		return valueAppendedToExistingEntry;
		
	}
	
	
	public boolean add(Map<String, Object> namevaluePairs){

		// get the yAxis value as that's the anchor
		String yAxisValue = (String) namevaluePairs.get(yAxisKey);
		
		// now get what's left
		Set<String> keys = namevaluePairs.keySet();
		for (String key : keys) {
			if (key.equalsIgnoreCase(yAxisKey) == false) {
				String xAxisValue = "scooby look at timemachine version!";
			}
		}
		
		
		String xAxisValue = (String) namevaluePairs.get(xAxisKey);
		
		Long value = (Long) namevaluePairs.get(valueKey);
		
		boolean valueAppendedToExistingEntry = add(xAxisValue, yAxisValue, value);
		
		return valueAppendedToExistingEntry;
	}

	
	private void logout(String message) {
		// System.out.println(message);
		
	}

	private String formHashKey(String xAxisValue, String yAxisValue) {
		return xAxisValue+"-"+yAxisValue;
	}

	public void dumpGrid() {

		Set<String> keys = grid.keySet();
		for (String key : keys) {
			logout(key+": "+grid.get(key));
		}
	}


	public String outputGrid() {
		
		ArrayList<String> sortedXAxisValues = new ArrayList<String>(xAxisValues.size());
		sortedXAxisValues.addAll(xAxisValues);
		Collections.sort(sortedXAxisValues);

		ArrayList<String> sortedYAxisValues = new ArrayList<String>(yAxisValues.size());
		sortedYAxisValues.addAll(yAxisValues);
		Collections.sort(sortedYAxisValues);		
		
		String[] xAxisKeys = (String[]) sortedXAxisValues.toArray(new String[sortedXAxisValues.size()]);
		String[] yAxisKeys = (String[]) sortedYAxisValues.toArray(new String[sortedYAxisValues.size()]);
		
		String output = outputGrid(xAxisKeys, yAxisKeys);

		return output;
	}
	
	

	
	public String outputGrid(String[] xAxisKeys, String[] yAxisKeys) {

		StringBuilder buff = new StringBuilder();

		for (String xAxisKey : xAxisKeys) {
			buff.append(xAxisKey);
			buff.append(",");
		}
		buff.append(NEWLINE);
		
		for (String yAxisKey : yAxisKeys) {
			buff.append(yAxisKey);
			buff.append(",");
			for (String xAxisKey : xAxisKeys) {
				String hashKey = formHashKey(xAxisKey, yAxisKey);
				Long value = grid.get(hashKey);
				if (value == null) {
					value = 0L; // output no result as zero
				}
				buff.append(value);
				buff.append(",");
			}	
			buff.append(NEWLINE);
		}		

		String output =buff.toString(); 
		logout(output);
		return output;
	}


	// this is naff but its only test code ;-)
	public Map<String, Long> getGrid() {
		return grid;
	}


	public SimpleGridContainer cloneMe() {
		
		SimpleGridContainer clone = null; //new SimpleGridContainer(xAxisKey, yAxisKey, valueKey);
		
		clone.grid.putAll(grid);
		clone.xAxisValues.addAll(xAxisValues);
		clone.yAxisValues.addAll(yAxisValues);
		
		return clone;
	}
	
	public void eatYou(SimpleGridContainer sourceGrid) {
		
		this.xAxisValues.addAll(sourceGrid.xAxisValues);
		this.yAxisValues.addAll(sourceGrid.yAxisValues);
		
		Set<String> keys = sourceGrid.getGrid().keySet();
		
		for (String key : keys) {
			Long existingValue = this.getGrid().get(key);
			Long additionalValue = sourceGrid.getGrid().get(key);
			if (existingValue == null) {
				existingValue = additionalValue;
			} else {
				existingValue = existingValue + additionalValue;
			}
			this.getGrid().put(key,existingValue);
		}
		
	}

	
}
