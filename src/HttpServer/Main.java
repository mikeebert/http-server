package HttpServer;

import tictactoe.GameRepository;

public class Main {
	private static int port = 9292;
	private static String directory = "/Users/ebert/Dropbox/projects/http-server/resources/CobSpec/";

	public static void main(String[] args) {
		parseCommands(args);

		Router router = new Router(directory);
		ConnectionHandler handler = new ConnectionHandler(port, router);
		HttpServer server = new HttpServer(handler);
		setUpRepository();

		try{
			server.run();
		} catch (Exception e) {
			System.out.println("Can't execute main.");
		}
	}

	private static void parseCommands(String[] args) {
		for(int i=0; i < args.length; i++ ) {
			if (args[i].equals("-p")) {
				port = Integer.parseInt(args[i+1]);
			} else if (args[i].equals("-d")) {
				directory = args[i+1];
			}
		}
	}

	private static void setUpRepository() {
		if (directory.contains("tictactoe"))
			GameRepository.initialize();
	}
}
