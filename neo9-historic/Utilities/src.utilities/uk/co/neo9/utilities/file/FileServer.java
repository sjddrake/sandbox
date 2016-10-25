package uk.co.neo9.utilities.file;

import java.io.*;
import java.util.*;

import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import uk.co.neo9.utilities.CommonConstants;

/**
 * Insert the method's description here.
 * Creation date: (18/10/2001 11:00:10)
 * @return java.util.Vector
 * @param csvLine java.lang.String
 */

 
public final class FileServer
{

	private String bufferedLine = null;
	private FileReader fileLineReader = null;
	private boolean fileLineReadingDone = true;
	

	
	public static Vector extractCSVFields(String csvLine) {
		return CSVHelper.extractCSVFields(csvLine);
	}

/**
 * Insert the method's description here.
 * Creation date: (25/10/2001 09:44:51)
 */
public boolean initialiseForReadByLine(String fileDetails) {

	boolean lSuccess = true;

	// open a file reader
	try {
		
		this.fileLineReader = new FileReader(fileDetails);
		this.fileLineReadingDone = false;

	} catch (FileNotFoundException fnf) {
		System.out.println("couldn't find file "+fileDetails);
		lSuccess = false;
	}
	
	return lSuccess;
}/**
 * Insert the method's description here.
 * Creation date: (22/10/2001 10:08:25)
 * @return java.lang.String[]
 */
public static String[] listDrives() {

	Vector lDrives = new Vector();
	File[] lRoots;

	// list the drives on the machine
	lRoots = File.listRoots();


	// get the drive name as strings
	for (int i=0;i<lRoots.length;i++) {
		lDrives.add(lRoots[i].getPath());
	}


	// convert the vector into an array
	String[] lArraySeed = new String[1];
	lArraySeed = (String[])(lDrives.toArray(lArraySeed));

	
	return lArraySeed;
}


public static UtilitiesSmartFileMetaData getUtilitiesSmartFileMetaData(String pFileDetails) {


	//	read the first line of the file in and set the metadata
	Vector lTextFileLines = null;
	try {
		lTextFileLines = FileServer.readTextFile(pFileDetails);
	} catch (IOException e) 
	{
		//-> handle the exception!
	}
	
	String lFirstLineInFile = (String)(lTextFileLines.elementAt(0));
	
	// now extract the data from it
	UtilitiesSmartFileMetaData lMetaData = new UtilitiesSmartFileMetaData();
	
	if (lFirstLineInFile!=null){
		lMetaData.setMetaData(lFirstLineInFile);
	}
	
	return lMetaData; 

}
/**
 * Insert the method's description here.
 * Creation date: (23/10/2001 13:43:42)
 * @return java.io.File[]
 * @param folder java.io.File
 */
public static File[] listFiles(File folder) {

	File[] lFolderContents = null;
	
	if (folder.isDirectory()) {

		lFolderContents = folder.listFiles();

	}

	return lFolderContents;
}/**
 * Insert the method's description here.
 * Creation date: (23/10/2001 13:47:44)
 * @return java.io.File[]
 * @param folder java.lang.String
 */
public static File[] listFiles(String folder) {

	File lFolder = new File(folder);

	File[] lFolderContents = lFolder.listFiles();

	return lFolderContents;
	
}/**
 * Insert the method's description here.
 * Creation date: (23/10/2001 12:51:02)
 * @param folder java.lang.String
 */
public static String[] listFolderContents(String folderName) {
	
	
	File lFolder = new File(folderName);

	String[] lFolderContents = lFolder.list();
	
	if (lFolderContents == null) {
		System.err.println("Empty folder > "+folderName);
	}

	return lFolderContents;
		
}



public static String[] listFolderContents(String folderName, String restrictToExt) {
	
	
	File lFolder = new File(folderName);

	String[] lFolderContents = lFolder.list();

	List<String> filteredFilenames = new ArrayList<String>(lFolderContents.length);
	for (int i = 0; i < lFolderContents.length; i++) {
		String filename = lFolderContents[i];
		if (filename.endsWith(restrictToExt)) {
			filteredFilenames.add(filename);
		}
	}
	
	
	lFolderContents = (String[]) filteredFilenames.toArray(new String[0]);
	
	return lFolderContents;
		
}


/**
 * Insert the method's description here.
 * Creation date: (25/10/2001 11:14:07)
 */
public void pushBackLine(String lLine) {
	this.bufferedLine = lLine;
}/**
 * Insert the method's description here.
 * Creation date: (25/10/2001 09:46:09)
 */
public String readFileByLine() {

	// set up the string to hold the line from the textfile
	String lLine = null;

	if (this.bufferedLine != null) {

		// the controlling code may have 'pushed back' a line
		// this needs returning before another read
		lLine = this.bufferedLine;
		this.bufferedLine = null;
		
	}
	else {
	
		try {

			// this boolean is a flag to indicate that a file
			// read has completed
			if (this.fileLineReadingDone) {

				// nullify the file reader instance variable
				this.fileLineReader = null;
			}
				
			// check that the reader instance has been setup ok
			else if (this.fileLineReader != null) {

				// get a local handle to the reader
				FileReader fr = this.fileLineReader;

				
				// this loop makes sure we stop at the end of the file
				StringBuffer line = null;
				boolean endOfFile = false;
				boolean endOfLine = false;
				while ((endOfFile == false) && (endOfLine == false))
				{
					
				  /*
				  // <debug>
				  System.out.print(nextString);
				  System.out.print(' ');
				  // </debug>
				  */


				  
				  // this loop reads in a line
				  line = new StringBuffer();
				  while (endOfLine == false) 
				  {

					// read some bytes till we reach the end of the line
					// or end of the file completely
					int i = fr.read();


					// process the character we've just read in
					if (i == -1) 
					{
						// reached the end of the file so the final line 
				    	// is in the buffer
						lLine = line.toString();
						endOfLine = true;
						endOfFile = true;
						
					 }

					
					 else if (i == '\r') 
					 {

						// ignore this character


					 }
					 
					 
					 else if (i == '\n') 
					 {

						// reached the end of the line so extract the previously read bytes as a line of text
						lLine = line.toString();
						endOfLine = true;

					 }

					 else
					 {
						// add the byte that's been read into the string buffer
						line.append((char)i);

					 } // end of processing the character that's read


				  } // end of line loop


				/*
				// <debug>
				System.out.print(line);
				System.out.print('\n');
				System.out.flush();
				// </debug>
				*/


				} // end of file loop


				if (endOfFile) {
					
					// close the reader and flag the read is done
					fr.close();
					this.fileLineReadingDone = true;

				}
			

			}
			else {

				System.out.println("The file reader hasn't been setup!!!");
			}


		} catch (IOException io) {
		
			System.out.println("error reading file!!!");
		}	
	}

	/*
	// <debug>
	System.out.println(lLine);
	System.out.flush();
	// </debug>
	*/
	
	return lLine;
}  // the copy engine



