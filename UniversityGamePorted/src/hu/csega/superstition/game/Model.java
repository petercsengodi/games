package hu.csega.superstition.game;

public class Model : IModel, IPeriod
{
	protected Engine engine;
	private PlayerObject player;
	private TwoWayLinkedGraph Map;
	private ArrayList game_elements, to_remove;
	protected ArrayList list;
	private ArrayList start_places;
	protected EndOfGame end_of_game;

	private object trigger;
	public object Trigger
	{
		get { return trigger; }
		set { trigger = value; }
	}

	public ArrayList GameElements
	{
		get{ return game_elements; }
	}

	public ArrayList GEToRemove
	{
		get{ return to_remove; }
	}

	public Model()
	{
		StaticRandomLibrary.Initialize();
		game_elements = new ArrayList();
		to_remove = new ArrayList();
		start_places = new ArrayList();
		trigger = null;
	}

	public void Build(object theme)
	{
		((Room) theme).Build(engine);
	}

	public void Render_Object(object theme)
	{
		((IRenderObject) theme).Render();
	}

	public void BuildLink(object theme)
	{
		((Entrance) theme).Build(engine);
	}

	public Color GetAmbient()
	{
		return Color.FromArgb(5, 5, 5);
	}

	public void SetDataModel(GameObjectData data)
	{
		ModelData d = data as ModelData;
		Map = new TwoWayLinkedGraph();
		player = new PlayerObject(d.player);
		player.Model = this;
		Room[] rooms = new Room[d.rooms.Length];
		for(int i = 0; i < d.rooms.Length; i++)
		{
			rooms[i] = new Room(d.rooms[i]);
			Map.AddNode(rooms[i]);
		}
		for(int i = 0; i < d.rooms.Length; i++)
		{
			rooms[i].ExtractRoomSightInformation(d.rooms[i], Map);
		}
		for(int i = 0; i < d.entrances.Length; i++)
		{
			new Entrance(d.entrances[i], Map);
		}
		Map.DoForAllNodes(new TWLFunc(IdentifyCurrentRoom));

		foreach(GameObjectData od in d.game_elements)
		{
			IGameElement element = (IGameElement)od.create();
			element.SetModel(this);
			game_elements.Add(element);
		}

		foreach(GameObjectData sp in d.start_places)
		{
			start_places.Add(sp.create());
		}

//		game_elements = new ArrayList();
//		to_remove = new ArrayList();
	}

	public GameObjectData GetDataModel()
	{
		ModelData ret = new ModelData();
		ret.player = player.getData();
		list = new ArrayList();

		Map.DoForAllNodes(new TWLFunc(LoadIntoList));
		ret.rooms = new GameObjectData[list.Count];
		for(int i = 0; i < list.Count; i++)
		{
			ret.rooms[i] = list[i] as GameObjectData;
		}
		list.Clear();

		Map.DoForAllLinks(new TWLFunc(LoadIntoList));
		ret.entrances = new GameObjectData[list.Count];
		for(int i = 0; i < list.Count; i++)
		{
			ret.entrances[i] = list[i] as GameObjectData;
		}

		ret.game_elements = new GameObjectData[game_elements.Count];
		for(int i = 0; i < game_elements.Count; i++)
		{
			ret.game_elements[i] = (game_elements[i] as IGameObject).getData();
		}

		ret.start_places = new GameObjectData[start_places.Count];
		for(int i = 0; i < start_places.Count; i++)
		{
			ret.start_places[i] = (start_places[i] as IGameObject).getData();
		}

		return ret;
	}

	public void LoadIntoList(object theme)
	{
		IGameObject obj = theme as IGameObject;
		list.Add(obj.getData());
	}

	public void DenseMap()
	{
		DenseMaze maze = new DenseMaze(10, 10);
		player = new PlayerObject(new Vector3(0f, 0f, 0f),
			new Vector3(-0.25f, -0.75f, -0.25f),
			new Vector3(0.25f, 0.25f, 0.25f));
		player.Model = this;
		Map = maze.Generate();

		start_places = maze.getStartPlaces();
		int rand = StaticRandomLibrary.IntValue(
			start_places.Count);
		player.position = (start_places[rand]
			as StartPlace).Position;
		Map.DoForAllNodes(new TWLFunc(IdentifyCurrentRoom));
	}

