package uk.co.neo9.utilities.file;
import java.io.File;
import java.io.IOException;
import java.util.Vector;


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
public class FileDirectoryTrawler {
	
	private IFileDirectoryTrawlerPlugIn _plugin = null;
	private String _startingFolder = null;
	
	private boolean _isTopLevel = true;
	private boolean _recurseSubDirectories = true;	

	public static void main(String[] args) {


		String lStartingFolderName = null;
		lStartingFolderName = "d:/temp/a";		
		
		FileDirectoryTrawler f = new FileDirectoryTrawler();
		f.setPlugin(new FileDirectoryTrawlerDefaultPlugIn());
		f.setStartingFolder(lStartingFolderName);
		f.go();

	}

	
	public void setNonRecursive(){
		_recurseSubDirectories = false;
	}
	
	public void setRecursive(){
		_recurseSubDirectories = true;
	}	
	public void setStartingFolder(String startingFolderName) {
		_startingFolder = startingFolderName;
		
	}

	public void setPlugin(IFileDirectoryTrawlerPlugIn in) {
		_plugin = in;
		
	}

	public void go() {


		// this is the base folder we're load from
//		String lDrive = "C:/";
//
//		String lIntroText = null;
//
//		lIntroText = "Put the CD in the drive and enter the category name for its contents";
//		System.out.println(lIntroText);
//
//		String lFolderName = getUserInput();
//		
//		String lStartingFolderName = lDrive + lFolderName;
		
		
		// initialise
		_isTopLevel = true;

		File lStartingFolderFile = new File(_startingFolder);
		
		Vector lSubFolders = new Vector();
		processFolder(lStartingFolderFile,lSubFolders, _plugin);

	}
	
	
	/**
	 * Insert the method's description here.
	 * Creation date: (25/10/2001 09:11:50)
	 */ 
	private void processFolder(File folder, Vector pathHistory, IFileDirectoryTrawlerPlugIn plugin) {
	
		
		// introduced a recursion stop
		if (!_recurseSubDirectories && !_isTopLevel){
			return;
		}		
		_isTopLevel = false;
		
		plugin.startNewLevel(folder.getName());
	
		// use the file server to list the files in the folder
		File[] lInnerFileList = FileServer.listFiles(folder);
	

		// only non-empty folders will have contents
		if (lInnerFileList != null) {
			
			boolean lProcessPreRecurse = plugin.getIsProcessPreRecurse();

			if (lProcessPreRecurse) {

				// process this level BEFORE recursing down
				for (int j=0;j<lInnerFileList.length;j++) {
					plugin.processFilePreRecurse(folder.getName(),lInnerFileList[j]);
				}
	
				plugin.endLevel(folder.getName());

			}


			// 'folder' is a directory with files in it; add it to the path history
			// to pass down to the next recursion
			Vector lPathHistory = (Vector)(pathHistory.clone());
			lPathHistory.add(folder.getName());
		
			// go down the folder heirachy LAST
			for (int j=0;j<lInnerFileList.length;j++) {
				if (lInnerFileList[j].isDirectory()){
					this.processFolder(lInnerFileList[j], lPathHistory, plugin);
				}
			}


			if (!lProcessPreRecurse) {

				// process this level AFTER recursing down
				for (int j=0;j<lInnerFileList.length;j++) {
					plugin.processFilePostRecurse(folder.getName(),lInnerFileList[j]);
				}
	
				plugin.endLevel(folder.getName());

			}


//			// clean up here
//			if (lFilesFound) {
//				 	
//				System.out.println("The folder - "+folder.getName()+" - contains "+lNoOfFilesFound+" files.");
//				
//			} else {
//				
//				System.out.println("The folder - "+folder.getName()+" - is empty.");
//			}
		
		}	
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (12/03/2002 07:29:14)


		This is the main processing loop of the CBM app.
		From here we get user input channeled in from the
		input controller, we interogate the MP3 manager
		for data and we display output to the user via
		the page manger.

 
	 */
	public static String getUserInput() {

	String lCommandLine = null;

		// get some input from the user

		byte[] buffer = new byte[256];
		int bytesRead = 0;
		try {
	
	
			bytesRead = System.in.read(buffer);

		} 
		catch (IOException e) {
			System.err.println(e);
		}
	
		// process the input
		byte[] data = new byte[256];
		int lLength = 0;
	
		for (int i=0;i<bytesRead;i++) {

			if (buffer[i] == '\n') {

				lLength = i-1;
				break;

			} else {

				data[i] = buffer[i];
			
			}

		
		}
	
		lCommandLine = new String(data, 0, lLength);

		return lCommandLine;	
	}


}
