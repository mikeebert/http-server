package HttpServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {
	private int port;
	private ServerSocket server;
	private Socket clientSocket;
	private RouterInterface router;

	public ConnectionHandler(int portNumber, RouterInterface appRouter) {
		port = portNumber;
		router = appRouter;
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
//		System.out.println("it got here with the response content: ");
//		System.out.println(response.getContent());
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

	private Response responseTo(Request request) throws IOException {
		return router.getResponseFor(request);
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
