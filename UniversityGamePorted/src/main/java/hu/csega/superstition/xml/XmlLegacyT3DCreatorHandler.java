package hu.csega.superstition.xml;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

class XmlLegacyT3DCreatorHandler implements XmlHandler {

	@Override
	public Object handle(XmlNode node, XmlNodeStack parents) throws XmlException {
		if("DATAFILE".equals(node.tag) && parents.isEmpty()) {
			// root element, end of processing
			XmlObjectProxy rootProxy = proxies.get("0");
			if(rootProxy != null)
				return rootProxy.value();
			else
				return null;
		}

		Object vectorOrMatrix = resolveVectorsAndMatrices(node);
		if(vectorOrMatrix != null)
			return vectorOrMatrix;

		if(node.tag.startsWith("T3DCreator.")) {
			String ref = node.attributes.get("ref");
			XmlObjectProxy proxy = new XmlObjectProxy(ref, node, this);
			proxies.put(ref, proxy);
			return null;
		}

		// we may not even want to handle this node
		return node;
	}

	private Object matrix4Of(XmlNode node) {
		Matrix4f m = new Matrix4f();

		for(Entry<String, String> e : node.attributes.entrySet()) {
			String name = e.getKey();
			String n = e.getValue();

			switch(name) {
			case "M11": f16[0] = floatOf(n); break;
			case "M12": f16[1] = floatOf(n); break;
			case "M13": f16[2] = floatOf(n); break;
			case "M14": f16[3] = floatOf(n); break;
			case "M21": f16[4] = floatOf(n); break;
			case "M22": f16[5] = floatOf(n); break;
			case "M23": f16[6] = floatOf(n); break;
			case "M24": f16[7] = floatOf(n); break;
			case "M31": f16[8] = floatOf(n); break;
			case "M32": f16[9] = floatOf(n); break;
			case "M33": f16[10] = floatOf(n); break;
			case "M34": f16[11] = floatOf(n); break;
			case "M41": f16[12] = floatOf(n); break;
			case "M42": f16[13] = floatOf(n); break;
			case "M43": f16[14] = floatOf(n); break;
			case "M44": f16[15] = floatOf(n); break;
			default:
				break;
			} // end switch

		} // end for c - children

		m.set(f16);
		return m;
	}

	private Object vector4Of(XmlNode node) {
		Vector4f v = new Vector4f();

		for(Entry<String, String> e : node.attributes.entrySet()) {
			String name = e.getKey();
			String n = e.getValue();

			switch(name) {
			case "X": f4[0] = floatOf(n); break;
			case "Y": f4[1] = floatOf(n); break;
			case "Z": f4[2] = floatOf(n); break;
			case "W": f4[3] = floatOf(n); break;
			default:
				break;
			} // end switch
		} // end for c - children

		v.set(f4[0], f4[1], f4[2], f4[3]);
		return v;
	}

	private Object vector3Of(XmlNode node) {
		Vector3f v = new Vector3f();

		for(Entry<String, String> e : node.attributes.entrySet()) {
			String name = e.getKey();
			String n = e.getValue();

			switch(name) {
			case "X": f3[0] = floatOf(n); break;
			case "Y": f3[1] = floatOf(n); break;
			case "Z": f3[2] = floatOf(n); break;
			default:
				break;
			} // end switch
		} // end for c - children

		v.set(f3[0], f3[1], f3[2]);
		return v;
	}

	private Object vector2Of(XmlNode node) {
		Vector2f v = new Vector2f();

		for(Entry<String, String> e : node.attributes.entrySet()) {
			String name = e.getKey();
			String n = e.getValue();

			switch(name) {
			case "X": f2[0] = floatOf(n); break;
			case "Y": f2[1] = floatOf(n); break;
			default:
				break;
			} // end switch

		} // end for c - children

		v.set(f2[0], f2[1]);
		return v;
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
			return Float.parseFloat(s.replace(',', '.'));

		if(class1 == double.class || class1 == Double.class)
			return Double.parseDouble(s.replace(',', '.'));

		throw new XmlException("Couldn't identify setter parameter: " + class1.getName());
	}

	private float floatOf(String n) {
		if(n == null || n.length() == 0)
			return 0f;

		return Float.parseFloat(n.replace(',', '.'));
	}

	public Object resolveVectorsAndMatrices(XmlNode node) {
		if("T3DCreator.Matrix4".equals(node.tag)) {
			return matrix4Of(node);
		}

		if("T3DCreator.Vector4".equals(node.tag)) {
			return vector4Of(node);
		}

		if("T3DCreator.Vector3".equals(node.tag)) {
			return vector3Of(node);
		}

		if("T3DCreator.Vector2".equals(node.tag)) {
			return vector2Of(node);
		}

		// unknown type
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void complete(XmlObjectProxy xmlObjectProxy) throws XmlException {
		XmlNode node = xmlObjectProxy.node;
		Object objectToComplete = xmlObjectProxy.value();
		Class<?> tagClass = XmlBinding.classOf(node.tag);

		Map<String, XmlFieldBinding> fields = XmlBinding.fieldBindingsMap(node.tag);
		if(fields != null && !fields.isEmpty()) {
			for(Map.Entry<String, String> e : node.attributes.entrySet()) {
				String name = e.getKey();
				if(!"ref".equals(name)) {
					XmlFieldBinding binding = fields.get(name);
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
					throw new XmlException("No setter for: " + tagClass.getName() + '.' + parameterNode.tag);

				Class<?> valueClass = binding.setter.getParameterTypes()[0];

				Object parameterValue;
				String ref = parameterNode.attributes.get("ref");
				if(ref != null) {
					parameterValue = proxies.get(ref).value();
				} else if(parameterNode.children.size() == 1){
					parameterValue = parameterNode.children.get(0);
				} else {
					String s = parameterNode.content.toString();
					if(s != null && s.length() > 0) {
						if(XmlBinding.isKindOfPrimitive(valueClass)) {
							parameterValue = parse(s, valueClass);
						} else {
							throw new XmlException("Couldn't set: " + tagClass.getName() + '.' + parameterNode.tag);
						}
					} else {
						// let it go
						parameterValue = null;
						continue;
					}
				}

				if(Collection.class.isAssignableFrom(valueClass)) {
					try {
						Object coll = binding.getter.invoke(objectToComplete);
						if(coll instanceof List<?>) {
							List<Object> list = (List<Object>)coll;
							list.add(parameterValue);
						} else if(coll instanceof List<?>) {
							Set<Object> set = (Set<Object>)coll;
							set.add(parameterValue);
						} else {
							throw new XmlException("Couldn't set: " + tagClass.getName() + '.' + parameterNode.tag);
						}
					} catch (IllegalAccessException | InvocationTargetException ex) {
						throw new XmlException("Error calling setter: " + tagClass.getName() + '.' + parameterNode.tag, ex);
					}
				} else if(valueClass.isArray()) {
					throw new UnsupportedOperationException("array");
				} else {
					try {
						binding.setter.invoke(objectToComplete, parameterValue);
					} catch (IllegalAccessException | InvocationTargetException ex) {
						throw new XmlException("Error calling setter: " + tagClass.getName() + '.' + parameterNode.tag, ex);
					}
				}

			} // end for c - children
		}
	}

	private Map<String, XmlObjectProxy> proxies = new HashMap<>();

	private float[] f16 = new float[16];
	private float[] f4 = new float[4];
	private float[] f3 = new float[3];
	private float[] f2 = new float[2];

}
