package CobSpec;

import java.io.*;
import HttpServer.Request;
import HttpServer.Response;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class CobSpecRouterTest {

	private CobSpecRouter router;
	private Request request;
	private Response response;

	@Before
	public void setUp() throws Exception {
		router = new CobSpecRouter();
		request = new Request();
		response = new Response();
	}

	@Test
	public void itReturnsASuccessfulRootPath() throws Exception {
		request.setVerb("GET");
		request.setPath("/");
		response.setContent(readFile("/index.html"));
		Response routerResponse = router.getResponseFor(request);
		assertEquals(200, routerResponse.getStatusCode());
		assertEquals(response.getContent(), routerResponse.getContent());
	}

	@Test
	public void itReturns404AndNotFoundPage() throws Exception {
		request.setVerb("GET");
		request.setPath("/some/non/existent/path");
		Response routerResponse = router.getResponseFor(request);
		assertEquals(404, routerResponse.getStatusCode());
		assertTrue(routerResponse.getContent().contains("Not Found"));
	}

	private String readFile(String fileName) throws IOException {
		FileInputStream fileStream = new FileInputStream("/Users/ebert/Dropbox/projects/http-server/viewsCobSpec/" + fileName);
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileStream));
		StringBuilder input = new StringBuilder();
		String line = fileReader.readLine();

		while ((line != null) && (!line.equals(""))) {
			input.append(line).append("\n");
			line = fileReader.readLine();
		}

		return input.toString();
	}


}
