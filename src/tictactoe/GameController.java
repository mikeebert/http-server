package tictactoe;

import HttpServer.ControllerInterface;
import org.mebert.ttt.Game;

import java.io.IOException;
import java.util.HashMap;

public class GameController implements ControllerInterface {
	private static final String controllerName = "/game/";
	private TttViewBuilder tttViewBuilder;

	public GameController() {
		tttViewBuilder = new TttViewBuilder();
	}

	public String process(String resource, HashMap<String, String> params) throws IOException {
		String resourceDirectory = resource.split(controllerName)[0];
		String method = resource.split(controllerName)[1];

		return updateResourceContent(method, resourceDirectory, params);
	}

	private String updateResourceContent(String method, String resourceDirectory, HashMap<String, String> params) throws IOException {
		if(method.equals("new"))
			return newGame(resourceDirectory);
		else if (method.equals("update"))
			return updateGame(resourceDirectory, params);
		else
			return "";
	}

	private String newGame(String resourceDirectory) throws IOException {
		Game game = new Game();
		GameRepository.store(game);
		return tttViewBuilder.buildNewBoard(resourceDirectory, game.getId());
	}

	private String updateGame(String resourceDirectory, HashMap<String, String> params) throws IOException {
		Game game = GameRepository.fetch(Integer.parseInt(params.get("game-id")));
		String[] updatedMoves = game.updateBoardWith(params.get("move"));

		String boardHtml = tttViewBuilder.updateBoardHtml(resourceDirectory, game.getId(), updatedMoves);

		return updatedHtmlFor(game, boardHtml);
	}

	private String updatedHtmlFor(Game game, String boardHtml) {
		if (game.isOver())
			return tttViewBuilder.buildGameOverHtml(boardHtml);
		else
			return boardHtml;
	}

	public void setViewBuilder(TttViewBuilder viewBuilder) {
		tttViewBuilder = viewBuilder;
	}
}
