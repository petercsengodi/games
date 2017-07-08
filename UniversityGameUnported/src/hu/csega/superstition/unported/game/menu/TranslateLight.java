package hu.csega.superstition.game.menu;

import java.awt.Color;

import org.joml.Vector3f;

import hu.csega.superstition.game.lights.Light;
import hu.csega.superstition.game.lights.PointLight;
import hu.csega.superstition.unported.game.Engine;

public class TranslateLight implements IMovingLight
{
	protected Light light;
	protected double radiusX, radiusY, phaseX, phaseY;
	protected Vector3f center, position;
	public static double PI2 = 2.0 * Math.PI;
	public static boolean circle;

	public TranslateLight(Engine engine, Color color, Vector3f center,
			double radiusX, double radiusY, double phaseX, double phaseY) {
		this.center = center;
		this.radiusX = radiusX;
		this.radiusY = radiusY;
		this.phaseX = phaseX;
		this.phaseY = phaseY;
		DoPeriod();
		this.light = engine.getPointLight(10f, color, position);
	}

	@Override
	public void Activate()
	{
		((PointLight)light).Position = position;
		light.activate();
	}

	@Override
	public void DeActivate()
	{
		light.deActivate();
	}

	public void doPeriod() {
		phaseX += 0.01;
		if(phaseX > PI2) phaseX -= PI2;
		phaseY += 0.01;
		if(phaseY > PI2) phaseY -= PI2;
		position.x = center.x + (float)(radiusX * Math.cos(phaseX)) + (float)(radiusX * Math.sin(phaseY));
		position.y = center.y + (float)(radiusY * Math.sin(phaseY)) + (float)(radiusX * Math.sin(phaseX));
		position.z = center.z;
	}



}