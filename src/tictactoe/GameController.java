package tictactoe;

import HttpServer.ControllerInterface;
import HttpServer.FileReader;

import java.io.IOException;
import java.util.HashMap;

public class GameController implements ControllerInterface {

	private boolean initialized = false;
	private String resourceContent = "";
	private FileReader fileReader;
	private static final int[] SPACES = {1,2,3,4,5,6,7,8,9};

	public GameController() {
		setInitialized(true);
		fileReader = new FileReader();
	}

	public String process(String resource, HashMap<String, String> params) throws IOException {
		String resourceDirectory = resource.split("/game/")[0];
		String method = resource.split("/game/")[1];

		getResource(method, resourceDirectory, params);
		return resourceContent;
	}

	private void getResource(String method, String resourceDirectory, HashMap<String, String> params) throws IOException {
		if(method.equals("new"))
			resourceContent = newGame(resourceDirectory);
		else if (method.equals("update"))
			resourceContent = updateGame(resourceDirectory, params);
	}

	private String newGame(String resourceDirectory) throws IOException {
		String boardFile = fileReader.readFile(resourceDirectory + "/board.html");

		for(int space: SPACES) {
			boardFile = boardFile.replace("&&cell" + space,
																		"<button name='cell" + space + "' value='on' type='submit'>move</button>");
		}

		return boardFile;
	}

	private String updateGame(String resourceDirectory, HashMap<String, String> params) {

		return resourceContent;
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public void setFileReader(FileReader reader) {
		fileReader = reader;
	}

	public String getResourceContent() {
		return resourceContent;
	}
}
