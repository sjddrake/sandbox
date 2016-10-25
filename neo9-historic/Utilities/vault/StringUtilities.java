package com.lloydstsb.chordiant.osp.helpers;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import com.chordiant.core.configuration.ConfigurationHelper;
import com.lloydstsb.crm.businessclasses.common.FormTypeConstants;
import com.lloydstsb.chordiant.osp.messageobjects.API;
import com.lloydstsb.framework.ea.loggingservice.LoggingService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Roger Chick v1.0
 * 
 * 		   Kuldip Bajwa v1.1 - adding Regular expressions functionality 
 * 							   5th April 2006
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class StringUtilities {
	static LoggingService logger = null;

	static {
		logger = new LoggingService(StringUtilities.class);
	}

	public static String padWithSpaces(String input, int desiredSize) {
		return padWithChar(input, desiredSize, ' ');
	}
	public static String padWithZeros(String input, int desiredSize) {
		return padWithChar(input, desiredSize, '0');
	}

	public static String padWithChar(String input, int desiredSize, char charToPad) {
		if (input.length() >= desiredSize)
			return input;

		StringBuffer finalString = new StringBuffer();

		for (int i = 0; i < desiredSize - input.length(); i++) {
			finalString.append(charToPad);
		}
		finalString.append(input);

		return finalString.toString();
	}

	public static String formatAmount(String input) {
		int desiredSize = 10;

		return padWithZeros(removePoints(appendZerosAfterPoint(input)), desiredSize);
	}

	public static String appendZerosAfterPoint(String input) {
		return appendCharsAfterChar(input, 2, '.', '0');
	}

	public static String appendCharsAfterChar(String input, int numberOfChars, char charToAppendAfter, char charToPad) {
		int position = input.indexOf(charToAppendAfter);

		if (position == -1 || input.substring(position).length() > numberOfChars)
			return input;
		else {
			int diff = numberOfChars - (input.substring(position).length() - 1);
			StringBuffer result = new StringBuffer();

			result.append(input);
			for (int i = 0; i < diff; i++) {
				result.append(charToPad);
			}
			return result.toString();
		}
	}

	/**
	* removes every occurance of a given character in a string. This uses StringTokenizer
	* and the default set of tokens as documented in the single arguement constructor.
	*
	* @param input a String to remove all characters from.
	* @param charToRemove a character to be removed from input String
	* @return a String that has had charToRemove removed.
	*/
	public static String removeChar(String input, String charToRemove) {
		StringBuffer result = new StringBuffer();
		if (input != null) {
			StringTokenizer st = new StringTokenizer(input, charToRemove);
			while (st.hasMoreTokens()) {
				result.append(st.nextToken());
			}
		}
		return result.toString();
	}

	/**
	 *  removes every occurance of a given character in a string. This implementation avoids using
	 *  StringTokenizer.
	 * 
	 *  @param input a String to remove all characters from.
	 *  @param charToRemove a character to be removed from input String
	 *  @return a String that has had charToRemove removed.
	 */
	public static String removeCharOptimized(String input, char charToRemove) {
		StringBuffer result = new StringBuffer();
		char[] inputArray = input.toCharArray();

		for (int i = 0; i < inputArray.length; i++) {
			if (inputArray[i] != charToRemove)
				result.append(inputArray[i]);
		}

		return result.toString();
	}

	public static String removeWhitespace(String input) {
		return removeChar(input, " ");
	}

	public static String removePoints(String input) {
		return removeChar(input, ".");
	}

	public static String removeReturns(String input) {
		return removeChar(input, "\n");
	}

	public static String removeTabs(String input) {
		return removeChar(input, "\t");
	}

	public static String formatSortCodeForOldAPIs(String sortCode) {
		return sortCode.substring(2);
	}

	/**
	 * **************************************************************
	 * createFax
	 * 
	 * Helper method to modify an XML string
	 * 
	 * **************************************************************
	 * **************************************************************
	 * @param 	String XMLRequest
	 * @return	java.lang.String
	 * @throws 	java.lang.Exception
	 * **************************************************************
	 */
	public static String changeXML(String XMLRequest, API faxRequest) {
		logger.methodEntry();

		StringBuffer sB = null;
		char[] cA = null;

		String t = null;
		String t1 = null;
		String t2 = null;

		if (XMLRequest != null && !XMLRequest.equals("")) {
			sB = new StringBuffer(XMLRequest);

			cA = XMLRequest.toCharArray();

			for (int i = 0; i < cA.length; i++) {
				if (cA[i] == '<' && (cA[i + 1] == 'A' || cA[i + 1] == 'a') && (cA[i + 2] == 'P' || cA[i + 2] == 'p') && (cA[i + 3] == 'I' || cA[i + 3] == 'i') && cA[i + 4] == '>') {
					t = "<API stylesheet=\"";

					String type = faxRequest.getRequest().getBody().getServiceInputData().getRightFaxRequest().getFaxBody().getType();

					if (type.equals(FormTypeConstants.AMEND_STANDING_ORDER)) {
						t1 = ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "PATH") + ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "AMEND_STANDING_ORDER");
					} else if (type.equals(FormTypeConstants.CANCEL_DIRECT_DEBIT)) {
						t1 = ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "PATH") + ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "CANCEL_DIRECT_DEBIT");
					} else if (type.equals(FormTypeConstants.CANCEL_NEW_DIRECT_DEBIT)) {
						t1 = ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "PATH") + ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "CANCEL_NEW_DIRECT_DEBIT");
					} else if (type.equals(FormTypeConstants.CANCEL_STANDING_ORDER)) {
						t1 = ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "PATH") + ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "CANCEL_STANDING_ORDER");
					} else if (type.equals(FormTypeConstants.SUSPEND_STANDING_ORDER)) {
						t1 = ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "PATH") + ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "SUSPEND_STANDING_ORDER");
					} else if (type.equals(FormTypeConstants.TRANSFER_DIRECT_DEBIT)) {
						t1 = ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "PATH") + ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "TRANSFER_DIRECT_DEBIT");
					} else if (type.equals(FormTypeConstants.TRANSFER_STANDING_ORDER)) {
						t1 = ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "PATH") + ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "TRANSFER_STANDING_ORDER");
					} else if (type.equals(FormTypeConstants.SETUP_STANDING_ORDER)) {
						t1 = ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "PATH") + ConfigurationHelper.getConfigurationValue("FaxStyleSheets", "CREATE_STANDING_ORDER");
					}
					t2 = t + t1 + "\"";

					sB.replace(i, i + 4, t2);

					logger.methodExit();
					
					return sB.toString();
				}
			}
		}
		
		logger.methodExit();

		return null;
	}

	/**
	 * **************************************************************
	 * removeEscapedCharacters
	 * 
	 * Helper method to modify an XML string to remove escaped
	 * characters and replace with their non-standard XML un-escaped 
	 * characters.
	 * 
	 * The characters are < >  & £ ¬
	 * 
	 * **************************************************************
	 * **************************************************************
	 * @param 	String XMLRequest
	 * @return	java.lang.String
	 * @throws 	java.lang.Exception
	 * **************************************************************
	 */
	public static String removeEscapedCharacters(String XMLRequest) {
		logger.methodEntry();

		XMLRequest = genericReplace(XMLRequest, " &amp; ", " and ");
		XMLRequest = genericReplace(XMLRequest, "&amp; ", "and ");
		XMLRequest = genericReplace(XMLRequest, " &amp;", " and");
		XMLRequest = genericReplace(XMLRequest, "&amp;", " and ");
		XMLRequest = genericReplace(XMLRequest, "&lt;", "");
		XMLRequest = genericReplace(XMLRequest, "&gt;", "");
		XMLRequest = genericReplace(XMLRequest, "&#163;", "GBP");
		//		XMLRequest = genericReplace(XMLRequest, "&#172;", "¬");

		logger.methodExit();	

		return XMLRequest;
	}

	/**
	 * **************************************************************
	 * genericReplace
	 * 
	 * Helper method to modify a string to replace any occurences of 
	 * a string with another string.
	 * 
	 * **************************************************************
	 * **************************************************************
	 * @param 	String input
	 * @param 	String searchstr
	 * @param 	String replaceStr
	 * @return	java.lang.String
	 * @throws 	java.lang.Exception
	 * **************************************************************
	 */
	public static String genericReplace(String input, String searchStr, String replaceStr) {
		logger.methodEntry();
		
		StringBuffer result = new StringBuffer();
		
		if (input != null) {
			int lastpos = 0;
			int matchpos = input.indexOf(searchStr, lastpos);
			if (matchpos >= 0) {
				while (matchpos >= 0) {
					if (lastpos > 0)
						result.append(replaceStr);
					result.append(input.substring(lastpos, matchpos));
					lastpos = matchpos + searchStr.length();
					matchpos = input.indexOf(searchStr, lastpos);
				}
				if (lastpos <= input.length()) {
					result.append(replaceStr);
					result.append(input.substring(lastpos));
				}
			} else {
				result.append(input);
			}
		}
		
		logger.methodExit();
		
		return result.toString();
	}

	/**
	 * **************************************************************
	 * removeTFromDate
	 * 
	 * Helper method to modify a string to replace any occurences of 
	 * a "T" in a date with a space. The tagValue contains the XML 
	 * tag that will enclose the date.
	 * 
	 * **************************************************************
	 * **************************************************************
	 * @param 	String input
	 * @param 	String tagValue
	 * @return	java.lang.String
	 * @throws 	java.lang.Exception
	 * **************************************************************
	 */
	public static String removeTFromDate(String input, String tagValue) {
		StringBuffer result = new StringBuffer();
		String startStr = "<" + tagValue + ">";
		String endStr = "</" + tagValue + ">";

		int startpos = input.indexOf(startStr, 0);
		int lastpos = input.indexOf(endStr, 0);

		if ((startpos > 0) && (lastpos > startpos)) {
			String firstStr = input.substring(0, startpos + startStr.length());
			String dateStr = input.substring(startpos + startStr.length(), lastpos);
			String secondStr = input.substring(lastpos);
			dateStr = genericReplace(dateStr, "T", " ");
			result.append(firstStr);
			result.append(dateStr);
			result.append(secondStr);
			return result.toString();
		} else
			return input;

	}

	public static String toTitleCase(String value) {
		StringBuffer strBuffer = null;
		StringTokenizer strToken = null;

		if (!ValidationHelper.isEmpty(value)) {
			strToken = new StringTokenizer(value, " ");
			strBuffer = new StringBuffer();

			try {
				while (strToken.hasMoreTokens()) {
					String tempToken = strToken.nextToken();
					if (tempToken != null && !tempToken.equals("")) {
						char[] charArray = tempToken.toCharArray();

						if (charArray != null) {
							for (int i = 0; i < charArray.length; i++) {
								char character = charArray[i];

								if (Character.isDigit(character) || !Character.isLetter(character)) {
									continue;
								}

								if (i == 0 && !Character.isDigit(character) && Character.isLetter(character)) {
									charArray[i] = Character.toUpperCase(character);
								} 
								else if (i > 0 && (Character.isDigit(charArray[i - 1]) || !Character.isLetter(charArray[i - 1]))) {
									charArray[i] = Character.toUpperCase(character);
								} 
								else if (i > 0 && !Character.isDigit(character) && Character.isLetter(character)) {
									charArray[i] = Character.toLowerCase(character);
								}
								
							}
							
							strBuffer.append(charArray);
							strBuffer.append(" ");
							charArray = null;
						}
						
					} 
					
				}				

			} 
			catch (NoSuchElementException nsex) {
				nsex.printStackTrace();
			}
			
		}

		return strBuffer.toString().trim();
	}

	/**
	 * isStringEmpty returned true if the input string is null, an empty string or just contains spaces.
	 *  
	 * @param input String to check if empty.
	 * @return returned true if the input string is null, an empty string or just contains spaces.
	 */
	public static boolean isStringEmpty (String input) {
		if (input == null)  {
			return true;
		}			
		else {
			return input.trim().equals("");
		}
			
	}
	
	public static String getInternalExternalSortCode(String sortCode) {
		String newSortCode = ConfigurationHelper.getConfigurationValue("ExternalToInternalSortCodes", sortCode);
		if (newSortCode != null){
			return newSortCode;
		} 
		else {
			return sortCode;
		}
		
	}
	
	
	public static String replaceToken(String replacingToken, String replaceTokenWith, String replaceIn) {
		String modifiedString = null;
		
		Pattern p = Pattern.compile(replacingToken);
		Matcher m = p.matcher(replaceIn); // get a matcher object

		modifiedString = m.replaceFirst(replaceTokenWith);

		return modifiedString;
		
	}
	
	
	public static String replaceTokenAll(String replacingToken, String replaceTokenWith, String replaceIn) {
		String modifiedString = null;
		
		Pattern p = Pattern.compile(replacingToken);
		Matcher m = p.matcher(replaceIn); // get a matcher object

		modifiedString = m.replaceAll(replaceTokenWith);

		return modifiedString;
		
	}
		 
	
	public static boolean isMultipleTokens(String tokenOne, String tokenTwo, String inString) {
		boolean isMultiple = false;

		Pattern p = Pattern.compile(tokenOne);
		Pattern p2 = Pattern.compile(tokenTwo);

		Matcher m = p.matcher(inString);
		Matcher m2 = p2.matcher(inString);

		if(m.find() && m2.find()) {
			isMultiple = true;	
		}
		
		return isMultiple;
		
	}

	public static String modifyString(String toRemoved, String replaceWith, String inString) {
		String modifiedString = inString.replaceAll(toRemoved, replaceWith);
		
		return modifiedString;
	}
	
	
	public static String getClassNameFromPackage(Object dataType) throws IllegalStateException {
		/*
		 * define candidate String
		 */		
		String className = dataType.toString();
		
		/*
		 * search for Periods
		 */
		String regex = "\\.";
		
		/*
		 * compile the regular expression
		 */
		Pattern pattern = Pattern.compile(regex);
		
		/*
		 * get a matcher object for
		 * candidate
		 */	
		Matcher m = pattern.matcher(className);
		
		int indexForSubstring = 0;
		
		
		/*
		 * search candidate String
		 * for regex and return index
		 * 
		 * note. end() will always
		 * return the index + 1
		 */
		while(m.find()) {			
			indexForSubstring = m.end();	
		}
		
		className = className.substring(indexForSubstring, className.length());
		
		
		if(!ValidationHelper.isEmpty(className)) {
			return className.trim();
		}
				
		return className;
		
	}
	
	
	
	
}
