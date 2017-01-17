package hu.csega.superstition.animatool;

public class MeshLibrary : IDisposable
{
	private static MeshLibrary instance;

	private ArrayList list;
	private Device device;
	private OpenFileDialog dialog;

	private MeshLibrary(Device device)
	{
		this.device = device;
		list = new ArrayList();
		dialog = new OpenFileDialog();
		dialog.Filter = "Mesh files (*.x)|*.x|All Files (*.*)|*.*";
		dialog.InitialDirectory = @"..\..\..\Superstition\bin\meshes\";
		dialog.Multiselect = false;
		dialog.RestoreDirectory = true;
	}

	public static void Initialize(Device device)
	{
		if(instance == null)
			instance = new MeshLibrary(device);
	}

	public static void DisposeLibrary()
	{
		if(instance != null) instance.Dispose();
	}

	public static MeshLibrary Instance()
	{
		return instance;
	}

	public MeshID LoadMesh(string name)
	{
		int idx, c = list.Count;
		for(idx = 0; idx < c; idx++)
		{
			if(name.Equals((list[idx] as MeshID).name))
			{
				return (list[idx] as MeshID);
			}
		}
		idx = c;

		string fname = @"..\..\..\Superstition\bin\meshes\" + name;
		ExtendedMaterial[] mats;
		Mesh mesh = Mesh.FromFile(fname, 0, device, out mats);
		Mesh simple_mesh = mesh.Clone(MeshFlags.Use32Bit,
			CustomVertex.PositionOnly.Format, device);

		int subsets = mats.Length;
		Material[] materials = new Material[subsets];
		TexID[] textures = new TexID[subsets];

		for(int i = 0; i < subsets; i++)
		{
			materials[i] = mats[i].Material3D;
			textures[i] = TextureLibrary.Instance().LoadImage(
				mats[i].TextureFilename);
		}

		MeshID id = new MeshID(name, mesh, simple_mesh,
			materials, textures, subsets);
		list.Add(id);
		return id;
	}

	public static MeshID LoadMesh()
	{
		string filename;
		DialogResult res = instance.dialog.ShowDialog();
		if((res != DialogResult.Cancel) &&
			(res != DialogResult.No))
		{
			filename = Path.GetFileName(
				instance.dialog.FileName);
			return instance.LoadMesh(filename);
		}
		return null;
	}

	public void Dispose()
	{
		foreach(MeshID id in list)
		{
			id.Mesh.Dispose();
			id.SimpleMesh.Dispose();
		}
		dialog.Dispose();
	}
}