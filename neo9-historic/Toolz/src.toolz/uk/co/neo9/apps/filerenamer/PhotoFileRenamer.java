package uk.co.neo9.apps.filerenamer;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import uk.co.neo9.utilities.file.FileDirectoryTrawler;
import uk.co.neo9.utilities.file.FileDirectoryTrawlerDefaultPlugIn;
import uk.co.neo9.utilities.file.FileServer;
import uk.co.neo9.utilities.file.IFileDirectoryTrawlerPlugIn;


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
public class PhotoFileRenamer {
	
	public static void main(String[] args) {


		String lStartingFolderName = null;
		lStartingFolderName = "d:/temp/new folder";		
		
		FileDirectoryTrawler f = new FileDirectoryTrawler();
		f.setPlugin(new PhotoFileRenamerPlugIn());
		f.setStartingFolder(lStartingFolderName);
		f.go();

	}
}
