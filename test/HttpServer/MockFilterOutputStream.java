package HttpServer;

import java.io.FilterOutputStream;
import java.io.OutputStream;

class MockFilterOutputStream extends FilterOutputStream {

	private byte[] writtenData;

	public MockFilterOutputStream(OutputStream out) {
		super(out);
	}

	public void write(byte[] binaryData) {
		writtenData = binaryData;
	}

	public byte[] getWrittenData() {
		return writtenData;
	}
}
