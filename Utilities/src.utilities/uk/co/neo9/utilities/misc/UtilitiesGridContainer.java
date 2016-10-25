package uk.co.neo9.utilities.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import uk.co.neo9.utilities.CommonConstants;
import uk.co.neo9.utilities.UtilitiesTextHelper;


/**
 * This class holds data in a grid - so it's good for information that 
 * naturally falls into a 2D, row-column format.
 * 
 * The initial implementation requires one axis to be of a fixed number
 * but the other can be variable. This relates to the input of a List of
 * Lists!
 * 
 */
public class UtilitiesGridContainer {
	
	private static final String GRID_KEY_DELIMITER = "|";

	public boolean outputAxisLabels = true; // not sure about this
	
	private String emptyCellOutput = "";
	
	private static int UNDEFINED = -1;
	
	private Map<String, Object> grid = new Hashtable<String, Object>();
	private int xLimit = UNDEFINED;
	private int yLimit = UNDEFINED;
	private boolean isCSVMarked = false;
	
	private UtilitiesGridContainerCellWrapperFactoryI wrapperFactory = null;
	
	protected IndexedLabelStore xAxisLabelStore = new IndexedLabelStore(); 
	protected IndexedLabelStore yAxisLabelStore = new IndexedLabelStore(); 

	
	
	private UtilitiesGridContainerCellWrapperFactoryI getWrapperFactory() {
		
		if (wrapperFactory == null) {
			//TODO either raise exception or use a default adapter
		}
		
		//TODO setEmptyCellOutput("blank,blank"); <-- this assumes two fields per cell o/p... no assumptions!
		
		return wrapperFactory;
	}


	public void setWrapperFactory(
			UtilitiesGridContainerCellWrapperFactoryI wrapperFactory) {
		this.wrapperFactory = wrapperFactory;
	}


	private boolean validateCellValueUniqueness(){
		
		//TODO realised that this algorithm as it stands requires a cell value to be unique within its column!
		
		return true;
	}
	
	
	
	/**
	 * This method 
	 * 
	 * 
	 * 
	 * @return
	 */
	public String outputGrid() {

// Was wondering about making this CSV specific, or smart enough to determine at runtime
// but we're getting into scope creep!		
//		
//		if (isCSVMarked == false) {
//			throw new RuntimeException("Attempting to output CSV data without CSV models!");
//		}
//		
		StringBuffer buf = new StringBuffer();
		
		
		//first build the x-axis headings
		StringBuffer xAxisLabelsBuf = null;
		xAxisLabelsBuf = buildXAxisLabelsOutputText();
		if (xAxisLabelsBuf != null && xAxisLabelsBuf.length() > 0) {
			buf.append(xAxisLabelsBuf);
			buf.append(CommonConstants.NEWLINE);
		}
		
		int xAxisLength = xAxisLabelStore.getLength();
		int yAxisLength = yAxisLabelStore.getLength();
		for (int i = 0; i < yAxisLength; i++) {

			
			if (outputAxisLabels) {
				buf.append(yAxisLabelStore.getLabel(i)); 
				buf.append(","); //TODO - CSV specific!!!
			}
			for (int j = 0; j < xAxisLength; j++) {
	
				String key = formGridKey(j,i);
				Object val = grid.get(key);
				
//				StringBuffer objValOutput;
//				if (val instanceof ICSVDataObject) {
//					objValOutput = buildCSVOutput((ICSVDataObject)val);
//				} else {
//					objValOutput = buildCSVOutput(val);
//				}
				
				if (val != null){
					String objValOutput = null;
					if (val instanceof UtilitiesGridContainerCellWrapperI) {
						objValOutput = ((UtilitiesGridContainerCellWrapperI)(val)).getOutput();
					} else {
						System.err.println("Not yet supporting other output formats!");
						// objValOutput = buildCSVOutput(val);
					}
					
					buf.append(objValOutput);
					
				} else {
					
					// this is an 'empty' cell 
					//TODO need some plugable approach to outputting the blanks!!
					buf.append(emptyCellOutput);
				}
				buf.append(","); //TODO <-- this is a bit too specific to CSV!!!
			}
			buf.append(CommonConstants.NEWLINE);
		}
		
		
		
		return buf.toString();
	}


	
	private StringBuffer buildXAxisLabelsOutputText() {
		
		
		StringBuffer xAxisLabelsBuf = null;
		if (outputAxisLabels && xAxisLabelStore.isAxisDefined()) {
			xAxisLabelsBuf = new StringBuffer();
			xAxisLabelsBuf.append(","); // first column is the yaxis headings!
			for (Iterator<String> iterator = xAxisLabelStore.getLabels().iterator(); iterator.hasNext();) {
				String label = iterator.next();
				xAxisLabelsBuf.append(label);
				//if (iterator.hasNext()) {
					xAxisLabelsBuf.append(",");
				//}
			}
			
		}
		
		return xAxisLabelsBuf;
	}


