package HttpServer;

import java.io.*;

public class Responder {

	private static final String VERSION = "HTTP/1.1";
	private static final String SPACE = " ";
	private static final String CRLF = "\r\n";
	private static final String CONTENTTYPEHEADER = "Content-Type: ";
	private static final String CONTENTLENGTHHEADER = "Content-Length: ";
	private static final String PREPARINGRESPONSE = "###Preparing Response: ";	
	
	private PrintWriter writer;
	private StringBuffer preparedResponse;
	private String responseType;
	private String binaryResource;
	private OutputStream outputStream;
	private FileReader fileReader;

	public Responder() {
		fileReader = new FileReader();
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
		preparedResponse.append(CONTENTTYPEHEADER + response.getType() + CRLF);
		if (response.getTextContent() != null) {
			preparedResponse.append(CONTENTLENGTHHEADER + response.getTextContent().length() + CRLF);
		}
		System.out.println(PREPARINGRESPONSE + preparedResponse + CRLF);
	}

	public void sendResponse() throws IOException {
		if (responseType.contains("text"))
			sendTextResponse();
		else
			sendBinaryResponse();
	}

	private void sendTextResponse() {
		writer.println(preparedResponse);
		writer.flush();
	}

	// Needs to be split into getting the data and sending the data
	private void sendBinaryResponse() throws IOException {
		byte[] binaryData =  fileReader.getBinaryData(binaryResource);

		FilterOutputStream filterOutput = new FilterOutputStream(outputStream);
		filterOutput.write(binaryData);
		filterOutput.flush();
	}

	public String getPreparedResponse() {
		return preparedResponse.toString();
	}

	public void setOutput(OutputStream output) {
		outputStream = output;
		writer = new PrintWriter(new OutputStreamWriter(outputStream), true);
	}
}
