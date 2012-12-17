package HttpServer;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class RequestTest {
	@Test
	public void itReturnsFalsePostStatusForGet() throws Exception {
		Request request = new Request();
		request.setVerb("GET");
		assertEquals(false, request.isPost());
	}

	@Test
	public void itReturnsTruePostStatusForPost() throws Exception {
		Request request = new Request();
		request.setVerb("POST");
		assertEquals(true, request.isPost());
	}
}
