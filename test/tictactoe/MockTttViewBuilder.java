package tictactoe;

public class MockTttViewBuilder extends TttViewBuilder {

	private String receivedDirectory;
	private int receivedId;
	private String[] receivedMoves;

	@Override
	public String buildNewBoard(String resourceDirectory, int gameId) {
		receivedDirectory = resourceDirectory;
		receivedId = gameId;
		return "";
	}

	@Override
	public String updateBoardHtml(String resourceDirectory, int gameId, String[] moves) {
		receivedDirectory = resourceDirectory;
		receivedId = gameId;
		receivedMoves = moves;
		return "";
	}

	public String getReceivedDirectory() {
		return receivedDirectory;
	}

	public int getReceivedId() {
		return receivedId;
	}

	public String[] getReceivedMoves() {
		return receivedMoves;
	}
}
