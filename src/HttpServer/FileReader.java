package HttpServer;

import java.io.*;

public class FileReader {

	public String readFile(String fileName) throws IOException {
		FileInputStream fileStream = new FileInputStream(fileName);
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
}
