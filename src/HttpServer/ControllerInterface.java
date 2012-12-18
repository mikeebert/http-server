package HttpServer;

import org.omg.CORBA.StringHolder;

import java.util.HashMap;

public interface ControllerInterface {

	public boolean isInitialized();

	public String updateWith(String resource, String requestVerb, HashMap<String, String> params, String postContent);

}
