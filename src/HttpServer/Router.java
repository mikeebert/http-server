package HttpServer;

public class Router {

	public Response getResponseFor(Request request) {
		// this will route the request based on the path...
		// set the content of the response and return the response.

		Response response = new Response();

		response.setContent("<!doctype html>\r\n<html>\r\n<head>\r\n</head>\r\n" +
												"<body>\r\nIt works\r\n</body>\r\n</html>\r\n");
		return response;
	}
}
