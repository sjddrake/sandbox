package uk.co.neo9.apps.accounts.wiptracker;

import java.util.ArrayList;
import java.util.List;

import uk.co.neo9.apps.accounts.WIPTrackerTestHelper;
import junit.framework.TestCase;

public class WIPTrackerCategoryContainerTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	public void test_someting() {
		
		WIPTrackerCategoryContainer catContainer = WIPTrackerTestHelper.createTestCategoryContainer(true);
		
		WIPTrackerTotalsGeneratorComponent generator = new WIPTrackerTotalsGeneratorComponent();
		ArrayList<WIPTrackerTotalsModel> listWithDuplicates = catContainer.getTotalsInCategory();
		List<WIPTrackerTotalsModel> depudeList = generator.dedupeByWhereField(listWithDuplicates);
		
		return;

	}
	
	
	 
	
}
