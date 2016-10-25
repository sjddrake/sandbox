package uk.co.neo9.apps.protoflow.expo;

import java.util.ArrayList;
import java.util.List;

public class PageNavigationCompositeModel {
	
	private PageDetails page;
	private List<NavigationLinkPage> links = new ArrayList<NavigationLinkPage>();
	
//	public PageNavigationCompositeModel(PageDetails page, NavigationLinkDetails... links) {
//		this.page = page;
//		for (NavigationLinkDetails navigationLinkDetails : links) {
//			this.links.add(navigationLinkDetails);
//		}
//	}
	
	

	public PageDetails getPage() {
		return page;
	}
	public void setPage(PageDetails page) {
		this.page = page;
	}
	
	public void addLink(NavigationLinkPage targetPageLink) {
		this.links.add(targetPageLink);
	}

	public List<NavigationLinkPage> getLinks() {
		List<NavigationLinkPage> retval = new ArrayList<NavigationLinkPage>();
		retval.addAll(links);
		return retval;
	}
}
