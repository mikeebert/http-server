package HttpServer;

import tictactoe.GameController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
	private static final String NOTFOUND = "404.html";

	private Response response;
	private String resource;
	private HashMap<String, String> params;
	private String requestVerb;
	private ControllerInterface responseController;
	private FileReader reader;

	public ResponseBuilder(String resource, String requestVerb, HashMap<String, String> params) {
		this.resource = resource;
		this.responseController = setUpController();
		this.requestVerb = requestVerb;
		this.params = params;
		this.reader = new FileReader();
	}

	private ControllerInterface setUpController() {
		//If I have a resource identified by a string, how could I go into the directory to get the class of the file it references???
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
			return getFile(getResource());
	}

	public String getUpdatedResource() throws IOException {

		if (requestIsForEcho())
			return updateEchoContents();
		else
			return responseController.process(resource, params);
	}

	private String updateEchoContents() throws IOException {
		String updatedContents = getFile(getResource());

		for (Map.Entry<String, String> entry : params.entrySet())
			updatedContents = updatedContents.replace("&&" + entry.getKey(), entry.getValue());

		return updatedContents;
	}

	public boolean requestIsForEcho() {
		String resource = this.resource;
		return resource.contains("echo-return") || resource.contains("dynamic");
	}

	private String getFile(String resource) throws IOException {
		return reader.readFile(resource);
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
