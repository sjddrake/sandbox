package uk.co.neo9.apps.accounts;

import static org.junit.Assert.*;

import org.junit.Test;


public class ChequeLookupTestCase {
	
	@Test
	public void test_load(){
		
		String filedetails = "D:/ZZ - Swap Zone/_ DropBoxes/jmcgDropBox/My Dropbox/Kathie and Simon/accounts/_ WIP/cheques.xls";
		ChequeDetailsLookup.setAccountsLookupFileName(filedetails);
		String description = ChequeDetailsLookup.chequeLookup("100278");
		
		assertEquals(description,"Joshua - Beavers");
	}

}
