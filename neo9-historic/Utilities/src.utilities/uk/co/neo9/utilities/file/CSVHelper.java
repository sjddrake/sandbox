/*
 * Created on 23-Apr-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities.file;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author ct039314
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CSVHelper {
	
	private static final char COMMA_CHAR = ',';
	private static final String COMMA_STRING = ",";
	private static final char DOUBLE_QUOTES = '"';
	private static final String EMPTY_STRING = "";

	private static final String CONSECUTIVE_FREE_TEXT_FIELDS = "\",\"";
	private static final String FREE_TEXT_FIELD_FOLLOWED_BY_CSV = "\",";
	private static final String FREE_CSV_FOLLOWED_BY_CSV = ",";
	
	
	
	public static String formatCSVLine(String[] fields) {
		
		if (fields == null || fields.length < 1) {
			 return null;
		}
		
		StringBuffer buf = new StringBuffer(fields[0]);
		for (int i = 1; i < fields.length; i++) {
			String field = fields[i];
			buf.append(COMMA_STRING);
			buf.append(field);
		}
		
		return buf.toString();
	}	
	
	
	public static String formatCSVLine(String oneFieldOrCompletedLine) {
		
		if (oneFieldOrCompletedLine == null) {
			 return null;
		}
		
		StringBuffer buf = new StringBuffer(oneFieldOrCompletedLine);
		buf.append(COMMA_STRING);
		
		return buf.toString();
	}		
	
	
	public static String formatData(String val){
		// might want to wrap in quotes!
		// 2010-04-29 - doing it now!
		StringBuffer buf = new StringBuffer("\"");
		if (val != null) {
			buf.append(val);
		}
		buf.append("\"");
		return buf.toString();
	}

	public static String formatData(boolean val){
		String text = null;
		text = val ? "Yes" : "No";
		return text;
	}	
	
	
	public static Vector extractCSVFields(String csvLine) {

		// null safety
		if (csvLine == null || csvLine.trim().length() == 0){
			return new Vector(0);
		}
		
		// check for a simple parse
		Vector fields = null;
		int indexOfQuotes = csvLine.indexOf(DOUBLE_QUOTES);
		if (indexOfQuotes != -1) {
			fields = parseCSVQuotedLine(csvLine);
		} else {
			fields = parseCSVUnQuotedLine(csvLine);
		}
		
		return fields;
	}
	
	
	
	private static Vector parseCSVQuotedLine(String csvLine) {

		Vector fields = new Vector();
		
		int csvLineLength = csvLine.length();
		int startOfField = 0;
		int endOfField= 0;
		boolean foundStart = false;
		boolean foundEnd = false;
		boolean isFreeTextField = false;
		char[] csvChars =  csvLine.toCharArray();
		
		char thisChar = 0;
		int i = 0;
		for (int j = 0; j < csvChars.length; j++) {
			
			if (j < i) {
				continue;
			}
			i = j;
			
			thisChar = csvChars[i];
			
			// find the start of a field
			if (thisChar == DOUBLE_QUOTES) {
				isFreeTextField = true;
				
				if (foundStart == false){
					// looks like the start of a free-text field
					foundStart = true;
					startOfField = i+1;
					if (startOfField > csvLineLength) {
						reportBadCSVFormatError("Found a rogue double-quote at the end of the line.");
					}
					
				} else { // already found the start so this must be the end
					
					boolean isEmbeddedQuotes = checkForEmbeddedQuotes(csvLine,i);
					
					if (!isEmbeddedQuotes) {
						foundStart = false;
						endOfField = i;
						String newField = csvLine.substring(startOfField,endOfField);
						fields.add(newField);
						startOfField = determineStartOfNextField(csvLine,endOfField,isFreeTextField);
						i = startOfField;
						isFreeTextField = false;
					} else {
						// need to skip forward two characters
						i = i+2;
					}
					
				}
			} else if (thisChar == COMMA_CHAR) {
				
				// if the comma is in a free-text field then ignore it
				if(!isFreeTextField){
					
					endOfField = i;
					String newField = csvLine.substring(startOfField,endOfField);
					fields.add(newField);		
					startOfField = determineStartOfNextField(csvLine,endOfField,isFreeTextField);
					
				}

			} else {
				// don't need to do anything??
			}
			
		}
		
		if (startOfField != csvLineLength) {
			// need to rescue the final field
			String newField = csvLine.substring(startOfField,csvLineLength);
			fields.add(newField);				
		}
		
		return fields;
	}	
	
	
	

	/**
	 * @param csvLine
	 * @param i
	 * @return
	 */
	private static boolean checkForEmbeddedQuotes(String csvLine, int offset) {
		boolean hasEmbeddedQuotes = false;
		
		// first check that we're not at the end of the string
		int lengthOfLine = csvLine.length();
		if (lengthOfLine != offset+1){
			if (csvLine.charAt(offset+1) == DOUBLE_QUOTES){
				hasEmbeddedQuotes = true;
			}
		}		
		
		return hasEmbeddedQuotes;
	}



	private static int determineStartOfNextField(String csvLine, int endOfField, boolean isFreeTextField) {
		
		/****
		 * Variations:
		 * 
		 * 	xxxx","xxxx
		 *      ^
		 * 
		 * 	xxxx",xxxx
		 *      ^
		 *     
		 * 	xxxx,xxxx
		 *      ^ 
		 *  or end of line!  
		 *  
		 *  xxx"
		 *     ^
		 *     
		 *  xxx,
		 *     ^
		 *       
		 *  xxx
		 *    ^     
		 */
		

		
		// first check whether we are at the last line (save ArrayOutOfBounds!)
		int lengthOfLine = csvLine.length();
		if (lengthOfLine == endOfField){
			return lengthOfLine;
		}
		
		if (lengthOfLine == endOfField+1){
			// then the last character could be the final quotes of a free-text
			// field or it could be the comma for another empty CSV field
			char lastChar = csvLine.charAt(endOfField);
			if (lastChar == DOUBLE_QUOTES) {
				return lengthOfLine; // end of the road
			} else if (lastChar == COMMA_CHAR) {
				return lengthOfLine; // dunno - can't think now!!!
			}
		}
		
		// this one could be really wrong!!
		if (lengthOfLine == endOfField+2){
			// then the last character could be the final quotes of a free-text
			// field or it could be the comma for another empty CSV field
			char lastChar = csvLine.charAt(endOfField);
			if (lastChar == DOUBLE_QUOTES) {
				return endOfField; // end of the road
			} else if (lastChar == COMMA_CHAR) {
				return endOfField; // dunno - can't think now!!!
			}
		}
		
		if (isFreeTextField) {
			
			// otherwise we're mid line with a mix of field types
			String bitToTest = csvLine.substring(endOfField,endOfField+3);
			if (CONSECUTIVE_FREE_TEXT_FIELDS.equals(bitToTest)) {
				return endOfField+2; //Si - changed from 3.
			} else {
				bitToTest = csvLine.substring(endOfField,endOfField+2);
				if (FREE_TEXT_FIELD_FOLLOWED_BY_CSV.equals(bitToTest)) {
					return endOfField+2;
				}
			}		
			
		} else {
			// start of next field is simply after the current comma
			return endOfField+1;
		}
		

		
		// if we get here it's gone wrong!
		reportBadCSVFormatError("Couldn't find the start of the next field!");
		
		return 0;
	}



	private static void reportBadCSVFormatError(String message) {
		System.err.println(message);
	}



	private static Vector parseCSVUnQuotedLine(String csvLine) {

		/*
		 * This version assumes that all commas
		 * in the line are simply field delimiters
		 * 
		 */
		
		Vector fields = new Vector();
		
		StringTokenizer s = new StringTokenizer(csvLine,COMMA_STRING,true);
		String lToken = null;
		boolean fieldAdded = false;
		while (s.hasMoreTokens()) {
			lToken = s.nextToken();
			lToken = lToken.trim();
			
			if(lToken.equals(COMMA_STRING)){
				if (fieldAdded){
					// this token is the delimiter at the end of the previously added field
					fieldAdded = false;
				} else {
					// this delimiter marks an empty field
					fields.add(EMPTY_STRING);
					fieldAdded = false;
				}
			} else {
				// this token holds the values in a field
				fields.add(lToken);
				fieldAdded = true;
			}
			
		}
		
		// last gotcha - could have been an empty field
		if (lToken != null) {
			if (lToken.equals(COMMA_STRING)) {
				fields.add(EMPTY_STRING);
			}			
		}
		
		return fields;
	}	
	
	
	


}
