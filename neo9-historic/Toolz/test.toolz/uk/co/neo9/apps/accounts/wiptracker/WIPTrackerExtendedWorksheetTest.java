package uk.co.neo9.apps.accounts.wiptracker;

import java.util.List;

import uk.co.neo9.test.Neo9TestingConstants;
import junit.framework.TestCase;
import jxl.Sheet;

public class WIPTrackerExtendedWorksheetTest extends TestCase {
	
	WIPTrackerTotalsGeneratorComponent generator;

	public WIPTrackerExtendedWorksheetTest(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
		generator = new WIPTrackerTotalsGeneratorComponent();
		generator.initialise(true);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	public void test_StandardWorksheet() {
		
		String lfilename = Neo9TestingConstants.ROOT_TEST_FOLDER+"/WIPTracker/WIP Tracker Test 1.xls";
		Sheet sheet = loadTestWIPTrackerSheet(lfilename);
		
		int returnedSheetType = generator.determineSheetFormat(sheet);
		
		assertTrue(returnedSheetType != 0);
		assertTrue(returnedSheetType != WIPTrackerConstants.WKSHT_TYPE_UNDEFINED);
		assertTrue(returnedSheetType == WIPTrackerConstants.WKSHT_TYPE_STANDARD);
		
	}
	
	
	public void test_ExtendedWorksheet() {
		
		String lfilename = Neo9TestingConstants.ROOT_TEST_FOLDER+"/WIPTracker/extended/WIP Tracker Extended Test 1.xls";
		Sheet sheet = loadTestWIPTrackerSheet(lfilename);
		
		int returnedSheetType = generator.determineSheetFormat(sheet);
		
		assertTrue(returnedSheetType != 0);
		assertTrue(returnedSheetType != WIPTrackerConstants.WKSHT_TYPE_UNDEFINED);
		assertTrue(returnedSheetType == WIPTrackerConstants.WKSHT_TYPE_EXTENDED);
		
	}
	
	
	
	public void test_extractModelsFromExtendedSheet(){
		
		String lfilename = Neo9TestingConstants.ROOT_TEST_FOLDER+"/WIPTracker/extended/WIP Tracker Extended Test 1.xls";
		Sheet sheet = loadTestWIPTrackerSheet(lfilename);
		
		assertNotNull("WIPTracker worksheet not loaded",sheet);
		
		List<WIPTrackerTotalsModel> models = generator.extractModelsFromSheet(sheet, WIPTrackerConstants.WKSHT_TYPE_EXTENDED);
		
		assertNotNull("WIPTracker models not loaded",sheet);
		int noOfModelsExpected = 84;
		assertEquals(noOfModelsExpected,models.size());
	}	
	

	
	public void test_loadGridFromExtendedSheet(){
		
		String lfilename = Neo9TestingConstants.ROOT_TEST_FOLDER+"/WIPTracker/extended/WIP Tracker Extended Test 1.xls";

		
		WIPTrackerTotalsGrid grid = generator.loadWIPTotalsIntoGrid(lfilename);
		
		assertNotNull("WIPTracker models not loaded",grid);

		// not sure how else to assert on this???
		String export = grid.outputGrid();
		System.out.println(export);
	}		
	
	
	public Sheet loadTestWIPTrackerSheet(String filename){
		
		Sheet sheet = generator.loadWIPTrackerSheet(filename);
		assertNotNull("WIPTracker worksheet not loaded",sheet);
		return sheet;
		
	}
}
