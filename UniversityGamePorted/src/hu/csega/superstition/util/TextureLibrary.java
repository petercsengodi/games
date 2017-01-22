package hu.csega.superstition.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import hu.csega.superstition.gamelib.model.STextureRef;

public class TextureLibrary {

	private static TextureLibrary instance;

	static {
		instance = new TextureLibrary();

		String root = FileUtil.workspaceRootOrTmp();
		List<String> ret = new ArrayList<>();
		FileUtil.collectFiles(root + File.separator + "UniversityGamePorted" +
				File.separator + "res" + File.separator + "textures", ret);

		for(String fileName : ret) {
			STextureRef key = new STextureRef();
			key.setName(FileUtil.cleanUpName(fileName));
			BufferedImage value = load(fileName);
			instance.textures.put(key, value);
		}
	}

	public static TextureLibrary instance() {
		return instance;
	}

	public BufferedImage resolve(STextureRef ref) {
		return textures.get(ref);
	}

	private static BufferedImage load(String fileName) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(fileName));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		return bufferedImage;
	}

	private Map<STextureRef, BufferedImage> textures = new HashMap<>();
}