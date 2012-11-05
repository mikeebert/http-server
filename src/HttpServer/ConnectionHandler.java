package HttpServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {
	private int port;
	private ServerSocket server;
	private Socket clientSocket;
	private Router router = new Router();

	public ConnectionHandler(int portNumber) {
		port = portNumber;
		//eventually instantiate this with a Router as well
	}

	public void open() throws IOException {
		server = new ServerSocket(port);
		printStatus("Socket open on " + port);

		handleNewClientSocket();
	}

	private void handleNewClientSocket() {
		while (true) {
			try {
			clientSocket = server.accept();
			Request request = getRequest(clientSocket, input(clientSocket));
			printStatus("Request received");

			processResponse(responseTo(request), output(clientSocket));

			printStatus("Closing socket");
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

	private void processResponse(Response response, OutputStream output) {
		Responder responder = new Responder(new PrintWriter(new OutputStreamWriter(output), true));
		responder.prepare(response);
		responder.sendResponse();
	}

	private InputStream input(Socket clientSocket) throws IOException {
		return clientSocket.getInputStream();
	}

	private OutputStream output(Socket clientSocket) throws IOException {
	  return clientSocket.getOutputStream();
	}

	private Response responseTo(Request request) {
		// TO START: have this return a generic response object
		// Eventually it returns a response with content
		Response response = router.getResponseFor(request);
		return null; //FIX
	}

	public int getPort() {
		return port;
	}

	public ServerSocket getServer() {
		return server;
	}

	private void printStatus(String status) {
		System.out.println(status);
	}
}
