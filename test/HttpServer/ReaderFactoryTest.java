package HttpServer;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static junit.framework.Assert.assertEquals;

public class ReaderFactoryTest {

	@Test
	public void itReturnsBufferedReaderFromSocket() throws Exception {
		ReaderFactory factory = new ReaderFactory();
		ServerSocket socket = new MockServerSocket();
		Socket clientSocket = socket.accept();

		BufferedReader reader = factory.getNewReader(clientSocket);
		BufferedReader testReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		assertEquals(testReader.getClass(), reader.getClass());
	}
}
