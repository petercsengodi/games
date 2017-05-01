package hu.csega.games.vbg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class VBGFramework {

	private static final String BASE_HEADER_VALUE = "latest-version";
	private static final String BASE_HEADER_NAME = "X-Game-Action";
	private static final String BASE_HTTP_METHOD = "GET";
	private static final String BASE_ENDPOINT = "http://csega.hu/games/";

	private static final String PROPERTY_MACHINE_ID = "machine";
	private static final String PROPERTY_CURRENT_VERSION = "version";
	private static final String PROPERTY_DEFAULT_VERSION = "v00.00.0000";
	private static final String FOLDER_DOWNLOAD = "download";

	private static final String RUN_MAIN_CLASS = "hu.csega.games.vbg.VirtualBoredGames";

	private static final String PROPERTY_FILE_NAME = "vgb.properties";

	private static Properties properties = new Properties();
	private static String currentlyUsedVersion = null;
	private static String machineId = null;

	public static void main(String[] args) {
		boolean propertiesChanged = false;
		File propertiesFile = new File(PROPERTY_FILE_NAME);

		try {

			loadProperties(propertiesFile);

			if(currentlyUsedVersion == null || currentlyUsedVersion.isEmpty()) {
				currentlyUsedVersion = PROPERTY_DEFAULT_VERSION;
				propertiesChanged = true;
			}

			System.out.println("Current version: " + currentlyUsedVersion);

			if(machineId == null || machineId.isEmpty()) {
				machineId = UUID.randomUUID().toString();
				propertiesChanged = true;
			}

			boolean downloadNeeded = false;
			boolean overrideNeeded = false;

			VBGVersionAndFileList result = downloadDescription();
			if(result == null || result.version == null || result.version.isEmpty()) {
				System.out.println("Couldn't download fresh version.");
			} else {
				downloadNeeded = true; // in case of missing files
				System.out.println("Latest version: " + result.version);
				overrideNeeded = !result.version.equals(currentlyUsedVersion);
				System.out.println("Override: " + overrideNeeded);
			}

			File downloadDir = new File(FOLDER_DOWNLOAD);
			if(!downloadDir.exists()) {
				// create download folder if not existsm yet
				downloadDir.mkdir();
			}

			if(downloadNeeded) {
				boolean successful = refreshFiles(result, overrideNeeded);
				if(successful && overrideNeeded) {
					currentlyUsedVersion = result.version;
					propertiesChanged = true;
				}
			}

			if(propertiesChanged) {
				savePropertyFile(propertiesFile);
			}

			System.out.println("Trying to run...");
			run(FOLDER_DOWNLOAD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadProperties(File propertiesFile) {
		if(propertiesFile.exists()) {
			System.out.println("Loading current properties");
			try (InputStream inStream = new FileInputStream(propertiesFile)) {
				properties.load(inStream);
				currentlyUsedVersion = properties.getProperty(PROPERTY_CURRENT_VERSION);
				machineId = properties.getProperty(PROPERTY_MACHINE_ID);
			} catch (IOException e) {
				System.out.println("Error occurred during loading properties!");
				e.printStackTrace();
			}
		} else {
			System.out.println("No properties file to load.");
		}

	}

	private static void savePropertyFile(File propertiesFile) {
		properties.setProperty(PROPERTY_CURRENT_VERSION, currentlyUsedVersion);
		properties.setProperty(PROPERTY_MACHINE_ID, machineId);
		try(FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile)) {
			properties.store(fileOutputStream, null);
		} catch (Exception e) {
			System.out.println("Exception during saving property files!");
			e.printStackTrace();
		}
	}

	private static VBGVersionAndFileList downloadDescription() {
		VBGVersionAndFileList ret = new VBGVersionAndFileList();

		HttpURLConnection connection = null;
		BufferedReader rd = null;

		try {

			// Create connection
			URL url = new URL(BASE_ENDPOINT + "index.php");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(BASE_HTTP_METHOD);
			connection.setRequestProperty(BASE_HEADER_NAME, BASE_HEADER_VALUE);
			connection.setUseCaches(false);

			// Get Response
			InputStream is = connection.getInputStream();
			rd = new BufferedReader(new InputStreamReader(is));

			String line = rd.readLine();
			while (line != null && line.isEmpty())
				line = rd.readLine();

			if (line == null)
				throw new RuntimeException("No version read!");

			ret.version = line.trim();

			String tmp;
			while ((line = rd.readLine()) != null) {
				tmp = line.trim();
				if (!tmp.isEmpty())
					ret.files.add(tmp);
			}

		} catch (Exception ex) {

			System.out.println("Exception occurred during downloading description.");
			ex.printStackTrace();
			return null; // work offline, please

		} finally {

			try {
				if(rd != null)
					rd.close();
			} catch(Exception ex) {
				System.out.println("Could not close buffer.");
			}

			try {
				if(connection != null)
					connection.disconnect();
			} catch(Exception ex) {
				System.out.println("Could not disconnect connection.");
			}

		}

		return ret;
	}

	private static boolean refreshFiles(VBGVersionAndFileList result, boolean overrideNeeded) {
		boolean successful = true;
		byte[] buffer = new byte[1024];
		List<String> files = result.files;

		for(String file : files) {
			String fileName = FOLDER_DOWNLOAD + File.separator + file;
			File toFile = new File(fileName);

			if(toFile.exists()) {
				if(overrideNeeded) {
					System.out.println("File " + file + " already exists, overriding.");
				} else {
					System.out.println("File " + file + " already exists. Checking next file instead.");
					continue;
				}
			}

			System.out.println("Downloading " + file + " to " + toFile.getAbsolutePath());

			HttpURLConnection connection = null;
			try (FileOutputStream stream = new FileOutputStream(toFile)) {

				URL url = new URL(BASE_ENDPOINT + file);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod(BASE_HTTP_METHOD);
				connection.setUseCaches(false);

				// Get Response
				InputStream is = connection.getInputStream();

				int len;
				while((len = is.read(buffer, 0, buffer.length)) >= 0) {
					if(len > 0) {
						stream.write(buffer, 0, len);
					}
				}

			} catch(Exception ex) {
				successful = false;
				throw new RuntimeException(ex);
			} // closes stream
		}

		return successful;
	}

	private static void run(String folder) {
		try {

			List<URL> urls = new ArrayList<>();
			File downloadFolder = new File(folder);
			String[] filesInFolder = downloadFolder.list();

			for(String fileName : filesInFolder) {
				if(fileName == null || fileName.isEmpty() || !fileName.endsWith(".jar"))
					continue;

				String jarFile = folder + File.separator + fileName;
				URL jar = new File(jarFile).toURI().toURL();
				urls.add(jar);
			}

			String mainClass = RUN_MAIN_CLASS;
			URL[] classPath = urls.toArray(new URL[urls.size()]);

			try (URLClassLoader classLoader = new URLClassLoader( classPath )) {
		        Class<?> aClass = classLoader.loadClass(mainClass);
		        Method method = aClass.getMethod("main", new Class<?>[]{ String[].class });
		        method.invoke(null, new Object[] { null });
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    }
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
