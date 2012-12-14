package HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

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

		printStatus("### REQUEST RECEIVED ###: " + CRLF + input);

		return input.toString();
	}

	private Request constructRequest(Request request, String input) {
		String firstLine = getfirstLine(input);

		request.setVerb(getVerb(firstLine));
		request.setPath(getResourcePath(getFullPath(firstLine)));
		request.setHttpVersion(getHttpVersion(firstLine));
		request.setHeader(getHeader(input));
		request.setBody(getBody(input));
		if (hasParams(getFullPath(firstLine)))
			request.setParams(getParams(firstLine));

		return request;
	}

	public String getVerb(String firstLine) {
		return firstLine.split(" ")[0];
	}

	public String getPath(String firstLine) {
		return getResourcePath(getFullPath(firstLine));
	}

	public String getResourcePath(String fullpath) {
		return fullpath.split("\\?")[0];
	}

	private String getFullPath(String firstLine) {
		return firstLine.split(" ")[1];
	}

	public String getHttpVersion(String firstLine) {
		return firstLine.split(" ")[2].split("/")[1];
	}

	private String getfirstLine(String input) {
		return input.split("\r\n")[0];
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

	public HashMap<String,String> getParams(String firstLine) {
		HashMap<String, String> params = new HashMap<String, String>();
		String fullPath = getFullPath(firstLine);
		String[] paramsArray = fullPath.split("\\?")[1].split("&");

		for (int i=0;i < paramsArray.length; i++) {
			String param = paramsArray[i];
			if (param.split("=").length > 1)
				params.put(param.split("=")[0], param.split("=")[1]);
		}

		return params;
	}

	private boolean hasParams(String fullPath) {
		String[] splitPath = fullPath.split("\\?");
		return splitPath.length > 1;
	}

	private void printStatus(String message) {
		System.out.println(message);
	}
}
