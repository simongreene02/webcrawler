package org.greatworksinc.webcrawler;

import static org.junit.Assert.*;
import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class PageGetterTest {

	@Test
	public void getHTMLFromURL_nonExistentTargetURL() {
		assertThat(PageGetter.getHTMLFromURL("j.com", "")).isNull();
	}
	
//	@Test
//	public void getHTMLFromURL

}
