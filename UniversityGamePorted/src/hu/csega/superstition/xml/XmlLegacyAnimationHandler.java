package hu.csega.superstition.xml;

class XmlLegacyAnimationHandler implements XmlHandler {

	@Override
	public Object handle(XmlNode node, XmlNodeStack parents) {
		if("meta".equals(node.tag) && parents.isEmpty()) {
			// root element, end of processing
			if(node.children.size() == 1)
				return node.children.get(0);
		}



		// we may not even want to handle this node
		return node;
	}

}
