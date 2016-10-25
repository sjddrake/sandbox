package uk.co.neo9.apps.mealplanner;
/*
 * TableSorterDemo.java is a 1.4 application that requires this file:
 *   TableSorter.java
 */

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import uk.co.neo9.apps.imageviewer.ImageRenamingModel;
import uk.co.neo9.utilities.file.FileServer;
import uk.co.neo9.utilities.gui.Neo9MessageWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
/**
 * TableSorterDemo is like TableDemo, except that it
 * inserts a custom model -- a sorter -- between the table
 * and its data model.  It also has column tool tips.
 */
public class MealPlannerListWindow extends JPanel {

    private boolean DEBUG = false;
    MyTableModel tableModel = new MyTableModel();
    private JButton exportBtn;
    MealPlannerDBManager dbManager = new MealPlannerDBManager();

    public MealPlannerListWindow(String pFilename) {
        super(new GridLayout(1,0));

//
//        TableSorter sorter = new TableSorter(new MyTableModel()); //ADDED THIS
//        //JTable table = new JTable(new MyTableModel());         //OLD
//        JTable table = new JTable(sorter);             //NEW
//        sorter.setTableHeader(table.getTableHeader()); //ADDED THIS
//        table.setPreferredScrollableViewportSize(new Dimension(500, 70));


		dbManager.loadRecipes(pFilename);
        tableModel.populate(dbManager.recipes);


        JTable table = new JTable(tableModel);
        TableColumn column = table.getColumnModel().getColumn(1);
        column.setPreferredWidth(20);
        table.setPreferredScrollableViewportSize(new Dimension(500, 500));


        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
		BorderLayout thisLayout = new BorderLayout();
		this.setLayout(thisLayout);
        this.add(scrollPane, BorderLayout.CENTER);
		{
			exportBtn = new JButton();
			this.add(exportBtn, BorderLayout.SOUTH);
			exportBtn.setText("Export");

			exportBtn.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		        	handleExportSelection();
		        }
		      });		
		}
		
		column.setPreferredWidth(10); // this doesn't seem to work!
		
    }





    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI(String pFilename) {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Recipes & Shopping Lists");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        MealPlannerListWindow newContentPane = new MealPlannerListWindow(pFilename);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        go(args);
    }

    
    public static void go(String[] args) {
    	
    	String filename = null;
    	if (args != null && args.length > 0){
    		filename = (String)args[0];
    	} else {
    		filename = "D:/simonz/My Documents/Home/Menus/Recipe Ingredients.xls";
    	}
    	final String fileToUse = filename;
    	
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(fileToUse);
            }
        });
    }    
    

    private void handleExportSelection(){

		// get the selected recipes from the table model
		ArrayList selection = tableModel.getSelectedRecipes();

		// now package 'em up in a shopping list
		MenuBO menu = new MenuBO();
		menu.addMeals(selection);
		String output = menu.output(dbManager.ingredientCatsTable);
		System.out.println(output);

		// save the output
		try {
			FileServer.writeTextFile("C:/Temp/Shopping List.txt", output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Neo9MessageWindow inst = new Neo9MessageWindow(output);
		inst.setTitle("Shopping List");
		inst.setVisible(true);
	}
	
	public JButton getExportBtn() {
		return exportBtn;
	}

//
//
//						TABLE MODEL
//
//


    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"Recipe",
                                        "Select"};
        private MealPlannerTableList data = new MealPlannerTableList();

        public void populate(Collection items) {
            data.addRecipes(items);
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return getRowModelField(data.getItem(row), col);
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col == 1) {
                return true;
            } else {
                return false;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
//        public void setValueAt(Object value, int row, int col) {
//            if (DEBUG) {
//                System.out.println("Setting value at " + row + "," + col
//                                   + " to " + value
//                                   + " (an instance of "
//                                   + value.getClass() + ")");
//            }
//
//            data[row][col] = value;
//            fireTableCellUpdated(row, col);
//
//            if (DEBUG) {
//                System.out.println("New value of data:");
//                printDebugData();
//            }
//        }


        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

			RecipeBOSelectionModel model = data.getItem(row);
            if (model == null){
            	model = new RecipeBOSelectionModel();
            	data.add(model);
            }
			setRowModelField(model,col,value);
            // fireTableCellUpdated(row, col);
			fireTableRowsUpdated(row,row);


            if (DEBUG) {
                System.out.println("New value of data:");
                // printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            System.out.println("-------- DEBUG -----------");

//            for (int i=0; i < numRows; i++) {
//                System.out.print("    row " + i + ":");
//                for (int j=0; j < numCols; j++) {
//                    System.out.print("  " + data[i][j]);
//                }
//                System.out.println();
//            }

            System.out.println("--------------------------");
        }


		private Object getRowModelField(RecipeBOSelectionModel entry, int col){

			if (entry == null){
				return null;
			}

			Object value = null;

			switch (col) {
				case 0 : value = new String(entry.getName()); break;
				case 1 : value = new Boolean(entry.getSelected()); break;
				default : break;
			}

			return value;
		}


		private void setRowModelField(RecipeBOSelectionModel entry, int col, Object value){

			switch (col) {

				// case 0 : entry.setName((String)value); break; // read only!
				case 1 : entry.setSelected(((Boolean)(value)).booleanValue()); break;
				// case 1 : System.out.println("Need a new model class!!!");; break;
				default : break;
			}


			return;
		}


		private ArrayList getSelectedRecipes() {

			ArrayList selection = new ArrayList();
			for (Iterator iter = data.getAll().iterator(); iter.hasNext();) {
				RecipeBOSelectionModel element = (RecipeBOSelectionModel) iter.next();
				if (element.getSelected()) selection.add(element.getRecipe());
			}
			
			return selection;

		}


    }
}
