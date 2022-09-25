package hu.csega.editors.anm.layer5.files.storage;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class AnimationExportGenerator {

	public static void generateExport(Object object, OutputStream stream) {
		try {
			generateExport(object, new PrintStream(stream, true, "UTF-8"));
		} catch(UnsupportedEncodingException ex) {
			throw new RuntimeException("Could not get UTF-8 encoding!", ex);
		}
	}

	public static void generateExport(Object object, PrintStream stream) {
		stream.println("File is generated here...");
	}

}
