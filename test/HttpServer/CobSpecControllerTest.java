package HttpServer;

import org.junit.Test;
import java.util.HashMap;
import static junit.framework.Assert.assertEquals;

public class CobSpecControllerTest {
	private static final String DIR = "/Users/ebert/Dropbox/projects/http-server/test/HttpServer/";


	@Test
	public void itUpdatesResourceWithParams() throws Exception {
		CobSpecController controller = new CobSpecController();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("this", "that");
		params.put("foo", "bar");

		String testString = controller.process(DIR + "test-echo-return", params);

		assertEquals("<html>\n<body>\nthat\nbar\n</body>\n</html>\n", testString);
	}
}
