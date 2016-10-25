package uk.co.neo9.utilities.app;

public class UtilitiesAppLaunchDetails {

	public static int UNDEFINED = -1;
	public static int TYPE_FILENAME_ONLY = 100;
	public static int TYPE_FOLDER = 101;
	public static int TYPE_FULL_FILE_DETAILS = 102;
	public static int TYPE_ENUMERATION = 103;
	public static int TYPE_FREE_FROM = 104;
	public static int TYPE_BOOLEAN = 105;
	public static int TYPE_YESNO = 106;
	
	private String value = null;
	private String name = null;
	private String description = null;

	private int type = UNDEFINED;
	private int index = UNDEFINED;
	private boolean optional = false;
	private boolean override = false;
	private boolean quickLaunch = false;
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isOptional() {
		return optional;
	}
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	public boolean isOverride() {
		return override;
	}
	public void setOverride(boolean override) {
		this.override = override;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setQuickLaunch(boolean quickLaunch) {
		this.quickLaunch = quickLaunch;
	}
	public boolean isQuickLaunch() {
		return quickLaunch;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getIndex() {
		return index;
	}
}