	public static String readFullTextFile(String fileDetails) throws IOException
	{
		StringBuffer lFullText = new StringBuffer();
		
		Vector lLines = readTextFile(fileDetails);
		for (Iterator iter = lLines.iterator(); iter.hasNext();) {
			String lLine = (String) iter.next();
			lFullText.append(lLine);
			lFullText.append(CommonConstants.NEWLINE);
		}
		
		return lFullText.toString();
	}


  public static Vector readTextFile(String fileDetails) throws IOException
  {

	// open a file reader
	FileReader fr = null;
	try {
		fr  = new FileReader(fileDetails);
	} catch (IOException e) {
		System.err.println("Unable to open file: "+fileDetails);
		throw e;
	}


	// initialise the vector to hold the strings
	Vector fileLines = new Vector();
	int nextString = 0;


	// this loop reads the whole file
	StringBuffer line = null;
	boolean endOfFile = false;
	while (endOfFile == false) 
	{
	  /*
	  // <debug>
	  System.out.print(nextString);
	  System.out.print(' ');
	  // </debug>
	  */

	  // this loop reads in a line
	  line = new StringBuffer();
	  boolean endOfLine = false;
	  while (endOfLine == false) 
	  {

		// read some bytes till we reach the end of the line
		// or end of the file completely
		int i = fr.read();


		// process the character we've just read in
		if (i == -1) 
		{
			// reached the end of the file so the final line 
	    	// is in the buffer
	    	endOfFile = true;
			fileLines.addElement(line.toString()); // .trim()); // trim 15-03-05
			endOfLine = true;
		 }
		 
		else if (i == '\r') {
			
			// do nothing! Not sure about on unix
			
		}
		 
		// else if (i == '\r' || i == '\n') // '\r' 15-03-05
		 else if (i == '\n')
		 {

			// reached the end of the line so add the previously read bytes
	    	// as a line into the array of strings
		//	line.append((char)i);
		//	line.append("\r\n");
			fileLines.addElement(line.toString()); //.trim()); // trim 15-03-05
			endOfLine = true;
			nextString += 1;

		 }

		 else
		 {
			// add the byte that's been read into the string buffer
			line.append((char)i);

		 } // end of processing the character that's read


	  } // end of line loop


	/*
	// <debug>
	System.out.print(line);
	System.out.print('\n');
	System.out.flush();
	// </debug>
	*/


	} // end of file loop


	// close the reader
	fr.close();


	return fileLines; 

  }

