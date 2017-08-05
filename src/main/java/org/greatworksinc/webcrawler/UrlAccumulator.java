package org.greatworksinc.webcrawler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.common.collect.ImmutableList;

public class UrlAccumulator {
	
	private static final String USER_AGENT = "";
	private final HtmlParser htmlParser;
	private final PageGetter getter;
	private Queue<String> linkQueue;
	
	public UrlAccumulator(String rootURL, HtmlParser htmlParser, PageGetter getter) {
		this.htmlParser = htmlParser;
		this.getter = getter;
		this.linkQueue = new ConcurrentLinkedQueue<String>();
		this.linkQueue.offer(rootURL);
		
	}
	
	/**
	 * 1. go to first url in queue
	 * 2. get html of url
	 * 3. get links from html
	 * 4. loop through links
	 * 5. add links to queue
	 */
	public void process() {
		while (!linkQueue.isEmpty()) {
			String url = linkQueue.poll();
			String page = getter.getHTMLFromURL(url, USER_AGENT);
			ImmutableList<String> linkList = htmlParser.getUrls(page);
			linkQueue.addAll(linkList);
		}
	}
	
}
