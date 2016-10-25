package uk.co.neo9.apps.accounts;

import java.util.HashMap;
import java.util.Map;

public class OutputParameterModel {

	// static helpers
	private static final String CSV_HEADER_STATEMENT = "date,,amount,,source,,in/out,balance,method,reference";
	private static final String CSV_HEADER_TRACKER = ",date,,amount,where,method,description,category,owner,paid";
	private static final String CSV_HEADER_TRACKER_EXTENDED = ",date,,amount,where,method,description,category,subcat,owner,paid";
	
	private static final String ID_STATEMENT = "statement";
	private static final String ID_TRACKER = "tracker";	
	
	private static Map<String,OutputParameterModel> params = new HashMap<String,OutputParameterModel>();
	
	static {
		
		// statement model
		OutputParameterModel statement = new OutputParameterModel();
		statement.outputParamId = getStatementParamId();
		statement.setOutputCSVHeader(CSV_HEADER_STATEMENT);
		params.put(statement.outputParamId,statement);
		
		// tracker model
		OutputParameterModel tracker = new OutputParameterModel();
		tracker.outputParamId = getTrackerParamId();
		tracker.setOutputCSVHeader(CSV_HEADER_TRACKER);
		params.put(tracker.outputParamId,tracker);		
		
		// new tracker with sub-cat
		OutputParameterModel trackerExt = new OutputParameterModel();
		trackerExt.outputParamId = getTrackerExtendedParamId();
		trackerExt.setOutputCSVHeader(CSV_HEADER_TRACKER_EXTENDED);
		params.put(trackerExt.outputParamId,trackerExt);	
	}
	
	
	public static OutputParameterModel getParams(String ID){
		return params.get(ID);	
	}
	
	
	// model attributes
	private String outputFileName;
	private String outputCSVHeader;
	private String outputParamId;
	private boolean extended = false;
	
	public String getOutputCSVHeader() {
		return outputCSVHeader;
	}
	public void setOutputCSVHeader(String outputCSVHeader) {
		this.outputCSVHeader = outputCSVHeader;
	}
	public String getOutputFileName() {
		return outputFileName;
	}
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
	public String getOutputParamId() {
		return outputParamId;
	}
//	public void setOutputParamId(String outputParamId) {
//		this.outputParamId = outputParamId;
//	}
	public static String getTrackerParamId() {
		return ID_TRACKER+false;
	}
	public static String getTrackerExtendedParamId() {
		return ID_TRACKER+true;
	}
	public static String getStatementParamId() {
		return ID_STATEMENT;
	}
}
