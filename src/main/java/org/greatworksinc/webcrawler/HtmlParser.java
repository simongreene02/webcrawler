package org.greatworksinc.webcrawler;

import com.google.common.collect.ImmutableList;

public class HtmlParser {
	
	
	public ImmutableList<String> getUrls(String htmlPage) {
		ImmutableList<String> urls;
		final int pageLength = htmlPage.length();
		ImmutableList.Builder<String> builder = ImmutableList.builder();
		for (int i = 6; i < pageLength; i++) {
			if (htmlPage.charAt(i-6) == 'h' && htmlPage.charAt(i-5) == 'r' && htmlPage.charAt(i-4) == 'e' 
					&& htmlPage.charAt(i-3) == 'f' && htmlPage.charAt(i-2) == '=' && htmlPage.charAt(i-1) == '\"') {
				StringBuilder urlString = new StringBuilder();
				while (htmlPage.charAt(i) != '\"') {
					urlString.append(htmlPage.charAt(i));
					i++;
					if (i >= pageLength) {
						break;
					}
				}
				builder.add(urlString.toString());
			}
		}
		return builder.build();
		
	}
	
	
}
