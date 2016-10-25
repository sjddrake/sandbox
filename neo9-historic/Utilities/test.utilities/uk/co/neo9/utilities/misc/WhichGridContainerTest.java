package uk.co.neo9.utilities.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.neo9.utilities.misc.UtilitiesGridComparator.UtilitiesGridComparatorResult;

import junit.framework.TestCase;

public class WhichGridContainerTest extends TestCase {



	
	public void test_loadWhichData() {
		
		final String CN_UK = "United Kingdom";
		final String CN_FRANCE = "France";
		final String CN_GERM = "Germany";
		final String CC_UK = "UK";
		final String CC_FRANCE = "FR";
		final String CC_GERM = "GE";
		
		final String PR_WHICH = "Which?";
		final String PR_GARD = "Gardening";
		final String PR_COMP = "Computing";
		
		final String xAxis = "COUNTRY";
		final String yAxisCode = "CODE";
		final String yAxisTotalColumnn = "TOTAL";
		final String yAxisProductWhich = "Which?";
		final String yAxisProductComputing = "Computing";
		
		GridContainer grid = new GridContainer("PRODUCT","YAXIS","VALUE");
		
		// country vs code
		grid.add(CN_FRANCE, yAxisCode, CC_FRANCE);
		grid.add(CN_GERM, yAxisCode, CC_GERM);
		grid.add(CN_UK, yAxisCode, CC_UK);

		grid.add(CN_FRANCE, yAxisTotalColumnn, 2345L);
		grid.add(CN_GERM, yAxisTotalColumnn, 4234L);
		grid.add(CN_UK, yAxisTotalColumnn, 1978L);
		
		grid.add(CN_FRANCE, yAxisProductComputing, 45L);
		grid.add(CN_GERM, yAxisProductComputing, 34L);
		grid.add(CN_UK, yAxisProductComputing, 78L);

		grid.add(CN_FRANCE, yAxisProductWhich, 23L);
		grid.add(CN_GERM, yAxisProductWhich, 34L);
		grid.add(CN_UK, yAxisProductWhich, 19L);
		
		
		String output = grid.outputGrid();
		System.out.println(output);
		
		grid.add(CN_UK, yAxisProductWhich, 10L);
		
		output = grid.outputGrid();
		System.out.println("---------------------");
		System.out.println(output);
	}
	

	protected UtilitiesGridContainer getTestGrid1() {
		
		List<String> ids1 = getIds("0");
		List<String> ids2 = getIds("1");
		List<String> ids3 = getIds("2");
		List<String> ids4 = getIds("3");
	
		
		List gridData = new ArrayList(); 
		gridData.add(ids1);
		gridData.add(ids2);
		gridData.add(ids3);
		gridData.add(ids4);
		
		UtilitiesGridContainer grid = new UtilitiesGridContainer();

		grid.loadGridInOneHit(gridData);
		
		return grid;
	}
	
	
	protected List<String> getIds(String id) {

		int counter = 0;
		
		List<String> ids = Arrays.asList(
				"weqeqw"+counter+++id,
				"fhgfufhyuf"+counter+++id,
				"sdftruy"+counter+++id,
				"asderwgd"+counter+++id,
				"ouinmgtre"+counter+++id,
				"fhgfufhyuf"+counter+++id,
				"sdftruy"+counter+++id,
				"asderwgd"+counter+++id
				);

		return ids;
	}
	
	protected List<UtilitiesGridContainerNullCell> getNullCells(int count) {

		List<UtilitiesGridContainerNullCell> nulls = new ArrayList<UtilitiesGridContainerNullCell>();
		for (int i = 0; i < count; i++) {
			// nulls.add(new UtilitiesGridContainerNullCell());
			nulls.add(UtilitiesGridContainerNullCell.getInstance());
		}		

		return nulls;
	}
	
	
	public void test_which_reportData_Load(){
		
		//List<Object[]> gridData = new ArrayList<Object[]>(); 
		List<List<Object>>  gridData =  new ArrayList<List<Object>>();
		
    	//gridData.add(new Object[]{"PRODUCT","TAXZONE","TAXRATE","VAT","GROSS"});
		
		List<Object> list = Arrays.asList(new Object[]{"PRODUCT","TAXZONE","TAXRATE","VAT","GROSS"});
		
    	gridData.add(Arrays.asList(new Object[]{"PRODUCT_NAME_WXTRASERVICE","UK","20.00",0L,0L}));
    	gridData.add(Arrays.asList(new Object[]{"NON_SUBS_PRODUCT_CAT_NAME","UK","0.00",0L,300L}));
    	gridData.add(Arrays.asList(new Object[]{"Sub Total","null","null",0L,8640L}));
    	gridData.add(Arrays.asList(new Object[]{"PRODUCT_NAME_WHICH","UK","0.00",0L,8640L}));
    	gridData.add(Arrays.asList(new Object[]{"Non-Sub Total","null","null",0L,300L}));
    	
    	
		UtilitiesGridContainer grid = new UtilitiesGridContainer();

		grid.loadGridInOneHit(gridData);
		
		String output = grid.outputGrid();
		System.out.println(output);
	}
	
}

