package hu.csega.superstition.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.csega.superstition.gamelib.animationdata.CModelData;
import hu.csega.superstition.util.FileUtil;

public class XmlReaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		String path = FileUtil.workspaceRootOrTmp() + "/UniversityGamePorted/res/anims/1.anm";
		System.out.println(path);
		Assert.assertTrue(new File(path).exists());

		Object root = XmlReader.read(path);
		Assert.assertTrue(root instanceof CModelData);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try (XmlWriter writer = new XmlWriter(stream)) {
			writer.write(root);
		}

		String result = new String(stream.toByteArray(), FileUtil.CHARSET);
		System.out.println(result);

	}

}