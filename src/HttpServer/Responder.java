package HttpServer;

import java.io.PrintWriter;

public class Responder {

	private String preparedResponse;
	private PrintWriter outputWriter;
	private String body;

	public Responder(PrintWriter writer) {
		outputWriter = writer;
	}

	public void prepare(Response response) {
//		preparedResponse = statusLine(response);
//			+ headers(response) + CRLF + response.getContent();

		//package response
		//set status line
		//set headers
		//insert CRLF
		//insert response.content

	}

	// spike code to generate a Hello World Page
	public void sendResponse() {
		body = "<!DOCTYPE html>\r\n<html>\r\n<head>\r\n</head>\r\n<body>\r\n<h1>\r\nHello World\r\n</h1>\r\n</body>\r\n</html>\r\n";
		System.out.println("HTTP/1.1 200 OK" + "\r\n" + "<!doctype html><html><head></head><body><h1>Hello World</h1></body></html>");
		outputWriter.println("HTTP/1.1 200 OK" + "\r\n" +
												 "Content-Type: text/html" + "\r\n" +
												 "Content-Length: " + body.length() + "\r\n\r\n" +
													 body);
		//needs to send response with the output
	}
}
