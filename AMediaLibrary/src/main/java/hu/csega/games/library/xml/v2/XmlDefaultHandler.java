package hu.csega.games.library.xml.v2;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import hu.csega.games.library.xml.XmlException;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

class XmlDefaultHandler extends DefaultHandler {

	public XmlNodeStack stack;
	public XmlFormat format;
	public XmlBinding binding;
	public Object root;

	public XmlDefaultHandler(XmlFormat format) {
		this.format = format;
		this.binding = format.provideBinding();
		this.stack = new XmlNodeStack();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
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

			if(convertedTo != null) {

				XmlNode top = stack.top();
				if (top != null)
					top.children.add(convertedTo);
				else
					root = convertedTo;

			}

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

	private Object handle(XmlNode node, XmlNodeStack parents) throws XmlException {
		if(parents.isEmpty()) {
			// root element, end of processing
			if(node.children.size() == 1) {
				// referenced objects should be removed from the list on-the-fly
				return node.children.get(0);
			} else {
				throw new XmlException("There should be at exactly 1 child of root!");
			}
		}

		Object kindOfPrimitive = resolveKindOfPrimitives(node.content, node.tag);
		if(kindOfPrimitive != null)
			return kindOfPrimitive;

		if(node.attributes.containsKey("__id")) {
			String id = node.attributes.get("__id");
			complete(id, node);

			// referenced objects should be removed from the list on-the-fly
			return ("0".equals(id) ? reachForObject(id, node.tag) : null);
		} else if(node.attributes.containsKey("__ref")) {
			String id = node.attributes.get("__ref");
			return reachForObject(id, node.tag);
		} else {
			throw new XmlException("Objects should have either an ID or a reference.");
		}
	}

	private Object parse(String s, Class<?> class1) throws XmlException {
		if(s == null || class1 == String.class)
			return s;

		if(class1 == int.class || class1 == Integer.class)
			return Integer.parseInt(s);

		if(class1 == byte.class || class1 == Byte.class)
			return Byte.parseByte(s);

		if(class1 == boolean.class || class1 == Boolean.class)
			return Boolean.parseBoolean(s);

		if(class1 == long.class || class1 == Long.class)
			return Long.parseLong(s);

		if(class1 == short.class || class1 == Short.class)
			return Short.parseShort(s);

		if(class1 == char.class || class1 == Character.class)
			return s.charAt(0);

		if(class1 == float.class || class1 == Float.class)
			return Float.parseFloat(s);

		if(class1 == double.class || class1 == Double.class)
			return Double.parseDouble(s);

		throw new XmlException("Couldn't identify setter parameter: " + class1.getName());
	}

	private Object resolveKindOfPrimitives(StringBuilder b, String tag) throws XmlException {
		if(!tag.startsWith("Lang.") || b == null) {
			return null;
		}

		String s = b.toString();

		String type = tag.substring("Lang.".length());
		if("String".equals(tag))
			return s;

		if(s.length() == 0)
			return null;

		switch(type) {
		case "Integer":
			return Integer.parseInt(s);
		case "Byte":
			return Byte.parseByte(s);
		case "Boolean":
			return Boolean.parseBoolean(s);
		case "Long":
			return Long.parseLong(s);
		case "Short":
			return Short.parseShort(s);
		case "Character":
			return s.charAt(0);
		case "Float":
			return Float.parseFloat(s);
		case "Double":
			return Double.parseDouble(s);
		default:
			throw new XmlException("Couldn't identify setter parameter: " + tag);
		}
	}

	private float floatOf(String n) {
		if(n == null || n.length() == 0)
			return 0f;

		return Float.parseFloat(n);
	}

	public void complete(String id, XmlNode node) throws XmlException {
		Object objectToComplete = reachForObject(id, node.tag);
		Class<?> tagClass = binding.classOf(node.tag);

		Map<String, XmlFieldBinding> fields = binding.fieldBindingsMap(node.tag);
		if(fields != null && !fields.isEmpty()) {
			for(Map.Entry<String, String> e : node.attributes.entrySet()) {
				String name = e.getKey();
				if(!"__id".equals(name) && !"__ref".equals(name)) {
					XmlFieldBinding binding = fields.get(name);
					if(binding == null)
						throw new XmlException("Can't find field: " + tagClass.getName() + '.' + name);

					Class<?> valueClass = binding.setter.getParameterTypes()[0];
					Object param = parse(e.getValue(), valueClass);

					try {
						binding.setter.invoke(objectToComplete, param);
					} catch (IllegalAccessException | InvocationTargetException ex) {
						throw new XmlException("Error calling setter: " + tagClass.getName() + '.'
								+ binding.setter.getName(), ex);
					}
				}
			}

			for(Object c : node.children) {
				XmlNode parameterNode = (XmlNode)c;
				XmlFieldBinding binding = fields.get(parameterNode.tag);
				if(binding == null)
					throw new XmlException("Can't find field: " + tagClass.getName() + '.' + parameterNode.tag);

				Class<?> valueClass = binding.setter.getParameterTypes()[0];
				if(Collection.class.isAssignableFrom(valueClass)) {
					Collection<?> coll;

					if(valueClass.isAssignableFrom(ArrayList.class)) {
						List<Object> l = new ArrayList<>();
						l.addAll(parameterNode.children);
						coll = l;
					} else if(valueClass.isAssignableFrom(HashSet.class)) {
						Set<Object> l = new HashSet<>();
						l.addAll(parameterNode.children);
						coll = l;
					} else {
						throw new XmlException("Couldn't set: " + tagClass.getName() + '.' + parameterNode.tag);
					}

					try {
						binding.setter.invoke(objectToComplete, coll);
					} catch (IllegalAccessException | InvocationTargetException ex) {
						throw new XmlException("Error calling setter: " + tagClass.getName() + '.' + parameterNode.tag, ex);
					}
				} else if(valueClass.isArray()) {
					Class<?> componentType = valueClass.getComponentType();
					int size = parameterNode.children.size();
					Object array = Array.newInstance(componentType, size);

					if(size > 0) {
						for(int i = 0; i < size; i++)
							Array.set(array, i, parameterNode.children.get(i));
					}

					try {
						binding.setter.invoke(objectToComplete, array);
					} catch (IllegalAccessException | InvocationTargetException ex) {
						throw new XmlException("Error calling setter: " + tagClass.getName() + '.' + parameterNode.tag, ex);
					}
				} else if(parameterNode.children.isEmpty()) {
					// This should neve happen, as we map them as attributes, but who knows...
					String s = parameterNode.content.toString();
					if(s != null && s.length() > 0) {
						Object param = parse(s, valueClass);
						try {
							binding.setter.invoke(objectToComplete, param);
						} catch (IllegalAccessException | InvocationTargetException ex) {
							throw new XmlException("Error calling setter: " + tagClass.getName() + '.' + parameterNode.tag, ex);
						}
					} else {
						throw new XmlException("Couldn't set: " + tagClass.getName() + '.' + parameterNode.tag);
					}
				} else if(parameterNode.children.size() == 1) {
					Object child = parameterNode.children.get(0);
					if(child != null && valueClass.isAssignableFrom(child.getClass())) {
						try {
							binding.setter.invoke(objectToComplete, child);
						} catch (IllegalAccessException | InvocationTargetException ex) {
							throw new XmlException("Error calling setter: " + tagClass.getName() + '.' + parameterNode.tag, ex);
						}
					} else {
						throw new XmlException("Couldn't set: " + tagClass.getName() + '.' + parameterNode.tag);
					}
				} else {
					throw new XmlException("Couldn't set: " + tagClass.getName() + '.' + parameterNode.tag);
				}

			} // end for c - children
		}
	}

	private Object reachForObject(String id, String tag) throws XmlException {
		Object _value = objects.get(id);
		if(_value != null)
			return _value;

		_value = createEmptyObject(id, tag);
		objects.put(id, _value);
		return _value;
	}

	private Object createEmptyObject(String id, String tag) throws XmlException {
		Class<?> valueClass = binding.classOf(tag);
		if(valueClass == null)
			throw new XmlException("Missing class for " + tag);

		Object _value;
		try {
			_value = valueClass.newInstance();
		} catch(Exception ex) {
			throw new XmlException("Couldn't create object of class " + valueClass.getName(), ex);
		}

		return _value;
	}

	private Map<String, Object> objects = new HashMap<>();

	private static final Logger logger = LoggerFactory.createLogger(XmlDefaultHandler.class);
}
