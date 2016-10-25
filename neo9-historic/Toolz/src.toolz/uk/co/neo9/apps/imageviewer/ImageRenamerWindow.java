package uk.co.neo9.apps.imageviewer;
/*
 * SimpleTableDemo.java is a 1.4 application that requires no other files.
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import uk.co.neo9.utilities.file.FileServer;
import uk.co.neo9.utilities.file.RelationalFileHelper;

public class ImageRenamerWindow extends JPanel {
    
	private boolean DEBUG = false;
	private JButton upButton = null;
	private JButton downButton = null;
	private JButton startButton = null;
	private JButton endButton = null;
	private JButton renameButton = null;
	private JButton folderButton = null; 
	private JTextField rootText = null;

	private JTable table = null;
	private MyTableModel tableModel = null;	
//	private Object[][] dataArray = null;
	private String currentFolder = "C:/Temp";

	final int INITIAL_ROWHEIGHT = 100;
	private RelationalFileHelper helper = new RelationalFileHelper();

	
    public ImageRenamerWindow() {
    	
       // super(new GridLayout(2,0));
    	super(new BorderLayout());
      
        // final File[] files = {new File(""),new File("")};
       // final String lTestFolder = "D:/simonz/My Photos/2006/The Kids/";
//        final String lTestFolder = "D:/Test/";
//        final File[] files = FileServer.listFiles(lTestFolder);
        
        
        tableModel = new MyTableModel();
        // tableModel.populateImageList(files);
        readFolder(currentFolder);
        table = new JTable(tableModel); 
        table.setRowHeight(INITIAL_ROWHEIGHT);
        initColumnSizes(table);
        
        table.setPreferredScrollableViewportSize(new Dimension(500, 700));
        initColumnSizes(table);

        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }


        
        
        
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
        addButtons();
        
        initColumnSizes(table);

        
        table.setRowSelectionInterval(0,0);

    }

    
    
    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Image Renamer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        ImageRenamerWindow newContentPane = new ImageRenamerWindow();
        // newContentPane.addButtons();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    
    private JPanel addButtons(){
		
    	JPanel p = new JPanel();	// put a panel between
    	

    	renameButton = new JButton();
	    p.add(renameButton);		
	    renameButton.setText("Rename");
		// saveButton.setPreferredSize(new java.awt.Dimension(503, 10)); -- monkeys with the change above
	    renameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				handleRename();
			}
		});	    
	    
	    
	    rootText = new JTextField();
	    rootText.setColumns(20);
	    p.add(rootText);
	    

	   	startButton = new JButton();
	    p.add(startButton);		
	    startButton.setText("<<");       	
	    startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				handleMoveToStart();
			}
		});  	    
	    
	   	upButton = new JButton();
	    p.add(upButton);		
	    upButton.setText("<");       	
	    upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				handleMoveUp();
			}
		});      	
	    
    	downButton = new JButton();
	    p.add(downButton);		
	    downButton.setText(">");
		// saveButton.setPreferredSize(new java.awt.Dimension(503, 10)); -- monkeys with the change above
	    downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				handleMoveDown();
			}
		});
	    
	    
    	endButton = new JButton();
	    p.add(endButton);		
	    endButton.setText(">>");
		// saveButton.setPreferredSize(new java.awt.Dimension(503, 10)); -- monkeys with the change above
	    endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				handleMoveToEnd();
			}
		});	    

    	folderButton = new JButton();
	    p.add(folderButton);		
	    folderButton.setText("Folder");
		// saveButton.setPreferredSize(new java.awt.Dimension(503, 10)); -- monkeys with the change above
	    folderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				handleSelectFolder();
			}
		});
	    
		this.add(p,BorderLayout.SOUTH);
			
		return p;
	}    
    
    
    
    
    
    private void handleMoveUp(){
    	
    	// first get the row
    	int row = table.getSelectedRow();
    	if (row > 0) {
    		int newPos = tableModel.moveUp(row);
    		table.setRowSelectionInterval(newPos,newPos);
    	}
    }
    
    
    private void handleMoveDown(){
       	// first get the row
    	int row = table.getSelectedRow();
   		int newPos = tableModel.moveDown(row);
		table.setRowSelectionInterval(newPos,newPos);
   }
    
    
    private void handleMoveToStart(){
    	
    	// first get the row
    	int row = table.getSelectedRow();
    	if (row > 0) {
    		int newPos = tableModel.moveToStart(row);
    		table.setRowSelectionInterval(newPos,newPos);
    	}
    }

    private void handleMoveToEnd(){
       	// first get the row
    	int row = table.getSelectedRow();
    	int newPos = tableModel.moveToEnd(row);
		table.setRowSelectionInterval(newPos,newPos);
   }    
    
    private void handleRename(){
    	
    	// simply get the tableModel to kick off the rename
    	tableModel.renameFiles(this.rootText.getText());
    }
    

	private void handleSelectFolder() {


		
    	// get the timesheet file details from the user
    	
		//String message = "Are you sure you want to run script: "+selectedFile.getName()+"?";
		//String dialogTitle = "Confirm Run Script";

		File currentFolderFile = new File(currentFolder);
		
    	JFileChooser chooser = new JFileChooser();
    	chooser.setCurrentDirectory(currentFolderFile);
    	chooser.setDialogType(JFileChooser.OPEN_DIALOG);
//    	ExampleFileFilter filter = new ExampleFileFilter();
//    	filter.addExtension("xml");
//	    filter.setDescription("Timesheet Template Files");
//	    chooser.setFileFilter(filter);
    	
		int choice = chooser.showDialog(this,"Pick Folder");  
		
		if (choice == JFileChooser.APPROVE_OPTION && chooser.getSelectedFile() != null){
			
			readFolder(chooser.getSelectedFile().getAbsolutePath());

//	    	// delegate to the controller to use the XML loader to load it in
//			TimePeriodEntryModelList templateTimesheet = null;
//			templateTimesheet = controller.importTimesheet(chooser.getSelectedFile().getAbsolutePath());
//			
//			// now add the timesheet entries to the table model
//			tableModel.addTimesheetToModel(templateTimesheet);
		}    	
    			
		
		
	}    
    
   private void readFolder(String absolutePath) {
       final String lTestFolder = helper.extractPath(absolutePath);
       final File[] files = FileServer.listFiles(lTestFolder);
       tableModel.clearTable();
       tableModel.populateImageList(files);
       
       // save the current folder
       currentFolder = lTestFolder;
	}



private void initColumnSizes(JTable table) {

	   TableColumn column = null;
       for (int i = 0; i < 3; i++) {
    	   
           column = table.getColumnModel().getColumn(i);
           
           switch (i) {
				case MyTableModel.COL_FILENAME: column.setPreferredWidth(20); break;
				case MyTableModel.COL_IMAGE: column.setPreferredWidth(80); break;
				case MyTableModel.COL_INDEX: column.setPreferredWidth(3); break;
				default: break;
			}
       }        
    }    
    
    
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    
    
    // ======================================================================
    // ======================================================================
    // =========================== TABLE MODEL ==============================
    // ======================================================================
    // ======================================================================
    
    private class MyTableModel extends AbstractTableModel {

    	protected final static int TK_TABLE_ROWS = 60;
    	
    	protected final static int COL_INDEX = 0;
    	protected final static int COL_IMAGE = 1;
    	protected final static int COL_FILENAME = 2;

    	
    	private String[] columnNames = {"Index",
                                        "Image",
                                        "File"};

		public ImageRenamingModelList data = new ImageRenamingModelList();

        
        public void removeRow(int row) {
        	if(row > -1 && row <data.size()) data.removeItem(row);
		}

		public void renameFiles(String root) {
			// simply get the list to rename its files
			this.data.renameFiles(root);
		}

		public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
        	//System.out.println("called getRowCount()");
            return TK_TABLE_ROWS; //data.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int col) {
        	
			Class value = null;
			
			switch (col) {
				case COL_INDEX : value = Integer.class; break;
				case COL_IMAGE : value = ImageIcon.class; break;				
				case COL_FILENAME : value = String.class; break;
				default : break;
			}
			
			return value;
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            return false;
        }        
        
        public Object getValueAt(int row, int col) {
            return getRowModelField(data.getItem(row), col);
        }

        
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

            ImageRenamingModel model = data.getItem(row);
            if (model == null){
            	model = new ImageRenamingModel();
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
        
        
		private Object getRowModelField(ImageRenamingModel entry, int col){
			
			if (entry == null){
				return null;
			}
			
			Object value = null;
			
			switch (col) {
				case COL_INDEX : value = new Integer(entry.getIndex()); break;
				case COL_IMAGE : value = entry.getImage(); break;				
				case COL_FILENAME : value = entry.getImageFile().getName(); break;
				default : break;
			}
			
			return value;
		}

		

		private void setRowModelField(ImageRenamingModel entry, int col, Object value){
			
			switch (col) {
				
				case COL_INDEX : entry.setIndex(((Integer)(value)).intValue()); break;
				case COL_IMAGE : entry.setImage((ImageIcon)(value)); break;
				case COL_FILENAME : System.out.println("need to sort setting the image file!");; break;
				default : break;
			}
			
		
			
			return;
		}

		
        public void populateImageList(File[] imageFiles){
        	
        	clearTable();
        	data.populateImageList(imageFiles);
			fireTableRowsUpdated(0,data.size()-1);
			
        }		
		
        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                   // System.out.print("  " + data.get(i).dump());
				   data.getItem(i).dump();
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }

            
        
        public void clearTable(){
        	this.data.clear();
        	this.data = new ImageRenamingModelList();
        	fireTableDataChanged();
        }		

 
    	public int moveUp(int position){
    		return this.data.moveUp(position);
    	}

    	public int moveDown(int position){
    		return this.data.moveDown(position);
    	}

    	public int moveToEnd(int position){
    		return this.data.moveToEnd(position);
    	}   

    	public int moveToStart(int position){
    		return this.data.moveToStart(position);
    	}    	
    }
    
}
