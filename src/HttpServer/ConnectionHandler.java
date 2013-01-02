package HttpServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {
	private int port;
	private ServerSocket socket;
	private Socket clientSocket;
	private Router router;

	public ConnectionHandler(int portNumber, Router appRouter) {
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

				Request request = getRequest(clientSocket);
				Response response = getResponseFor(request);
				processResponse(response, output(clientSocket));

				//THIS FUNCTION SHOULD REPLACE processResponse
				//sendResponse(response, clientSocket));

				printStatus("### Closing Socket.\n\n");
				clientSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Request getRequest(Socket clientSocket) throws IOException {
		RequestParser parser = new RequestParser(new BufferedReader(new InputStreamReader(input(clientSocket))));
		return parser.parseRequest();
	}
//
	private Response getResponseFor(Request request) throws IOException {
		String resourcePath = router.getResourceFor(request.getPath());
		ResponseBuilder builder = new ResponseBuilder(resourcePath);

		return builder.buildResponseFor(resourcePath,
																		request.getVerb(),
																		request.getParams());
	}
//
//	private void sendResponse(Response response, Socket clientSocket) {
//		Responder responder = new ResponderFactory(response.getType(), clientSocket);
//		responder.sendResponse(response);
//	}

	private void processResponse(Response response, OutputStream output) throws IOException {
		Responder responder = new Responder(output);
		responder.prepare(response);
		responder.sendResponse();
	}

	private InputStream input(Socket clientSocket) throws IOException {
		return clientSocket.getInputStream();
	}

	private OutputStream output(Socket clientSocket) throws IOException {
		return clientSocket.getOutputStream();
	}

	private void printStatus(String status) {
		System.out.println(status);
	}
}
