package uk.co.neo9.utilities.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.JCheckBox;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import uk.co.neo9.utilities.file.FileDirectoryTrawler;
import uk.co.neo9.utilities.file.FileDirectoryTrawlerDefaultPlugIn;
import uk.co.neo9.utilities.file.IFileDirectoryTrawlerPlugIn;


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
public class QuickFileChooserAppWindow extends javax.swing.JFrame {

	public final static int VERSION = 1;
	
	protected JCheckBox isRecursiveCHKBX;
	private JFileChooser fileChooser;

	private JMenuBar jMenuBar1;
	
	protected FileDirectoryTrawler directoryTrawler = null;
	protected IFileDirectoryTrawlerPlugIn plugin = null;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {

		QuickFileChooserAppWindow inst = new QuickFileChooserAppWindow();
		inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inst.setVisible(true);
	}
	
	
	public IFileDirectoryTrawlerPlugIn initialisePlugin(){
		return new FileDirectoryTrawlerDefaultPlugIn();
	}
	
	public QuickFileChooserAppWindow() {
		super();

		directoryTrawler = new FileDirectoryTrawler();
		plugin = initialisePlugin();
		directoryTrawler.setPlugin(plugin);
		
		initGUI();
	}
	
	protected int getFileSelectionMode(){
		return JFileChooser.DIRECTORIES_ONLY;
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.getContentPane().setLayout(thisLayout);
			this.setSize(800, 600);
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(jMenuBar1);

			}


			// ------ FileChooser -------------
			{
				fileChooser = new JFileChooser();

			    Neo9FileFilterAdaptor filter = getFileFilter();
			    fileChooser.setFileFilter(filter);
			    fileChooser.setFileSelectionMode(getFileSelectionMode());
			    
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
				addControls();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected void addControls(){
		
//		isRecursiveCHKBX = new JCheckBox();
//		this.getContentPane().add(isRecursiveCHKBX, BorderLayout.NORTH);
//		isRecursiveCHKBX.setText("Recurse Sub-Directories?");
//		isRecursiveCHKBX.setSelected(true);	
		
		
		Container controlParent = this.getContentPane();

		isRecursiveCHKBX = new JCheckBox();
		controlParent.add(isRecursiveCHKBX, BorderLayout.NORTH);
		isRecursiveCHKBX.setText("Recurse Sub-Directories?");
		isRecursiveCHKBX.setSelected(true);				
		
		do_addControls(controlParent);
		
	}

	protected void do_addControls(Container controlParent){
		
			// overide this
	}

	protected Neo9FileFilterAdaptor getFileFilter() {	
		Neo9FileFilterAdaptor filter = new Neo9FileFilterAdaptor();
		filter.addExtension("*");
		filter.setDescription("Any Files");
		return filter;
	}

	// workings 
	public void handleDoIt(File selectedFile){
		
//		// first verify that this is the correct one to run
//		String lPath = plugin.helper.extractPath(selectedFile.getPath());
//		String message = "Are you sure you want to rename the files in "+lPath+"?";
//		String dialogTitle = "Confirm";
//		int choice = JOptionPane.showConfirmDialog(this,message,dialogTitle,JOptionPane.OK_CANCEL_OPTION);  
//		if (choice == JOptionPane.CANCEL_OPTION){
//			// ignore
//			return;
//		}		
//
//		f.setStartingFolder(lPath);
//		if (isRecursiveCHKBX.isSelected()){
//			f.setRecursive();
//		} else {
//			f.setNonRecursive();
//		}
//		f.go();
		
	
	}
	
	
	
}
