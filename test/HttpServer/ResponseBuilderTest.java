package HttpServer;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

import java.util.HashMap;

public class ResponseBuilderTest {
	private static final String DIR = "/Users/ebert/Dropbox/projects/http-server/test/HttpServer/";
	private ResponseBuilder builder;
	private MockController mockController = new MockController();
	private static final HashMap<String, String> NULLPARAMS = null;

	@Before
	public void setUp() {
		builder = new ResponseBuilder();
		builder.setController(mockController);
	}

	@Test
	public void itSetsResourceAndVerbAndParams() throws Exception {
		String resource = DIR + "test.html";
		HashMap<String, String> params = new HashMap<String, String>();
		String requestVerb = "GET";

		builder.buildResponseFor(resource, requestVerb, params);
		assertEquals(builder.getResponseResource(), resource);
		assertEquals(builder.getRequestVerb(), requestVerb);
		assertEquals(builder.getParams(), params);
	}

	@Test
	public void itSetsResponseTypeForHTML() throws Exception {
		Response response = builder.buildResponseFor(DIR + "test.html", "GET", NULLPARAMS);
		assertEquals("text/html", response.getType());
	}

	@Test
	public void itSetsResponseTypeForJPG() throws  Exception {
		Response response = builder.buildResponseFor(DIR + "test.jpg", "GET", NULLPARAMS);
		assertEquals("image/jpeg", response.getType());
	}

	@Test
	public void itAddsEmptyStringToTextContentForImage() throws Exception {
		Response response = builder.buildResponseFor(DIR + "test.jpg", "GET", NULLPARAMS);
		assertEquals("", response.getTextContent());
	}

	@Test
	public void itAddsResourceFileContentToResponse() throws Exception {
		builder.buildResponseFor(DIR + "test.html", "GET", NULLPARAMS);

		Response response = builder.getResponse();

		assertEquals("<h1>Hello World</h1>\n", response.getTextContent());
	}

	@Test
	public void itSetsStatusCodeForOK() throws Exception {
		Response response = builder.buildResponseFor(DIR + "test.html", "GET", NULLPARAMS);

		assertEquals(200, response.getStatusCode());
	}

	@Test
	public void itSetsResponseForNotFound() throws Exception {
		Response response = builder.buildResponseFor(DIR + "test404.html", "GET", NULLPARAMS);

		assertEquals(404, response.getStatusCode());
	}

	@Test
	public void itUpdatesEchoResponsesWithParams() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("this", "that");
		params.put("foo", "bar");

		Response response = builder.buildResponseFor(DIR + "test-echo-return", "GET", params);

		assertEquals("<html>\n<body>\nthat\nbar\n</body>\n</html>\n", response.getTextContent());
	}

}
