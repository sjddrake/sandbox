package uk.co.neo9.apps.accounts;

import junit.framework.TestCase;

public class AccountsDataModelTest extends TestCase {

	public AccountsDataModelTest(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void test_buildTrackerOutputText_NotExtended(){
		
		AccountsDataModel model = getTestModel1();
		
		boolean extended = false;
		String output = model.buildTrackerOutputText(extended);
		
		System.out.println(output);
		
	}
	
	
	private AccountsDataModel getTestModel1(){
		
		AccountsDataModel model = new AccountsDataModel();

		model._Line1 = null;
		
		model._DateText = "22/03/2010";
		model._Amount = "£34.56";
		model._Balance = "£102.56";
		model._TransactionType = null;
		model._Description = null;
		model._Reference = null;
		model._PlusOrMinus = null;
		model._method = "CC";
		model._Owner = null;
		
		return model;
		
	}

}
