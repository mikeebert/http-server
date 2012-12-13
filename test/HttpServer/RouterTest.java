package HttpServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class RouterTest {
	private static final String DIR = "/Users/ebert/Dropbox/projects/http-server/test/HttpServer/";
	private Router router;

	@Before
	public void setUp() throws Exception {
		router = new Router(DIR);
	}

	@Test
	public void readsRoutesFromFile() throws Exception {
		List<HashMap<String, String>> routesList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> routesHash = new HashMap<String, String>();
		routesHash.put("path", "/");
		routesHash.put("verb", "GET");
		routesHash.put("resource", "index.html");
		routesList.add(routesHash);

		assertEquals(routesList, router.getRoutes());
	}

	@Test
	public void itReturnsResourcePathInRoutes() throws Exception {
		assertEquals(DIR + "index.html", router.getResourceFor("/"));
	}

	@Test
	public void itReturns404ResourceForPathNotFound() throws Exception {
		assertEquals(DIR + "404.html", router.getResourceFor("nonexistantpath"));
	}

}
