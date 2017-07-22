package org.greatworksinc.webcrawler;

import com.google.common.collect.ImmutableList;

public class HtmlParser {
	private final ImmutableList<String> urls;
	public HtmlParser(String htmlPage) {
		String urlString = "";
		ImmutableList.Builder<String> builder = ImmutableList.builder();
		for (int i = 6; i < htmlPage.length(); i++) {
			if (htmlPage.charAt(i-6) == 'h' && htmlPage.charAt(i-5) == 'r' && htmlPage.charAt(i-4) == 'e' 
					&& htmlPage.charAt(i-3) == 'f' && htmlPage.charAt(i-2) == '=' && htmlPage.charAt(i-1) == '\"') {
			}
		}
		builder.add(urlString);
		this.urls = builder.build();
	}
}
