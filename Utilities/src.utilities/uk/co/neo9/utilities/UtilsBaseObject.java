package uk.co.neo9.utilities;

/**
 * Insert the type's description here.
 * Creation date: (24/10/2001 10:04:59)
 * @author: Administrator
 */
public class UtilsBaseObject {
	
	protected static boolean isLoggingOn = true;
	
/**
 * UtilsBaseObject constructor comment.
 */
public UtilsBaseObject() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (24/10/2001 11:43:07)
 */
public String convertToString(int value) {
	
	return new Integer(value).toString();
	
}
/**
 * Insert the method's description here.
 * Creation date: (24/10/2001 11:43:07)
 */
public String convertToString(boolean value) {

	/*
	if (value) {
		return "TRUE";
	}
	else {
		return "FALSE";
	}
	*/

	return new Boolean(value).toString();
}

public void dump(){
	// override to use
}

public void dumpField(String fieldName, char fieldValue) {

	StringBuffer buf = new StringBuffer();
	buf.append(fieldName);
	buf.append(": ");
	buf.append(fieldValue);	
	dumpOut(buf.toString());
	
}
private void dumpOut(String txt) {
	System.out.println(txt);	
	
}
public void dumpField(String fieldName, int fieldValue) {

	StringBuffer buf = new StringBuffer();
	buf.append(fieldName);
	buf.append(": ");
	buf.append(fieldValue);	
	dumpOut(buf.toString());
	
}
/**
 * Insert the method's description here.
 * Creation date: (24/10/2001 10:02:23)
 */
public void dumpField(String fieldName, Object fieldValue) {

	StringBuffer buf = new StringBuffer();
	buf.append(fieldName);
	buf.append(": ");
	buf.append(fieldValue);	
	dumpOut(buf.toString());
	
}
public void dumpField(String fieldName, boolean fieldValue) {

	StringBuffer buf = new StringBuffer();
	buf.append(fieldName);
	buf.append(": ");
	buf.append(fieldValue);	
	dumpOut(buf.toString());
	
}

public static void staticLog(String message){
	if (isLoggingOn) System.out.println(message);	
}

public void log(String message){
	if (isLoggingOn) System.out.println(message);	
}
}
