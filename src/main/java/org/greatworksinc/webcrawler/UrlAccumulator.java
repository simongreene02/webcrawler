package org.greatworksinc.webcrawler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.ImmutableList;

public class UrlAccumulator {
	private static final String TERMINAL_TOKEN = "TERMINAL_TOKEN";
	private static final String USER_AGENT = "";
	private final HtmlParser htmlParser;
	private final PageGetter getter;
	private Queue<String> linkQueue;
	private final AtomicInteger count = new AtomicInteger();
	private ExecutorService executorService = Executors.newScheduledThreadPool(3);

	public UrlAccumulator(String rootURL, HtmlParser htmlParser, PageGetter getter) {
		this.htmlParser = htmlParser;
		this.getter = getter;
		this.linkQueue = new ConcurrentLinkedQueue<String>();
		this.linkQueue.offer(rootURL);

	}

	/**
	 * 1. go to first url in queue 2. get html of url 3. get links from html 4.
	 * loop through links 5. add links to queue
	 */
	public void process() {
		executorService.execute(new WebsiteChecker());
		while (count.get() > 0 || !linkQueue.isEmpty()) {
			if (!linkQueue.isEmpty()) {
				//Run new thread
			}
		}
	}

	public class WebsiteChecker implements Runnable {

		@Override
		public void run() {
			String page = getter.getHTMLFromURL(linkQueue.poll(), USER_AGENT);
			ImmutableList<String> linkList = htmlParser.getUrls(page);
			linkQueue.addAll(linkList);
		}

	}

}
