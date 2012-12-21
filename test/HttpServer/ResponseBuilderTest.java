package HttpServer;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

import java.util.HashMap;

public class ResponseBuilderTest {
	private static final String DIR = "/Users/ebert/Dropbox/projects/http-server/test/HttpServer/";
	private static final String TESTFILE = "/Users/ebert/Dropbox/projects/http-server/test/HttpServer/test.html";
	private ResponseBuilder builder;
	private MockController mockController = new MockController();

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
		assertEquals(builder.getResource(), resource);
		assertEquals(builder.getRequestVerb(), requestVerb);
		assertEquals(builder.getParams(), params);
	}

	@Test
	public void itTestsForStaticContentByExtensionAndParams() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("foo", "bar");

		builder.buildResponseFor(TESTFILE, null, null);
		assertEquals(true, builder.isStaticResource());

		ResponseBuilder builder2 = new ResponseBuilder();
		builder2.setController(mockController);
		builder2.buildResponseFor(TESTFILE, null, params);
		assertEquals(true, builder2.isStaticResource());

		ResponseBuilder builder3 = new ResponseBuilder();
		builder3.setController(mockController);
		builder3.buildResponseFor("something/index", null, params);
		assertEquals(false, builder3.isStaticResource());

		ResponseBuilder builder4 = new ResponseBuilder();
		builder4.setController(mockController);
		builder4.buildResponseFor("something/index", null, null);
		assertEquals(false, builder4.isStaticResource());
	}

//	@Test
//	public void itSetsUpAControllerResourceIfPassedOne() throws Exception {
//		String resource = DIR + "/game/test.html";
//		builder.buildResponseFor(resource, null, null);
//
//		assertEquals(true, builder.getController().isInitialized());
//	}

	@Test
	public void itAddsResourceFileContentToResponse() throws Exception {
		builder.buildResponseFor(DIR + "test.html", "GET", null);

		Response response = builder.getResponse();

		assertEquals("<h1>Hello World</h1>\n", response.getContent());
	}

	@Test
	public void itSetsStatusCodeForOK() throws Exception {
		Response response = builder.buildResponseFor(DIR + "test.html", "GET", null);

		assertEquals(200, response.getStatusCode());
	}

	@Test
	public void itSetsResponseForNotFound() throws Exception {
		Response response = builder.buildResponseFor(DIR + "test404.html", "GET", null);

		assertEquals(404, response.getStatusCode());
	}

	@Test
	public void itReturnsTrueForEchoRequests() throws Exception {
		builder.setResource("/something/echo-return");

		assertEquals(true, builder.requestIsForEcho());
	}

	@Test
	public void itUpdatesEchoResponsesWithParams() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("this", "that");
		params.put("foo", "bar");

		Response response = builder.buildResponseFor(DIR + "dynamic_test", "GET", params);

		assertEquals("<html>\n<body>\nthat\nbar\n</body>\n</html>\n", response.getContent());
	}

}
