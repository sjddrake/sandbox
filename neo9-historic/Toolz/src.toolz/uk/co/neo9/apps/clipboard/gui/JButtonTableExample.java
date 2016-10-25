package uk.co.neo9.apps.clipboard.gui;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
public class JButtonTableExample extends JFrame { 


  public JButtonTableExample(){ 
    super( "JButtonTable Example" ); 


    DefaultTableModel dm = new DefaultTableModel(); 
    dm.setDataVector(new Object[][]{{"button 1","foo"}, 
                                    {"button 2","bar"}}, 
                     new Object[]{"Button","String"}); 


    JTable table = new JTable(dm); 
    table.getColumn("Button").setCellRenderer(new ButtonRenderer()); 
    table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox())); 
    JScrollPane scroll = new JScrollPane(table); 
    getContentPane().add( scroll ); 
    setSize( 400, 100 ); 
    setVisible(true); 
  } 


  public static void main(String[] args) { 
    JButtonTableExample frame = new JButtonTableExample(); 
    frame.addWindowListener(new WindowAdapter() { 
      public void windowClosing(WindowEvent e) { 
        System.exit(0); 
      } 
    }); 
  } 



} 

