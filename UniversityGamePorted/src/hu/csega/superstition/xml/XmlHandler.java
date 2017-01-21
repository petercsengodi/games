package hu.csega.superstition.xml;

import org.xml.sax.SAXException;

interface XmlHandler {

	/**
	 * Input is a subtree (node with children) and the node stack (parents),
	 * output is the object this node should be replaced with.
	 */
	Object handle(XmlNode node, XmlNodeStack parents) throws SAXException;

}
