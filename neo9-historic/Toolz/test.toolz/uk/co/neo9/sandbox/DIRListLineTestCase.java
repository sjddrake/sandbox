package uk.co.neo9.sandbox;

import uk.co.neo9.sandbox.DIRListLine;
import junit.framework.TestCase;

public class DIRListLineTestCase extends TestCase {

	public DIRListLineTestCase(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testGetTextToMatch_WITH_TRACK_NO() {
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\02 - Guitar Solo Wolf.mp3";
		DIRListLine line1 = new DIRListLine(line1Text);
		
		// perform test
		String expectedText = "GuitarSoloWolf";
		String result = line1.getTextToMatch();
		
		assertEquals(expectedText, result);
	}

	public final void testIsFile_TRUE() {
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\02 - Guitar Solo Wolf.mp3";
		DIRListLine line1 = new DIRListLine(line1Text);
		
		boolean result = line1.isFile();
		
		assertEquals(true, result);
	}

	public final void testIsFile_FALSE() {
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)";
		DIRListLine line1 = new DIRListLine(line1Text);
		
		boolean result = line1.isMatched();
		
		assertEquals(false, result);
	}
	
	public final void testIsUnknownFile_TXT_TRUE() {
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\list.txt";
		DIRListLine line1 = new DIRListLine(line1Text);
		
		boolean result = line1.isUnknownFile();
		
		assertEquals(true, result);
	}
	
	
	public final void testIsMovieFile_MOV_TRUE() {
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\Kira.mov";
		DIRListLine line1 = new DIRListLine(line1Text);
		
		boolean result = line1.isMovieFile();
		
		assertEquals(true, result);
	}
	
	public final void testIsMusicFile_MP3_TRUE() {
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\02 - Guitar Solo Wolf.mp3";
		DIRListLine line1 = new DIRListLine(line1Text);
		
		boolean result = line1.isMusicFile();
		
		assertEquals(true, result);
	}
	

	public final void testIsMusicFile_MP3_FALSE1() {
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\02 - Guitar Solo Wolf.jpg";
		DIRListLine line1 = new DIRListLine(line1Text);
		
		boolean result = line1.isMusicFile();
		
		assertEquals(false, result);
	}
	
	public final void testIsMusicFile_MP3_FALSE2() {
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\";
		DIRListLine line1 = new DIRListLine(line1Text);
		
		boolean result = line1.isMusicFile();
		
		assertEquals(false, result);
	}
	
	
	public final void testIsMusicFile_MP3_FALSE3() {
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)";
		DIRListLine line1 = new DIRListLine(line1Text);
		
		boolean result = line1.isMusicFile();
		
		assertEquals(false, result);
	}
	
	public final void testIsMatched_TRUE(){
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)";
		DIRListLine line1 = new DIRListLine(line1Text);
		
		line1.setMatchToken(123);
		
		boolean result = line1.isMatched();
		
		assertEquals(true, result);		
	}
	
	
	public final void testIsMatched_FALSE(){
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)";
		DIRListLine line1 = new DIRListLine(line1Text);
		
		// line1.setMatchToken(123); <--- not matching it!
		
		boolean result = line1.isMatched();
		
		assertEquals(false, result);		
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
		
		boolean result = line1.isMatchedTo(line2);
		
		assertEquals(true, result);		
	}	
	
	
	public final void testIsMatchedTo_UNMATCHED(){
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)";
		DIRListLine line1 = new DIRListLine(line1Text);

		String line2Text = null;
		line2Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\03 - Restless And Wild.mp3";
		DIRListLine line2 = new DIRListLine(line2Text);			
		
		
		line1.setMatchToken(1234567); 
		line2.setMatchToken(7654332); 
		
		boolean result = line1.isMatchedTo(line2);
		
		assertEquals(false, result);		
	}		
	
	public final void testIsMatchedTo_NULL_MATCH_LINE1(){
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)";
		DIRListLine line1 = new DIRListLine(line1Text);

		String line2Text = null;
		line2Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\03 - Restless And Wild.mp3";
		DIRListLine line2 = new DIRListLine(line2Text);			
		
		
		// don't give line1 a match token at all
		line2.setMatchToken(7654332); 
		
		boolean result = line1.isMatchedTo(line2);
		
		assertEquals(false, result);		
	}	
	
	public final void testIsMatchedTo_NULL_MATCH_LINE2(){
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)";
		DIRListLine line1 = new DIRListLine(line1Text);

		String line2Text = null;
		line2Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\03 - Restless And Wild.mp3";
		DIRListLine line2 = new DIRListLine(line2Text);			
		
		line1.setMatchToken(7654332); 
		// don't give line2 a match token at all
		
		
		boolean result = line1.isMatchedTo(line2);
		
		assertEquals(false, result);		
	}	
	
	
	public final void testIsMatchedTo_NULL_MATCH_BOTH(){
		// setup the test data
		String line1Text = null;
		line1Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)";
		DIRListLine line1 = new DIRListLine(line1Text);

		String line2Text = null;
		line2Text = "H:\\_ Henrick\\Accept\\Staying A Life (disk 2)\\03 - Restless And Wild.mp3";
		DIRListLine line2 = new DIRListLine(line2Text);			
		 
		// don't give either line a match token at all
		
		
		boolean result = line1.isMatchedTo(line2);
		
		assertEquals(false, result);		
	}	
}
