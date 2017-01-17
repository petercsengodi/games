package hu.csega.superstition.t3dcreator.texture;

public class TextureLibrary : IDisposable
{
	private static TextureLibrary instance;

	private ArrayList list;
	private Device device;
	private OpenFileDialog dialog;

	private TextureLibrary(Device device)
	{
		this.device = device;
		list = new ArrayList();
		dialog = new OpenFileDialog();
		dialog.Filter = "BMP files (*.bmp)|*.bmp|All Files (*.*)|*.*";
		dialog.InitialDirectory = @"..\..\..\Superstition\bin\textures\mesh_textures";
		dialog.Multiselect = false;
		dialog.RestoreDirectory = true;
	}

	public static void Initialize(Device device)
	{
		if(instance == null)
			instance = new TextureLibrary(device);
	}

	public static void DisposeLibrary()
	{
		if(instance != null) instance.Dispose();
	}

	public static TextureLibrary Instance()
	{
		return instance;
	}

	public TexID LoadImage(string name)
	{
		int idx, c = list.Count;
		for(idx = 0; idx < c; idx++)
		{
			if(name.Equals((list[idx] as TexID).name))
			{
				return (list[idx] as TexID);
			}
		}
		idx = c;

		string fname = @"..\..\..\Superstition\bin\textures\mesh_textures\" + name;
		Texture texture = TextureLoader.FromFile(device, fname);
		Image map = Image.FromFile(fname);
		TexID id = new TexID(name, texture, map);
		list.Add(id);
		return id;
	}

	public static TexID LoadImage()
	{
		string filename;
		DialogResult res = instance.dialog.ShowDialog();
		if((res != DialogResult.Cancel) &&
			(res != DialogResult.No))
		{
			filename = Path.GetFileName(
				instance.dialog.FileName);
			return instance.LoadImage(filename);
		}
		return null;
	}

	public void Dispose()
	{
		foreach(TexID id in list)
		{
			id.Map.Dispose();
			id.Texture.Dispose();
		}
		dialog.Dispose();
	}
}