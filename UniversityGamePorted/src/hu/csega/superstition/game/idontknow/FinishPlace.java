package hu.csega.superstition.game.idontknow;

import hu.csega.superstition.game.object.Clipper;

public class FinishPlace extends Clipper implements IGameElement {

	protected float radius;

	protected Engine engine;
	protected Element element;
	protected float Angle;
	protected string mesh;

	protected float range;
	protected Light light;

	protected Model model;

	protected static float speed = 0.01f, limit = (float)(Math.PI * 2.0);

	public FinishPlace(Vector3 position, float radius) :
		base(position - new Vector3(radius, radius, radius),
				position + new Vector3(radius, radius, radius))
		{
		this.position = position;
		this.radius = 0.1f;
		this.mesh = "end.x";
		this.Angle = 0f;
		this.range = 10f;
		}

	/// <summary>
	/// Serializable data class for symbol.
	/// </summary>
	[Serializable]
			protected class FinishPlaceData : GameObjectData
			{
		public float angle;
		public Vector3 position;

		public FinishPlaceData()
		{
			description = "Finish Place";
		}

		public override object create()
		{
			return new FinishPlace(this);
		}

			}

	public FinishPlace(GameObjectData data) :
		base(new Vector3(0f,0f,0f), new Vector3(0f,0f,0f))
		{
		FinishPlaceData d = data as FinishPlaceData;
		this.Angle = d.angle;
		this.position = d.position;
		this.radius = 0.1f;
		this.mesh = "end.x";
		this.Angle = 0f;
		this.range = 10f;
		}

	@Override
	public GameLib.GameObjectData getData()
	{
		FinishPlaceData ret = new FinishPlaceData();
		ret.angle = this.Angle;
		ret.position = this.position;
		return ret;
	}

	public void Build(Engine engine)
	{
		this.engine = engine;
		element = engine.GetMeshElement(mesh,
				EngineMeshFlags.None, Color.Blue);
		radius = (element as MeshElement).Radius();
		this.corner1 = - new Vector3(radius, radius, radius);
		this.corner2 = - this.corner1;
		light = engine.GetPointLight(range, Color.Blue, position);
	}

	public void PreRender()
	{
		light.Activate();
	}

	public void PostRender()
	{
		light.DeActivate();
	}

	@Override
	public void Render()
	{
		element.Render(position, 0f, Angle, 0f);
	}

	@Override
	public void Period()
	{
		Angle += 0.005f;
		if(Angle > limit) Angle -= limit;
	}

	public override void PlayerEffect(object player)
	{
		model.FinishGame(new EndOfGame(true));
	}

	public override void Clip(Clipable clipable)
	{
		base.Clip (clipable);
	}


	public void SetModel(Model model)
	{
		this.model = model;
	}

}
