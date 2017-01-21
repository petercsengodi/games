package hu.csega.superstition.xml;

import java.util.ArrayList;
import java.util.List;

class XmlNodeStack {

	public void push(XmlNode node) {
		stack.add(node);
	}

	public XmlNode top() {
		if(stack.isEmpty())
			return null;

		return stack.get(stack.size() - 1);
	}

	public XmlNode pop() {
		if(stack.isEmpty())
			return null;

		return stack.remove(stack.size() - 1);
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}

	public List<XmlNode> stack = new ArrayList<>();
}
