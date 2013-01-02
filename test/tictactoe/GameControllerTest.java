package tictactoe;

import HttpServer.MockFileReader;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import static junit.framework.Assert.assertEquals;

public class GameControllerTest {
	private GameController controller;
	private MockFileReader mockReader;
	private MockTttViewBuilder mockViewBuilder;
	private static final HashMap<String, String> NULLPARAMS = null;
	private HashMap<String, String> params = new HashMap<String, String>();

	@Before
	public void setUp() {
		GameRepository.initialize();
		controller = new GameController();
		mockViewBuilder = new MockTttViewBuilder();
		controller.setViewBuilder(mockViewBuilder);
		params.put("game-id", "1");
		params.put("move", "1");
	}

	//ID assertion is hard-coded
	@Test
	public void itSendsDirectoryToViewBuilderForNewBoard() throws Exception {

		controller.process("some-directory/game/new", NULLPARAMS);
		assertEquals("some-directory", mockViewBuilder.getReceivedDirectory());
	}

	@Test
	public void itTellsGameRepoToStoreNewGame() throws Exception {
		GameRepository.initialize();

		controller.process("some-directory/game/new", NULLPARAMS);
		assertEquals(2, GameRepository.getNextGameId());
	}

	@Test
	public void itPassesMoveToGameForUpdate() throws Exception {
		MockGame mockGame = new MockGame();
		GameRepository.store(mockGame);
		controller.process("some-directory/game/update", params);

		assertEquals("1", mockGame.getReceivedMove());
	}

	@Test
	public void itPassesDirectoryGameIdAndUpdatedMovesToViewBuilder() throws Exception {
		MockGame mockGame = new MockGame();
		GameRepository.store(mockGame);

		String[] moves = {"X"};
		mockGame.setMockMoves(moves);

		controller.process("some-directory/game/update", params);

		assertEquals("some-directory", mockViewBuilder.getReceivedDirectory());
		assertEquals(1, mockViewBuilder.getReceivedId());
		assertEquals(moves, mockViewBuilder.getReceivedMoves());
	}

	@Test
	public void itChecksIfGameIsOverBeforeReturningABoard() throws Exception {
		MockGame mockGame = new MockGame();
		GameRepository.store(mockGame);

		controller.process("some-directory/game/update", params);
		assertEquals(true, mockGame.checkedForOver());
	}
}
