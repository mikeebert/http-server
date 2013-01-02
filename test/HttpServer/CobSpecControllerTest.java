package HttpServer;

import org.junit.Test;
import java.util.HashMap;
import static junit.framework.Assert.assertEquals;

public class CobSpecControllerTest {

	@Test
	public void itUpdatesContentWithParams() throws Exception {
		CobSpecController controller = new CobSpecController();
		MockFileReader mockReader = new MockFileReader();
		mockReader.setFileContents("<html>\n" +
															 "<body>\n" +
															 "&&this\n" +
															 "&&foo\n" +
															 "</body>\n" +
															 "</html>");


		controller.setFileReader(mockReader);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("this", "that");
		params.put("foo", "bar");

		String testString = controller.process("some-file", params);

		assertEquals("<html>\n<body>\nthat\nbar\n</body>\n</html>", testString);
	}
}
