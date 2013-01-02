package HttpServer;

import java.io.IOException;

public class MockFileReader extends FileReader {

	private String fileContents;

	public String readFile(String filePath) {
		return fileContents;
	}

	public void setFileContents(String fileContents) {
		this.fileContents = fileContents;
	}
}
