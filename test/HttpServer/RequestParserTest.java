package HttpServer;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

import java.io.StringReader;
import java.util.HashMap;


public class RequestParserTest {

	private RequestParser parser;
	private MockBufferedReader mockReader;
	private String simpleInput;

	@Before
	public void setUp() {
		mockReader = new MockBufferedReader(new StringReader(""));
		parser = new RequestParser(mockReader);
		simpleInput = "GET / HTTP/1.1\nHost: localhost: 8080\r\n";
	}

	@Test
	public void itReadsTheInput() throws Exception {
		mockReader.setMockInput(simpleInput);
		assertEquals(simpleInput + "\r\n", parser.getInput());
	}

	@Test
	public void returnsTheRequestVerb() throws Exception {
		assertEquals("GET", parser.getVerb(simpleInput));
	}

	@Test
	public void itReturnsTheResourcePath() throws Exception {
		assertEquals("/", parser.getPath(simpleInput));
	}

	@Test
	public void itReturnsTheResourcePathForComplexURL() throws Exception {
		assertEquals("/paramsurl", parser.getPath("GET /paramsurl?this=that HTTP/1.1"));
	}

	public void itReturnsParamsHashFromAComplexURL() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("this", "that");
		assertEquals(params, parser.getParams("GET /paramsurl?this=that HTTP/1.1"));
	}

	public void itSetsMultipleParams() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("variable_1", "12345987");
		params.put("variable_2", "some_value");
		assertEquals(params, parser.getParams("GET /some-script-url?variable_1=123459876&variable_2=some_value HTTP/1.1"));
	}

	@Test
	public void itReturnsTheVersion() throws Exception {
		assertEquals("1.1", parser.getHttpVersion(simpleInput));
	}

	@Test
	public void itReturnsAComplexPath() throws Exception {
		String input = "GET /model/something/1 HTTP/1.1";
		assertEquals("/model/something/1", parser.getPath(input));
	}


	@Test
	public void itReturnsTheHeaders() throws Exception {
		String complexInput = "GET /model/something/1 HTTP/1.1\r\n" +
													"Header1\r\n" +
													"Header2\r\n" + "\r\n" +
													"This is the body.";
		assertEquals("Header1\r\nHeader2\r\n", parser.getHeader(complexInput));
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
		assertEquals("Header1\r\nHeader2\r\n", request.getHeader());
		assertEquals("This is the body.\r\n", request.getBody());
	}

	@Test
	public void itReturnsParsedRequestWithNoBody() throws Exception {
		String inputNoBody = "GET /model/something/1 HTTP/1.1\r\n" +
													 "Header1\r\n" +
													 "Header2";
		mockReader.setMockInput(inputNoBody);
		Request request = parser.parseRequest();
		assertEquals("GET", request.getVerb());
		assertEquals("/model/something/1", request.getPath());
		assertEquals("1.1", request.getHttpVersion());
		assertEquals("Header1\r\nHeader2\r\n", request.getHeader());
		assertEquals(null, request.getBody());
	}


}
