/**
 * 
 */
package uk.co.neo9.sandbox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import uk.co.neo9.test.Neo9TestingConstants;
import uk.co.neo9.utilities.file.FileServer;

/**
 * @author Simon
 *
 */
public class ArchiveComparisonApplication {

	private final static String WORKING_FOLDER = Neo9TestingConstants.ROOT_TEST_FOLDER+ "mp3man/mp3ListCompare/"; 
	
	public final static int MODE_MUSIC = 1;
	public final static int MODE_MOVIE = 2;
	public final static int MODE_PHOTO = 3;
	
	DIRListLineCollator store = new DIRListLineCollator();
	
	public int mode = -1;
	
	public static void main(String[] args) {

		ArchiveComparisonApplication a = new ArchiveComparisonApplication();

		a.go_movies();

	}
	
	
	public void go_music() {
		
		// default the mode!
		if (mode == -1){
			mode = MODE_MUSIC;
		}
		
		// read in the lines from the text files
		String fileDetails1 = null;
		String fileDetails2 = null;
//		String folder = Neo9TestingConstants.ROOT_TEST_FOLDER+ "fileserver/";
//		fileDetails = folder + "jukebox_dir - 2007-09-13.txt";

		String folder = WORKING_FOLDER;
		//String filename = "scooby my music.txt";
		// String filename1 = "From Henrick.txt";
		
//		String filename1 = "TestHenrick.txt";
//		String filename2 = "TestHenrick.txt";
		
//		String filename1 = "scooby my music.txt";
//		String filename2 = "jmcg HDD Simonz.txt";		
		
		String filename1 = "IdenticalMP3sList-copy1.txt";
		String filename2 = "IdenticalMP3sList-copy2.txt";		
		
		
		fileDetails1 = folder + filename1;
		fileDetails2 = folder + filename2;
		
		List DIRLineList1 = processDIRListFile(fileDetails1);
		List DIRLineList2 = processDIRListFile(fileDetails2);
		
		// sort them by MP3 order
		
		
		
		
		// compare the two lists
		
		
		compareLists(DIRLineList1,DIRLineList2);
		
		
		// output the results
//		outputMatchedLines(DIRLineList1);
//		outputMatchedLines(DIRLineList2);
		
		outputToCSV();
	}


	
	public void go_movies() {
		
		// default the mode!
		if (mode == -1){
			mode = MODE_MOVIE;
		}
		
		// read in the lines from the text files

//		String folder = WORKING_FOLDER;
//	
//		String filename1 = "vaio zz-vault myphotos photoslist.txt";
//		String filename2 = "MyBook xHDD unprocessedPhotosList.txt";		
	
		
		String folder = "D:/ZZ - Swap Zone/_ DropBoxes/jmcgDropBox/My Dropbox/My Private/Sandbox/xHDDs/";
		
		String filename1 = "mybook.drive.movie.filelist - 2010-05-06.txt";
		String filename2 = "scooby.drive.movie.filelist - 2010-05-06.txt";			
		
		String fileDetails1 = null;
		String fileDetails2 = null;
		fileDetails1 = folder + filename1;
		fileDetails2 = folder + filename2;
		
		List DIRLineList1 = processDIRListFile(fileDetails1);
		List DIRLineList2 = processDIRListFile(fileDetails2);
		

	
		// compare the two lists
		compareLists(DIRLineList1,DIRLineList2);
		
		
		// output the results
//		outputMatchedLines(DIRLineList1);
//		outputMatchedLines(DIRLineList2);
		
		outputToCSV();
	}	
	
	public void go_photos() {
		
		// default the mode!
		if (mode == -1){
			mode = MODE_PHOTO;
		}
		
		// read in the lines from the text files

		String folder = WORKING_FOLDER;
	
		String filename1 = "vaio zz-vault myphotos photoslist.txt";
		String filename2 = "MyBook xHDD unprocessedPhotosList.txt";		
//	

		String fileDetails1 = null;
		String fileDetails2 = null;
		fileDetails1 = folder + filename1;
		fileDetails2 = folder + filename2;
		
		List DIRLineList1 = processDIRListFile(fileDetails1);
		List DIRLineList2 = processDIRListFile(fileDetails2);
		

	
		// compare the two lists
		compareLists(DIRLineList1,DIRLineList2);
		
		
		// output the results
//		outputMatchedLines(DIRLineList1);
//		outputMatchedLines(DIRLineList2);
		
		outputToCSV();
	}	
	
	
	/**
	 * Algorithm:
	 * 
	 * Work through two lists from the top
	 * compare each item
	 * if its a match, record and move on
	 * if not, move up one place in the list that is LESS
	 * keep going until end of one list is reached
	 * 
	 * @param lineList1
	 * @param lineList2
	 */
	void compareLists(List lineList1, List lineList2) {

		
		
		int list1Size = lineList1.size();
		int list2Size = lineList2.size();
		
//		for (Iterator iterator = lineList2.iterator(); iterator.hasNext();) {
//			DIRListLine list1Line = (DIRListLine) iterator.next();
//			
//		}
		
		
//		boolean moreLinesLeft = true;
		boolean moreFromList1 = true;
		boolean moreFromList2 = true;
		int list1Index = 0;
		int list2Index = 0;
		
		
		do {
			// get the next two lines to compare
			DIRListLine list1Line, list2Line = null; 
			int comparisonResult = 0;
			
			
			moreFromList1 = (list1Index < list1Size);
			if (moreFromList1) {
				list1Line = (DIRListLine)lineList1.get(list1Index);
				// list1Index++; <-- NO!!!
			} else {
				list1Line = null;
				comparisonResult = -1;
			}
			
			moreFromList2 = (list2Index < list2Size);
			if (moreFromList2) {
				list2Line = (DIRListLine)lineList2.get(list2Index);
				// list2Index++; <-- NO!!!
			} else {
				list2Line = null;
				comparisonResult = 1;
			}
			
			// cheat!
			if ((list1Line == null)&&(list2Line == null)){
				continue;
			}

			if (comparisonResult == 0) {
				comparisonResult = DIRListLineHelper.compareDIRListLines(list1Line, list2Line);
			}
			
			// process the result
			if (comparisonResult == 0) {
				
				this.store.storeMatches(list1Line, list2Line);
				list1Index++;
				list2Index++;

			} else {
				if (comparisonResult < 0) { // line in list 1 is less than line in list 2 (or reached end of list 1!)
					
					if (moreFromList1) {
						// store this unmatched line from list1 and move index up to next one
						this.store.storeUnmatched(list1Line);
						list1Index++;
					} else {
						// reached end of list1 so need to simply put rest of list two in as unmatched
						this.store.storeUnmatched(list2Line);
						list2Index++;
					}
					

				} else { // line in list 2 is less than list one or reached end of list 2
					
					if (moreFromList2) {
						this.store.storeUnmatched(list2Line);
						list2Index++;
					} else {
						this.store.storeUnmatched(list1Line);
						list1Index++;
					}
					

				}
			}
			
	

		} while (moreFromList1 || moreFromList2);

		
		
	}

	
	
