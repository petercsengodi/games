package hu.csega.superstition.xml;

interface XmlHandler {

	/**
	 * Input is a subtree (node with children) and the node stack (parents),
	 * output is the object this node should be replaced with.
	 */
	Object handle(XmlNode node, XmlNodeStack parents) throws XmlException;

	void complete(XmlObjectProxy proxy) throws XmlException;

}
