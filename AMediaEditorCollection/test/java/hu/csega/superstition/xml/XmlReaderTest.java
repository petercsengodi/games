package hu.csega.superstition.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.csega.superstition.gamelib.legacy.animationdata.CModelData;
import hu.csega.superstition.gamelib.legacy.modeldata.CModel;
import hu.csega.superstition.gamelib.model.story.SMap;
import hu.csega.superstition.util.FileUtil;

public class XmlReaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLegacyAnimation() throws Exception {
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

	@Test
	public void testLegacyT3DCreatorModel() throws Exception {
		String path = FileUtil.workspaceRootOrTmp() + "/UniversityGamePorted/res/t3d_files/3.t3d";
		System.out.println(path);
		Assert.assertTrue(new File(path).exists());

		Object root = XmlReader.read(path);
		Assert.assertTrue(root instanceof CModel);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try (XmlWriter writer = new XmlWriter(stream)) {
			writer.write(root);
		}

		String result = new String(stream.toByteArray(), FileUtil.CHARSET);
		System.out.println(result);
	}

	@Test
	public void testLegacyT3DCreatorModel_To_NewModel() throws Exception {
		String path = FileUtil.workspaceRootOrTmp() + "/UniversityGamePorted/res/t3d_files/3.t3d";
		System.out.println(path);
		Assert.assertTrue(new File(path).exists());

		Object root = XmlReader.read(path);
		Assert.assertTrue(root instanceof CModel);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try (XmlWriter writer = new XmlWriter(stream)) {
			writer.write(root);
		}

		String result = new String(stream.toByteArray(), FileUtil.CHARSET);
		System.out.println(result);

		ByteArrayInputStream bais = new ByteArrayInputStream(result.getBytes(FileUtil.CHARSET));
		Object read = XmlReader.read(bais);
		Assert.assertTrue(read instanceof CModel);
	}

	@Test
	public void testMap1() throws Exception {
		String path = FileUtil.workspaceRootOrTmp() + "/UniversityGamePorted/res/maps/fake.xml";
		System.out.println(path);
		Assert.assertTrue(new File(path).exists());

		Object root = XmlReader.read(path);
		Assert.assertTrue(root instanceof SMap);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try (XmlWriter writer = new XmlWriter(stream)) {
			writer.write(root);
		}

		String result = new String(stream.toByteArray(), FileUtil.CHARSET);
		System.out.println(result);

		ByteArrayInputStream bais = new ByteArrayInputStream(result.getBytes(FileUtil.CHARSET));
		Object read = XmlReader.read(bais);
		Assert.assertTrue(read instanceof SMap);
	}

	@Test
	public void testMap2() throws Exception {
		String path = FileUtil.workspaceRootOrTmp() + "/UniversityGamePorted/res/maps/gun.xml";
		System.out.println(path);
		Assert.assertTrue(new File(path).exists());

		Object root = XmlReader.read(path);
		Assert.assertTrue(root instanceof SMap);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try (XmlWriter writer = new XmlWriter(stream)) {
			writer.write(root);
		}

		String result = new String(stream.toByteArray(), FileUtil.CHARSET);
		System.out.println(result);

		ByteArrayInputStream bais = new ByteArrayInputStream(result.getBytes(FileUtil.CHARSET));
		Object read = XmlReader.read(bais);
		Assert.assertTrue(read instanceof SMap);
	}

	@Test
	public void testMap3() throws Exception {
		String path = FileUtil.workspaceRootOrTmp() + "/UniversityGamePorted/res/maps/test.xml";
		System.out.println(path);
		Assert.assertTrue(new File(path).exists());

		Object root = XmlReader.read(path);
		Assert.assertTrue(root instanceof SMap);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try (XmlWriter writer = new XmlWriter(stream)) {
			writer.write(root);
		}

		String result = new String(stream.toByteArray(), FileUtil.CHARSET);
		System.out.println(result);

		ByteArrayInputStream bais = new ByteArrayInputStream(result.getBytes(FileUtil.CHARSET));
		Object read = XmlReader.read(bais);
		Assert.assertTrue(read instanceof SMap);
	}

}
