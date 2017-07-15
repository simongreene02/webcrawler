package org.greatworksinc.webcrawler;

public class Main {

	public static void main(String[] args) {
		System.out.println(PageGetter.getHTMLFromURL("https://www.google.com/search?q=car", "some java program"));

	}

}
