package hu.csega.superstition.game.maze;

class FaceLibrary
{
	class Cache
	{
		public Cache(string library, string[] list)
		{
			this.library = library;
			this.list = list;
		}

		public string library;
		public string[] list;
	}

	protected static ArrayList cache = null;

	public static string[] getLibrary(string library)
	{
		if(cache == null) cache = new ArrayList();

		foreach(Object o in cache)
		{
			Cache c = o as Cache;
			if(c.library == library) return c.list;
		}

		DirectoryInfo info = new DirectoryInfo(@"..\textures\" + library);
		FileInfo[] files = info.GetFiles();
		string[] list = new string[files.Length];
		for(int i = 0; i < files.Length; i++)
		{
			list[i] = @"..\textures\" + library + "\\" + files[i].Name;
		}

		cache.Add(new Cache(library, list));
		return list;
	}

	public static string RandomGetFromLibrary(string library)
	{
		string[] list = getLibrary(library);
		int idx = (int)Math.Round(StaticRandomLibrary.DoubleValue(list.Length));
		return list[idx];
	}

	public static string RandomGetFromLibrary(string[] list)
	{
		int idx = (int)Math.Floor(StaticRandomLibrary.DoubleValue(list.Length));
		return list[idx];
	}
}