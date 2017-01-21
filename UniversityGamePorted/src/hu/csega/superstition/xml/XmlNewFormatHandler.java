package hu.csega.superstition.xml;

class XmlNewFormatHandler implements XmlHandler {

	@Override
	public Object handle(XmlNode node, XmlNodeStack parents) {
		if(parents.isEmpty()) {
			// we arrived at the root element

			if(node.children.size() == 1)
				return node.children.get(0);
			else
				// don't know, what to do with it, maybe somebody will
				return node;
		}


		// it's something we don't want to handle
		return node;
	}

}
