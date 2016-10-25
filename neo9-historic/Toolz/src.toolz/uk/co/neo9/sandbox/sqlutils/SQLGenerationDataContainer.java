package uk.co.neo9.sandbox.sqlutils;

import java.util.Iterator;

import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.file.CSVGenericDataContainer;
import uk.co.neo9.utilities.file.CSVGenericDataObject;


public class SQLGenerationDataContainer extends CSVGenericDataContainer{

	private SQLGenerationCSVBuddy buddy = null;
	
	public String generateSQLScript() {
		
		StringBuffer buff = new StringBuffer();
		for (Iterator iter = rows.iterator(); iter.hasNext();) {
			CSVGenericDataObject row = (CSVGenericDataObject) iter.next();
			buff.append(CommonConstants.NEWLINE);
			buff.append(buddy.generateInsertSQL(row));
		}
		return buff.toString();
	}


	public void setBuddy(SQLGenerationCSVBuddy buddy) {
		this.buddy = buddy;
		
	}
	
}
