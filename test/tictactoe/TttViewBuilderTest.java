package tictactoe;

import HttpServer.MockFileReader;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class TttViewBuilderTest {
	private TttViewBuilder viewBuilder;
	private MockFileReader fileReader;

	@Before
	public void setUp() {
		viewBuilder = new TttViewBuilder();
		fileReader = new MockFileReader();
		viewBuilder.setFileReader(fileReader);
	}

	@Test
	public void itUpdatesNewBoardWithGameId() throws Exception {
		fileReader.setFileContents("&&gameId");
		int gameID = 10;

		assertEquals("10", viewBuilder.buildNewBoard("some-file-path", gameID));

	}

	@Test
	public void itUpdatesBoardHtmlWithButtonForMoveSpace() throws Exception {
		fileReader.setFileContents("&&cell1");
		int gameID = 1;
		String buttonHtml = "<button name='move' value='1' type='submit'>move</button>";

		assertEquals(buttonHtml, viewBuilder.buildNewBoard("some-file-path", gameID));
	}

	@Test
	public void itUpdatesStringContentForMovesAndID() throws Exception {
		fileReader.setFileContents("&&cell1 | &&cell2 | &&cell3 | &&cell4 - &&gameId");
		String[] mockMoves = {"X", "2", "O", "4"};
		int gameID = 1;

		String expectedOutput = "X | <button name='move' value='2' type='submit'>move</button> | O | <button name='move' value='4' type='submit'>move</button> - 1";

		assertEquals(expectedOutput, viewBuilder.updateBoardHtml("some-file-path", gameID, mockMoves));
	}
}
