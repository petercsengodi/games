package hu.csega.superstition.game.tetxure;

public class TextureID : IDisposable
{
	private string name;
	private Texture face;

	/// <summary>
	/// Constructor.
	/// </summary>
	/// <param name="device">Device used by DirectX Engine.</param>
	/// <param name="_name">Texture's filename.</param>
	public TextureID(Device device, string _name)
	{
		name = (string)(_name.Clone());
		face = TextureLoader.FromFile(device, _name);
	}

	/// <summary>
	/// Disposes the Texture.
	/// </summary>
	public void Dispose()
	{
		face.Dispose();
	}

	/// <summary>
	/// Gets Texture from Filename.
	/// </summary>
	/// <param name="_name">Filename.</param>
	/// <returns>Texture reference, is Filenames equal, otherwise _null_.</returns>
	public Texture getFace(string _name)
	{
		if(name.CompareTo(_name) == 0) return face;
		else return null;
	}
}