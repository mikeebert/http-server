package HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServerSocket extends ServerSocket {

	public boolean isListening;
	private boolean returnedSocket = false;

	public MockServerSocket() throws IOException {
		super();
	}

	@Override
	public Socket accept() throws IOException {
		returnedSocket = true;
		return new MockSocket();
	}

	public void startListening() {
		isListening = true;
	}

}

class MockSocket extends Socket {

	public InputStream getInputStream() {
		return new MockInputStream();
	}
}

class MockInputStream extends InputStream {

	@Override
	public int read() throws IOException {
		return 0;
	}
}
