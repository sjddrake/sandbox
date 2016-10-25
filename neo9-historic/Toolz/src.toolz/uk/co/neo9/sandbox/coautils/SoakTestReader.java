package uk.co.neo9.sandbox.coautils;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import uk.co.neo9.utilities.file.FileServer;

/*
 * Created on 26-Jul-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author ct039314
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SoakTestReader {

	public static void main(String[] args) {
		
		final String matchText = ": freed";
		
		Vector lines = new Vector();
		try {
			lines = FileServer.readTextFile("C:\\Temp\\COA_stderr.log");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Vector output = new Vector();
		for (Iterator iter = lines.iterator(); iter.hasNext();) {
				String line = (String) iter.next();
				if (line != null && line.length() > 0){
					if (line.indexOf(matchText) > -1){
						System.out.println(line);
						output.add(line);
					}
				}
		}
		
		try {
			FileServer.writeTextFile("C:\\Temp\\EDITED_COA_stderr.log",output);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
