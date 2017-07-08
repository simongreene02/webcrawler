package org.greatworksinc.webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageGetter {
	private static final Logger log = LoggerFactory.getLogger(PageGetter.class);
	/**
	 * 
	 * @param url
	 * @param userAgent
	 * @return The content of the page, or {@code null} if the program encounters an error
	 */
	public static String getHTMLFromURL(String url, String userAgent) {
		log.debug("start of method");
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		
		request.addHeader("User-Agent", userAgent);
		try {
			HttpResponse response = client.execute(request);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder output = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				output.append(line);
			}
			return output.toString();
		} catch (IOException e) {
			log.warn("Error connecting to {} with {}", url, userAgent);
			return null;
		}
	}
}
