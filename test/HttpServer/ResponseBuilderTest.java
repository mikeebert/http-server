package HttpServer;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

import java.util.HashMap;

public class ResponseBuilderTest {
	private static final String DIR = "/Users/ebert/Dropbox/projects/http-server/test/HttpServer/";

	@Test
	public void itInitializesWithResourceAndVerbAndParams() throws Exception {
		String resource = "/some-resource";
		HashMap<String, String> params = new HashMap<String, String>();
		String requestVerb = "GET";

		ResponseBuilder builder = new ResponseBuilder(resource, requestVerb, params);
		assertEquals(builder.getResource(), resource);
		assertEquals(builder.getRequestVerb(), requestVerb);
		assertEquals(builder.getParams(), params);
	}

	@Test
	public void itSetsUpAControllerResourceIfPassedOne() throws Exception {
		String resource = DIR + "/game/";
		ResponseBuilder builder = new ResponseBuilder(resource, null, null);
		assertEquals(true, builder.getController().isInitialized());
	}

	@Test
	public void itAddsResourceFileContentToResponse() throws Exception {
		ResponseBuilder builder = new ResponseBuilder(DIR + "test.html", "GET", null);

		Response response = builder.buildResponse();
		assertEquals("<h1>Hello World</h1>\n", response.getContent());
	}

	@Test
	public void itSetsStatusCodeForOK() throws Exception {
		ResponseBuilder builder = new ResponseBuilder(DIR + "test.html", "GET", null);

		Response response = builder.buildResponse();
		assertEquals(200, response.getStatusCode());
	}

	@Test
	public void itSetsResponseForNotFound() throws Exception {
		ResponseBuilder builder = new ResponseBuilder(DIR + "test404.html", "GET", null);

		Response response = builder.buildResponse();
		assertEquals(404, response.getStatusCode());
	}

	@Test
	public void itReturnsTrueForEchoRequests() throws Exception {
		ResponseBuilder builder = new ResponseBuilder("/echo-return", "GET", null);
		ResponseBuilder builder2 = new ResponseBuilder("/dynamic", "GET", null);
		assertEquals(true, builder.requestIsForEcho());
		assertEquals(true, builder2.requestIsForEcho());
	}

	@Test
	public void itUpdatesEchoResponsesWithParams() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("this", "that");
		params.put("foo", "bar");

		ResponseBuilder builder = new ResponseBuilder(DIR + "dynamic_test.html", "GET", params);
		Response response = builder.buildResponse();

		assertEquals("<html>\n<body>\nthat\nbar\n</body>\n</html>\n", response.getContent());
	}

}
