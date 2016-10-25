package uk.co.neo9.utilities;

import java.math.BigDecimal;
import java.util.Date;


public abstract class UtilitiesComparatorPlusAdapter implements UtilitiesComparatorPlus{
	
	private final int UNDEFINED_FIELD_TO_USE = -2375;
	
    protected final int BEFORE = -1;
    protected final int EQUAL = 0;
    protected final int AFTER = 1;
    protected final int COMPARE_FIELDS = 999;
	
	private int fieldToUseId = UNDEFINED_FIELD_TO_USE;

	public UtilitiesComparatorPlusAdapter() {
		super();
		this.initialiseFieldToUse();
	}
	
	public UtilitiesComparatorPlusAdapter(int pFieldToUse) {
		super();
		this.setFieldToUseId(pFieldToUse);
	}
	
	public int compare(Object o1, Object o2) {
		int result = EQUAL;
		int fieldToUse = getFieldToUseId();
		result = compare(o1,o2,fieldToUse);
		return result;
	}

	
	abstract public int compare(Object o1, Object o2, int fieldToUse);

	
	public int getFieldToUseId() {
		
		int id = 0;
		if (fieldToUseId != UNDEFINED_FIELD_TO_USE) {
			id = fieldToUseId;
		} else {
			// TODO - should raise an exception here!
		}
		
		return id;
	}


	public void setFieldToUseId(int id) {
		
		if (id != UNDEFINED_FIELD_TO_USE) {
			fieldToUseId = id;
		} else {
			// TODO - should raise an exception here!
		}
		
	}		
	
	// this method must be implemented so that the ComparatorPLUS can be used in 
	// calling code in exactly the same manner as the Java Comparator - it
	// defaults the comparison field
	abstract public void initialiseFieldToUse();
	

	
	
	
	// ==================== the re-usable functional methods ===========================
	
	
	protected int compareFields(BigDecimal field0, BigDecimal field1) {
		int result = 0;
		
		result = compareForNullValues(field0,field1);
		if (result == COMPARE_FIELDS) {
			result = field0.compareTo(field1);
		}
		
		return result;
	}


	protected int compareFields(Date field0, Date field1) {
		int result = 0;
		result = compareForNullValues(field0,field1);
		if (result == COMPARE_FIELDS) {
			result = field0.compareTo(field1);
		}
		return result;
	}


	protected int compareFields(int field0, int field1) {
		
		int result = 0;
		result = field0 - field1;
		return result;
		
	}


	protected int compareStringFieldsCaseInsensitive(String string0, String string1) {
		
		int result = 0;
		
		result = compareForNullValues(string0,string1);
		if (result == COMPARE_FIELDS) {
			result = string0.toLowerCase().compareTo(string1.toLowerCase());
		}
		
		return result;
	}
	
	protected int compareFields(String string0, String string1) {
		
		int result = 0;
		
		result = compareForNullValues(string0,string1);
		if (result == COMPARE_FIELDS) {
			result = string0.compareTo(string1);
		}
		
		return result;
	}

	
	protected int compareForNullValues(Object obj0, Object obj1) {
		
		int result = EQUAL;
		
	    if ((obj0 != null)&& (obj1 != null)) {
	    	result = COMPARE_FIELDS;
		} else if (obj0 == null) {
			if (obj1 == null) {
				result = EQUAL;
			} else {
				result = BEFORE;
			}
		} else if (obj1 == null) {
			if (obj0 == null) {
				result = EQUAL;
			} else {
				result = AFTER;
			}
		}
	    
		return result;
	}
	
}
