package hu.csega.games.library;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import hu.csega.games.library.model.STextureRef;
import hu.csega.games.library.util.FileUtil;

public class TextureLibrary {

	private FileUtil fileUtil;

	public TextureLibrary (FileUtil fileUtil) {
		String textures = fileUtil.projectPath() + "textures";
		List<String> ret = new ArrayList<>();
		FileUtil.collectFiles(textures, ret);

		this.textures = new HashMap<>();
		for(String fileName : ret) {
			STextureRef key = new STextureRef();
			key.setName(FileUtil.cleanUpName(fileName));
			BufferedImage value = load(fileName);
			this.textures.put(key, value);
		}
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

	private Map<STextureRef, BufferedImage> textures;
}