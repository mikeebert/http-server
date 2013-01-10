package HttpServer;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static junit.framework.Assert.assertEquals;

public class ResponderTest {
	private static final String CRLF = "\r\n";
	Responder responder;
	Response response;

	@Before
	public void setUp() {
		responder = new Responder();
		responder.setOutput(new MockOutputStream());
		response = new Response();
	}

	@Test
	public void itAppendsStatusAndHeadersToPreparedResponse() throws Exception {
		response.setStatusCode(200);
		response.setType("text/html");
		response.setResource("some-file.html");

		responder.prepare(response);

		String expectedResponse = "HTTP/1.1 200 OK" + CRLF +
															"Content-Type: text/html" + CRLF +
															"Content-Length: 0" + CRLF + CRLF;

		assertEquals(expectedResponse,responder.getResponseHeaders());
	}

	@Test
	public void itAppendsLocationToRedirect() throws Exception {
		response.setStatusCode(302);
		response.setType("text/html");
		response.setResource("redirect");

		responder.prepare(response);

		String expectedHeaders = "HTTP/1.1 302 FOUND" + CRLF +
														 "Content-Type: text/html" + CRLF +
														 "Content-Length: 0" + CRLF +
														 "Location: http://localhost:5000/" + CRLF + CRLF;

		assertEquals(expectedHeaders, responder.getResponseHeaders());
	}

	@Test
	public void itSendsTextDataToPrintWriter() throws Exception {
		MockPrintWriter mockPrintWriter = new MockPrintWriter();
		responder.setPrintWriter(mockPrintWriter);

		response.setBodyTextContent("test content");
		response.setType("text/html");
		responder.prepare(response);

		responder.setResponseHeaders("test headers");
		responder.sendResponse();

		String expectedOutput = "test headers" + "test content";
		assertEquals(expectedOutput, mockPrintWriter.getReceivedOutput().toString());
	}

	@Test
	public void itSendsBinaryDataToOutputStream() throws Exception {
		MockFilterOutputStream mockFilterStream = new MockFilterOutputStream(new MockOutputStream());
		MockOutputStreamFactory mockFactory = new MockOutputStreamFactory();
		mockFactory.setMockFilterStreamToReturn(mockFilterStream);
		responder.setOutputStreamFactory(mockFactory);

		byte[] data = "somedata".getBytes();
		response.setBinaryContent(data);
		response.setType("image/html");
		responder.prepare(response);

		String header = "some-header";
		responder.setResponseHeaders(header);
		responder.sendResponse();

		byte[] testData = combine(header, data);

		for (int i=0 ; i < testData.length; i++) {
			assertEquals(testData[i], mockFilterStream.getWrittenData()[i]);
		}
	}

	private byte[] combine(String header, byte[] data) {
		byte[] binaryHeaders = header.getBytes();
		byte[] combinedData = new byte[binaryHeaders.length + data.length];
		System.arraycopy(binaryHeaders,0, combinedData,0,binaryHeaders.length);
		System.arraycopy(data,0, combinedData, binaryHeaders.length, data.length);
		return combinedData;
	}
}

class MockOutputStreamFactory extends OutputStreamFactory {
	private FilterOutputStream mockFilterOutputStream;

	public FilterOutputStream newStream(OutputStream outputStream) {
		return mockFilterOutputStream;
	}

	public void setMockFilterStreamToReturn(FilterOutputStream mockFilterOutputStream) {
		this.mockFilterOutputStream = mockFilterOutputStream;
	}
}