package tictactoe;

import org.mebert.ttt.Game;

import java.util.HashMap;

public class GameRepository {
	private static int gameId;
	private static HashMap<Integer, Game> repo;

	public static void initialize() {
		gameId = 1;
		repo = new HashMap<Integer, Game>();
	}

	public static int getGameId() {
		return gameId;
	}

	public static Game fetch(int i) {
		return repo.get(i);
	}

	public static void store(Game game) {
		repo.put(gameId, game);
		game.setId(gameId);
		gameId++;
	}
}
