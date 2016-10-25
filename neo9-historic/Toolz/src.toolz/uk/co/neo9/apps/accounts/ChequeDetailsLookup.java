package uk.co.neo9.apps.accounts;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ChequeDetailsLookup {

	private static Map<String,String> chequesLookup = new HashMap<String,String>();

	private static String chequesLookupFileName = null; 
	private static boolean isLoaded = false;
	
	public static String chequeLookup(String chequeNo) {
		
		if (isLoaded == false) {
			loadChequeLookup();
		}
		
		String chequeDescription = chequesLookup.get(chequeNo);

		return chequeDescription;
	}
	
	

	public static void loadChequeLookup(String fileDetails) {
		setAccountsLookupFileName(fileDetails);
		loadChequeLookup();
	}
	
	
	public static void loadChequeLookup() {
		
//		// load in the accounts lookup data
//		List<String> accounts = readAccountsDataFile();
//		for (Iterator<String> iter = accounts.iterator(); iter.hasNext();) {
//			String data = (String) iter.next();
//			int delimiter = data.indexOf(',');
//			if (delimiter != -1) {
//				String chequeNo = data.substring(0,delimiter);
//				String description = data.substring(delimiter+1);
//				chequesLookup.put(chequeNo,description);
//			}
//		}
		
		readChequesDataFile();
		isLoaded = true;
		
	}
	
	
	private static void readChequesDataFile() {

		if (chequesLookupFileName == null) {
			throw new IllegalArgumentException("ChequeDetailsLookup - no filename defined!");
		} else {
			
			Workbook workbook = loadWorkbook(chequesLookupFileName);
			
			Sheet sheet = workbook.getSheet(0); 
			convertWorksheetToModels(sheet);
			
		}

	}

	protected static void convertWorksheetToModels(Sheet sheet) {

		int noOfRows = sheet.getRows();
		boolean entriesExist = true;
		int rowIndex = 180;
		while (entriesExist) {
			
			Cell chequeNoCell = sheet.getCell(1,rowIndex); 
			Cell descriptionCell = sheet.getCell(4, rowIndex); 

			if (chequeNoCell != null && descriptionCell != null) {
				entriesExist = true;
			} else {
				entriesExist = false;
			}
			
			if (entriesExist) {
				String chequeNoValue = chequeNoCell == null ? null : chequeNoCell.getContents(); 
				String descriptionValue = descriptionCell == null ? null : descriptionCell.getContents(); 
				
				if (chequeNoValue != null) {
					if (descriptionValue != null) {
						chequesLookup.put(chequeNoValue, descriptionValue);
					}
				} else {
					entriesExist = false;
				}
			}
			
			rowIndex++;
			if (rowIndex == noOfRows) {
				entriesExist = false;
			}
		}
		
	}



	protected static Workbook loadWorkbook(String pFilename){
		
		log("Loading spreadsheet: "+pFilename);
		
		// first load the spreadsheet
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(new File(pFilename));
		} catch (BiffException e) {
			log("Load failed - BiffException - exiting!");
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			log("Load failed - IOException - exiting!");
			e.printStackTrace();
			System.exit(-1);
		} 
		
		
		int noOfSheets = workbook.getNumberOfSheets();
		log("Number of sheets in workbook: " + noOfSheets);
		
		return workbook;
	}		
	
	
	public static void log(String message){
		System.out.println(message);
	}

	public static String getAccountsLookupFileName() {
		return chequesLookupFileName;
	}


	public static void setAccountsLookupFileName(String fileName) {
		chequesLookupFileName = fileName;
	}
}
