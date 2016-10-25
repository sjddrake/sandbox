package uk.co.neo9.apps.accounts.budget.model;


/**
 * A Diary Point Id is a simple identifier for a period in time that can be plotted against the calendar.
 * A given date can be mapped to a "Dairy Point" via simple rules.
 * 
 * This version uses Month and Year only.
 * 
 * The diary point identifier rule for this version is simply
 * 
 * Jan 2015 = 2015*100 + 00 + 1 => 201501  (as month is using the Java constant values)
 * Dec 2015 = 2015*100 + 11 + 1 => 201512
 * Feb 2016 = 2016*100 + 01 + 1 => 201602
 * 
 * @author sdrake
 *
 */
public class DiaryPointId {
    
    final static private int YEAR_MULTIPLIER = 100;

	private int year;
	private int month;
	
	
	public DiaryPointId(int year, int month) {
		super();
		this.year = year;
		this.month = month;
	}


	public DiaryPointId() {
		super();
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public int getMonth() {
		return month;
	}


	public void setMonth(int month) {
		this.month = month;
	}


	/**
	 * Calculate the diary point for the month - see header for algorithm details
	 * 
	 * @return
	 */
	public int getMonthPointId() {
		
	    int yearMultiplied = YEAR_MULTIPLIER*year;
	    
		int id = yearMultiplied+month+1;
		
		return id;
	}
	
}

