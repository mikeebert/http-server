package HttpServer;

import java.io.IOException;

public class HttpServer {

	private ConnectionHandler socketHandler;

	public HttpServer(ConnectionHandler handler) {
		socketHandler = handler;
	}

	public void run() {
		try {
			socketHandler.openServerSocket();
			socketHandler.handleNewConnections();
		} catch (IOException e) {
			System.out.println("Error running server");
			e.printStackTrace();
		}
	}
}
