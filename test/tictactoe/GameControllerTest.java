package tictactoe;

import HttpServer.MockFileReader;
import org.junit.Before;
import org.junit.Test;
import org.mebert.ttt.Board;
import org.mebert.ttt.Game;
import org.mebert.ttt.MockBoard;

import java.util.HashMap;
import static junit.framework.Assert.assertEquals;

public class GameControllerTest {
	private GameController controller;
	private MockFileReader mockReader;

	@Before
	public void setUp() {
		GameRepository.initialize();
		controller = new GameController();
		mockReader = new MockFileReader();
		controller.setFileReader(mockReader);
	}

	@Test
	public void itReturnsNewBoard() throws Exception {
		mockReader.fileContents = "test";

		assertEquals("test", controller.process("/something/game/new", null));
	}

	@Test
	public void itUpdatesBoardHtml() throws Exception {
		mockReader.fileContents = "&&cell1";
		controller.process("/game/new", null);
		String updatedContent = controller.getResourceContent();

		assertEquals("<button name='move' value='1' type='submit'>move</button>", updatedContent);
	}

	@Test
	public void itPassesParamsToUpdateExistingGame() throws Exception {
		MockGame mockGame = new MockGame();
		GameRepository.store(mockGame);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("game-id", "1");
		params.put("move", "1");

		controller.process("some-directory/game/update", params);

		assertEquals("1", mockGame.getReceivedMove());
	}

	@Test
	public void itUpdatesStringContentForMovesAndID() throws Exception {
		MockGame mockGame = new MockGame();
		GameRepository.store(mockGame);
		mockReader.fileContents = "&&cell1 | &&cell2 | &&cell3 | &&gameId";
		mockGame.setMockMoves(new String[]{"X", "2", "O"});
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("game-id", "1");
		params.put("move", "1");
		String expectedOutput = "X | <button name='move' value='2' type='submit'>move</button> | O | 1";

		controller.process("some-directory/game/update", params);

		assertEquals(expectedOutput, controller.getResourceContent());
	}
}
