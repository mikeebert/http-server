package tictactoe;

import org.junit.Test;

import org.mebert.ttt.Game;

import static junit.framework.Assert.assertEquals;

public class GameRepositoryTest {

	@Test
	public void itInitializesWithAGameID() throws Exception {
		GameRepository.initialize();
		assertEquals(1, GameRepository.getGameId());
	}

	@Test
	public void itStoresGame() throws Exception {
		GameRepository.initialize();
		Game game = new Game();
		GameRepository.store(game);
		assertEquals(game, GameRepository.fetch(1));
	}
}
