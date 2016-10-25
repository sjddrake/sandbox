/*
 * Created on 17-Sep-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities.file;

import java.util.*;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CSVAbstractBuddy implements ICSVBuddy {
	
	protected final static String COMMA = ",";
	
	protected static String formatCSVLine(String[] fields) {
		return CSVHelper.formatCSVLine(fields);
	}


	public ICSVDataObject buildDataObject(Vector lCSVData){
		return null;
	}

	
	public String buildCSVLine(ICSVDataObject dataObject) {
		return null;
	}


	public String getCSVHeader() {
		return null;
	}
	
	public String formatData(String val){
		return CSVHelper.formatData(val);
	}

	public String formatData(boolean val){
		return CSVHelper.formatData(val);
	}
	
}
