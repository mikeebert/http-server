package HttpServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {
	private int port;
	private ServerSocket server;
	private Socket clientSocket;
	private Router router = new Router();

	public ConnectionHandler(int portNumber, ServerSocket serverSocket) {
		port = portNumber;
		server = serverSocket;
	}

	public void createSockets(ServerSocket serverSocket) throws IOException {
		server = new ServerSocket(port);
		handleNewClientSocket();
	}

	private void handleNewClientSocket() {
		while (true) {
			try {
			clientSocket = server.accept();
			Request request = getRequest(clientSocket, input(clientSocket));
			processResponse(responseTo(request), output(clientSocket));
			clientSocket.close();
			} catch (Exception e) {
				//should this do something?
			}
		}
	}

	private Request getRequest(Socket clientSocket, InputStream input) throws IOException {
		RequestParser parser = new RequestParser(new BufferedReader(new InputStreamReader(input)));
		return parser.parseRequest();
	}

	private InputStream input(Socket clientSocket) throws IOException {
		return clientSocket.getInputStream();
	}

	private void processResponse(Response response, OutputStream output) {
		Responder responder = new Responder(new PrintWriter(new OutputStreamWriter(output), true));
		responder.prepare(response);
		responder.respond();
	}

	private OutputStream output(Socket clientSocket) throws IOException {
	  return clientSocket.getOutputStream();
	}

	private Response responseTo(Request request) {
		// TO START: have this return some sort of response object

		// this needs to return a response from whatever controller action it goes to
		router.handle(request);
		return null; //FIX
	}

	public int getPort() {
		return port;
	}

	public ServerSocket getServer() {
		return server;
	}
}
