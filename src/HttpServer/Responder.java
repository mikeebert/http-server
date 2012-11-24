package HttpServer;

import java.io.PrintWriter;
import java.net.FileNameMap;
import java.net.URLConnection;

public class Responder {

	private static final String VERSION = "HTTP/1.1";
	private static final String SPACE = " ";
	private static final String CRLF = "\r\n";
	private StringBuffer preparedResponse;
	private PrintWriter outputWriter;
	private String body;

	public Responder(PrintWriter writer) {
		outputWriter = writer;
	}

	public void prepare(Response response) {
		preparedResponse = new StringBuffer();
		setResponseStatusLine(response);
		setHeaders(response);
		addLineBreak();
		addBody(response);
	}

	private void addLineBreak() {
		preparedResponse.append(CRLF);
	}

	private void addBody(Response response) {
		preparedResponse.append(response.getContent());
	}

	private void setResponseStatusLine(Response response) {
		preparedResponse.append(VERSION + SPACE);
		preparedResponse.append(response.getStatusCode() + SPACE);
		preparedResponse.append(response.getStatusMessage() + CRLF);
	}

	private void setHeaders(Response response) {
//		FileNameMap fileNameMap = URLConnection.getFileNameMap();
//		String type = fileNameMap.getContentTypeFor(response.getResource());
		preparedResponse.append("Content-Type: text/html" + CRLF);
		preparedResponse.append("Content-Length: " + response.getContent().length() + CRLF);
	}

	public void sendResponse() {
		outputWriter.println(getPreparedResponse());
		outputWriter.flush();
	}

	public String getPreparedResponse() {
		return preparedResponse.toString();
	}
}
