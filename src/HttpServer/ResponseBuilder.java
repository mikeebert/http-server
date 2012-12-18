package HttpServer;

import sun.jvm.hotspot.oops.ObjectHistogramElement;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
	private static final String NOTFOUND = "404.html";

	private Response response;
	private String resource;
	private HashMap<String, String> params;
	private String requestVerb;
	private String postContent;
	private ControllerInterface responseController;

	public ResponseBuilder(String resource, String requestVerb, HashMap<String, String> params, String postContent) {
		this.resource = resource;
		this.responseController = setUpController();
		this.requestVerb = requestVerb;
		this.params = params;
		this.postContent = postContent;
	}

	private ControllerInterface setUpController() {
		if (this.resource.contains("game"))
			return new GameController();
		else
			return null;
	}

	public Response buildResponse() throws IOException {
		response = new Response();
		addContentToResponse();
		//setResponseType?
		addStatusCodeToResponse(requestVerb);
		return response;
	}

	private void addStatusCodeToResponse(String requestVerb) {
		if(!getResource().endsWith(NOTFOUND)) {
			response.setStatusCode(200);
		} else {
			response.setStatusCode(404);
		}
	}

	private void addContentToResponse() throws IOException {
		//FIRST: what type of content is it? text? image? css? it will affect send.
		//SECOND: is it a dynamic resource that gets updated?

		if(isStaticResource()) {
			response.setContent(getStaticResourceContents());
		}	else {
			response.setContent(getUpdatedResource());
		}
	}

	private boolean isStaticResource() {
		return this.params == null;
	}

	private String getStaticResourceContents() throws IOException {
		if(this.resource.endsWith("favicon.ico"))
			return null;
		else
			return readFile(getResource());
	}

	public String getUpdatedResource() throws IOException {

		if (requestIsForEcho())
			return updateEchoContents();
		else
			return responseController.updateWith(resource, requestVerb, params, postContent);
	}

	private String updateEchoContents() throws IOException {
		String updatedContents = readFile(getResource());

		for (Map.Entry<String, String> entry : params.entrySet())
			updatedContents = updatedContents.replace("&&" + entry.getKey(), entry.getValue());

		return updatedContents;
	}

	public boolean requestIsForEcho() {
		String resource = this.resource;
		if (resource.contains("echo-return") || resource.contains("dynamic"))
			return true;
		else
			return false;
	}

	private String readFile(String resource) throws IOException {
		FileInputStream fileStream = new FileInputStream(resource);
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileStream));
		StringBuilder input = new StringBuilder();
		String line = fileReader.readLine();

		while ((line != null) && (!line.equals(""))) {
			input.append(line).append("\n");
			line = fileReader.readLine();
		}

		fileStream.close();
		return input.toString();
	}

	public String getResource() {
		return resource;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public String getRequestVerb() {
		return requestVerb;
	}

	public ControllerInterface getController() {
		return responseController;
	}
}
