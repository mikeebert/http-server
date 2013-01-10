package HttpServer;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

class MockPrintWriter extends PrintWriter {
	private Object receivedOutput;

	public MockPrintWriter() {
		super(new OutputStreamWriter(new MockOutputStream()), true);
	}

	public void println(Object buffer) {
		receivedOutput = buffer;
	}

	public Object getReceivedOutput() {
		return receivedOutput;
	}
}