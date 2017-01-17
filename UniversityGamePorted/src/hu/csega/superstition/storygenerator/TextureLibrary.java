package hu.csega.superstition.storygenerator;

public class TextureLibrary
{
	private static Device device;
	private static ArrayList library;

	/// <summary>
	/// Constructor.
	/// </summary>
	/// <param name="_device">Device used by DirectX Engine.</param>
	public static void InitializeTextureLibrary(Device _device, ImageList il)
	{
		device = _device;
		library = new ArrayList(0);
	}

	/// <summary>
	/// Gets a Texture belongs to a Filename.
	/// </summary>
	/// <param name="FileName">Texture File Name.</param>
	/// <returns>Texture Reference.</returns>
	public static Texture getTexture(string FileName)
	{
		if(FileName.CompareTo("mirror") == 0) return null;
		Texture tex = null;

		foreach(object texid in library)
		{
			if((tex = (texid as TextureID).getFace(FileName)) != null) break;
		}

		if(tex == null)
		{
			TextureID id = new TextureID(device, FileName);
			library.Add(id);
			tex = id.getFace(FileName);
		}

		return tex;
	}

	/// <summary>
	/// Disposes every used Texture in Library.
	/// </summary>
	public static void TextureDispose()
	{
		foreach(object texid in library) (texid as TextureID).Dispose();
	}
}