package HttpServer;

import java.io.IOException;

public class MockFileReader extends FileReader {

	private String fileContents;
	private byte[] binaryFileContents;

	public String readFile(String filePath) {
		return fileContents;
	}

	public void setFileContents(String fileContents) {
		this.fileContents = fileContents;
	}

	public void setBinaryFileContents(byte[] someImageData) {
		binaryFileContents = someImageData;
	}

	public byte[] getBinaryData(String resourcePath) {
		return binaryFileContents;
	}
}
