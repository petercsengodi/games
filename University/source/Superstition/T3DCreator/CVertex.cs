using System;
using System.Collections;

using Microsoft.DirectX;

namespace T3DCreator
{
	/// <summary>
	/// Summary description for Vertex.
	/// </summary>
	public class CVertex : IPart
	{
		public Vector3 position;
		public Vector2 texture_coordinates;

		public CVertex()
		{
			this.texture_coordinates = new Vector2(0f, 0f);
			this.position = new Vector3(0f, 0f, 0f);
		}

		public CVertex(Vector3 position)
		{
			this.texture_coordinates = new Vector2(0f, 0f);
			this.position = position;
		}

		public CVertex(Vector3 position, Vector2 texture_coordinates)
		{
			this.texture_coordinates = texture_coordinates;
			this.position = position;
		}

		public override string ToString()
		{
			return "Vertex (" + position.X.ToString() + ";" +
				position.Y.ToString() + ";" + 
				position.Z.ToString() + ") [" +
				texture_coordinates.X.ToString() + ";" + 
				texture_coordinates.Y.ToString() + "]";
		}

		#region IPart Members

		public void move(Vector3 direction)
		{
			this.position += direction;
		}

		public void moveTexture(Vector2 direction)
		{
			this.texture_coordinates += direction;
			if(texture_coordinates.X < 0f) 
				texture_coordinates.X = 0f;
			if(texture_coordinates.X > 1f) 
				texture_coordinates.X = 1f;
			if(texture_coordinates.Y < 0f) 
				texture_coordinates.Y = 0f;
			if(texture_coordinates.Y > 1f) 
				texture_coordinates.Y = 1f;
		}

		public bool hasPart(IPart part)
		{
			return part.Equals(this);
		}

		public Vector3 centerPoint()
		{
			return position;
		}

		public void scale(Matrix matrix)
		{
			position = Vector3.TransformCoordinate(
				position, matrix);
		}

		#endregion

	}
}
