package HttpServer;

import java.io.IOException;
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
	public String process(String resource, HashMap<String, String> params) throws IOException {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
