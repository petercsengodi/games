package hu.csega.superstition.game.menu;

public class MainMenu implements IModel
{

	protected Engine engine;
	private ModelParams param;
	private IMenu menu, main;
	protected int actual;
	protected static double Angle_max = Math.PI / 5.0;
	protected static double Angle_min = Math.PI / (-5.0);
	protected Vector3 origo = new Vector3(0f, 0f, 0f);
	protected double _alfa = 0.0, _beta = Math.PI / 16.0, _gamma = -Math.PI / 16.0;
	protected double d_alfa = 0.003, d_beta = 0.002, d_gamma = 0.004;
	protected Vector3 standard_position = new Vector3(0f, 0f, -12f);
	protected Vector3 position, light_pos;

	protected Light light;
	protected Primitive triangle1, triangle2;
	private Frame frame;
	protected Vector3 correction;
	protected IMovingLight[] lights;

	protected int item = 0;

	// plus animation
	protected Animation spider;
	private SpiderAgent[] agents;

	public MainMenu(Engine engine)
	{
		this.engine = engine;
		param = new ModelParams();
		param.engine = engine;
		param.menu = this;
		param.game_menu = false;
		param.game_model = null;
		//		param.texts = new Texts(engine);
		lights = new TranslateLight[7];
	}

	public MainMenu(Engine engine, boolean game_menu, Model.Model game_model)
	{
		this.engine = engine;
		param = new ModelParams();
		param.engine = engine;
		param.menu = this;
		param.game_menu = game_menu;
		param.game_model = game_model;
		//		param.texts = new Texts(engine);
		lights = new TranslateLight[7];
	}

	public void Initialize(Engine _engine)
	{
		param.slider = new Slider(_engine);
		param.onoff = new OnOff(_engine);
		param.filemenu = new FileMenu(param);

		main = menu = new Main(param);
		light_pos = new Vector3(0f, 7.5f, -20f);
		//			light = engine.GetDirectedLight(Color.FromArgb(128, 128, 255), -light_pos);
		light = engine.GetDirectedLight(Color.FromArgb(192,192,192), -light_pos);
		correction = new Vector3(MenuHelpClass.LeftExtr, 0f, 0f);

		triangle1 = engine.Pr_TesselatedTriangle(
				new Vector3(-6f, -6f, 3f),
				new Vector3(-6f,  6f, 3f),
				new Vector3( 6f,  6f, 3f),
				@"..\textures\menu_textures\web2.bmp");
		triangle1.Shadow = false;
		triangle1.NotEffectedByLight = true;

		triangle2 = engine.Pr_TesselatedTriangle(
				new Vector3(  6f,   6f, 3f),
				new Vector3(  6f,  -6f, 3f),
				new Vector3( -6f,  -6f, 3f),
				@"..\textures\menu_textures\web2.bmp");
		triangle2.Shadow = false;
		triangle2.NotEffectedByLight = true;

		menu = new Main(param);
		frame = new Frame(param);

		for(int i = 0; i < lights.Length; i++)
		{
			//				lights[i] = new CircleLight(_engine, Color.FromArgb(
			//					(int)(StaticRandomLibrary.DoubleValue() * 255),
			//					(int)(StaticRandomLibrary.DoubleValue() * 255),
			//					(int)(StaticRandomLibrary.DoubleValue() * 255)),
			//					new Vector3(0f, 0f, 0f),
			//					StaticRandomLibrary.DoubleValue() * 2.0 + 2.0,
			//					StaticRandomLibrary.DoubleValue() * 2.0 + 2.0,
			//					StaticRandomLibrary.DoubleValue() * 2.0 * Math.PI,
			//					StaticRandomLibrary.DoubleValue() * 2.0 * Math.PI);

			lights[i] = new TranslateLight(_engine, Color.FromArgb(
					(int)(StaticRandomLibrary.DoubleValue() * 255),
					(int)(StaticRandomLibrary.DoubleValue() * 255),
					(int)(StaticRandomLibrary.DoubleValue() * 255)),
					new Vector3(0f, 0f, -1f),
					StaticRandomLibrary.DoubleValue() * 2.0 + 2.0,
					StaticRandomLibrary.DoubleValue() * 1.0 + 0.5,
					StaticRandomLibrary.DoubleValue() * 2.0 * Math.PI,
					StaticRandomLibrary.DoubleValue() * 2.0 * Math.PI);
		}

		// plus animation
		spider = engine.GetAnimation("spider_running");
		agents = new SpiderAgent[25];
		for(int i = 0; i < agents.Length; i++)
		{
			agents[i] = new SpiderAgent(spider,
					new Vector3(0f, 0f, -1f),
					new Vector3(-5.5f, -5.5f, 3f),
					new Vector3(5.5f, 5.5f, 3f));
		}

		Period();
	}

