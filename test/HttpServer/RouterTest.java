package HttpServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class RouterTest {
	private static final String DIR = "some-directory-path";
	private Router router;
	private MockFileReader mockReader;

	@Before
	public void setUp() throws Exception {
		router = new Router("some-directory-path");
		mockReader = new MockFileReader();
	}

	@Test
	public void createsRoutesHashFromFileContents() throws Exception {
		router.setFileReader(mockReader);
		mockReader.setFileContents("PATH|RESOURCE\n" + "/ | GET | index.html");

		HashMap<String, String> routesHash = new HashMap<String, String>();
		routesHash.put("path", "/");
		routesHash.put("verb", "GET");
		routesHash.put("resource", "index.html");

		List<HashMap<String, String>> routesList = new ArrayList<HashMap<String, String>>();
		routesList.add(routesHash);

		router.createRoutesHash();
		assertEquals(routesList, router.getRoutes());
	}

	@Test
	public void itReturnsResourcePathInRoutes() throws Exception {
		router.setFileReader(mockReader);
		mockReader.setFileContents("PATH|RESOURCE\n" + "/ | GET | index.html");
		
		router.createRoutesHash();
		assertEquals(DIR + "index.html", router.getResourceFor("/"));
	}

	@Test
	public void itReturns404ResourceForPathNotFound() throws Exception {
		router.createRoutesHash();
		assertEquals(DIR + "404.html", router.getResourceFor("nonexistantpath"));
	}

}
