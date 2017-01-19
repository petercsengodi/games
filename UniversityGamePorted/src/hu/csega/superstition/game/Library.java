package hu.csega.superstition.game;

public abstract class Library {

	private static MeshLibrary meshes;
	private static AnimationLibrary animations;
	private static TextureLibrary textures;

	public static void Initialize(Engine engine)
	{
		if(meshes != null) return;
		meshes = MeshLibrary.create(engine);
		animations = AnimationLibrary.create(engine);
		textures = TextureLibrary.create(engine);
	}

	public static MeshLibrary Meshes()
	{
		return meshes;
	}

	public static AnimationLibrary Animations()
	{
		return animations;
	}

	public static TextureLibrary Textures()
	{
		return textures;
	}

	public static void SDispose()
	{
		if(meshes == null) return;
		meshes.Dispose();
		meshes = null;
		animations.Dispose();
		animations = null;
		textures.Dispose();
		textures = null;
	}

	protected ArrayList library;
	protected Engine engine;

	protected Library(Engine engine)
	{
		this.engine = engine;
		this.library = new ArrayList();
	}

	public void Dispose()
	{
		Clear();
	}

	public virtual void Clear()
	{
		foreach(IDisposable disp in library)
		{
			disp.Dispose();
		}
		library.Clear();
	}
}