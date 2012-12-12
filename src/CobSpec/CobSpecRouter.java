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
	private HashMap<String, String> routes = new HashMap<String, String>();
	private Response response;

	public CobSpecRouter(String directory) {
		setDirectory(directory);
		routes.put("/", "index.html");
		routes.put("/index.html", "index.html");
		routes.put("/form", "index.html");
		routes.put("/file1", "file1");
		routes.put("/file2", "file2");
		routes.put("/image.jpeg", "image.jpeg");
		routes.put("/image.png", "image.png");
		routes.put("/image.gif", "image.gif");
		routes.put("/some-script-url", "echo-return.html");
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
			addFileToResponse(resource);
		} else {
			response.setNotFound(true);
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
		String fileContents = readFile(dir + path);
		response.setContent(fileContents);
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

}
