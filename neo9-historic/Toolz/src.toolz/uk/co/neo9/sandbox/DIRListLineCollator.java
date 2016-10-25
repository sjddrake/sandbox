package uk.co.neo9.sandbox;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class DIRListLineCollator {
	
	private ArrayList matches = new ArrayList();
	private ArrayList mismatches = new ArrayList();
//	private Hashtable store = new Hashtable();
	private int matchToken = 0;

	
	public void storeMatches(DIRListLine line1, DIRListLine line2){
		// create new match
		matchToken++;
		line1.setMatchToken(matchToken);
		line2.setMatchToken(matchToken);
		DIRListLineMatch match = new DIRListLineMatch(line1,line2);
		matches.add(match);
	}
	
	
	public void storeUnmatched(DIRListLine line1){
		// create new match
		DIRListLineMatch match = new DIRListLineMatch(line1,null);
		mismatches.add(match);
	}


	public List exportMatchedTextToMatch() {
		
		ArrayList results = new ArrayList();
		
		for (Iterator iterator = matches.iterator(); iterator.hasNext();) {
			DIRListLineMatch match = (DIRListLineMatch) iterator.next();
			results.add(match.getTextToMatch());
		}
		
		return results;
	}
	
	
	public List exportMismatchedTextToMatch() {
		
		ArrayList results = new ArrayList();
		
		for (Iterator iterator = mismatches.iterator(); iterator.hasNext();) {
			DIRListLineMatch mismatch = (DIRListLineMatch) iterator.next();
			results.add(mismatch.getTextToMatch());
		}
		
		return results;
	}	
	
	public List getMatches() {
		return matches;
	}


	public List getMismatches() {
		return mismatches;
	}
	
	
}