	private String formGridKey(int x, int y) {
		String key = x+GRID_KEY_DELIMITER+y;
		return key;
	}
	
	private String formGridKey(String x, String y) {
		String key = x+GRID_KEY_DELIMITER+y;
		return key;
	}	
	
	public void loadGridWithStringsInOneHit(List<List<String>> gridData) {

		int x = -1;
		int y = -1;

		for (Iterator<List<String>> iterator = gridData.iterator(); iterator.hasNext();) {
			List<String> outer = (List<String>) iterator.next();
			y++;
			for (Iterator<String> iterator2 = outer.iterator(); iterator2.hasNext();) {
				String val = (String) iterator2.next();
				System.out.print(val +",");
				
				x++;
				String key = formGridKey(x,y);	
				grid.put(key, val);
				
			}
			xLimit = x;
			x = -1;
			System.out.println();
			
		}
		yLimit = y;
		
		System.out.println("------------------------");
		
	}
	

	
	public void loadGridInOneHit(List<List<Object>> gridData) {

		int x = -1;
		int y = -1;

		for (Iterator<List<Object>> iterator = gridData.iterator(); iterator.hasNext();) {
			List<Object> outer = iterator.next();
			y++;
			for (Iterator<Object> iterator2 = outer.iterator(); iterator2.hasNext();) {
				Object val = iterator2.next();
				
				// GRID-CONTAINER
				
				/*
				 * Not sure if I should be leaving this method pure.
				 * ... for that matter not sure what use this method would???
				 */
				
				if (wrapperFactory != null) {
					val = wrapperFactory.wrapCellModel(val);
				}
				
				// GRID-CONTAINER
				
				x++;
				String key = formGridKey(x,y);	
				grid.put(key, val);
				
				System.out.print(key+"*"+val +",");
				
			}
			xLimit = x;
			x = -1;
			System.out.println();
			
		}
		yLimit = y;
		
		System.out.println("------------------------");
		
	}	
	
	
	public void loadGrid(List<List<Object>> gridData) {

//		int x = -1;
//		int y = -1;
//
//		for (Iterator<List<Object>> iterator = gridData.iterator(); iterator.hasNext();) {
//			List<Object> outer = iterator.next();
//			y++;
//			for (Iterator<Object> iterator2 = outer.iterator(); iterator2.hasNext();) {
//				Object val = iterator2.next();
//				System.out.print(val +",");
//				
//				x++;
//				String key = formGridKey(x,y);	
//				grid.put(key, val);
//				
//			}
//			xLimit = x;
//			x = -1;
//			System.out.println();
//			
//		}
//		yLimit = y;
//		
//		System.out.println("------------------------");
		
		
		
		// work out what kind of data we have
		boolean matchedNoOfElements;
		matchedNoOfElements = isGridDataMatched(gridData);
		
		if (matchedNoOfElements) {
			loadGridInOneHit(gridData); // ahhhh - but what about type
		} else {
			loadUmatchedGridData(gridData); // for WIPTrackerAnalysis, this one is triggered
		}
	}	
	
	
	
