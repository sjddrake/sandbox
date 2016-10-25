package uk.co.neo9.utilities;

import java.util.ArrayList;
import java.util.List;

public class SimpleCSVLineContainer {
	
	private static String NEWLINE = System.getProperty("line.separator");
	private String builtText = null;
	
	protected List<Object[]> csvLines = new ArrayList<Object[]>();
	
	public void addLine(Object[] fields){
		
		// add the fields
		csvLines.add(fields);
		
		// clear the built text as the content has changed
		builtText = null;
	}	
	
	protected int checkNoOfCSVFields() {
		int noOfFields = 0;
		
		for (Object[] lineOfCSVFields : csvLines) {
			int noOfFieldsInLine = lineOfCSVFields.length;
			if (noOfFields != 0) {
				if (noOfFields != noOfFieldsInLine) {
					// throw exception!

				}
			} else {
				noOfFields = noOfFieldsInLine;
			}
		}
		
		return noOfFields;
	}
	
	private void buildLine(StringBuilder bigbuff, Object[] fields){
		
		StringBuilder linebuff = new StringBuilder();
		for (Object field : fields) {
			if (linebuff.length() > 0) {
				linebuff.append(",");
			}
			linebuff.append(field.toString());
		}
		
		if (bigbuff.length() > 0) {
			bigbuff.append(NEWLINE);
		}
		
		bigbuff.append(linebuff);
	}

	
	private String buildText() {
		
		StringBuilder bigbuff = new StringBuilder();
		
		for (Object[] lineOfCSVFields : csvLines) {
			buildLine(bigbuff, lineOfCSVFields);
		}
		
		return bigbuff.toString();
	}
	
	
	public String outputText() {
		
		if (builtText == null) {
			builtText = buildText();
		}
		
		return builtText;
	}
}
