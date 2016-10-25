package uk.co.neo9.apps.accounts.wiptracker;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import java.util.*;

import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.UtilitiesDateHelper;
import uk.co.neo9.utilities.UtilsBaseObject;

public class WIPTrackerRulesHelper extends UtilsBaseObject {
	
	public static Set<String> categoryLabelSet = null;
	
	static {
		
		Set<String> set = new HashSet<String>();
		set.add(WIPTrackerConstants.CATLABEL_BILLS);
		set.add(WIPTrackerConstants.CATLABEL_BILLS_ANNUAL);
		set.add(WIPTrackerConstants.CATLABEL_BP_SAVING);
		set.add(WIPTrackerConstants.CATLABEL_CAR_MAINTNNS);
		set.add(WIPTrackerConstants.CATLABEL_DIY);
		set.add(WIPTrackerConstants.CATLABEL_ENTS);
		set.add(WIPTrackerConstants.CATLABEL_FOOD);
		set.add(WIPTrackerConstants.CATLABEL_HOLIDAY_SAVING);
		set.add(WIPTrackerConstants.CATLABEL_HOMEWARES);
		set.add(WIPTrackerConstants.CATLABEL_HOUSE_MAINTNNS);
		set.add(WIPTrackerConstants.CATLABEL_KIDS_BIRTHDAY);
		set.add(WIPTrackerConstants.CATLABEL_KIDZ);
		set.add(WIPTrackerConstants.CATLABEL_LOANS);
		set.add(WIPTrackerConstants.CATLABEL_MEDICAL);
		set.add(WIPTrackerConstants.CATLABEL_PETROL);
		set.add(WIPTrackerConstants.CATLABEL_PRESENTS);
		set.add(WIPTrackerConstants.CATLABEL_SCHOOL_PHOTOS);
		set.add(WIPTrackerConstants.CATLABEL_SUNDRIES);
		set.add(WIPTrackerConstants.CATLABEL_TOYS);
		set.add(WIPTrackerConstants.CATLABEL_TRAVEL);
		set.add(WIPTrackerConstants.CATLABEL_UNKNOWN);
		categoryLabelSet = set;
	}	
	
	
	public static List<String> getAllCategoryLabels() {
		ArrayList<String> allCatNames = new ArrayList<String>();
		allCatNames.addAll(categoryLabelSet);
		return allCatNames;
	}
	
	
	public static boolean checkForValidCatLabel(String catName) {
		boolean isValid = false;
		if (catName != null && catName.trim().length() > 0) {
			isValid = categoryLabelSet.contains(catName);
		}
		return isValid;
	}	
	
	public final static int FILTERBY_IGNORED = 1;
	public final static int FILTERBY_INCLUDED_BUT_INVALID = 2;
	
	private final static SimpleDateFormat hashKeyDateFormat = new SimpleDateFormat("dd-MMM");//("dd-MMM-yy");
	private final static SimpleDateFormat dayAndMonthOnlyDateFormat = new SimpleDateFormat("dd-MMM");
	private final static SimpleDateFormat MonthOnlyAsMMMDateFormat = new SimpleDateFormat("MMM");


	
	
	
	protected boolean logWorkingOut = true;
	
	public void log(String message){
		
		if (logWorkingOut){
			super.log(message);
		}
	}
	
	
	
