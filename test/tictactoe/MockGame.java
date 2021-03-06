package tictactoe;

import org.mebert.ttt.Game;
import org.mebert.ttt.Player;

public class MockGame extends Game {

	private String receivedMove;
	private Player receivedPlayer;
	private String[] mockMoves = {"X"};
	private boolean checkedForOver = false;

	public String[] updateBoard(Player player, String space) {
		setReceivedPlayer(player);
		setReceivedMove(space);
		return null;
	}

	public String[] updateBoardWith(String move) {
		setReceivedMove(move);
		return mockMoves;
	}

	public boolean isOver() {
		checkedForOver = true;
		return false;
	}

	private void setReceivedMove(String space) {
		receivedMove = space;
	}

	private void setReceivedPlayer(Player player) {
		receivedPlayer = player;
	}

	public Player getReceivedPlayer() {
		return receivedPlayer;
	}

	public String getReceivedMove() {
		return receivedMove;
	}

	public void setMockMoves(String[] mockMoves) {
		this.mockMoves = mockMoves;
	}

	public boolean checkedForOver() {
		return checkedForOver;
	}
}
