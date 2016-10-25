package uk.co.neo9.sandbox.cameraeye;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import uk.co.neo9.test.Neo9TestingConstants;

import junit.framework.TestCase;

public class PhotoFileArchiveCheckerTestCase extends TestCase {

	public static void main(String[] args) {
	}

	
	public void test_checkFileForDCF_1(){
		
		PhotoFileArchiveChecker app = new PhotoFileArchiveChecker();
		
		boolean actualValue = app.checkFileNameForDCF("DCFdsfdsfdsfds");
		
		assertEquals("the text should match",true,actualValue);
		
	}
	
	public void test_checkFileForDCF_2(){
		
		PhotoFileArchiveChecker app = new PhotoFileArchiveChecker();
		
		boolean actualValue = app.checkFileNameForDCF("dsfdsfdsfds");
		
		assertEquals("the text should not match",false,actualValue);
		
	}
	
	
	public void test_checkFileForDCF_3(){
		
		PhotoFileArchiveChecker app = new PhotoFileArchiveChecker();
		
		boolean actualValue = app.checkFileNameForDCF("dsfdsfdsfdsDCF");
		
		assertEquals("the text should not match",false,actualValue);
		
	}
	
	
	
	public void test_generateText_TrailingNo_Many() {
		
		// put the expected text together
		String[] testData = {"DCF000034.JPG",
							 "DCF000035.JPG",
							 "DCF000036.JPG",
							 "DCF000037.JPG",
							 "DCF000038.JPG",
							 "DCF000039.JPG",
							 "DCF000040.JPG"};
		
		Collection list = new ArrayList();
		Collections.addAll(list,testData);
		
		IncrementedTextModel model = null;
		model = new IncrementedTextModel("DCF",6,34);
		
		PhotoFileArchiveChecker app = new PhotoFileArchiveChecker();
		app.setParams(model);
		app.processFileNames((ArrayList)list);
		

		
//		IncrementedTextGenerator gen = null;
//		gen = new IncrementedTextGenerator("DCF",6,34);
//
//		ArrayList returnedText = new ArrayList();
//		for (int i = 0; i < 7; i++) {
//			String generatedText = gen.generateNext();
//			returnedText.add(generatedText);
//		}
//		String actualResult = mashItems(returnedText);
//
//		assertEquals(expectedResult,actualResult);
//		
	}
	
	
	
	public void test_checkFilenameMatch_match(){
		
		String filename = "DCF000034.JPG";
		String genText = "DCF000034";
		
		PhotoFileArchiveChecker app = new PhotoFileArchiveChecker();
		
		boolean result = app.checkFilenameMatch(filename,genText);
		
		assertEquals("It was supposed to match!",true,result);
		
	}
	
	public void test_checkFilenameMatch_notMatch(){
		
		String filename = "DCF000034.JPG";
		String genText = "scooby";
		
		PhotoFileArchiveChecker app = new PhotoFileArchiveChecker();
		
		boolean result = app.checkFilenameMatch(filename,genText);
		
		assertEquals("It was supposed to match!",false,result);
		
	}
	
	
	public void test_loadDIRList1() {
		
		String fileDetails = Neo9TestingConstants.ROOT_TEST_FOLDER+"photoDIR/"+"photoslist1.txt";
		
		PhotoFileArchiveChecker app = new PhotoFileArchiveChecker();
		
		List filenames = app.loadDIRList(fileDetails);
		
		
		assertNotNull("The file load returned null!",filenames);
		assertTrue("The file load returned null!",(filenames.size() != 0));
		
//		for (Iterator iter = filenames.iterator(); iter.hasNext();) {
//			String element = (String) iter.next();
//			System.out.println(element);
//		}
		
	}
	
	public void test_stripDownFileDetails1() {
		
		String fileDetails = Neo9TestingConstants.ROOT_TEST_FOLDER+"photoDIR/"+"photoslist1.txt";
		
		PhotoFileArchiveChecker app = new PhotoFileArchiveChecker();
		
		List files = app.loadDIRList(fileDetails);
		
		
		assertNotNull("The file load returned null!",files);
		assertTrue("The file load returned an empty list!",(files.size() != 0));
		
		
		List filenames = app.stripDownFileDetails(files);
		
		assertNotNull("The dir dump file processing returned null!",filenames);
		assertTrue("The dir dump file processing returned an empty list!",(filenames.size() != 0));
		
//		for (Iterator iter = filenames.iterator(); iter.hasNext();) {
//			String element = (String) iter.next();
//			System.out.println(element);
//		}
		
	}
	
	
	
	public void test_stripDownFileDetails2() {
		
		String fileDetails = Neo9TestingConstants.ROOT_TEST_FOLDER+"photoDIR/"+"moviesList.txt";
		
		PhotoFileArchiveChecker app = new PhotoFileArchiveChecker();
		
		List files = app.loadDIRList(fileDetails);
		
		
		assertNotNull("The file load returned null!",files);
		assertTrue("The file load returned an empty list!",(files.size() != 0));
		
		
		List filenames = app.stripDownFileDetails(files);
		
		assertNotNull("The dir dump file processing returned null!",filenames);
		assertTrue("The dir dump file processing returned an empty list!",(filenames.size() != 0));
		
		for (Iterator iter = filenames.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			System.out.println(element);
		}
		
	}
	
	
	public void test_go_dirdump() {
		
		String fileDetails = Neo9TestingConstants.ROOT_TEST_FOLDER+"photoDIR/"+"photoslist1.txt";
		
		PhotoFileArchiveChecker app = new PhotoFileArchiveChecker();
		
		app.go_dirdump(fileDetails);
		
		
	}
	
	
}