	public boolean isModelIncluded(WIPTrackerTotalsModel model, boolean fixBadData) {
		boolean isIncluded = true;
		
		StringBuffer message = new StringBuffer();
		message.append("Checking row for inclusion - row: "+model.getRowNumber());
		message.append(CommonConstants.NEWLINE);
		
		String owner = model.getOwner();
		if (owner != null){
			owner = owner.trim();
		}
		if ( owner == null || owner.length() == 0) {
			if (fixBadData) {
				message.append("RESETING row - no owner.... setting to 'unknown'");
				message.append(CommonConstants.NEWLINE);
				model.getStats().setUnknownScore();
				model.setOwner("unknown");
				isIncluded = true;	
			}else {
				message.append("ignore row - no owner");
				message.append(CommonConstants.NEWLINE);
				model.getStats().setUnknownScore();
				isIncluded = false;
			}
		} else if (owner.equalsIgnoreCase("unknown") == true) {
			if (fixBadData) {
				message.append("Including row - not a Joint transaction; it belongs to > "+model.getOwner());
				message.append(CommonConstants.NEWLINE);
				model.getStats().setUnknownScore();
				isIncluded = true;
			} else {
				message.append("ignore row - not a Joint transaction; it belongs to > "+model.getOwner());
				message.append(CommonConstants.NEWLINE);
				model.getStats().setUnknownScore();
				isIncluded = false;
			}
		} else if (owner.equalsIgnoreCase("ignore") == true) {
			message.append("ignore row - not a Joint transaction; it belongs to > "+model.getOwner());
			message.append(CommonConstants.NEWLINE);
			model.getStats().setIgnoreScore();
			isIncluded = false;
		} else if (owner.equalsIgnoreCase("joint") == false) {
			message.append("ignore row - not a Joint transaction; it belongs to > "+model.getOwner());
			message.append(CommonConstants.NEWLINE);
			model.getStats().setIgnoreScore();
			isIncluded = false;
		} 

		message.append("... check complete; include: "+isIncluded);
		message.append(CommonConstants.NEWLINE);
		
		model.getStats().setIncluded(isIncluded);
		
		if (isIncluded == false) {
			log(message.toString());
		}
		
		return isIncluded;
	}	
	
	public boolean isModelValid(WIPTrackerTotalsModel model, boolean fixBadData) {
		boolean isValid = true;
		
		StringBuffer message = new StringBuffer();
		message.append("Checking row for validity - row: "+model.getRowNumber());
		message.append(CommonConstants.NEWLINE);
		
		if (model.getAmount() == null) {
			message.append("bad row - no amount");
			message.append(CommonConstants.NEWLINE);
			isValid = false;
		}
		
		if (model.getDate() == null) {
			message.append("bad row - no date");
			message.append(CommonConstants.NEWLINE);
			isValid = false;
		}

		boolean fixCategory = false;
		if (model.getCategory() == null) {
			message.append("bad row - no category");
			message.append(CommonConstants.NEWLINE);
			isValid = false;
			fixCategory = true;
		} else if (model.getCategory().trim().equals("") == true) {
			message.append("bad row - blank category");
			message.append(CommonConstants.NEWLINE);
			isValid = false;
			fixCategory = true;
		}
		
		if (fixCategory) {
			model.setCategory("unknown");
		}
		
		
		if (model.getCategory().equalsIgnoreCase("unknown")) {
			model.getStats().setUnknownScore();
		}
		
		message.append("... check complete; valid: "+isValid);
		message.append(CommonConstants.NEWLINE);
		if (isValid == false) {
			log(message.toString());
		}
		
		model.getStats().setValid(isValid);
		
		return isValid;
	}

	
	
	
	
	public BigDecimal totalModelAmount(List<WIPTrackerTotalsModel> models) {

		BigDecimal total = new BigDecimal(0);
		for (Iterator<WIPTrackerTotalsModel> iter = models.iterator(); iter.hasNext();) {
			WIPTrackerTotalsModel model = (WIPTrackerTotalsModel) iter.next();
			total = total.add(model.getAmount());
		}
		
		return total;
	}
	
	
	public List<WIPTrackerTotalsModel> filterModels(List<WIPTrackerTotalsModel> worksheetAsModels, int filterType) {
		List<WIPTrackerTotalsModel> models = new ArrayList<WIPTrackerTotalsModel>();
		for (Iterator<WIPTrackerTotalsModel> iterator = worksheetAsModels.iterator(); iterator.hasNext();) {
			WIPTrackerTotalsModel model = (WIPTrackerTotalsModel) iterator.next();
			if (checkSatisfiesFilter(model, filterType)) {
				models.add(model);
			}
		}
		return models;
	}
	
	
	public boolean checkSatisfiesFilter(WIPTrackerTotalsModel model, int filterType){
		
		boolean satisfied = false;
		
		switch (filterType) {
		case FILTERBY_IGNORED: satisfied = !model.getStats().isIncluded(); break;
		case FILTERBY_INCLUDED_BUT_INVALID: satisfied = model.getStats().isIncludedButInvalid(); break;
		default:
			break;
		}
		
		return satisfied;
	}
	
	

