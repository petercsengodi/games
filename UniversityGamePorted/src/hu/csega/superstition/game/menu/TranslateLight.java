package hu.csega.superstition.game.menu;

public class TranslateLight : IMovingLight
{
	protected Light light;
	protected double radiusX, radiusY, phaseX, phaseY;
	protected Vector3 center, position;
	public static double PI2 = 2.0 * Math.PI;
	public static bool circle;

	public TranslateLight(Engine engine, Color color, Vector3 center,
		double radiusX, double radiusY, double phaseX, double phaseY)
	{
		this.center = center;
		this.radiusX = radiusX;
		this.radiusY = radiusY;
		this.phaseX = phaseX;
		this.phaseY = phaseY;
		DoPeriod();
		this.light = engine.GetPointLight(10f, color, position);
	}

	#region IMovingLight Members

	public void Activate()
	{
		(light as PointLight).Position = position;
		light.Activate();
	}

	public void DeActivate()
	{
		light.DeActivate();
	}

	public void DoPeriod()
	{
		phaseX += 0.01;
		if(phaseX > PI2) phaseX -= PI2;
		phaseY += 0.01;
		if(phaseY > PI2) phaseY -= PI2;
		position.X = center.X + (float)(radiusX * Math.Cos(phaseX)) + (float)(radiusX * Math.Sin(phaseY));
		position.Y = center.Y + (float)(radiusY * Math.Sin(phaseY)) + (float)(radiusX * Math.Sin(phaseX));
		position.Z = center.Z;
	}

	#endregion

}