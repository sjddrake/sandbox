package uk.co.neo9.utilities.file;
import java.io.File;

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
public class FileDirectoryTrawlerDefaultPlugIn
	implements IFileDirectoryTrawlerPlugIn {
		
	int count = 0;

	/* (non-Javadoc)
	 * @see IFileDirectoryTrawlerPlugIn#processFilePreRecurse(java.lang.String, java.io.File)
	 */
	public void processFilePreRecurse(String pFolderName, File fileInFolder) {
		
		if (!fileInFolder.isDirectory()) {
			
			String lFileName = fileInFolder.getName();
			if (!lFileName.equalsIgnoreCase("Thumbs.db")){
				System.out.println(" - "+lFileName);
				count++;
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
