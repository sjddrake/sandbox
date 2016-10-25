package uk.co.neo9.sandbox;

import uk.co.neo9.sandbox.DIRListLine;
import uk.co.neo9.sandbox.DIRListLineComparator;
import junit.framework.TestCase;

public class DIRListLineComparatorTestCase extends TestCase {

	public DIRListLineComparatorTestCase(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

// ==========================================================
	
	public final void testCompare_SIMPLE_EQUALITY() {
		
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\03 - Restless And Wild.mp3";
		DIRListLine line1 = new DIRListLine(line1Text);
		
		String line2Text = null;
		line2Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\03 - Restless And Wild.mp3";
		DIRListLine line2 = new DIRListLine(line2Text);	
		
		
		// setup the comparator
		// DIRListLineComparator comparator = new DIRListLineComparator(); // use default setup in this test
		DIRListLineComparator comparator = new DIRListLineComparator(DIRListLine.FIELD_ID_TEXT_TO_MATCH);
		
		// compare the two for equality
		
		int result = comparator.compare(line1, line2);
		
		assertEquals(0, result);
	}
	
	
	public final void testCompare_SIMPLE_LESSER() {
		
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\02 - Guitar Solo Wolf.mp3";
		DIRListLine line1 = new DIRListLine(line1Text);
		
		String line2Text = null;
		line2Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\03 - Restless And Wild.mp3";
		DIRListLine line2 = new DIRListLine(line2Text);	
		
		
		// setup the comparator
		// DIRListLineComparator comparator = new DIRListLineComparator(); // use default setup in this test
		DIRListLineComparator comparator = new DIRListLineComparator(DIRListLine.FIELD_ID_TEXT_TO_MATCH);
		
		// compare the two for equality
		
		int result = comparator.compare(line1, line2);
		
		assertEquals(-1, result);
	}
	
	
	public final void testCompare_SIMPLE_GREATER() {
		
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\04 - Son Of A Bitch.mp3";
		DIRListLine line1 = new DIRListLine(line1Text);
		
		String line2Text = null;
		line2Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\03 - Restless And Wild.mp3";
		DIRListLine line2 = new DIRListLine(line2Text);	
		
		
		// setup the comparator
		// DIRListLineComparator comparator = new DIRListLineComparator(); // use default setup in this test
		DIRListLineComparator comparator = new DIRListLineComparator(DIRListLine.FIELD_ID_TEXT_TO_MATCH);
		
		// compare the two for equality
		
		int result = comparator.compare(line1, line2);
		
		assertEquals(1, result);
	}
}
