package org.greatworksinc.webcrawler;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

public class UrlAccumulator {
	private static final String TERMINAL_TOKEN = "TERMINAL_TOKEN";
	private static final String USER_AGENT = "";
	private final HtmlParser htmlParser;
	private final PageGetter getter;
	private final String rootUrl;
	private final AtomicInteger count = new AtomicInteger();
	private HashMap<String, Integer> urlDepthMap = Maps.newHashMap();
	private ExecutorService executorService = Executors.newScheduledThreadPool(3);

	public UrlAccumulator(String rootUrl, HtmlParser htmlParser, PageGetter getter) {
		this.htmlParser = htmlParser;
		this.getter = getter;
		this.rootUrl = rootUrl;
		urlDepthMap.put(rootUrl, 0);

	}

	/**
	 * 1. go to first url in queue 2. get html of url 3. get links from html 4.
	 * loop through links 5. add links to queue
	 */
	public void process() {
		executorService.execute(new WebsiteChecker(rootUrl));

	}

	public class WebsiteChecker implements Runnable {

		private final String url;

		WebsiteChecker(String url) {
			this.url = url;
		}

		@Override
		public void run() {
			int depth = urlDepthMap.get(url);
			int childDepth = depth + 1;
			String page = getter.getHTMLFromURL(url, USER_AGENT);
			ImmutableList<String> urls = htmlParser.getUrls(page);
			urls.forEach(childUrl -> {
				urlDepthMap.put(childUrl, depth);
				executorService.execute(new WebsiteChecker(url));
			});
		}

	}

}
