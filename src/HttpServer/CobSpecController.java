package HttpServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CobSpecController implements ControllerInterface {
	private FileReader fileReader;

	public CobSpecController() {
		fileReader = new FileReader();
	}

	@Override
	public String process(String resource, HashMap<String, String> params) throws IOException {
		return updateEchoContents(resource, params);  //To change body of implemented methods use File | Settings | File Templates.
	}

	private String updateEchoContents(String resource, HashMap<String, String> params) throws IOException {
		String updatedContents = fileReader.readFile(resource);

		for (Map.Entry<String, String> entry : params.entrySet())
			updatedContents = updatedContents.replace("&&" + entry.getKey(), entry.getValue());

		return updatedContents;
	}

	public void setFileReader(FileReader reader) {
		fileReader = reader;
	}
}
