package HttpServer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Router {
	private String dir;
	private List<HashMap<String, String>> routes = new ArrayList<HashMap<String, String>>();

	public Router(String directory) {
		setDirectory(directory);
		try {
			setRoutes();
		} catch (IOException e) {
			System.out.println("Error reading routes file.");
		}
	}

	private void setRoutes() throws IOException {
		String[] routesArray = readFile(dir + "routes.txt").split("\n");
		int routes_header_row = 0;

		for (int i= routes_header_row + 1; i < routesArray.length; i++) {
			String[] route = routesArray[i].split(" \\| ");

			HashMap<String, String> pathResource = new HashMap<String, String>();
			pathResource.put("path", route[0]);
			pathResource.put("verb", route[1]);
			pathResource.put("resource", route[2]);
			routes.add(pathResource);
		}
	}

//	private boolean routesContain(String path) {
//		for(HashMap route: routes ) {
//			if(route.get("path").equals(path)) {
//				return true;
//			}
//		}
//		return false;
//	}

	public String getResourceFor(String path) {

		for(HashMap route: routes) {
			if(route.get("path").equals(path))
				return dir + route.get("resource").toString();
		}

		return dir + "404.html";
	}

	private String readFile(String filePath) throws IOException {
		FileInputStream fileStream = new FileInputStream(filePath);
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileStream));
		StringBuilder input = new StringBuilder();
		String line = fileReader.readLine();

		while ((line != null) && (!line.equals(""))) {
			input.append(line).append("\n");
			line = fileReader.readLine();
		}

		fileStream.close();
		return input.toString();
	}

	public List<HashMap<String, String>> getRoutes() {
		return routes;
	}

	private void setDirectory(String directory) {
		dir = directory;
	}
}
