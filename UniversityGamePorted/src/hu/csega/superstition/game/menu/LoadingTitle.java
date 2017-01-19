package hu.csega.superstition.game.menu;

public class LoadingTitle implements IModel
{
	protected double angle = 0.0;
	protected double speed = 0.03;
	protected double limit = Math.PI * 2.0;
	protected Element text;
	protected Engine engine;
	protected Light light;

	#region IModel Members

	public void Initialize(Engine _engine)
	{
		engine = _engine;
		Font font = new Font("Arial", MenuHelpClass.TextSize * 2);
		text = engine.GetTextMesh(font, "Loading...",
				MenuHelpClass.TextBend, MenuHelpClass.TextExtr);
		text.Shadow = false;
		light = engine.GetDirectedLight(Color.LightBlue, new Vector3(4f, 4f, 8f));
	}

	public void Render()
	{
		light.Activate();
		Matrix m =
				Matrix.Translation(new Vector3(1f, 0f, 0f) - (text as MeshText).Center)*
				Matrix.RotationX(0.3f) *
				Matrix.RotationZ(0.3f) *
				Matrix.RotationY(-(float)angle);
		engine.Device.Transform.World = m;
		text.Render();
		engine.Device.Transform.World = Matrix.Identity;
		light.DeActivate();
	}

	public Vector3 GetViewPosition()
	{
		return new Vector3(0f, 0f, -8f);
	}

	public Vector3 GetViewDirection()
	{
		return new Vector3(0f, 0f, 1f);
	}

	public Vector3 GetVUp()
	{
		return new Vector3(0f, 1f, 0f);
	}

	public void OnKeyDown(int key)
	{
	}

	public void OnKeyUp(int key)
	{
	}

	public void OnButtonDown(int button)
	{
	}

	public void OnButtonUp(int button)
	{
	}

	public void OnMovement(int x, int y)
	{
	}

	public Color GetAmbient()
	{
		return Color.Black;
	}

	#endregion

	#region IPeriod Members

	public void Period()
	{
		angle += speed;
		if(angle > limit) angle -= limit;
	}

	#endregion

	#region IDisposable Members

	public void Dispose()
	{
		text.Dispose();
	}

	#endregion

}