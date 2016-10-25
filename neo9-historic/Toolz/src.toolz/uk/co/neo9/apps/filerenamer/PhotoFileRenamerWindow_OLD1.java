package uk.co.neo9.apps.filerenamer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JCheckBox;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import uk.co.neo9.utilities.file.FileDirectoryTrawler;


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
public class PhotoFileRenamerWindow_OLD1 extends javax.swing.JFrame {

	public final static int VERSION = 1;
	
	private JMenuItem helpMenuItem;
	private JMenu jMenu5;
	private JMenuItem deleteMenuItem;
	private JSeparator jSeparator1;
	private JMenuItem pasteMenuItem;
	private JCheckBox isRecursiveCHKBX;
	private JFileChooser fileChooser;
	private JMenuItem copyMenuItem;
	private JMenuItem cutMenuItem;
	private JMenu jMenu4;
	private JMenuItem exitMenuItem;
	private JSeparator jSeparator2;
	private JMenuItem closeFileMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem openFileMenuItem;
	private JMenuItem newFileMenuItem;
	private JMenu jMenu3;
	private JMenuBar jMenuBar1;
	
	FileDirectoryTrawler f = null;
	PhotoFileRenamerPlugIn plugin = null;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {

		PhotoFileRenamerWindow_OLD1 inst = new PhotoFileRenamerWindow_OLD1();
		inst.setVisible(true);
	}
	
	public PhotoFileRenamerWindow_OLD1() {
		super();

		f = new FileDirectoryTrawler();
		plugin = new PhotoFileRenamerPlugIn();
		f.setPlugin(plugin);
		
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.getContentPane().setLayout(thisLayout);
			this.setSize(800, 600);
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(jMenuBar1);
//x
//				{
//					jMenu3 = new JMenu();
//					jMenuBar1.add(jMenu3);
//					jMenu3.setText("File");
//					{
//						newFileMenuItem = new JMenuItem();
//						jMenu3.add(newFileMenuItem);
//						newFileMenuItem.setText("New");
//					}
//					{
//						openFileMenuItem = new JMenuItem();
//						jMenu3.add(openFileMenuItem);
//						openFileMenuItem.setText("Open");
//					}
//					{
//						saveMenuItem = new JMenuItem();
//						jMenu3.add(saveMenuItem);
//						saveMenuItem.setText("Save");
//					}
//					{
//						saveAsMenuItem = new JMenuItem();
//						jMenu3.add(saveAsMenuItem);
//						saveAsMenuItem.setText("Save As ...");
//					}
//					{
//						closeFileMenuItem = new JMenuItem();
//						jMenu3.add(closeFileMenuItem);
//						closeFileMenuItem.setText("Close");
//					}
//					{
//						jSeparator2 = new JSeparator();
//						jMenu3.add(jSeparator2);
//					}
//					{
//						exitMenuItem = new JMenuItem();
//						jMenu3.add(exitMenuItem);
//						exitMenuItem.setText("Exit");
//					}
//				}
//				{
//					jMenu4 = new JMenu();
//					jMenuBar1.add(jMenu4);
//					jMenu4.setText("Edit");
//					{
//						cutMenuItem = new JMenuItem();
//						jMenu4.add(cutMenuItem);
//						cutMenuItem.setText("Cut");
//					}
//					{
//						copyMenuItem = new JMenuItem();
//						jMenu4.add(copyMenuItem);
//						copyMenuItem.setText("Copy");
//					}
//					{
//						pasteMenuItem = new JMenuItem();
//						jMenu4.add(pasteMenuItem);
//						pasteMenuItem.setText("Paste");
//					}
//					{
//						jSeparator1 = new JSeparator();
//						jMenu4.add(jSeparator1);
//					}
//					{
//						deleteMenuItem = new JMenuItem();
//						jMenu4.add(deleteMenuItem);
//						deleteMenuItem.setText("Delete");
//					}
//				}
//				{
//					jMenu5 = new JMenu();
//					jMenuBar1.add(jMenu5);
//					jMenu5.setText("Help");
//					{
//						helpMenuItem = new JMenuItem();
//						jMenu5.add(helpMenuItem);
//						helpMenuItem.setText("Help");
//					}
//				}
//x
			}


			// ------ FileChooser -------------
			{
				fileChooser = new JFileChooser();

			    ExampleFileFilter filter = new ExampleFileFilter();
			    this.setTitle("Photo File Renamer");
			    filter.addExtension("jpg");
			    filter.addExtension("mov");
			    filter.addExtension("avi");
			    filter.setDescription("Photo Files");
			    fileChooser.setFileFilter(filter);
			 //   fileChooser.setApproveButtonText("Run Script");

				
				this.getContentPane().add(fileChooser,BorderLayout.CENTER);
				fileChooser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						if (evt.getActionCommand().equalsIgnoreCase("ApproveSelection")) {
							handleDoIt(fileChooser.getSelectedFile());
						} else if (evt.getActionCommand().equalsIgnoreCase("CancelSelection")) {
							System.exit(0);
						}
						
					}
				});
			}
			{
				isRecursiveCHKBX = new JCheckBox();
				this.getContentPane().add(isRecursiveCHKBX, BorderLayout.NORTH);
				isRecursiveCHKBX.setText("Recurse Sub-Directories?");
				isRecursiveCHKBX.setSelected(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// workings 
	public void handleDoIt(File selectedFile){
		
		// first verify that this is the correct one to run
		String lPath = plugin.helper.extractPath(selectedFile.getPath());
		String message = "Are you sure you want to rename the files in "+lPath+"?";
		String dialogTitle = "Confirm";
		int choice = JOptionPane.showConfirmDialog(this,message,dialogTitle,JOptionPane.OK_CANCEL_OPTION);  
		if (choice == JOptionPane.CANCEL_OPTION){
			// ignore
			return;
		}		

		f.setStartingFolder(lPath);
		if (isRecursiveCHKBX.isSelected()){
			f.setRecursive();
		} else {
			f.setNonRecursive();
		}
		f.go();
		
		
//
//		// first verify that this is the correct one to run
//		String message = "Are you sure you want to run script: "+selectedFile.getName()+"?";
//		String dialogTitle = "Confirm Run Script";
//		int choice = JOptionPane.showConfirmDialog(this,message,dialogTitle,JOptionPane.OK_CANCEL_OPTION);  
//		if (choice == JOptionPane.CANCEL_OPTION){
//			// ignore
//			return;
//		}
//		
//		
//		// the file
//		StringBuffer lFileDetails = new StringBuffer();
//		lFileDetails.append(selectedFile.getPath());
//
//		
//		// load in the sql commands
//		Vector lSQLCommands = null;
//		try {
//			lSQLCommands = FileServer.readTextFile(lFileDetails.toString());
//		} catch (IOException e) {
//
//			e.printStackTrace();
//			System.exit(-1);
//		}
//		
//		// loop through the commands
//		TimekeeperBatchDAO dao = DAOFactory.vendBatchDAO();
//		boolean lSuccess = false;
//		for (Iterator iter = lSQLCommands.iterator(); iter.hasNext();) {
//
//			String lCommand = (String) iter.next();
//			System.out.println(lCommand);
//
//			// fire the command off at the db
//			lSuccess = dao.save(lCommand);
//			
//			// check for success
//			if (!lSuccess){
//				System.out.println("Last SQL statement through an exception!");
//				System.exit(-1);
//			}
//		}
//		
//		// done?
//		JOptionPane.showMessageDialog(this, "Script Complete!");
//	
		
	}
	
	
	
}
