package uk.co.neo9.apps.accounts.wiptracker;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import uk.co.neo9.utilities.UtilitiesDateHelper;

public class WIPTrackerTotalsModel {
	
	public static final String FNAME_CATEGORY = "category";
	public static final String FNAME_CATEGORY_CASE_INSENSITIVE = "category (insensitive)";
	public static final String FNAME_DATE = "date";
	public static final String FNAME_AMOUNT = "amount";
	
	public static final int FID_CATEGORY = 10;
	public static final int FID_DATE = 11;
	public static final int FID_AMOUNT = 12;
	public static final int FID_WHERE = 13;
	public static final int FID_CATEGORY_CASE_INSENSITIVE = 22;
	
	private String category;
	private String subCat;
	private Date date;
	private BigDecimal amount;
	private String owner;
	private int rowNumber;
	private String where;
	private String description;
	private boolean extended;
	
	// Oct 2012 - a quick & dirty introduction for expected payments and forecasting
	private String customField1;
	
	private WIPTrackerStatsDetails stats = new WIPTrackerStatsDetails();
	
	
	public WIPTrackerTotalsModel(BigDecimal amount, String category, Date date) {
		super();
		this.amount = amount;
		this.category = category;
		this.date = date;
	}
	
	public WIPTrackerTotalsModel() {
		super();
	}
	
	public static String decodeFID(int fidId){
		
		String feildName = "UNDEFINED_FIELD";
		
		switch (fidId) {
		case FID_AMOUNT: feildName = FNAME_AMOUNT; break;
		case FID_CATEGORY: feildName = FNAME_CATEGORY; break;
		case FID_CATEGORY_CASE_INSENSITIVE: feildName = FNAME_CATEGORY; break;
		case FID_DATE: feildName = FNAME_DATE; break;
		default:
			break;
		}
		
		return feildName;
	}
	
	
	
	public boolean setField(int fidId, Object value){
		
		boolean success = true;
		
		switch (fidId) {
		case FID_AMOUNT: 
			setAmount((BigDecimal) value);
			break;
//		case FID_CATEGORY: feildName = FNAME_CATEGORY; break;
//		case FID_CATEGORY_CASE_INSENSITIVE: feildName = FNAME_CATEGORY; break;
//		case FID_DATE: feildName = FNAME_DATE; break;
		default:
			break;
		}
		
		return success;
	}
	
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}


	public WIPTrackerStatsDetails getStats() {
		return stats;
	}
	
	


	public boolean isCategoryMember(List<String> validCategoryNames) {
		boolean notFoundYet = true;
		for (Iterator<String> iterator = validCategoryNames.iterator(); (iterator.hasNext() && notFoundYet);) {
			String catName = (String) iterator.next();
			if (isCategoryMember(catName)) {
				notFoundYet = false;
			}
		}
		
		boolean isMember;
		if (notFoundYet) {
			isMember = false;
		} else {
			isMember = true;
		}
		return isMember;
	}

	
	public boolean isCategoryMember(String validCategory) {

		boolean isMember = false;
		
		String thisCategory = getCategory();
		if (thisCategory != null && thisCategory.trim().length() > 0) {
			if (thisCategory.equalsIgnoreCase(validCategory)) {
				isMember = true;
			}
		}
		
		return isMember;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getSubCat() {
		return subCat;
	}

	public void setSubCat(String subCat) {
		this.subCat = subCat;
	}

	public boolean isExtended() {
		return extended;
	}

	public void setExtended(boolean extended) {
		this.extended = extended;
	}

	public String getUniqueIdentifier() {
		
		final String DELIMITER = "|";
		
		StringBuilder buff = new StringBuilder();
		buff.append(UtilitiesDateHelper.formatDate(date));
		buff.append(DELIMITER);
		buff.append(amount);
		buff.append(DELIMITER);
		buff.append(where);
		
		return buff.toString();
	}


	public String getCustomField1() {
		return customField1;
	}

	public void setCustomField1(String customField1) {
		this.customField1 = customField1;
	}

}
