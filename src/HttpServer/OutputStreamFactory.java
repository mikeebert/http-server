package HttpServer;

import java.io.FilterOutputStream;
import java.io.OutputStream;

public class OutputStreamFactory {

	public FilterOutputStream newStream(OutputStream outputStream) {
		return new FilterOutputStream(outputStream);
	}
}
