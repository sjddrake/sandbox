package uk.co.neo9.apps.accounts.wiptracker;

import uk.co.neo9.utilities.misc.UtilitiesGridContainer;

public class WIPTrackerTotalsGrid extends UtilitiesGridContainer {
	
	public static int MODE_DAILY_CAT_TOTALS = 1;
	public static int MODE_MONTHLY_ANALYSIS = 2;
	
	private int mode = 0;
	
	
	public WIPTrackerTotalsGrid(int gridMode) {
		super();
		initGrid(gridMode);
	}
	
	
	public void initGrid(int gridMode) {
		
		mode = gridMode;
		
		WIPTrackerCellWrapperFactory factory = new WIPTrackerCellWrapperFactory();
		

		if (mode == MODE_DAILY_CAT_TOTALS) {
			outputAxisLabels = true;
			factory.xAxisDataSetID = WIPTrackerCellWrapper.DATA_SET_ID_DATE_DAY_AND_MONTH;
			factory.yAxisDataSetID = WIPTrackerCellWrapper.DATA_SET_ID_CATEGORY;
			
		} else if (mode == MODE_MONTHLY_ANALYSIS) {
			outputAxisLabels = true;
			factory.xAxisDataSetID = WIPTrackerCellWrapper.DATA_SET_ID_DATE_MONTH_AS_MMM;
			factory.yAxisDataSetID = WIPTrackerCellWrapper.DATA_SET_ID_DESC;
		}
		
		setWrapperFactory(factory);
		
	}

	
	@Override
	public String outputGrid() {
		
		
		String output = super.outputGrid();
		return output;
		
	}
	
}
