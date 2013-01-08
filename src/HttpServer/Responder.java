package HttpServer;

import org.apache.commons.io.FileUtils;

import java.io.*;

public class Responder {

	private static final String VERSION = "HTTP/1.1";
	private static final String SPACE = " ";
	private static final String CRLF = "\r\n";
	private static final String CONTENTLENGTH = "Content-Length: ";
	private static final String PREPAREDRESPONSE = "###Prepared Response: ###" + CRLF;

	private OutputStreamFactory outputStreamFactory;
	private PrintWriter printWriter;
	private StringBuffer responseHeaders;
	private OutputStream outputStream;
	private Response response;

	public Responder() {
		outputStreamFactory = new OutputStreamFactory();
	}

	public void prepare(Response newResponse) {
		response = newResponse;
		responseHeaders = new StringBuffer();
		setResponseStatusLine();
		setHeaders();
		addLineBreakAfterHeaders();
		System.out.println(PREPAREDRESPONSE + responseHeaders + CRLF);
	}

	private void setResponseStatusLine() {
		responseHeaders.append(VERSION + SPACE);
		responseHeaders.append(response.getStatusCode() + SPACE);
		responseHeaders.append(response.getStatusMessage() + CRLF);
	}

	private void setHeaders() {
		responseHeaders.append("Content-Type: " + response.getType() + CRLF);
		responseHeaders.append(contentLengthFor(response) + CRLF);
		if (response.getStatusCode() == 302) {
			responseHeaders.append("Location: http://localhost:5000/" + CRLF);
		}
	}

	private void addLineBreakAfterHeaders() {
		responseHeaders.append(CRLF);
	}

	private String contentLengthFor(Response response) {
		if (response.getTextContent() != null)
			return CONTENTLENGTH + response.getTextContent().length();
		else if (response.getBinaryContent() != null)
			return CONTENTLENGTH + response.getBinaryContent().length;
		else
			return "Content-Length: 0";
	}

	public void sendResponse() throws IOException {
		if (responseBodyIsText())
			sendTextResponse();
		else
			sendBinaryResponse();
	}

	private void sendTextResponse() {
		if (responseBodyIsText()) {
			responseHeaders.append(response.getTextContent());
			printWriter.println(responseHeaders);
			printWriter.flush();
		}
	}

	private void sendBinaryResponse() throws IOException {
		FilterOutputStream binaryOutputStream = outputStreamFactory.newStream(outputStream);
		byte [] binaryResponse = combineHeadersAndBinaryContent();

		binaryOutputStream.write(binaryResponse);
		binaryOutputStream.flush();
	}

	private byte[] combineHeadersAndBinaryContent() {
		byte [] binaryHeaders = responseHeaders.toString().getBytes();
		byte [] binaryContent = response.getBinaryContent();
		byte [] fullBinaryResponse = new byte[binaryHeaders.length + binaryContent.length];

		System.arraycopy(binaryHeaders, 0, fullBinaryResponse, 0, binaryHeaders.length);
		System.arraycopy(binaryContent, 0, fullBinaryResponse, binaryHeaders.length, binaryContent.length );
		return fullBinaryResponse;
	}

	private boolean responseBodyIsText() {
		return response.getType().contains("text");
	}

	public String getResponseHeaders() {
		return responseHeaders.toString();
	}

	public void setOutput(OutputStream output) {
		outputStream = output;
		printWriter = new PrintWriter(new OutputStreamWriter(outputStream), true);
	}

	public void setOutputStreamFactory(OutputStreamFactory factory) {
		outputStreamFactory = factory;
	}


	public void setResponseHeaders(String string) {
		responseHeaders = new StringBuffer();
		responseHeaders.append(string);
	}
}
