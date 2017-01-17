package hu.csega.superstition.game.object;

class MeshMapObject : MapObject
{
	protected Element element;
	protected float Angle;
	protected string mesh;

	public MeshMapObject(string mesh, Vector3 position)
		: base(position, position)
	{
		this.position = position;
		this.Angle = 0f;
		this.mesh = mesh;
	}

	/// <summary>
	/// Serializable data class for Mesh Map Object.
	/// </summary>
	[Serializable]
	protected class MeshMapObjectData : GameObjectData
	{
		public float angle;
		public Vector3 position;
		public string mesh;

		public MeshMapObjectData()
		{
			description = "Mesh Map Object";
		}

		public override object create()
		{
			return new MeshMapObject(this);
		}

	}

	public MeshMapObject(GameObjectData data)
		: base(new Vector3(), new Vector3())
	{
		MeshMapObjectData d = data as MeshMapObjectData;
		this.Angle = d.angle;
		this.position = d.position;
		this.mesh = d.mesh;
		element = Library.Meshes().getMesh(mesh);
	}

	public override GameObjectData getData()
	{
		MeshMapObjectData ret = new MeshMapObjectData();
		ret.angle = this.Angle;
		ret.mesh = this.mesh;
		ret.position = this.position;
		return ret;
	}

	public override void Build(Engine engine)
	{
		element = Library.Meshes().getMesh(mesh);
	}


	public override void Render()
	{
		element.Render(position, 0f, Angle, 0f);
	}
}