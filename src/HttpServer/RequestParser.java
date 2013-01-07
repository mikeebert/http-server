package HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class RequestParser {

	private BufferedReader reader;
	private static final String CRLF = "\r\n";
	private static final String REQUESTRECEIVED = "### REQUEST RECEIVED ###: ";
	private static final String POSTHEADER = "POST Content: ";
	private static final String CONTENTLENGTH = "Content-Length";
	private static final String EQUALSIGN = "=";
	private static final String BLANKSPACE = " ";
	private static final String EMPTYSTRING = "";
	private static final String QUESTIONMARK = "\\?";


	public RequestParser() {

	}

	public void setReader(BufferedReader bufferedReader) {
		reader = bufferedReader;
	}

	public RequestParser(BufferedReader inputReader) {
		reader = inputReader;
	}

	public Request receiveRequest() throws IOException {
		Request request = new Request();
		String input = getInput();
		return constructRequest(request, input);
	}

	public String getInput() throws IOException {
		StringBuilder input = new StringBuilder();
		String line = reader.readLine();

		while ((line != null) && (!line.equals(EMPTYSTRING))) {
			input.append(line + CRLF);
			line = reader.readLine();
		}

		printStatus(REQUESTRECEIVED + CRLF + input);

		return input.toString();
	}

	private Request constructRequest(Request request, String input) throws IOException {
		String firstLine = getfirstLine(input);

		request.setVerb(getVerb(firstLine));
		request.setPath(getResourcePath(getFullPath(firstLine)));
		request.setHttpVersion(getHttpVersion(firstLine));
		request.setHeader(getHeader(input));
		request.setBody(getBody(input));
		request.setParams(getURLParams(firstLine));
		addPostContentToParams(request);

		return request;
	}

	private String getfirstLine(String input) {
		return input.split(CRLF)[0];
	}

	public String getVerb(String firstLine) {
		return firstLine.split(BLANKSPACE)[0];
	}

	public String getPath(String firstLine) {
		return getResourcePath(getFullPath(firstLine));
	}

	public String getResourcePath(String fullpath) {
		return fullpath.split(QUESTIONMARK)[0];
	}

	private String getFullPath(String firstLine) {
		if (firstLine.contains(BLANKSPACE))
			return firstLine.split(BLANKSPACE)[1];
		else
			return "";
	}

	public String getHttpVersion(String firstLine) {
		if (firstLine.contains(BLANKSPACE))
			return firstLine.split(BLANKSPACE)[2].split("/")[1];
		else
			return "";
	}

	public String getHeader(String input) {
		StringBuilder header = new StringBuilder();
		String[] lines = input.split(CRLF);
		int lineNumber = 1;
		String line = lines[lineNumber];

		while (!line.equals(EMPTYSTRING)) {
			header.append(line + CRLF);
			lineNumber++;
			if (lineNumber == lines.length)
				break;
			line = lines[lineNumber];
		}

		return header.toString();
	}

	public String getBody(String input) {
		if (input.split(CRLF + CRLF).length > 1)
			return input.split(CRLF + CRLF)[1];
		else
			return null;
	}

	public HashMap<String,String> getURLParams(String firstLine) {
		HashMap<String, String> params = new HashMap<String, String>();

		if (urlHasParams(getFullPath(firstLine))) {
			String fullPath = getFullPath(firstLine);
			String[] termsArray = fullPath.split(QUESTIONMARK)[1].split("&");
			return paramaterizeTerms(params, termsArray);
		}

		return null;
	}

	private boolean urlHasParams(String fullPath) {
		String[] splitPath = fullPath.split(QUESTIONMARK);
		return splitPath.length > 1;
	}

	private HashMap<String, String> paramaterizeTerms(HashMap<String, String> params, String[] termsArray) {
		if (params == null)
			params = new HashMap<String, String>();

		for (int i=0;i < termsArray.length; i++) {
			String param = termsArray[i];
			if (param.split(EQUALSIGN).length > 1)
				params.put(param.split(EQUALSIGN)[0], param.split(EQUALSIGN)[1]);
		}

		return params;
	}

	private void addPostContentToParams(Request request) throws IOException {
		if (request.isPost()) {
			String postContent = getPostContent(getContentLength(request.getHeader()));
			String[] termsArray = postContent.split("&");

			request.setParams(paramaterizeTerms(request.getParams(), termsArray));
		}
	}

	public String getPostContent(int length) throws IOException {
		char[] characters = new char[length];
		StringBuilder postContent = new StringBuilder();

		reader.read(characters, 0, length);
		postContent.append(new String(characters));

		printStatus(POSTHEADER + postContent.toString() + CRLF);
		return postContent.toString();
	}

	public int getContentLength(String headers) {
		String[] headerArray = headers.split(CRLF);
		int contentLength = 0;

		for(String header: headerArray) {
			if (header.startsWith(CONTENTLENGTH))
				contentLength = Integer.parseInt(header.split(": ")[1]);
		}

		return contentLength;
	}

	private void printStatus(String message) {
		System.out.println(message);
	}
}
