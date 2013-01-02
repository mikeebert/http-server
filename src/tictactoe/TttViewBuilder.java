package tictactoe;

import HttpServer.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class TttViewBuilder {
	private static final int[] SPACES = {1,2,3,4,5,6,7,8,9};
	private FileReader fileReader;

	public TttViewBuilder() {
	}

	public String buildNewBoard(String resourceDirectory, int gameId) throws IOException {
		String boardFile = fileReader.readFile(resourceDirectory + "/board.html");
		String newBoard = addGameId(boardFile, gameId);

		for(int space: SPACES) {
			boardFile = boardFile.replace("&&cell" + space, buildButtonFor(space));
		}
		return boardFile;
	}

	public String updateBoardHtml(String resourceDirectory, int gameId, String[] moves) throws IOException {
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

	public String buildGameOverHtml(String gameBoardHtml) {
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
}
