package uk.co.neo9.sandbox.coautils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/*
 * Created on 05-Oct-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author Simon
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LogLine {

	ArrayList text = new ArrayList();
	Date timeStamp = null;
	
	public String exportText(){
		StringBuffer buf = new StringBuffer();
		
		for (Iterator iter = text.iterator(); iter.hasNext();) {
			String textLine = (String) iter.next();
			buf.append(textLine);
			// should I append a NEWLINE here?!"???!?
		}
		
		return buf.toString();
	}
	
}
