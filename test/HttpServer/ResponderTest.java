package HttpServer;

import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;

import static junit.framework.Assert.assertEquals;

public class ResponderTest {

	private MockPrintWriter mockWriter;
	private Responder responder;

	@Before
	public void setUp() throws Exception {
		mockWriter = new MockPrintWriter(new MockOutputStream());
		responder = new Responder(mockWriter);
	}

	@Test
	public void itAppendsStatusAndHeadersToPreparedResponse() throws Exception {
		Response response = new Response();
		response.setStatusCode(200);
		response.setResource("/Users/ebert/Dropbox/projects/http-server/public/CobSpec/index.html");
		String someContent = "This is some test content";
		response.setContent(someContent);

		String expectedResponse = "HTTP/1.1 200 OK\r\n" +
															"Content-Type: text/html\r\n" +
															"Content-Length: 25\r\n\r\n" +
															"This is some test content";

		responder.prepare(response);
		assertEquals(expectedResponse,responder.getPreparedResponse());
	}

}
