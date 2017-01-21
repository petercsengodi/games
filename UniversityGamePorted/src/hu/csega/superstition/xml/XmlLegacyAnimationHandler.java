package hu.csega.superstition.xml;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

class XmlLegacyAnimationHandler implements XmlHandler {

	@Override
	public Object handle(XmlNode node, XmlNodeStack parents) throws XmlException {
		if("meta".equals(node.tag) && parents.isEmpty()) {
			// root element, end of processing
			if(node.children.size() == 1)
				return node.children.get(0);
		}

		if("Legacy.Matrix".equals(node.tag)) {
			return matrix4Of(node);
		}

		if("Legacy.Vector3".equals(node.tag)) {
			return vector3Of(node);
		}

		if("Legacy.Vector2".equals(node.tag)) {
			return vector2Of(node);
		}

		if(node.tag.startsWith("Legacy.")) {
			Object obj;
			Class<?> tagClass = XmlBinding.classOf(node.tag);

			try {
				obj = tagClass.newInstance();
			} catch(Exception ex) {
				throw new XmlException("Couldn't create object of class " + tagClass.getName(), ex);
			}

			Map<String, XmlFieldBinding> fields = XmlBinding.fieldBindingsMap(node.tag);
			if(fields != null && !fields.isEmpty()) {
				for(Object c : node.children) {
					XmlNode parameterNode = (XmlNode)c;
					XmlFieldBinding binding = fields.get(parameterNode.tag);

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
							binding.setter.invoke(obj, coll);
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
							binding.setter.invoke(obj, array);
						} catch (IllegalAccessException | InvocationTargetException ex) {
							throw new XmlException("Error calling setter: " + tagClass.getName() + '.' + parameterNode.tag, ex);
						}
					} else if(valueClass == Vector3f.class) {
						Object param = vector3Of(parameterNode);
						try {
							binding.setter.invoke(obj, param);
						} catch (IllegalAccessException | InvocationTargetException ex) {
							throw new XmlException("Error calling setter: " + tagClass.getName() + '.' + parameterNode.tag, ex);
						}
					} else if(valueClass == Vector2f.class) {
						Object param = vector2Of(parameterNode);
						try {
							binding.setter.invoke(obj, param);
						} catch (IllegalAccessException | InvocationTargetException ex) {
							throw new XmlException("Error calling setter: " + tagClass.getName() + '.' + parameterNode.tag, ex);
						}
					} else if(valueClass == Matrix4f.class) {
						Object param = matrix4Of(parameterNode);
						try {
							binding.setter.invoke(obj, param);
						} catch (IllegalAccessException | InvocationTargetException ex) {
							throw new XmlException("Error calling setter: " + tagClass.getName() + '.' + parameterNode.tag, ex);
						}
					} else if(parameterNode.children.isEmpty()) {
						String s = parameterNode.content.toString();
						if(s != null && s.length() > 0) {
							Object param = parse(s, valueClass);
							try {
								binding.setter.invoke(obj, param);
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
								binding.setter.invoke(obj, child);
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

			// Return fully converted object
			return obj;
		}

		// we may not even want to handle this node
		return node;
	}

	private Object vector2Of(XmlNode node) {
		Vector2f v = new Vector2f();

		for(Object c : node.children) {
			XmlNode n = (XmlNode)c;

			switch(n.tag) {
			case "X": f2[0] = floatOf(n); break;
			case "Y": f2[1] = floatOf(n); break;
			default:
				break;
			} // end switch

		} // end for c - children

		v.set(f2[0], f2[1]);
		return v;
	}

	private Object matrix4Of(XmlNode node) {
		Matrix4f m = new Matrix4f();

		for(Object c : node.children) {
			XmlNode n = (XmlNode)c;

			switch(n.tag) {
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

	private Object vector3Of(XmlNode node) {
		Vector3f v = new Vector3f();

		for(Object c : node.children) {
			XmlNode n = (XmlNode)c;

			switch(n.tag) {
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

	private float floatOf(XmlNode c) {
		return Float.parseFloat(c.content.toString().replace(',', '.'));
	}

	@Override
	public void complete(XmlObjectProxy proxy) throws XmlException {
		throw new UnsupportedOperationException("complete");
	}

	private float[] f16 = new float[16];
	private float[] f3 = new float[3];
	private float[] f2 = new float[2];
}
