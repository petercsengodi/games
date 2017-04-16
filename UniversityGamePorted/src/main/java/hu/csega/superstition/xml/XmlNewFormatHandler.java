package hu.csega.superstition.xml;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * The new way of parsing the data files.
 * It is a hybrid solution of the handers of the legacy format with some new aspects.
 * <br/><br/>
 * <ul>
 * <li>IDs and reference attributes are now named as `__id` and `__ref` to better avoid collisions.</li>
 * <li>References are created now on the fly, and trying to be completed on their turn in the object stream
 * (this was reversed in the earlier version). If not all references are available, it will be proxied
 * like earlier.</li>
 * <li>While writing, if an object doesn't have any references to other objects
 * (has only primitives, vectors and matrices),
 * may be written out in the subtree, not as a referenced object
 * (current writer is not capable of that).</li>
 * <li>Vectors and matrices are written with "Matrix." prefix.</li>
 * <li>List elements of one property will be collected in a child node.</li>
 * <li>Arrays are supported.</li>
 * <li>There can be a list of kind-of-primitives with the prefix of "Lang.".</li>
 * </ul>
 */
class XmlNewFormatHandler implements XmlHandler {

	@Override
	public Object handle(XmlNode node, XmlNodeStack parents) throws XmlException {
		if(XmlWriter.ROOT_TAG.equals(node.tag) && parents.isEmpty()) {
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

		Object vectorOrMatrix = resolveVectorsAndMatrices(node);
		if(vectorOrMatrix != null)
			return vectorOrMatrix;

		if(node.tag.startsWith("Superstition.") || // knows the legacy objects as well, but uses only in tests
				node.tag.startsWith("T3DCreator.") || node.tag.startsWith("Legacy.")) {

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

	private Object matrix3Of(XmlNode node) {
		Matrix3f m = new Matrix3f();

		for(Entry<String, String> e : node.attributes.entrySet()) {
			String name = e.getKey();
			String n = e.getValue();

			switch(name) {
			case "M11": f9[0] = floatOf(n); break;
			case "M12": f9[1] = floatOf(n); break;
			case "M13": f9[2] = floatOf(n); break;
			case "M21": f9[3] = floatOf(n); break;
			case "M22": f9[4] = floatOf(n); break;
			case "M23": f9[5] = floatOf(n); break;
			case "M31": f9[6] = floatOf(n); break;
			case "M32": f9[7] = floatOf(n); break;
			case "M33": f9[8] = floatOf(n); break;
			default:
				break;
			} // end switch

		} // end for c - children

		m.set(f9);
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

	public Object resolveVectorsAndMatrices(XmlNode node) {
		if("Math.Matrix4".equals(node.tag)) {
			return matrix4Of(node);
		}

		if("Math.Matrix3".equals(node.tag)) {
			return matrix3Of(node);
		}

		if("Math.Vector4".equals(node.tag)) {
			return vector4Of(node);
		}

		if("Math.Vector3".equals(node.tag)) {
			return vector3Of(node);
		}

		if("Math.Vector2".equals(node.tag)) {
			return vector2Of(node);
		}

		// unknown type
		return null;
	}

	@Override
	public void complete(XmlObjectProxy xmlObjectProxy) throws XmlException {
		// FIXME object proxies in interfaces should be re-considered
		throw new UnsupportedOperationException("complete");
	}

	public void complete(String id, XmlNode node) throws XmlException {
		Object objectToComplete = reachForObject(id, node.tag);
		Class<?> tagClass = XmlBinding.classOf(node.tag);

		Map<String, XmlFieldBinding> fields = XmlBinding.fieldBindingsMap(node.tag);
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
		Class<?> valueClass = XmlBinding.classOf(tag);
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

	private float[] f16 = new float[16];
	private float[] f9 = new float[9];
	private float[] f4 = new float[4];
	private float[] f3 = new float[3];
	private float[] f2 = new float[2];

}
