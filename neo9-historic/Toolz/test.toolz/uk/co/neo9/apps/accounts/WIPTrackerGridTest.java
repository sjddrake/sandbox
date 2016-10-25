package uk.co.neo9.apps.accounts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.neo9.apps.accounts.wiptracker.WIPTrackerCellWrapper;
import uk.co.neo9.apps.accounts.wiptracker.WIPTrackerCellWrapperFactory;
import uk.co.neo9.apps.accounts.wiptracker.WIPTrackerTotalsModel;
import uk.co.neo9.utilities.misc.UtilitiesGridContainer;
import uk.co.neo9.utilities.misc.UtilitiesGridContainerCellWrapperFactoryI;
import uk.co.neo9.utilities.misc.UtilitiesGridContainerCellWrapperI;
import junit.framework.TestCase;

public class WIPTrackerGridTest extends TestCase {

	
	String[] testStrings1 = {"x1111x","x2222x","x3333x"};
	String[] testStrings2 = {"y1111y","y2222y","y3333y"};
	String[] testStrings3 = {"z1111z","z2222z","z3333z"};
	
	public void test_proto_InitialWrapperDev_List() {
		
		List<WIPTrackerTotalsModel> models = getTestModels1();
		StringBuffer controlBuf = getTestModelsComparisonStrings1();
		
		WIPTrackerCellWrapperFactory factory = new WIPTrackerCellWrapperFactory();
		factory.xAxisDataSetID = WIPTrackerCellWrapper.DATA_SET_ID_DATE_DAY_AND_MONTH;
		factory.yAxisDataSetID = WIPTrackerCellWrapper.DATA_SET_ID_CATEGORY;
		List<UtilitiesGridContainerCellWrapperI> wrappedModels = factory.wrapCellModels(models);
		
		StringBuffer resultBuf = new StringBuffer();
		for (Iterator<UtilitiesGridContainerCellWrapperI> iter = wrappedModels.iterator(); iter.hasNext();) {
			UtilitiesGridContainerCellWrapperI element = iter.next();
			String cellValue = element.getXAxisValue();
			System.out.println(cellValue);
			makeComparisonString(resultBuf,cellValue);
		}
		
		
		
		assertEquals(controlBuf.toString(), resultBuf.toString());
		
		// negative check to test the test!
		resultBuf.append("a");
		assertTrue(controlBuf.toString().equals(resultBuf.toString()) == false);
	}
	
	

	public void test_proto_GridTest() {
		
		List listOfModelLists;
		listOfModelLists = getTestModelListOfLists();
		
		WIPTrackerCellWrapperFactory factory = new WIPTrackerCellWrapperFactory();
		factory.xAxisDataSetID = WIPTrackerCellWrapper.DATA_SET_ID_DESC;
		factory.yAxisDataSetID = WIPTrackerCellWrapper.DATA_SET_ID_CATEGORY;
		
		UtilitiesGridContainer grid = new UtilitiesGridContainer();
		grid.setWrapperFactory(factory);
		grid.loadGrid(listOfModelLists);
		
		String output = grid.outputGrid();
		System.out.println(output);
		
		
//		assertEquals(controlBuf.toString(), resultBuf.toString());
//		
//		// negative check to test the test!
//		resultBuf.append("a");
//		assertTrue(controlBuf.toString().equals(resultBuf.toString()) == false);
	}
	
	
	
	private StringBuffer getTestModelsComparisonStrings1() {
		StringBuffer buf = new StringBuffer();
		makeComparisonString(buf,testStrings1[0]);
		makeComparisonString(buf,testStrings1[1]);
		makeComparisonString(buf,testStrings1[2]);
		return buf;
	}

	private StringBuffer getTestModelsComparisonStrings2() {
		StringBuffer buf = new StringBuffer();
		makeComparisonString(buf,testStrings2[0]);
		makeComparisonString(buf,testStrings2[1]);
		makeComparisonString(buf,testStrings2[2]);
		return buf;
	}

