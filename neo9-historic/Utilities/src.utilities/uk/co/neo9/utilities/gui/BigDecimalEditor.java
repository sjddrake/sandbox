package uk.co.neo9.utilities.gui;
/*
 * IntegerEditor is a 1.4 class used by TableFTFEditDemo.java.
 */

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Component;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import uk.co.neo9.utilities.CommonConstants;

/**
 * Implements a cell editor that uses a formatted text field
 * to edit Integer values.
 */
public class BigDecimalEditor extends DefaultCellEditor {
	
	Neo9GUIValidationPlugInI customValidator;
    JFormattedTextField ftf;
    NumberFormat format;
    private BigDecimal minimum, maximum;
    private boolean DEBUG = false;

    public BigDecimalEditor(double min, double max, Neo9GUIValidationPlugInI validator) {
    	this(min,max);
    	this.customValidator = validator;
    }    
    
    
    public BigDecimalEditor(double min, double max) {
    	
    	// uses JFormattedTextField as the engine
        super(new JFormattedTextField());   	
        
        // get the textfield instance and set it up
        ftf = (JFormattedTextField)getComponent();
        minimum = new BigDecimal(min);
        maximum = new BigDecimal(max);

        //Set up the editor for the integer cells.
        format = NumberFormat.getNumberInstance(); // getIntegerInstance();
        format.setMaximumFractionDigits(2); // this didn't do what I wanted it to do
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setFormat(format);
        formatter.setMinimum(minimum);
        formatter.setMaximum(maximum);


        // add the formatter
        ftf.setFormatterFactory(new DefaultFormatterFactory(formatter));
        ftf.setValue(minimum);
        ftf.setHorizontalAlignment(JTextField.TRAILING);
        ftf.setFocusLostBehavior(JFormattedTextField.PERSIST);

        //React when the user presses Enter while the editor is
        //active.  (Tab is handled as specified by
        //JFormattedTextField's focusLostBehavior property.)
        ftf.getInputMap().put(KeyStroke.getKeyStroke(
                                        KeyEvent.VK_ENTER, 0),
                                        "check");
        
        ftf.getActionMap().put(
        	"check", 
        	// annon inner class
        	new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
				if (!ftf.isEditValid()) { //The text is invalid.					
		           if (userSaysRevert()) {
		                //reverted
				        ftf.postActionEvent(); //inform the editor
				    }
                } else try {              //The text is valid,
                	
                	// first do custom validation
                	String errorMessage = doCustomValidation(getCellEditorValue());
                	if (errorMessage == null || errorMessage.trim().length()== 0){
	                    ftf.commitEdit();     //so use it.
	                    ftf.postActionEvent(); //stop editing
                	} else {
                		// failed the custom validation
     		           if (userSaysRevert(errorMessage)) {
     		        	   //reverted
     		        	   ftf.postActionEvent(); //inform the editor
     		           }               		
                	}
                } catch (java.text.ParseException exc) {
                	
                	// swallow the exception!!!
                }
            }

        });
    }

	private String doCustomValidation(Object value) {
		
		//System.out.println(value);
		
		// a null return error messagemeans validation has passed; naff, I know!
		String lErrorMessage = null;
		if (customValidator != null){
			lErrorMessage = customValidator.doCustomValidation(value);
		}
		return lErrorMessage;
	}
	
    //Override to invoke setValue on the formatted text field.
    public Component getTableCellEditorComponent
    					(JTable table, Object value, boolean isSelected, int row, int column) {
        JFormattedTextField ftf =
            (JFormattedTextField)super.getTableCellEditorComponent(
                table, value, isSelected, row, column);
        ftf.setValue(value);
        ftf.selectAll(); // Simon Added THIS!
        return ftf;
    }

    //Override to ensure that the value remains a BigDecimal.
    public Object getCellEditorValue() {
    	
        JFormattedTextField ftf = (JFormattedTextField)getComponent();
        Object o = ftf.getText();// Value();
        
        if (o instanceof BigDecimal) {
            return o;
        } else if (o instanceof Number) {
            return new BigDecimal(((Number)o).doubleValue());
        } else {
            if (DEBUG) {
                System.out.println("getCellEditorValue: o isn't a Number");
            }
            try {
                return format.parseObject(o.toString());
            } catch (ParseException exc) {
                System.err.println("getCellEditorValue: can't parse o: " + o);
                return null;
            }
        }
    }

    //Override to check whether the edit is valid,
    //setting the value if it is and complaining if
    //it isn't.  If it's OK for the editor to go
    //away, we need to invoke the superclass's version 
    //of this method so that everything gets cleaned up.
    public boolean stopCellEditing() {
    	
        JFormattedTextField ftf = (JFormattedTextField)getComponent();
        
        if (ftf.isEditValid()) {
        	
        	// first do custom validation
        	String errorMessage = doCustomValidation(getCellEditorValue());
        	if (errorMessage == null || errorMessage.trim().length()== 0){
	            try {
	                ftf.commitEdit();
	            } catch (java.text.ParseException exc) { 
	            	
	            	// swallow this sort of exception
	            }
        	} else {
                if (!userSaysRevert(errorMessage)) { //user wants to edit
    		        return false; //don't let the editor go away
    		    } 
        	}
        } else { //text is invalid
            if (!userSaysRevert()) { //user wants to edit
		        return false; //don't let the editor go away
		    } 
        }
        return super.stopCellEditing();
    }

    protected boolean userSaysRevert() {
		return userSaysRevert(null);
	}

	/** 
     * Lets the user know that the text they entered is 
     * bad. Returns true if the user elects to revert to
     * the last good value.  Otherwise, returns false, 
     * indicating that the user wants to continue editing.
     */
    protected boolean userSaysRevert(String errorMessage) {
    	
    	boolean revert = false;
    	
        Toolkit.getDefaultToolkit().beep();
        ftf.selectAll();
        Object[] options = {"Edit",
                            "Revert"};
        
        StringBuffer lMessage = null;
        if (errorMessage != null){
        	lMessage = new StringBuffer(errorMessage);
        	lMessage.append(CommonConstants.NEWLINE);
        } else {
        	lMessage = new StringBuffer();
        }
//      "The value must be a number between "
//      + minimum + " and "
//      + maximum +  
        
        lMessage.append("You can either continue editing "
        				+ "or revert to the last valid value.");
        
        int answer = JOptionPane.showOptionDialog(
            SwingUtilities.getWindowAncestor(ftf),
            lMessage.toString(),
            "Invalid Text Entered",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            options,
            options[1]);
	    
        if (answer == 1) { //Revert!
            ftf.setValue(ftf.getValue());
            revert = true;
        }
        
        return revert;
    }
}
