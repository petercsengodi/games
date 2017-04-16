package hu.csega.superstition.game.maze;

class FaceLibrary
{
	class Cache
	{
		public Cache(String library, string[] list)
		{
			this.library = library;
			this.list = list;
		}

		public String library;
		public string[] list;
	}

	protected static ArrayList cache = null;

	public static string[] getLibrary(String library)
	{
		if(cache == null) cache = new ArrayList();

		for(Object o : cache)
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

	public static String RandomGetFromLibrary(String library)
	{
		string[] list = getLibrary(library);
		int idx = (int)Math.Round(StaticRandomLibrary.DoubleValue(list.Length));
		return list[idx];
	}

	public static String RandomGetFromLibrary(string[] list)
	{
		int idx = (int)Math.Floor(StaticRandomLibrary.DoubleValue(list.Length));
		return list[idx];
	}
}