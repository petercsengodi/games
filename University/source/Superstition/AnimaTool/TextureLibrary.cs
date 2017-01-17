using System;
using System.IO;
using System.Drawing;
using System.Collections;
using System.Windows.Forms;

using Microsoft.DirectX;
using Microsoft.DirectX.Direct3D;

namespace AnimaTool
{
	/// <summary>
	/// Summary description for TextureLibrary.
	/// </summary>
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
			if(name == null) return null;
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
			TexID id = new TexID(name, texture);
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
				id.Texture.Dispose();
			}
			dialog.Dispose();
		}
	}

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

}
