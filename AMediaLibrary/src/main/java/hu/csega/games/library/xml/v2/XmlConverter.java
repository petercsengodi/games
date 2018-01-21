package hu.csega.games.library.xml.v2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import hu.csega.games.library.xml.v1.XmlReflectionException;

public class XmlConverter {

	private XmlFormat format;

	public XmlConverter(XmlFormat format) {
		this.format = format;
	}

	public Object read(String fileName) throws IOException, SAXException {
		try (InputStream stream = new FileInputStream(fileName)) {
			return read(stream);
		}
	}

	public Object read(InputStream stream) throws IOException, SAXException {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XmlDefaultHandler handler = new XmlDefaultHandler(format);
			parser.parse(stream, handler);
			return handler.root;
		} catch(ParserConfigurationException ex) {
			throw new SAXException("Parser could not be configured.");
		}
	}

	public void write(String fileName, Object obj) throws IOException, XmlReflectionException {
		FileOutputStream stream = new FileOutputStream(fileName);
		write(stream, obj);
	}

	public void write(OutputStream stream, Object obj) throws IOException, XmlReflectionException {
		PrintStream printer = new PrintStream(stream);;
		write(printer, obj);
	}

	public void write(PrintStream printer, Object obj) throws IOException, XmlReflectionException {
	}
}
