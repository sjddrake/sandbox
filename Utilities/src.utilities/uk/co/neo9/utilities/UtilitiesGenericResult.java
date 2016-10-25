/*
 * Created on 30-Jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package uk.co.neo9.utilities;

/**
 * @author simon.j.d.drake
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UtilitiesGenericResult implements UtilitiesGenericResultTypeConstants{
	
	private String textValue = null;
	private int dataType = DT_TEXT;
	private int identifier;


	//
	// Constructors
	//

	public UtilitiesGenericResult(){
		super();
	}


	public UtilitiesGenericResult(int pIdentifier,
						   String pTextValue){
		super();
		
		setTextValue(pTextValue);
		setDataType(DT_TEXT);
		setIdentifier(pIdentifier);
		
	}
	
	

	public UtilitiesGenericResult(int pIdentifier,
						   String pTextValue,
						   int pDataType){
		super();
		
		setTextValue(pTextValue);
		setDataType(pDataType);
		setIdentifier(pIdentifier);
		
	}


	//
	// Getters & Setters
	//

	public void setTextValue(String pTextValue) {
		this.textValue = pTextValue;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setDataType(int pDataType) {
		this.dataType = pDataType;
	}

	public int getDataType() {
		return dataType;
	}

	public void setIdentifier(int pIdentifier) {
		this.identifier = pIdentifier;
	}

	public int getIdentifier() {
		return identifier;
	}


	//
	// Functional Methods
	// 

	public String dump() {
		
		StringBuffer lOutput = new StringBuffer();
		lOutput.append(getIdentifier());
		lOutput.append(" - ");
		lOutput.append(getDataType());
		lOutput.append(" - ");
		lOutput.append(getTextValue());
		
		System.out.println(lOutput);
		
		return lOutput.toString();
		
	}

}