	public BigDecimal convertValueToBigDecimal(String amountValue) {
		BigDecimal retVal = null;
		
		if (amountValue == null) {
			log("ERROR - null amount");
			System.exit(-1);
		} else {
			
			amountValue = getNumberFromText(amountValue);
			
			try {
				retVal = new BigDecimal(amountValue);
			} catch (Exception e) {
				log("ERROR - unable to read amount: "+amountValue);
				e.printStackTrace();
				System.exit(-1);
			}
			
		}
		
		return retVal;
	}

	
	public WIPTrackerTotalsModel totalUpOneDaysModels(List<WIPTrackerTotalsModel> oneDaysModels) {
		WIPTrackerTotalsModel oneDaysTotalModel = null;
		
		// chances are there's just one entry anyway
		int noOfModels = oneDaysModels.size();
		if (noOfModels == 1) {
			oneDaysTotalModel = (WIPTrackerTotalsModel)oneDaysModels.get(0);
		} else {

			// there's more than one, so total them now
			oneDaysTotalModel = oneDaysModels.get(0);
			
			for (int i = 1; i < noOfModels; i++) {
				WIPTrackerTotalsModel singleEntryModel = oneDaysModels.get(i);
				BigDecimal runningTotal = oneDaysTotalModel.getAmount().add(singleEntryModel.getAmount());
				oneDaysTotalModel.setAmount(runningTotal);	
			}
		}
		
		return oneDaysTotalModel;
	}
	
	
	
//	
//	public static String getNumberFromText(String text){
//
//		boolean stripOutCommas = true; // Scooby - parameterise???
//		
//		// this simply extracts a number from within a string
//
//		StringBuffer number = new StringBuffer();
//		boolean notFinished = true;
//		boolean notStarted = true;
//		boolean notNegativeCheckYet = false;
//		int offset = 0;
//		while (notFinished && offset < text.length()) {
//			char c = text.charAt(offset);
//			if (Character.isDigit(c)){
//				notStarted = false;
//				number.append(c);
//			} else if (c == '-'){
//				notNegativeCheckYet = true;
//				notStarted = false;
//			} else {
//				// allow the loop to continue around non-numeric
//				// characters until we start hitting numerics
//				// - once we've started, stop when get to the 
//				// next non-numeric
//				if (notStarted == false) {
//					
//					if (notNegativeCheckYet){
//						// back up and look for a negative sign
//						boolean dontGiveUp = true;
//						int reverseOffset = offset-1;
//						int howManyStepsBack = 4;
//						while (dontGiveUp && reverseOffset > 0 && howManyStepsBack > 0){
//							char maybeMinus = text.charAt(reverseOffset);
//							if (maybeMinus == '-'){
//								number.append("-");
//								dontGiveUp = false;
//							} else if (maybeMinus == ' '){
//								dontGiveUp = false;
//							}
//							
//							// count the steps & exit
//							howManyStepsBack--;
//							reverseOffset--;
//						}
//						notNegativeCheckYet = false;
//					}
//					
//					// we have started parsing number by now
//					// so include valid punctuation - , .
//					if (c == '.') {
//						number.append(c);
//					}	else if (c == ',') {
//						if (stripOutCommas == false){
//							number.append(c);
//						}
//					} else {
//						// we've found a character that isn't allwoed so 
//						// we must be at the end of the number
//						notFinished = false;
//					}
//				
//				}
//
//			}
//			offset++;
//		}
//
//		// we've allowed 'punctuation' but make sure it
//		// isn't the final character - that's invalid
//		int lastCharIndex = number.length()-1;
//		char c = number.charAt(lastCharIndex);
//		if (Character.isDigit(c) == false){
//			number.deleteCharAt(lastCharIndex);
//		}
//		
//		return number.toString();
//	}	
//	
	


