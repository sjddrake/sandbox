package uk.co.neo9.apps.videocat;

import java.util.Iterator;
import java.util.Vector;

import uk.co.neo9.utilities.file.CSVAbstractBuddy;
import uk.co.neo9.utilities.file.ICSVDataObject;

public class VideoEntryCSVBuddy extends CSVAbstractBuddy {
	
	private final static int no_of_fields_in_csv_line = 8;
	
	private final static int index_category = 5;
	private final static int index_comments = 7;
	private final static int index_format 	= 1;
	private final static int index_genre 	= 2;
	private final static int index_original = 6;
	private final static int index_revisit 	= 3;
	private final static int index_source 	= 4;
	private final static int index_title	= 0;
	
	public String getCSVHeader(){

		String[] fields = new String[no_of_fields_in_csv_line];
		
		fields[index_category] = "Category";
		fields[index_comments] = "Comments";
		fields[index_format] = "Format";
		fields[index_genre] = "Genre";
		fields[index_original] = "Original";
		fields[index_revisit] = "Revisit";
		fields[index_source] = "Source";
		fields[index_title] = "Title";	
		
		
		String csvLine = formatCSVLine(fields);

		return csvLine;		
		
	}
	
	
	public String buildCSVLine(ICSVDataObject dataObject) {
		
		VideoEntry entry = (VideoEntry)dataObject;
		
		String[] fields = new String[no_of_fields_in_csv_line];
		
		fields[index_category] = formatData(entry.getCategory());
		fields[index_comments] = formatData(entry.getComments());
		fields[index_format] = formatData(entry.getFormat());
		fields[index_genre] = formatData(entry.getGenre());
		fields[index_original] = formatData(entry.getOriginal());
		fields[index_revisit] = formatData(entry.getRevisit());
		fields[index_source] = formatData(entry.getSource());
		fields[index_title] = formatData(entry.getTitle());	
		
		String csvLine = formatCSVLine(fields);
			
		return csvLine;
	}
	
	
	public ICSVDataObject buildDataObject(Vector lCSVData){
		
		VideoEntry entry = new VideoEntry();
		for (int i = 0; i < lCSVData.size(); i++) {

			String field =(String)lCSVData.get(i);

			switch (i) {
				case index_category : entry.setCategory(field); break;
				case index_comments : entry.setComments(field); break;
				case index_format : entry.setFormat(field); break;
				case index_genre : entry.setGenre(field); break;
				case index_original : entry.setOriginal(Boolean.valueOf(field).booleanValue()); break;
				case index_revisit : entry.setRevisit(Boolean.valueOf(field).booleanValue()); break;
				case index_source : entry.setSource(field); break;
				case index_title : entry.setTitle(field); break;
				default : break;
			}			
		}
		
		return entry;
	}
	
}
