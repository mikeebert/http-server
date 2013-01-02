package HttpServer;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ResponderTest {

	@Test
	public void itAppendsStatusAndHeadersToPreparedResponse() throws Exception {
		Responder responder = new Responder(new MockOutput(new MockOutputStream()));
		Response response = new Response();
		response.setStatusCode(200);
		response.setType("text/html");
		response.setResource("some-file.html");
		String someContent = "This is some test content";
		response.setTextContent(someContent);

		String expectedResponse = "HTTP/1.1 200 OK\r\n" +
															"Content-Type: text/html\r\n" +
															"Content-Length: 25\r\n\r\n" +
															"This is some test content";

		responder.prepare(response);
		assertEquals(expectedResponse,responder.getPreparedResponse());
	}

}
