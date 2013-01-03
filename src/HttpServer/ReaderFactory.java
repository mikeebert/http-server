package HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReaderFactory {

	public BufferedReader getNewReader(Socket socket) throws IOException {
		return new BufferedReader(new InputStreamReader(input(socket)));
	}

	private InputStream input(Socket clientSocket) throws IOException {
		return clientSocket.getInputStream();
	}
}
