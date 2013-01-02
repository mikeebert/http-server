package HttpServer;

import java.io.*;

public class FileReader {

	private static final String FILEREADERROR = "Error reading file.";

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

	public byte[] getBinaryData(String binaryResource) {
		File file = new File(binaryResource);
		byte[] buffer = new byte[(int)file.length()];

		try {
			InputStream input = null;
			try {
				int totalBytesRead = 0;
				input = new BufferedInputStream(new FileInputStream(file));
				while (totalBytesRead < buffer.length) {
					int bytesRemaining = buffer.length - totalBytesRead;
					int bytesRead = input.read(buffer, totalBytesRead, bytesRemaining);

					if (bytesRead > 0) {
						totalBytesRead = totalBytesRead + bytesRead;
					}
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			System.out.println(FILEREADERROR);
		}
		return buffer;
	}
}
