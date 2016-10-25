package uk.co.neo9.apps.clipboard.gui;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import uk.co.neo9.apps.clipboard.ClipboardDBManager;
import uk.co.neo9.apps.clipboard.ClipboardFolder;
import uk.co.neo9.apps.clipboard.ClipboardItemI;

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
public class JButtonTableExampleClipboard extends JFrame { 


  public JButtonTableExampleClipboard(){ 
    super( "JButtonTable Example" ); 

    ClipboardDBManager db = new ClipboardDBManager();
    ClipboardFolder folder = db.loadClipboardItems();    

    ArrayList firstDimension  = new ArrayList();
    for (Iterator iter = folder.getSimpleClipItems().iterator(); iter.hasNext();) {
        ClipboardItemI item = (ClipboardItemI) iter.next();
        firstDimension.add(new Object[]{item});
    }


    DefaultTableModel dm = new DefaultTableModel(); 
//    dm.setDataVector(new Object[][]{{"button 1"}, 
//                                    {"button 2"}, 
//                                    {"button 3"}}, 

//      dm.setDataVector((Object [][])firstDimension.toArray(),
//                     new Object[]{"Button"}); 

      Vector v1 = new Vector();
      for (Iterator iter = folder.getSimpleClipItems().iterator(); iter.hasNext();) {
          ClipboardItemI item = (ClipboardItemI) iter.next();
          Vector vv = new Vector();
          vv.add(item.getClipText());
          v1.add(vv);
      }
      Vector v2 = new Vector();
      v2.add("Button");
      dm.setDataVector(v1,v2); 


    JTable table = new JTable(dm); 
    table.getColumn("Button").setCellRenderer(new ButtonRenderer()); 
    ClipboardButtonEditor editor = new ClipboardButtonEditor(new JCheckBox());
    editor.setClipboard(getToolkit().getSystemClipboard());
    table.getColumn("Button").setCellEditor(editor); 
    JScrollPane scroll = new JScrollPane(table); 
    getContentPane().add( scroll ); 
    setSize( 400, 100 ); 
    setVisible(true); 
    
    
  } 


  public static void main(String[] args) { 
    JButtonTableExampleClipboard frame = new JButtonTableExampleClipboard(); 
    frame.addWindowListener(new WindowAdapter() { 
      public void windowClosing(WindowEvent e) { 
        System.exit(0); 
      } 
    }); 
  } 



} 

