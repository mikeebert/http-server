package HttpServer;

import tictactoe.GameController;

import javax.activation.MimeType;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
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

	public Response buildResponseFor(String resourcePath, String method, HashMap<String,String> requestParams) throws IOException {
		response = new Response();
		resource = resourcePath;
		response.setResource(resource);
		setupResourceController();
		requestVerb = method;
		params = requestParams;
		reader = new FileReader();
		buildResponse();
		return response;
	}

	//ideally this method would grab the controller from the resources directory.
	private void setupResourceController() {
		if (resource.contains("game"))
			responseController = new GameController();
//		responseController = getClass().getResource(resource);

	}

	public void buildResponse() throws IOException {
		response.setType(getResourceType());
		addContentToResponse();
		addStatusCodeToResponse(requestVerb);
	}

	private String getResourceType() {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		return fileNameMap.getContentTypeFor(resource);
	}

	private void addStatusCodeToResponse(String requestVerb) {
		if(!resource.endsWith(NOTFOUND)) {
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

	public boolean isStaticResource() {
		String[] fileExtensions = {".html", ".txt", ".jpeg", ".png", ".gif"};

		for (String ext: fileExtensions) {
			if (resource.endsWith(ext))
				return true;
		}

		return false;
	}

	private String getStaticResourceContents() throws IOException {
		//HACK for favicon
		if(resource.endsWith("favicon.ico"))
			return resource;
		else
			return getFile();
	}

	private String getFile() throws IOException {
		if (isImage(resource)) {
			return resource;
		} else
			return reader.readFile(resource);
	}

	public String getUpdatedResource() throws IOException {
		if (requestIsForEcho())
			return updateEchoContents();
		else
			return responseController.process(resource, params);
	}

	private String updateEchoContents() throws IOException {
		String updatedContents = getFile();

		for (Map.Entry<String, String> entry : params.entrySet())
			updatedContents = updatedContents.replace("&&" + entry.getKey(), entry.getValue());

		return updatedContents;
	}

	public boolean requestIsForEcho() {
		return resource.contains("echo-return") || resource.contains("dynamic");
	}

	private boolean isImage(String resource) {
		return resource.endsWith(".jpeg") || resource.endsWith(".gif") || resource.endsWith(".png");
	}

	// Getters and Setters
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

	public void setController(ControllerInterface controller) {
		responseController = controller;
	}

	public void setResource(String path) {
		resource = path;
	}

	public Response getResponse() {
		return response;
	}

}