	private StringBuffer getTestModelsComparisonStrings3() {
		StringBuffer buf = new StringBuffer();
		makeComparisonString(buf,testStrings3[0]);
		makeComparisonString(buf,testStrings3[1]);
		makeComparisonString(buf,testStrings3[2]);
		return buf;
	}	
	
	public void test_proto_InitialWrapperDev_Single() {
		
		String description = "x1111x";
		
		WIPTrackerTotalsModel model1 = getTestModel(description);
		
		
		WIPTrackerCellWrapperFactory factory = new WIPTrackerCellWrapperFactory();
		factory.xAxisDataSetID = WIPTrackerCellWrapper.DATA_SET_ID_DESC;
		factory.yAxisDataSetID = WIPTrackerCellWrapper.DATA_SET_ID_CATEGORY;
	
		WIPTrackerCellWrapper wrapper = (WIPTrackerCellWrapper)factory.wrapCellModel(model1);
		
		String cellValue = wrapper.getXAxisValue();
		
		assertEquals(description, cellValue);
		
	}

	
	private List<WIPTrackerTotalsModel> getTestModels1() {
		
		WIPTrackerTotalsModel model1 = getTestModel(testStrings1[0]);
		WIPTrackerTotalsModel model2 = getTestModel(testStrings1[1]);
		WIPTrackerTotalsModel model3 = getTestModel(testStrings1[2]);
		
		List<WIPTrackerTotalsModel> models = new ArrayList<WIPTrackerTotalsModel>();
		
		models.add(model1);
		models.add(model2);
		models.add(model3);
		return models;
	}

	private List<WIPTrackerTotalsModel> getTestModels2() {
		
		WIPTrackerTotalsModel model1 = getTestModel(testStrings2[0]);
		WIPTrackerTotalsModel model2 = getTestModel(testStrings2[1]);
		WIPTrackerTotalsModel model3 = getTestModel(testStrings2[2]);
		
		List<WIPTrackerTotalsModel> models = new ArrayList<WIPTrackerTotalsModel>();
		
		models.add(model1);
		models.add(model2);
		models.add(model3);
		return models;
	}	
	
	
	private List<WIPTrackerTotalsModel> getTestModels3() {
		
		WIPTrackerTotalsModel model1 = getTestModel(testStrings3[0]);
		WIPTrackerTotalsModel model2 = getTestModel(testStrings3[1]);
		WIPTrackerTotalsModel model3 = getTestModel(testStrings3[2]);
		
		List<WIPTrackerTotalsModel> models = new ArrayList<WIPTrackerTotalsModel>();
		
		models.add(model1);
		models.add(model2);
		models.add(model3);
		return models;
	}	
	
	private List<List<WIPTrackerTotalsModel>> getTestModelListOfLists() {
		
		List<List<WIPTrackerTotalsModel>> listOfModelLists = new ArrayList<List<WIPTrackerTotalsModel>>();
		
		List<WIPTrackerTotalsModel> models1 = getTestModels1();
		listOfModelLists.add(models1);
		
		List<WIPTrackerTotalsModel> models2 = getTestModels2();
		listOfModelLists.add(models2);
		
		List<WIPTrackerTotalsModel> models3 = getTestModels3();
		listOfModelLists.add(models3);
		
		
		// mess with the data!
		models1.get(1).setDescription("bills");
		models2.get(1).setDescription("bills");
		models3.get(1).setDescription("bills");
		models3.add(getTestModel("food"));
		
		return listOfModelLists;
	}	
	
	private WIPTrackerTotalsModel getTestModel(String description) {
		
		WIPTrackerTotalsModel model = WIPTrackerTestHelper.createTestModel(description);
		
		return model;
	}
	
	private StringBuffer makeComparisonString(StringBuffer buf, String input){
		
		buf.append(input);
		buf.append("<~>");
		return buf;
	}
	
}