  public static Vector loadCSVData(String pFileDetails, ICSVBuddy pCSVBuddy) 
  {
	// load the data from the csv file
	Vector lCSVFileLines = null;
	try {
		lCSVFileLines = readCSVFile(pFileDetails);
 	} catch (IOException e) 
 	{
 		//-> handle the exception!
 	}
	
	
	// transform the data into data objects
	Vector lCSVDataCollection = new Vector();
	for (Iterator iter = lCSVFileLines.iterator(); iter.hasNext();) {
		
		Vector lParsedLine = (Vector) iter.next();
		ICSVDataObject lDO = pCSVBuddy.buildDataObject(lParsedLine);
		lCSVDataCollection.add(lDO);
		
	}

	return lCSVDataCollection; 

  }
  
  
	public static String convertStreamToString(InputStream is) throws IOException {

		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	} 
  
  
  public static Vector readCSVFile(String fileDetails) throws IOException
  {
  	/*
  	 * 
  	 *  this method returns a vector of vectors - fields in lines
  	 * 
  	 */

	// open a file reader
	FileReader fr = null;
	fr  = new FileReader(fileDetails);


	// initialise the vector to hold the strings
	Vector fileLines = new Vector();
	StringBuffer line = new StringBuffer();
	boolean endOfFile = false;
	boolean lIsFreeText = false;
	
	// loop around every charcter in the file and assign it to a 'line'
	while (endOfFile == false) 
	{
	  /*
	  // <debug>
	  System.out.print(nextString);
	  System.out.print(' ');
	  // </debug>
	  */

		// read some bytes till we reach the end of the line
		// or end of the file completely
		int i = fr.read();
		StringBuffer lDebugView = new StringBuffer();
		lDebugView.append((char)(i));

		//
		// process the character we've just read in
		//
		
		// - end of file
		if (i == -1) 
		{
			// reached the end of the file so the final line 
	    	// is in the buffer - split it into fields
	    	String finalLine = line.toString();
			Vector fields = extractCSVFields(finalLine);
			fileLines.addElement(fields);

			// make sure we exit the loop!
			endOfFile = true;
		 }
		
		// - free text
		else if (i == '"') 
		{
		   // this character denotes a free-text field  
		   if (lIsFreeText) {
		   		// we've come to another double-quote, so it's either
			   // the end of the field or an embedded quote
			   // NOTE: an emmbeded quote in a field looks like: "xxxx""xxxx"
			   // so no need to check for carriage return between them
			   lIsFreeText = false;
		   } else {
		   		// mark that we've entered a free text field
				lIsFreeText = true;
		   }
		}
		
		
		// - end of line
		else if (i == '\n') 
		 {
			// reached a carriage return 
			// - this is either the end of the line or a return within a CSV field.
	    	if (lIsFreeText) {
	    		// THIS IS PROBABLY UNIX Unfriendly!!!!
				line.append(CommonConstants.NEWLINE); // due to /r/n combo
	    	} else {
		
				// true end of line so split into fields now.
		    	String theLine = line.toString();
				Vector fields = extractCSVFields(theLine);
				fileLines.addElement(fields);
				
				// reset the buffer
	    		line = new StringBuffer();
	    	}
		 }


		 // - carraige return
		 else if (i == '\r') 
		 {
			// THIS IS PROBABLY UNIX Unfriendly!!!!			 
		 	// do nothing!!! ;-)
		 }
		 
		 
		 // - everything else
		 else
		 {
			// add the byte that's been read into the line buffer
			line.append((char)i);
		 } // end of processing the character that's read


	/*
	// <debug>
	System.out.print(line);
	System.out.print('\n');
	System.out.flush();
	// </debug>
	*/


	} // end of file loop


	// close the reader
	fr.close();


	return fileLines; 

  } 

  
  public static Vector readCSVFile_OLD2(String fileDetails) throws IOException
  {
  	/*
  	 * 
  	 *  this method returns a vector of vectors - fields in lines
  	 * 
  	 */

	// open a file reader
	FileReader fr = null;
	fr  = new FileReader(fileDetails);


	// initialise the vector to hold the strings
	Vector fileLines = new Vector();
	int nextString = 0;


	// this loop reads the whole file
	StringBuffer line = new StringBuffer();
	
	
	boolean endOfFile = false;
	boolean lIsFreeText = false;
	
	
	boolean lFound2ndFTDelim = false;
	
	while (endOfFile == false) 
	{
	  /*
	  // <debug>
	  System.out.print(nextString);
	  System.out.print(' ');
	  // </debug>
	  */

	  // this loop reads in a line
	  // line = new StringBuffer();
	  
//	  boolean endOfLine = false;
//	  while (endOfLine == false) 
//	  {

		// read some bytes till we reach the end of the line
		// or end of the file completely
		int i = fr.read();
		StringBuffer lDebugView = new StringBuffer();
		lDebugView.append((char)(i));


		// process the character we've just read in
		
		// - end of file
		if (i == -1) 
		{
			// reached the end of the file so the final line 
	    	// is in the buffer
	    	
			fileLines.addElement(line.toString());
			
//			String lFieldText = lField.toString();
//			lFieldText.trim();
//			if (lFieldText.length() != 0) {
//				lFields.addElement(lFieldText);
//				fileLines.addElement(lFields);
//			}
			
			// endOfLine = true;
			endOfFile = true;
		 }
		
		// - free text
		else if (i == '"') 
		{

		   // this character denotes a free-text field
		   
		   if (lIsFreeText) {
		   	
		   		// we've come to another double-quote, mark it or see what's next
			   lIsFreeText = false;
		   } else {
		   	
		   		// mark that we've entered a free text field
				lIsFreeText = true;
		   }

		}
		
		// - comma 
//		else if (i == ',') {
//			
//			if (lIsFreeText) {
//				// copy the newline over into the field
//				lField.append((char)i);
//			} else {
//				String lFieldText = lField.toString();
//				lFieldText.trim();
//				lFields.addElement(lFieldText);
//				lField = new StringBuffer();
//			}		
//		
//		}
		
		
		// - end of line
		else if (i == '\n') 
		 {

			// reached the end of the line so add the previously read bytes
	    	// as a line into the array of strings
	    	if (lIsFreeText) {

				line.append(CommonConstants.NEWLINE); // due to /r/n combo
	    	} else {
//				String lFieldText = lField.toString();
//				lFieldText.trim();
//				lFields.addElement(lFieldText);
//				fileLines.addElement(lFields);
//				endOfLine = true;
//				nextString += 1;
	    		
	    		// endOfLine = true; 
	    		fileLines.addElement(line.toString());
	    		line = new StringBuffer();
	    	}
		 }


		 // - carraige return
		 else if (i == '\r') 
		 {
		 	// do nothing!!! ;-)
		 }
		 
		 
		 // - everything else
		 else
		 {
			// add the byte that's been read into the string buffer
			line.append((char)i);
		
		

		 } // end of processing the character that's read


//	  } // end of line loop


	/*
	// <debug>
	System.out.print(line);
	System.out.print('\n');
	System.out.flush();
	// </debug>
	*/


	} // end of file loop


	// close the reader
	fr.close();


	return fileLines; 

  }
  

