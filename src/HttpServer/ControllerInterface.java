package HttpServer;

import org.omg.CORBA.StringHolder;

import java.io.IOException;
import java.util.HashMap;

public interface ControllerInterface {

	public String process(String resource, HashMap<String, String> params) throws IOException;

}
