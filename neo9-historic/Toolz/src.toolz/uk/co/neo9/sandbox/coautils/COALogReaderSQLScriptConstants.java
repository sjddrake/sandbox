/*
 * Created on 05-Sep-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.sandbox.coautils;

import uk.co.neo9.utilities.CommonConstants;

/**
 * @author ct039314
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface COALogReaderSQLScriptConstants {

	public static final String SQL_UPDATE_PARTY
		 = "select ID, status, RES_ADDRESS_CHANGE, CORR_ADDRESS_CHANGE, CONTACT_DETAIL_CHANGE"
		   + CommonConstants.NEWLINE
		   + "from address_change_sessions where id in (";
		   
		   
}