  public static Vector readCSVFile_OLD1(String fileDetails) throws IOException
  {
  	/*
  	 * 
  	 *  this method returns a vector of vectors - fields in lines
  	 * 
  	 */

	// open a file reader
	FileReader fr = null;
	fr  = new FileReader(fileDetails);


	// initialise the vector to hold the strings
	Vector fileLines = new Vector();
	int nextString = 0;


	// this loop reads the whole file
	StringBuffer line = null;
	
	Vector lFields = null;
	StringBuffer lField = null;
	
	boolean endOfFile = false;
	boolean lIsFreeText = false;
	
	
	boolean lFound2ndFTDelim = false;
	
	while (endOfFile == false) 
	{
	  /*
	  // <debug>
	  System.out.print(nextString);
	  System.out.print(' ');
	  // </debug>
	  */

	  // this loop reads in a line
	  line = new StringBuffer();
	  
	  lField = new StringBuffer();
	  lFields = new Vector();
	  
	  boolean endOfLine = false;
	  while (endOfLine == false) 
	  {

		// read some bytes till we reach the end of the line
		// or end of the file completely
		int i = fr.read();
		StringBuffer lDebugView = new StringBuffer();
		lDebugView.append((char)(i));


		// process the character we've just read in
		
		// - end of file
		if (i == -1) 
		{
			// reached the end of the file so the final line 
	    	// is in the buffer
	    	
			String lFieldText = lField.toString();
			lFieldText.trim();
			if (lFieldText.length() != 0) {
				lFields.addElement(lFieldText);
				fileLines.addElement(lFields);
			}
			
			endOfLine = true;
			endOfFile = true;
		 }
		
		// - free text
		else if (i == '"') 
		{

		   // this character denotes a free-text field
		   
		   if (lIsFreeText) {
		   	
		   		// we've come to another double-quote, mark it or see what's next
		   		if (!lFound2ndFTDelim) {
		   			// mark that we've found the next delimiter
					lFound2ndFTDelim = true;
					lIsFreeText = false;
		   		}
		   } else {
		   	
		   		// mark that we've entered a free text field
				lIsFreeText = true;
				lFound2ndFTDelim = false;
		   }

		}
		
		// - comma 
		else if (i == ',') {
			
			if (lIsFreeText) {
				// copy the newline over into the field
				lField.append((char)i);
			} else {
				String lFieldText = lField.toString();
				lFieldText.trim();
				lFields.addElement(lFieldText);
				lField = new StringBuffer();
			}		
		
		}
		
		
		// - end of line
		else if (i == '\n') 
		 {

			// reached the end of the line so add the previously read bytes
	    	// as a line into the array of strings
	    	if (lIsFreeText) {
	    		// copy the newline over into the field
				//-> lField.append((char)i);			// I think this is better
				lField.append(CommonConstants.NEWLINE); // due to /r/n combo
	    	} else {
				String lFieldText = lField.toString();
				lFieldText.trim();
				lFields.addElement(lFieldText);
				fileLines.addElement(lFields);
				endOfLine = true;
				nextString += 1;
	    	}
		 }


		 // - carraige return
		 else if (i == '\r') 
		 {
		 	// do nothing!!! ;-)
		 }
		 
		 
		 // - everything else
		 else
		 {
			// add the byte that's been read into the string buffer
			lField.append((char)i);
		
		

		 } // end of processing the character that's read


	  } // end of line loop


	/*
	// <debug>
	System.out.print(line);
	System.out.print('\n');
	System.out.flush();
	// </debug>
	*/


	} // end of file loop


	// close the reader
	fr.close();


	return fileLines; 

  }

/**
 * Insert the method's description here.
 * Creation date: (19/10/2001 16:15:55)
 */
public static void writeTextFile(String fileDetails, String textToSave) throws IOException {
	
	// setup a file writer
	FileWriter fw = null;
	fw  = new FileWriter(fileDetails);


	// write the data
	fw.write(textToSave);
	fw.flush();
	fw.close();
	
}


/**
 * Insert the method's description here.
 * Creation date: (19/10/2001 16:24:52)
 * @param fileDetails java.lang.String
 * @param textToSave java.lang.String[]
 * @exception java.io.IOException The exception description.
 */
public static void writeTextFile(String fileDetails, Vector textToSave) throws java.io.IOException {
	
	// setup a file writer
	FileWriter fw = null;
	fw  = new FileWriter(fileDetails);


	// write the data
	for (int i=0;i<textToSave.size();i++) {
		
		fw.write((String)(textToSave.elementAt(i)));
		fw.write("\r\n");
		fw.flush();
	}
	
	fw.close();
}


public static void writeTextFile(String fileDetails, List textToSave) throws java.io.IOException {
	
	// setup a file writer
	FileWriter fw = null;
	fw  = new FileWriter(fileDetails);


	// write the data
	for (int i=0;i<textToSave.size();i++) {
		
		fw.write((String)(textToSave.get(i)));
		fw.write("\r\n");
		fw.flush();
	}
	
	fw.close();
}

public static void writeCSVFile(String fileDetails, List csvDataObjects, ICSVBuddy buddy) throws java.io.IOException{
	
	// setup a file writer
	FileWriter fw = null;
	fw  = new FileWriter(fileDetails);

	try {
		
		String header = buddy.getCSVHeader();
		if (header != null) {
			fw.write(header);
			fw.write(CommonConstants.NEWLINE);
			fw.flush();				
		}

		for (Iterator iter = csvDataObjects.iterator(); iter.hasNext();) {
			ICSVDataObject dataObject = (ICSVDataObject) iter.next();
			String csvLine = buddy.buildCSVLine(dataObject);
			
			fw.write(csvLine);
			fw.write(CommonConstants.NEWLINE);
			fw.flush();		
		}
		
	} finally  {

		fw.close();
	}

	
}



public static Document loadXMLDocumentFromFile(String pXMLFileDetails) {

	Document dom = null;
	DocumentBuilder documentBuilder = null;
	DocumentBuilderFactory theDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
	if(theDocumentBuilderFactory == null)
	{
		System.out.println("Failed to create instance of the Document Builder Factory");
	} else
	
		try{
				documentBuilder = theDocumentBuilderFactory.newDocumentBuilder();
			}
			catch(ParserConfigurationException ex)
			{
				System.out.println("Failed to create Document Builder");
			}
			
			if(documentBuilder == null)
				System.out.println("Failed to create Document Builder");
			else
				try
				{
					dom = documentBuilder.parse(new File(pXMLFileDetails));
				}
				catch(SAXException ex)
				{
					System.out.println("Sax Exception");
				}
				catch(IOException ex)
				{
					System.out.println("IO Exception");
				}
	
	if (dom == null) {
		System.out.println("Failed to create Document Object Model");
	}
	
	return dom;
}


