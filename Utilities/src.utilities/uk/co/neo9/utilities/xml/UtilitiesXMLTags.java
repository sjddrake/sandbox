package uk.co.neo9.utilities.xml;

/**
 * Insert the type's description here.
 * Creation date: (24/10/2001 11:36:37)
 * @author: Administrator
 */
public class UtilitiesXMLTags {

    public final static java.lang.String START_OPENING_TAG = "<";
    public final static java.lang.String START_CLOSING_TAG = "</";
    public final static java.lang.String END_TAG = ">";

    /**
     * UtilitiesXMLTags constructor comment.
     */
    public UtilitiesXMLTags() {
        super();
    }
    public static String buildTag(String pElementName, boolean pOpen) {

        // build the tag text
        String lTag = null;
        if (pOpen) {
            lTag = new String(START_OPENING_TAG);
        } else {
            lTag = new String(START_CLOSING_TAG);
        }
        lTag = lTag.concat(pElementName);
        lTag = lTag.concat(END_TAG);

        return lTag;
    }

    public static String buildElement(String pElementName, Integer pValue) {
    	String lTag = null;
    	if (pValue!=null){
    		lTag = buildElement(pElementName,pValue.toString());
    	} else {
    		String nullString = null;
    		lTag = buildElement(pElementName,nullString);
    	}
    	return lTag;
    }    
    
    public static String buildElement(String pElementName, int pValue) {
    	return buildElement(pElementName,Integer.toString(pValue));
    }
    
    public static String buildElement(String pElementName, String pValue) {

        // build the tag text
        String lTag = null;

        // opening tag
        lTag = new String(START_OPENING_TAG);
        lTag = lTag.concat(pElementName);
        lTag = lTag.concat(END_TAG);

        // value
        if (pValue != null) {
        	lTag = lTag.concat(pValue);
        }
        
        // closing tag
        lTag = lTag.concat(START_CLOSING_TAG);
        lTag = lTag.concat(pElementName);
        lTag = lTag.concat(END_TAG);
        
        return lTag;
    }



    public static String buildElement(String pElementName, boolean pValue) {

        // build the tag text
        String lTag = null;

        // opening tag
        lTag = new String(START_OPENING_TAG);
        lTag = lTag.concat(pElementName);
        lTag = lTag.concat(END_TAG);

        // value
        if (pValue) 
			lTag = lTag.concat("true");
        else
			lTag = lTag.concat("false");
        
        // closing tag
        lTag = lTag.concat(START_CLOSING_TAG);
        lTag = lTag.concat(pElementName);
        lTag = lTag.concat(END_TAG);
        
        return lTag;
    }
}
