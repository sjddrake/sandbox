package uk.co.neo9.utilities.gui;

import java.awt.BorderLayout;
import javax.swing.JTextArea;


public class Neo9MessageWindow extends javax.swing.JFrame {

	private String message = null;
	private JTextArea messageControl;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {

		Neo9MessageWindow inst = new Neo9MessageWindow("Hello");
		inst.setTitle("Neo9 Window");
		inst.setVisible(true);
	}
	


	private Neo9MessageWindow() {
		super();
		initGUI();
	}

	public Neo9MessageWindow(String pMessage) {
		super();
		message = pMessage;
		initGUI();
	}
	
	private void initGUI() {
		try {
			{
				messageControl = new JTextArea();
				this.getContentPane().add(messageControl, BorderLayout.CENTER);
				messageControl.setText(this.message);
				messageControl.setPreferredSize(new java.awt.Dimension(392, 16));
				messageControl.setMargin(new java.awt.Insets(20, 20, 20, 20));
				messageControl.setEditable(false);
				
				// the window monitor closes the application!!!!
				//addWindowListener(new BasicWindowMonitor());
			}
			setSize(600, 580);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
