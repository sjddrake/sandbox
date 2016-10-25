package uk.co.neo9.apps.accounts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import uk.co.neo9.apps.accounts.wiptracker.WIPTrackerRulesHelper;
import uk.co.neo9.apps.accounts.wiptracker.WIPTrackerTotalsModel;

public class WIPTrackerRulesHelperTest extends TestCase {
	
	WIPTrackerRulesHelper generator = new WIPTrackerRulesHelper();

	public static void main(String[] args) {
		junit.textui.TestRunner.run(WIPTrackerRulesHelperTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	
	public void test_isNegativeNumber_INVALID_1(){
		
		String input = "-XXXX4";
		boolean output = generator.isNegativeNumber(input);
		
		assertFalse(output);
	}	
	
	public void test_isNegativeNumber_INVALID_2(){
		
		String input = "- 4";
		boolean output = generator.isNegativeNumber(input);
		
		assertFalse(output);
	}	
	
	public void test_isNegativeNumber_INVALID_3(){
		
		String input = "- -4";
		boolean output = generator.isNegativeNumber(input);
		
		assertFalse(output);
	}
	
	public void test_isNegativeNumber_USD(){
		
		String input = "-USD4";
		boolean output = generator.isNegativeNumber(input);
		
		assertTrue(output);
		
	}
	
	public void test_isNegativeNumber_Simple1(){
		
		String input = "-4";
		boolean output = generator.isNegativeNumber(input);
		
		assertTrue(output);
		
	}
	
	public void test_isNegativeNumber_Simple2(){
		
		String input = "-44";
		boolean output = generator.isNegativeNumber(input);
		
		assertTrue(output);
		
	}	
	
	public void test_isNegativeNumber_Pound1(){
		
		String input = "-£4";
		boolean output = generator.isNegativeNumber(input);
		
		assertTrue(output);
		
	}
	
	public void test_isNegativeNumber_Pound2(){
		
		String input = "-£44";
		boolean output = generator.isNegativeNumber(input);
		
		assertTrue(output);
		
	}	

	public void test_isNegativeNumber_Pound3(){
		
		String input = "-£44.00";
		boolean output = generator.isNegativeNumber(input);
		
		assertTrue(output);
		
	}	
	

	public void test_isNegativeNumber_Pound1_REVERSED(){
		
		String input = "£-4";
		boolean output = generator.isNegativeNumber(input);
		
		assertTrue(output);
		
	}
	
	public void test_isNegativeNumber_Pound2_REVERSED(){
		
		String input = "£-44";
		boolean output = generator.isNegativeNumber(input);
		
		assertTrue(output);
		
	}	

	public void test_isNegativeNumber_Pound3_REVERSED(){
		
		String input = "£-44.00";
		boolean output = generator.isNegativeNumber(input);
		
		assertTrue(output);
		
	}	

	public void test_isNegativeNumber_FALSE(){
		
		String input = "44";
		boolean output = generator.isNegativeNumber(input);
		
		assertTrue(output == false);
		
	}
	
	
//	public void test_getDatesInCurrentPeriod1(){
//		
//		List dates = generator.getDatesInCurrentPeriod(new Date());
//		assertNotNull("List of dates returned was null.", dates);
//		
//		//ScoobyDo - this is NOT a proper test!!!!
//		for (Iterator iter = dates.iterator(); iter.hasNext();) {
//			Date date = (Date) iter.next();
//			System.out.println(date);
//		}
//		
//	}
	
	
	
	public void test_getNumberFromText_Pound(){
	
		String input = "£4.00";
		String output = generator.getNumberFromText(input);
		String expectedValue = "4.00";
		
		assertEquals(expectedValue,output);
		
	}
	
	public void test_getNumberFromText_Pound_Negative1(){
		
		String input = "-£4.00";
		String output = generator.getNumberFromText(input);
		String expectedValue = "-4.00";
		
		assertEquals(expectedValue,output);
		
	}
	
	public void test_getNumberFromText_Pound_Negative2(){
		
		String input = "£-4.00";
		String output = generator.getNumberFromText(input);
		String expectedValue = "-4.00";
		
		assertEquals(expectedValue,output);
		
	}
	
	public void test_getNumberFromText_Simple1(){
		
		String input = "44";
		String output = generator.getNumberFromText(input);
		String expectedValue = "44";
		
		assertEquals(expectedValue,output);
		
	}
	
	public void test_getNumberFromText_Simple1_Negative(){
		
		String input = "-44";
		String output = generator.getNumberFromText(input);
		String expectedValue = "-44";
		
		assertEquals(expectedValue,output);
		
	}
	
	public void test_getNumberFromText_Simple2(){
		
		String input = "4";
		String output = generator.getNumberFromText(input);
		String expectedValue = "4";
		
		assertEquals(expectedValue,output);
		
	}
	
	public void test_getNumberFromText_Thousands(){
		
		String input = "4,444.44";
		String output = generator.getNumberFromText(input);
		String expectedValue = "4444.44";
		
		assertEquals(expectedValue,output);
		
	}

	
	public void test_getNumberFromText_Buried_Negative(){
		
		String input = "Some text with a X-1234TY random number in it";
		String output = generator.getNumberFromText(input);
		String expectedValue = "-1234";
		
		assertEquals(expectedValue,output);
		
	}
	

	
	public void test_getNumberFromText_Buried(){
		
		String input = "Some text with a X1234TY random number in it";
		String output = generator.getNumberFromText(input);
		String expectedValue = "1234";
		
		assertEquals(expectedValue,output);
		
	}
	
	public void test_convertValueToDate(){
		
		String input = "01/01/2010";
		Date output = generator.convertValueToDate(input);
		Date expectedValue = new Date("01-Jan-2010");
		
		assertEquals(expectedValue,output);
	}
	


	
//	
//	
//	public void test_produceDailyTotals1(){
//		
//		List modelsToTotal = produceDailyTotalsInput1();
//		List totalledResults = generator.produceDailyTotals(modelsToTotal);
//		
//		// ============== start checking the results ==============
//		assertNotNull("Returned totalled items was null",totalledResults);
//		assertTrue("Return list was not empty!",totalledResults.size() != 0);
//		
//		List expectedResultModels = produceDailyTotalsResultModels1();
//		int noOfReturnItemsExpected = expectedResultModels.size();
//		
//		
//		String assertionMessage = "Expected no of returned totals was "+noOfReturnItemsExpected
//		  						  +" but got back: "+expectedResultModels.size();
//		assertTrue(assertionMessage,totalledResults.size() == noOfReturnItemsExpected);		
//
//		
//		String returnedOutput = "";
//		for (Iterator iter = totalledResults.iterator(); iter.hasNext();) {
//			WIPTrackerTotalsModel categoryTotal = (WIPTrackerTotalsModel) iter.next();
//			String mashed = mashModel(categoryTotal);
//			returnedOutput = returnedOutput.concat(mashed);
//		}
//
//		
//		String expectedOutput = "";
//		for (Iterator iter = expectedResultModels.iterator(); iter.hasNext();) {
//			WIPTrackerTotalsModel categoryTotal = (WIPTrackerTotalsModel) iter.next();
//			String mashed = mashModel(categoryTotal);
//			expectedOutput = expectedOutput.concat(mashed);
//		}
//		
//		assertEquals("The mashed return data should match the expected mash",
//					expectedOutput,
//					returnedOutput);
//
//		
//	}
//	
//	
//	
//public void test_produceDailyTotals2(){
//		
//		List modelsToTotal = produceDailyTotalsInput2();
//		List totalledResults = generator.produceDailyTotals(modelsToTotal);
//		
//		// ============== start checking the results ==============
//		assertNotNull("Returned totalled items was null",totalledResults);
//		assertTrue("Return list was not empty!",totalledResults.size() != 0);
//		
//		List expectedResultModels = produceDailyTotalsResultModels2();
//		int noOfReturnItemsExpected = expectedResultModels.size();
//		
//		
//		String assertionMessage = "Expected no of returned totals was "+noOfReturnItemsExpected
//		  						  +" but got back: "+expectedResultModels.size();
//		assertTrue(assertionMessage,totalledResults.size() == noOfReturnItemsExpected);		
//
//		
//		String returnedOutput = "";
//		for (Iterator iter = totalledResults.iterator(); iter.hasNext();) {
//			WIPTrackerTotalsModel categoryTotal = (WIPTrackerTotalsModel) iter.next();
//			String mashed = mashModel(categoryTotal);
//			returnedOutput = returnedOutput.concat(mashed);
//		}
//
//		
//		String expectedOutput = "";
//		for (Iterator iter = expectedResultModels.iterator(); iter.hasNext();) {
//			WIPTrackerTotalsModel categoryTotal = (WIPTrackerTotalsModel) iter.next();
//			String mashed = mashModel(categoryTotal);
//			expectedOutput = expectedOutput.concat(mashed);
//		}
//		
//		assertEquals("The mashed return data should match the expected mash",
//					expectedOutput,
//					returnedOutput);
//
//		
//	}
	
//	
	
	private String validModelInput1Totals() {

		StringBuffer buf = new StringBuffer();
		
		WIPTrackerTotalsModel expectedResult = null;
		
		expectedResult = new WIPTrackerTotalsModel(new BigDecimal(18),
												   "1234",
												   null);
		buf.append(mashModel(expectedResult));
		
		
		expectedResult = new WIPTrackerTotalsModel(new BigDecimal(10),
				   "987",
				   null);
		buf.append(mashModel(expectedResult));		
		
		return buf.toString();
		
	}
	
//	
//	public void testCollateItems_NullReturns() {
//		
//		ArrayList itemsToCollate = new ArrayList();
//		
//		List collatedItems = generator.collateItems(itemsToCollate);
//		
//		assertNotNull("Returned collated items was null",collatedItems);
//		assertTrue("Return list was not empty!",collatedItems.size() == 0);
//
//	}
//
//	
//	public void testCollateItems_NullInputs() {
//		
//		boolean exceptionCaught = false;
//		try {
//			List collatedItems = generator.collateItems(null);
//		} catch (Throwable e) {
//			exceptionCaught = true;
//		}
//		
//		assertTrue("The null test should have produced an exception!",exceptionCaught);
//		
//	}	
//	
//	
//	public void testCollateItems_validInput1() {
//		
//		List itemsToCollate = validInput1();
//		int noOfReturnItemsExpected = validInput1ReturnSize();
//		
//		List collatedItems = generator.collateItems(itemsToCollate);
//
//		assertNotNull("Returned collated items was null",collatedItems);
//		
//		String assertionMessage = "Expected no of returned collations was "+noOfReturnItemsExpected
//								  +" but got back: "+collatedItems.size();
//		assertTrue(assertionMessage,collatedItems.size() == noOfReturnItemsExpected);		
//		
//		String returnedOutput = "";
//		for (Iterator iter = collatedItems.iterator(); iter.hasNext();) {
//			List category = (List) iter.next();
//			String mashed = mashItems(category);
//			returnedOutput = returnedOutput.concat(mashed);
//			
//		}
//		
//		assertEquals("The mashed return data should match the expected mash",
//					 validInput1ReturnData(),
//					 returnedOutput);
//		System.out.println(returnedOutput);
//	}	
//	
//	
//	
//	public void testCollateItems_validInput2() {
//		
//		List itemsToCollate = validInput2();
//		int noOfReturnItemsExpected = validInput2ReturnSize();
//		
//		List collatedItems = generator.collateItems(itemsToCollate);
//		
//
//		
//		assertNotNull("Returned collated items was null",collatedItems);
//		
//		String assertionMessage = "Expected no of returned collations was "+noOfReturnItemsExpected
//								  +" but got back: "+collatedItems.size();
//		assertTrue(assertionMessage,collatedItems.size() == noOfReturnItemsExpected);		
//		
//		String returnedOutput = "";
//		for (Iterator iter = collatedItems.iterator(); iter.hasNext();) {
//			List category = (List) iter.next();
//			String mashed = mashItems(category);
//			returnedOutput = returnedOutput.concat(mashed);
//			
//		}
//		
//		assertEquals("The mashed return data should match the expected mash",
//					 validInput2ReturnData(),
//					 returnedOutput);
//		System.out.println(returnedOutput);
//	}	
//	
	
	private List validInput1() {
		
		List<String> itemsToCollate = new ArrayList<String>();
		
		itemsToCollate.add("987");	
		itemsToCollate.add("1234");
		itemsToCollate.add("987");
		itemsToCollate.add("1234");
		itemsToCollate.add("987");
		itemsToCollate.add("1234");		
		
		return itemsToCollate;
		
	}
	
	private List validInput2() {
		
		List<String> itemsToCollate = new ArrayList<String>();

		itemsToCollate.add("9");	
		itemsToCollate.add("7");
		itemsToCollate.add("4");
		itemsToCollate.add("9");
		itemsToCollate.add("3");
		itemsToCollate.add("5");
		itemsToCollate.add("9");	
		itemsToCollate.add("7");
		itemsToCollate.add("4");
		itemsToCollate.add("2");
		itemsToCollate.add("3");
		itemsToCollate.add("9");	
		
		return itemsToCollate;
	}	
	

	private List validInput3() {
		
		List<String> itemsToCollate = new ArrayList<String>();
		
		itemsToCollate.add("1234");
		itemsToCollate.add("1234");
		itemsToCollate.add("1234");		
		
		return itemsToCollate;
		
	}
	
	private String validInput2ReturnData() {
		
		List<String> itemsToCollate = new ArrayList<String>();

		itemsToCollate.add("2");
		itemsToCollate.add("3");
		itemsToCollate.add("3");
		itemsToCollate.add("4");
		itemsToCollate.add("4");
		itemsToCollate.add("5");
		itemsToCollate.add("7");
		itemsToCollate.add("7");
		itemsToCollate.add("9");
		itemsToCollate.add("9");	
		itemsToCollate.add("9");
		itemsToCollate.add("9");	
		
		String returnData = mashItems(itemsToCollate);
		
		return returnData;
	}	
	
	
	
	private String validInput1ReturnData() {
		
		List<String> itemsToCollate = new ArrayList<String>();
		itemsToCollate.add("1234");
		itemsToCollate.add("1234");
		itemsToCollate.add("1234");
		itemsToCollate.add("987");
		itemsToCollate.add("987");
		itemsToCollate.add("987");
		
		
		String returnData = mashItems(itemsToCollate);
		return returnData;
		
	}
	
	
	private String validModelInput1ReturnData() {
		
		List<WIPTrackerTotalsModel> itemsToCollate = new ArrayList<WIPTrackerTotalsModel>();
		
		itemsToCollate.add(createModel("1234",6,null));
		itemsToCollate.add(createModel("1234",6,null));
		itemsToCollate.add(createModel("1234",6,null));	
		itemsToCollate.add(createModel("987",1,null));	
		itemsToCollate.add(createModel("987",1,null));
		itemsToCollate.add(createModel("987",2,null));
		itemsToCollate.add(createModel("987",3,null));
		itemsToCollate.add(createModel("987",3,null));
		
		String returnData = mashModels(itemsToCollate,WIPTrackerTotalsModel.FID_CATEGORY);
		return returnData;
		
	}	
	
	private String validInput3ReturnData() {
		
		List<String> itemsToCollate = new ArrayList<String>();
		itemsToCollate.add("1234");
		itemsToCollate.add("1234");
		itemsToCollate.add("1234");
		
		String returnData = mashItems(itemsToCollate);
		return returnData;
		
	}	
	
	private int validInput1ReturnSize() {
		return 2;
	}
	
	private int validInput2ReturnSize() {
		return 6;
	}
	
	private int validInput3ReturnSize() {
		return 1;
	}
	
	
//	
//	public void testCollateItems_validInput3() {
//		
//		List itemsToCollate = validInput3();
//		int noOfReturnItemsExpected = validInput3ReturnSize();
//		
//		List collatedItems = generator.collateItems(itemsToCollate);
//
//		assertNotNull("Returned collated items was null",collatedItems);
//		
//		String assertionMessage = "Expected no of returned collations was "+noOfReturnItemsExpected
//								  +" but got back: "+collatedItems.size();
//		assertTrue(assertionMessage,collatedItems.size() == noOfReturnItemsExpected);		
//		
//		String returnedOutput = "";
//		for (Iterator iter = collatedItems.iterator(); iter.hasNext();) {
//			List category = (List) iter.next();
//			String mashed = mashItems(category);
//			returnedOutput = returnedOutput.concat(mashed);
//			
//		}
//		
//		assertEquals("The mashed return data should match the expected mash",
//					 validInput3ReturnData(),
//					 returnedOutput);
//		System.out.println(returnedOutput);
//	}	
//	
//	
//	public void testCollateItems_model_validInput1() {
//		
//		List<Object> itemsToCollate = validModelInputData1();
//		int noOfReturnItemsExpected = validInput1ReturnSize();
//		WIPTrackerTotalsComparator comparator = new WIPTrackerTotalsComparator();
//		
//		List collatedItems = generator.collateItems(itemsToCollate, comparator);
//
//		assertNotNull("Returned collated items was null",collatedItems);
//		
//		String assertionMessage = "Expected no of returned collations was "+noOfReturnItemsExpected
//								  +" but got back: "+collatedItems.size();
//		assertTrue(assertionMessage,collatedItems.size() == noOfReturnItemsExpected);		
//		
//		String returnedOutput = "";
//		for (Iterator iter = collatedItems.iterator(); iter.hasNext();) {
//			List category = (List) iter.next();
//			String mashed = mashModels(category, WIPTrackerTotalsModel.FID_CATEGORY);
//			returnedOutput = returnedOutput.concat(mashed);
//			
//		}
//		
//		assertEquals("The mashed return data should match the expected mash",
//					validModelInput1ReturnData(),
//					 returnedOutput);
//		System.out.println(returnedOutput);
//	}	
//	
//
//	public void testCollateItems_model_validInput2() {
//		
//		List<Object> itemsToCollate = validModelInputData2();
//		int noOfReturnItemsExpected = validModelInput2ReturnSize();
//		WIPTrackerTotalsComparator comparator = new WIPTrackerTotalsComparator();
//		comparator.setFieldToUseId(WIPTrackerTotalsModel.FID_AMOUNT);
//		List collatedItems = generator.collateItems(itemsToCollate, comparator);
//
//		assertNotNull("Returned collated items was null",collatedItems);
//		
//		String assertionMessage = "Expected no of returned collations was "+noOfReturnItemsExpected
//								  +" but got back: "+collatedItems.size();
//		assertTrue(assertionMessage,collatedItems.size() == noOfReturnItemsExpected);		
//		
//		String returnedOutput = "";
//		for (Iterator iter = collatedItems.iterator(); iter.hasNext();) {
//			List category = (List) iter.next();
//			String mashed = mashModels(category);
//			returnedOutput = returnedOutput.concat(mashed);
//			
//		}
//		
//		assertEquals("The mashed return data should match the expected mash",
//					 validModelInput2ReturnData(),
//					 returnedOutput);
//		System.out.println(returnedOutput);
//	}	
	
	
	private Object validModelInput2ReturnData() {
		List<WIPTrackerTotalsModel> itemsToCollate = new ArrayList<WIPTrackerTotalsModel>();
		
		itemsToCollate.add(createModel("1234",6,null));
		itemsToCollate.add(createModel("1234",6,null));
		itemsToCollate.add(createModel("1234",6,null));	
		
		String returnData = mashModels(itemsToCollate);
		return returnData;
	}

	private int validModelInput2ReturnSize() {
		return 1;
	}
//
//	public void testCollateItems_model_multilevel1() {
//		
//		List<Object> itemsToCollate = multiLevel1InputData();
//		int noOfReturnItemsExpected = validInput1ReturnSize();
//		WIPTrackerTotalsComparator comparator = new WIPTrackerTotalsComparator();
//		comparator.setFieldToUseId(WIPTrackerTotalsModel.FID_CATEGORY);
//		List collatedItems = generator.collateItems(itemsToCollate, comparator);
//
//		assertNotNull("Returned collated items was null",collatedItems);
//		
//		String assertionMessage = "Expected no of returned collations was "+noOfReturnItemsExpected
//								  +" but got back: "+collatedItems.size();
//		assertTrue(assertionMessage,collatedItems.size() == noOfReturnItemsExpected);		
//		
//		String returnedOutput = "";
//		for (Iterator iter = collatedItems.iterator(); iter.hasNext();) {
//			List category = (List) iter.next();
//			String mashed = mashModels(category,WIPTrackerTotalsModel.FID_CATEGORY);
//			returnedOutput = returnedOutput.concat(mashed);
//			
//		}
//		
//		assertEquals("The mashed return data should match the expected mash",
//					 validModelInput1ReturnData(),
//					 returnedOutput);
//
//		// =================================================================
//		
//
//		// now get another level!!!
//		List byCategory = (List) collatedItems.get(1);
//		comparator.setFieldToUseId(WIPTrackerTotalsModel.FID_DATE);
//		List byDate = generator.collateItems(byCategory,comparator);
//		
//		assertNotNull("Returned collated items was null",byDate);
//		
//		noOfReturnItemsExpected = multiLevel1ReturnSize();
//		assertionMessage = "Expected no of returned collations was "+noOfReturnItemsExpected
//								  +" but got back: "+byDate.size();
//		assertTrue(assertionMessage,byDate.size() == noOfReturnItemsExpected);		
//		
//		returnedOutput = "";
//		for (Iterator iter = byDate.iterator(); iter.hasNext();) {
//			List amount = (List) iter.next();
//			String mashed = mashModels(amount,WIPTrackerTotalsModel.FID_DATE);
//			returnedOutput = returnedOutput.concat(mashed);
//			
//		}
//		
//		assertEquals("The mashed return data should match the expected mash",
//					 multiLevel1ReturnData(),
//					 returnedOutput);
//
//	}	

	
	private int multiLevel1ReturnSize() {
		return 2;
	}

	private List produceDailyTotalsInput1(){
		
		List<WIPTrackerTotalsModel> itemsToCollate = new ArrayList<WIPTrackerTotalsModel>();
		
		Date date1 = new Date("01-Jan-2010");
		Date date2 = new Date("02-Jan-2010");
		Date date3 = new Date("03-Jan-2010");
		Date date4 = new Date("04-Jan-2010");
		
		itemsToCollate.add(createModel("cat1",1,date1));	
		itemsToCollate.add(createModel("cat1",6,date2));
		itemsToCollate.add(createModel("cat1",3,date1));
		itemsToCollate.add(createModel("cat1",6,date1));
		itemsToCollate.add(createModel("cat1",2,date2));
		itemsToCollate.add(createModel("cat1",6,date2));
		itemsToCollate.add(createModel("cat1",4,date4));
		itemsToCollate.add(createModel("cat1",3,date2));
		itemsToCollate.add(createModel("cat1",1,date3));
		itemsToCollate.add(createModel("cat1",1,date1));	
		itemsToCollate.add(createModel("cat1",5,date1));
		itemsToCollate.add(createModel("cat1",3,date3));
		
		return itemsToCollate;	
		
	}	
	
	
	private List produceDailyTotalsInput2(){
		
		List<WIPTrackerTotalsModel> itemsToCollate = new ArrayList<WIPTrackerTotalsModel>();
		
		Date date1 = new Date("01-Jan-2010");
		Date date2 = new Date("02-Jan-2010");
		Date date3 = new Date("03-Jan-2010");
		Date date4 = new Date("04-Jan-2010");
		
		itemsToCollate.add(createModel("cat1",6,date2));
		itemsToCollate.add(createModel("cat1",3,date1));
		itemsToCollate.add(createModel("cat1",4,date4));
		itemsToCollate.add(createModel("cat1",1,date3));
		
		return itemsToCollate;	
		
	}	
	
	private List produceDailyTotalsResultModels2(){
		
		List<WIPTrackerTotalsModel> itemsToCollate = new ArrayList<WIPTrackerTotalsModel>();
		
		Date date1 = new Date("01-Jan-2010");
		Date date2 = new Date("02-Jan-2010");
		Date date3 = new Date("03-Jan-2010");
		Date date4 = new Date("04-Jan-2010");
		
		itemsToCollate.add(createModel("cat1",3,date1));			
		itemsToCollate.add(createModel("cat1",6,date2));		
		itemsToCollate.add(createModel("cat1",1,date3));
		itemsToCollate.add(createModel("cat1",4,date4));
		
		return itemsToCollate;	
		
	}
	
	private List produceDailyTotalsResultModels1(){
		
		List<WIPTrackerTotalsModel> itemsToCollate = new ArrayList<WIPTrackerTotalsModel>();
		
		Date date1 = new Date("01-Jan-2010");
		Date date2 = new Date("02-Jan-2010");
		Date date3 = new Date("03-Jan-2010");
		Date date4 = new Date("04-Jan-2010");
		
		itemsToCollate.add(createModel("cat1",16,date1));			
		itemsToCollate.add(createModel("cat1",17,date2));		
		itemsToCollate.add(createModel("cat1",4,date3));
		itemsToCollate.add(createModel("cat1",4,date4));

		return itemsToCollate;	
		
	}
	
	private Object multiLevel1ReturnData() {
		List<WIPTrackerTotalsModel> itemsToCollate = new ArrayList<WIPTrackerTotalsModel>();
		
		Date date1 = new Date("05-Mar-2010");
		Date date2 = new Date("01-Jan-2010");
		
		itemsToCollate.add(createModel("dummy",1,date2));
		itemsToCollate.add(createModel("dummy",1,date2));	
		itemsToCollate.add(createModel("dummy",1,date2));
		itemsToCollate.add(createModel("dummy",1,date1));
		itemsToCollate.add(createModel("dummy",1,date1));
		
		String returnData = mashModels(itemsToCollate,WIPTrackerTotalsModel.FID_DATE);
		return returnData;
	}	
	
	
	
	
	private List multiLevel1InputData(){
		
		
		List<WIPTrackerTotalsModel> itemsToCollate = new ArrayList<WIPTrackerTotalsModel>();
		
		Date date1 = new Date("05-Mar-2010");
		Date date2 = new Date("01-Jan-2010");
		
		itemsToCollate.add(createModel("987",1,date1));	
		itemsToCollate.add(createModel("1234",6,date2));
		itemsToCollate.add(createModel("987",3,date2));
		itemsToCollate.add(createModel("1234",6,date1));
		itemsToCollate.add(createModel("987",2,date2));
		itemsToCollate.add(createModel("1234",6,date1));	
		itemsToCollate.add(createModel("987",3,date1));
		itemsToCollate.add(createModel("987",1,date2));
		
		// totals are:
		
//		1234,6,date1
//		1234,6,date1
//		1234,6,date2
//		987,1,date1	
//		987,3,date1
//		987,3,date2
//		987,2,date2
//		987,1,date2
		
		// 1234, date1 = 12
		// 1234, date2 = 6
		// 987, date1 = 4
		// 987, date2 = 6
		
		return itemsToCollate;	
		
	}
	
	
	private List validModelInputData1(){
		
		
		List<WIPTrackerTotalsModel> itemsToCollate = new ArrayList<WIPTrackerTotalsModel>();
		
		itemsToCollate.add(createModel("987",1,null));	
		itemsToCollate.add(createModel("1234",6,null));
		itemsToCollate.add(createModel("987",3,null));
		itemsToCollate.add(createModel("1234",6,null));
		itemsToCollate.add(createModel("987",2,null));
		itemsToCollate.add(createModel("1234",6,null));	
		itemsToCollate.add(createModel("987",3,null));
		itemsToCollate.add(createModel("987",1,null));
		
		// totals are:
		// 1234 = 18 , 1 category
		// 987 = 10 , 3 categories 
		
		return itemsToCollate;	
		
	}	
	
	private List validModelInputData2(){
		
		
		List<WIPTrackerTotalsModel> itemsToCollate = new ArrayList<WIPTrackerTotalsModel>();
		
		itemsToCollate.add(createModel("1234",6,null));
		itemsToCollate.add(createModel("1234",6,null));
		itemsToCollate.add(createModel("1234",6,null));	
		
		return itemsToCollate;	
		
	}
	
	private WIPTrackerTotalsModel createModel(String catgory, int amount, Date date) {
		
		WIPTrackerTotalsModel model = new WIPTrackerTotalsModel();
		model.setCategory(catgory);
		model.setAmount(new BigDecimal(amount));
		model.setDate(date);
		
		return model;
	}

	private String mashItems(List items) {
		
		if (items == null) {
			return "null-items";
		}
		
		StringBuffer buf = new StringBuffer();
		for (Iterator iter = items.iterator(); iter.hasNext();) {
			Object item = (Object) iter.next();
			buf.append(item.toString());
			buf.append("|");
		}
		
		return buf.toString();
	}
	
	
	private String mashModels(List items) {
		return mashModels(items, 0);
	}
	
	private String mashModels(List items, int fid) {
		
		if (items == null) {
			return "null-items";
		}
		
		boolean showAll = (fid == 0);
		
		
		StringBuffer buf = new StringBuffer();
		for (Iterator iter = items.iterator(); iter.hasNext();) {
			WIPTrackerTotalsModel model = (WIPTrackerTotalsModel) iter.next();
			if (showAll || WIPTrackerTotalsModel.FID_CATEGORY == fid) {
				buf.append(model.getCategory());
				buf.append("|");
			}
			if (showAll || WIPTrackerTotalsModel.FID_AMOUNT == fid) {
				buf.append(model.getAmount());
				buf.append("|");
			}
			if (showAll || WIPTrackerTotalsModel.FID_DATE == fid) {
				buf.append(model.getDate());
				buf.append("|");
			}
			buf.append("%");
		}
		
		return buf.toString();
	}
	
	private String mashModel(WIPTrackerTotalsModel model) {
		
		if (model == null) {
			return "null-model";
		}
		
		StringBuffer buf = new StringBuffer();
		buf.append(model.getCategory());
		buf.append("|");
		buf.append(model.getDate());
		buf.append("|");
		buf.append(model.getAmount());
		buf.append("|");
		
		return buf.toString();
	}
	
}
