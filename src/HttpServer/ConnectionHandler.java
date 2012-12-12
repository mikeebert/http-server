package HttpServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {
	private int port;
	private ServerSocket socket;
	private Socket clientSocket;
	private RouterInterface router;
	private ResponseBuilder builder;

	public ConnectionHandler(int portNumber, RouterInterface appRouter) {
		port = portNumber;
		router = appRouter;
	}

	public void open() throws IOException {
		socket = new ServerSocket(port);
		printStatus("Listening on " + port);

		handleNewConnection();
	}

	private void handleNewConnection() {
		while (true) {
			try {
				clientSocket = socket.accept();
				Request request = getRequest(clientSocket, input(clientSocket));
				Response response = routeRequest(request);
				processResponse(response, output(clientSocket));
				clientSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private InputStream input(Socket clientSocket) throws IOException {
		return clientSocket.getInputStream();
	}

	private OutputStream output(Socket clientSocket) throws IOException {
	  return clientSocket.getOutputStream();
	}

	private Request getRequest(Socket clientSocket, InputStream input) throws IOException {
		RequestParser parser = new RequestParser(new BufferedReader(new InputStreamReader(input)));
		return parser.parseRequest();
	}

	private void processResponse(Response response, OutputStream output) {
		Responder responder = new Responder(new PrintWriter(new OutputStreamWriter(output), true));
		responder.prepare(response);
		responder.sendResponse();

//		builder = new ResponseBuilder(response);
//		Response preparedResponse = builder.build(response);
//    ResponseSender sender = new ResponseSender(new PrintWriter(new OutputStreamWriter(output), true));
//		sender.sendResponse(preparedResponse);
	}

	private Response routeRequest(Request request) throws IOException {
		return router.setResponseFor(request);
	}

	public int getPort() {
		return port;
	}

	public ServerSocket getSocket() {
		return socket;
	}

	public void setSocket(ServerSocket newSocket) {
		socket = newSocket;
	}

	private void printStatus(String status) {
		System.out.println(status);
	}
}
