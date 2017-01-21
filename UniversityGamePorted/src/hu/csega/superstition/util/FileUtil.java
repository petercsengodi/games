package hu.csega.superstition.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class FileUtil {

	public static String workspaceRootOrTmp() {
		String path = System.getProperty("user.dir");
		String search = File.separatorChar + "UniversityGamePorted";
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
