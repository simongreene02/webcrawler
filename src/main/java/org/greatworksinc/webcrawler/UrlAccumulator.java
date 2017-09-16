package org.greatworksinc.webcrawler;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;

public class UrlAccumulator {
	private static final Predicate<String> MY_URL_FILTER = url ->  url.endsWith(".html");
	private static final Logger log = LoggerFactory.getLogger(UrlAccumulator.class);
	private static final String TERMINAL_TOKEN = "TERMINAL_TOKEN";
	private static final String USER_AGENT = "";
	private final HtmlParser htmlParser;
	private final PageGetter getter;
	private final AtomicInteger count = new AtomicInteger();
	private Map<String, Integer> urlDepthMap = Maps.newConcurrentMap();
	private ExecutorService executorService = Executors.newFixedThreadPool(2);

	public UrlAccumulator() {
		this(new HtmlParser(), new PageGetter());
	}

	@VisibleForTesting
	UrlAccumulator(HtmlParser htmlParser, PageGetter getter) {
		this.htmlParser = htmlParser;
		this.getter = getter;
	}

	public void process(String rootUrl) {
		urlDepthMap.put(rootUrl, 0);
		executorService.execute(new WebsiteChecker(rootUrl));

	}

	public class WebsiteChecker implements Runnable {

		private final String url;
		private final int depth;

		WebsiteChecker(String url) {
			this.url = url;
			this.depth = urlDepthMap.get(url);
		}

		@Override
		public void run() {
			String page = getter.getHTMLFromURL(url, USER_AGENT);
			htmlParser.getUrls(page).stream().filter(MY_URL_FILTER).forEach(this::addUrlToMap);
		}
		
		private void addUrlToMap(final String childUrl) {
			String parentUrl = url;
			if (!urlDepthMap.containsKey(childUrl) && depth < 3) {
				if (MY_URL_FILTER.test(url)) {
					parentUrl = parentUrl.substring(0, parentUrl.lastIndexOf('/'));
				}
				String newChildUrl = parentUrl+'/'+childUrl;
				urlDepthMap.put(newChildUrl, depth+1);
				executorService.execute(new WebsiteChecker(newChildUrl));
				log.info("depth: {}, url: {}", depth, newChildUrl);
			}
		}

	}

}
