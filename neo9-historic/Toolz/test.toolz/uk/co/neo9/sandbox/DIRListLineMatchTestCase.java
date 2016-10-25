package uk.co.neo9.sandbox;

import uk.co.neo9.sandbox.DIRListLine;
import uk.co.neo9.sandbox.DIRListLineMatch;
import junit.framework.TestCase;

public class DIRListLineMatchTestCase extends TestCase {

	public DIRListLineMatchTestCase(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testIsMatchedTo_TRUE(){
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)";
		DIRListLine line1 = new DIRListLine(line1Text);

		String line2Text = null;
		line2Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)";
		DIRListLine line2 = new DIRListLine(line2Text);			
		
		int matchToken = 1234567;
		line1.setMatchToken(matchToken); 
		line2.setMatchToken(matchToken); 
		
		
		DIRListLineMatch matchedPair = new DIRListLineMatch(line1,line2);
		
		boolean result = matchedPair.isMatched();
		
		assertEquals(true, result);		
	}

	
	public final void testIsMatchedTo_FALSE(){
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)";
		DIRListLine line1 = new DIRListLine(line1Text);

		String line2Text = null;
		line2Text = "H:\\_ Henrick\\Accept\\blah";
		DIRListLine line2 = new DIRListLine(line2Text);			
		
		int matchToken = 1234567;
		line1.setMatchToken(matchToken); 
		// dont give line 2 a match token
		
		
		DIRListLineMatch matchedPair = new DIRListLineMatch(line1,line2);
		
		boolean result = matchedPair.isMatched();
		
		assertEquals(false, result);		
	}	
}
