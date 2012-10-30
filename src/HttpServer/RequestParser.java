package HttpServer;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestParser {

	private BufferedReader reader;

	public RequestParser(BufferedReader inputReader) {
		reader = inputReader;
	}

	public Request parseRequest() throws IOException {
		Request request = new Request();
		String input = getInput();
		return constructRequest(request, input);
	}

	public String getInput() throws IOException {
		StringBuilder input = new StringBuilder();
		String line = reader.readLine();

		while ((line != null) && (!line.equals(""))) {
			input.append(line);
			line = reader.readLine();
		}

		return input.toString();
	}

	private Request constructRequest(Request request, String input) {
		request.setVerb(getVerb(input));
		request.setPath(getPath(input));
		request.setHttpVersion(getHttpVersion(input));
		request.setBody(getBody(input));
		return request;
	}

	public String getVerb(String input) {
		return firstLine(input).split(" ")[0];
	}

	public String getPath(String input) {
		return firstLine(input).split(" ")[1];
	}

	public String getHttpVersion(String input) {
		return firstLine(input).split(" ")[2].split("/")[1];
	}

	public String getBody(String input) {
		return input.split("\r\n\r\n")[1];
	}

	private String firstLine(String input) {
		return input.split("\r\n")[0];
	}
}

//
//	public String getHeader() throws IOException {
//		StringBuilder header = new StringBuilder();
//		String line = reader.readLine();
//
//		while (line != null && !line.equals("")) {
//			header.append(line).append("\n");
//			line = reader.readLine();
//		}
//		return header.toString();
//	}
