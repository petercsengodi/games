package hu.csega.superstition.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.csega.superstition.gamelib.model.SAnimationRef;
import hu.csega.superstition.gamelib.model.SMeshRef;
import hu.csega.superstition.gamelib.model.STextureRef;
import hu.csega.superstition.gamelib.model.animation.SAnimation;
import hu.csega.superstition.gamelib.model.mesh.SMesh;

public class FileUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFileCollection1() {
		String root = FileUtil.workspaceRootOrTmp();
		List<String> ret = new ArrayList<>();
		FileUtil.collectFiles(root + File.separator + "UniversityGamePorted" +
				File.separator + "res" + File.separator + "textures", ret);

		for(String s : ret)
			System.out.println(s);
	}

	@Test
	public void testFileCollection2() {
		String root = FileUtil.workspaceRootOrTmp();
		List<String> ret = new ArrayList<>();
		FileUtil.collectFiles(root + File.separator + "UniversityGamePorted" +
				File.separator + "res" + File.separator + "t3d_files", ret);

		for(String s : ret)
			System.out.println(s);
	}

	@Test
	public void testTextureLibrary() {
		long start = System.currentTimeMillis();

		STextureRef ref = new STextureRef("evilwall01");
		BufferedImage image = TextureLibrary.instance().resolve(ref);
		Assert.assertNotNull(image);

		long end = System.currentTimeMillis();
		double duration = (end - start) / 1000.0;
		System.out.println("duration: " + duration + " sec.");
	}

	@Test
	public void testMeshLibrary() {
		long start = System.currentTimeMillis();

		SMeshRef ref = new SMeshRef("human_torso");
		SMesh mesh = MeshLibrary.instance().resolve(ref);
		Assert.assertNotNull(mesh);

		long end = System.currentTimeMillis();
		double duration = (end - start) / 1000.0;
		System.out.println("duration: " + duration + " sec.");
	}

	@Test
	public void testAnimationLibrary() {
		long start = System.currentTimeMillis();

		SAnimationRef ref = new SAnimationRef("human_run");
		SAnimation mesh = AnimationLibrary.instance().resolve(ref);
		Assert.assertNotNull(mesh);

		long end = System.currentTimeMillis();
		double duration = (end - start) / 1000.0;
		System.out.println("duration: " + duration + " sec.");
	}
}
