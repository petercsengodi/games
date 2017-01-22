package hu.csega.superstition.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.csega.superstition.gamelib.legacy.migration.SMigration;
import hu.csega.superstition.gamelib.legacy.modeldata.CModel;
import hu.csega.superstition.gamelib.model.SMesh;
import hu.csega.superstition.gamelib.model.SObject;
import hu.csega.superstition.util.FileUtil;

public class XmlMigrationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testModelMigration() throws Exception {
		String path = FileUtil.workspaceRootOrTmp() + "/UniversityGamePorted/res/t3d_files/3.t3d";
		System.out.println(path);
		Assert.assertTrue(new File(path).exists());

		Object root = XmlReader.read(path);
		Assert.assertTrue(root instanceof CModel);

		SObject migrated = SMigration.migrate(root, "test");
		Assert.assertTrue(migrated instanceof SMesh);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try (XmlWriter writer = new XmlWriter(stream)) {
			writer.write(migrated);
		}

		String result = new String(stream.toByteArray(), FileUtil.CHARSET);
		System.out.println(result);

		ByteArrayInputStream bais = new ByteArrayInputStream(result.getBytes(FileUtil.CHARSET));
		Object read = XmlReader.read(bais);
		Assert.assertTrue(read instanceof SMesh);
	}

}