	public static boolean isNegativeNumber(String text){

		boolean isNegativeNumber = false;
		boolean minusSignFound = false;
		boolean notFinished = true;
		boolean notStarted = true;
		boolean numberFound = false;
		int offset = 0;
		int countBetween = 0;
		while (notFinished && offset < text.length()) {
			char c = text.charAt(offset);
			if (c == '-'){
				notStarted = false;
				minusSignFound = true;
			} else {

				if (notStarted == false) {
					
					if (Character.isDigit(c)) {
						numberFound = true;
					}	else if (c == ' ') {
						notFinished = false;
					} else {
						// we've found a character that isn't allwoed so 
						// maybe its currency - leave a gap but
						// dont allow too big a gap between
						if (countBetween == 4) {
							notFinished = false;
						}
					}
				

				}

			}
			offset++;
			countBetween++;
		}

		if (minusSignFound && numberFound) {
			isNegativeNumber = true;
		}
		
		return isNegativeNumber;
	}	
	

	public static String getNumberFromText(String text){

		boolean stripOutCommas = true; // Scooby - parameterise???
		
		
		// this simply extracts a number from within a string
		StringBuffer number = new StringBuffer();
		
		
		// first, determine if its a negative number
		boolean isNegativeNumber = isNegativeNumber(text);
		if (isNegativeNumber) {
			number.append("-");
		}
		

		boolean notFinished = true;
		boolean notStarted = true;
		int offset = 0;
		while (notFinished && offset < text.length()) {
			char c = text.charAt(offset);
			if (Character.isDigit(c)){
				notStarted = false;
				number.append(c);
			} else {
				// allow the loop to continue around non-numeric
				// characters until we start hitting numerics
				// - once we've started, stop when get to the 
				// next non-numeric
				if (notStarted == false) {
					
					// we have started parsing number by now
					// so include valid punctuation - , .
					if (c == '.') {
						number.append(c);
					}	else if (c == ',') {
						if (stripOutCommas == false){
							number.append(c);
						}
					} else {
						// we've found a character that isn't allwoed so 
						// we must be at the end of the number
						notFinished = false;
					}
				
				}

			}
			offset++;
		}

		// we've allowed 'punctuation' but make sure it
		// isn't the final character - that's invalid
		int lastCharIndex = number.length()-1;
		char c = number.charAt(lastCharIndex);
		if (Character.isDigit(c) == false){
			number.deleteCharAt(lastCharIndex);
		}
		
		return number.toString();
	}
	
	public String generateCatTotalHashKey(WIPTrackerTotalsModel catTotalModel) {
		String key = null;
		key = generateCatTotalHashKey(catTotalModel.getCategory(),catTotalModel.getDate());
		return key;
	}
	

	public String generateCatTotalHashKey(String category, Date date) {
		String key = null;
		key = category.toLowerCase()+"|"+hashKeyDateFormat.format(date);
		return key;
	}

	
	public Date convertValueToDate(int day){
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, day);
		Date date = cal.getTime();
		return date;
	}

	
	public Date convertValueToDate(int day, int month){
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.MONTH, month);
		Date date = cal.getTime();
		return date;
	}	
	
	public Date convertValueToDate(String dateValue){
		
		Date date = UtilitiesDateHelper.convertValueToDate(dateValue);
		
		if (date == null) {; 
			log("ERROR - unable to form date object from input: "+dateValue);
			System.exit(-1);
		}
		
		return date;
	}
	
	
	public static String formatDate_DayAndMonth(Date date) {
		String value = null;
		value = dayAndMonthOnlyDateFormat.format(date); 
		return value;
	}
	
	public static String formatDate_Month3Characters(Date date) {
		String value = null;
		value = MonthOnlyAsMMMDateFormat.format(date); 
		return value;
	}	
	
}
