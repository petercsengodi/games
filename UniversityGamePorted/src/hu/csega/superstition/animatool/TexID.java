package hu.csega.superstition.animatool;

public class TexID
{
	public string name;
	private Texture texture;

	public Texture Texture
	{
		get { return texture; }
	}

	public TexID(string name, Texture texture)
	{
		this.name = name;
		this.texture = texture;
	}

	private TexID()
	{
		this.name = null;
		this.texture = null;
	}

	public static object GetNullInstance()
	{
		return new TexID();
	}

}
