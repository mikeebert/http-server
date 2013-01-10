package HttpServer;

import tictactoe.GameController;

import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;

public class ResponseBuilder {
	private static final String NOTFOUND = "404";
	private static final String REDIRECT = "REDIRECT";

	private Response response;
	private HashMap<String, String> params;
	private String resourcePath;
	private String requestVerb;
	private ControllerInterface resourceController;
	private FileReader fileReader;


	public ResponseBuilder() {
		fileReader = new FileReader();
	}

	//ideally this method would grab the controller by name from the resources directory.
	public void setupResourceController(String path) {
		resourcePath = path;

		if (resourcePath.contains("game"))
			resourceController = new GameController();
		else
			resourceController = new CobSpecController();
	}

	public Response buildResponseFor(String method, HashMap<String,String> requestParams) throws IOException {
		response = new Response();
		response.setResource(resourcePath);
		requestVerb = method;
		params = requestParams;
		buildResponse();

		return response;
	}

	public void buildResponse() throws IOException {
		setResponseType();
		addStatusCodeToResponse(requestVerb);
		addContentToResponse();
	}

	private void setResponseType() {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String type = fileNameMap.getContentTypeFor(response.getResource());

		response.setType(type != null ? type : "text/html");
	}

	private void addStatusCodeToResponse(String requestVerb) {
		if (response.getResource().contains(NOTFOUND))
			response.setStatusCode(404);
		else if (resourcePath.contains("redirect"))
			response.setStatusCode(302);
		else
			response.setStatusCode(200);
	}

	//need to come up with solution for stupid favicon requests
	private void addContentToResponse() throws IOException {
		if (resourceIsFavicon())
			response.setBodyTextContent(null);
		else if(resourcePath.contains("redirect"))
			response.setBodyTextContent("");
		else if(resourceIsStatic())
			getStaticResourceContents();
		else
			getUpdatedResource();
	}

	private void getStaticResourceContents() throws IOException {
		if (responseIsImage()) {
			response.setBinaryContent(fileReader.getBinaryData(resourcePath));
		} else
			response.setBodyTextContent(fileReader.readFile(response.getResource()));
	}

	public void getUpdatedResource() throws IOException {
		response.setBodyTextContent(resourceController.process(response.getResource(), params));
	}

	private boolean resourceIsFavicon() {
		return response.getResource().endsWith(".ico");
	}

	private boolean resourceIsStatic() {
		return response.getResource().contains(".");
	}

	private boolean responseIsImage() {
		return response.getResource().endsWith(".jpeg") ||
					 response.getResource().endsWith(".jpg") ||
					 response.getResource().endsWith(".gif") ||
					 response.getResource().endsWith(".png");
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

	public void setController(ControllerInterface controller) {
		resourceController = controller;
	}

	public Response getResponse() {
		return response;
	}

	public void setFileReader(FileReader reader) {
		fileReader = reader;
	}
}
