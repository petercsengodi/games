package hu.csega.superstition.t3dcreator.texture;

public class TexID
{
	public string name;
	private Texture texture;
	private Image map;

	public Texture Texture
	{
		get { return texture; }
	}

	public Image Map
	{
		get { return map; }
	}

	public TexID(string name, Texture texture, Image map)
	{
		this.name = name;
		this.texture = texture;
		this.map = map;
	}

	private TexID()
	{
		this.name = null;
		this.texture = null;
		this.map = null;
	}

	public static object GetNullInstance()
	{
		return new TexID();
	}

}