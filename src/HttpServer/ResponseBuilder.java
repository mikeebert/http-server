package HttpServer;

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

	public ResponseBuilder(String resource, String requestVerb, HashMap<String, String> params) {
		this.requestVerb = requestVerb;
		setResource(resource);
		setRequestVerb(requestVerb);
		setParams(params);
	}

	public Response buildResponse() throws IOException {
		response = new Response();
		addContentToResponse(getResource());
		//setResponseType?
		addStatusCodeToResponse(requestVerb);
		return response;
	}

	private void addStatusCodeToResponse(String requestVerb) {
	//handle different status codes based on verbs and resources
		if(!getResource().endsWith(NOTFOUND)) {
			response.setStatusCode(200);
		} else {
			response.setStatusCode(404);
		}
	}

	private void addContentToResponse(String resource) throws IOException {
		//TWO BRANCHES:
		//FIRST: what type of content is it? text? image? css? it will affect send.
		//SECOND: is it a dynamic resource that gets updated?

		String resourceContents = readfile(getResource());

		if(params == null) {
			response.setContent(resourceContents);
		} else if (resource.endsWith("favicon.ico")) {
			response.setContent(null);
		}	else {
			response.setContent(update(resourceContents));
		}
	}

	public String update(String resourceContents) {
		String updatedContents = resourceContents;

		for (Map.Entry<String, String> entry : params.entrySet()) {
			updatedContents = updatedContents.replace("&&" + entry.getKey(), entry.getValue());
		}

		return updatedContents;
	}

	private String readfile(String resource) throws IOException {
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

	public void setResource(String resource) {
		this.resource = resource;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public String getRequestVerb() {
		return requestVerb;
	}

	public void setRequestVerb(String requestVerb) {
		this.requestVerb = requestVerb;
	}
}
