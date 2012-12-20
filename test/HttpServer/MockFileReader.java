package HttpServer;

import java.io.IOException;

public class MockFileReader extends FileReader {

	public String fileContents = "";

	public String readFile(String filePath) {
		return fileContents;
	}
}