	public void Render()
	{
		engine.LightPosition = light_pos;

		for(int i = 0; i < lights.Length; i++)
		{
			lights[i].Activate();
		}

		menu.RenderElements();

		for(int i = 0; i < lights.Length; i++)
		{
			lights[i].DeActivate();
		}

		if(engine.IsLighted) light.Activate();

		triangle1.Render();
		triangle2.Render();

		// plus animation
		foreach(SpiderAgent agent in agents)
		{
			agent.Render();
		}

		frame.Render(menu.getMenuElements()[item].Translation + correction);

		if(engine.IsLighted) light.DeActivate();
	}

	public Microsoft.DirectX.Vector3 GetViewPosition()
	{
		return position;
	}

	public Microsoft.DirectX.Vector3 GetViewDirection()
	{
		return - position;
	}


	public Microsoft.DirectX.Vector3 GetVUp()
	{
		return new Vector3(0f, 1f, 0f);
	}

	public void OnKeyDown(int key)
	{
		if(key == 0xC8) if(item > 0)
		{ // on key up
			menu.setLastIndex(--item);
		}

		if(key == 0xD0) if(item < menu.getMenuElements().Length-1)
		{ // on key down
			menu.setLastIndex(++item);
		}

		if(key == 0x01)
		{
			IMenu temp = menu.DoEscape();
			if((temp != null) && (temp != menu))
			{
				menu = temp;
				item = menu.getLastIndex();
			}
		}

		if(key == 0x1C)
		{
			IMenu temp = menu.getMenuElements()[item].DoItem();
			if((temp != null) && (temp != menu))
			{
				menu = temp;
				item = menu.getLastIndex();
			}
		}

	}

	public void OnKeyUp(int key)
	{
		// TODO:  Add Game_Menu.OnKeyUp implementation
	}

	public void OnButtonDown(int button)
	{
		// TODO:  Add Game_Menu.OnButtonDown implementation
	}

	public void OnButtonUp(int button)
	{
		// TODO:  Add Game_Menu.OnButtonUp implementation
	}

	public void OnMovement(int x, int y)
	{
		// TODO:  Add Game_Menu.OnMovement implementation
	}

	public Color GetAmbient()
	{
		return Color.FromArgb(64, 64, 64);
	}

	public void Dispose()
	{
		frame.Dispose();
		main.Dispose();
	}

	public void Period()
	{
		if((_alfa > Angle_max) || (_alfa < Angle_min)) d_alfa = -d_alfa;
		_alfa += d_alfa;
		if((_beta > Angle_max) || (_beta < Angle_min)) d_beta = -d_beta;
		_beta += d_beta;
		if((_gamma > Angle_max) || (_gamma < Angle_min)) d_gamma = -d_gamma;
		_gamma += d_gamma;

		position = standard_position;
		position.X = (float)(position.X * Math.Cos(_alfa) + position.Y * Math.Sin(_alfa));
		position.Y = (float)(position.Y * Math.Cos(_alfa) - position.X * Math.Sin(_alfa));
		position.X = (float)(position.X * Math.Cos(_beta) + position.Z * Math.Sin(_beta));
		position.Z = (float)(position.Z * Math.Cos(_beta) - position.X * Math.Sin(_beta));
		position.Z = (float)(position.Z * Math.Cos(_gamma) + position.Y * Math.Sin(_gamma));
		position.Y = (float)(position.Y * Math.Cos(_gamma) - position.Z * Math.Sin(_gamma));

		for(int i = 0; i < lights.Length; i++)
		{
			lights[i].DoPeriod();
		}

		// plus animation
		foreach(SpiderAgent agent in agents)
		{
			agent.Period();
		}
	}

}