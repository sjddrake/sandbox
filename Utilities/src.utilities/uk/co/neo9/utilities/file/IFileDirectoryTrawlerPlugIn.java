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
public interface IFileDirectoryTrawlerPlugIn {

	/**
	 * @param file
	 */
	void processFilePreRecurse(String pFolderName, File fileInFolder);

	void processFilePostRecurse(String pFolderName, File fileInFolder);

	/**
	 * @param string
	 * @param lNoOfFilesFound
	 */
	void endLevel(String pFolderName);

	/**
	 * @param string
	 */
	void startNewLevel(String pFolderName);

	/**
	 * @return
	 */
	boolean getIsProcessPreRecurse();

}
