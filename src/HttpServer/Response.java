package HttpServer;

public class Response {
	private String content;
	private int statusCode;
	private boolean notFound;
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

	public void setNotFound(boolean notFoundValue) {
		this.notFound = notFoundValue;
	}

	public boolean checkNotFound() {
		if (this.notFound) {
			return true;
		} else {
			return false;
		}
	}
}
