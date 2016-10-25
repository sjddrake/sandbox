package uk.co.neo9.sandbox.cameraeye;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import uk.co.neo9.utilities.UtilitiesTextHelper;
import uk.co.neo9.utilities.file.FileDirectoryTrawler;
import uk.co.neo9.utilities.file.FileDirectoryTrawlerDefaultPlugIn;
import uk.co.neo9.utilities.file.FileServer;

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
public class tempDIRRenamer
	extends FileDirectoryTrawlerDefaultPlugIn {
	
	private String lastFolderName = null;
	
	public static void main(String[] args) {
		
		FileDirectoryTrawler f = new FileDirectoryTrawler();
		tempDIRRenamer plugin = new tempDIRRenamer();
		f.setPlugin(plugin);
		f.setStartingFolder("J:\\_ New To Listen\\Otter 2007 - done");
		f.go();
	
		
	}
	

	public void processFilePreRecurse(String pFolderName, File fileInFolder) {
		
	if (fileInFolder.isDirectory()) {
		

		
		String folderName = fileInFolder.getName();
		String artist = extractArtist(folderName);
		String album = extractAlbum(folderName);
		
		boolean isSameAsLast = checkSameAsLastFolderName(artist);
		lastFolderName = artist;
//		if (isSameAsLast == false) {
//			System.out.println(folderName);
//		}
		
		outputDIRCommands(artist,album);
		
	}

}
	
	private String extractAlbum(String text) {
		String album = null;
		int offset = text.indexOf("-");
		
		if (offset != - 1) {
			album = text.substring(offset+1);
			album = album.trim();
		}
		return album;
	}


	private boolean checkSameAsLastFolderName(String folderName) {
		boolean isSame = false;
		
		if (lastFolderName != null) {
			
			if (folderName != null){
				
				isSame = folderName.equalsIgnoreCase(lastFolderName);
			}

		}
		
		
		return isSame;
	}


	private String extractArtist(String text){
		String artist = null;
		int offset = text.indexOf("-");
		
		if (offset != - 1) {
			artist = text.substring(0,offset);
			artist = artist.trim();
		}
		return artist;
	}
	
	private void outputDIRCommands(String artist, String album){
		
		System.out.println("mkdir \""+artist+"\"");
		System.out.println("cd \""+artist+"\"");
		System.out.println("mkdir \""+album+"\"");
		System.out.println("cd..");

		
	}
	
	
}
