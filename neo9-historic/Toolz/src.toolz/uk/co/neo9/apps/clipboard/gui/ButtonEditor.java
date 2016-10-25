package uk.co.neo9.apps.clipboard.gui;
import java.awt.Component;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/*
 * Created on 27-Oct-06
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
public class ButtonEditor extends DefaultCellEditor {
     
  protected JButton button; 
  private String    label; 
  private boolean   isPushed; 
//  private final Clipboard clipboard = getToolkit().getSystemClipboard();



  public ButtonEditor(JCheckBox checkBox) { 
    super(checkBox); 
    button = new JButton(); 
    button.setOpaque(true); 
    button.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        fireEditingStopped(); 
      } 
    }); 
  } 


  public Component getTableCellEditorComponent(JTable table, Object value, 
                   boolean isSelected, int row, int column) { 
    if (isSelected) { 
      button.setForeground(table.getSelectionForeground()); 
      button.setBackground(table.getSelectionBackground()); 
    } else{ 
      button.setForeground(table.getForeground()); 
      button.setBackground(table.getBackground()); 
    } 
    label = (value ==null) ? "111" : value.toString(); 
    button.setText( label ); 
    isPushed = true; 
    return button; 
  } 


  public Object getCellEditorValue() { 
    if (isPushed)  { 
      // 
      // 
      JOptionPane.showMessageDialog(button ,label + ": Ouch!"); 
      // System.out.println(label + ": Ouch!"); 
    } 
    isPushed = false; 
    return new String( label ) ; 
  } 


  public boolean stopCellEditing() { 
    isPushed = false; 
    return super.stopCellEditing(); 
  } 


  protected void fireEditingStopped() { 
    super.fireEditingStopped(); 
  } 

}
