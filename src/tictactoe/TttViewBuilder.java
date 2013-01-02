package tictactoe;

import HttpServer.FileReader;
import java.io.IOException;

public class TttViewBuilder {
	private static final int[] SPACES = {1,2,3,4,5,6,7,8,9};
	private FileReader fileReader;
	private static final String CELL = "&&cell";
	private static final String GAMEINPROGRESSMESSAGE = "<p>Game In Progress</p>";
	private static final String GAMEOVERMESSAGE = "<p>Game Over</p>";
	private static final String BOARDFILE = "/board.html";
	private static final String XMOVE = "X";
	private static final String OMOVE = "O";

	public TttViewBuilder() {
	}

	public String buildNewBoard(String resourceDirectory, int gameId) throws IOException {
		String newBoardHtml = addIdToBoardFile(resourceDirectory, gameId);

		for(int space: SPACES) {
			newBoardHtml = newBoardHtml.replace(CELL + space, buildButtonFor(space));
		}
		
		return newBoardHtml;
	}

	public String updateBoardHtml(String resourceDirectory, int gameId, String[] moves) throws IOException {
		String cleanBoardHtml = addIdToBoardFile(resourceDirectory, gameId);
		return buildUpdatedBoard(moves, cleanBoardHtml);
	}

	private String addIdToBoardFile(String resourceDirectory, int gameId) throws IOException {
		String boardFile = fileReader.readFile(resourceDirectory + BOARDFILE);
		return addGameIdToHtml(boardFile, gameId);
	}

	private String buildUpdatedBoard(String[] updatedMoves, String boardFile) {
		for (int i = 0;i < updatedMoves.length; i++) {
			if (isEmpty(updatedMoves[i])) {
				boardFile = boardFile.replace(CELL + move(i), buildButtonFor(move(i)));
			} else {
				boardFile = boardFile.replace(CELL + move(i), updatedMoves[i]);
			}
		}
		
		return boardFile;
	}

	public String buildGameOverHtml(String gameBoardHtml) {
		String noMoveButtonsLeft = gameBoardHtml.replaceAll("<button.*</button>", "");
		return noMoveButtonsLeft.replace(GAMEINPROGRESSMESSAGE, GAMEOVERMESSAGE);
	}

	private String buildButtonFor(int move) {
		return "<button name='move' value='" + move + "' type='submit'>move</button>";
	}

	private int move(int i) {
		return 1 + i;
	}

	private boolean isEmpty(String move) {
		return !move.equals(XMOVE) && !move.equals(OMOVE);
	}

	private String addGameIdToHtml(String boardHtml, int gameId) {
		return boardHtml.replace("&&gameId", String.valueOf(gameId));
	}

	public void setFileReader(FileReader reader) {
		fileReader = reader;
	}
}
