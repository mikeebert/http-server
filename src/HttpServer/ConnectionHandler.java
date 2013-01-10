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
	private Responder responder;
	private boolean keepServerSocketOpen = true;
	private boolean closeSocketAfterConnection = false;

	public ConnectionHandler(int portNumber, RouterInterface appRouter) {
		port = portNumber;
		router = appRouter;
		parser = new RequestParser();
		readerFactory = new ReaderFactory();
		builder = new ResponseBuilder();
		responder = new Responder();
	}

	public void openServerSocket() throws IOException {
		socket = new ServerSocket(port);
		printStatus("Listening on " + port);
	}

	public void handleNewConnections() {
		while (keepServerSocketOpen) {
			try {
				clientSocket = socket.accept();

				Request request = getRequest();
				Response response = getResponseFor(request);
				respondToRequest(response, clientSocket);

				printStatus("### Closing Socket ###.\n\n");
				clientSocket.close();

				checkIfServerSocketStaysOpen();

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
		builder.setupResourceController(router.getResourceFor(request.getURI()));
		return builder.buildResponseFor(request.getVerb(), request.getParams());
	}

	private void respondToRequest(Response response, Socket clientSocket) throws IOException {
		responder.prepare(response);
		responder.setOutput(clientSocket.getOutputStream());
		responder.sendResponse();
	}

	private void checkIfServerSocketStaysOpen() {
		if (closeSocketAfterConnection)
			keepServerSocketOpen = false;
	}

	private void printStatus(String status) {
		System.out.println(status);
	}

	public void setServerSocket(ServerSocket serverSocket) {
		socket = serverSocket;
	}

	public void setParser(RequestParser requestParser) {
		parser = requestParser;
	}

	public void setBuilder(ResponseBuilder responseBuilder) {
		builder = responseBuilder;
	}

	public void setResponder(Responder newResponder) {
		responder = newResponder;
	}

	public void setCloseSocketAfterConnection(boolean value) {
		closeSocketAfterConnection = value;
	}
}
