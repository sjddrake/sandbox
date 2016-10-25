/**
 * 
 */
package uk.co.neo9.sandbox;

import uk.co.neo9.utilities.file.RelationalFileHelper;

/**
 * @author Simon
 *
 */
public class DIRListLine {
	
	public final static String FIELD_NAME_TEXT_TO_MATCH = "textToMatch";
	public final static String FIELD_NAME_LINE_TEXT = "lineText";
	public static final String FIELD_NAME_FILE_NAME = "fileName";
	
	public final static int FIELD_ID_TEXT_TO_MATCH = 10;
	public final static int FIELD_ID_LINE_TEXT = 11;
	public static final int FIELD_ID_FILE_NAME = 12;
	
	private static int UNDEFINED = -55245;
	
	private String lineText = null;
	private String textToMatch = null;
	private int fileNameOffset = UNDEFINED;
	private int matchToken = UNDEFINED;
	
	
	private boolean isFile = false;
	private boolean isMusicFile = false;
	private boolean isMovieFile = false;
	private boolean isPictureFile = false;
	private boolean isUnknownFile = false;
	
	private DIRListLine() {};
	
	public DIRListLine(String textLine) {
		super();
		this.lineText = textLine;
		
		// now process the text line
		this.processRawText();
	}
	
	private void reset() {
		isFile = false;
		isMusicFile = false;
		isMovieFile = false;
		isPictureFile = false;
		isUnknownFile = false;
	}
	
	
	private void processRawText() {
		
		// reset so we don't get side-effects
		reset();
		
		// first check the text line is for a file
		isFile = RelationalFileHelper.testDIRLineForFileName(this.lineText);
		
		if (isFile){
		
			// work out where the file name beings in the text
			this.fileNameOffset = this.lineText.lastIndexOf('\\') + 1;
			
			// record what type of file this is
			String fileExtension = lineText.substring(lineText.length()-4);
			if (isMusicFileExtension(fileExtension)){
				isMusicFile = true;
			} else if (isMovieFileExtension(fileExtension)){
				isMovieFile = true;
			} else if (isPictureFileExtension(fileExtension)){
				isPictureFile = true;
			} else {
				isUnknownFile = true;
			}

			// initialise textToMatch
			
			// TODO should allow the outside application to determine what the test to match is
			if (isMusicFile) {
				textToMatch = generaliseTextToMatch(getFileName());
			} else {
				textToMatch = getFileName();
			}
			
		}
		

		
	}
	
	
	protected boolean isMusicFileExtension(String ext) {
		
		// quick safety check
		if (ext == null || ext.trim().length() == 0) {
			return false;
		}
		
		// now test for the registered extensions
		boolean confirmed = false;
		if (ext.equalsIgnoreCase(".MP3")){
			confirmed = true;
		} else if (ext.equalsIgnoreCase(".WMA")){
			confirmed = true;
		}else if (ext.equalsIgnoreCase(".m4a")){
			confirmed = true;
		}
		return confirmed;
	}
	
	
	protected boolean isPictureFileExtension(String ext) {
		
		// quick safety check
		if (ext == null || ext.trim().length() == 0) {
			return false;
		}
		
		// now test for the registered extensions
		boolean confirmed = false;
		if (ext.equalsIgnoreCase(".BMP")){
			confirmed = true;
		} else if (ext.equalsIgnoreCase(".JPG")){
			confirmed = true;
		}
		return confirmed;
	}	
	

	protected boolean isMovieFileExtension(String ext) {
		
		// quick safety check
		if (ext == null || ext.trim().length() == 0) {
			return false;
		}
		
		// now test for the registered extensions
		boolean confirmed = false;
		if (ext.equalsIgnoreCase(".MOV")){
			confirmed = true;
		} else if (ext.equalsIgnoreCase(".AVI")){
			confirmed = true;
		} else if (ext.equalsIgnoreCase(".WMV")){
			confirmed = true;
		} else if (ext.equalsIgnoreCase(".MPG")){
			confirmed = true;
		} else if (ext.equalsIgnoreCase(".3GP")){
			confirmed = true;
		}
		return confirmed;
	}
	
	
	protected String generaliseTextToMatch(String text) {

		
		// swap spaces & control characters for the chosen delimiter

		StringBuffer generalisedText = new StringBuffer();
		boolean notFinished = true;
		int offset = 0;
		while (notFinished && offset < text.length()) {
			char c = text.charAt(offset);
			if (Character.isLetter(c)){ //OrDigit(c)){ 		// TODO might want to leave a delimiter for ease of reading output
				generalisedText.append(c);
			} else {
				// notFinished = false; <-------- this bit hung over from a previous algorithm
			}
			offset++;
		}	
		
		return generalisedText.toString();		
		
	}

	public String getTextToMatch() {
		return textToMatch;
	}
	
	public String getLineText() {
		return lineText;
	}

	public void setLineText(String lineText) {
		this.lineText = lineText;
		this.fileNameOffset = UNDEFINED;
	}
	
	public String getFileName(){
		String retVal = null;
		if (this.isFile){
			retVal = this.lineText.substring(this.fileNameOffset, this.lineText.length()-4);
		}
		return retVal;
	}

	public boolean isFile() {
		return isFile;
	}

	public void setMatchToken(int i) {
		matchToken = i;
		
	}

	public int getMatchToken() {
		return matchToken;
		
	}	
	
	public boolean isMatched() {
		boolean matched = (this.matchToken == UNDEFINED) ? false : true;
		return matched;
	}

	public boolean isMatchedTo(DIRListLine matchedLine) {
		
		boolean matched = false;
		
		if (this.isMatched() && matchedLine.isMatched()) {
			if (matchedLine.matchToken == this.matchToken) {
				matched = true;
			}
		}
		
		return matched;
	}

	public boolean isMusicFile() {
			return this.isMusicFile;
	}

	public boolean isMovieFile() {
		return isMovieFile;
	}



	public boolean isPictureFile() {
		return isPictureFile;
	}



	public boolean isUnknownFile() {
		return isUnknownFile;
	}


	
}
