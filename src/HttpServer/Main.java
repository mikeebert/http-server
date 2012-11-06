package HttpServer;

import CobSpec.CobSpecRouter;

public class Main {

	public static void main(String[] args) {
		int port = 5000;
		RouterInterface router = new CobSpecRouter();
		ConnectionHandler handler = new ConnectionHandler(port, router);
		HttpServer server = new HttpServer(handler);
		try{
			server.run();
		} catch (Exception e) {
			System.out.println("Can't execute run.");
		}
	}

}
