package HttpServer;

import java.io.BufferedReader;
import java.io.Reader;

public class MockBufferedReader extends BufferedReader {

	private String[] mockInput = new String[100];
	private int readLineCount = 0;
	private int inputLineCount = 0;
	private String mockCharacter;
	private boolean fetchedPostContent;

	public MockBufferedReader(Reader in) {
		super(in);
	}

	public String readLine() {
		return mockInput[readLineCount++];
	}

	public void setMockInput(String string) {
		mockInput[inputLineCount++] = string;
	}

	@Override
	public int read(char[] characters, int buffer, int length) {
		fetchedPostContent = true;
		return length;
	}

	public String getMockCharacter() {
		return mockCharacter;
	}

	public void setMockCharacter(String mockCharacter) {
		this.mockCharacter = mockCharacter;
	}

	public boolean fetchedPostContent() {
		return fetchedPostContent;
	}
}
