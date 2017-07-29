package org.greatworksinc.webcrawler;

public class UrlAccumulator {
	
	private final String rootURL;
	private final HtmlParser htmlParser;
	private final PageGetter getter;
	private String[] linkList;
	
	public UrlAccumulator(String rootURL, HtmlParser htmlParser, PageGetter getter) {
		this.rootURL = rootURL;
		this.htmlParser = htmlParser;
		this.getter = getter;
	}
	
	
}
