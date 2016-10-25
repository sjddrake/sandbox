package uk.co.neo9.utilities.file;

public class CSVMappingTransform {

	public static int TYPE_STRING = 111;
	public static int TYPE_DATE = 222;
	public static int TYPE_UNFORMATED = 666;
	
	private boolean useDefault = false;
	private String defaultValue = null;
	private int inputIndex = -1;
	private int type = TYPE_STRING;
	
	private CSVMappingTransform() {
		super();
	}		
	
	public CSVMappingTransform(int index) {
		super();
		inputIndex = index;
	}	
	
	public CSVMappingTransform(int index,  int type) {
		super();
		inputIndex = index;
		this.type = type;
	}		
	
	public CSVMappingTransform(int index, String value) {
		super();
		defaultValue = value;
		inputIndex = index;
		useDefault = true;
	}

	public CSVMappingTransform(int index, String value, int type) {
		super();
		defaultValue = value;
		inputIndex = index;
		useDefault = true;
		this.type = type;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public int getInputIndex() {
		return inputIndex;
	}
	public void setInputIndex(int inputIndex) {
		this.inputIndex = inputIndex;
	}
	public boolean isUseDefault() {
		return useDefault;
	}
	public void setUseDefault(boolean useDefault) {
		this.useDefault = useDefault;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
