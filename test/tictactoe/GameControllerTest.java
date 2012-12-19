package tictactoe;

import HttpServer.MockFileReader;
import org.junit.Test;

import java.util.HashMap;
import static junit.framework.Assert.assertEquals;

public class GameControllerTest {

	@Test
	public void itReturnsNewBoard() throws Exception {
		GameController controller = new GameController();
		MockFileReader mockReader = new MockFileReader();
		mockReader.fileContents = "test";

		controller.setFileReader(mockReader);
		HashMap<String, String> params = new HashMap<String, String>();

		assertEquals("test", controller.process("/something/game/new", params));
	}

	@Test
	public void itUpdatesBoardFile() throws Exception {
		GameController controller = new GameController();
		MockFileReader mockReader = new MockFileReader();
		mockReader.fileContents = "&&cell1";

		controller.setFileReader(mockReader);
		controller.process("/game/new", null);

		String updatedContent = controller.getResourceContent();

		assertEquals("<button name='cell1' value='on' type='submit'>move</button>", updatedContent);
	}
}
