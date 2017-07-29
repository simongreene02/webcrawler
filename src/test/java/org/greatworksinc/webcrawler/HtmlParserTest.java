package org.greatworksinc.webcrawler;

import static org.junit.Assert.*;
import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class HtmlParserTest {

	@Test
	public void htmlParser_oneLink() {
		assertThat(new HtmlParser("<something>href=\"a-link\"</something>").getUrls())
		.containsExactly("a-link");
	}
	
	@Test
	public void htmlParser_twoLinks() {
		assertThat(new HtmlParser("<something>href=\"a-link\"</something><else>href=\"anotherLink\"</else>").getUrls())
		.containsExactly("a-link", "anotherLink")
		.inOrder();
	}
	
	@Test
	public void htmlParser_emptyLink() {
		assertThat(new HtmlParser("<something>href=\"\"</something>").getUrls())
		.containsExactly("");
	}
	
	@Test
	public void htmlParser_noLink() {
		assertThat(new HtmlParser("<something>ref=\"\"</something>").getUrls())
		.isEmpty();
	}
	
	@Test
	public void htmlParser_emptyPage() {
		assertThat(new HtmlParser("").getUrls())
		.isEmpty();
	}
	
	@Test
	public void htmlParser_onlyUrl() {
		assertThat(new HtmlParser("href=\"a-link\"").getUrls())
		.containsExactly("a-link");
	}
	
	@Test
	public void htmlParser_onlyUrls() {
		assertThat(new HtmlParser("href=\"a-link\"href=\"anotherLink\"").getUrls())
		.containsExactly("a-link", "anotherLink")
		.inOrder();
	}

}