	public void LoadXmlMap(string filename)
	{
//		game_elements = new ArrayList();
//		to_remove = new ArrayList();

		StructedMaze generator; // !!!
		player = new PlayerObject(new Vector3(4f, 0f, 4f),
			new Vector3(-0.25f, -0.5f, -0.25f), new Vector3(0.25f, 0.75f, 0.25f));
		player.Model = this;

		#region Load Story from an Xml File

		Room actual; String tmp; XmlNode tmp_node;
		ArrayList Rooms = new ArrayList(), Texts = new ArrayList();

		XmlDocument story = new XmlDocument();
		story.Load(filename);
		XmlNode root = story.DocumentElement;

		// Creates a big enough level
		int count = root.ChildNodes.Count * 9;
		int side = (int)(Math.Ceiling(Math.Sqrt(count))); // !!!
		generator = new StructedMaze(side, side); // !!!

		ArrayList weapon_rooms = new ArrayList();

		foreach(XmlNode node in root.ChildNodes)
		{
			Texts.Add(node.Attributes.GetNamedItem("Text").Value);
			Rooms.Add(actual = generator.SelectRandomRoom());

			tmp = node.Attributes.GetNamedItem("Special").Value;
			if(tmp.CompareTo("Start") == 0)
			{
				generator.addStartRoom(actual);
			}
			else if(tmp.CompareTo("Finish") == 0)
			{
				FinishPlace fp = new FinishPlace(
					actual.CenterOnFloor, 2f);
				fp.SetModel(this);
				actual.AddObject(fp);
			}


			tmp = node.Attributes.GetNamedItem("Texture").Value;
			if(tmp.CompareTo("") != 0) actual.Wall_face = tmp;

			tmp_node = node.Attributes.GetNamedItem("Floor_Texture");
			if((tmp_node != null) && (tmp_node.Value.CompareTo("") != 0))
				actual.Floor_face = tmp;

			tmp = node.Attributes.GetNamedItem("Element").Value;

			if(tmp.CompareTo("") != 0)
			{
				Symbol symbol;
				symbol = new Symbol(actual.CenterOnFloor,
					tmp, Color.FromArgb(0, 0, 255), 4f);
				actual.AddObject(symbol);
			}

			tmp = node.Attributes.GetNamedItem("Weapon").Value;
			if(tmp.CompareTo("Gun") == 0)
			{
				Weapon weapon = new Weapon(
					actual.CenterOnFloor,
					WeaponType.Gun);
				weapon_rooms.Add(actual);
				weapon.Status = WeaponStatus.Ground;
				weapon.SetModel(this);
				game_elements.Add(weapon);
			}

		}

		int i = 0, j = 0, c; string tmp1, tmp2;
		foreach(XmlNode node in root.ChildNodes)
		{
			tmp1 = node.Attributes.GetNamedItem("Text").Value;
			for(c = 0; c < Texts.Count; c++)
				if(tmp1.CompareTo(Texts[c] as string) == 0)
					i = c;

			foreach(XmlNode link in node.ChildNodes)
			{
				tmp2 = link.Attributes.GetNamedItem("ref").Value;
				for(c = 0; c < Texts.Count; c++)
					if(tmp2.CompareTo(Texts[c] as string) == 0)
						j = c;

				if(i < j) generator.Connect((Rooms[i] as Room), (Rooms[j] as Room));
			}
		}

		#endregion

//		generator.MakeDense(); // !!!
		Map = generator.Generate();
		generator.print();

		for(int idx = 0; idx < weapon_rooms.Count; idx++)
		{
			(game_elements[idx] as Clipper).Position =
				(weapon_rooms[idx] as Room).CenterOnFloor;
		}

		start_places = generator.getStartPlaces();
		int rand = StaticRandomLibrary.IntValue(
			start_places.Count);
		player.position = (start_places[rand]
			as StartPlace).Position;
	}

	#region IModel Members

	public void Initialize(Engine _engine)
	{
		this.engine = _engine;
		player.Build(engine);
		Map.DoForAllNodes(new TWLFunc(Build));
		Map.DoForAllLinks(new TWLFunc(BuildLink));

		foreach(IGameObject g in game_elements)
		{
			g.Build(engine);
		}

		engine.State.trigger(null);
		end_of_game = new EndOfGame(false);
		ModelOptions.view = ViewStatus.Normal;
	}

	public void Render()
	{
		player.Render();
	}

	public Vector3 GetViewPosition()
	{
		Vector3 ret = player.position;

		switch(ModelOptions.view)
		{
			case ViewStatus.Map:
				ret.Y += 20f;
				break;

			case ViewStatus.Side:
				ret.X += 2f;
				ret.Y += 2f;
				ret.Z += 2f;
				break;

			default:
				if(player.camera_pos == null)
				{
					ret.X += 2f;
					ret.Y += 2f;
					ret.Z += 2f;
				} else ret = player.camera_pos.position;
				break;
		}

		return ret;
	}

	public Vector3 GetViewDirection()
	{
		Vector3 ret = player.getDirection();

		switch(ModelOptions.view)
		{
			case ViewStatus.Map:
				ret.X = 0f;
				ret.Y = -1f;
				ret.Z = 0f;
				break;

			case ViewStatus.Side:
				ret.X = -2f;
				ret.Y = -2f;
				ret.Z = -2f;
				break;

			default:
				if((player.camera_dir == null) || (player.camera_pos == null))
				{
					ret.X = -2f;
					ret.Y = -2f;
					ret.Z = -2f;
				}
//				else ret = player.camera_dir.position - player.camera_pos.position;
				else ret = player.getDirection();
				break;
		}

		return ret;
	}

