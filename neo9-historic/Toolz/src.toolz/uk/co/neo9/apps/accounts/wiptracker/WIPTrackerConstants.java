package uk.co.neo9.apps.accounts.wiptracker;

public interface WIPTrackerConstants {
	
	public final static int WKSHT_TYPE_UNDEFINED = -1;
	public final static int WKSHT_TYPE_STANDARD = 1;
	public final static int WKSHT_TYPE_EXTENDED = 2;
	
	public final static String HDRMASH_STANDARD = "/date//amount/where/method/description/category/Owner/Paid///////////";
	public final static String HDRMASH_EXTENDED = "/date//amount/where/method/description/category/subcat/Owner/Paid//////////";

	// this is set is based on the headings in the MAIN TRACKER
	public final static String CATLABEL_BILLS = "bills";
	public final static String CATLABEL_LOANS = "loans";
	public final static String CATLABEL_FOOD = "food";
	public final static String CATLABEL_HOMEWARES = "homewares";
	public final static String CATLABEL_PETROL = "petrol";
	public final static String CATLABEL_SUNDRIES = "misc / sundries / cash";
	public final static String CATLABEL_TRAVEL = "travel";
	public final static String CATLABEL_HOUSE_MAINTNNS = "house maintenance";
	public final static String CATLABEL_CAR_MAINTNNS = "car maintenance";
	public final static String CATLABEL_BILLS_ANNUAL = "bills (anually)"; 
	public final static String CATLABEL_MEDICAL = "medical";
	public final static String CATLABEL_DIY = "DIY";
	public final static String CATLABEL_ENTS = "entertainments"; 
	public final static String CATLABEL_TOYS = "toys";
	public final static String CATLABEL_PRESENTS = "presents";
	public final static String CATLABEL_SCHOOL_PHOTOS = "school photos";
	public final static String CATLABEL_KIDZ = "kidz";
	public final static String CATLABEL_KIDS_BIRTHDAY = "kids birthday";
	public final static String CATLABEL_BP_SAVING = "Big Purchase Saving";
	public final static String CATLABEL_HOLIDAY_SAVING = "Holiday Saving";	
	public final static String CATLABEL_UNKNOWN = "Unknown";
	public final static String CATLABEL_PETS = "Pets";
	
	
	// this set is the category value data set (as assigned to transactions in the monthly total spready)
	public final static String CATVALUE_BILLS = "bills";
	public final static String CATVALUE_LOANS = "loans";
	public final static String CATVALUE_FOOD = "Food";
	public final static String CATVALUE_HOMEWARES = "homewares";
	public final static String CATVALUE_PETROL = "petrol";
	public final static String CATVALUE_SUNDRIES = "sundries";
	public final static String CATVALUE_TRAVEL = "travel";
	public final static String CATVALUE_HOUSE_MAINTNNS = "house maintnns";
	public final static String CATVALUE_CAR_MAINTNNS = "car maintnns";
	public final static String CATVALUE_BILLS_ANNUAL = "bills (anually)";
	public final static String CATVALUE_MEDICAL = "medical";
	public final static String CATVALUE_DIY = "DIY";
	public final static String CATVALUE_ENTS = "ents";
	public final static String CATVALUE_TOYS = "toys";
	public final static String CATVALUE_PRESENTS = "presents";
	public final static String CATVALUE_SCHOOL_PHOTOS = "school photos";
	public final static String CATVALUE_KIDZ = "kidz";
	public final static String CATVALUE_KIDS_BIRTHDAY = "kids birthday";
	public final static String CATVALUE_BP_SAVING = "BP Saving";
	public final static String CATVALUE_HOLIDAY_SAVING = "Holiday Saving";	
	public final static String CATVALUE_UNKNOWN = "unknown";
	public final static String CATVALUE_PETS = "Pets";
}
