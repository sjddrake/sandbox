package uk.co.neo9.utilities.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GridContainer {
	
    private static String NEWLINE = System.getProperty("line.separator");
	
	private Map<String, Object> grid = new Hashtable<String, Object>();
	
	private Set<String> xAxisValues = new HashSet<String>();
	private Set<String> yAxisValues = new HashSet<String>();
	
	private String xAxisKey;
	private String yAxisKey;
	private String valueKey;
	

	public GridContainer(String xAxisKey, String yAxisKey, String valueKey) {
		super();
		this.xAxisKey = xAxisKey;
		this.yAxisKey = yAxisKey;
		this.valueKey = valueKey;
	}

	
	public GridContainer() {
		super();
	}


	public boolean add(String xAxisValue, String yAxisValue, Object pointValue) {
		
		boolean valueAppendedToExistingEntry = false;
		
		if (pointValue != null) {
			
			xAxisValues.add(xAxisValue);
			yAxisValues.add(yAxisValue);
			
			String hashKey = formHashKey(xAxisValue, yAxisValue);
			Object object = grid.get(hashKey);
			
			if (object != null) {
				
				if (object instanceof Number) {
					// add the values together
					if (pointValue instanceof Number) {
						Number totalValue = ((Number)object).doubleValue() + ((Number)pointValue).doubleValue();
						grid.put(hashKey,totalValue);
						valueAppendedToExistingEntry = true;
						logout("added new value of "+pointValue+" to existing value of "+object+" for "+hashKey);
					} else {
						String message = "Incompatable types found on trying to add key: "+hashKey +
										 ". Existing value in grid is: "+ object + ", value added: " +
										 pointValue;
						throw new IllegalArgumentException(message);
					}
				} else {

					// reaplcing the original entry!
					grid.put(hashKey,pointValue); //TODO might want to wrap in a check and throw an exception in a checked mode
				}
				
				
			} else {

				grid.put(hashKey,pointValue);
				logout("added new value of "+pointValue+" to grid for the first time for "+hashKey);
			}
			
		}
		return valueAppendedToExistingEntry;
		
	}
	
	
	public boolean add(Map<String, Object> resultSet){

		String xAxisValue = (String) resultSet.get(xAxisKey);
		String yAxisValue = (String) resultSet.get(yAxisKey);
		Long value = (Long) resultSet.get(valueKey);
		
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
		sortMonthList(sortedXAxisValues);

		ArrayList<String> sortedYAxisValues = new ArrayList<String>(yAxisValues.size());
		sortedYAxisValues.addAll(yAxisValues);
		sortMonthList(sortedYAxisValues);		
		
		String[] xAxisKeys = (String[]) sortedXAxisValues.toArray(new String[sortedXAxisValues.size()]);
		String[] yAxisKeys = (String[]) sortedYAxisValues.toArray(new String[sortedYAxisValues.size()]);
		
		String output = outputGrid(xAxisKeys, yAxisKeys);

		return output;
	}
	
	
	private List<String> sortMonthList(List<String> axisKeys) {
		
		Collections.sort(axisKeys);
		return axisKeys;
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
//				Long value = grid.get(hashKey);
//				if (value == null) {
//					value = 0L; // output no result as zero
//				}
				Object value = grid.get(hashKey);
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
	public Map<String, Object> getGrid() {
		return grid;
	}


	public GridContainer cloneMe() {
		
		GridContainer clone = new GridContainer(xAxisKey, yAxisKey, valueKey);
		
		clone.grid.putAll(grid);
		clone.xAxisValues.addAll(xAxisValues);
		clone.yAxisValues.addAll(yAxisValues);
		
		return clone;
	}
	
	public void eatYou(GridContainer sourceGrid) {
		
		this.xAxisValues.addAll(sourceGrid.xAxisValues);
		this.yAxisValues.addAll(sourceGrid.yAxisValues);
		
		Set<String> keys = sourceGrid.getGrid().keySet();
		
		for (String key : keys) {
			Object existingValue = this.getGrid().get(key);
			Object additionalValue = sourceGrid.getGrid().get(key);
			if (existingValue != null) {


				if (existingValue instanceof Number) {
					// add the values together
					if (additionalValue instanceof Number) {
						Number totalValue = ((Number)existingValue).doubleValue() + ((Number)additionalValue).doubleValue();
						grid.put(key,totalValue);
						logout("added new value of "+additionalValue+" to existing value of "+existingValue+" for "+key);
					} else {
						String message = "Incompatable types found on trying to add key: "+key +
										 ". Existing value in grid is: "+ existingValue + ", value added: " +
										 additionalValue;
						throw new IllegalArgumentException(message);
					}
				} else {

					// reaplcing the original entry!
					grid.put(key,additionalValue); //TODO might want to wrap in a check and throw an exception in a checked mode
				}
				
				
			} else {
				existingValue = additionalValue;
			}
			this.getGrid().put(key,existingValue);
			
			
			
			
		}
		
	}


}
