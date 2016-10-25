
package testutilities.data;

import java.io.Serializable;

/***/
public class AddressTestData extends BaseTestData
{
	//Name given to this class.
	public final static String CLASS_NAME = "AddressTestData";

	
	/**
	 * StructuredAddressDTO constructor.
	 */
	public AddressTestData()
	{
	
		super();
	
	}

    /***/
	private java.lang.String  addressOrganisationName = null;

	

	public java.lang.String getAddressOrganisationName()
	{
		return addressOrganisationName;
	}

	public void setAddressOrganisationName(java.lang.String value)
	{
		addressOrganisationName = value;
	}
	
	/***/
	private java.lang.String  subBuildingName = null;

	

	public java.lang.String getSubBuildingName()
	{
		return subBuildingName;
	}

	public void setSubBuildingName(java.lang.String value)
	{
		subBuildingName = value;
	}
	
	/***/
	private java.lang.String  buildingName = null;

	

	public java.lang.String getBuildingName()
	{
		return buildingName;
	}

	public void setBuildingName(java.lang.String value)
	{
		buildingName = value;
	}
	
	/***/
	private java.lang.String  buildingNumber = null;

	

	public java.lang.String getBuildingNumber()
	{
		return buildingNumber;
	}

	public void setBuildingNumber(java.lang.String value)
	{
		buildingNumber = value;
	}
	
	/***/
	private java.lang.String  district = null;

	

	public java.lang.String getDistrict()
	{
		return district;
	}

	public void setDistrict(java.lang.String value)
	{
		district = value;
	}
	
	/***/
	private java.lang.String  town = null;

	

	public java.lang.String getTown()
	{
		return town;
	}

	public void setTown(java.lang.String value)
	{
		town = value;
	}
	
	/***/
	private java.lang.String  county = null;

	

	public java.lang.String getCounty()
	{
		return county;
	}

	public void setCounty(java.lang.String value)
	{
		county = value;
	}
	
	/***/
	private java.lang.String  outboundPostCode = null;

	

	public java.lang.String getOutboundPostCode()
	{
		return outboundPostCode;
	}

	public void setOutboundPostCode(java.lang.String value)
	{
		outboundPostCode = value;
	}
	
	/***/
	private java.lang.String  fullPostCode = null;



	public void setFullPostCode(java.lang.String value)
	{
		fullPostCode = value;
	}
	
	
	private java.lang.String  inboundPostCode = null;

	

	public java.lang.String getInboundPostCode()
	{
		return inboundPostCode;
	}

	public void setInboundPostCode(java.lang.String value)
	{
		inboundPostCode = value;
	}	
	
	
	public java.lang.String getFullPostCode()
	{
		if (fullPostCode != null && fullPostCode.trim().length() > 0){
			return fullPostCode;
		} else {
			String constructedPostcode = getOutboundPostCode()+" "+getInboundPostCode();
			if (constructedPostcode != null && constructedPostcode.trim().length() > 0){
				return constructedPostcode;
			}
		}
		 
		 return null;
	}
	
	
	/***/
	private java.lang.String  street = null;

	

	/**
	 * @return
	 */
	public java.lang.String getStreet() {
		return street;
	}

	/**
	 * @param string
	 */
	public void setStreet(java.lang.String string) {
		street = string;
	}

}
