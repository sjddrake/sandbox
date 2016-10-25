package uk.co.neo9.apps.filerenamer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
import uk.co.neo9.utilities.gui.Neo9FileFilterAdaptor;
import uk.co.neo9.utilities.gui.QuickFileChooserAppWindow;


public class PhotoFileRenamerWindow extends QuickFileChooserAppWindow {

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {

		PhotoFileRenamerWindow inst = new PhotoFileRenamerWindow();
		inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inst.setVisible(true);
	}
	
	public IFileDirectoryTrawlerPlugIn initialisePlugin(){
		return new PhotoFileRenamerPlugIn();
	}

	public PhotoFileRenamerPlugIn getPlugin(){
		this.setTitle("Photo File Renamer"); // crap place for this!!!
		return (PhotoFileRenamerPlugIn)plugin;
	}

	
	protected Neo9FileFilterAdaptor getFileFilter() {	
		Neo9FileFilterAdaptor filter = new Neo9FileFilterAdaptor();
		this.setTitle("Photo File Renamer");
		filter.addExtension("jpg");
		filter.addExtension("mov");
		filter.addExtension("avi");
		filter.setDescription("Photo Files");
		return filter;
	}	
	
	// workings 
	public void handleDoIt(File selectedFile){
		
		// first verify that this is the correct one to run
		String lPath = getPlugin().helper.extractPath(selectedFile.getPath());
		String message = "Are you sure you want to rename the files in "+lPath+"?";
		String dialogTitle = "Confirm";
		int choice = JOptionPane.showConfirmDialog(this,message,dialogTitle,JOptionPane.OK_CANCEL_OPTION);  
		if (choice == JOptionPane.CANCEL_OPTION){
			// ignore
			return;
		}		

		directoryTrawler.setStartingFolder(lPath);
		if (isRecursiveCHKBX.isSelected()){
			directoryTrawler.setRecursive();
		} else {
			directoryTrawler.setNonRecursive();
		}
		directoryTrawler.go();
		
	}
	
}
