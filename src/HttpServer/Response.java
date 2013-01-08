package HttpServer;



public class Response {
	private String textContent = null;
	private String resource;
	private int statusCode;
	private String type;
	private byte[] binaryContent;

	public void setTextContent(String htmlString) {
		textContent = htmlString;
	}

	public String getTextContent() {
		return textContent;
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
		} else if (statusCode == 302) {
			return "FOUND";
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

	public void setType(String resourceType) {
		type = resourceType;
	}

	public String getType() {
		return type;
	}

	public byte[] getBinaryContent() {
		return binaryContent;
	}

	public void setBinaryContent(byte[] binaryContent) {
		this.binaryContent = binaryContent;
	}
}
