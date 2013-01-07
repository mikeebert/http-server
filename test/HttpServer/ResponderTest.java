package HttpServer;

import org.junit.Before;
import org.junit.Test;

import java.io.FilterOutputStream;
import java.io.OutputStream;

import static junit.framework.Assert.assertEquals;

public class ResponderTest {
	Responder responder;

	@Before
	public void setUp() {
		responder = new Responder();
		responder.setOutput(new MockOutput(new MockOutputStream()));
	}

	@Test
	public void itAppendsStatusAndHeadersToPreparedResponse() throws Exception {
		Response response = new Response();
		response.setStatusCode(200);
		response.setType("text/html");
		response.setResource("some-file.html");
		String someContent = "This is some test content";
		response.setTextContent(someContent);

		responder.prepare(response);

		String expectedResponse = "HTTP/1.1 200 OK\r\n" +
															"Content-Type: text/html\r\n" +
															"Content-Length: 25\r\n\r\n";

		assertEquals(expectedResponse,responder.getResponseHeaders());
	}

	@Test
	public void itSendsTextDataToPrintWriter() throws Exception {

	}

	@Test
	public void itSendsBinaryDataToOutputStream() throws Exception {
		MockOutputStreamFactory mockFactory = new MockOutputStreamFactory();
		MockFilterOutputStream mockFilterStream = new MockFilterOutputStream(new MockOutputStream());
		mockFactory.setMockFilterOutputStream(mockFilterStream);
		responder.setOutputStreamFactory(mockFactory);

		Response response = new Response();
		byte[] data = "somedata".getBytes();
		response.setBinaryContent(data);
		response.setType("image/html");
		responder.prepare(response);

		String header = "some-header";
		responder.setResponseHeaders(header);

		byte[] testData = combine(header, data);
		responder.sendResponse();

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

	public void setMockFilterOutputStream(FilterOutputStream mockFilterOutputStream) {
		this.mockFilterOutputStream = mockFilterOutputStream;
	}
}

class MockFilterOutputStream extends FilterOutputStream {

	private byte[] writtenData;

	public MockFilterOutputStream(OutputStream out) {
		super(out);
	}

	public void write(byte[] binaryData) {
		writtenData = binaryData;
	}

	public byte[] getWrittenData() {
		return writtenData;
	}
}