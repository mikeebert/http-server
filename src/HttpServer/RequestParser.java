package HttpServer;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestParser {

	private BufferedReader reader;
	private static final String CRLF = "\r\n";

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
			input.append(line + CRLF);
			line = reader.readLine();
		}

		System.out.println("### REQUEST RECEIVED ###: " + CRLF + input);

		return input.toString();
	}

	private Request constructRequest(Request request, String input) {
		request.setVerb(getVerb(input));
		request.setPath(getPath(input));
		request.setHttpVersion(getHttpVersion(input));
		request.setHeader(getHeader(input));
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

	private String firstLine(String input) {
		return input.split("\r?\n")[0];
	}

	public String getHeader(String input) {
		StringBuilder header = new StringBuilder();
		String[] lines = input.split(CRLF);
		int lineNumber = 1;
		String line = lines[lineNumber];

		while (!line.equals("")) {
			header.append(line + CRLF);
			lineNumber++;
			if (lineNumber == lines.length)
				break;
			line = lines[lineNumber];
		}

		return header.toString();
	}

	public String getBody(String input) {
		if (input.split("\r\n\r\n").length > 1)
			return input.split("\r\n\r\n")[1];
		else
			return null;
	}

	private void printStatus(String message) {
		System.out.println(message);
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
