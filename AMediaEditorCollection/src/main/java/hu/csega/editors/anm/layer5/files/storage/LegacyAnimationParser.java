package hu.csega.editors.anm.layer5.files.storage;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.SAXException;

import hu.csega.games.library.xml.v1.XmlReader;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class LegacyAnimationParser {

	public static Object parseAnimationFile(InputStream stream) {
		if(stream == null) {
			logger.warning("Stream is null!");
			return null;
		}

		try {
			return XmlReader.read(stream);
		} catch (IOException | SAXException ex) {
			logger.error("Could not parse stream!", ex);
			return null;
		}
	}

	private static final Logger logger = LoggerFactory.getDefaultImplementation(LegacyAnimationParser.class);
}