	public Vector3 GetVUp()
	{
		Vector3 ret = new Vector3(0f, 1f, 0f);

		switch(ModelOptions.view)
		{
			case ViewStatus.Map:
				ret = player.getDirection();
				ret.Normalize();
				break;

			case ViewStatus.Side:
				ret.X = 0f;
				ret.Y = 1f;
				ret.Z = 0f;
				break;

			default:
				if((player.camera_up == null) || (player.camera_pos == null))
				{
					ret.X = -2f;
					ret.Y = 2f;
					ret.Z = -2f;
				}
				else ret = player.camera_up.position - player.camera_pos.position;
				break;
		}

		return ret;
	}

	public void OnKeyDown(int key)
	{
		if(key == 0x10)
		{
			engine.State.trigger(null);
		}

		if(key == 0x01)
		{
			engine.State.trigger(null);
		}

		else if(key < 0x10) player._SwitchWeapon(key-2);

		if(end_of_game.finished)
		{
			if(key == 0x1C)
			{
//				engine.State.trigger(Menu.MainMenuSelection.QUIT_GAME);
//				ModelOptions.view = ViewStatus.Side;
				engine.State.trigger(null);
			}

			return;
		}

		if(key == 0xC8) player.GoForward = true;
		if(key == 0xD0) player.GoBack = true;
		if(key == 0xCB) player.GoLeft = true;
		if(key == 0xCD) player.GoRight = true;
		if(key == 0x36) if(player.OnGround) player.OnJump = true;
		if(key == 0x1F) engine.Options.renderShadow = !engine.Options.renderShadow;
		if(key == 0x20) engine.Options.renderMeshShadow = !engine.Options.renderMeshShadow;
		if(key == 0x31) player._Stand();

		if(key == 0x32)
		{
			ModelOptions.view++;
			if(ModelOptions.view > ViewStatus.Side)
				ModelOptions.view = ViewStatus.Normal;
			if(end_of_game.finished) ModelOptions.view = ViewStatus.Side;
		}
	}

	public void OnKeyUp(int key)
	{
		if(key == 0xC8) player.GoForward = false;
		if(key == 0xD0) player.GoBack = false;
		if(key == 0xCB) player.GoLeft = false;
		if(key == 0xCD) player.GoRight = false;
	}

	public void OnButtonDown(int button)
	{
		if(end_of_game.finished)
		{
			return;
		}

		if(button == 0) player._OnceShot(true);
		if(button == 1) player._OnceShot(false);
	}

	public void OnButtonUp(int button)
	{
	}

	public void OnMovement(int x, int y)
	{
		if(end_of_game.finished)
		{
			return;
		}

		if(x < 0) player._TurnLeft();
		if(x > 0) player._TurnRight();
		if(y < 0) player._TurnUp();
		if(y > 0) player._TurnDown();
	}

	public void Dispose()
	{
		if(Map != null)
		{
			Map.DoForAllNodes(new TWLFunc(Dispose));
			Map.DoForAllLinks(new TWLFunc(Dispose));
		}

		if(player!= null) player.Dispose();
		player = null;
		Map = null;

		game_elements.Clear();
		to_remove.Clear();

		Library.Animations().Clear(); // TODO: allowed??
		Library.Meshes().Clear(); // TODO: allowed?
	}

	public void Dispose(object theme)
	{
		(theme as IDisposable).Dispose();
	}

	#endregion

	#region IPeriod Members

	public void Period()
	{
		// Clipping
		Map.DoForAllNodes(new TWLFunc(player.ClipRooms));
		Map.DoForAllLinks(new TWLFunc(player.ClipRooms));

		to_remove.Clear();
		foreach(IGameElement c in game_elements)
		{
			c.Clip(player);
		}

		foreach(object o in to_remove)
		{
			game_elements.Remove(o);
		}

		// Player changes
		if(player.CurrentRoom == null)
		{
			Map.DoForAllNodes(new TWLFunc(IdentifyCurrentRoom));
		}

		player.Period();
		foreach(IPeriod p in game_elements)
		{
			p.Period();
		}

		player.CurrentRoom.FollowPlayer(player);

		Map.DoForAllNodes(new TWLFunc(RoomPeriod));
		Map.DoForAllLinks(new TWLFunc(RoomPeriod));

		if(trigger != null)
		{
			engine.State.trigger(trigger);
		}
	}

	#endregion

	public void RoomPeriod(object theme)
	{
		(theme as IPeriod).Period();
	}

	public void IdentifyCurrentRoom(object theme)
	{
		((Room) theme).IdentifyCurrent(player);
	}

	public void StartNetworkPlay(Network.PlayClient play_client, int userID)
	{
		if(player == null) return;
		player.StartNetworkPlay(play_client, userID);
	}

	public void FinishGame(EndOfGame end_of_game)
	{
		this.end_of_game = end_of_game;
		ModelOptions.view = ViewStatus.Side;
	}
}

