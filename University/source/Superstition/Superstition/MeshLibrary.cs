using System;
using System.Drawing;
using System.Collections;

namespace Engine
{
	/// <summary>
	/// Summary description for MeshLibrary.
	/// </summary>
	sealed public class MeshLibrary : Library
	{

		/// <summary>
		/// Class for representing meshes in the list
		/// </summary>
		private class MeshId : IDisposable
		{
			/// <summary>
			/// Mesh's name
			/// </summary>
			public string name;

			/// <summary>
			/// Mesh's memory representation
			/// </summary>
			public Element mesh;

			#region IDisposable Members

			public void Dispose()
			{
//				mesh.Dispose();
			}

			#endregion
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="engine">Actual engine</param>
		private MeshLibrary(Engine engine) : base(engine)
		{
		}

		/// <summary>
		/// Gets a Mesh from Library with full options.
		/// </summary>
		/// <param name="name">Mesh name</param>
		/// <param name="flags">Option flags</param>
		/// <param name="color">Sparkling color, if necessary</param>
		/// <returns>Mesh element</returns>
		private Element getMesh(string name, EngineMeshFlags flags, Color color)
		{
			string idstring = (string)name.Clone();
			if((flags & EngineMeshFlags.NoShadow) > 0) 
				idstring = idstring + "|NoShadow";
			if((flags & EngineMeshFlags.Colored) > 0) 
				idstring = idstring + "|Color " + color.R.ToString() +
					":" + color.G.ToString() + ":" + color.A.ToString();

			foreach(object o in library)
			{
				MeshId id = o as MeshId;
				if(id.name.Equals(idstring))
				{
					return id.mesh;
				}
			}

			MeshId idx = new MeshId();
			idx.name = idstring;
			idx.mesh = engine.GetMeshElement(name, flags, color);
			library.Add(idx);
			Console.WriteLine("Mesh Library : loaded mesh [ " + idstring + "]");
			return idx.mesh;
		}

		/// <summary>
		/// Gets Mesh from Library with sparkling color. Automatically shadowed.
		/// </summary>
		/// <param name="name">Mesh name.</param>
		/// <param name="color">Sparkling color.</param>
		/// <returns>Mesh element.</returns>
		public Element getMesh(string name, Color color)
		{
			return getMesh(name, EngineMeshFlags.Colored, color);
		}

		/// <summary>
		/// Gets Mesh from Library. Automatically shadowed;
		/// </summary>
		/// <param name="name">Mesh name.</param>
		/// <returns>Mesh element.</returns>
		public Element getMesh(string name)
		{
			return getMesh(name, EngineMeshFlags.None, Color.Transparent);
		}

		/// <summary>
		/// Gets Mesh from Library with sparkling color and shadow option.
		/// </summary>
		/// <param name="name">Mesh name.</param>
		/// <param name="shadow">True if shadowed.</param>
		/// <param name="color">Sparkling color.</param>
		/// <returns>Mesh element</returns>
		public Element getMesh(string name, bool shadow,  Color color)
		{
			EngineMeshFlags flags = EngineMeshFlags.Colored;
			if(!shadow) flags = flags | EngineMeshFlags.NoShadow;
			return getMesh(name, flags, color);
		}

		/// <summary>
		/// Gets Mesh from Library with shadow option.
		/// </summary>
		/// <param name="name">Mesh name.</param>
		/// <param name="shadow">True if shadowed.</param>
		/// <returns>Mesh element.</returns>
		public Element getMesh(string name, bool shadow)
		{
			EngineMeshFlags flags = EngineMeshFlags.None;
			if(!shadow) flags = flags | EngineMeshFlags.NoShadow;
			return getMesh(name, flags, Color.Transparent);
		}

		/// <summary>
		/// Initializes Mesh Library.
		/// </summary>
		/// <param name="engine">Game engine.</param>
		public static MeshLibrary create(Engine engine)
		{
			return new MeshLibrary(engine);
		}

	}

	/// <summary>
	/// Enumeration for Mesh Library Mesh Falgs.
	/// </summary>
	public enum EngineMeshFlags
	{
		None = 0,
		NoShadow = 1,
		Colored = 2
	}

}
