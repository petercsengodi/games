package hu.csega.superstition.game.elements;

import org.joml.Vector3f;

public class MeshText extends Element {

	private Material material;
	private Mesh mesh;
	private GlyphMetricsFloat[] metrics;
	private float extrusion;
	private Vector3f center = new Vector3f(0f, 0f, 0f);
	public Vector3f Center { get { return center; } }
	public String test;

	public MeshText(Engine engine, System.Drawing.Font font, String text, float deviation, float extrusion)
	{
		super(engine);
		test = text;
		mesh = Mesh.TextFromFont(engine.Device, font, text, deviation, extrusion, out metrics);
		engine.AddToDisposeList(mesh);
		material = new Material();
		material.Ambient = Color.White;
		material.Diffuse = Color.White;

		this.extrusion = extrusion;

		for(int i = 0; i < metrics.Length; i++)
		{
			center.X += metrics[i].BlackBoxX;
			center.Y = Math.Max(center.Y, metrics[i].BlackBoxY);
		}
		center.Z = -extrusion / 2f;
	}

	public @Override
	void Render()
	{
		if(engine.IsShadowRendering)
		{
			if(shadow)
			{
				if(engine.Options.renderMeshShadow) RenderShadow();
				return;
			}
		}

		//		RenderShadow(); return;

		Material temp = engine.Device.Material;
		engine.Device.Material = material;
		mesh.DrawSubset(0);
		engine.Device.Material = temp;
	}

	@Override
	public void dispose()
	{
		engine.RemoveFromDisposeList(mesh);
		mesh.Dispose();
	}

	@Override
	public void RenderShadow()
	{
		Matrix temp = engine.Device.Transform.World;
		Matrix temp2 = Matrix.Identity;

		Vector3f light = engine.LightPosition;
		Vector3f b = Vector3.TransformCoordinate(
				new Vector3f(center.X, center.Y, 0f),
				engine.Device.Transform.World);
		Vector3f a = Vector3.TransformCoordinate(
				new Vector3f(center.X, center.Y, -extrusion),
				engine.Device.Transform.World);
		float l = (light - a).Length();
		float d = l / (20f + l);
		float z1 = a.Z, z2 = b.Z;

		temp2.M31 = (d - 1f) * light.X / (z2 - z1);
		temp2.M32 = (d - 1f) * light.Y / (z2 - z1);
		temp2.M33 = (d - 1f) * light.Z / (z2 - z1);
		temp2.M34 = (d - 1f) / (z2 - z1);
		temp2.M41 = - z1 * (d - 1f) * light.X / (z2 - z1);
		temp2.M42 = - z1 * (d - 1f) * light.Y / (z2 - z1);
		temp2.M43 = - z1 * (d - 1f) * light.Z / (z2 - z1);
		temp2.M44 = 1 - z1 * (d - 1f) / (z2 - z1);
		engine.Device.Transform.World = temp * temp2;


		engine.Device.RenderState.CullMode = Cull.CounterClockwise;
		//		Device.RenderState.CullMode = Cull.CounterClockwise;
		engine.Device.RenderState.StencilPass = StencilOperation.Increment;
		mesh.DrawSubset(0);

		engine.Device.RenderState.CullMode = Cull.Clockwise;
		//		Device.RenderState.CullMode = Cull.Clockwise;
		engine.Device.RenderState.StencilPass = StencilOperation.Decrement;
		mesh.DrawSubset(0);

		engine.Device.RenderState.CullMode = Cull.CounterClockwise;
		engine.Device.Transform.World = temp;
	}

}