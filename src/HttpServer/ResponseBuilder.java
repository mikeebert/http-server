package HttpServer;

import tictactoe.GameController;

import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;

public class ResponseBuilder {
	private static final String NOTFOUND = "404";

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

	//ideally this method would grab the controller by name from the resources directory.
	private void setupResourceController() {
		if (resourceRequiresGameController())
			resourceController = new GameController();
		else
			resourceController = new CobSpecController();
	}

	public void buildResponse() throws IOException {
		setResponseType();
		addContentToResponse();
		addStatusCodeToResponse(requestVerb);
	}

	private void setResponseType() {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String type = fileNameMap.getContentTypeFor(response.getResource());

		response.setType(type != null ? type : "text/html");
	}

	//need to come up with solution for stupid favicon requests
	private void addContentToResponse() throws IOException {
		if (isFaviconRequest())
			response.setTextContent(null);
		else if(isStaticResource())
			getStaticResourceContents();
		else
			getUpdatedResource();
	}

	private void getStaticResourceContents() throws IOException {
		if (responseIsImage())
			response.setTextContent("");
		else
			response.setTextContent(reader.readFile(response.getResource()));
	}

	public void getUpdatedResource() throws IOException {
		response.setTextContent(resourceController.process(response.getResource(), params));
	}

	private void addStatusCodeToResponse(String requestVerb) {
		if (response.getResource().contains(NOTFOUND))
			response.setStatusCode(404);
		else
			response.setStatusCode(200);
	}

	private boolean isFaviconRequest() {
		return response.getResource().endsWith(".ico");
	}

	private boolean isStaticResource() {
		return response.getResource().contains(".");
	}

	private boolean responseIsImage() {
		return response.getResource().endsWith(".jpeg") ||
					 response.getResource().endsWith(".jpg") ||
					 response.getResource().endsWith(".gif") ||
					 response.getResource().endsWith(".png");
	}

	private boolean resourceRequiresGameController() {
		return response.getResource().contains("game");
	}

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
