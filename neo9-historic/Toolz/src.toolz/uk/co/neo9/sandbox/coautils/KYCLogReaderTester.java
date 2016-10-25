/*
 * Created on 03-Oct-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.sandbox.coautils;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author ct039314
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KYCLogReaderTester extends KYCLogReader {
	
	private Iterator testLineIterator = null;
	
	/* DESIGN
	 * 
	 * - read a session worth of lines in (hope it's contiguous!)
	 * - keep the lines in an array list
	 * - give the list to a container
	 * - container identifies the session line and gets the session ref
	 * - container stores the lines per party and tag
	 * - cotnainer identifies if the KYC was captured
	 * - container identifies if the KYC update was successful
	 * 
	 */
	
	

	public static void main(String[] args) {
		
		
		KYCLogReaderTester reader = new KYCLogReaderTester();
		reader.test2();
		
	}


	public void test1(){
		
		String testLine = "KYC Thread[ngine.Transports : 1] 2007-10-03 12:07:26,014 F075RspAudV1 - PARTY: 175802674 -> ,03102007 120709,02102007 143053,03102007 120709,03102007 120709,03102007 120709,02102007 150329,03102007 120709,02102007 150329,";
//		int offset = testLine.indexOf("PARTY: ");
//		String partyID = getNumberFromText(testLine,offset+7);
//		System.out.println(partyID);

		Integer partyID = extractPartyIdFromLine(testLine);
		System.out.println(partyID);

	}


	public void test2(){
		
		testLineIterator = getTestLines().iterator();
		
	//	go();

	}

	protected String getNextLine() {
		String nextLine = null;
		if (testLineIterator.hasNext()){
			nextLine = (String)testLineIterator.next();
		}
		return nextLine;
	}


	public static ArrayList getTestLines(){
		ArrayList testLines = new ArrayList();
		
		testLines.add("KYC Thread[ngine.Transports : 0] 2007-10-03 12:06:02,967 LOGKYC - PARTY: null!! - Starting to log new session");
		testLines.add("KYC Thread[ngine.Transports : 0] 2007-10-03 12:06:11,248 F061RspValV1 - PARTY: 175802674 -> ,GBR,14051973,fdasfd~safdsa~fdsaf~fdsa~dsafdsa~fdsa~fdsafds~BB104JJ~,BMU,BWA,3,5,");
		testLines.add("KYC Thread[ngine.Transports : 0] 2007-10-03 12:06:11,248 F061RspAudV1 - PARTY: 175802674 -> ,03102007 114812,02102007 143053,03102007 114812,03102007 114812,03102007 114812,03102007 114812,03102007 114812,");
		testLines.add("KYC Thread[ngine.Transports : 0] 2007-10-03 12:06:11,248 F075RspAudV1 - PARTY: 175802674 -> ,03102007 114812,02102007 143053,03102007 114812,03102007 114812,03102007 114812,02102007 150329,03102007 114812,02102007 150329,");
		testLines.add("KYC Thread[ngine.Transports : 0] 2007-10-03 12:06:19,358 F061RspValV1 - PARTY: 1717216027 -> ,GBR,01041986,dsadsa~dsad~sadsads~adsa~dsadsa~,ATG,null!!,1,3,");
		testLines.add("KYC Thread[ngine.Transports : 0] 2007-10-03 12:06:19,358 F061RspAudV1 - PARTY: 1717216027 -> ,10092007 150354,19042007 160215,10092007 150354,10092007 150354,null!! null!!,10092007 150421,10092007 150421,");
		testLines.add("KYC Thread[ngine.Transports : 0] 2007-10-03 12:06:19,358 F075RspAudV1 - PARTY: 1717216027 -> ,10092007 150354,19042007 160215,10092007 150354,10092007 150354,               ,10092007 150421,10092007 150421,10092007 150421,");
		testLines.add("KYC Thread[ngine.Transports : 0] 2007-10-03 12:06:24,061 KYCRequiredV1 - PARTY: 175802674 - KYC refresh is NOT required");
//		testLines.add("KYC Thread[ngine.Transports : 0] 2007-10-03 12:06:24,061 KYCRequiredV1 - PARTY: 175802674 - KYC refresh is required");
		testLines.add("KYC Thread[ngine.Transports : 0] 2007-10-03 12:06:58,092 KYCSessionV1 - PARTY: 175802674 - REF: 2905");
		testLines.add("KYC Thread[Thread-22           ] 2007-10-03 12:07:05,733 KYCChangesV1 - PARTY: 175802674 -> ,false,false,false,false,false,false,false,false,false,false,false,false,false,");
		testLines.add("KYC Thread[Thread-22           ] 2007-10-03 12:07:06,967 F062ReqValV1 - PARTY: 175802674 -> ,GBR,14051973,fdasfd~safdsa~fdsaf~fdsa~dsafdsa~fdsa~fdsafds~BB104JJ~,BMU,BWA,3,5,");
		testLines.add("KYC Thread[Thread-22           ] 2007-10-03 12:07:06,967 F062ReqAudV1 - PARTY: 175802674 -> ,3102007 114812,2102007 143053,3102007 114812,3102007 114812,3102007 114812,3102007 114812,null!!,");
		testLines.add("KYC Thread[Thread-22           ] 2007-10-03 12:07:13,498 F073CallV1 - PARTY: 175802674 - F073 is NOT being called!");
		testLines.add("KYC Thread[ngine.Transports : 1] 2007-10-03 12:07:26,014 F061RspValV1 - PARTY: 175802674 -> ,GBR,14051973,fdasfd~safdsa~fdsaf~fdsa~dsafdsa~fdsa~fdsafds~BB104JJ~,BMU,BWA,3,5,");
		testLines.add("KYC Thread[ngine.Transports : 1] 2007-10-03 12:07:26,014 F061RspAudV1 - PARTY: 175802674 -> ,03102007 120709,02102007 143053,03102007 120709,03102007 120709,03102007 120709,03102007 120709,03102007 120709,");
		testLines.add("KYC Thread[ngine.Transports : 1] 2007-10-03 12:07:26,014 F075RspAudV1 - PARTY: 175802674 -> ,03102007 120709,02102007 143053,03102007 120709,03102007 120709,03102007 120709,02102007 150329,03102007 120709,02102007 150329,");
//		testLines.add("KYC Thread[Thread-34           ] 2007-09-27 18:47:18,245 F073SuccessV1 - PARTY: 175802674 - F073 returned an error");
		testLines.add("KYC Thread[ngine.Transports : 0] 2007-10-03 12:10:02,967 LOGKYC - PARTY: null!! - Starting to log new session");
		
		return testLines;
		
	}

}
