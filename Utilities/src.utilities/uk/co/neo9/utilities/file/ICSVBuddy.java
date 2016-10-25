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
public interface ICSVBuddy {
	
	public ICSVDataObject buildDataObject(Vector lCSVData);
	public String buildCSVLine(ICSVDataObject dataObject);
	public String getCSVHeader();

}
