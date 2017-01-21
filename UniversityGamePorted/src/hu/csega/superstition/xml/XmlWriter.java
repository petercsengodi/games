package hu.csega.superstition.xml;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

		this.openNode(ROOT_TAG);
	}

	public void write(Object obj) throws IOException, XmlReflectionException {
		reference_list.add(obj);
		index = 0;
		while(index < reference_list.size())
		{
			Object tmp = reference_list.get(index);
			Class<?> tmpClass = tmp.getClass();

			XmlClass xmlClass = tmpClass.getAnnotation(XmlClass.class);
			String xmlType = xmlClass.value();

			startOpeningNode(xmlType);
			printAttribute("ref", String.valueOf(index));

			Collection<XmlFieldBinding> bindings = XmlBinding.fieldBindingsOf(xmlType);

			// possible attributes
			for(XmlFieldBinding fb : bindings) {
				if(fb.attribute) {
					writeAttribute(obj, fb);
				}
			}

			finishOpeningNode();

			// possible children
			for(XmlFieldBinding fb : bindings) {
				writeField(obj, fb);
			}

			closeNode(xmlType);
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

		int reference;
		if(value instanceof Collection<?>)
		{
			Collection<?> list = (Collection<?>)value;
			for(Object o : list)
			{
				reference = reference_list.indexOf(o);
				if(reference == -1)
				{
					reference = reference_list.size();
					reference_list.add(o);
				}

				startOpeningNode(fb.field);
				printAttribute("ref", String.valueOf(reference));
				finishOpeningNodeWithSelfClosure();
			}
			return;
		}

		else if(value instanceof Vector2f) {
			Vector2f v = (Vector2f) value;
			openNode(fb.field);

			startOpeningNode("T3DCreator.Vector2");
			printAttribute("X", String.valueOf(v.x));
			printAttribute("Y", String.valueOf(v.y));
			finishOpeningNodeWithSelfClosure();

			closeNode(fb.field);

		} else if (value instanceof Vector3f) {
			Vector3f v = (Vector3f) value;
			openNode(fb.field);

			startOpeningNode("T3DCreator.Vector3");
			printAttribute("X", String.valueOf(v.x));
			printAttribute("Y", String.valueOf(v.y));
			printAttribute("Z", String.valueOf(v.y));
			finishOpeningNodeWithSelfClosure();

			closeNode(fb.field);

		} else if (value instanceof Vector4f) {
			Vector4f v = (Vector4f) value;
			openNode(fb.field);

			startOpeningNode("T3DCreator.Vector4");
			printAttribute("X", String.valueOf(v.x));
			printAttribute("Y", String.valueOf(v.y));
			printAttribute("Z", String.valueOf(v.y));
			printAttribute("W", String.valueOf(v.w));
			finishOpeningNodeWithSelfClosure();

			closeNode(fb.field);


		} else if (value instanceof Matrix3f) {
			Matrix3f m = (Matrix3f) value;
			openNode(fb.field);

			startOpeningNode("T3DCreator.Matrix3");
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

			closeNode(fb.field);

		} else if (value instanceof Matrix4f) {
			Matrix4f m = (Matrix4f) value;
			openNode(fb.field);

			startOpeningNode("T3DCreator.Matrix4");
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

			closeNode(fb.field);


		} else {

			openNode(fb.field);
			finishOpeningNode();

			reference = reference_list.indexOf(value);
			if(reference == -1) {
				reference = reference_list.size();
				reference_list.add(value);
			}

			printAttribute("ref", String.valueOf(reference));
			finishOpeningNodeWithSelfClosure();

		}

	} // End of function

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

	@SuppressWarnings("unused")
	private void printContent(String text) throws IOException {
		printIndentation();
		writer.write(StringEscapeUtils.escapeXml11(text));
		writer.write("\n");
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

	private static final String ROOT_TAG = "DATAFILE";
	private static final String SPACES = "                                        ";
	private static final int SPACES_LENGTH = SPACES.length();

} // End of class