	public static boolean breakdownFileName(String pInFileDetails,
											StringBuffer pOutFileNameBuf,
											StringBuffer pOutFileExtBuf){
		
		String lFilename = null;
		String lFileExtension = null;
		
		if (pInFileDetails != null){
			
			// move to the final '.'
			int lOffset = pInFileDetails.lastIndexOf(".");
			if (lOffset != CommonConstants.INDEX_OF_NOT_FOUND){
				lFilename = pInFileDetails.substring(0,lOffset);
				lFileExtension = pInFileDetails.substring(lOffset+1);
			}
		}
		
		pOutFileNameBuf.append(lFilename);
		pOutFileExtBuf.append(lFileExtension);
		
		return true;
	}

	
	public static String extractFileNameOnly(String inputFileName) {
		StringBuffer fileNameBuff = new StringBuffer();
		StringBuffer fileExtBuff = new StringBuffer();
		
		FileServer.breakdownFileName(inputFileName, fileNameBuff, fileExtBuff);
		
		return fileNameBuff.toString();
	}

	/**
	 * THis is an alternative approach - probably still not the best!
	 * 
	 * 
	 * @param pFileDetails
	 * @return
	 */
	public static String getFileNameFromFileDetails(String pFileDetails) {
		
		// first need to work out which slash we are working with
		StringTokenizer s = null;
		if (pFileDetails.indexOf("/") != -1) {
			s = new StringTokenizer(pFileDetails,"/",false);
		} else if (pFileDetails.indexOf("\\") != -1) {
			s = new StringTokenizer(pFileDetails,"\\",false);
		} else {
			return pFileDetails;
		}
		
		
		
		
		int lNoOfTokens = s.countTokens();
		
		String lToken = null;
		String lFileName = null;
		for (int i = 0; i < lNoOfTokens; i++) {

			lToken = s.nextToken();

			if (i==lNoOfTokens-1) lFileName = lToken;
				
		}
			
		return lFileName;
	}	
	
	
	
