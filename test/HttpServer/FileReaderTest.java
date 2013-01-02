package HttpServer;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertEquals;

public class FileReaderTest {
	private static final String DIR = "/Users/ebert/Dropbox/projects/http-server/test/HttpServer/";

	@Test
	public void itReadsATextFile() throws Exception {
		FileReader reader = new FileReader();

		assertEquals("<h1>Hello World</h1>\n", reader.readFile(DIR + "test.html"));
	}

	@Test
	public void itReadsABinaryFile() throws Exception {
		FileReader reader = new FileReader();
		File file = new File(DIR + "test.jpg");
		byte[] imageContent = FileUtils.readFileToByteArray(file);
		byte[] readData = reader.getBinaryData(DIR + "test.jpg");

		for (int i = 0; i < imageContent.length; i++) {
			assertEquals(imageContent[i], readData[i]);
		}
	}
}
