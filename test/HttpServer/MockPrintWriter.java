package HttpServer;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

public class MockPrintWriter extends PrintWriter {
	public MockPrintWriter(OutputStream out) {
		super(out);
	}
}
