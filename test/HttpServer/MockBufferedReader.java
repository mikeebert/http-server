package HttpServer;

import java.io.BufferedReader;
import java.io.Reader;

public class MockBufferedReader extends BufferedReader {

	private String[] mockInput = new String[100];
	private int readLineCount = 0;
	private int inputLineCount = 0;

	public MockBufferedReader(Reader in) {
		super(in);
	}

	public String readLine() {
		return mockInput[readLineCount++];
	}

	public void setMockInput(String string) {
		mockInput[inputLineCount++] = string;
	}
}
