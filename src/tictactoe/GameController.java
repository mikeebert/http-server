package tictactoe;

import HttpServer.ControllerInterface;
import HttpServer.FileReader;

import java.io.IOException;
import java.util.HashMap;

public class GameController implements ControllerInterface {

	private boolean initialized = false;
	private String responseContent = "";
	private FileReader reader;

	public GameController() {
		setInitialized(true);
		reader = new FileReader();
	}

	public String process(String resource, HashMap<String, String> params) throws IOException {
		String[] directoryAndMethod = resource.split("/game/");
		String resourceDirectory = directoryAndMethod[0];
		String method = directoryAndMethod[1];

		if(method.equals("new"))
			responseContent = newGame(resourceDirectory);
		else if (method.equals("update"))
			responseContent = updateGame(resourceDirectory, params);

		return responseContent;
	}

	private String updateGame(String resourceDirectory, HashMap<String, String> params) {

		return responseContent;
	}

	private String newGame(String resourceDirectory) throws IOException {
		responseContent = reader.readFile(resourceDirectory + "/board.html");
		return responseContent;
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
}
