package HttpServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Router {
	private String dir;
	private List<HashMap<String, String>> routes = new ArrayList<HashMap<String, String>>();
	private FileReader fileReader;

	private static final String ROUTESFILE = "routes.txt";
	private static final String PATH = "path";
	private static final String VERB = "verb";
	private static final String RESOURCE = "resource";
	private static final String PIPECHARACTER = " \\| ";
	private static final String FOUROHFOURFILE = "404.html";

	public Router(String directory) {
		fileReader =  new FileReader();
		setDirectory(directory);
	}

	public void createRoutesHash() {
		String[] routesArray = new String[0];
		try {
			routesArray = fileReader.readFile(dir + ROUTESFILE).split("\n");
		} catch (IOException e) {
			System.out.println("Error reading routes file.");
		}

		int routes_header_row = 0;

		for (int i= routes_header_row + 1; i < routesArray.length; i++) {
			String[] route = routesArray[i].split(PIPECHARACTER);

			HashMap<String, String> pathResource = new HashMap<String, String>();
			pathResource.put(PATH, route[0]);
			pathResource.put(VERB, route[1]);
			pathResource.put(RESOURCE, route[2]);
			routes.add(pathResource);
		}
	}

	public String getResourceFor(String path) {

		for(HashMap route: routes) {
			if(route.get(PATH).equals(path))
				return dir + route.get(RESOURCE).toString();
		}

		return dir + FOUROHFOURFILE;
	}

	public List<HashMap<String, String>> getRoutes() {
		return routes;
	}

	private void setDirectory(String directory) {
		dir = directory;
	}

	public void setFileReader(FileReader reader) {
		fileReader = reader;
	}
}
