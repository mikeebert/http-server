package HttpServer;

import java.io.IOException;
import java.net.ServerSocket;

public class MockServerSocket extends ServerSocket {

	public boolean isListening;

	public MockServerSocket() throws IOException {
		super();
	}

	public void startListening() {
		isListening = true;
	}

}
