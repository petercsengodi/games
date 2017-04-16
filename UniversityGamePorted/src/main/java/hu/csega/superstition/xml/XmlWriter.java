package hu.csega.superstition.xml;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import hu.csega.superstition.util.FileUtil;

public class XmlWriter implements Closeable {

	private List<Object> reference_list;
	private OutputStreamWriter writer;
	private int index;
	private int indentation;

	public XmlWriter(String fileName) throws IOException {
		this(new FileOutputStream(fileName));
	}

	public XmlWriter(OutputStream stream) throws IOException {
		this.reference_list = new ArrayList<>();
		this.index = 0;
		this.indentation = 0;
		this.writer = FileUtil.openWriter(stream);

		this.startOpeningNode(ROOT_TAG);
		this.printAttribute("version", XmlWriter.ROOT_VERSION);
		this.finishOpeningNode();
	}

	public void write(Object obj) throws IOException, XmlReflectionException {
		reference_list.add(obj);
		index = 0;
		while(index < reference_list.size())
		{
			Object tmp = reference_list.get(index);
			Class<?> tmpClass = tmp.getClass();

			XmlClass xmlClass = tmpClass.getAnnotation(XmlClass.class);
			if(xmlClass == null) {
				throw new XmlReflectionException("No annotation for class: " + tmpClass.getName());
			}

			String xmlType = xmlClass.value();

			startOpeningNode(xmlType);
			printAttribute("__id", String.valueOf(index));

			Collection<XmlFieldBinding> bindings = XmlBinding.fieldBindingsOf(xmlType);

			if(bindings == null) {
				throw new XmlReflectionException("No bindings for class: " + tmpClass.getName());
			}

			// possible attributes
			for(XmlFieldBinding fb : bindings) {
				if(fb.attribute) {
					writeAttribute(tmp, fb);
				}
			}

			boolean first = true; // if no children tags, we write a self-closing tag

			// possible children
			for(XmlFieldBinding fb : bindings) {
				if(!fb.attribute) {
					if(first) {
						finishOpeningNode();
						first = false;
					}

					writeField(tmp, fb);
				}
			}

			if(first) {
				finishOpeningNodeWithSelfClosure();
			} else {
				closeNode(xmlType);
			}
			index++;
		}
	}

	private void writeAttribute(Object obj, XmlFieldBinding fb) throws IOException, XmlReflectionException {

		Object value;

		try {
			value = fb.getter.invoke(obj);
		} catch(Exception ex) {
			throw new XmlReflectionException(fb.field, ex);
		}

		if(value == null)
			return;

		printAttribute(fb.field, value.toString());

	} // End of function

	private void writeField(Object obj, XmlFieldBinding fb) throws IOException, XmlReflectionException {

		Object value;

		try {
			value = fb.getter.invoke(obj);
		} catch(Exception ex) {
			throw new XmlReflectionException(fb.field, ex);
		}

		if(value == null)
			return;

		openNode(fb.field);

		if(value instanceof Collection<?>)
		{
			Collection<?> list = (Collection<?>)value;
			for(Object o : list) {
				writeItem(fb, o);
			}

		} else if(value.getClass().isArray()) {
			int size = Array.getLength(value);
			for(int i = 0; i < size; i++)
			{
				Object o = Array.get(value, i);
				writeItem(fb, o);
			}

		} else {

			writeItem(fb, value);

		}

		closeNode(fb.field);
	} // End of function

	private void printMatrix4(XmlFieldBinding fb, Object value) throws IOException {
		Matrix4f m = (Matrix4f) value;
		startOpeningNode("Math.Matrix4");
		printAttribute("M00", String.valueOf(m.m00()));
		printAttribute("M01", String.valueOf(m.m01()));
		printAttribute("M02", String.valueOf(m.m02()));
		printAttribute("M03", String.valueOf(m.m03()));
		printAttribute("M10", String.valueOf(m.m10()));
		printAttribute("M11", String.valueOf(m.m11()));
		printAttribute("M12", String.valueOf(m.m12()));
		printAttribute("M13", String.valueOf(m.m13()));
		printAttribute("M20", String.valueOf(m.m20()));
		printAttribute("M21", String.valueOf(m.m21()));
		printAttribute("M22", String.valueOf(m.m22()));
		printAttribute("M23", String.valueOf(m.m23()));
		printAttribute("M30", String.valueOf(m.m30()));
		printAttribute("M31", String.valueOf(m.m31()));
		printAttribute("M32", String.valueOf(m.m32()));
		printAttribute("M33", String.valueOf(m.m33()));
		finishOpeningNodeWithSelfClosure();
	}

	private void printMatrix3(XmlFieldBinding fb, Object value) throws IOException {
		Matrix3f m = (Matrix3f) value;
		startOpeningNode("Math.Matrix3");
		printAttribute("M00", String.valueOf(m.m00));
		printAttribute("M01", String.valueOf(m.m01));
		printAttribute("M02", String.valueOf(m.m02));
		printAttribute("M10", String.valueOf(m.m10));
		printAttribute("M11", String.valueOf(m.m11));
		printAttribute("M12", String.valueOf(m.m12));
		printAttribute("M20", String.valueOf(m.m20));
		printAttribute("M21", String.valueOf(m.m21));
		printAttribute("M22", String.valueOf(m.m22));
		finishOpeningNodeWithSelfClosure();
	}

