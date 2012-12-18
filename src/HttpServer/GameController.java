package HttpServer;

import java.util.HashMap;

public class GameController implements ControllerInterface {

	private boolean initialized = false;

	public GameController() {
		setInitialized(true);
	}

	@Override
	public String updateWith(String resource, String requestVerb, HashMap<String, String> params, String postContent) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
}
