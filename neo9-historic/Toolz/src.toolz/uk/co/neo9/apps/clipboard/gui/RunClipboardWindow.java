/*
 * Created on 23-Jun-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.apps.clipboard.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


// import org.eclipse.swt.widgets.Display;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RunClipboardWindow {

	public static void main(String[] args) {
		
        // ClipboardWindow_EXPO.go(args);
	    JButtonTableExampleClipboard frame = new JButtonTableExampleClipboard(); 
	    frame.addWindowListener(new WindowAdapter() { 
	      public void windowClosing(WindowEvent e) { 
	        System.exit(0); 
	      } 
	    }); 
	}
}