	/*
	 * Algorithm:
	 * 
	 * - process lists to build a single matched pair list
	 * - put the matches together, signles in their own instances of the container
	 * - once collated like that, can then think about output formats... e.g. CSV
	 * 
	 */
	private void outputToCSV() {

		List matches = this.store.getMatches();
		List mismatches = this.store.getMismatches();
		DIRListLineMatchCSVBuddy csvBuddy = new DIRListLineMatchCSVBuddy();

		String folder = WORKING_FOLDER;
		String filename = "matchedResults.csv";
		try {
			FileServer.writeCSVFile(folder + filename, matches, csvBuddy);
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		filename = "unmatchedResults.csv";
		try {
			FileServer.writeCSVFile(folder + filename, mismatches, csvBuddy);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		System.out.println(csvBuddy.getCSVHeader());

		for (Iterator iterator = matches.iterator(); iterator.hasNext();) {
			DIRListLineMatch match = (DIRListLineMatch) iterator.next();
			String csvLine = csvBuddy.buildCSVLine(match);
			System.out.println(csvLine);
		}
		
		for (Iterator iterator = mismatches.iterator(); iterator.hasNext();) {
			DIRListLineMatch mismatch = (DIRListLineMatch) iterator.next();
			String csvLine = csvBuddy.buildCSVLine(mismatch);
			System.out.println(csvLine);
			
		}
	
//TODO write CSV file		
//		try {
//			FileServer.writeCSVFile(folder+outputCSVFilename,catalogue.getEntries(),new VideoEntryCSVBuddy());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
	}	
	
	
	

	private List processDIRListFile(String fileDetails){
		
		List processedLines = null;
		try {
			List lines = FileServer.readTextFile(fileDetails);
			processedLines = processLines(lines);
			
			//TODO sysout --> outputLines(processedLines);
			
			System.out.println(" & & & & & & & & & & & & & & & & ");
			System.out.println(" & & & & & & & & & & & & & & & & ");
			System.out.println(" & & & & & & & & & & & & & & & & ");
			System.out.println(" & & & & & & & & & & & & & & & & ");
			System.out.println(" & & & & & & & & & & & & & & & & ");
			
			Collections.sort(processedLines, new DIRListLineComparator());
			//TODO sysout --> outputLines(processedLines);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return processedLines;
	}
	
	private List processLines(List lines) {
		List processedLines = new ArrayList(lines.size());
		for (Iterator iterator = lines.iterator(); iterator.hasNext();) {
			String line = (String) iterator.next();
			DIRListLine processedLine = new DIRListLine(line);
			if (checkFileIsCorrectType(processedLine)) {
				processedLines.add(processedLine);
			}
		}
		return processedLines;
	}
	
	
	private boolean checkFileIsCorrectType(DIRListLine fileLine){
		boolean correct = false;
		if (mode == MODE_MUSIC) {
			correct = fileLine.isMusicFile();
		} else if (mode == MODE_MOVIE) {
			correct = fileLine.isMovieFile();
		} else if (mode == MODE_PHOTO) {
			correct = fileLine.isPictureFile();
		}
		return correct;
	}
	
	
	private void outputLines(List lines) {
		for (Iterator iterator = lines.iterator(); iterator.hasNext();) {
			// String line = (String) iterator.next();
			// System.out.println(line);
			DIRListLine line = (DIRListLine)iterator.next();
			System.out.print(line.getTextToMatch() + " >> ");
			System.out.print(line.getFileName() + " || ");
			System.out.println(line.getLineText());
		}
	}

	private void outputMatchedLines(List lines) {

		
		System.out.println(" ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ");
		System.out.println(" ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ");
		System.out.println(" ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ");
		System.out.println(" ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ");
		System.out.println(" ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ");
		
		
		for (Iterator iterator = lines.iterator(); iterator.hasNext();) {
			// String line = (String) iterator.next();
			// System.out.println(line);
			DIRListLine line = (DIRListLine)iterator.next();
			if (line.isMatched()) {
				System.out.print("MATCHED: "+line.getTextToMatch() + " >> ");
				System.out.println(line.getMatchToken());						
			} else {
				System.out.print(line.getTextToMatch() + " >> ");
				System.out.println(line.getMatchToken());		
			}

		}
	}
}
