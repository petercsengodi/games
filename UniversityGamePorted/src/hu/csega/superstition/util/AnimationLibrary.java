package hu.csega.superstition.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.SAXException;

import hu.csega.superstition.gamelib.legacy.migration.MigrationException;
import hu.csega.superstition.gamelib.legacy.migration.SMigration;
import hu.csega.superstition.gamelib.model.SAnimationRef;
import hu.csega.superstition.gamelib.model.animation.SAnimation;
import hu.csega.superstition.xml.XmlReader;

public class AnimationLibrary {

	private static AnimationLibrary instance;

	static {
		instance = new AnimationLibrary();

		String root = FileUtil.workspaceRootOrTmp();
		List<String> ret = new ArrayList<>();
		FileUtil.collectFiles(root + File.separator + "UniversityGamePorted" +
				File.separator + "res" + File.separator + "anims", ret);

		for(String fileName : ret) {
			String defaultName = FileUtil.cleanUpName(fileName);
			SAnimation animation = load(fileName, defaultName);
			SAnimationRef key = new SAnimationRef(animation.getName());
			instance.animations.put(key, animation);
		}
	}

	public static AnimationLibrary instance() {
		return instance;
	}

	public SAnimation resolve(SAnimationRef ref) {
		return animations.get(ref);
	}

	private static SAnimation load(String fileName, String defaultName) {
		SAnimation animation;
		try {
			animation = (SAnimation) SMigration.migrate(XmlReader.read(fileName), defaultName);
		} catch (IOException | MigrationException | SAXException ex) {
			throw new RuntimeException(ex);
		}

		return animation;
	}

	private Map<SAnimationRef, SAnimation> animations = new HashMap<>();
}