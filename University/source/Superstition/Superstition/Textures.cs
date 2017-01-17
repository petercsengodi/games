using System;
using System.Collections;
using Microsoft.DirectX;
using Microsoft.DirectX.Direct3D;

namespace Engine 
{
	/// <summary>
	/// Library for Textures.
	/// </summary>
	public sealed class TextureLibrary : Library
	{

		/// <summary>
		/// Single instance.
		/// </summary>
		public static TextureLibrary create(Engine engine)
		{
			return new TextureLibrary(engine);
		}
		
		/// <summary>
		/// Constructor.
		/// </summary>
		/// <param name="_device">Device used by DirectX Engine.</param>
		public TextureLibrary(Engine engine) : base(engine)
		{
		}

		/// <summary>
		/// Gets a Texture belongs to a Filename.
		/// </summary>
		/// <param name="FileName">Texture File Name.</param>
		/// <returns>Texture Reference.</returns>
		public Texture getTexture(string FileName)
		{
			if(FileName.CompareTo("null") == 0) return null;
			Texture tex = null;
			
			foreach(object texid in library)
			{
				if((tex = (texid as TextureID).getFace(FileName)) != null) break;
			}

			if(tex == null)
			{
				TextureID id = new TextureID(engine.Device, FileName);
				library.Add(id);
				tex = id.getFace(FileName);
			}

			return tex;
		}

	}
	
	/// <summary>
	/// Class for a single Texture.
	/// </summary>
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
}