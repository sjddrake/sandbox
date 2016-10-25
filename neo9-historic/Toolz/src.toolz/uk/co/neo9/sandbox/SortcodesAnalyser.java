package uk.co.neo9.sandbox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import uk.co.neo9.test.Neo9TestingConstants;
import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.UtilsBaseObject;
import uk.co.neo9.utilities.file.FileServer;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class SortcodesAnalyser 
extends UtilsBaseObject {
	
	private final String SQL_INSERT_CLAUSE =
					"INSERT INTO OSPTSTOWNER.INDUSTRY_SORTCODES (" +
				   "BANK_CODE, SORTCODE, SHORT_TITLE, " +
				   "BRANCH_BIC, SUB_BRANCH_SUFFIX, ADDRESS_LINE1, " +
				   "ADDRESS_LINE2, ADDRESS_LINE3, ADDRESS_LINE4, " +
				   "TOWN, COUNTY, POSTCODE, " +
				   "TELEPHONE_NUMBER, FAX_NUMBER, BANK_BIC)";
		
	
	private final String defaultWorkingFolder = "C:/Simonz/_Work/OSP - PP/Industry Bank Data Extract/Working";

	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SortcodesAnalyser app = new SortcodesAnalyser();
		app.go();

		// System.out.println(app.stripIllegalCharacters("Hel'o my f'ne feat'ed Friend!"));
	}

	
	
	public void go(){
		
		
		// load the set of LIVE industry sortcodes
		String workingFolder = defaultWorkingFolder;
		String lfilename = "OSP_USER_NFT.xls";
		String lfiledetails = workingFolder+"/"+lfilename;
			
		Sheet liveSheet = loadExcelSheet(lfiledetails,0);
		List<SortcodesModel> liveModels = extractModelsFromSheet(liveSheet,false);
		liveSheet = null;
		
		// put the sortcodes into a HashTable
		Hashtable<String, ArrayList<SortcodesModel>> liveSortcodesHash;
		liveSortcodesHash = loadHashWithSortcodes(liveModels);
		
		// now load the NEW set of sortcodes
		// workingFolder = "C:/Simonz/_Work/OSP - PP/Industry Bank Data Extract/Working";
		// lfilename = "IndustrySortcodes-working.xls";
		lfilename = "ISCD-Pearl-lite.xls";
		lfiledetails = workingFolder+"/"+lfilename;
			
		Sheet newSheet = loadExcelSheet(lfiledetails,0);
		List<SortcodesModel> newModels = extractModelsFromSheet(newSheet, true);	
		
		// put the sortcodes into a HashTable
		Hashtable<String, ArrayList<SortcodesModel>> newSortcodesHash;
		newSortcodesHash = loadHashWithSortcodes(newModels);
		
		// find the sortcodes that are NOT shared
		ArrayList<String> unmatchedSortcodes = null;
		Enumeration<String> distinctNewSortcodes = newSortcodesHash.keys();
		unmatchedSortcodes = extractUnmatchedSortcodes(liveSortcodesHash,distinctNewSortcodes);
		
		
		// now get the details(models) of the sortcodes that are unmatched
		ArrayList<SortcodesModel> unmatchedModels = extractModelsBySortcode(newSortcodesHash,unmatchedSortcodes);
		
		
		// finally output the models as an insert script
		createLoadScript(unmatchedModels);
		
		
	}
	
	
	
	private void createLoadScript(ArrayList<SortcodesModel> unmatchedModels) {
		
		StringBuffer buf = new StringBuffer();
		
		int countOfSortcodesUsed = 0;
		for (Iterator iterator = unmatchedModels.iterator(); iterator.hasNext();) {
			SortcodesModel sortcodesModel = (SortcodesModel) iterator.next();
			if (sortcodesModel.isBACS()) {
				
				sortcodesModel.formatFields(); // added this at the last minute... I sould go one way or t'other!
				
				buf.append(createInsertStatement(sortcodesModel));
				buf.append(CommonConstants.NEWLINE);
				countOfSortcodesUsed++;
			}
		}
		
//		System.out.println(buf);
//		unmatchedModels.get(unmatchedModels.size()-1).dump();
	
		
		saveLoadScript(buf);
		
		
		log("Total number of sortcode records exported = "+countOfSortcodesUsed);
	}



	private void saveLoadScript(StringBuffer buf) {

		String workingFolder = defaultWorkingFolder;
		String lfilename = "INSERT_INDUSTRY_SORTCODES_INTEGRATION.SQL";
		String lfiledetails = workingFolder+"/"+lfilename;
		
		try {
			FileServer.writeTextFile(lfiledetails, buf.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private String createInsertStatement(SortcodesModel model) {
		
		StringBuffer buf = new StringBuffer(SQL_INSERT_CLAUSE);
		
		buf.append(" VALUES (");
		buf.append(quoteValue(model.bankCodeValue) +", ");
		buf.append(quoteValue(model.sortcode) +", ");
		buf.append(quoteValue(model.shortTitleValue) +", ");
		buf.append(quoteValue(model.branchbicValue) +", ");
		buf.append(quoteValue(model.subBranchSufxValue) +", ");
		buf.append(quoteValue(model.address1) +", ");
		buf.append(quoteValue(model.address2) +", ");
		buf.append(quoteValue(model.address3) +", ");
		buf.append(quoteValue(model.address4) +", ");
		buf.append(quoteValue(model.town) +", ");
		buf.append(quoteValue(model.county) +", ");
		buf.append(quoteValue(model.postcode) +", ");
		buf.append(quoteValue(model.tel) +", ");
		buf.append(quoteValue(model.fax) +", ");
		buf.append(quoteValue(model.bankbicValue) +");");
		
		return buf.toString();
	}



	private String quoteValue(String value) {
		
		// before we quote the text, strip out any illegal characters!
		value = stripIllegalCharacters(value);
		
		return "'"+value+"'";
	}



	private String stripIllegalCharacters(String value) {

		int offset = 0;
		StringBuffer buff = new StringBuffer();
		while (offset < value.length()) {
			char c = value.charAt(offset);
			if (c == '&'){
				buff.append("&'||'");
			} 
			else if (c != '\''){
				buff.append(c);
			} 
			offset++;
		}

		return buff.toString();		

	}



	private ArrayList<SortcodesModel> extractModelsBySortcode(
			Hashtable<String, ArrayList<SortcodesModel>> newSortcodesHash,
			ArrayList<String> unmatchedSortcodes) {

		ArrayList<SortcodesModel> extractedModels = new ArrayList<SortcodesModel>();
		
		
		for (Iterator iterator = unmatchedSortcodes.iterator(); iterator.hasNext();) {
			String sortcode = (String) iterator.next();
			if (newSortcodesHash.containsKey(sortcode)) {
				ArrayList<SortcodesModel> models = newSortcodesHash.get(sortcode);
				extractedModels.addAll(models);
			} else {
				System.err.println("The sortcode details were missing!!!");
			}
			
		}		
		
		log("The full number of extra sortcode records is:"+extractedModels.size());
		
		return extractedModels;
	}



	private ArrayList<String> extractUnmatchedSortcodes(Hashtable<String, ArrayList<SortcodesModel>> sortcodesHash,
			Enumeration<String> distinctNewSortcodes) {
		
		ArrayList<String> unmatchedSortcodes = new ArrayList<String>();
		
		int matchedCount = 0;
		int unmatchedCount = 0;
		
		while (distinctNewSortcodes.hasMoreElements()) {
			String sortcode = (String) distinctNewSortcodes.nextElement();
			if (sortcodesHash.containsKey(sortcode)) {
				matchedCount++;
			} else {
				unmatchedCount++;
				log(">> "+sortcode);
				unmatchedSortcodes.add(sortcode);
			}
		}
		
		log("No of matched sortcodes = "+matchedCount);
		log("No of unmatched sortcodes = "+unmatchedCount);		
		
		return unmatchedSortcodes;
	}



	private Hashtable<String, ArrayList<SortcodesModel>> loadHashWithSortcodes(List<SortcodesModel> models) {
		
		Hashtable<String, ArrayList<SortcodesModel>> hashTable = new Hashtable<String, ArrayList<SortcodesModel>>();
		
		for (Iterator iterator = models.iterator(); iterator.hasNext();) {
			SortcodesModel model = (SortcodesModel) iterator.next();
			ArrayList<SortcodesModel> sameSortcodes = null;
			if (hashTable.containsKey(model.sortcode) == false) {
				sameSortcodes = new ArrayList<SortcodesAnalyser.SortcodesModel>();
				sameSortcodes.add(model);
				hashTable.put(model.sortcode, sameSortcodes);
			} else {
				sameSortcodes = hashTable.get(model.sortcode);
				sameSortcodes.add(model);
			}
			
		}
		
		log("Loaded hastable contains this many SETS of sortcode: "+hashTable.size());
		
		return hashTable;
	}



	protected Sheet loadExcelSheet(String pFilename, int sheetIndex){
		
		String lfilename = Neo9TestingConstants.ROOT_TEST_FOLDER+"/WIPTracker/WIP Tracker Test 1.xls";
		if (pFilename != null){
			lfilename = pFilename;
		}
		
		log("Loading spreadsheet: "+lfilename);
		
		// first load the spreadsheet
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(new File(lfilename));
		} catch (BiffException e) {
			log("Load failed - BiffException - exiting!");
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			log("Load failed - IOException - exiting!");
			e.printStackTrace();
			System.exit(-1);
		} 
		
		// now get the applicable worksheet and return it
		int noOfSheets = workbook.getNumberOfSheets();
		log("Number of sheets in workbook: " + noOfSheets);
		Sheet sheet = workbook.getSheet(sheetIndex); 
		return sheet;
	}
	
	
	
	protected List<SortcodesModel> extractModelsFromSheet(Sheet transactionsSheet, boolean isFullModel){
		
		// this method simply turns the excel data into models 
		
		int noOfRows = transactionsSheet.getRows();
		
		List<SortcodesModel> models = new ArrayList<SortcodesModel>();
		boolean entriesExist = true;
		int rowIndex = 1;
		while (entriesExist) {
			
			Cell sortcodeCell = transactionsSheet.getCell(1,rowIndex); 
			
			if ((sortcodeCell != null)) 
			{
				
				// get the contents as Strings
				String sortcodeValue = sortcodeCell == null ? null : sortcodeCell.getContents(); 

				// transform the contents into models
				if (sortcodeValue != null && sortcodeValue.trim().length() > 0) {
					
					String bacsValue = getCellValueAsString(transactionsSheet.getCell(2, rowIndex));
					
					SortcodesModel model = new SortcodesModel();
					// model.setRowNumber(rowIndex+1);
					model.sortcode = sortcodeValue;
					model.bacs = bacsValue;
					
					if (isFullModel){
					
						String bankbicValue = getCellValueAsString(transactionsSheet.getCell(3, rowIndex));
						String branchbicValue = getCellValueAsString(transactionsSheet.getCell(4, rowIndex));
						String subBranchSufxValue = getCellValueAsString(transactionsSheet.getCell(5, rowIndex));
						String shortTitleValue = getCellValueAsString(transactionsSheet.getCell(6, rowIndex));
						String bankCodeValue = getCellValueAsString(transactionsSheet.getCell(7, rowIndex));
						String address1 = getCellValueAsString(transactionsSheet.getCell(8, rowIndex));
						String address2 = getCellValueAsString(transactionsSheet.getCell(9, rowIndex));
						String address3 = getCellValueAsString(transactionsSheet.getCell(10, rowIndex));
						String address4 = getCellValueAsString(transactionsSheet.getCell(11, rowIndex));
						String town = getCellValueAsString(transactionsSheet.getCell(12, rowIndex));
						String county = getCellValueAsString(transactionsSheet.getCell(13, rowIndex));
						String postcode1 = getCellValueAsString(transactionsSheet.getCell(14, rowIndex));
						String postcode2 = getCellValueAsString(transactionsSheet.getCell(15, rowIndex));
						String tel1 = getCellValueAsString(transactionsSheet.getCell(16, rowIndex));
						String tel2 = getCellValueAsString(transactionsSheet.getCell(17, rowIndex));
						String fax1 = getCellValueAsString(transactionsSheet.getCell(18, rowIndex));
						String fax2 = getCellValueAsString(transactionsSheet.getCell(19, rowIndex));
	
						model.bankbicValue = bankbicValue;
						model.branchbicValue = branchbicValue;
						model.subBranchSufxValue = subBranchSufxValue;
						model.shortTitleValue = shortTitleValue;
						model.bankCodeValue = bankCodeValue;
						model.address1 = address1;
						model.address2 = address2;
						model.address3 = address3;
						model.address4 = address4;
						model.town = town;
						model.county = county;
						model.postcode = formPostcode(postcode1,postcode2);
						model.tel = formTelephoneNo(tel1,tel2);
						model.fax = formFaxNo(fax1,fax2);
					}
					
					models.add(model);	
						
					
				} else {
					if (true){ // is logging on?
						log("Date value is missing - this must be the end of the data set - row index = "+rowIndex);
					}
					entriesExist = false;
				}
				
			}else {
				if (true){ // is logging on?
					log("Date cell is missing - this must be the end of the data set - row index = "+rowIndex);
				}
				entriesExist = false;
			}
			
			
			// increment count & do safety check
			rowIndex++;
			if (rowIndex>noOfRows-1){
				// log("ERROR - Stopped an infinite loop happening!!!");
				entriesExist = false;
			}
		}
		
		log("No of sortcodes = "+rowIndex);
		
		return models;
	}		
	
	private String formFaxNo(String fax1, String fax2) {
		String result = fax1+fax2;
		if (result.length() > 0) {
			result = "0"+result;
		}
		return result.trim();
	}



	private String formTelephoneNo(String tel1, String tel2) {
		String result = tel1+tel2;
		return result.trim();
	}



	private String formPostcode(String postcode1, String postcode2) {
		String result = postcode1;
		if (result.length() > 0) {
			// result = result+" "+postcode2;   <-- current data has no space
			result = result+postcode2;
		}
		return result.trim();
	}



	private String getCellValueAsString(Cell cell) {
		String value = cell == null ? "" : cell.getContents(); 
		return value.trim();
	}

	public class SortcodesModel {
		
		public String sortcode = null;
		public String bacs = null;
		
		String bankbicValue	;
		String branchbicValue	;
		String subBranchSufxValue	;
		String shortTitleValue	;
		String bankCodeValue	;
		String address1	;
		String address2	;
		String address3	;
		String address4	;
		String town	;
		String county	;
		String postcode	;
		String tel	;
		String fax	;
		
		
		
		public boolean isBACS(){
			boolean result = false;
			if (bacs != null && bacs.equalsIgnoreCase("M")) {
				result = true;
			} else {
				log("Filtering out a non-bacs sortcode: "+sortcode);
			}
			return result;
		}
		
		
		public void formatFields(){
			
			// naff - there's sonme of this logic in the main code as well!
			
			if (subBranchSufxValue != null && subBranchSufxValue.trim().length() > 0) {
				Integer numValue = new Integer(subBranchSufxValue);
				subBranchSufxValue = numValue.toString();
			}

			if (bankCodeValue != null && bankCodeValue.trim().length() > 0) {
				Integer numValue = new Integer(bankCodeValue);
				bankCodeValue = numValue.toString();
			}
			
		}
		
		
		public void dump() {
			
			StringBuffer buf = new StringBuffer();

			buf.append("bacs = " + bacs);
			buf.append(CommonConstants.NEWLINE);
			buf.append("sortcode = " + sortcode);
			buf.append(CommonConstants.NEWLINE);
			buf.append("bankbicValue = " + bankbicValue);
			buf.append(CommonConstants.NEWLINE);
			buf.append("branchbicValue = " + branchbicValue);
			buf.append(CommonConstants.NEWLINE);
			buf.append("subBranchSufxValue = " + subBranchSufxValue);
			buf.append(CommonConstants.NEWLINE);
			buf.append("shortTitleValue = " + shortTitleValue);
			buf.append(CommonConstants.NEWLINE);
			buf.append("bankCodeValue = " + bankCodeValue);
			buf.append(CommonConstants.NEWLINE);
			buf.append("address1 = " + address1);
			buf.append(CommonConstants.NEWLINE);
			buf.append("address2 = " + address2);
			buf.append(CommonConstants.NEWLINE);
			buf.append("address3 = " + address3);
			buf.append(CommonConstants.NEWLINE);
			buf.append("address4 = " + address4);
			buf.append(CommonConstants.NEWLINE);
			buf.append("town = " + town);
			buf.append(CommonConstants.NEWLINE);
			buf.append("county = " + county);
			buf.append(CommonConstants.NEWLINE);
			buf.append("postcode = " + postcode);
			buf.append(CommonConstants.NEWLINE);
			buf.append("tel = " + tel);
			buf.append(CommonConstants.NEWLINE);
			buf.append("fax = " + fax);
			
			log(buf.toString());
		}
		
	}
	
}
