/*
 * Created on 27-Nov-06
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package testutilities.vendor;

import java.util.ArrayList;
import java.util.Date;

import testutilities.data.AddressTestData;



public class AddressTestDataVendor {
	

	
//	public static TelephoneDTO setTelephoneNumber(TelephoneDTO target, String value) {
//
//		target.setSubscriberNumber(value);
//		return target;
//	
//	}	
	
	
	// ****************************** phone numbers ***********************
	
//	public static HomePhoneDTO getHomeTelephone(String value) {		
//		HomePhoneDTO homePhone = new HomePhoneDTO();
//		setTelephoneNumber(homePhone, value);
//		return homePhone;
//	}	
//	
//	public static HomePhoneDTO getHomeTelephone() {
//		return getHomeTelephone("0207556666");
//	}		
//
//	public static WorkPhoneDTO getWorkTelephone(String value) {		
//		WorkPhoneDTO workPhone = new WorkPhoneDTO();
//		setTelephoneNumber(workPhone, value);
//		return workPhone;
//	}	
//	
//	public static WorkPhoneDTO getWorkTelephone() {
//		return getWorkTelephone("0207225555");
//	}		
//
//
//	public static MobilePhoneDTO getMobileTelephone(String value) {		
//		MobilePhoneDTO mobilePhone = new MobilePhoneDTO();
//		setTelephoneNumber(mobilePhone, value);
//		return mobilePhone;
//	}	
//	
//	public static MobilePhoneDTO getMobileTelephone() {
//		return getMobileTelephone("07940888888");
//	}	
//
//
//
//	public static EmailDTO getEmail(String value) {		
//		EmailDTO email = new EmailDTO();
//		email.setTelecommunicationsAddress(value);
//		return email;
//	}	
//
//	public static EmailDTO getEmail() {		
//		return getEmail("joe.bloggs@isp.nut");
//	}	


	// ***************************** ADDRESSES *********************************




	public static AddressTestData getAddress() {
		
		AddressTestData structuredAddress = new AddressTestData();
		
		structuredAddress.setBuildingName("The Castle");
		structuredAddress.setBuildingNumber("28");
		structuredAddress.setStreet("Prospect Place");
		structuredAddress.setTown("Old Town");
		structuredAddress.setDistrict("Swindon");
		structuredAddress.setCounty("England");
		structuredAddress.setOutboundPostCode("SN1");
		structuredAddress.setInboundPostCode("3LQ");	
		
		return structuredAddress;
	}




		
}
