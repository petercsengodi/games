package hu.csega.klongun.imported;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;

public class FileUtil {

	public static String workspaceRootOrTmp() {
		String path = System.getProperty("user.dir");
		String search = File.separatorChar + "KlonGunPorted";
		int index = path.indexOf(search);
		if(index > 0)
			path = path.substring(0, index);
		else
			path = "/tmp";

		return path;
	}

	public static OutputStreamWriter openWriter(String fileName) throws IOException {
		return new OutputStreamWriter(new FileOutputStream(fileName), CHARSET);
	}

	public static OutputStreamWriter openWriter(OutputStream stream) throws IOException {
		return new OutputStreamWriter(stream, CHARSET);
	}

	public static BufferedReader openReader(String fileName) throws IOException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(fileName), CHARSET));
	}

	public static void collectFiles(String path, List<String> files) {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		if(listOfFiles == null || listOfFiles.length == 0)
			return;

		for(File f : listOfFiles) {
			String name = f.getName();
			if(name.equals(".") || name.equals(".."))
				continue;

			String fullPath = path + File.separator + name;
			if(f.isDirectory()) {
				collectFiles(fullPath, files);
				continue;
			}

			files.add(fullPath);
		}

	}

	public static String cleanUpName(String fileName) {
		int index = fileName.lastIndexOf('/');
		if(index > -1)
			fileName = fileName.substring(index + 1);

		if(fileName.endsWith(".t3d") || fileName.endsWith(".anm"))
			fileName = fileName.substring(0, fileName.length() - 4);

		if(fileName.endsWith(".x"))
			fileName = fileName.substring(0, fileName.length() - 2);

		if(fileName.endsWith(".bmp") || fileName.endsWith(".jpg") || fileName.endsWith(".png"))
			fileName = fileName.substring(0, fileName.length() - 4);

		if(fileName.endsWith(".jpeg"))
			fileName = fileName.substring(0, fileName.length() - 5);

		return fileName;
	}

	public static byte[] readAllBytes(String fileName) {
		File file = new File(fileName);
		byte[] array = new byte[(int)file.length()];

		try (InputStream ios = new FileInputStream(file)) {
			if ( ios.read(array) == -1 ) {
				throw new IOException("EOF reached while trying to read the whole file");
			}
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}

		return array;
	}

	public static final Charset CHARSET;

	static {
		Charset charsetToUse;

		try {
			charsetToUse = Charset.forName("UTF-8");
		} catch(UnsupportedCharsetException ex) {
			charsetToUse = Charset.defaultCharset();
		}

		CHARSET = charsetToUse;
	}
}
