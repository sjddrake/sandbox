/*
 * Created on 28-Sep-07
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
public interface KYCLoggerConstants {

	 final static String MESSAGE_PIPE = "~";
	 final static String MESSAGE_EMPTY_STRING = "";
	 final static String MESSAGE_NEWLINE = "\r\n";
	 final static String MESSAGE_NULL = "null!!";
	 final static String MESSAGE_HYPHEN = " - ";
	 final static String MESSAGE_HYPHEN_ARROW = " -> ";
	 final static String MESSAGE_COMMA = ",";
	 final static String MESSAGE_COLONSPACE = ": ";
	 final static String MESSAGE_SPACE = " ";
	
	 final static String MESSAGE_REF = "REF: ";
	 final static String MESSAGE_PARTY = "PARTY: ";

	 final static String MESSAGE_KYCLogStartMarker = "Starting to log new session";
	 final static String MESSAGE_F073_GO = "F073 is being called...";
	 final static String MESSAGE_F073_NO_GO = "F073 is NOT being called!";

	 final static String MESSAGE_F073_SUCCESS = "F073 call completed ok";
	 final static String MESSAGE_F073_FAIL = "F073 returned an error";

	 final static String MESSAGE_KYC_REQUIRED = "KYC refresh is required";
	 final static String MESSAGE_KYC_NOT_REQUIRED = "KYC refresh is NOT required";

	// log line identifiers
	 final static String LLI_F061ResponseV1 = "F061RspValV1";
	 final static String LLI_F061RespAuditV1 = "F061RspAudV1";
	 final static String LLI_F062RequestV1 = "F062ReqValV1";
	 final static String LLI_F062ReqAuditV1 = "F062ReqAudV1";
	 final static String LLI_F062RespAuditV1 = "F062RspAudV1";
	 final static String LLI_F073RequestV1 = "F073ReqValV1";
	 final static String LLI_F073ReqAuditV1 = "F073ReqAudV1";
	 final static String LLI_F075RespAuditV1 = "F075RspAudV1";
	 final static String LLI_KYCChangesV1 = "KYCChangesV1";
	 final static String LLI_KYCRequiredV1 = "KYCRequiredV1";
	 final static String LLI_KYCSessionV1 = "KYCSessionV1";
	 final static String LLI_F073SuccessV1 = "F073SuccessV1";
	 final static String LLI_F073CallV1 = "F073CallV1";
	 final static String LLI_KYCLogStartMarker = "LOGKYC";
	
	 final static String LLI_F073RequestAttributes = "F073RequestAttributes";
	 final static String LLI_F062ResponseAttributes = "F062ResponseAttributes";
	 
	 
	final static String TAG_CntryResdnceAuditData = "CntryResdnceAuditData";
	final static String TAG_BirthDtAuditData = "BirthDtAuditData";
	final static String TAG_EmplyAddrAuditData = "EmplyAddrAuditData";
	final static String TAG_FrstNatnAuditData = "FrstNatnAuditData";
	final static String TAG_ScndNatnAuditData = "ScndNatnAuditData";
	final static String TAG_KYCNonCoreAuditData = "KYCNonCoreAuditData";
	final static String TAG_PartyNonCoreAuditData = "PartyNonCoreAuditData";
	final static String TAG_KYCDataAuditData = "KYCDataAuditData";
}
