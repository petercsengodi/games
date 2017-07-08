package hu.csega.superstition.unported.game;

import hu.csega.superstition.util.AnimationLibrary;
import hu.csega.superstition.util.MeshLibrary;
import hu.csega.superstition.util.TextureLibrary;

public abstract class Library {

	private static MeshLibrary meshes = MeshLibrary.instance();
	private static AnimationLibrary animations = AnimationLibrary.instance();
	private static TextureLibrary textures = TextureLibrary.instance();

	public static void Initialize(Engine engine) {
	}

	public static MeshLibrary Meshes() {
		return meshes;
	}

	public static AnimationLibrary Animations() {
		return animations;
	}

	public static TextureLibrary Textures() {
		return textures;
	}

	public static void SDispose() {
	}

	protected Engine engine;

	protected Library(Engine engine) {
		this.engine = engine;
	}

	public void dispose() {
		Clear();
	}

	public void Clear() {
	}
}