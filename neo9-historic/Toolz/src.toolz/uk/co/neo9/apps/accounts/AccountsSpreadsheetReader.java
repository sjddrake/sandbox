package uk.co.neo9.apps.accounts;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;

import uk.co.neo9.apps.mealplanner.RecipeBO;
import uk.co.neo9.utilities.UtilsBaseObject;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class AccountsSpreadsheetReader extends UtilsBaseObject {

	private final static String PT_DIRECT_DEBIT = "Direct Debit";
	private final static String PT_STANDING_ORDER = "Standing Order";
	
	public static void main(String[] args) {
		
		AccountsSpreadsheetReader app = new AccountsSpreadsheetReader();
		app.loadAccounts(null);
	}

	
	public void loadAccounts(String pFilename){
		
		String lfilename = "D:/test/accounts/Joint Account 2007 Totals.xls";
		if (pFilename != null){
			lfilename = pFilename;
		}
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(new File(lfilename));
		} catch (BiffException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} 
		
		// quick poke
		int noOfSheets = workbook.getNumberOfSheets();
		log("Number of sheets in workbook: " + noOfSheets);
		
		// load in the relevant sheets
		String [] sheetNames = {"Jan"
								,"Feb"
								,"Mar"
								,"Apr"
								,"May"
								,"Jun"
								,"Jul"
								,"Aug"};
		
		ArrayList allDetails = new ArrayList();
		for (int i = 0; i < sheetNames.length; i++) {
			String sheetName = sheetNames[i];
			Sheet lookupSheet = workbook.getSheet(sheetName); 
			ArrayList details = processMonth(lookupSheet);
			allDetails.addAll(details);
			log("Loaded worksheet "+sheetName);			
		}
		
		
		// order the details for easy processing
		AccountDetailsDTOComparator comparator = new AccountDetailsDTOComparator();
		Collections.sort(allDetails,comparator);
		
		
		log("");
		log("================= COMPLETE OUTPUT =================");
		log("");		
		for (Iterator iterator = allDetails.iterator(); iterator.hasNext();) {				
			AccountDetailsDTO detail = (AccountDetailsDTO) iterator.next();
			log(detail.paymentSource+","
					+detail.paymentType+","
					+detail.paymentAmount);
		}		
		
		
		// filter out replciates
		ArrayList filteredDetails = new ArrayList();
		Iterator iter = allDetails.iterator();
		AccountDetailsDTO previousDetail = null;
		if (iter.hasNext()) {
			previousDetail = (AccountDetailsDTO) iter.next(); 
			filteredDetails.add(previousDetail);
		}
		
		while (iter.hasNext()) {
			AccountDetailsDTO thisDetail = (AccountDetailsDTO) iter.next();
			if (comparator.compare(previousDetail,thisDetail)!=0) {
				// this one is different to previous so keep it
				filteredDetails.add(thisDetail);
			}
			previousDetail = thisDetail;
		}

		// spit it out
	
		log("");
		log("================= FILERED OUTPUT =================");
		log("");
//		for (Iterator iterator = allDetails.iterator(); iterator.hasNext();) {				
		for (Iterator iterator = filteredDetails.iterator(); iterator.hasNext();) {
			AccountDetailsDTO filteredDetail = (AccountDetailsDTO) iterator.next();
			log(filteredDetail.paymentSource+","
					+filteredDetail.paymentType+","
					+filteredDetail.paymentAmount);
		}
		
		
		
	}


	
	private ArrayList processMonth(Sheet lookupSheet) {
		
		int noOfRows = lookupSheet.getRows();
		
		ArrayList entries = new ArrayList();
		boolean entriesExist = true; 
		int i = 0; // can change this if required!
		while (entriesExist) {
			
			Cell paymentSourceCell = lookupSheet.getCell(5,i); 
			Cell paymentTypeCell = lookupSheet.getCell(9, i); 
			Cell paymentAmountCell = lookupSheet.getCell(3, i); 

			if ((paymentSourceCell != null)&&(paymentTypeCell != null)&&(paymentAmountCell != null)) {
				
				String paymentSource = paymentSourceCell.getContents(); // recipe name
				String paymentType = paymentTypeCell.getContents(); // recipe status
				String paymentAmount = paymentAmountCell.getContents();
				
				if (paymentType != null && paymentType.trim().length() > 0) {
					if (applicablePaymentType(paymentType)) {
						AccountDetailsDTO details = new AccountDetailsDTO();
						details.paymentSource = paymentSource;
						details.paymentType = paymentType;
						details.paymentAmount = paymentAmount;
						entries.add(details);
						
						// log(paymentSource+","+paymentType+","+paymentAmount);
					}
				} else {
					entriesExist = false;
				}
				
			}else {
				entriesExist = false;
			}
			
			
			// increment count & do safety check
			i++;
			if (i>noOfRows-1){
				//System.out.println("Stopped an infinite loop happening!!!");
				entriesExist = false;
			}
			
		}
		return entries;
	}

	
	
	private boolean applicablePaymentType(String paymentType) {
		boolean isApplicable = false;
		if (PT_DIRECT_DEBIT.equals(paymentType) || PT_STANDING_ORDER.equals(paymentType)){
			isApplicable = true;
		}
		return isApplicable;
	}



	public class AccountDetailsDTO {
		String paymentSource = null;
		String paymentType = null;
		String paymentAmount = null;
	}
	
	
	public class AccountDetailsDTOComparator implements Comparator{

		public int compare(Object o1, Object o2) {
			int result = 0;
			AccountDetailsDTO details1 = (AccountDetailsDTO)o1;
			AccountDetailsDTO details2 = (AccountDetailsDTO)o2;
			
			result = compare(details1,details2,"PaymentSource");
			
			return result;
		}

		
		public int compare(AccountDetailsDTO details1, AccountDetailsDTO details2, String fieldName) {
			int result = 0;

			if (fieldName.equals("PaymentSource")){
				result = details1.paymentSource.compareTo(details2.paymentSource);
			} else if (fieldName.equals("PaymentAmount")){
				result = details1.paymentAmount.compareTo(details2.paymentAmount);
			} else if (fieldName.equals("PaymentType")){
				result = details1.paymentType.compareTo(details2.paymentType);
			} 
			
			return result;
		}		
		
	}
}
