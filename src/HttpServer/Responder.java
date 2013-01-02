package HttpServer;

import java.io.*;

public class Responder {

	private static final String VERSION = "HTTP/1.1";
	private static final String SPACE = " ";
	private static final String CRLF = "\r\n";
	private StringBuffer preparedResponse;
	private OutputStreamWriter outputWriter;
	private String responseType;
	private OutputStream outputStream;
	private String binaryResource;

	public Responder(OutputStream output) {
		this.outputStream = output;
	}

	public void prepare(Response response) {
		preparedResponse = new StringBuffer();
		responseType = response.getType();
		binaryResource = response.getResource();
		setResponseStatusLine(response);
		setHeaders(response);
		addLineBreak();
		addBody(response);
	}

	private void addLineBreak() {
		preparedResponse.append(CRLF);
	}

	private void addBody(Response response) {
		preparedResponse.append(response.getTextContent());
	}

	private void setResponseStatusLine(Response response) {
		preparedResponse.append(VERSION + SPACE);
		preparedResponse.append(response.getStatusCode() + SPACE);
		preparedResponse.append(response.getStatusMessage() + CRLF);
	}

	private void setHeaders(Response response) {
		preparedResponse.append("Content-Type: " + response.getType() + CRLF);
		if (response.getTextContent() != null) {
			preparedResponse.append("Content-Length: " + response.getTextContent().length() + CRLF);
		}
		System.out.println("###Preparing Response: " + preparedResponse + CRLF);
	}

	public void sendResponse() throws IOException {
		if (responseType.contains("text"))
			sendTextResponse();
		else
			sendBinaryResponse();
	}

	private void sendTextResponse() {
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream), true);
		writer.println(preparedResponse);
		writer.flush();
	}

	// Needs to be split into getting the data and sending the data
	private void sendBinaryResponse() throws IOException {
		FileReader reader = new FileReader();
		byte[] binaryData =  reader.getBinaryData(binaryResource);

		FilterOutputStream filterOutput = new FilterOutputStream(outputStream);
		filterOutput.write(binaryData);
		filterOutput.flush();
	}

	public String getPreparedResponse() {
		return preparedResponse.toString();
	}
}
