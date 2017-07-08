package hu.csega.superstition.unported.game.elements;

import org.joml.Vector3f;

import hu.csega.superstition.game.object.Clipper;
import hu.csega.superstition.gamelib.network.GameObjectData;

public class FinishPlace extends Clipper implements IGameElement {

	protected float radius;

	protected Engine engine;
	protected Element element;
	protected float Angle;
	protected String mesh;

	protected float range;
	protected Light light;

	protected Model model;

	protected static float speed = 0.01f, limit = (float)(Math.PI * 2.0);

	public FinishPlace(Vector3f position, float radius) {
		super(position - new Vector3f(radius, radius, radius),
				position + new Vector3f(radius, radius, radius));
		this.position = position;
		this.radius = 0.1f;
		this.mesh = "end.x";
		this.Angle = 0f;
		this.range = 10f;
	}

	protected class FinishPlaceData extends GameObjectData {
		public float angle;
		public Vector3f position;

		public FinishPlaceData()
		{
			description = "Finish Place";
		}

		public Object create()
		{
			return new FinishPlace(this);
		}

	}

	public FinishPlace(GameObjectData data) {
		super(new Vector3f(0f,0f,0f), new Vector3f(0f,0f,0f));
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
		this.corner1 = - new Vector3f(radius, radius, radius);
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

	@Override
	public void PlayerEffect(Object player)
	{
		model.FinishGame(new EndOfGame(true));
	}

	public void Clip(Clipable clipable)
	{
		super.Clip (clipable);
	}


	public void SetModel(Model model)
	{
		this.model = model;
	}

}
