package HttpServer;

import java.util.HashMap;

public class MockController implements ControllerInterface {

	private boolean initialized = false;

	public MockController() {
		this.initialized = true;
	}

	@Override
	public boolean isInitialized() {
		return initialized;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String updateWith(String resource, String requestVerb, HashMap<String, String> params, String postContent) {
		return null;
	}
}
