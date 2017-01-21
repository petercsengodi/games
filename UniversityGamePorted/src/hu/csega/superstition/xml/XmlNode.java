package hu.csega.superstition.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class XmlNode {

	public String tag;
	public Map<String, String> attributes = new HashMap<>();
	public List<Object> children = new ArrayList<>();

	public StringBuilder content = new StringBuilder();
}
