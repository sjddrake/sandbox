package uk.co.neo9.apps.videocat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


import uk.co.neo9.test.Neo9TestingConstants;
import uk.co.neo9.utilities.file.FileServer;


/**
 *  What this is:
 *  
 *  - starts of life as a file converter to read in my various lists of DVDs/Videos
 *    and format them for one structured master
 * 
 *  - will use master as XML but may also provide an Excel formatter to use Exel as
 *    a GUI 
 * 
 * 	- then we can think about creating our own GUI and maybe providing it with filters
 *    
 * 
 */

public class VideoLibraryHelper {
	

	final private static String folder = Neo9TestingConstants.ROOT_TEST_FOLDER+"videocat/";
	final private static String inputFilename_FULL_VIDEO_TV_RECORDINGS = "Full Blank Videos List.txt";
	final private static String inputFilename_MY_VIDEO_TV_RECORDINGS = "current video recordings.txt";
	final private static String inputFilename_VIDEO_TV_FILMS = "Video Library (Recorded).txt";
	final private static String inputFilename_VIDEO_PUBLISHED_FILMS = "Video Library (Commercial).txt";
	final private static String inputFilename_DVD_PUBLISHED = "DVD Library.txt";
	final private static String inputFilename_DVD_TV_RECORDINGS = "DVD TV.txt";
	final private static String inputFilename_MY_DVD_RECORDINGS = "DVD VR.txt";
	final private static String inputFilename_DIVX = "DIVx.txt";
	final private static String outputCSVFilename = "video.csv";
	final private static String outputXMLFilename = "video.xml";	
	
	
//	VideoEntry loadedMeta = null;

	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VideoLibraryHelper vlh = new VideoLibraryHelper();
		vlh.go();
	}


	public void go(){
		
		VideoCatalogue catalogue = new VideoCatalogue();
		
		// select in the text file
		final String filename = "test.txt";
//		loadEntriesFromFile(filename,catalogue);
		
// These are all the recordings I'm keeping - my catalogue		
		

//		loadEntriesFromFile(inputFilename_VIDEO_TV_FILMS,catalogue);
//		loadEntriesFromFile(inputFilename_VIDEO_PUBLISHED_FILMS,catalogue);
//		loadEntriesFromFile(inputFilename_DVD_PUBLISHED,catalogue);
//		loadEntriesFromFile(inputFilename_DVD_TV_RECORDINGS,catalogue);
//		loadEntriesFromFile(inputFilename_DIVX,catalogue);
//		loadEntriesFromFile(inputFilename_,catalogue);

// And these are my current recordings

		loadEntriesFromFile(inputFilename_MY_VIDEO_TV_RECORDINGS,catalogue);
//		loadEntriesFromFile(inputFilename_FULL_VIDEO_TV_RECORDINGS,catalogue);
		loadEntriesFromFile(inputFilename_MY_DVD_RECORDINGS,catalogue);
		
		System.out.println(catalogue.exportAsXML());
			
			
		try {
			FileServer.writeCSVFile(folder+outputCSVFilename,catalogue.getEntries(),new VideoEntryCSVBuddy());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}



	private void loadEntriesFromFile(String filename, VideoCatalogue catalogue){

		List fileLines = null;
		List baggage = null;

		
		try {
			fileLines = FileServer.readTextFile(folder+filename);
			outputLines(fileLines);
			
			baggage = cleanLines(fileLines, filename);
			
			
			// now try building some objects from the data
			VideoEntry entry = null;
			for (Iterator iter = baggage.iterator(); iter.hasNext();) {
				
				VideoLibraryHelperBag bag = (VideoLibraryHelperBag) iter.next();
				List vidsFromBag = getVidsFromBag(bag);
				catalogue.addAll(vidsFromBag);
			}

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return;
	}	
	
	
	
	private List getVidsFromBag(VideoLibraryHelperBag bag) {

		ArrayList catalogue = new ArrayList();
		
		// first build the default values from the meta line
		VideoEntry defaultValues = null;
		if (bag.metaLine != null && bag.metaLine.trim().length() > 0) {
			defaultValues = buildEntryFromCSVLine(bag.metaLine);
		} else {
			defaultValues = new VideoEntry();
		}
		
		
		// now try building some objects from the data
		VideoEntry entry = null;
		VideoEntry data = null;
		for (Iterator iter = bag.names.iterator(); iter.hasNext();) {
			String line = (String) iter.next();

			entry = defaultValues.makeCopy();
			data = loadEntryLine(line);
			
			if (data.getTitle() != null){
				String title = data.getTitle().trim();
				if (title.length() > 0) {
					entry.setTitle(title);
				}
			}
			
			if (data.getComments() != null){
				String comments = data.getComments().trim();
				if (comments.length() > 0) {
					entry.setComments(comments);
				}
			}
			
			catalogue.add(entry);
		}
		
		return catalogue;
	}


	private VideoEntry buildEntryFromCSVLine(String cleanLine){
		
		Vector fields = FileServer.extractCSVFields(cleanLine);
		VideoEntryCSVBuddy buddy = new VideoEntryCSVBuddy();
		VideoEntry entry = (VideoEntry)buddy.buildDataObject(fields);
		
		return entry;
		
	}

	private void outputLines(List fileLines) {
		for (Iterator iter = fileLines.iterator(); iter.hasNext();) {
			String line = (String) iter.next();
			System.out.println(line);	
		}
	}


	
	
	private List cleanLines(List fileLines, String filename){
		
		final String META_TAG = "META:";
		ArrayList baggage = new ArrayList();
		
//		// the first line may contain meta information so check that first
//		String metaLine = (String)fileLines.get(0);
//		boolean firstLineIsMeta = false;
//		if (metaLine.startsWith(META_TAG)){
//			metaLine = metaLine.substring(META_TAG.length());
//			firstLineIsMeta = true;
//		}
//		
//		int beginAt = 0;
//		if (firstLineIsMeta) {
//			beginAt = 1;
//			VideoEntry meta = loadMetaLine(metaLine);
//			loadedMeta = meta;
//		}

		// ignore all header lines - they are there for human consumption!
		int beginAt = 0;
		for (int i = 0; i < fileLines.size(); i++) {
			String line = (String)fileLines.get(i);
			if (line.startsWith(META_TAG)){
				beginAt = i;
				break;
			}
		}
		
		VideoLibraryHelperBag bag = null;
		for (int i = beginAt; i < fileLines.size(); i++) {

			String line = (String)fileLines.get(i);
			
			if (line.startsWith(META_TAG)){
				// got to the start of a new group of lines
				bag = new VideoLibraryHelperBag();
				baggage.add(bag);
				bag.metaLine = line.substring(META_TAG.length());
				
			} else {
				if (line.trim().length() > 0){
					line = cleanLine(line, filename);
					bag.names.add(line);				
				}
			}

		}
		
		return baggage;
	}



//	private VideoEntry loadMetaLine(String metaLine) {
//		
////		VideoEntry meta = new VideoEntry();
////		
////		List fields = FileServer.extractCommaDelimitedFieldsV2(metaLine);
////		for (int i = 0; i < fields.size(); i++) {
////			
////			String field =(String)fields.get(i);
////			
////			switch (i) {
////				case 0 : meta.setCategory(field); break;
////				case 1 : meta.setFormat(field); break;
////				case 2 : meta.setSource(field); break;
////				case 3 : meta.setRevisit(Boolean.valueOf(field).booleanValue()); break;
////				default : break;
////			}
////			
////		}
//		
//		Vector fields = FileServer.extractCommaDelimitedFieldsV2(metaLine);
//		VideoEntryCSVBuddy buddy = new VideoEntryCSVBuddy();
//		VideoEntry meta = (VideoEntry)buddy.buildDataObject(fields);
//
//		
//		return meta;
//	}


	private VideoEntry loadEntryLine(String metaLine) {
		
		VideoEntry entry = new VideoEntry();
		
		List fields = FileServer.extractCSVFields(metaLine);
		for (int i = 0; i < fields.size(); i++) {
			
			String field =(String)fields.get(i);
			
			switch (i) {
				case 0 : entry.setTitle(field); break;
				case 1 : entry.setComments(field); break;
				default : break;
			}
			
		}
		

		
		return entry;
	}


	private String cleanLine(String line, String filename) {
		String cleanedLine = null;
		if (inputFilename_FULL_VIDEO_TV_RECORDINGS.equals(filename) ||
			inputFilename_MY_DVD_RECORDINGS.equals(filename)){
			cleanedLine = cleanLineX1(line);
		} else {
			cleanedLine = cleanLineX2(line);
		}
		
		return cleanedLine;
	}	
	
	/**
	 * FORMAT:
	 * 
	 * 		P - You Can Count on Me¥ - Matthew Broderick - 4K (21/12/04) Watched?
	 * 
	 * DOES
	 * 
	 * 		P,You Can Count on Me,- Matthew Broderick - 4K (21/12/04) Watched?
	 * 
	 */
	private String cleanLineX1(String line) {
		
		final Character HYPHEN = new Character('-');
		final Character YEN = new Character(',');
		StringBuffer cleanedLine = new StringBuffer();
		
		
		int offset = 0;
		for (int i = 0; i < line.length(); i++) {
			char charToTest = line.charAt(i);
			if (HYPHEN.charValue() == charToTest) {
				offset = i;
				break;
			}
		}
		
		boolean theresMore = false;
		String id = line.substring(0,offset);
		String rest = null;
		if (offset == line.length()-1) {
			rest = "";
		} else {
			rest = line.substring(offset+1);
			theresMore = true;
		}
		
		
		
		if (theresMore) {
			offset = 0;
			for (int i = 0; i < rest.length(); i++) {
				char charToTest = rest.charAt(i);
				if (YEN.charValue() == charToTest) {
					offset = i;
					break;
				}
			}
			
	
			String name = rest.substring(0,offset);
			String description = null;
			if (offset == rest.length()-1) {
				description = "";
			} else {
				description = rest.substring(offset+1);
			}		
			
			cleanedLine.append(name.trim());
			cleanedLine.append(",");
			cleanedLine.append(id.trim());
			cleanedLine.append(" - ");
			cleanedLine.append(description.trim());
		
		} else {
			
			cleanedLine.append(id.trim());
			
		}
		
		
				
		return cleanedLine.toString();
	}

	private String cleanLineX2(String line) {
		
		int offset = 0;
		for (int i = 0; i < line.length(); i++) {
			char charToTest = line.charAt(i);
			if (Character.isLetterOrDigit(charToTest)) {
				offset = i;
				break;
			}
		}
		
		String cleanedLine = line.substring(offset);		
		return cleanedLine;
	}
}
