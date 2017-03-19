package hu.csega.superstition.game.menu;

import org.joml.Vector3f;

public class CircleLight implements IMovingLight
{
	protected Light light;
	protected double radiusX, radiusY, phaseX, phaseY;
	protected Vector3f center, position;
	public static double PI2 = 2.0 * Math.PI;
	public static boolean circle;

	public CircleLight(Engine engine, Color color, Vector3f center,
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

	@Override
	public void Activate()
	{
		(light as PointLight).Position = position;
		light.Activate();
	}

	@Override
	public void DeActivate()
	{
		light.DeActivate();
	}

	@Override
	public void DoPeriod()
	{
		phaseX += 0.01;
		if(phaseX > PI2) phaseX -= PI2;
		phaseY += 0.01;
		if(phaseY > PI2) phaseY -= PI2;
		position.X = center.X + (float)(radiusX * Math.Sin(phaseX));
		position.Y = center.Y + (float)(radiusY * Math.Sin(phaseY));
		position.Z = center.Z + (float)(
				radiusX * Math.Cos(phaseX) + radiusY * Math.Cos(phaseY));
	}
}