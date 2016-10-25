/*
 * Created on 18-Dec-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities.file;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RelationalFileHelper {

	private Vector pathAsLevels = null;
	
	
	
	public void setWorkingFolder(String pPath){
		
		pathAsLevels = new Vector();
		
		// break the path up into its constituents
		
		StringTokenizer s = new StringTokenizer(pPath,"/",false);
		String lToken = null;
		while (s.hasMoreTokens()) {
			lToken = s.nextToken();
			pathAsLevels.add(lToken);
		}
		
	}

	

	public Vector deconstructFileDetails(String pRelationalFileDetails){
		
		// break the file details up into its constituents
		Vector lRelationalFileDetailsConstituents = new Vector();
		StringTokenizer s = new StringTokenizer(pRelationalFileDetails	,"/",false);
		String lToken = null;
		
		if (s.countTokens() < 2) {
			s = new StringTokenizer(pRelationalFileDetails	,"\\",false);
		}
		
		while (s.hasMoreTokens()) {
			lToken = s.nextToken();
			lRelationalFileDetailsConstituents.add(lToken);
		}
		return lRelationalFileDetailsConstituents;
	}


	public String extractPath(String pFileDetails){
		
		// break the file details up into its constituents
		Vector lFileDetailsConstituents;
		lFileDetailsConstituents = deconstructFileDetails(pFileDetails);
		
		// remove the file name from parts
		lFileDetailsConstituents.remove(lFileDetailsConstituents.size()-1);
		
		return reconstructFileDetails(lFileDetailsConstituents,"");
	}
	
	
	
	public String constructFileDetails(String pFileDetails, String pWorkingFolder) {

		// convert the path
		setWorkingFolder(pWorkingFolder);
		Vector lWorkingFolder = new Vector();
		lWorkingFolder.addAll(this.pathAsLevels);


		// now deconstruct the filename
		Vector lFileDetails = deconstructFileDetails(pFileDetails);
		
		
		// ok, now process the deconstructed file details to form the true path
		String lFileName = (String)lFileDetails.remove(lFileDetails.size()-1);
		for (Iterator iter = lFileDetails.iterator(); iter.hasNext();) {
			
			String lFolderName = (String)iter.next();
			
			if (lFolderName.equalsIgnoreCase("..")){
				
				// remove a level of the path
				lWorkingFolder.remove(lWorkingFolder.size()-1);
				
			} else {
				
				// add a level
				lWorkingFolder.add(lFolderName);
			}
			
		}

		// re-constitute the path and file name
		String lFullFileDetails = reconstructFileDetails(lWorkingFolder,lFileName);		
		
		return lFullFileDetails;
	}
	


	private String reconstructFileDetails(Vector pPath, String pFileName){
		
		StringBuffer lFileDetails = new StringBuffer();
		for (Iterator iter = pPath.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			lFileDetails.append(element);
			lFileDetails.append('/');
		}
		lFileDetails.append(pFileName);
		
		return lFileDetails.toString();
	}



//
//	
//	public String constructFileDetails(String pFileName, String pWorkingFolder) {
//
//		// convert the path
//		setWorkingFolder(pWorkingFolder);
//
//		// see if we need to process the fileDetails
//		boolean lIsRelational = isRelational(pFileName);
//		String lFileName = null; 
//		int lNoOfFoldersToMoveUp = 0;
//		
//		if (lIsRelational) {
//			
//			// work out how many levels we need to go up the folder stack
//			lNoOfFoldersToMoveUp = getNoOfLevelsFromFileDetails(pFileName);
//			
//			// get the file name
//			lFileName = getFileNameFromFileDetails(pFileName);
//			
//			// split the working folder into its levels
//		
//		} else {
//			
//			// we'll simply append the file name to the path given
//			// but use the relational data as the path passed
//			// in has not been checked for a trailing '/'
//			lFileName = new String(pFileName);
//			
//		}
//		
//		
//		// re-constitute the path and file name
//		String lFullFileDetails = buildRelationalFileDetails(lNoOfFoldersToMoveUp,lFileName);		
//		
//		return lFullFileDetails;
//	}
//	
//
//


	private String buildRelationalFileDetails(int pNoOfLevelsUp, String pFileName){
		
		String lFullFileDetails = new String();
		
		int lNoOfLevelsToUse = pathAsLevels.size()-pNoOfLevelsUp;
		
		for (int i = 0; i < lNoOfLevelsToUse; i++) {
			String lPathLevel = (String)pathAsLevels.elementAt(i);
			lFullFileDetails = lFullFileDetails + lPathLevel + "/";
		}
		
		lFullFileDetails = lFullFileDetails + pFileName;
		
		return lFullFileDetails;
	}


	private boolean isRelational(String pFileDetails) {
			
		StringTokenizer s = new StringTokenizer(pFileDetails,"/",false);
		
		int lNoOfTokens = s.countTokens();

		boolean lIsRelational = false;
		if (lNoOfTokens > 1) lIsRelational = true;
		
		return lIsRelational;
	
	}


	private String getFileNameFromFileDetails(String pFileDetails) {
		
		StringTokenizer s = new StringTokenizer(pFileDetails,"/",false);
		
		int lNoOfTokens = s.countTokens();
		
		String lToken = null;
		String lFileName = null;
		int lNoOfFoldersToMoveUp = 0;
		for (int i = 0; i < lNoOfTokens; i++) {

			lToken = s.nextToken();

			if (i==lNoOfTokens-1) lFileName = lToken;
				
		}
			
		return lFileName;
	}	


	private int getNoOfLevelsFromFileDetails(String pFileDetails) {
		
		StringTokenizer s = new StringTokenizer(pFileDetails,"/",false);
		
		int lNoOfTokens = s.countTokens();
		
		String lToken = null;
		String lFileName = null;
		int lNoOfFoldersToMoveUp = 0;
		for (int i = 0; i < lNoOfTokens; i++) {

			lToken = s.nextToken();
			
			if (lToken.equalsIgnoreCase("..")) lNoOfFoldersToMoveUp = lNoOfFoldersToMoveUp+1;
			
			if (i==lNoOfTokens-1) lFileName = lToken;
				
		}
			
		return lNoOfFoldersToMoveUp;
	}	
	
	

	static public boolean testDIRLineForFileName(String filedetails) {
		
		// valid input safety check first
		if (filedetails == null || filedetails.length() == 0) {
			return false;
		} else {
			if (filedetails.length() < 4){
				return false;
			}
		}
		
		// first check the text line is for a file
		boolean isFile = false;
		if (filedetails.charAt(filedetails.length()-4) == '.'){
			isFile = true;
		}
		
		return isFile;
		
	}

	
	

}
