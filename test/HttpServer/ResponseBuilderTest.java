package HttpServer;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

import java.util.HashMap;

public class ResponseBuilderTest {
	private ResponseBuilder builder;
	private MockFileReader mockReader;
	private static final HashMap<String, String> NULLPARAMS = null;
	private static final String STATICRESOURCEPATH = "some-resource.html";
	private static final String IMAGERESOURCEPATH = "test.jpg";
	private static final String NOTFOUNDPATH = "test404.html";
	private static final String DYNAMICRESOURCEPATH = "dynamic-resource";

	@Before
	public void setUp() {
		builder = new ResponseBuilder(STATICRESOURCEPATH);
		mockReader = new MockFileReader();
		builder.setFileReader(mockReader);
	}

	@Test
	public void itSetsResourceAndVerbAndParams() throws Exception {
		String resource = STATICRESOURCEPATH;
		HashMap<String, String> params = new HashMap<String, String>();
		String requestVerb = "GET";

		builder.buildResponseFor(resource, requestVerb, params);
		assertEquals(builder.getResponseResource(), resource);
		assertEquals(builder.getRequestVerb(), requestVerb);
		assertEquals(builder.getParams(), params);
	}

	@Test
	public void itSetsResponseTypeForHTML() throws Exception {
		Response response = builder.buildResponseFor(STATICRESOURCEPATH, "GET", NULLPARAMS);
		assertEquals("text/html", response.getType());
	}

	@Test
	public void itSetsResponseTypeForJPG() throws  Exception {
		Response response = builder.buildResponseFor(IMAGERESOURCEPATH, "GET", NULLPARAMS);
		assertEquals("image/jpeg", response.getType());
	}

	@Test
	public void itAddsEmptyStringToTextContentForImage() throws Exception {
		Response response = builder.buildResponseFor(IMAGERESOURCEPATH, "GET", NULLPARAMS);
		assertEquals("", response.getTextContent());
	}

	@Test
	public void itAddsResourceFileContentToResponse() throws Exception {
		mockReader.setFileContents("<h1>Hello World</h1>");
		builder.buildResponseFor(STATICRESOURCEPATH, "GET", NULLPARAMS);

		Response response = builder.getResponse();

		assertEquals("<h1>Hello World</h1>", response.getTextContent());
	}

	@Test
	public void itSetsStatusCodeForOK() throws Exception {
		Response response = builder.buildResponseFor(STATICRESOURCEPATH, "GET", NULLPARAMS);

		assertEquals(200, response.getStatusCode());
	}

	@Test
	public void itSetsResponseForNotFound() throws Exception {
		Response response = builder.buildResponseFor(NOTFOUNDPATH, "GET", NULLPARAMS);

		assertEquals(404, response.getStatusCode());
	}

	@Test
	public void itAsksControllerForDynamicContent() throws Exception {
		MockController mockController = new MockController();
		builder.setController(mockController);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("this", "that");
		params.put("foo", "bar");

		builder.buildResponseFor(DYNAMICRESOURCEPATH, "GET", params);

		assertEquals(DYNAMICRESOURCEPATH,mockController.getReceivedResource() );
		assertEquals(params, mockController.getReceivedParams());
	}

}
