package uk.co.neo9.sandbox.sqlutils;

import java.util.Iterator;
import java.util.List;

import uk.co.neo9.utilities.file.CSVGenericDataObject;
import uk.co.neo9.utilities.file.CSVMappingTransform;

public class SQLGenerationCSVBuddy {
	
	private static String INSERT_INTO = "Insert into ";
	private static String DATE_FORMAT = "DD/MM/YYYY HH24:MI:SS";
	
	private List<String> columnNames = null;
	private String tableName = null;
	
	private List<CSVMappingTransform> transforms = null;
	private boolean useTransforms = false;
	
	private int defaultFormatType = CSVMappingTransform.TYPE_STRING;
	
	public String generateInsertSQL(CSVGenericDataObject row) {
		StringBuffer buff = new StringBuffer();
		
		buff.append(INSERT_INTO);
		buff.append(tableName);
		buff.append(" (");

		
		for (Iterator<String> iter = columnNames.iterator(); iter.hasNext();) {
			String columnName = iter.next();
			buff.append(columnName);
			if (iter.hasNext()) {
				buff.append(",");
			}
		}
		
		buff.append(") VALUES (");
		
		
		if (useTransforms == false) {
		
			List<String> values = row.getFields();
			int index = -1;
			for (Iterator<String> iter = values.iterator(); iter.hasNext();) {
				index++;
				String value = iter.next();
				buff.append(formatDataType(value,defaultFormatType)); // default to format as text
				if (iter.hasNext()) {
					buff.append(",");
				}
			}
			
		
		} else {
			
			int noOfCSVFields = row.getNoOFields();
			int noOfColumns = this.columnNames.size();
			if (noOfColumns != this.transforms.size()) {
				//TODO raise an exception here!
				System.err.println("columnNames <> transforms!!!!");
			}
			
			for (int i = 0; i < noOfColumns; i++) {
				CSVMappingTransform transformDetails = this.transforms.get(i);
				String value = null;
				if (transformDetails.isUseDefault()) {
					value = transformDetails.getDefaultValue();
				} else {
					value = row.getField(transformDetails.getInputIndex());					
				}
				int fieldType = transformDetails.getType();
				buff.append(formatDataType(value,fieldType));
				if (i < noOfColumns-1) {
					buff.append(",");
				}
			}
			
		}
		
		buff.append(");");
		// buff.append(CommonConstants.NEWLINE);
		
		return buff.toString();
	}

//	private String formatDataType(String value, int index) {
//		String retVal = value;
//		
//		//TODO make this GENERIC AS WELL!!!!
//		// format according to the expected type map
//		switch (index) {
//		case 0: retVal = formatText(value); break; 
//		case 1: retVal = formatText(value); break;
//		case 2: retVal = formatText(value); break; 
//		case 3: retVal = formatText(value); break; 
//		case 4: retVal = formatText(value); break; 
//		case 5: retVal = formatText(value); break; 
//		case 6: retVal = formatText(value); break; 
//		case 7: retVal = formatText(value); break; 
//		case 8: retVal = formatText(value); break; 
//		case 9: retVal = formatText(value); break; 
//		case 10: retVal = formatText(value); break; 
//		case 11: retVal = formatText(value); break; 
//		case 12: retVal = formatText(value); break; 
//		case 13: retVal = formatText(value); break; 
//		case 14: retVal = formatText(value); break; 
//		case 15: retVal = formatText(value); break; 
//		
//		default: break;
//		}
//		
//		return retVal;
//	}

	
	private String formatDataType(String value, int type) {
		
		String retVal = null;
		
		if (type == CSVMappingTransform.TYPE_STRING) {
			retVal = formatText(value);
		} else if (type == CSVMappingTransform.TYPE_DATE) {
			retVal = formatDate(value);
		} else {
			retVal = value;
		}
		
		return retVal;
	}	
	
	private String formatText(String value) {
		
		//TODO this is a bit primitive!!
		// first see if we have to 'escape' any characters
		int found = value.indexOf("&");
		if (found > 0) {
			value = value.replace("&","&'||'");
		}
	
		String retVal = "'"+ value+"'";
		return retVal;
	}

	
	private String formatDate(String value) {
		StringBuffer buff = new StringBuffer();
		buff.append("TO_DATE('");
		buff.append(value);
		buff.append("', '");
		buff.append(DATE_FORMAT);
		buff.append("')");
		return buff.toString();
	}
	
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTransforms(List<CSVMappingTransform> transforms) {
		useTransforms = true;
		this.transforms = transforms;
	}

	public void setDefaultFormatType(int defaultFormatType) {
		this.defaultFormatType = defaultFormatType;
	}

}
