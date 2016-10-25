package uk.co.neo9.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.neo9.utilities.file.ICSVDataObject;

public class DIRListLineMatch implements ICSVDataObject {

	private DIRListLine line1 = null;
	private DIRListLine line2 = null;
	
	public DIRListLineMatch(DIRListLine line1, DIRListLine line2) {
		super();
		this.line1 = line1;
		this.line2 = line2;
		
		// could raise an exception if the tokens don't match!!!
		// NO!!!! Can't then setup non-matched holders
	}
	
	
	public boolean isMatched() {
		boolean isMatched = false;
		if (line1 != null && line2 != null) {
			isMatched = line1.isMatchedTo(line2);
		}
		return isMatched;
	}
	
	public List getMatchedLines() {
		
		ArrayList list = new ArrayList();
		if (line1 != null){
			list.add(line1);
		}
		if (line2 != null){
			list.add(line2);
		}
	
		return list;
	}
	
	public String getTextToMatch(){
		String txt = null;
		if (line1 != null){
			txt = line1.getTextToMatch(); // safely assume that 1 has it if there is text to match defined???
		}
		return txt;
	}
	
}
