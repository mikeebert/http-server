package HttpServer;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class CobSpecControllerTest {

	private CobSpecController controller;
	private HashMap<String,String> params;

	@Before
	public void setUp() {
		controller = new CobSpecController();
		params = new HashMap<String, String>();
	}

	@Test
	public void itUpdatesContentWithOneParam() throws Exception {
		params.put("this", "that");

		String testString = controller.process("some-file", params);

		assertEquals("<html>\n<body>\nthis = that\n</body>\n</html>", testString);
	}

	@Test
	public void itUpdatesContentWithTwoParams() throws Exception {
		params.put("this", "that");
		params.put("other", "thing");

		String testString = controller.process("some-file", params);

		assertTrue(testString.contains("this = that"));
		assertTrue(testString.contains("other = thing"));

	}

	@Test
	public void itReturnsAnEmptyBodyForNullParams() throws Exception {
		String testString = controller.process("some-file", null);

		assertEquals("<html>\n<body>\n</body>\n</html>", testString);
	}
}