	private void loadUmatchedGridData(List<List<Object>> gridData) {

		/**
		 * - first the specific models/objects are wrapped... structure remains the same,
		 * so we have a list of lists, and the sub-lists contain an odd number of models
		 * 
		 * - the wrapping process also collects the 'cellValue' for later use... why?
		 * 
		 * - the cellValues are then de-duped and made into the y-axis columns
		 * 		-> 2 maps - one storing y-index against column name, & the other, vice versa
		 * 
		 */
		
		List<List<UtilitiesGridContainerCellWrapperI>> wrappedGridData;
		wrappedGridData = new ArrayList<List<UtilitiesGridContainerCellWrapperI>>();

		List<String> allXAxisDataSetValues = new ArrayList<String>();
		List<String> allYAxisDataSetValues = new ArrayList<String>();
		for (Iterator<List<Object>> iterator = gridData.iterator(); iterator.hasNext();) {
			
			List<Object> modelLists = (List<Object>) iterator.next();
			List<UtilitiesGridContainerCellWrapperI> wrappedModelsList;
			wrappedModelsList = wrapperFactory.wrapCellModels(modelLists);
			wrappedGridData.add(wrappedModelsList);

			// while we are iterating and have the wrapped model list, get the cell values from it
			extractAxisDataSetValues(wrappedModelsList,allXAxisDataSetValues,allYAxisDataSetValues);
		}
		
		/**
		 * The magic begins!
		 * 
		 * - each model in each inner list, has a cell value... this is used to lookup the Y-axis index
		 * - the x-axis index is simply the index of the current list in the list of lists!
		 * - a key is then formed from the y&x indexes
		 * - the wrapped model is then put in the hash with that key
		 * 
		 * ... so this relies on the data having one fixed axis for the 'rows' (the number of inner lists) and then
		 * a common field that has a finite set of overlapping values, what is strangely named 'CellSetMemberValue'.
		 * This then forms the 'columns'. This way we get our 2D grid with a model sitting at each defined point.
		 * The model can then identified by its grid x-y key and an output generated from the model. The output dose
		 * not have to be fixed... the wrapper can be made to output whatever is required for that instance of the grid.
		 * There can be undefined points as well... so no need for a null wrapper... we just don't operate on that point.
		 */
		
		// now the specific models are wrapped in a generic wrapper,
		// we can start processing them at a generic level
		
		// setup the indexing for the grid
		
		// setup the y-Axis indexing if not already done so
		
		
		if (xAxisLabelStore.isAxisDefined() == false) {
			List<String> uniqueSetValues = UtilitiesTextHelper.deduplicateLines(allXAxisDataSetValues, true);
			setXAxisLabels(uniqueSetValues);
		}		
		
		if (yAxisLabelStore.isAxisDefined() == false) {
			List<String> uniqueSetValues = UtilitiesTextHelper.deduplicateLines(allYAxisDataSetValues, true);
			setYAxisLabels(uniqueSetValues);
		}
		
		// now we can load the grid!
		for (Iterator<List<UtilitiesGridContainerCellWrapperI>> iterator = wrappedGridData.iterator(); iterator.hasNext();) {	
			List<UtilitiesGridContainerCellWrapperI> wrappedModels = iterator.next();

			for (Iterator<UtilitiesGridContainerCellWrapperI> iterator2 = wrappedModels.iterator(); iterator2.hasNext();) {
				UtilitiesGridContainerCellWrapperI wrappedModel = iterator2.next();
				String xAxisValue = wrappedModel.getXAxisValue();
				String yAxisValue = wrappedModel.getYAxisValue();
				// get the grid key for this model
				Integer xAxisIndex = xAxisLabelStore.getIndex(xAxisValue.toUpperCase());
				Integer yAxisIndex = yAxisLabelStore.getIndex(yAxisValue.toUpperCase());
				String gridKey = null;
				if (xAxisIndex != null && yAxisIndex != null) {
					gridKey = formGridKey(xAxisIndex, yAxisIndex);
				} else {
					System.out.println("// TODO raise an exception - didn't get all the indexes!!!! X: "+xAxisIndex+ " Y:"+yAxisIndex);
				}
				
				
				// and finally add the wrapped model into the grid!
				Object existingEntry = grid.get(gridKey);
				if (existingEntry != null) {
					System.out.println("following gridkey has an existing entry! > "+gridKey + " ... so can't put in value");
				} else {

				}
				grid.put(gridKey,wrappedModel);
			}

		}

// SCOOBY		
//		// record the outer limits of the grid
//		xLimit = gridData.size()-1;
//		yLimit = uniqueSetValues.size()-1;

	}





	private void extractAxisDataSetValues(List<UtilitiesGridContainerCellWrapperI> wrappedModelsList,List<String> allXAxisDataSetValues, List<String> allYAxisDataSetValues) {
		
		
		for (Iterator<UtilitiesGridContainerCellWrapperI> iterator = wrappedModelsList.iterator(); iterator.hasNext();) {
			UtilitiesGridContainerCellWrapperI wrappedModel = iterator.next();
			String xValue = wrappedModel.getXAxisValue();
			allXAxisDataSetValues.add(xValue);
			String yValue = wrappedModel.getYAxisValue();
			allYAxisDataSetValues.add(yValue);
		}
		
		return;
	}


