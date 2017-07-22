package org.greatworksinc.webcrawler;

import static com.google.common.truth.Truth.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PageGetterTest extends Mockito {
	
	private @Mock HttpClient httpClient;
	private @Mock HttpResponse httpResponse;
	private @Mock HttpEntity httpEntity;
	private InputStream inputStream;
	
	@Before
	public void setup() throws UnsupportedEncodingException {
		this.inputStream = new ByteArrayInputStream("Hello World".getBytes("UTF-8"));
	}

	@Test
	public void getHTMLFromURL_nonExistentTargetURL() throws ClientProtocolException, IOException {
		when(httpClient.execute(any(HttpGet.class))).thenThrow(new IOException());
		assertThat(PageGetter.getHTMLFromURL("j.com", "", httpClient)).isNull();
	}
	
	@Test
	public void getHTMLFromURL_positiveScenario() throws ClientProtocolException, IOException {
		when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		when(httpEntity.getContent()).thenReturn(inputStream);
		assertThat(PageGetter.getHTMLFromURL("www.google.com", "", httpClient)).isEqualTo("Hello World");
	}

}
