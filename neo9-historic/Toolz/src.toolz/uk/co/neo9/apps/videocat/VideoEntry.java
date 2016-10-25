package uk.co.neo9.apps.videocat;

import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.file.ICSVDataObject;
import uk.co.neo9.utilities.xml.UtilitiesXMLTags;

public class VideoEntry implements VideoEntryConstants, ICSVDataObject {

	private String title = null;
	private String source = null;
	private String format = null;
	private String category = null;
	private String genre = null;
	private String comments = null;
	private boolean revisit = false;
	private boolean original = false;
	
	// =========== Helper Methods ===============
	
	public VideoEntry makeCopy(){
		VideoEntry copy = new VideoEntry();
		
		copy.setCategory(this.getCategory());
		copy.setComments(this.getComments());
		copy.setFormat(this.getFormat());
		copy.setGenre(this.getGenre());
		copy.setOriginal(this.getOriginal());
		copy.setRevisit(this.getRevisit());
		copy.setSource(this.getSource());
		copy.setTitle(this.getTitle());
		
		return copy;
	}
	

	/**
	 * Insert the method's description here.
	 * Creation date: (24/10/2001 11:34:39)
	 * @return java.lang.String
	 */
	public String exportAsXML() {

		StringBuffer lXMLText = new StringBuffer();

	
		// start the entry
		lXMLText.append(UtilitiesXMLTags.buildTag(VideoCatXMLTags.VIDEO_ENTRY, true));
		lXMLText.append(CommonConstants.NEWLINE);
	
	
		// add the fields
	
		lXMLText.append(UtilitiesXMLTags.buildElement(VideoCatXMLTags.VIDEO_CATEGORY, this.category));
		lXMLText.append(CommonConstants.NEWLINE);

		lXMLText.append(UtilitiesXMLTags.buildElement(VideoCatXMLTags.VIDEO_FORMAT, this.getFormat()));
		lXMLText.append(CommonConstants.NEWLINE);

		lXMLText.append(UtilitiesXMLTags.buildElement(VideoCatXMLTags.VIDEO_GENRE, this.getGenre()));
		lXMLText.append(CommonConstants.NEWLINE);

		lXMLText.append(UtilitiesXMLTags.buildElement(VideoCatXMLTags.VIDEO_REVISIT, this.getRevisit()));
		lXMLText.append(CommonConstants.NEWLINE);
	
		lXMLText.append(UtilitiesXMLTags.buildElement(VideoCatXMLTags.VIDEO_SOURCE, this.getSource()));
		lXMLText.append(CommonConstants.NEWLINE);

		lXMLText.append(UtilitiesXMLTags.buildElement(VideoCatXMLTags.VIDEO_TITLE, this.getTitle()));
		lXMLText.append(CommonConstants.NEWLINE);	
	
		lXMLText.append(UtilitiesXMLTags.buildElement(VideoCatXMLTags.VIDEO_COMMENTS, this.getComments()));
		lXMLText.append(CommonConstants.NEWLINE);			

		// close the entry
		lXMLText.append(UtilitiesXMLTags.buildTag(VideoCatXMLTags.VIDEO_ENTRY, false));	
		lXMLText.append(CommonConstants.NEWLINE);
	
		return lXMLText.toString();
	}


	
	// =========== Getters & Setters ===============
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public boolean getRevisit() {
		return revisit;
	}
	public void setRevisit(boolean revisit) {
		this.revisit = revisit;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param string
	 */
	public void setTitle(String string) {
		title = string;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return
	 */
	public boolean getOriginal() {
		return original;
	}

	/**
	 * @param b
	 */
	public void setOriginal(boolean b) {
		original = b;
	}

}
