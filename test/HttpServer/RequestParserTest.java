package HttpServer;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

import java.io.StringReader;


public class RequestParserTest {

	private RequestParser parser;
	private MockBufferedReader mockReader;
	private String simpleInput;

	@Before
	public void setUp() {
		mockReader = new MockBufferedReader(new StringReader(""));
		parser = new RequestParser(mockReader);
		simpleInput = "GET / HTTP/1.1\r\nHost: localhost: 8080\r\n";
	}

	@Test
	public void itReadsTheInput() throws Exception {
		mockReader.setMockInput(simpleInput);
		assertEquals(simpleInput, parser.getInput());
	}

	@Test
	public void returnsTheRequestVerb() throws Exception {
		assertEquals("GET", parser.getVerb(simpleInput));
	}

	@Test
	public void itReturnsThePath() throws Exception {
		assertEquals("/", parser.getPath(simpleInput));
	}

	@Test
	public void itReturnsAComplexPath() throws Exception {
		String complexInput = "GET /model/something/1 HTTP/1.1";
		assertEquals("/model/something/1", parser.getPath(complexInput));
	}

	@Test
	public void itReturnsTheVersion() throws Exception {
		assertEquals("1.1", parser.getHttpVersion(simpleInput));
	}

	@Test
	public void itReturnsTheContent() throws Exception {
		String inputWithBody = "GET /model/something/1 HTTP/1.1\r\n\r\n" +
			"This is the body.";
		assertEquals("This is the body.", parser.getBody(inputWithBody));
	}

	@Test
	public void itReturnsAProperlyParsedRequest() throws Exception {
		String complexInput = "GET /model/something/1 HTTP/1.1\r\n" +
													 "Header1\r\n" +
													 "Header2\r\n" + "\r\n" +
			                     "This is the body.";
		mockReader.setMockInput(complexInput);
		Request request = parser.parseRequest();
		assertEquals("GET", request.getVerb());
		assertEquals("/model/something/1", request.getPath());
		assertEquals("1.1", request.getHttpVersion());
		assertEquals("This is the body.", request.getBody());
	}
}
