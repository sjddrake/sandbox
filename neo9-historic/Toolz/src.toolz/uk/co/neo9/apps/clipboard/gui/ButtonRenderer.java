package uk.co.neo9.apps.clipboard.gui;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

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
public class ButtonRenderer extends JButton implements TableCellRenderer { 


  public ButtonRenderer() { 
    setOpaque(true); 
  } 


  public Component getTableCellRendererComponent(JTable table, Object value, 
                   boolean isSelected, boolean hasFocus, int row, int column) { 
    if (isSelected) { 
      setForeground(table.getSelectionForeground()); 
      setBackground(table.getSelectionBackground()); 
    } else{ 
      setForeground(table.getForeground()); 
      setBackground(UIManager.getColor("Button.background")); 
    } 
    setText( (value ==null) ? "" : value.toString() ); 
    return this; 
  } 



} 

