package uk.co.neo9.apps.protoflow.expo;

public class NavigationLinkDetails {
	
	private String sourcePageId;
	public String getSourcePageId() {
		return sourcePageId;
	}
	public void setSourcePageId(String sourcePageId) {
		this.sourcePageId = sourcePageId;
	}
	public String getTargetPageId() {
		return targetPageId;
	}
	public void setTargetPageId(String targetPageId) {
		this.targetPageId = targetPageId;
	}
	public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	private String targetPageId;
	private String coordinates;
	
	
	
}
