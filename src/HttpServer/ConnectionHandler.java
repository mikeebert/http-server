package HttpServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {
	private int port;
	private ServerSocket socket;
	private Socket clientSocket;
	private RouterInterface router;
	private RequestParser parser;
	private ReaderFactory readerFactory;
	private ResponseBuilder builder;

	public ConnectionHandler(int portNumber, RouterInterface appRouter) {
		port = portNumber;
		router = appRouter;
		parser = new RequestParser();
		readerFactory = new ReaderFactory();
		builder = new ResponseBuilder();
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

				Request request = getRequest();
				Response response = getResponseFor(request);
				processResponse(response, output(clientSocket));

				//THIS FUNCTION SHOULD REPLACE processResponse
				//sendResponse(response, clientSocket));

				printStatus("### Closing Socket ###.\n\n");
				clientSocket.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Request getRequest() throws IOException {
		parser.setReader(readerFactory.getNewReader(clientSocket));
		return parser.receiveRequest();
	}

	private Response getResponseFor(Request request) throws IOException {
		builder.setResourcePath(router.getResourceFor(request.getPath()));
		builder.setupResourceController();
		return builder.buildResponseFor(request.getVerb(), request.getParams());
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
