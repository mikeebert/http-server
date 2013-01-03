package HttpServer;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static junit.framework.Assert.assertEquals;


public class ConnectionHandlerTest {
	private static final int PORT = 9999;
	private ConnectionHandler handler;
	private MockServerSocket mockServerSocket;
	private MockParser mockParser = new MockParser();
	private MockResponseBuilder mockBuilder = new MockResponseBuilder();
	private MockResponder mockResponder = new MockResponder();

	@Before
	public void setUp() throws IOException {
		handler = new ConnectionHandler(PORT, new MockRouter());
		mockServerSocket = new MockServerSocket();

		handler.setServerSocket(mockServerSocket);
		handler.setParser(mockParser);
		handler.setBuilder(mockBuilder);
		handler.setResponder(mockResponder);
		handler.setCloseSocketAfterConnection(true);
	}

	@Test
	public void itCreatesANewClientSocket() throws Exception {
		handler.handleNewConnections();
		assertEquals(true, mockServerSocket.returnedSocket());
	}

	@Test
	public void itSetsReaderOnParser() throws Exception {
		handler.handleNewConnections();
		assertEquals(true, mockParser.receivedReader());
	}

	@Test
	public void itTellsParserToReceiveRequest() throws Exception {
		handler.handleNewConnections();
		assertEquals(true, mockParser.receivedRequestCommand());
	}

	@Test
	public void itSetsBuilderResourceController() throws Exception {
		handler.handleNewConnections();
		assertEquals(true, mockBuilder.setupController());
	}

	@Test
	public void itGetsResponseFromBuilder() throws Exception {
		handler.handleNewConnections();
		assertEquals(true, mockBuilder.returnedAResponse());
	}

	@Test
	public void itUsesResponderToSendResponse() {
		handler.handleNewConnections();
		assertEquals(true, mockResponder.receivedOutputSetter());
		assertEquals(true, mockResponder.preparedAResponse());
		assertEquals(true, mockResponder.sentResponse());
	}
}

