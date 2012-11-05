package HttpServer;

import org.junit.Test;

import java.io.PrintWriter;

public class ResponderTest {

	@Test
	public void itPreparesResponseWithStatusLine() throws Exception {
		MockPrintWriter mockWriter = new MockPrintWriter(new MockOutputStream());
		Responder responder = new Responder(mockWriter);
	}
}