	private void printVector4(XmlFieldBinding fb, Object value) throws IOException {
		Vector4f v = (Vector4f) value;
		startOpeningNode("Math.Vector4");
		printAttribute("X", String.valueOf(v.x));
		printAttribute("Y", String.valueOf(v.y));
		printAttribute("Z", String.valueOf(v.y));
		printAttribute("W", String.valueOf(v.w));
		finishOpeningNodeWithSelfClosure();
	}

	private void printVector3f(XmlFieldBinding fb, Object value) throws IOException {
		Vector3f v = (Vector3f) value;
		startOpeningNode("Math.Vector3");
		printAttribute("X", String.valueOf(v.x));
		printAttribute("Y", String.valueOf(v.y));
		printAttribute("Z", String.valueOf(v.y));
		finishOpeningNodeWithSelfClosure();
	}

	private void printVector2(XmlFieldBinding fb, Object value) throws IOException {
		Vector2f v = (Vector2f) value;
		startOpeningNode("Math.Vector2");
		printAttribute("X", String.valueOf(v.x));
		printAttribute("Y", String.valueOf(v.y));
		finishOpeningNodeWithSelfClosure();
	}

	private void writeItem(XmlFieldBinding fb, Object value) throws IOException, XmlReflectionException {
		if(value == null)
			return;

		Class<?> c = value.getClass();
		if(XmlBinding.isKindOfPrimitive(c)) {
			printKindOfPrimitive(value.toString(), c);
			return;

		} else if(value instanceof Vector2f) {
			printVector2(fb, value);
			return;

		} else if (value instanceof Vector3f) {
			printVector3f(fb, value);
			return;

		} else if (value instanceof Vector4f) {
			printVector4(fb, value);
			return;


		} else if (value instanceof Matrix3f) {
			printMatrix3(fb, value);
			return;

		} else if (value instanceof Matrix4f) {
			printMatrix4(fb, value);
			return;
		}

		String tag;
		XmlClass annotation = c.getAnnotation(XmlClass.class);
		if(annotation != null) {
			tag = annotation.value();
			if(tag == null || tag.length() == 0) {
				throw new XmlReflectionException("Empty annotation of: " + c.getName());
			}
		}  else {
			throw new XmlReflectionException("Don't know how to write: " + c.getName());
		}

		int reference = reference_list.indexOf(value);
		if(reference == -1)
		{
			reference = reference_list.size();
			reference_list.add(value);
		}

		startOpeningNode(tag);
		printAttribute("__ref", String.valueOf(reference));
		finishOpeningNodeWithSelfClosure();
		return;
	}

	private void openNode(String tag) throws IOException {
		startOpeningNode(tag);
		finishOpeningNode();
	}

	private void startOpeningNode(String tag) throws IOException {
		printIndentation();
		writer.write("<" + tag);
	}

	private void printAttribute(String name, String value) throws IOException {
		writer.write(" " + name + "=\"" + StringEscapeUtils.escapeJava(value) + '\"');
	}

	private void finishOpeningNodeWithSelfClosure() throws IOException {
		writer.write("/>\n");
	}

	private void finishOpeningNode() throws IOException {
		writer.write(">\n");
		indentation += 2;
	}

	private void printKindOfPrimitive(String s, Class<?> class1) throws IOException, XmlReflectionException {
		printIndentation();
		String tag = tagForClass(class1);
		writer.write('<' + tag + '>' + StringEscapeUtils.escapeXml11(s) + "</" + tag + ">\n");
	}

	private String tagForClass(Class<?> class1) throws XmlReflectionException {
		if(class1 == String.class)
			return "Lang.String";

		if(class1 == int.class || class1 == Integer.class)
			return "Lang.Integer";

		if(class1 == byte.class || class1 == Byte.class)
			return "Lang.Byte";

		if(class1 == boolean.class || class1 == Boolean.class)
			return "Lang.Boolean";

		if(class1 == long.class || class1 == Long.class)
			return "Lang.Long";

		if(class1 == short.class || class1 == Short.class)
			return "Lang.Short";

		if(class1 == char.class || class1 == Character.class)
			return "Lang.Character";

		if(class1 == float.class || class1 == Float.class)
			return "Lang.Float";

		if(class1 == double.class || class1 == Double.class)
			return "Lang.Double";

		throw new XmlReflectionException("Couldn't identify parameter type: " + class1.getName());
	}

	private void closeNode(String tag) throws IOException {
		indentation -= 2;
		printIndentation();
		writer.write("</" + tag + ">\n");
	}

	private void printIndentation() throws IOException {
		int tmp = indentation;

		while(tmp > SPACES_LENGTH) {
			writer.write(SPACES_LENGTH);
			tmp -= SPACES_LENGTH;
		}

		if(tmp > 0) {
			writer.write(SPACES.substring(0, tmp));
		}
	}

	@Override
	public void close() throws IOException {
		closeNode(ROOT_TAG);
		writer.close();
	}

	public static final String ROOT_TAG = "Superstition.Model";
	public static final String ROOT_VERSION = "01.00.0000";
	private static final String SPACES = "                                        ";
	private static final int SPACES_LENGTH = SPACES.length();

} // End of class