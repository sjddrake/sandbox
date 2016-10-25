package uk.co.neo9.apps.filerenamer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import uk.co.neo9.utilities.UtilitiesTextHelper;
import uk.co.neo9.utilities.file.FileServer;
import uk.co.neo9.utilities.file.IFileDirectoryTrawlerPlugIn;
import uk.co.neo9.utilities.file.RelationalFileHelper;

/*
 * Created on 20-Aug-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PhotoFileRenamerPlugIn
	implements IFileDirectoryTrawlerPlugIn {
		
	int count = 0;
	protected RelationalFileHelper helper = new RelationalFileHelper();
	protected final SimpleDateFormat FILE_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");

	
	public String formatDate(Date pDate) {
		
		// null check
		if (pDate == null) return "";
		
		// the thing
		return FILE_DATE_FORMAT.format(pDate);
	}		

	
	/* (non-Javadoc)
	 * @see IFileDirectoryTrawlerPlugIn#processFilePreRecurse(java.lang.String, java.io.File)
	 */
	public void processFilePreRecurse(String pFolderName, File fileInFolder) {
		

		
		if (!fileInFolder.isDirectory()) {
			
			StringBuffer lFullFileName = new StringBuffer();
			StringBuffer lFilename = new StringBuffer();
			StringBuffer lFileExt = new StringBuffer();
			FileServer.breakdownFileName(fileInFolder.getName(),lFilename,lFileExt);
			
			if (lFileExt.toString().equalsIgnoreCase("JPG")) {
			
				// increment the counter
				count++;
				
				// date stamp
				long lastModified = fileInFolder.lastModified();
				String format = formatDate(new Date(lastModified));
				
				
				// now convert to date ad append to file name
				lFullFileName.append(lFilename.toString());
				lFullFileName.append(" ");
				lFullFileName.append(format);
				lFullFileName.append(".");
				lFullFileName.append(lFileExt.toString());
				
				
				// need to get the path out of the file as well
				String lPath = helper.extractPath(fileInFolder.getPath());
				String lFullFileDetails = helper.constructFileDetails(lFullFileName.toString(),lPath);
				
				// rename the file 
				File renameToFile = new File(lFullFileDetails);
				boolean success = fileInFolder.renameTo(renameToFile);	
				
				if (success){
					System.out.print("Succesfully renamed: ");
				} else {
					System.out.print("Had problems renaming: ");
				}
				System.out.println(renameToFile);
			
			} else {
				
				try {
					System.out.println("Skipped file: "+fileInFolder.getCanonicalPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		} else {
			
			System.out.println(" > Subfolder: "+fileInFolder.getName());
			
		}

	}

	/* (non-Javadoc)
	 * @see IFileDirectoryTrawlerPlugIn#processFilePostRecurse(java.lang.String, java.io.File)
	 */
	public void processFilePostRecurse(String pFolderName, File fileInFolder) {
		
		
	}

	/* (non-Javadoc)
	 * @see IFileDirectoryTrawlerPlugIn#endLevel(java.lang.String)
	 */
	public void endLevel(String pFolderName) {
		
		System.out.println("total number of files = "+count);
		System.out.println("-----------------------------");
		count = 0;
	}

	/* (non-Javadoc)
	 * @see IFileDirectoryTrawlerPlugIn#startNewLevel(java.lang.String)
	 */
	public void startNewLevel(String pFolderName) {
		
		System.out.println(pFolderName+" >");
		
	}

	/* (non-Javadoc)
	 * @see IFileDirectoryTrawlerPlugIn#getIsProcessPreRecurse()
	 */
	public boolean getIsProcessPreRecurse() {
		
		return true;
	}

}