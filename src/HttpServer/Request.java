package HttpServer;

public class Request {
	private String verb;
	private String path;
	private String httpVersion;
	private String header;
	private String body;

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getVerb() {
		return verb;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
}
