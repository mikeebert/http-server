package CobSpec;

import HttpServer.Request;
import HttpServer.Response;
import HttpServer.RouterInterface;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CobSpecRouter implements RouterInterface {
	private final static String DIR = "/Users/ebert/Dropbox/projects/http-server/viewsCobSpec/";
	private Response response;

	@Override
	public Response getResponseFor(Request request) throws IOException {
		response = new Response();
		getResponseBodyFor(request);
		setStatusCode();
		return response;
	}

	private void getResponseBodyFor(Request request) throws IOException {
		if (request.getPath().equals("/hello_world")) {
			addFileToResponse(request.getPath());
		} else if (request.getPath().equals("/")) {
			addFileToResponse("/index");
		} else {
			response.setNotFound(true);
			addFileToResponse("/404");
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
		FileInputStream fileStream = new FileInputStream( DIR + path + ".html");
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
