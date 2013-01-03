package HttpServer;

import java.io.BufferedReader;
import java.util.HashMap;

public class MockParser extends RequestParser {
	private boolean readerReceived;
	private boolean requestReceived;

	public void setReader(BufferedReader reader) {
		readerReceived = true;
	}
	public Request receiveRequest() {
		requestReceived = true;
		return new MockRequest();
	}

	public boolean receivedReader() {
		return readerReceived;
	}

	public boolean receivedRequestCommand() {
		return requestReceived;
	}
}

class MockRequest extends Request {
	public String getURI() {return null;}
	public String getVerb() {return null;}
	public HashMap<String, String> getParams() {return null;}
}