	public boolean isGridDataMatched(List<List<Object>> gridData){
		
		final int UNDEFINED = -1;
		int previousNoOfElementsInInnerList = UNDEFINED;
		boolean matchedNoOfElements = true;
		for (Iterator<List<Object>> iterator = gridData.iterator(); (iterator.hasNext() && matchedNoOfElements == true);) {
			List<Object> outer = iterator.next();
			int noOfElementsInInnerList = outer.size();
			if (previousNoOfElementsInInnerList != UNDEFINED) {
				if (previousNoOfElementsInInnerList != noOfElementsInInnerList) {
					matchedNoOfElements = false;
				}
			}
			previousNoOfElementsInInnerList = noOfElementsInInnerList;
		}
		
		return matchedNoOfElements;
	}
	
	
	public String dumpGrid() {
		
		StringBuffer buf = new StringBuffer();
		
		for (int i = 0; i < yAxisLabelStore.getLength(); i++) {
			for (int j = 0; j < xAxisLabelStore.getLength(); j++) {
					
				String key = formGridKey(j,i);	
				Object output = grid.get(key);
				buf.append(key+"-"+output +",");
			}
			buf.append(CommonConstants.NEWLINE);
		}
		
		buf.append("X-Axis: "+xAxisLabelStore.dumpAxis());
		buf.append(CommonConstants.NEWLINE);
		buf.append("Y-Axis: "+yAxisLabelStore.dumpAxis());
		
		return buf.toString();
	}


	//TODO this is an inelegant solution to a pluggable 
	// approach to having a plugabe empty cell output
	public void setEmptyCellOutput(String emptyCellOutput) {
		this.emptyCellOutput = emptyCellOutput;
	}


	public void setXAxisLabels(List<String> axisLabels) {  
		for (Iterator<String> iter = axisLabels.iterator(); iter.hasNext();) {
			String label = (String) iter.next();
			this.xAxisLabelStore.addLabel(label);
		}
	}

	public void setYAxisLabels(List<String> axisLabels) {
		for (Iterator iter = axisLabels.iterator(); iter.hasNext();) {
			String label = (String) iter.next();
			this.yAxisLabelStore.addLabel(label);
		}
	}	

	
	public Set<String> getKeys() {
		return this.grid.keySet();
	}


	public Object getCellValue(String key) {
		return this.grid.get(key);
	}


	public void addCellValue(String key, Object value) {
		
		//TODO - this needs to cleverly know when to use the model wrapper!
		this.grid.put(key,value);
		
	}
	
	public String decodeKey(String keyValue){
		
		// split Key first
		GridKeyModel keyModel = splitKey(keyValue);
		
		// get xAxis
		String xAxisDecode = xAxisLabelStore.getLabel(keyModel.xAxis);
		String yAxisDecode = yAxisLabelStore.getLabel(keyModel.yAxis);
		
		String labelsForKey = formGridKey(xAxisDecode, yAxisDecode);
		
		return labelsForKey;
	}
	
	
	private GridKeyModel splitKey(String keyValue) {
		
		GridKeyModel keyModel = new GridKeyModel();
		
		StringTokenizer tokenizer = new StringTokenizer(keyValue, GRID_KEY_DELIMITER, false);

		String firstToken = tokenizer.nextToken();
		keyModel.xAxis = new Integer(firstToken);
		
		String secondToken = tokenizer.nextToken();
		keyModel.yAxis = new Integer(secondToken);		
		
		return keyModel;
	}

	
	private class IndexedLabelStore {
		
		private boolean axisLabelsDefined = false;
		
		// this is used to create the index values
		private int indexCounter = 0;
		
		// this gives the label for a given axis index
		private Map<Integer,String> axisLabels = new HashMap<Integer,String>(); 
		
		// this gives the axis index a given label
		private Map<String,Integer> axisIndex = new HashMap<String,Integer>();     // <--- need to make this settable by calling application

		
		private void addLabel(String plabel){
			String label = plabel.toUpperCase();
			Integer key = new Integer(indexCounter);
			axisIndex.put(label,key);
			axisLabels.put(key, label);
			indexCounter++;
			axisLabelsDefined = true;
		}
		
		
		public int getLength() {
			int length = axisLabels.size();
			return length;
		}


		public List<String> getLabels() {
			List<String> labels = new ArrayList<String>();
	//		labels.addAll(axisIndex.keySet());
	//		Collections.sort(labels);
			
			
			int length = axisLabels.size();
			for (int i = 0; i < length; i++) {
				String label = axisLabels.get(new Integer(i));
				labels.add(label);
			}
			
			
			return labels;
		}


		private boolean isAxisDefined() {
			return axisLabelsDefined;
		}

		
		private String getLabel(int index){
			String label = axisLabels.get(new Integer(index));
			return label;
		}
		
		
		private Integer getIndex(String label){
			Integer index = axisIndex.get(label);
			return index;
		}
		
		
		
		public String dumpAxis(){
			StringBuilder buff = new StringBuilder();
			int axisLength = getLength();
			for (int i = 0; i < axisLength; i++) {
				String label = getLabel(i);
				buff.append(i+" - "+label);
				buff.append(",");
			}
			return buff.toString();
		}
		
		
	}
	
	
	private class GridKeyModel {
		private int xAxis = -1;
		private int yAxis = -1;
	}



	
}
