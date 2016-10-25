package uk.co.neo9.sandbox.cameraeye;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
public class PhotoFileArchiveChecker {
	
	
	private IncrementedTextModel model = null;
	
	
	public static void main(String[] args) {

		String lStartingFolderName = null;
		lStartingFolderName = "C:/simonz/Temp (simonz)";		

		PhotoFileArchiveChecker application = new PhotoFileArchiveChecker();
		application.go_trawler(lStartingFolderName);
	}
	
	
	protected void go_trawler(String startingFolderName){
		
		FileDirectoryTrawler f = new FileDirectoryTrawler();
		PhotoFileArchiveCheckerPlugIn plugin = new PhotoFileArchiveCheckerPlugIn();
		f.setPlugin(plugin);
		f.setStartingFolder(startingFolderName);
		f.go();
		
		// now get the list to work with
		processFileNames(plugin.getCapturedFileNames());
	}
	

	protected void go_dirdump(String fileDetails){
		
		// load the contents of the dir dump file
		List dirDump = loadDIRList(fileDetails);
		
		// extract the file names
		List fileNames = stripDownFileDetails(dirDump);
		
		// process the file names
		processFileNames(fileNames);
	}
	
	
	protected List loadDIRList(String fileDetails){
		
		List filenames = null;
		try {
			Vector fileLines = FileServer.readTextFile(fileDetails);
			filenames = new ArrayList();
			filenames.addAll(fileLines);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to load the file: "+fileDetails);
			System.exit(-1);// TODO Haven't I made a logger that wraps this?
		}
				
		return filenames;
	}
	
	
	protected List stripDownFileDetails(List capturedFileDetails) {
		
		List capturedFileNames = new ArrayList(capturedFileDetails.size());
		
		// this method is for using the details loaded in from a file
		for (Iterator iter = capturedFileDetails.iterator(); iter.hasNext();) {
			String fileDetails = (String) iter.next();
			String lFilename = FileServer.getFileNameFromFileDetails(fileDetails);
			capturedFileNames.add(lFilename.toString());
		}
		
		Collections.sort(capturedFileNames);
		
		return capturedFileNames;
		
	}
	
	
	protected List filterFileDetails(List capturedFileDetails, List validExtensions) {
		
		
		List extensions = new ArrayList(validExtensions);
		for (Iterator iter = validExtensions.iterator(); iter.hasNext();) {
			String ext = (String) iter.next();
			extensions.add("."+ext);
		}
		
		List capturedFileNames = new ArrayList(capturedFileDetails.size());
		
		// this method is for using the details loaded in from a file
		for (Iterator iter = capturedFileDetails.iterator(); iter.hasNext();) {
			String fileDetails = (String) iter.next();
			String lFilename = FileServer.getFileNameFromFileDetails(fileDetails);
			boolean include = checkFileNameForInclusion(lFilename,extensions);
			if (include) {
				capturedFileNames.add(lFilename.toString());
			}
		}
		
		Collections.sort(capturedFileNames);
		
		return capturedFileNames;
		
	}
		
	
	
	protected boolean checkFileNameForInclusion(String filename, List extensions) {
		// TODO Auto-generated method stub
		return false;
	}


	protected void processFileNames(List capturedFileNames) {

		List filenamesToCheck = new ArrayList();
		
		// filter out any none camera type files
		System.out.println(" XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX ");
		for (Iterator iter = capturedFileNames.iterator(); iter.hasNext();) {
			String filename = (String) iter.next();
			boolean include = checkFileNameForInclusion(filename);
			if (include) {
				System.out.println(filename);
				filenamesToCheck.add(filename);
			}
			
		}
		
		
		// simple checks
		if (filenamesToCheck.size() == 0) {
			System.out.println("No files found!!!");
			return;
		}
		
		
		// now do the processing
		
		/*
		 * ALGORITHM
		 * 
		 * - start at a suitable number
		 * - get incremented filenames in the correct style
		 * - check the increments against the first filename in the list
		 * - log result
		 * - when we get a match, move on to the next filename and continue incrementing
		 * - end when all our filenames are done or we have no matches for a 100
		 *   consecutive iterations
		 * 
		 */
		
		int seed = 1;
		int filenamesIndex = 0;
		int noOfFilenames = filenamesToCheck.size();
		boolean safetyStop = false;
		String incrementedFilename, filenameToCheck;
		IncrementedTextGenerator generator = new IncrementedTextGenerator(model);
		
		while (safetyStop == false) {
			
			filenameToCheck = (String)filenamesToCheck.get(filenamesIndex);
			filenamesIndex++;
			
			//
			// we need an inner loop or at least a condition to do nothing in the loop??
			//
			final int cutOffPoint = 100;
			int count = 0;
			boolean matchFound = false;
			for (;((count<cutOffPoint) && (matchFound == false)); count++) {
				
				// generate the incremented file name
				incrementedFilename = generator.generateNext();
				matchFound = checkFilenameMatch(filenameToCheck,incrementedFilename);
				
				// report on the result
				if (matchFound) {
					
				} else {

				}
				
			}
			
			if ((count == cutOffPoint) || (filenamesIndex == noOfFilenames)) {
				safetyStop = true;
			}
			

		}
		
		
	}
	
	
	protected boolean checkFilenameMatch(String filenameToCheck, String incrementedFilename) {
		boolean matchFound = false;
		int result = filenameToCheck.indexOf(incrementedFilename);
		if (result != -1) {
			matchFound = true;
		}
		return matchFound;
	}


	protected boolean checkFileNameForInclusion(String filename){
		
		boolean include = false;
		
		include = checkFileNameForDCF(filename);
		
		
		return include;
	}
	
	
	protected boolean checkFileNameForDCF(String filename){
		
		boolean include = false;
		
		int index = filename.indexOf("DCF");
		if (index == 0) {
			include = true;
		}
		
		
		return include;
	}


	public void setParams(IncrementedTextModel inputs) {
		model= inputs;
		
	}
	
}
