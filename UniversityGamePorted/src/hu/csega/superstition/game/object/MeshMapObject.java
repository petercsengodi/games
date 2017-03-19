package hu.csega.superstition.game.object;

class MeshMapObject extends MapObject
{
	protected Element element;
	protected float Angle;
	protected string mesh;

	public MeshMapObject(string mesh, Vector3 position) {
		super(position, position);
		this.position = position;
		this.Angle = 0f;
		this.mesh = mesh;
	}

	protected class MeshMapObjectData extends GameObjectData
	{
		public float angle;
		public Vector3 position;
		public string mesh;

		public MeshMapObjectData()
		{
			description = "Mesh Map Object";
		}

		public Object create()
		{
			return new MeshMapObject(this);
		}

	}

	public MeshMapObject(GameObjectData data) {
		super(new Vector3(), new Vector3());
		MeshMapObjectData d = data as MeshMapObjectData;
		this.Angle = d.angle;
		this.position = d.position;
		this.mesh = d.mesh;
		element = Library.Meshes().getMesh(mesh);
	}

	@Override
	public GameObjectData getData()
	{
		MeshMapObjectData ret = new MeshMapObjectData();
		ret.angle = this.Angle;
		ret.mesh = this.mesh;
		ret.position = this.position;
		return ret;
	}

	public void Build(Engine engine)
	{
		element = Library.Meshes().getMesh(mesh);
	}


	@Override
	public void Render()
	{
		element.Render(position, 0f, Angle, 0f);
	}
}