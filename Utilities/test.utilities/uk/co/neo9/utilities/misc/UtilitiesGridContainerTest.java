package uk.co.neo9.utilities.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.neo9.utilities.misc.UtilitiesGridComparator.UtilitiesGridComparatorResult;

import junit.framework.TestCase;

public class UtilitiesGridContainerTest extends TestCase {

	
	public void test_NullCellWrapper() {
	
		int count = 4;
		List data = new ArrayList<List<Object>>();
		
		for (int i = 0; i < count; i++) {
			data.add(getNullCells(count));
		}		
		UtilitiesGridContainer grid = new UtilitiesGridContainer();
		grid.loadGridInOneHit(data);
		String output = grid.outputGrid();
		
	//	System.out.println(output);
	}	
	

	public void test_proto_InitialWrapperDev() {
	
	}
	
	
	public void test_proto_CompareGrids_identical() {
		
		UtilitiesGridContainer grid1 = getTestGrid1();
		UtilitiesGridContainer grid2 = getTestGrid1();
		
		UtilitiesGridComparator comparator = new UtilitiesGridComparator();
		
		int result = comparator.compare(grid1, grid2);
		
		assertEquals(0, result);

		List<UtilitiesGridComparatorResult> results = comparator.getResults();
		assertEquals(32, results.size());
		
		comparator.dumpResults();
	}	
	
	
	public void test_proto_CompareGrids_different() {
		
		UtilitiesGridContainer grid1 = getTestGrid1();
		UtilitiesGridContainer grid2 = getTestGrid1();
		
		// make one of them different
		String key = "3|2";
		Object value = new String("different");
		grid2.addCellValue(key,value);
		
		grid2.addCellValue("6|9","mismatch");
		
		UtilitiesGridComparator comparator = new UtilitiesGridComparator();
		
		int result = comparator.compare(grid1, grid2);
		
		assertEquals(-1, result);

		List<UtilitiesGridComparatorResult> results = comparator.getResults();
		assertEquals(33, results.size());
		
		comparator.dumpResults();
	}	
	
	
	public void test_proto_SimpleOutputOfSimpleData() {
		
		UtilitiesGridContainer grid = getTestGrid1();
		
		String output = grid.dumpGrid();
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

