package hu.csega.games.library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.SAXException;

import hu.csega.games.library.animation.SAnimation;
import hu.csega.games.library.migration.MigrationException;
import hu.csega.games.library.migration.SMigration;
import hu.csega.games.library.model.SAnimationRef;
import hu.csega.games.library.util.FileUtil;
import hu.csega.games.library.xml.v1.XmlReader;

public class AnimationLibrary {

	private FileUtil fileUtil;

	public AnimationLibrary(FileUtil fileUtil) {
		this.fileUtil = fileUtil;

		String animsPath = fileUtil.projectPath() + "anims";
		List<String> ret = new ArrayList<>();
		FileUtil.collectFiles(animsPath, ret);

		this.animations = new HashMap<>();
		for(String fileName : ret) {
			String defaultName = FileUtil.cleanUpName(fileName);
			SAnimation animation = load(fileName, defaultName);
			SAnimationRef key = new SAnimationRef(animation.getName());
			this.animations.put(key, animation);
		}
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

	private Map<SAnimationRef, SAnimation> animations;
}