package uk.co.neo9.utilities.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVDataFields {

//TODO retro fit the CSV reading stuff to output rows in this format	
	
	private Map<Integer,String> fields = new HashMap<Integer, String>();
	private int noOfFields = 0;

	public CSVDataFields() {
		super();
	}	

	public CSVDataFields(List<String> data) {
		super();
		this.setFields(data);
	}	
	
	public void putField(int fieldIndex, String fieldValue){
		fields.put(new Integer(fieldIndex),fieldValue);
	}
	
	public String getField(int fieldIndex){
		String field = (String)fields.get(new Integer(fieldIndex));
		return field;
	}
	
	public CSVDataFields setFields(List<String> data) {
		
		if (data == null){
			return null;
		}
		
		for (int i = 0; i < data.size(); i++) {
			String field = (String) data.get(i);
			this.putField(i,field);
			noOfFields++;
		}
		
		return this;
	}

	
	public List<String> getFields(){
				
		List<String> fieldValues = new ArrayList<String>();
		for (int i = 0; i < noOfFields; i++) {
			String field = (String) this.getField(i);
			fieldValues.add(field);
		}
		
		return fieldValues;
	}	
	
	
	public int getNoOfFields() {
		return noOfFields;
	}


	public String dump(){
		return outputSimpleCSV();
	}
	
	public String outputSimpleCSV(){
		
		//TODO need to add the text rules really
		
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < noOfFields-1; i++) {
			String field = (String) this.getField(i);
			buff.append(field);
			if (i < noOfFields-2) { // 2015 -> changed from -1 to -2... think that's right
				buff.append(",");
			}
		}
		
		return buff.toString();
	}
}
