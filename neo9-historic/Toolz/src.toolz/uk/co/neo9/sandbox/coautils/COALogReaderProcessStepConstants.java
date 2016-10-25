/*
 * Created on Dec 18, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.sandbox.coautils;

/**
 * @author ct020576
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface COALogReaderProcessStepConstants{

	// process step names
	public final static String STEP_NAME_UPDATE_SUBJECT_PARTY_DETAILS 		= "Update subject party details";
	public final static String STEP_NAME_UPDATE_EMAIL 						= "Update Email";
	public final static String STEP_NAME_MOVING_WITH_PARTY 					= "Update moving with party";
	public final static String STEP_NAME_MOVING_WITH_PARTIES 				= "Update moving with parties";
	public final static String STEP_NAME_CORRESPONDENCE_DETAILS_CHANGE 		= "Update Correspondence address :";
	public final static String STEP_NAME_CORRESPONDENCE_DETAILS_CHANGES 	= "Update Correspondence addresses";
	public final static String STEP_NAME_GENERATE_LETTERS 					= "Generate Letters";
	public final static String STEP_NAME_SET_CBS_INDICATORS 				= "Set CBS Indicators";
	public final static String STEP_NAME_GENERATE_CONTACT_NOTE 				= "Generate Contact Note";
	public final static String STEP_NAME_ADDRES_PRTOECTION 					= "Address Protection";
		
	// these are for recording the hand off step
	public final static String STEP_NAME_HANDOFF_EXCEPTION = "Service Centre Handoff"; //"Service Centre Handoff (Exception)";
	public final static String STEP_NAME_HANDOFF_PENDING_EXCEPTION = "Handoff Pending Exception";
	public final static String STEP_NAME_HANDOFF_STP_FAILURE = "Service Centre Handoff STP Failure";
	
	// these are specifically for pending requests
	public final static String STEP_NAME_PENDING_WOKEN_UP = "Triggered pending request";
	public final static String STEP_NAME_PENDING_VALIDATION = "Pending request validation";
	
	// new
	public final static String STEP_NAME_CHECK_FOR_CHAINS = "Check For Chains";
	
	// process step names
	public final static int STEP_INDEX_UPDATE_SUBJECT_PARTY_DETAILS 	= 0;
	public final static int STEP_INDEX_UPDATE_EMAIL 					= 1;
	public final static int STEP_INDEX_MOVING_WITH_PARTY 				= 2;
	public final static int STEP_INDEX_MOVING_WITH_PARTIES 				= 3;
	public final static int STEP_INDEX_CORRESPONDENCE_DETAILS_CHANGE 	= 4;
	public final static int STEP_INDEX_CORRESPONDENCE_DETAILS_CHANGES 	= 5;
	public final static int STEP_INDEX_GENERATE_LETTERS 				= 6;
	public final static int STEP_INDEX_SET_CBS_INDICATORS 				= 7;
	public final static int STEP_INDEX_GENERATE_CONTACT_NOTE 			= 8;
	public final static int STEP_INDEX_ADDRES_PRTOECTION 				= 9;
		
	// these are for recording the hand off step
	public final static int STEP_INDEX_HANDOFF_EXCEPTION 				= 10;
	public final static int STEP_INDEX_HANDOFF_PENDING_EXCEPTION 		= 11;
	public final static int STEP_INDEX_HANDOFF_STP_FAILURE 				= 12;

	// these are specifically for pending requests
	public final static int STEP_INDEX_PENDING_WOKEN_UP 				= 13;
	public final static int STEP_INDEX_PENDING_VALIDATION 				= 14;
	
	// new
	public final static int STEP_INDEX_CHECK_FOR_CHAINS 				= 15;

}
