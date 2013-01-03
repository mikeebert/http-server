package HttpServer;

import java.util.HashMap;

public class MockResponseBuilder extends ResponseBuilder {
	private boolean receivedUriForController;
	private boolean returnedResponse;

	public void setupResourceController(String uri) {
		receivedUriForController = true;
	}

	public Response buildResponseFor(String verb, HashMap<String, String> params) {
		returnedResponse = true;
		return new MockResponse();
	}

	public boolean setupController() {
		return receivedUriForController;
	}

	public boolean returnedAResponse() {
		return returnedResponse;
	}
}
