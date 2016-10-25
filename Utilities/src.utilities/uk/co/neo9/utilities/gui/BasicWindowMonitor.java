package uk.co.neo9.utilities.gui;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BasicWindowMonitor extends WindowAdapter {

// ftp://ftp.orielly.com/pub/examples/java/swing

// how to use this:
//	addWindowListener(new BasicWindowMonitor());

	public void windowClosing(WindowEvent e){

		Window w = e.getWindow();
		w.setVisible(false);
		w.dispose();	
		System.exit(0);

	}

}