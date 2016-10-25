/*
 * Created on 09-Jan-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UtilitiesTextHelper {
	
	private final static NumberFormat GBP_CURRENCY_FORMAT = new DecimalFormat("£#,###.##"); //new DecimalFormat("'�'#,###.##");


	
	public static String formatGBPCurrency(BigDecimal pAmount) {

		// null check
		if (pAmount == null) return "";

		// the thing
		return GBP_CURRENCY_FORMAT.format(pAmount);
	}	
	
	
	public static String lowerFirstCharacter(String pText){

		if (pText == null) return null;

		int length = pText.length();
		if (length == 0) return "";

		String lText = pText.substring(0,1);
		lText = lText.toLowerCase();
		lText = lText + pText.substring(1,length);

		return lText;

	}


	public static String upperFirstCharacter(String pText){

		if (pText == null) return null;

		int length = pText.length();
		if (length == 0) return "";

		String lText = pText.substring(0,1);
		lText = lText.toUpperCase();
		lText = lText + pText.substring(1,length);

		return lText;

	}

	public static String replaceMarker(String pText, String pMarker, String pValue){

//
//		!!!! There's a bug in this! Try doing this call and watch it loop forever!!!!
//
//		String lXOsFileName;
//		lXOsFileName = UtilitiesTextHelper.replaceMarker("AbandonContextCO.java",".java","s.java");
//
//

		String lText = new String(pText);
		int lOffset = lText.indexOf(pMarker);

		while (lOffset != CommonConstants.INDEX_OF_NOT_FOUND){

			// replace the range of the string that is the marker with the value
			StringBuffer lBuffer = new StringBuffer(lText);

			if (pValue != null) {
				lBuffer.replace(lOffset, lOffset+pMarker.length(), pValue);
			} else {
				lBuffer.delete(lOffset, lOffset+pMarker.length());
			}
			lText = lBuffer.toString();

			lOffset = lText.indexOf(pMarker);
		}

		return lText;

	}


	public static String deleteMarker(String pText, String pMarker){

		return replaceMarker(pText,pMarker,null);

	}



	public static String clipToWord(String pText) {

			/*
			 *         This method should be used to isolate a word embbeded in
			 *        other characters.
			 *
			 *         e.g.  *ExtLocIdTx[20];
			 *
			 *         would be stripped to ExtLocIdTx
			 *
			 */


			StringBuffer lTextBuffer = new StringBuffer();
			boolean lIsStartFound = false;

			char[] lCharacters = pText.toCharArray();
			for (int i = 0; i < lCharacters.length; i++) {

					if (Character.isLetter(lCharacters[i])){
							lIsStartFound = true;
							lTextBuffer.append(lCharacters[i]);
					} else {

							if (lIsStartFound == true) {
									// found the end of the word now
									break;
							}

					}
			}

			return lTextBuffer.toString();

	}

	public static List<String> splitLineIntoWords(String pLine){

		StringTokenizer s = new StringTokenizer(pLine," ",false);
		List<String> lWords = new ArrayList<String>();
		while (s.hasMoreTokens()) {
			lWords.add(s.nextToken().trim()); // takes out tabs etc
		}
		return lWords;
	}

	
	/**
	 * If a line of text has tabs in or multiple space characters 
	 * together, this will replace all with a single space
	 * 
	 * 
	 * @param pLine
	 * @return
	 */
	public static String removePaddingInText(String pLine){

		StringTokenizer s = new StringTokenizer(pLine," ",false);
		StringBuilder lWords = new StringBuilder();
		while (s.hasMoreTokens()) {
			lWords.append(s.nextToken().trim()); // takes out tabs etc
			if (s.hasMoreTokens()) {
				lWords.append(" ");
			}
		}
		return lWords.toString();
	}

	public static List<String> tokenizeString(String pLine, String pDelimiter, boolean pReturnDelimiters){

		StringTokenizer s = new StringTokenizer(pLine,pDelimiter,pReturnDelimiters);
		List<String> lWords = new ArrayList<String>();
		while (s.hasMoreTokens()) {
			lWords.add(s.nextToken().trim()); // takes out tabs etc
		}
		return lWords;
	}

	public static List<String> tokenizeString(String pLine, String pDelimiter){
		return tokenizeString(pLine, pDelimiter, false);
	}


	public static List<String> sortStringsAlphabetically(List<String> pList){

		Collections.sort(pList);
		return pList;
	}

	/**
	 * Simply gets a contiguous sequence of numeric characters from a string
	 *
	 *  This version requires the offset of the first numeric character in the String 
	 *
	 * @param text
	 * @param offset
	 * @return
	 */
	public static String getNumberFromText(String text, int offset){

		// this simply extracts a number from within a string

		StringBuffer number = new StringBuffer();
		boolean notFinished = true;
		while (notFinished && offset < text.length()) {
			char c = text.charAt(offset);
			if (Character.isDigit(c)){
				number.append(c);
			} else {
				notFinished = false;
			}
			offset++;
		}

		return number.toString();
	}
	
	
	/**
	 * Simply gets a contiguous sequence of numeric characters from a string
	 *
	 *  This version starts at the start of the String and extracts the 
	 *  first sequence it identifies
	 *
	 * @param text
	 * @param offset
	 * @return
	 */
	public static String getNumberFromText(String text){

		// first find the first occurence of a numeric character
		int offset = 0;
		boolean found = false;
		while ((found == false) && (offset < text.length())) {
			char c = text.charAt(offset);
			if (Character.isDigit(c)){
				found = true;
			} else {
				offset++;
			}
		}

		// have we found a numeric character?
		String numberFromString = null;
		if (found) {
			numberFromString = getNumberFromText(text, offset);
		}
		return numberFromString;
	}	
	

    public static String padString(String iString, char delimiter, int length, boolean left) {

        String target = null;

        if (iString != null) {

              StringBuffer iStringPadded = new StringBuffer();

              while (iStringPadded.length()+iString.length()< length) {
                    iStringPadded.append(delimiter);
              }

              if (left) {
            	  target = iStringPadded.toString() + iString;
              }	else {
            	  target = iString + iStringPadded.toString();
              }

        }

        return target;
  }


  	public static List<String> splitTextIntoLines(String text){

  		List<String> lines = new ArrayList<String>();

  		StringTokenizer tokenizer = new StringTokenizer(text,"\n");

  			String line = null;
  			while (tokenizer.hasMoreTokens()) {
  				line = tokenizer.nextToken();
  				lines.add(line.trim());
  			}

  		return lines;
	}

	public static StringBuffer flattenText(String lLine){

			StringTokenizer s = new StringTokenizer(lLine," ",false);
			String lToken = null;
			StringBuffer buff = new StringBuffer();

			while (s.hasMoreTokens()) {
				lToken = s.nextToken();
				lToken = lToken.trim();
				buff.append(lToken);
			}

			return buff;
	}

	
	public static List<String> deduplicateLines(List<String> lines) {
		return deduplicateLines(lines,true);
	}
	
	public static List<String> deduplicateLines(List<String> lines, boolean trim) {		
		Collections.sort(lines);
		List<String> extractedLines = new ArrayList<String>();
		
		// simply loop through and only include unique lines
		int noOfLines = lines.size();
		String previousLine = ((String)lines.get(0)).trim();
		extractedLines.add(previousLine);
		for (int i = 1; i < noOfLines; i++) {
			String line = ((String)lines.get(i)).trim();
			boolean sameAsPrevious = line.equals(previousLine);
			if (!sameAsPrevious) {
				extractedLines.add(line);
				previousLine = line;
			}
		}
		
		return extractedLines;
	}	
	

	
	/**
	 * This method tries to identify a month embedded in a string
	 * and return the Calendar ID for it
	 * 
	 * It uses the CommonConstants maps for the decode
	 * 
	 * @param text
	 * @return
	 */
	public static int extractMonthIdFromText(String text) {
		
		int monthId = CommonConstants.UNDEFINED;
		
		// first split the text into words
		List<String> words = UtilitiesTextHelper.splitLineIntoWords(text);
		for (String word : words) {
			// try and find a match
			monthId = CommonConstants.getMonthIdFromName(word);
			if (monthId != CommonConstants.UNDEFINED) {
				break;
			}
		}
		
		return monthId;
	}
	
	
	/**
	 * This method tries to identify a month embedded in a string
	 * and return a constant name for it. The name is either in
	 * its full form "January" or abbreviated "Jan" as specified
	 * by the boolean.
	 * 
	 * Returns NULL if no match found
	 * 
	 * It uses the CommonConstants maps for the decode
	 * 
	 * @param text
	 * @return
	 */
	public static String extractMonthNameFromText(String text, boolean fullName) {
		
		// first identify the month 
		int monthId = extractMonthIdFromText(text);
		
		// if we have succeeded, get the text name for it
		String monthName = null;
		if (monthId != CommonConstants.UNDEFINED) {
			if (fullName) {
				monthName = CommonConstants.decodeMonthID_English(monthId);
			} else {
				monthName = CommonConstants.decodeMonthID_EnglishShort(monthId);
			}
		}
		return monthName;
	}
	
	
	public static String formatPositionAsText(int pos){
		String text = null;
		switch (pos) {
			case 0: text = ""; break;
			case 1: text = "1st"; break;
			case 2: text = "2nd"; break;
			case 3: text = "3rd"; break;
			default: text = pos+"th"; break;
		}
		return text;
	}

	
	public static String stripLineOfText(String text, String lineToRemove) {
		String modifiedText = text.replace(lineToRemove+CommonConstants.NEWLINE, "");
		return modifiedText;
	}
	
}
