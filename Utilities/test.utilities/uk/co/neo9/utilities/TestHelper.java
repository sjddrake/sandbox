package uk.co.neo9.utilities;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import uk.co.neo9.test.Neo9TestingConstants;
import uk.co.neo9.utilities.CommonConstants;

public class TestHelper {

	
	public static String getDefaultTestFolderRoot(){
		return Neo9TestingConstants.ROOT_TEST_FOLDER;
	}
	
	public static String rebuildTestFileText(Vector pCSVLines) {

		StringBuffer text = new StringBuffer();
		for (Iterator iter = pCSVLines.iterator(); iter.hasNext();) {
			Vector csvFields = (Vector) iter.next();
			StringBuffer line = new StringBuffer();
			for (Iterator iterator = csvFields.iterator(); iterator.hasNext();) {
				String field = (String) iterator.next();
				line.append(field);
				if (iterator.hasNext()){
					line.append(",");
				}
			}
			text.append(line.toString());
			if (iter.hasNext()){
				text.append(CommonConstants.NEWLINE);
			}
		}
		
		return text.toString();
}		
	
	public static String buildTestFileText(Vector pFieldValues) {

		int x = pFieldValues.size();
		String[] fiellds = new String[x];
		int i = 0;
		for (Iterator iter = pFieldValues.iterator(); iter.hasNext();) {
			String line = (String) iter.next();
			fiellds[i] = line;
			i++;
		}
		
		return buildTestFileText(fiellds);
}	
	
	public static String buildTestFileText(String[] pLines) {
		
		String[] lLines = null;
		if (false) {
//			lFieldValues = new Vector();
//			for (Iterator iter = pFieldValues.iterator(); iter.hasNext();) {
//				String field = (String) iter.next();
//				lFieldValues.add('"'+field+'"');
//			}
			
		} else {
			lLines = pLines;
		}
		
		StringBuffer buf = null;
		if (lLines == null || lLines.length == 0) {
			return "";
		} else {
			buf = new StringBuffer(lLines[0]);
		}
		
		for (int i = 1; i < lLines.length; i++) {
			String line = lLines[i];
			buf.append(CommonConstants.NEWLINE);
			buf.append(line);
		}
		
		return buf.toString();
	}
	
		
	public static String buildCSVLine(String[] pFieldValues, boolean withQuotes) {
	
			Vector lFieldValues = new Vector();
			List list = Arrays.asList(pFieldValues);
			lFieldValues.addAll(list);
			return buildCSVLine(lFieldValues, withQuotes);
	}
	
	
	public static String buildCSVLine(Vector pFieldValues, boolean withQuotes) {
		
		Vector lFieldValues = null;
		if (withQuotes) {
			lFieldValues = new Vector();
			for (Iterator iter = pFieldValues.iterator(); iter.hasNext();) {
				String field = (String) iter.next();
				lFieldValues.add(surroundFieldWithQuotes(field));
			}
			
		} else {
			lFieldValues = pFieldValues;
		}
		
		StringBuffer buf = null;
		if (lFieldValues == null || lFieldValues.size() == 0) {
			return null;
		} else {
			buf = new StringBuffer((String)lFieldValues.get(0));
		}
		
		for (int i = 1; i < lFieldValues.size(); i++) {
			String element = (String) lFieldValues.get(i);
			buf.append(",");
			buf.append(element);
		}
		return buf.toString();
	}
	
	public static String surroundFieldWithQuotes(String field){
		String retVal = null;
		
		retVal = '"'+field+'"';
		
		return retVal;
	}
}
