package HttpServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CobSpecController implements ControllerInterface {

	@Override
	public String process(String resource, HashMap<String, String> params) throws IOException {
		return updateEchoContents(params);
	}

	private String updateEchoContents(HashMap<String, String> params) throws IOException {
		String body = "";

		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				body = body.concat(entry.getKey() + " = " + entry.getValue() + "\n");
			}
		}

		return "<html>\n<body>\n" + body + "</body>\n</html>";
	}

}
