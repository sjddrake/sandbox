/*
 * Created on 21-Dec-06
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.sandbox.coautils;

/**
 * @author ct039314
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface DailyStatsStatusConstants {
	

	public static final String CREATED = "CTD";
	public static final String CONFIRMED = "CNF";
	public static final String EXECUTING = "EXE";
	public static final String SCHEDULED = "SCH";
	public static final String CANCELLED = "CAN";	
	public static final String FAILED = "FLD";
	public static final String EXCEPTION = "XPT";
	public static final String PROCESSED = "PRO";
	public static final String TMS = "TMS";

	public static final int INDEX_PROCESSED = 0;
	public static final int INDEX_CREATED   = 1;
	public static final int INDEX_CONFIRMED = 2;
	public static final int INDEX_EXECUTING = 3;
	public static final int INDEX_SCHEDULED = 4;
	public static final int INDEX_CANCELLED = 5;
	public static final int INDEX_FAILED    = 6;
	public static final int INDEX_EXCEPTION = 7;
	public static final int INDEX_TMS = 8;
	
	public static final int INDEX_SPECIAL_ERROR_TOTAL = 99;
	
	public static final int NO_OF_STATUS_CODES = 9;
}
