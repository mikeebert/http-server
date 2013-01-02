package HttpServer;

import java.io.IOException;
import java.util.HashMap;

public class MockController implements ControllerInterface {

	private boolean initialized = false;
	private String receivedResource;
	private HashMap<String, String> receivedParams;

	public MockController() {
		this.initialized = true;
	}

	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public String process(String resource, HashMap<String, String> params) throws IOException {
		receivedResource = resource;
		receivedParams = params;

		return null;
	}

	public String getReceivedResource() {
		return receivedResource;
	}

	public HashMap<String, String> getReceivedParams() {
		return receivedParams;
	}
}
