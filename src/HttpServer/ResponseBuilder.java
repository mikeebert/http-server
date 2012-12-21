package HttpServer;

import tictactoe.GameController;

import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;

public class ResponseBuilder {
	private static final String NOTFOUND = "404.html";
	private Response response;
	private HashMap<String, String> params;
	private String requestVerb;
	private ControllerInterface resourceController;
	private FileReader reader;

	public ResponseBuilder() {
		reader = new FileReader();
	}

	public Response buildResponseFor(String resourcePath, String method, HashMap<String,String> requestParams) throws IOException {
		response = new Response();
		response.setResource(resourcePath);
		requestVerb = method;
		params = requestParams;
		setupResourceController();
		buildResponse();

		return response;
	}

	public void buildResponse() throws IOException {
		setResponseType();
		addContentToResponse();
		addStatusCodeToResponse(requestVerb);
	}

	private void setResponseType() {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String type = fileNameMap.getContentTypeFor(response.getResource());

		response.setType(type == null ? "text/html" : type);
	}

	//ideally this method would grab the controller by name from the resources directory.
	private void setupResourceController() {
		if (response.getResource().contains("game"))
			resourceController = new GameController();
		else
			resourceController = new CobSpecController();
	}

	//AND statement is hack for favicon requests
	private void addContentToResponse() throws IOException {
		if(isStaticResource() && !response.getResource().endsWith(".ico")) {
			getStaticResourceContents();
		}	else {
			getUpdatedResource();
		}
	}

	private boolean isStaticResource() {
		return response.getResource().contains(".");
	}

	private void getStaticResourceContents() throws IOException {
		if (isImage(response.getResource()))
			response.setTextContent(null);
		else
			response.setTextContent(reader.readFile(response.getResource()));
	}

	public void getUpdatedResource() throws IOException {
		response.setTextContent(resourceController.process(response.getResource(), params));
	}

	private boolean isImage(String resource) {
		return resource.endsWith(".jpeg") ||
					 resource.endsWith(".gif") ||
					 resource.endsWith(".png");
	}

	private void addStatusCodeToResponse(String requestVerb) {
		if (resourceNotFound()) {
			response.setStatusCode(404);
		} else {
			response.setStatusCode(200);
		}
	}

	private boolean resourceNotFound() {
		return response.getResource().endsWith(NOTFOUND);
	}

	// Getters and Setters
	public String getResponseResource() {
		return response.getResource();
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public String getRequestVerb() {
		return requestVerb;
	}

	public ControllerInterface getController() {
		return resourceController;
	}

	public void setController(ControllerInterface controller) {
		resourceController = controller;
	}

	public Response getResponse() {
		return response;
	}

}
