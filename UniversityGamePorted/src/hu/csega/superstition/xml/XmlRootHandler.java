package hu.csega.superstition.xml;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class XmlRootHandler extends DefaultHandler implements XmlHandler {

	public XmlNodeStack stack = new XmlNodeStack();
	public XmlHandler subHandler;
	public Object root;

	public XmlRootHandler() {

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (subHandler == null) {
			if ("meta".equals(qName)) {
				logger.info("Using legacy handler.");
				subHandler = new XmlLegacyAnimationHandler();
			} else if (XmlWriter.ROOT_TAG.equals(qName)) {
				logger.info("Using legacy handler.");
				subHandler = new XmlNewFormatHandler();
			} else {
				logger.error("Root tag:" + qName);
				throw new SAXException("Invalid format! Root tag: " + qName);
			}
		}

		XmlNode node = new XmlNode();
		node.tag = qName;

		int al = attributes.getLength();
		for (int i = 0; i < al; i++)
			node.attributes.put(attributes.getQName(i), attributes.getValue(i));

		stack.push(node);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		try {

			XmlNode node = stack.pop();
			Object convertedTo = handle(node, stack);

			XmlNode top = stack.top();
			if (top != null)
				top.children.add(convertedTo);
			else
				root = convertedTo;

		} catch(XmlException ex) {
			throw new SAXException("Error when closing tag found!", ex);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		XmlNode top = stack.top();
		if (top != null)
			top.content.append(ch, start, length);
	}

	@Override
	public Object handle(XmlNode node, XmlNodeStack parents) throws XmlException {
		return subHandler.handle(node, parents);
	}

	private static final Logger logger = Logger.getLogger(XmlHandler.class);
}
