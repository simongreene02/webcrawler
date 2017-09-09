package org.greatworksinc.webcrawler;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;

public class UrlAccumulator {
	private static final Logger log = LoggerFactory.getLogger(UrlAccumulator.class);
	private static final String TERMINAL_TOKEN = "TERMINAL_TOKEN";
	private static final String USER_AGENT = "";
	private final HtmlParser htmlParser;
	private final PageGetter getter;
	private final AtomicInteger count = new AtomicInteger();
	private HashMap<String, Integer> urlDepthMap = Maps.newHashMap();
	private ExecutorService executorService = Executors.newScheduledThreadPool(3);

	public UrlAccumulator() {
		this(new HtmlParser(), new PageGetter());
	}
	
	@VisibleForTesting UrlAccumulator( HtmlParser htmlParser, PageGetter getter) {
		this.htmlParser = htmlParser;
		this.getter = getter;
	}

	public void process(String rootUrl) {
		urlDepthMap.put(rootUrl, 0);
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
			htmlParser.getUrls(page).forEach(childUrl -> {
				urlDepthMap.put(childUrl, depth);
				executorService.execute(new WebsiteChecker(childUrl));
				log.info(childUrl);
			});
		}

	}

}
