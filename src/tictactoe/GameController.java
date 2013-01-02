package tictactoe;

import HttpServer.ControllerInterface;
import HttpServer.FileReader;
import org.mebert.ttt.Game;

import java.io.IOException;
import java.util.HashMap;

public class GameController implements ControllerInterface {
	private static final String controllerName = "/game/";

	private String resourceContent = "";
	private TttViewBuilder tttViewBuilder;
	private FileReader fileReader;

	public GameController() {
		fileReader = new FileReader();
		tttViewBuilder = new TttViewBuilder();
	}

	public String process(String resource, HashMap<String, String> params) throws IOException {
		String resourceDirectory = resource.split(controllerName)[0];
		String method = resource.split(controllerName)[1];

		updateResourceContent(method, resourceDirectory, params);
		return resourceContent;
	}

	private void updateResourceContent(String method, String resourceDirectory, HashMap<String, String> params) throws IOException {
		if(method.equals("new"))
			resourceContent = newGame(resourceDirectory);
		else if (method.equals("update"))
			resourceContent = updateGame(resourceDirectory, params);
	}

	public String newGame(String resourceDirectory) throws IOException {
		Game game = new Game();
		GameRepository.store(game);
		return tttViewBuilder.buildNewBoard(resourceDirectory, game.getId());
	}

	public String updateGame(String resourceDirectory, HashMap<String, String> params) throws IOException {
		Game game = GameRepository.fetch(Integer.parseInt(params.get("game-id")));

		String boardHtml = tttViewBuilder.updateBoardHtml(resourceDirectory, game.getId(), game.updateBoardWith(params.get("move")));

		if (game.isOver())
			return tttViewBuilder.buildGameOverHtml(boardHtml);
		else
			return boardHtml;

	}

	public void setFileReader(FileReader reader) {
		fileReader = reader;
		tttViewBuilder.setFileReader(reader);
	}

	public String getResourceContent() {
		return resourceContent;
	}
}
