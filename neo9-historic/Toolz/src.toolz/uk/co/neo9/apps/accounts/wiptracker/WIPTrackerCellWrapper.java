package uk.co.neo9.apps.accounts.wiptracker;

import java.math.BigDecimal;

import uk.co.neo9.utilities.UtilitiesTextHelper;
import uk.co.neo9.utilities.file.CSVHelper;
import uk.co.neo9.utilities.misc.UtilitiesGridContainerCellWrapperI;

public class WIPTrackerCellWrapper implements UtilitiesGridContainerCellWrapperI {

	public final static int DATA_SET_ID_DATE_DAY_AND_MONTH = 1;
	public final static int DATA_SET_ID_DATE_MONTH_AS_MMM = 2;
	public final static int DATA_SET_ID_DESC = 3;
	public final static int DATA_SET_ID_CATEGORY = 4;

	public int xAxisDataSetID = 0;
	public int yAxisDataSetID = 0;
	
//	private final static String DELIMITER = "+";
	
	private WIPTrackerTotalsModel model = null;
//	private WIPTrackerCSVBuddy csvBuddy = new WIPTrackerCSVBuddy();
	
	
	
	public WIPTrackerCellWrapper(Object cellModel){
		super();
		this.wrapCellModel(cellModel);
	}

	public WIPTrackerCellWrapper(WIPTrackerTotalsModel cellModel){
		super();
		this.wrapCellModel(cellModel);
	}
	
	// FLAGS
	public boolean includeDescription = false;
	
	public boolean wrapCellModel(Object cellModel) {

		// check that the input is the correct object
		if ((cellModel instanceof WIPTrackerTotalsModel) == false) {
			
			//TODO raise exception
		}
		
		model = (WIPTrackerTotalsModel)cellModel;

		return true;
	}

	
	protected String getAxisValue(int dataSetID) {
		
		String value = null;
		
		switch (dataSetID) {
		
		case WIPTrackerCellWrapper.DATA_SET_ID_DATE_DAY_AND_MONTH: 
			value = getAxisValueDateDayAndMonth();
			break;
			
		case WIPTrackerCellWrapper.DATA_SET_ID_DATE_MONTH_AS_MMM: 
			value = getAxisValueDateMonthAsMMM();
			break;
			
		case WIPTrackerCellWrapper.DATA_SET_ID_CATEGORY: 
			value = getAxisValueCategory();
			break;
			
		case WIPTrackerCellWrapper.DATA_SET_ID_DESC: 
			value = getAxisValueDescription();
			break;			
		default:
			break;
		}
		
		return value;
	}	
	
	
	public String getXAxisValue() {
		String value = null;
		value = getAxisValue(this.xAxisDataSetID);
		return value;
	}
	
	
	public String getYAxisValue() {
		String value = null;
		value = getAxisValue(this.yAxisDataSetID);
		return value;
	}
	

	private String getAxisValueCategory() {

		String cellUniqueId = model.getCategory();
		return cellUniqueId;
	}	
	
	
	private String getAxisValueDateDayAndMonth() {

		String cellUniqueId = null;
		if (model.getDate() != null) {
			cellUniqueId = WIPTrackerRulesHelper.formatDate_DayAndMonth(model.getDate()); 
		}
		return cellUniqueId;
	}
	
	
	private String getAxisValueDateMonthAsMMM() {

		String cellUniqueId = null;
		if (model.getDate() != null) {
			cellUniqueId = WIPTrackerRulesHelper.formatDate_Month3Characters(model.getDate()); 
		}
		return cellUniqueId;
	}	
	
	private String getAxisValueDescription() {

		String cellUniqueId = null;
		if (model.getWhere() != null) {
			cellUniqueId = model.getWhere();
		}
		
//		if (model.getDescription() != null) {
//			cellUniqueId = cellUniqueId + DELIMITER + model.getDescription();
//		}
		
		if (cellUniqueId == null) {
			cellUniqueId = model.getDescription();
		}
		
		
		if (cellUniqueId != null) {
			cellUniqueId = cellUniqueId.trim();
		} else {
			cellUniqueId = "UNKNOWN";
		} 
		
		return cellUniqueId;
		
	}
	
	
	
	
	public String getOutput(){
		
		String output = null;
		if (includeDescription) {
			output = getFullOutput();
		} else {
			output = getAmountOnlyOutput();
		}
		return output;
	}
	
	
	private String getAmountOnlyOutput() {
		
		BigDecimal amount = model.getAmount();
		if (amount == null) {
			amount = new BigDecimal(0.0);
		}
		
		String amountAsCurrency = UtilitiesTextHelper.formatGBPCurrency(amount);
		
		String output = CSVHelper.formatData(amountAsCurrency);
		return output;
		
	}
	
	private String getFullOutput() {
		
		String identifier = getXAxisValue();
		String amount = "";
		if (model.getAmount() != null) {
			amount = model.getAmount().toString();
		}
		String[] fields = { identifier,
							amount};
		
		String output = CSVHelper.formatCSVLine(fields);
		return output;
		
	}

	@Override
	public String toString() {
		return this.getOutput();
	}
}
