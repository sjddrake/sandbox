package uk.co.neo9.apps.imageviewer;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

import uk.co.neo9.utilities.file.FileServer;
import uk.co.neo9.utilities.file.RelationalFileHelper;

public class ImageRenamingModel {
	
	private ImageIcon image = null;
	private File imageFile = null;
	private int index = 0;
	private String renamingRoot = null;

	protected RelationalFileHelper helper = new RelationalFileHelper();
	
	// ============== helper methods ===============
	
	public void dump() {
		System.out.println("Haven't coded up ImageRenamingModel.dump() yet!!!");
	}
	
	public void increment() {
		this.index = this.index+1;
	}	

	public void decrement() {
		this.index = this.index-1;
	}	
	
	
	public void renameImageFile(String root) {

		// need the details from the original file
		StringBuffer lFilename = new StringBuffer();
		StringBuffer lFileExt = new StringBuffer();
		FileServer.breakdownFileName(imageFile.getName(),lFilename,lFileExt);		
		
		// build the new name
		// root_index.ext
		
		StringBuffer lFullFileName = new StringBuffer(root);
		lFullFileName.append("_");
		if (index < 10) lFullFileName.append("0");
		lFullFileName.append(index);
		lFullFileName.append(".");
		lFullFileName.append(lFileExt);
		
		// need to get the path out of the file as well
		String lPath = helper.extractPath(imageFile.getPath());
		String lFullFileDetails = helper.constructFileDetails(lFullFileName.toString(),lPath);
		
		// rename the file 
		File renameToFile = new File(lFullFileDetails);
		boolean success = imageFile.renameTo(renameToFile);	
		
		if (success){
			System.out.print("Succesfully renamed: ");
		} else {
			System.out.print("Had problems renaming: ");
		}
		System.out.println(renameToFile);
	}	
	
	
	
	public ImageIcon loadImage() {
		return loadImage(getImageFile());
	}	


	public ImageIcon loadImage(File newImageFile) {
		ImageIcon icon = loadImageFromFile(newImageFile);
		setImage(icon);
		return getImage();
	}	
	
	private ImageIcon loadImageFromFile(File newImageFile) {
		
		// load the image from this file
		// ImageIcon icon = new ImageIcon("D:/Test/DCP02678  24-Dec-2003.JPG");
		ImageIcon icon = new ImageIcon(newImageFile.getAbsolutePath());


		// scale image
 	    Image image = icon.getImage();
 	    image = image.getScaledInstance(100,100,Image.SCALE_DEFAULT);
 	    icon.setImage(image);		
		// scale end
 	    
 	    
		setImage(icon);
		
		return getImage();
	}		
	
	// ============== getters & setters ============
	
	public ImageIcon getImage() {
		return image;
	}
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public File getImageFile() {
		return imageFile;
	}
	public void setImageFile(File parentFolder) {
		this.imageFile = parentFolder;
	}
	public String getRenamingRoot() {
		return renamingRoot;
	}
	public void setRenamingRoot(String renamingRoot) {
		this.renamingRoot = renamingRoot;
	}
	
		
}
