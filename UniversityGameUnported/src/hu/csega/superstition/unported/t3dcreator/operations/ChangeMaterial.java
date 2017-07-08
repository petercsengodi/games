package hu.csega.superstition.unported.t3dcreator.operations;

import java.awt.Color;

import hu.csega.superstition.unported.t3dcreator.CFigure;

public class ChangeMaterial extends Operation
{
	private Color ambient, diffuse, emissive;
	private Color old_ambient, old_diffuse, old_emissive;
	private CFigure figure;

	public ChangeMaterial(CFigure figure, Color ambient, Color diffuse, Color emissive) {
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.emissive = emissive;

		this.figure = figure;

		this.old_ambient = figure.getAmbient_color();
		this.old_diffuse = figure.getDiffuse_color();
		this.old_emissive = figure.getEmissive_color();
	}

	@Override
	public void OnTransform()
	{
		figure.setAmbient_color(ambient);
		figure.setDiffuse_color(diffuse);
		figure.setEmissive_color(emissive);
	}

	@Override
	public void OnInvert()
	{
		figure.setAmbient_color(old_ambient);
		figure.setDiffuse_color(old_diffuse);
		figure.setEmissive_color(old_emissive);
	}

}