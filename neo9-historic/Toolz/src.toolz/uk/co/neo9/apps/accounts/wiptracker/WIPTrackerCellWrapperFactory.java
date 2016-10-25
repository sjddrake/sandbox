package uk.co.neo9.apps.accounts.wiptracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.neo9.utilities.misc.UtilitiesGridContainerCellWrapperFactoryI;
import uk.co.neo9.utilities.misc.UtilitiesGridContainerCellWrapperI;

public class WIPTrackerCellWrapperFactory implements
		UtilitiesGridContainerCellWrapperFactoryI {

	public int xAxisDataSetID = 0;
	public int yAxisDataSetID = 0;
	
	
	public WIPTrackerCellWrapperFactory() { 
		super();
	}

	
	public UtilitiesGridContainerCellWrapperI wrapCellModel(Object cellModel) {

		UtilitiesGridContainerCellWrapperI wrapper = null;
		
		// check that the input is the correct object
		if ((cellModel instanceof WIPTrackerTotalsModel) == true) {
			
			// setup the wrapper
			WIPTrackerCellWrapper modelWrapper = new WIPTrackerCellWrapper(cellModel);
			modelWrapper.xAxisDataSetID = this.xAxisDataSetID;
			modelWrapper.yAxisDataSetID = this.yAxisDataSetID;
			
			// configure the wrapper
			modelWrapper.includeDescription = false;
			
			// return it
			wrapper = modelWrapper;
			
			
		} else if ((cellModel instanceof UtilitiesGridContainerCellWrapperI)) {	
			 
			wrapper = (UtilitiesGridContainerCellWrapperI)cellModel; // this should be generic if actually needed at all!
			
		} else {
			
			//TODO raise exception
		}
		

		return wrapper;
	}

	// This is generic!!!! Means we could have an abstract adapter 
	public List<UtilitiesGridContainerCellWrapperI> wrapCellModels(List cellModels) {
		List<UtilitiesGridContainerCellWrapperI> wrappers;
		wrappers = new ArrayList<UtilitiesGridContainerCellWrapperI>();
		
		for (Iterator iterator = cellModels.iterator(); iterator.hasNext();) {
			Object model = iterator.next();
			UtilitiesGridContainerCellWrapperI wrapper = wrapCellModel(model);
			wrappers.add(wrapper);
		}
		
		return wrappers;
	}

}
