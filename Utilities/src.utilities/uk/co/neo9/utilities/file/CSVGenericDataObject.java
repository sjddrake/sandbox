package uk.co.neo9.utilities.file;

import java.util.List;

public class CSVGenericDataObject implements ICSVDataObject {
	
	private CSVDataFields fields = new CSVDataFields();

	public void addFields(List<String> rawData){
		this.fields.setFields(rawData);
	}
	
	public List<String> getFields(){
		return this.fields.getFields();
	}
	
	public String getField(int fieldIndex){
		return this.fields.getField(fieldIndex);
	}
	
	public int getNoOFields(){
		return this.fields.getNoOfFields();
	}
	
	public String dump() {
		return fields.dump();
	}
	
}
