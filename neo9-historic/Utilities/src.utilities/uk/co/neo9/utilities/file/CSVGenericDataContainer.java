package uk.co.neo9.utilities.file;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.neo9.utilities.CommonConstants;

public class CSVGenericDataContainer implements ICSVDataObject {
	
	protected CSVGenericDataObject headers = new CSVGenericDataObject();
	protected List<CSVGenericDataObject> rows = new ArrayList<CSVGenericDataObject>();

	protected boolean areHeadersDefined = false;
	
	public void addHeaders(List<String> headerValues) {
		
		//TODO some simple checking 
		if (areHeadersDefined) {
			//TODO raise exception? Should we make sure instance is cleared first?
		}
		
		headers.addFields(headerValues);
		areHeadersDefined = true;
	}
	
	
	public void addRows(List<List<String>> rawDataRows){
		
		//TODO need some good type checking on this one!
		
	}

	public void addRow(List<String> rawData){
		
		CSVGenericDataObject rowFields = new CSVGenericDataObject();
		rowFields.addFields(rawData);
		this.rows.add(rowFields);
	}


	public String dump() {
		
		StringBuffer buff = new StringBuffer();
		buff.append(headers.dump());
		for (Iterator<CSVGenericDataObject> iter = rows.iterator(); iter.hasNext();) {
			CSVGenericDataObject row = (CSVGenericDataObject) iter.next();
			buff.append(CommonConstants.NEWLINE);
			buff.append(row.dump());
		}
		return buff.toString();
	}



	
}
