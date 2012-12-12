package CobSpec;

import java.io.*;
import java.net.FileNameMap;
import java.net.URLConnection;

import HttpServer.Request;
import HttpServer.Response;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.code.DebugInfoReadStream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class CobSpecRouterTest {

	private static final String DIR = "/Users/ebert/Dropbox/projects/http-server/public/CobSpec/";
	private CobSpecRouter router;
	private Request request;
	private Response response;

	@Before
	public void setUp() throws Exception {
		router = new CobSpecRouter(DIR);
		request = new Request();
		response = new Response();
	}

	@Test
	public void itReturnsASuccessfulRootPath() throws Exception {
		request.setVerb("GET");
		request.setPath("/");
		response.setContent(readFile("index.html"));
		Response routerResponse = router.setResponseFor(request);
		assertEquals(200, routerResponse.getStatusCode());
		assertEquals(response.getContent(), routerResponse.getContent());
	}

	@Test
	public void itReturns404AndNotFoundPage() throws Exception {
		request.setVerb("GET");
		request.setPath("/some/non/existent/path");
		Response routerResponse = router.setResponseFor(request);
		assertEquals(404, routerResponse.getStatusCode());
		assertTrue(routerResponse.getContent().contains("Not Found"));
	}

	@Test
	public void itReturnsFile1() throws Exception {
		request.setVerb("GET");
		request.setPath("/file1");
		response.setContent(readFile("/file1"));
		Response routerResponse = router.setResponseFor(request);
		assertEquals(200, routerResponse.getStatusCode());
		assertEquals(response.getContent(), routerResponse.getContent());
	}

	@Test
	public void itReturnsJpegImage() throws Exception {
		request.setVerb("GET");
		request.setPath("/image.jpeg");
		response.setContent(readFile("/image.jpeg"));
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String type = fileNameMap.getContentTypeFor("/index.html");
		System.out.println("The type is " + type);
		Response routerResponse = router.setResponseFor(request);
		assertEquals(200, routerResponse.getStatusCode());
		assertEquals(response.getContent(), routerResponse.getContent());
	}

	private String readFile(String fileName) throws IOException {
		FileInputStream fileStream = new FileInputStream(DIR + fileName);
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
