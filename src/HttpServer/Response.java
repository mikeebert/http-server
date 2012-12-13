package HttpServer;

import java.util.HashMap;

public class Response {
	private String content;
	private String resource;
	private int statusCode;
	private String statusMessage;

	public void setContent(String htmlString) {
		content = htmlString;
	}

	public String getContent() {
		return content;
	}

	public void setStatusCode(int code) {
		statusCode = code;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		if (statusCode == 200) {
			return "OK";
		} else if (statusCode == 404) {
			return "NOT FOUND";
		} else
			return "STATUS MESSAGE";
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
}
