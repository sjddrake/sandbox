package uk.co.neo9.apps.protoflow.expo;

public class NavigationLinkPage extends NavigationLinkDetails {
	
	private PageDetails sourcePage;
	private PageDetails targetPage;
	
	
	private NavigationLinkPage() {
		// not used
	}
	
	public NavigationLinkPage(NavigationLinkDetails linkDetails) {
		super.setCoordinates(linkDetails.getCoordinates());
		super.setSourcePageId(linkDetails.getSourcePageId());
		super.setTargetPageId(linkDetails.getTargetPageId());
	}
	
	public PageDetails getSourcePage() {
		return sourcePage;
	}
	public void setSourcePage(PageDetails sourcePage) {
		this.sourcePage = sourcePage;
		super.setSourcePageId(sourcePage.getPageId());
	}
	public PageDetails getTargetPage() {
		return targetPage;
	}
	public void setTargetPage(PageDetails targetPage) {
		this.targetPage = targetPage;
		super.setTargetPageId(targetPage.getPageId());
	}


}
