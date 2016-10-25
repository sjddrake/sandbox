package uk.co.neo9.sandbox;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import uk.co.neo9.utilities.file.CSVAbstractBuddy;
import uk.co.neo9.utilities.file.ICSVDataObject;

public class DIRListLineMatchCSVBuddy extends CSVAbstractBuddy {
	
	private final static int no_of_fields_in_csv_line = 6;
	
	private final static int index_IS_MATCH = 0;
	private final static int index_TEXT_TO_MATCH = 1;
	private final static int index_LINE_TEXT_1 = 3;
	private final static int index_FILE_NAME_1 	= 2;
	private final static int index_LINE_TEXT_2 = 4;
	private final static int index_FILE_NAME_2 	= 5;

	
	public String getCSVHeader(){

		String[] fields = new String[no_of_fields_in_csv_line];
		
		fields[index_IS_MATCH] = "Match Found";
		fields[index_TEXT_TO_MATCH] = "Text To Match";
		fields[index_FILE_NAME_1] = "FileName 1";
		fields[index_LINE_TEXT_1] = "Line Text 1";
		fields[index_FILE_NAME_2] = "FileName 2";
		fields[index_LINE_TEXT_2] = "Line Text 2";
		
		
		String csvLine = formatCSVLine(fields);

		return csvLine;		
		
	}
	
	
	public String buildCSVLine(ICSVDataObject dataObject) {
		
		DIRListLineMatch matches = (DIRListLineMatch)dataObject;
		
		String[] fields = new String[no_of_fields_in_csv_line];
		
		fields[index_IS_MATCH] = formatData(matches.isMatched());
		
		List matchedLines = matches.getMatchedLines();
		for (int i = 0; i < matchedLines.size(); i++) {
			DIRListLine listLine = (DIRListLine)matchedLines.get(i);
			if (i==0) {
				fields[index_TEXT_TO_MATCH] = formatData(listLine.getTextToMatch());
				fields[index_FILE_NAME_1] = formatData(listLine.getFileName());
				fields[index_LINE_TEXT_1] = formatData(listLine.getLineText());
			} else if (i==1){
				fields[index_FILE_NAME_2] = formatData(listLine.getFileName());
				fields[index_LINE_TEXT_2] = formatData(listLine.getLineText());
			}
		}
		
		String csvLine = formatCSVLine(fields);
			
		return csvLine;
	}
	
	
	public ICSVDataObject buildDataObject(Vector lCSVData){
		
//		VideoEntry entry = new VideoEntry();
//		for (int i = 0; i < lCSVData.size(); i++) {
//
//			String field =(String)lCSVData.get(i);
//
//			switch (i) {
//				case index_category : entry.setCategory(field); break;
//				case index_comments : entry.setComments(field); break;
//				case index_format : entry.setFormat(field); break;
//				case index_genre : entry.setGenre(field); break;
//				case index_original : entry.setOriginal(Boolean.valueOf(field).booleanValue()); break;
//				case index_revisit : entry.setRevisit(Boolean.valueOf(field).booleanValue()); break;
//				case index_source : entry.setSource(field); break;
//				case index_title : entry.setTitle(field); break;
//				default : break;
//			}			
//		}
//		
//		return entry;
		
		return null;
	}
	
}
