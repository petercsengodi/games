package hu.csega.games.library.xml.v2;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

public class XmlBinding {

	private static final Set<Class<?>> ATTRIBUTES = new HashSet<>();

	private final Map<String, Class<?>> nameToClass = new HashMap<>();
	private final Map<String, XmlBinding> nameToBinding = new HashMap<>();
	private final Map<String, XmlFieldBinding> fieldToBindings = new TreeMap<>();

	public XmlBinding() {
	}

	public XmlBinding(Iterable<Class<?>> usedClasses) {
		for(Class<?> usedClass : usedClasses)
			registerClass(usedClass);
	}

	public Collection<XmlFieldBinding> fieldBindingsOf(String xmlTypeName) {
		Map<String, XmlFieldBinding> map = fieldBindingsMap(xmlTypeName);
		return (map == null ? null : map.values());
	}

	public Map<String, XmlFieldBinding> fieldBindingsMap(String xmlTypeName) {
		XmlBinding binding = nameToBinding.get(xmlTypeName);
		return (binding == null ? null : binding.fieldToBindings);
	}

	public Class<?> classOf(String type) {
		return nameToClass.get(type);
	}

	public static boolean isKindOfPrimitive(Class<?> c) {
		return ATTRIBUTES.contains(c);
	}

	private void registerClass(Class<?> classToRegister) {
		XmlClass xmlClass = classToRegister.getAnnotation(XmlClass.class);
		if(xmlClass == null)
			throw new RuntimeException("XmlCass annotation is missing!");

		String typeName = xmlClass.value();
		if(typeName == null || typeName.length() == 0)
			throw new RuntimeException("No type name specified for class: " + classToRegister.getName());

		if(nameToClass.containsKey(typeName))
			throw new RuntimeException("At least two classes claim the same xml type name of " + typeName
					+ ": " + nameToClass.get(typeName).getName() + " and " + classToRegister.getName());

		nameToClass.put(typeName, classToRegister);
		XmlBinding binding = new XmlBinding();
		nameToBinding.put(typeName, binding);
		Map<String, XmlFieldBinding> fieldBindings = binding.fieldToBindings;

		Method[] methods1 = classToRegister.getMethods();
		Method[] methods2 = classToRegister.getDeclaredMethods();
		Set<Method> methods = new HashSet<>();
		methods.addAll(Arrays.asList(methods1));
		methods.addAll(Arrays.asList(methods2));

		for(Method m : methods) {
			XmlField annotation = m.getAnnotation(XmlField.class);
			if(annotation == null)
				continue;

			String methodName = m.getName();
			String field = annotation.value();
			if(field == null || field.length() == 0)
				throw new RuntimeException("Field name is missing for method: "
						+ classToRegister.getName() + '.' + methodName);

			XmlFieldBinding fb = fieldBindings.get(field);
			if(fb == null) {
				fb = new XmlFieldBinding(field);
				fieldBindings.put(field, fb);
			}

			if(methodName.startsWith("get")) {

				if(m.getParameterTypes().length > 0 || methodName.length() <= 3) {
					throw new RuntimeException("Not a real getter: " + classToRegister.getName() + '.' + methodName);
				}

				if(fb.getter != null) {
					throw new RuntimeException("At least two getters found for field " + field + " : "
							+ classToRegister.getName() + '.' + fb.getter.getName() + " and "
							+ classToRegister.getName() + '.' + methodName);
				}

				fb.getter = m;

				Class<?> returnType = m.getReturnType();
				fb.attribute = ATTRIBUTES.contains(returnType);

			} else if(methodName.startsWith("is")) {

				if(m.getParameterTypes().length > 0 || methodName.length() <= 2) {
					throw new RuntimeException("Not a real getter (isXXX): " + classToRegister.getName() + '.' + methodName);
				}

				if(fb.getter != null) {
					throw new RuntimeException("At least two getters found for field " + field + " : "
							+ classToRegister.getName() + '.' + fb.getter.getName() + " and "
							+ classToRegister.getName() + '.' + methodName);
				}

				fb.getter = m;

				Class<?> returnType = m.getReturnType();
				fb.attribute = ATTRIBUTES.contains(returnType);

			} else if(methodName.startsWith("set")) {

				if(m.getParameterTypes().length != 1 || methodName.length() <= 3) {
					throw new RuntimeException("Not a real setter: " + classToRegister.getName() + '.' + methodName);
				}

				if(fb.setter != null) {
					throw new RuntimeException("At least two setters found for field " + field + " : "
							+ classToRegister.getName() + '.' + fb.setter.getName() + " and "
							+ classToRegister.getName() + '.' + methodName);
				}

				fb.setter = m;
			} else {
				throw new RuntimeException("Neither getter nor setter: " + classToRegister.getName() + '.' + methodName);
			}
		}
	}

	static {
		ATTRIBUTES.add(String.class);
		ATTRIBUTES.add(int.class);
		ATTRIBUTES.add(Integer.class);
		ATTRIBUTES.add(byte.class);
		ATTRIBUTES.add(Byte.class);
		ATTRIBUTES.add(boolean.class);
		ATTRIBUTES.add(Boolean.class);
		ATTRIBUTES.add(long.class);
		ATTRIBUTES.add(Long.class);
		ATTRIBUTES.add(short.class);
		ATTRIBUTES.add(Short.class);
		ATTRIBUTES.add(char.class);
		ATTRIBUTES.add(Character.class);
		ATTRIBUTES.add(float.class);
		ATTRIBUTES.add(Float.class);
		ATTRIBUTES.add(double.class);
		ATTRIBUTES.add(Double.class);
	}

}
