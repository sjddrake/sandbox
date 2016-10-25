/*
 * Created on 20-Apr-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.apps.videocat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.xml.UtilitiesXMLTags;

/**
 * @author ct039314
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class VideoCatalogue {

	private ArrayList entries = new ArrayList();
	
	
	
	
	/**
	 * Insert the method's description here.
	 * Creation date: (24/10/2001 11:34:39)
	 * @return java.lang.String
	 */
	public String exportAsXML() {

		StringBuffer lXMLText = new StringBuffer();

	
		// add the tag that denotes that start of the catalogue
		lXMLText.append(UtilitiesXMLTags.buildTag(VideoCatXMLTags.VIDEO_CATALOGUE, true));
		lXMLText.append(CommonConstants.NEWLINE);
	

		// add the seperate entries

		for (int i=0; i<this.entries.size(); i++) {
			lXMLText.append(((VideoEntry)(this.entries.get(i))).exportAsXML());
		}	
	

		
		// end the catalogue
		lXMLText.append(UtilitiesXMLTags.buildTag(VideoCatXMLTags.VIDEO_CATALOGUE, false));	
		lXMLText.append(CommonConstants.NEWLINE);
	
		return lXMLText.toString();
	}

	
	
	public List getEntries() {
		return entries;
	}


	public void add(VideoEntry entry){
		this.entries.add(entry);
	}
	
	public void addAll(Collection list){
		this.entries.addAll(list);
	}
}
