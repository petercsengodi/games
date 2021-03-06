package hu.csega.superstition.xml;

import java.io.ByteArrayOutputStream;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hu.csega.superstition.gamelib.legacy.modeldata.CVertex;
import hu.csega.superstition.util.FileUtil;

public class XmlWriterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		CVertex obj = new CVertex();
		obj.position = new Vector3f(1f, 2f, 3f);
		obj.texture_coordinates = new Vector2f(1.2f, 1.5f);

		try (XmlWriter writer = new XmlWriter(stream)) {
			writer.write(obj);
		}

		String result = new String(stream.toByteArray(), FileUtil.CHARSET);
		System.out.println(result);
	}

}
