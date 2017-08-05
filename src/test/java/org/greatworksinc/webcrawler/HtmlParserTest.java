package org.greatworksinc.webcrawler;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

public class HtmlParserTest {
	private HtmlParser htmlParser;

	@Before
	public void setUp() {
		htmlParser = new HtmlParser();
	}
	
	@Test
	public void htmlParser_oneLink() {
		assertThat(htmlParser.getUrls("<something>href=\"a-link\"</something>"))
		.containsExactly("a-link");
	}
	
	@Test
	public void htmlParser_twoLinks() {
		assertThat(htmlParser.getUrls("<something>href=\"a-link\"</something><else>href=\"anotherLink\"</else>"))
		.containsExactly("a-link", "anotherLink")
		.inOrder();
	}
	
	@Test
	public void htmlParser_emptyLink() {
		assertThat(htmlParser.getUrls("<something>href=\"\"</something>"))
		.containsExactly("");
	}
	
	@Test
	public void htmlParser_noLink() {
		assertThat(htmlParser.getUrls("<something>ref=\"\"</something>"))
		.isEmpty();
	}
	
	@Test
	public void htmlParser_emptyPage() {
		assertThat(htmlParser.getUrls(""))
		.isEmpty();
	}
	
	@Test
	public void htmlParser_onlyUrl() {
		assertThat(htmlParser.getUrls("href=\"a-link\""))
		.containsExactly("a-link");
	}
	
	@Test
	public void htmlParser_onlyUrls() {
		assertThat(htmlParser.getUrls("href=\"a-link\"href=\"anotherLink\""))
		.containsExactly("a-link", "anotherLink")
		.inOrder();
	}

}
