package hu.csega.agomol.connection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Properties;

public class ClientConnection {

	public void loadProperties() {
		props = new Properties();

		String userHome = System.getProperty("user.home");
		System.out.println("User home: " + userHome);
		String fullPath = userHome + "/agomol.properties";

		try {
			File f = new File(fullPath);
			if(f.exists()) {
				props.load(new FileInputStream(f));
			} else {
				System.out.println("Not found: " + fullPath);
				System.exit(1);
			}

			String version = getContent(null);
			System.out.println("Server version: " + version);
			String die = getContent("roll");
			System.out.println("Rolled die: " + die);
		} catch(Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	public void connectionInfo() {

	}

	private String getContent(String action) throws Exception {
		String ending;
		if(action == null || action.length() == 0)
			ending = "";
		else
			ending = "?action=" + action;

		URLConnection urlConnection = new URL(props.getProperty("endpoint")+ending).openConnection();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b;

		try (InputStream inputStream = urlConnection.getInputStream()) {
			while((b = inputStream.read()) >= 0) {
				baos.write(b);
			}
		}

		String content = new String(baos.toByteArray(), Charset.forName("UTF-8"));
		return content;
	}

	private Properties props;
}
