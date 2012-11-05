package HttpServer;

public class Main {

	public static void main(String[] args) {
		int port = 5000;
		ConnectionHandler handler = new ConnectionHandler(port);
		HttpServer server = new HttpServer(handler);
		try{
			server.run();
		} catch (Exception e) {
			System.out.println("Can't execute run.");
		}
	}

}
