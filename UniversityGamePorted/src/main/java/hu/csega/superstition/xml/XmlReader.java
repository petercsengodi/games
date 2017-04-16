package hu.csega.superstition.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class XmlReader {

	public static Object read(String fileName) throws IOException, SAXException {
		try (InputStream stream = new FileInputStream(fileName)) {
			return read(stream);
		}
	}

	public static Object read(InputStream stream) throws IOException, SAXException {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XmlRootHandler handler = new XmlRootHandler();
			parser.parse(stream, handler);
			return handler.root;
		} catch(ParserConfigurationException ex) {
			throw new SAXException("Parser could not be configured.");
		}
	}

}
