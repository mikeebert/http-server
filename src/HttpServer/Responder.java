package HttpServer;

import java.io.PrintWriter;

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
		preparedResponse.append("Content-Type: text/html" + CRLF);
		preparedResponse.append("Content-Length: " + response.getContent().length() + CRLF);
	}

	public void sendResponse() {
		outputWriter.println(getPreparedResponse());
		outputWriter.flush();

//
//		body = "<!DOCTYPE html>\r\n<html>\r\n<head>\r\n</head>\r\n<body>\r\n<h1>\r\nHello World\r\n</h1>\r\n</body>\r\n</html>\r\n";
//		System.out.println("HTTP/1.1 200 OK" + "\r\n" + "<!doctype html><html><head></head><body><h1>Hello World</h1></body></html>");
//		outputWriter.println("HTTP/1.1 200 OK" + "\r\n" +
//			"Content-Type: text/html" + "\r\n" +
//			"Content-Length: " + body.length() + "\r\n\r\n" +
//			body);

//		outputWriter.flush();
		//needs to send response with the output
	}

	public String getPreparedResponse() {
		return preparedResponse.toString();
	}
}
