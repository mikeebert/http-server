package tictactoe;

import HttpServer.ControllerInterface;
import HttpServer.FileReader;

import java.io.IOException;
import java.util.HashMap;

import org.mebert.ttt.Game;

public class GameController implements ControllerInterface {
	private static final String controllerName = "/game/";
	private static final int[] SPACES = {1,2,3,4,5,6,7,8,9};

	private String resourceContent = "";
	private FileReader fileReader;


	public GameController() {
		fileReader = new FileReader();
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

	private String newGame(String resourceDirectory) throws IOException {
		Game game = new Game();
		GameRepository.store(game);
		String boardFile = fileReader.readFile(resourceDirectory + "/board.html");
		String newBoard = addGameId(boardFile, game.getId());

		return  buildNewBoard(newBoard);
	}

	private String updateGame(String resourceDirectory, HashMap<String, String> params) throws IOException {
		Game game = GameRepository.fetch(Integer.parseInt(params.get("game-id")));
		String gameBoardHtml = updateBoardHtml(resourceDirectory, game.getId(), game.updateBoardWith(params.get("move")));

		if (game.isOver())
			return buildGameOverHtml(gameBoardHtml);
		else
			return gameBoardHtml;
	}

	private String buildNewBoard(String boardFile) {
		for(int space: SPACES) {
			boardFile = boardFile.replace("&&cell" + space, buildButtonFor(space));
		}
		return boardFile;
	}

	private String updateBoardHtml(String resourceDirectory, int gameId, String[] moves) throws IOException {
		String boardFile = fileReader.readFile(resourceDirectory + "/board.html");
		String gameBoardFile = boardFile.replace("&&gameId", String.valueOf(gameId));

		return buildUpdatedBoard(moves, gameBoardFile);
	}

	private String buildUpdatedBoard(String[] updatedMoves, String boardFile) {
		for (int i = 0;i < updatedMoves.length; i++) {
			if (isEmpty(updatedMoves[i])) {
				boardFile = boardFile.replace("&&cell" + move(i), buildButtonFor(move(i)));
			} else {
				boardFile = boardFile.replace("&&cell" + move(i), updatedMoves[i]);
			}
		}
		return boardFile;
	}

	private String buildGameOverHtml(String gameBoardHtml) {
		String noButtons = gameBoardHtml.replaceAll("<button.*</button>", "");
		String gameOverHtml = noButtons.replace("<p>Game In Progress</p>", "<p>Game Over</p>");
		return gameOverHtml;
	}

	private String buildButtonFor(int move) {
		return "<button name='move' value='" + move + "' type='submit'>move</button>";
	}

	private int move(int i) {
		return 1 + i;
	}

	private boolean isEmpty(String move) {
		return !move.equals("X") && !move.equals("O");
	}

	private String addGameId(String boardHtml, int gameId) {
		return boardHtml.replace("&&gameId", String.valueOf(gameId));
	}

	public void setFileReader(FileReader reader) {
		fileReader = reader;
	}

	public String getResourceContent() {
		return resourceContent;
	}
}
