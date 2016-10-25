/*
 * Created on 09-Jan-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities;

import uk.co.neo9.utilities.file.RelationalFileHelper;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Test_RelationalFileHelper {

	public static void main(String[] args) {
		
		Test_RelationalFileHelper t = new Test_RelationalFileHelper();
		t.go();
	}
	
	public void go(){
		
		String lWorkingFolder = "c:/simonz/dev/java/test/xsds/sib0nn";
		String lFileName = "../API_TYPES.xsd";
		String lResult = null;
		
		RelationalFileHelper r = new RelationalFileHelper();
		lResult = r.constructFileDetails(lFileName,lWorkingFolder);
		
		System.out.println(lWorkingFolder);
		System.out.println(lFileName);
		System.out.println(lResult);
		System.out.println("//----------------------------------------");
		
		//----------------------------------------
		
		
		lFileName = "SIB0nn.xsd";
		lResult = r.constructFileDetails(lFileName,lWorkingFolder);
		
		System.out.println(lWorkingFolder);
		System.out.println(lFileName);
		System.out.println(lResult);
		System.out.println("//----------------------------------------");
	

		//----------------------------------------
		
		lFileName = "enquire/SIB0nn.xsd";
		lResult = r.constructFileDetails(lFileName,lWorkingFolder);
		
		System.out.println(lWorkingFolder);
		System.out.println(lFileName);
		System.out.println(lResult);
		System.out.println("//----------------------------------------");
		
	
		//----------------------------------------
		
		lFileName = "../enquire/SIB0nn.xsd";
		lResult = r.constructFileDetails(lFileName,lWorkingFolder);
		
		System.out.println(lWorkingFolder);
		System.out.println(lFileName);
		System.out.println(lResult);
		System.out.println("//----------------------------------------");
		
		

		//----------------------------------------
		
		lFileName = "D:\\simonz\\My Music\\_ Processing\\_ Not on ext HDD\\From Mike\\07 Wake Me Up When September Ends.m4p";
		test_testDIRLineForFileName(lFileName);
		
		lFileName = "D:\\simonz\\My Music\\_ Processing\\_ Not on ext HDD\\From Mike";
		test_testDIRLineForFileName(lFileName);					

		lFileName = "";
		test_testDIRLineForFileName(lFileName);					

		lFileName = null;
		test_testDIRLineForFileName(lFileName);			
	}
	
	public void test_testDIRLineForFileName(String DIRLine){
		boolean isFile = RelationalFileHelper.testDIRLineForFileName(DIRLine);
		if (isFile) {
			System.out.println("found a filename in > ");
		} else {
			System.out.println(" NO FILENAME IN > ");
		}
		System.out.println(DIRLine);	
		System.out.println("//----------------------------------------");
		System.out.println("");
	}
}