	public static String extractFileExtensionOnly(String inputFileName) {
		StringBuffer fileNameBuff = new StringBuffer();
		StringBuffer fileExtBuff = new StringBuffer();
		
		FileServer.breakdownFileName(inputFileName, fileNameBuff, fileExtBuff);
		
		return fileExtBuff.toString();
	}
	
	
	
   public static boolean isFileValid(File f) {
	   
	 boolean isValid = true;
	 try {
	      // does the file exist
		  boolean exists = f.exists();
		  if (!exists) {
			  isValid = false;
		  
		  } else if (!f.canRead()) { // is the file readable
			  isValid = false;
			  
		  } if (!f.isFile()) { // is the file actually a file (not a dir)
			  isValid = false;
		     
		  }
	 
		} catch (Exception e) {
			e.printStackTrace();
			isValid = false; // somethign else went wrong!
		}
	   
	  return isValid;
   }


   public static boolean isFileValid(String filename) {
	   try {
		   File f = new File(filename);
		   return isFileValid(f);		
	} catch (Exception e) {
		e.printStackTrace();
		return false;
	}

   }
   
   /**
	* This method reads a properties file which is passed as
	* the parameter to it and load it into a java Properties 
	* object and returns it.
	* @param <b>fileDetails</b> Represents the .properties file
	* @return <b>Properties</b>The properties object
	* @throws <b>IOException</b> The exception this method can throw
	*/
  public static Properties loadProperties( String fileDetails ) throws IOException {
   
	 // Loads a ResourceBundle and creates Properties from it
	 Properties prop = new Properties();
	 ResourceBundle bundle = ResourceBundle.getBundle( fileDetails );
  
	 Enumeration<String> keys = bundle.getKeys();
	 String key = null;
 
	 while( keys.hasMoreElements() ) {
	   key = (String)keys.nextElement();
	   prop.put( key, bundle.getObject( key ) );
	 }
	 
	 return prop;
  }
  
  
  /**
	* This method reads a properties file, the details of which are in a System
	* Property whose key is passed in, and loads it into a java Properties 
	* object and returns it.
	* @param <b>sysPropKey</b> The key to get the property file details
	* @return <b>Properties</b>The properties object
	* @throws <b>IOException</b> The exception this method can throw
	*/
	public static Properties loadPropertiesFromSystemProperty( String sysPropKey ) throws IOException {
		
		String fileDetails = System.getProperty(sysPropKey);
		Properties prop = null;
		if (fileDetails != null) {
			prop = loadProperties(fileDetails);
		}
		 
		return prop;
	}
   
	
	public class Neo9FileDetails{
		
		public String filename;
		public String fileExtension;
		
		public Neo9FileDetails(){}
		

		public Neo9FileDetails(String pFilename,String pFileExtension){
			fileExtension = pFileExtension;
			filename = pFilename;
		}
		
	}
	
	
}
