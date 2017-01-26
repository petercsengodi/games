package hu.csega.superstition.t3dcreator.operations;

public class ChangeMaterial extends Operation
{
	private int ambient, diffuse, emissive;
	private int old_ambient, old_diffuse, old_emissive;
	private CFigure figure;

	public ChangeMaterial(CFigure figure,
			int ambient, int diffuse, int emissive)
	{
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.emissive = emissive;

		this.figure = figure;

		this.old_ambient = figure.ambient_color;
		this.old_diffuse = figure.diffuse_color;
		this.old_emissive = figure.emissive_color;
	}

	@Override
	public override void OnTransform()
	{
		figure.ambient_color = ambient;
		figure.diffuse_color = diffuse;
		figure.emissive_color = emissive;
	}

	@Override
	public override void OnInvert()
	{
		figure.ambient_color = old_ambient;
		figure.diffuse_color = old_diffuse;
		figure.emissive_color = old_emissive;
	}

}