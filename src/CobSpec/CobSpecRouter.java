package CobSpec;

import HttpServer.Request;
import HttpServer.Response;
import HttpServer.RouterInterface;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CobSpecRouter implements RouterInterface {
	private String dir;
	private HashMap<String, String> routes;
	private Response response;

	public CobSpecRouter(String directory) {
		setDirectory(directory);
		routes = new HashMap();
		routes.put("/", "index.html");
		routes.put("/index", "index.html");
		routes.put("/form", "index.html");
		routes.put("/file1", "file1");
		routes.put("/image.jpeg", "image.jpeg");
		routes.put("/image.png", "image.png");
		routes.put("/image.gif", "image.gif");
	}

	private void setDirectory(String directory) {
		dir = directory;
	}

	@Override
	public Response setResponseFor(Request request) throws IOException {
		response = new Response();
		getResponseFor(request);
		setStatusCode();
		return response;
	}

	private void getResponseFor(Request request) throws IOException {
		if (routes.containsKey(request.getPath())) {
			String resource = routes.get(request.getPath());
			response.setStatusCode(200);
//			response.setResource(dir + resource);
			addFileToResponse(resource);
		} else {
			response.setNotFound(true);
//			response.setResource(dir + "/404.html");
			addFileToResponse("/404.html");
		}
	}

	private void setStatusCode() {
		if (response.checkNotFound()) {
			response.setStatusCode(404);
		} else {
			response.setStatusCode(200);
		}
	}

	private void addFileToResponse(String path) throws IOException {
		System.out.println(dir + path);
		FileInputStream fileStream = new FileInputStream(dir + path);
		String fileContents = readFile(fileStream);
		fileStream.close();
		response.setContent(fileContents);
	}

	private String readFile(FileInputStream fileStream) throws IOException {
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
