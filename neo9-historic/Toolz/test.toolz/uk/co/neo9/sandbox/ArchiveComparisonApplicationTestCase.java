package uk.co.neo9.sandbox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import uk.co.neo9.sandbox.ArchiveComparisonApplication;

import junit.framework.TestCase;

public class ArchiveComparisonApplicationTestCase extends TestCase {

	public ArchiveComparisonApplicationTestCase(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	private void outputTestHeader(String testMethodName) {
		System.out.println("");
		System.out.println("----------- "+ testMethodName +"------------");
		System.out.println("");
	}

	
	
	public final void testCompareIdenticalLists() {
		
		final String TEST_METHOD_NAME = "testCompareIdenticalLists";
		outputTestHeader(TEST_METHOD_NAME);
		
		Vector lineList1 = DIRListLineObjectMother.getDIRList_Line1Line2();
		Vector lineList2 = DIRListLineObjectMother.getDIRList_Line1Line2();
		List expectedResults = new ArrayList();
		expectedResults =  DIRListLineObjectMother.getDIRListTextToMatch_Line1Line2();
		
		ArchiveComparisonApplication application = new ArchiveComparisonApplication();
		application.compareLists(lineList1, lineList2);
		
		
		// TODO should do an assert here!!!
		List textToMatchResults = application.store.exportMatchedTextToMatch();
		
		assertEquals(expectedResults.size(),textToMatchResults.size());
		
		for (int i = 0; i < expectedResults.size(); i++) {
			String expectedTextToMatch = (String)expectedResults.get(i);
			String resultingTextToMatch = (String)textToMatchResults.get(i);
			assertEquals(expectedTextToMatch,resultingTextToMatch);
			System.out.println(resultingTextToMatch);
		}
	
		return;
	}


	
	public final void testCompareNonIdenticalLists() {
		
		final String TEST_METHOD_NAME = "testCompareNonIdenticalLists";
		outputTestHeader(TEST_METHOD_NAME);
		
		Vector lineList1 = DIRListLineObjectMother.getDIRList_Line1Line2();
		Vector lineList2 = DIRListLineObjectMother.getDIRList_Line2Line3();
		
		List expectedMatches = new ArrayList();
		expectedMatches.add(DIRListLineObjectMother.getLine2TextToMatch());
		
		List expectedMismatches = new ArrayList();
		expectedMismatches.add(DIRListLineObjectMother.getLine1TextToMatch());
		expectedMismatches.add(DIRListLineObjectMother.getLine3TextToMatch());
		
		ArchiveComparisonApplication application = new ArchiveComparisonApplication();
		application.compareLists(lineList1, lineList2);
		
		// check the results
		List matchedResults = application.store.exportMatchedTextToMatch();
		assertEquals(expectedMatches.size(),matchedResults.size());
		
		System.out.println("--------- MATCHES -----------");
		for (int i = 0; i < expectedMatches.size(); i++) {
			String expectedTextToMatch = (String)expectedMatches.get(i);
			String resultingTextToMatch = (String)matchedResults.get(i);
			assertEquals(expectedTextToMatch,resultingTextToMatch);
			System.out.println(resultingTextToMatch);
		}
	
		List mismatchedResults = application.store.exportMismatchedTextToMatch();
		assertEquals(expectedMismatches.size(),mismatchedResults.size());
		
		System.out.println("--------- UNMATCHED -----------");
		for (int i = 0; i < expectedMismatches.size(); i++) {
			String expectedTextToMatch = (String)expectedMismatches.get(i);
			String resultingTextToMatch = (String)mismatchedResults.get(i);
			assertEquals(expectedTextToMatch,resultingTextToMatch);
			System.out.println(resultingTextToMatch);
		}		
		
		return;
	}


}
