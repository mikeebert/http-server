package HttpServer;

import java.io.OutputStream;

public class MockResponder extends Responder {
	private boolean setTheOutput;
	private boolean responsePrepared;
	private boolean responseSent;

	public void setOutput(OutputStream outputStream) {
		setTheOutput = true;
	}

	public void prepare(Response response) {
		responsePrepared = true;
	}
	public void sendResponse() {
		responseSent = true;
	}

	public boolean receivedOutputSetter() {
		return setTheOutput;
	}

	public boolean preparedAResponse() {
		return responsePrepared;
	}

	public boolean sentResponse() {
		return responseSent;
	}

}
