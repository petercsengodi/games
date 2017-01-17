using System;
using System.IO;
using System.Drawing;
using Microsoft.DirectX;
using System.Collections;
using Mathematics;

using GameLib;
using System.Net;

namespace Menu
{
	using Engine;

	public enum MainMenuSelection 
	{
		FAILURE = -1, 
		NEW_GAME = 1, LOAD_MAP = 2, NETWORK_PLAY = 3,
		LOAD_GAME = 4, SAVE_GAME = 5,
		JOIN_HOST,
		PUBLISH_HOST,

		DENSE_MAP,

		QUIT_GAME, QUIT = 255 };

	struct ModelParams
	{
		public Engine engine;
		public IModel menu;
		public Slider slider;
		public OnOff onoff;
		public FileMenu filemenu;
		public bool game_menu;
		public Model.Model game_model;
//		public Texts texts;
	}

	struct TriggerParams
	{
		public MainMenuSelection command;
		public string StringParameter;
		public object ObjectParameter;

		public TriggerParams(MainMenuSelection command)
		{
			this.command = command;
			this.StringParameter = null;
			this.ObjectParameter = null;
		}

		public TriggerParams(MainMenuSelection command, string StringParameter)
		{
			this.command = command;
			this.StringParameter = StringParameter;
			this.ObjectParameter = null;
		}

		public TriggerParams(MainMenuSelection command, 
			string StringParameter, object ObjectParameter)
		{
			this.command = command;
			this.StringParameter = StringParameter;
			this.ObjectParameter = ObjectParameter;
		}
	};

	public class Slider
	{
		protected Engine engine;
		public static int LOW = 0;
		public static int MEDIUM = 1;
		public static int HIGH = 2;
		protected Element[] mesh;
		public static Vector3[] DEFAULT = new Vector3[]{
														   new Vector3(6f, 0f, 0f),
														   new Vector3(4.5f, 0f, 0f),
														   new Vector3(5.8f, 0f, 0f)
													   };

		public Slider(Engine engine)
		{
			this.engine = engine;
			mesh = new Element[3];
			Font font = new Font("Comics Sans MS", MenuHelpClass.TextSize);
			mesh[0] = engine.GetTextMesh(font, "Low", MenuHelpClass.TextBend, MenuHelpClass.TextExtr);
			mesh[1] = engine.GetTextMesh(font, "Medium", MenuHelpClass.TextBend, MenuHelpClass.TextExtr);
			mesh[2] = engine.GetTextMesh(font, "High", MenuHelpClass.TextBend, MenuHelpClass.TextExtr);
		}

		public void Render(Vector3 translation, int val)
		{
			mesh[val].Render(translation);
		}
	}

	public class OnOff
	{
		protected Engine engine;
		protected Element meshOn, meshOff;
		public static Vector3 DEFAULT = new Vector3(6.5f, 0f, 0f);

		public OnOff(Engine engine)
		{
			this.engine = engine;
			Font font = new Font("Comics Sans MS", MenuHelpClass.TextSize);
			meshOn = engine.GetTextMesh(font, "On", MenuHelpClass.TextBend, MenuHelpClass.TextExtr);
			meshOff = engine.GetTextMesh(font, "Off", MenuHelpClass.TextBend, MenuHelpClass.TextExtr);
		}

		public void Render(Vector3 translation, bool val)
		{
			Element drawable = val?meshOn:meshOff;
			drawable.Render(translation);
		}
	}

	interface IFileParent : IMenu
	{
		IMenu DoChildrenItem(string filename);
	}

	class UpArrow : MenuElement
	{
		protected FileMenu menu;

		public UpArrow(ModelParams param, FileMenu menu) : base(param)
		{
			this.menu = menu;
		}

		public override IMenu DoItem()
		{
			menu.ScrollUp();
			return null;
		}

		public override string getText()
		{
			return "< UP >";
		}

	}

	class DownArrow : MenuElement
	{
		protected FileMenu menu;

		public DownArrow(ModelParams param, FileMenu menu) : base(param)
		{
			this.menu = menu;
		}

		public override IMenu DoItem()
		{
			menu.ScrollDown();
			return null;
		}

		public override string getText()
		{
			return "< DOWN >";
		}

	}

	class Dots : MenuElement
	{
		protected FileMenu menu;
		protected IFileParent parent;
		protected bool show;

		public bool Show
		{
			get { return show; }
			set { show = value; }
		}

		public Dots(ModelParams param, FileMenu menu) : base(param)
		{
			this.menu = menu;
			show = true;
			this.parent = parent;
		}

		public void setParent(IFileParent parent)
		{
			this.parent = parent;
		}

		public override IMenu DoItem()
		{
			return parent.DoChildrenItem(null);
		}

		public override string getText()
		{
			return "...";
		}

		public override void Render()
		{
			if(show) base.Render();
		}

	}

	class FileMenu : IMenu
	{
		protected ModelParams param;
		protected int lastIndex;
		protected IMenu parent;
		protected IFileParent fileParent;
		protected ArrayList files;
		protected MenuElement[] elements;
		protected MenuHelpClass menuhelp;
		protected MenuElement up, down;
		protected Dots[] dots;
		protected int count, scroll;

		public FileMenu(ModelParams param)
		{
			this.param = param;
			this.parent = null;
			elements = new MenuElement[6];
			files = new ArrayList();
			lastIndex = 1;
			count = 0;
			scroll = 0;

			dots = new Dots[elements.Length - 2];
			elements[0] = (up = new UpArrow(param, this));
			for(int i = 0; i < elements.Length - 2; i++)
			{
				elements[i + 1] = (dots[i] = new Dots(param, this));
			}
			elements[5] = (down = new DownArrow(param, this));
			menuhelp = new MenuHelpClass(param, elements);
		}

		#region IMenu Members

		public MenuElement[] getMenuElements()
		{
			return elements;
		}

		public IMenu getParent()
		{
			return parent;
		}

		public void RenderElements()
		{
			for(int i = 0; i < 6; i++)
			{
				elements[i].Render();
			}
		}

		public IMenu DoEscape()
		{
			return parent.getParent();
		}

		public void setLastIndex(int idx)
		{
			lastIndex = idx;
		}

		public int getLastIndex()
		{
			return lastIndex;
		}

		#endregion

		#region IDisposable Members

		public void Dispose()
		{
			foreach(object o in files)
			{
				(o as FileMenuElement).Dispose();
			}
			
			up.Dispose();
			down.Dispose();
		}

		#endregion

		public void Refresh(string dir, string filter, int count, IMenu parent)
		{
			this.parent = parent;
			fileParent = parent as IFileParent;

			for(int i = 0; i < dots.Length; i++)
			{
				dots[i].setParent(fileParent);
			}

			ArrayList temp = new ArrayList();
			DirectoryInfo info = new DirectoryInfo(dir);
			FileInfo[] infos;
			this.count = count;

			if(info.Exists)
			{
				string name;
				Object obj;
				infos = info.GetFiles(filter);
				for(int i = 0; i < infos.Length; i++)
				{
					name = infos[i].Name;
					obj = null;
					foreach(object o in files)
					{
						if((o as FileMenuElement).File == name)
						{
							obj = o;
						}
					}

					if(obj == null)
					{
						temp.Add(new FileMenuElement(param, fileParent, name));
					} 
					else 
					{
						temp.Add(obj);
						files.Remove(obj);
						(obj as FileMenuElement).Parent = fileParent;
					}
				}
			}
			
			foreach(object o in files)
			{
				(o as FileMenuElement).Dispose();
			}

			files = temp;

			if((count == 0) && (files.Count < scroll + 4))
			{
				scroll = 0;
			}

			for(int i = 0; (i < 4) && (i < files.Count); i++)
			{
				elements[i+1] = files[i] as MenuElement;
				elements[i+1].Translation = dots[i].Translation;
			}

			for(int i = files.Count; (i < 4) && (i < count - scroll); i++)
			{
				elements[i+1] = dots[i];
			}

			if(count == 0) for(int i = 0; i < 4; i++) dots[i].Show = false;
			else for(int i = 0; i < 4; i++) dots[i].Show = true;
		}

		public void ScrollUp()
		{
			if(count == 0) 
			{
				if(scroll > 0)  scroll --;
				for(int i = 0; i < 4; i++)
				{
					elements[i+1] = (MenuElement)files[i + scroll];
					elements[i+1].Translation = dots[i].Translation;
				}
			}
			else 
			{
				if(scroll > 0)  scroll --;
				int i = 0;
				for(; (i < 4) && (i + scroll < files.Count); i++)
				{
					elements[i+1] = (MenuElement)files[i + scroll];
					elements[i+1].Translation = dots[i].Translation;
				}

				for(; i < 4; i++)
				{
					elements[i+1] = dots[i];
				}
			}
		}

		public void ScrollDown()
		{
			if(count == 0) 
			{
				if(scroll + 4 < files.Count)  scroll ++;
				for(int i = 0; i < 4; i++)
				{
					elements[i+1] = (MenuElement)files[i + scroll];
					elements[i+1].Translation = dots[i].Translation;
				}
			}
			else 
			{
				if(scroll + 4 < count)  scroll ++;
				int i = 0;
				for(; (i < 4) && (i + scroll < files.Count); i++)
				{
					elements[i+1] = (MenuElement)files[i + scroll];
					elements[i+1].Translation = dots[i].Translation;
				}

				for(; i < 4; i++)
				{
					elements[i+1] = dots[i];
				}
			}
		}

		public string GetIndexString(int idx)
		{
			return (files[idx] as FileMenuElement).File;
		}
	}

	class FileMenuElement : MenuElement
	{
		protected string file;
		protected IFileParent parent;
		protected Element filename;
		protected bool disposed = false;

		public IFileParent Parent
		{
			get { return parent; }
			set { parent = value; }
		}

		public FileMenuElement(ModelParams param, IFileParent parent, string file) : base(param)
		{
			this.file = file;
			this.parent = parent;

			Font font = new Font("Times New Roman", MenuHelpClass.TextSize);
			filename = param.engine.GetTextMesh(font, file, MenuHelpClass.TextBend, MenuHelpClass.TextExtr);
		}

		public string File
		{
			get{ return file; }
		}

		public override IMenu DoItem()
		{
			return parent.DoChildrenItem(file);
		}

		public override string getText()
		{
			return null;
		}

		public override void Dispose()
		{
			disposed = true;
			filename.Dispose();
		}

		public override void Render()
		{
			if(disposed) return;
			filename.Render(translation);
		}
	}

	abstract class MenuElement 
	{
		protected Element text;
		protected ModelParams param;

		protected Vector3 translation;

		public Vector3 Translation
		{
			get
			{
				return translation;
			}
			set
			{
				translation = value;
			}
		}
        
		public MenuElement(ModelParams param)
		{
			this.param = param;
			
			if(getText() != null)
			{
				System.Drawing.Font font = new System.Drawing.Font("Comic Sans MS", MenuHelpClass.TextSize);
				text = param.engine.GetTextMesh(font, getText(), MenuHelpClass.TextBend, MenuHelpClass.TextExtr);
			} 
			else text = null;
			translation = new Vector3(0f, 0f, 0f);
		}

		public MenuElement(ModelParams param, string text)
		{
			this.param = param;
			
			if(text != null)
			{
				System.Drawing.Font font = new System.Drawing.Font("Comic Sans MS", MenuHelpClass.TextSize);
				this.text = param.engine.GetTextMesh(font, text, MenuHelpClass.TextBend, MenuHelpClass.TextExtr);
			} 
			else this.text = null;
			translation = new Vector3(0f, 0f, 0f);
		}

		public abstract string getText();

		public virtual void OnEnter(){}
		public virtual void OnLeft(){}
		public virtual void OnRight(){}

		public virtual void Render()
		{
			if(text != null) text.Render(translation);
		}

		public abstract IMenu DoItem();

		public virtual void Dispose()
		{
			if(text != null) text.Dispose();
		}
		
	} // end of class MenuElement

	interface IMenu : IDisposable
	{
		MenuElement[] getMenuElements();
		IMenu getParent();
		void RenderElements();
		IMenu DoEscape();
		void setLastIndex(int idx);
		int getLastIndex();
	}

	class DenseMaze : MenuElement
	{
		public DenseMaze(ModelParams param) : base(param)
		{
		}

		public override IMenu DoItem()
		{
			param.engine.State.trigger(
				new TriggerParams(MainMenuSelection.DENSE_MAP, null));
			return null;
		}

		public override string getText()
		{
			return "Dense Maze";
		}

	}

	class RandomGame : MenuElement, IMenu
	{
		protected IMenu parent;
		protected MenuElement[] elements;
		protected MenuHelpClass menuhelp;
		protected int lastindex = 0;

		public RandomGame(ModelParams param, IMenu parent) : base(param)
		{
			this.parent = parent;

			elements = new MenuElement[]{
											new DenseMaze(param)
										};

			menuhelp = new MenuHelpClass(param, elements);
		}

		public override string getText()
		{
			return "Random Game";
		}

		public override IMenu DoItem()
		{
			return this;
		}

		#region IMenu Members

		public MenuElement[] getMenuElements()
		{
			return elements;
		}

		public IMenu getParent()
		{
			return parent;
		}

		public void RenderElements()
		{
			menuhelp.RenderElements();
		}

		public IMenu DoEscape()
		{
			return parent;
		}

		public void setLastIndex(int idx)
		{
			lastindex = idx;
		}
		
		public int getLastIndex()
		{
			return lastindex;
		}

		public override void Dispose()
		{
			base.Dispose();
			menuhelp.Dispose();
		}

		#endregion
	}

	class LoadMap : MenuElement, IMenu, IFileParent
	{
		protected IMenu parent;

		public LoadMap(ModelParams param, IMenu parent) : base(param)
		{
			this.parent = parent;
		}

		public override string getText()
		{
			return "Load Map";
		}

		public override IMenu DoItem()
		{
			param.filemenu.Refresh(@"..\maps", "*.xml", 0, this);
			return param.filemenu;
		}

		#region IMenu Members

		public MenuElement[] getMenuElements()
		{
			return null;
		}

		public IMenu getParent()
		{
			return parent;
		}

		public void RenderElements()
		{
			param.filemenu.RenderElements();
		}

		public IMenu DoEscape()
		{
			return parent;
		}

		public void setLastIndex(int idx)
		{
		}

		public int getLastIndex()
		{
			return 0;
		}

		#endregion

		#region IFileParent Members

		public IMenu DoChildrenItem(string filename)
		{
			param.engine.State.trigger(
				new TriggerParams(MainMenuSelection.LOAD_MAP, @"..\maps\" + filename));
			return null;
		}

		#endregion
	}

	class LoadGame : MenuElement, IMenu, IFileParent
	{
		protected IMenu parent;

		public LoadGame(ModelParams param, IMenu parent) : base(param)
		{
			this.parent = parent;
		}

		public override string getText()
		{
			return "Load Game";
		}

		public override IMenu DoItem()
		{
			param.filemenu.Refresh(@"..\saves", "*.save", 15, this);
			return param.filemenu;
		}

		#region IMenu Members

		public MenuElement[] getMenuElements()
		{
			return null;
		}

		public IMenu getParent()
		{
			return parent;
		}

		public void RenderElements()
		{
			param.filemenu.RenderElements();
		}

		public IMenu DoEscape()
		{
			return parent;
		}

		public void setLastIndex(int idx)
		{
		}

		public int getLastIndex()
		{
			return 0;
		}

		#endregion

		#region IFileParent Members

		public IMenu DoChildrenItem(string filename)
		{
			param.engine.State.trigger(
				new TriggerParams(MainMenuSelection.LOAD_GAME, @"..\saves\" + filename));
			return null;
		}

		#endregion
	}

	class SaveGame : MenuElement, IMenu, IFileParent
	{
		protected IMenu parent;

		public SaveGame(ModelParams param, IMenu parent) : base(param)
		{
			this.parent = parent;
		}

		public override string getText()
		{
			return "Save Game";
		}

		public override IMenu DoItem()
		{
			param.filemenu.Refresh(@"..\saves", "*.save", 15, this);
			return param.filemenu;
		}

		#region IMenu Members

		public MenuElement[] getMenuElements()
		{
			return null;
		}

		public IMenu getParent()
		{
			return parent;
		}

		public void RenderElements()
		{
			param.filemenu.RenderElements();
		}

		public IMenu DoEscape()
		{
			return parent;
		}

		public void setLastIndex(int idx)
		{
		}

		public int getLastIndex()
		{
			return 0;
		}

		#endregion

		#region IFileParent Members

		public IMenu DoChildrenItem(string filename)
		{
			int idx = param.filemenu.getLastIndex();
			
			string fn;
			fn = @"..\saves\save" + (idx / 10).ToString() 
				+ (idx % 10).ToString() + ".save";
			param.engine.State.trigger(
				new TriggerParams(MainMenuSelection.SAVE_GAME, fn));
			return null;
		}

		#endregion
	}

	//	class TextEntry : MenuElement
	//	{
	//		protected IMenu menu;
	//		protected string text;
	//
	//		public TextEntry(ModelParams param, IMenu menu, string text)
	//			: base(param, text)
	//		{
	//			this.menu = menu;
	//			this.text = text;
	//		}
	//
	//		public override IMenu DoItem()
	//		{
	//			return menu;
	//		}
	//
	//		public override string getText()
	//		{
	//			return text;
	//		}
	//
	//	}

	class AddressEntry : MenuElement
	{
		protected IMenu parent;
		protected IPAddress address;
		protected Network.NetHost host;
		
		public AddressEntry(ModelParams param, IMenu parent,
			IPAddress address, Network.NetHost host)
			: base(param, address.ToString())
		{
			this.parent = parent;
			this.host = host;
			this.param = param;
			this.address = address;
		}
		
		public override IMenu DoItem()
		{
			Network.NetworkClient nclient = new Network.NetworkClient(
				address, host.Port);
			return new HostMenu(param, parent, nclient, host, address);
		}
		
		public override string getText()
		{
			return address.ToString();
		}
	}

	class AddressList : IMenu
	{
		protected ModelParams param;
		protected IMenu parent;
		protected IPAddress[] list;
		protected Network.NetHost host;
		protected MenuElement[] elements;
		protected MenuHelpClass helpclass;

		public AddressList(ModelParams param, IMenu parent, Network.NetHost host)
		{
			this.param = param;
			this.parent = parent;
			this.host = host;
			this.list = host.GetHost();
			
			this.elements = new MenuElement[list.Length];
			for(int i = 0; i < list.Length; i++)
			{
				elements[i] = new AddressEntry(param, parent, list[i], host);
			}
			
			this.helpclass = new MenuHelpClass(param, elements);
		}

		#region IMenu Members

		public MenuElement[] getMenuElements()
		{
			return elements;
		}

		public IMenu getParent()
		{
			return parent;
		}

		public void RenderElements()
		{
			helpclass.RenderElements();
		}

		public IMenu DoEscape()
		{
			return parent;
		}

		public void setLastIndex(int idx)
		{
		}

		public int getLastIndex()
		{
			return 0;
		}

		#endregion

		#region IDisposable Members

		public void Dispose()
		{
			helpclass.Dispose();
			// TODO:  Add AddressList.Dispose implementation
		}

		#endregion

	}

	class HostEntry : MenuElement
	{
		protected IMenu menu;
		protected HostData data;
		protected Network.NetHost host;
		protected Network.NetworkClient nclient;
		protected bool publish;
		protected IPAddress address;
		
		public HostEntry(ModelParams param, IMenu menu,
			HostData data, Network.NetHost host, 
			IPAddress address, Network.NetworkClient nclient)
			: base(param, "Join Game:" + 
			data.player_count.ToString() + "/" + 
			data.limit.ToString())
		{
			this.menu = menu;
			this.param = param;
			this.data = data;
			this.host = host;
			this.nclient = nclient;
			this.publish = false;
			this.address = address;
		}
		
		public HostEntry(ModelParams param, IMenu menu, 
			HostData data, Network.NetHost host, IPAddress address,
			Network.NetworkClient nclient, bool publish)
			: base(param, "Publish Game")
		{
			this.menu = menu;
			this.param = param;
			this.data = data;
			this.host = host;
			this.nclient = nclient;
			this.publish = true;
			this.address = address;
		}
		
		public HostEntry(ModelParams param)
			: base(param, "< empty >")
		{
			this.menu = menu;
			this.param = param;
			this.data = null;
			this.nclient = null;
			this.publish = false;
		}
		
		public override IMenu DoItem()
		{
			if(nclient == null) return null;

			GameObjectData map = null;

			if(!publish)
			{
				nclient.SendJoinHost(data.ID);
				map = nclient.PollReceive();
			} 
			else 
			{
				nclient.SendPublishHost(param.game_model.GetDataModel());
			}

			GameObjectData user_info = nclient.PollReceive();
			nclient.TcpDisconnect();

			if(publish)	
			{
				object[] parameters = new object[4];
				parameters[0] = host;
				parameters[1] = address;
				parameters[2] = data;
				parameters[3] = user_info;

				param.engine.State.trigger(
					new TriggerParams(
					MainMenuSelection.PUBLISH_HOST,
					"Publish Host",
					parameters));
			}
			else
			{
				object[] parameters = new object[5];
				parameters[0] = host;
				parameters[1] = address;
				parameters[2] = data;
				parameters[3] = user_info;
				parameters[4] = map;
				
				param.engine.State.trigger(
					new TriggerParams(
					MainMenuSelection.JOIN_HOST,
					"Join Host",
					parameters));

			}

			return null;
			
		}
		
		public override string getText()
		{
			return "Join / Publish";
		}
	}

	class HostMenu : IMenu
	{
		protected ModelParams param;
		protected IMenu parent;
		protected Network.NetworkClient nclient;
		protected MenuElement[] elements;
		protected MenuHelpClass menuhelp;
		protected HostList host_list;
		protected Network.NetHost host;
		protected IPAddress address;

		public HostMenu(ModelParams param, IMenu parent, 
			Network.NetworkClient nclient, Network.NetHost host,
			IPAddress address)
		{
			this.host = host;
			this.param = param;
			this.parent = parent;
			this.nclient = nclient;
			this.address = address;

			nclient.TcpConnect();

			nclient.SendHostQuery();
			GameLib.GameObjectData data = nclient.PollReceive();

			if(data.Description.Equals("Host List"))
			{
				host_list = data as HostList;
				elements = new MenuElement[host_list.list.Length];
				for(int i = 0; i < elements.Length; i++)
				{

					if(host_list.list[i].ID == -1)
					{
						if(param.game_menu)	
							elements[i] = new HostEntry(param, parent, 
								host_list.list[i], host, address,
								nclient, true);

						else elements[i] = new HostEntry(param);
					}
					else 
					{
						elements[i] = new HostEntry(param, parent,
							host_list.list[i], host, address,
							nclient);
					}
				}
				
			}

			
			menuhelp = new MenuHelpClass(param, elements);
		}

		#region IMenu Members

		public MenuElement[] getMenuElements()
		{
			return elements;
		}

		public IMenu getParent()
		{
			return parent;
		}

		public void RenderElements()
		{
			menuhelp.RenderElements();
		}

		public IMenu DoEscape()
		{
			nclient.TcpDisconnect();
			return parent;
		}

		public void setLastIndex(int idx)
		{
		}

		public int getLastIndex()
		{
			return 0;
		}

		#endregion

		#region IDisposable Members

		public void Dispose()
		{
			menuhelp.Dispose();
			// TODO:  Add HostEntry.Dispose implementation
		}

		#endregion
	}

	class NetworkPlay : MenuElement, IFileParent
	{
		protected IMenu parent;

		public NetworkPlay(ModelParams param, IMenu parent) : base(param)
		{
			this.parent = parent;
		}
	
		public override IMenu DoItem()
		{
			param.filemenu.Refresh(@"..\network", "*.srv", 0, this);
			return param.filemenu;
		}

		public override string getText()
		{
			return "Network Play";
		}

		#region IFileParent Members

		public IMenu DoChildrenItem(string filename)
		{
			Network.NetHost host = new Network.NetHost("../network/" + filename);
			return new AddressList(param, parent, host);
		}

		#endregion

		#region IMenu Members

		public MenuElement[] getMenuElements()
		{
			return null;
		}

		public IMenu getParent()
		{
			return parent;
		}

		public void RenderElements()
		{
			param.filemenu.RenderElements();
		}

		public IMenu DoEscape()
		{
			return parent;
		}

		public void setLastIndex(int idx)
		{
		}

		public int getLastIndex()
		{
			return 0;
		}

		#endregion

		#region IDisposable Members

		public override void Dispose()
		{
			base.Dispose();
			
			// TODO:  Add JoinHost.Dispose implementation
		}

		#endregion
	}

	class ResumeGame : MenuElement
	{
		public ResumeGame(ModelParams param) : base(param)
		{
		}

		public override string getText()
		{
			return "Resume Game";
		}

		public override IMenu DoItem()
		{
			param.engine.State.trigger(new TriggerParams(MainMenuSelection.FAILURE));
			return null;
		}

	}

	class Quit : MenuElement
	{
		public Quit(ModelParams param) : base(param)
		{
		}

		public override string getText()
		{
			return "Quit";
		}

		public override IMenu DoItem()
		{
			param.engine.State.trigger(new TriggerParams(MainMenuSelection.QUIT));
			return null;
		}

	}
	
	class GameSubMenu : MenuElement, IMenu
	{
		protected IMenu parent;
		protected MenuElement[] elements;
		protected MenuHelpClass menuhelp;
		protected int lastindex = 0;

		public GameSubMenu(ModelParams param, IMenu parent) : base(param)
		{
			this.parent = parent;


			if(param.game_menu)
			{
				elements = new MenuElement[]{
												//											new NewGame(param),
												new LoadMap(param, this),
												new LoadGame(param, this),
												new SaveGame(param, this),
												new RandomGame(param, this)
											};
			}
			else 
			{
				elements = new MenuElement[]{
												//											new NewGame(param),
												new LoadMap(param, this),
												new LoadGame(param, this),
												new RandomGame(param, this)
											};
			}

			menuhelp = new MenuHelpClass(param, elements);
		}

		public override string getText()
		{
			return "Game";
		}

		public override IMenu DoItem()
		{
			return this;
		}

		#region IMenu Members

		public MenuElement[] getMenuElements()
		{
			return elements;
		}

		public IMenu getParent()
		{
			return parent;
		}

		public void RenderElements()
		{
			menuhelp.RenderElements();
		}

		public IMenu DoEscape()
		{
			return parent;
		}

		public void setLastIndex(int idx)
		{
			lastindex = idx;
		}
		
		public int getLastIndex()
		{
			return lastindex;
		}

		public override void Dispose()
		{
			base.Dispose();
			menuhelp.Dispose();
		}

		#endregion
	}

	class Detail : MenuElement
	{
		public Detail(ModelParams param) : base(param)
		{
		}

		public override string getText()
		{
			return "Detail";
		}

		public override void Render()
		{
			int val;
			if(param.engine.Options.detail == EngineOptions.D_LOW)
				val = Slider.LOW;
			else if(param.engine.Options.detail == EngineOptions.D_MID) 
				val = Slider.MEDIUM;
			else val = Slider.HIGH;

			base.Render();
			param.slider.Render(translation + Slider.DEFAULT[val], val);
		}

		public override IMenu DoItem()
		{
			if(param.game_menu) return null;
			if(param.engine.Options.detail == EngineOptions.D_LOW)
				param.engine.Options.detail = EngineOptions.D_MID;
			else if(param.engine.Options.detail == EngineOptions.D_MID) 
				param.engine.Options.detail = EngineOptions.D_HIGH;
			else if (param.engine.Options.detail == EngineOptions.D_HIGH) 
				param.engine.Options.detail = EngineOptions.D_LOW;
			return null;
		}

	}

	class Shadows : MenuElement
	{
		public Shadows(ModelParams param) : base(param)
		{
		}

		public override string getText()
		{
			return "Dynamic Shadows";
		}

		public override IMenu DoItem()
		{
			param.engine.Options.renderShadow = !param.engine.Options.renderShadow;
			return null;
		}

		public override void Render()
		{
			bool val = param.engine.Options.renderShadow;
			base.Render();
			param.onoff.Render(translation + OnOff.DEFAULT, val);
		}

	}

	class MeshShadows : MenuElement
	{
		public MeshShadows(ModelParams param) : base(param)
		{
		}

		public override string getText()
		{
			return "Mesh Shadows";
		}

		public override IMenu DoItem()
		{
			param.engine.Options.renderMeshShadow = !param.engine.Options.renderMeshShadow;
			return null;
		}

		public override void Render()
		{
			bool val = param.engine.Options.renderMeshShadow;
			base.Render();
			param.onoff.Render(translation + OnOff.DEFAULT, val);
		}
	}

	class Options : MenuElement, IMenu
	{
		protected IMenu parent;
		protected MenuElement[] elements;
		protected MenuHelpClass menuhelp;
		protected int lastindex = 0;
		
		public Options(ModelParams param, IMenu parent) : base(param)
		{
			this.parent = parent;
			
			elements = new MenuElement[]{
											new Detail(param),
											new Shadows(param),
											new MeshShadows(param)
										};
			
			menuhelp = new MenuHelpClass(param, elements);
		}

		public override string getText()
		{
			return "Options";
		}

		public override IMenu DoItem()
		{
			return this;
		}

		#region IMenu Members

		public MenuElement[] getMenuElements()
		{
			return elements;
		}

		public IMenu getParent()
		{
			return parent;
		}

		public void RenderElements()
		{
			menuhelp.RenderElements();
		}

		public IMenu DoEscape()
		{
			return parent;
		}

		public void setLastIndex(int idx)
		{
			lastindex = idx;
		}
		
		public int getLastIndex()
		{
			return lastindex;
		}

		public override void Dispose()
		{
			base.Dispose ();
			menuhelp.Dispose();
		}

		#endregion

	}
	
	class MenuHelpClass : IDisposable
	{
		protected ModelParams param;
		public static float Space = 0.8f;
		public static float LeftExtr = 3.8f;
		public static float LowerExtr = 1.0f;
		public static float TextExtr = 0.2f;
		public static float TextSize = 0.1f;
		public static float TextBend = 0.001f;
		public static float LetterWidth = 0.4f;
		protected MenuElement[] elements;

		public MenuHelpClass(ModelParams param, MenuElement[] elements)
		{
			this.param = param;
			this.elements = elements;
			if(elements != null)
			{
				int n = elements.Length;
				for(int i = 0; i < n; i++)
				{
					elements[i].Translation = new Vector3(
						- LeftExtr,
						(float) ( (n / 2.0 - i) * 2 * Space - LowerExtr ),
						MenuHelpClass.TextExtr / 2f);
				}
			} 
			else this.elements = new MenuElement[0];
		}

		public void RenderElements()
		{
			for(int i=0; i < elements.Length; i ++)
			{
				elements[i].Render();
			}
		}

		#region IDisposable Members

		public void Dispose()
		{
			for(int i = 0; i < elements.Length; i++)
			{
				elements[i].Dispose();
			}
		}

		#endregion
	}

	class Main : IMenu
	{
		protected ModelParams param;
		protected MenuElement[] elements;
		protected MenuHelpClass menuhelp;
		protected int lastindex = 0;

		public Main(ModelParams param)
		{
			elements = new MenuElement[]{
											new GameSubMenu(param, this),
											new NetworkPlay(param, this),
											new Options(param, this),
											new ResumeGame(param),
											new Quit(param)
										};

			this.param = param;
			menuhelp = new MenuHelpClass(param, elements);
		}
		
		#region IMenu Members

		public MenuElement[] getMenuElements()
		{
			return elements;
		}

		public IMenu getParent()
		{
			return null;
		}

		public void RenderElements()
		{
			menuhelp.RenderElements();
		}

		public IMenu DoEscape()
		{
			param.engine.State.trigger(new TriggerParams(MainMenuSelection.FAILURE));
			return null;
		}

		public void setLastIndex(int idx)
		{
			lastindex = idx;
		}
		
		public int getLastIndex()
		{
			return lastindex;
		}

		public void Dispose()
		{
			menuhelp.Dispose();
		}

		#endregion
	}

	class Frame : IDisposable
	{
		Vector3[] side = new Vector3[2]{
										   new Vector3(-4f, -0.75f, 0f), 
										   new Vector3(4f, 1.25f, 0f)
									   };
		float ext = 0.5f;

		Primitive[] triangles;

		public Frame(ModelParams param)
		{
			triangles = new Primitive[32];
			Vector3[] v = new Vector3[16];

			int outer, front, left, up;
			int UP = 1, LEFT = 2, FRONT = 4, OUTER = 8;
			
			for(outer = 0; outer <= 1; outer++)
				for(front = 0; front <= 1; front++)
					for(left = 0; left <= 1; left++)
						for(up = 0; up <= 1; up++)
						{
							v[outer*OUTER + front*FRONT + left*LEFT + up*UP] = 
								new Vector3(
								(float)(side[left].X + outer * ext * ((left==1)?1:-1)),
								(float)(side[up].Y + (1 - outer) * ext * ((up==1)?-1:1)),
								(float)(side[0].Z - front * ext)
								);
						}

			Engine engine = param.engine;
			
			// Front part
			triangles[0] = engine.Pr_TesselatedTriangle(
				v[FRONT+LEFT+UP+OUTER], v[FRONT+UP], v[FRONT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[1] = engine.Pr_TesselatedTriangle(
				v[FRONT+UP+LEFT], v[FRONT+UP], v[FRONT+LEFT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[2] = engine.Pr_TesselatedTriangle(
				v[FRONT+LEFT+OUTER], v[FRONT+OUTER], v[FRONT], @"..\textures\menu_textures\wood.bmp");
			triangles[3] = engine.Pr_TesselatedTriangle(
				v[FRONT], v[FRONT+LEFT], v[FRONT+LEFT+OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[4] = engine.Pr_TesselatedTriangle(
				v[FRONT+LEFT+UP+OUTER], v[FRONT+LEFT], v[FRONT+UP+LEFT], @"..\textures\menu_textures\wood.bmp");
			triangles[5] = engine.Pr_TesselatedTriangle(
				v[FRONT+LEFT+OUTER], v[FRONT+LEFT], v[FRONT+LEFT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[6] = engine.Pr_TesselatedTriangle(
				v[FRONT+UP+OUTER], v[FRONT+UP], v[FRONT], @"..\textures\menu_textures\wood.bmp");
			triangles[7] = engine.Pr_TesselatedTriangle(
				v[FRONT], v[FRONT+OUTER], v[FRONT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");

			// Back part
			triangles[8] = engine.Pr_TesselatedTriangle(
				v[LEFT+UP+OUTER], v[UP+OUTER], v[UP], @"..\textures\menu_textures\wood.bmp");
			triangles[9] = engine.Pr_TesselatedTriangle(
				v[UP], v[UP+LEFT], v[LEFT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[10] = engine.Pr_TesselatedTriangle(
				v[LEFT+OUTER], v[0], v[OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[11] = engine.Pr_TesselatedTriangle(
				v[LEFT], v[0], v[LEFT+OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[12] = engine.Pr_TesselatedTriangle(
				v[LEFT+UP+OUTER], v[UP+LEFT], v[LEFT], @"..\textures\menu_textures\wood.bmp");
			triangles[13] = engine.Pr_TesselatedTriangle(
				v[LEFT], v[LEFT+OUTER], v[LEFT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[14] = engine.Pr_TesselatedTriangle(
				v[UP+OUTER], v[0], v[UP], @"..\textures\menu_textures\wood.bmp");
			triangles[15] = engine.Pr_TesselatedTriangle(
				v[OUTER], v[0], v[UP+OUTER], @"..\textures\menu_textures\wood.bmp");

			// Inner Tile		
			triangles[16] = engine.Pr_TesselatedTriangle(
				v[FRONT+LEFT+UP], v[LEFT], v[LEFT+UP], @"..\textures\menu_textures\wood.bmp");
			triangles[17] = engine.Pr_TesselatedTriangle(
				v[FRONT+LEFT], v[LEFT], v[FRONT+UP+LEFT], @"..\textures\menu_textures\wood.bmp");
			triangles[18] = engine.Pr_TesselatedTriangle(
				v[FRONT+UP], v[UP], v[0], @"..\textures\menu_textures\wood.bmp");
			triangles[19] = engine.Pr_TesselatedTriangle(
				v[0], v[FRONT], v[FRONT+UP], @"..\textures\menu_textures\wood.bmp");
			triangles[20] = engine.Pr_TesselatedTriangle(
				v[FRONT+LEFT+UP], v[LEFT+UP], v[UP], @"..\textures\menu_textures\wood.bmp");
			triangles[21] = engine.Pr_TesselatedTriangle(
				v[UP], v[FRONT+UP], v[FRONT+UP+LEFT], @"..\textures\menu_textures\wood.bmp");
			triangles[22] = engine.Pr_TesselatedTriangle(
				v[FRONT+LEFT], v[0], v[LEFT], @"..\textures\menu_textures\wood.bmp");
			triangles[23] = engine.Pr_TesselatedTriangle(
				v[FRONT], v[0], v[FRONT+LEFT], @"..\textures\menu_textures\wood.bmp");

			// Outer Tile		
			triangles[24] = engine.Pr_TesselatedTriangle(
				v[FRONT+LEFT+OUTER], v[LEFT+OUTER+UP], v[LEFT+OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[25] = engine.Pr_TesselatedTriangle(
				v[LEFT+OUTER+UP], v[LEFT+OUTER+FRONT], v[FRONT+UP+LEFT+OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[26] = engine.Pr_TesselatedTriangle(
				v[FRONT+UP+OUTER], v[OUTER], v[UP+OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[27] = engine.Pr_TesselatedTriangle(
				v[FRONT+OUTER], v[OUTER], v[FRONT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[28] = engine.Pr_TesselatedTriangle(
				v[FRONT+LEFT+UP+OUTER], v[UP+OUTER], v[LEFT+UP+OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[29] = engine.Pr_TesselatedTriangle(
				v[FRONT+UP+OUTER], v[UP+OUTER], v[FRONT+UP+LEFT+OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[30] = engine.Pr_TesselatedTriangle(
				v[FRONT+LEFT+OUTER], v[LEFT+OUTER], v[OUTER], @"..\textures\menu_textures\wood.bmp");
			triangles[31] = engine.Pr_TesselatedTriangle(
				v[OUTER], v[FRONT+OUTER], v[FRONT+LEFT+OUTER], @"..\textures\menu_textures\wood.bmp");

			for(int i = 0; i < 32; i++)
			{
				triangles[i].NotEffectedByLight = true;
			}
		}

		public void Render(Vector3 translation)
		{
			for(int i = 0; i < triangles.Length; i++)
			{
				triangles[i].Render(translation);
			}
		}

		#region IDisposable Members

		public void Dispose()
		{
			for(int i = 0; i < triangles.Length; i++)
			{
				triangles[i].Dispose();
			}
		}

		#endregion
	}

	public interface IMovingLight
	{
		void Activate();
		void DeActivate();
		void DoPeriod();
	}

	public class CircleLight : IMovingLight
	{
		protected Light light;
		protected double radiusX, radiusY, phaseX, phaseY;
		protected Vector3 center, position;
		public static double PI2 = 2.0 * Math.PI;
		public static bool circle;

		public CircleLight(Engine engine, Color color, Vector3 center, 
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
			position.X = center.X + (float)(radiusX * Math.Sin(phaseX));
			position.Y = center.Y + (float)(radiusY * Math.Sin(phaseY));
			position.Z = center.Z + (float)(
				radiusX * Math.Cos(phaseX) + radiusY * Math.Cos(phaseY));
		}
	}

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


	public class MainMenu : IModel
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
//			param.texts = new Texts(engine);
			lights = new TranslateLight[7];
		}

		public MainMenu(Engine engine, bool game_menu, Model.Model game_model)
		{
			this.engine = engine;
			param = new ModelParams();
			param.engine = engine;
			param.menu = this;
			param.game_menu = game_menu;
			param.game_model = game_model;
//			param.texts = new Texts(engine);
			lights = new TranslateLight[7];
		}

		#region IModel Members

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

		#endregion

		#region IPeriod Members

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

		#endregion
	}

	class SpiderAgent : IPeriod
	{
		protected Animation animation;
		protected Vector3 up, min, max;
		protected Vector3 position,
			direction, velocity;
		protected const int slow = 3;
		protected int state = 0;
		protected float size;
		protected float ch = 0.4f;

		public SpiderAgent(Animation animation, 
			Vector3 up, Vector3 min, Vector3 max)
		{
			this.animation = animation;
			this.min = Vector3.Minimize(min, max);
			this.max = Vector3.Maximize(min, max);
			this.up = up;

			this.position = new Vector3(
				StaticRandomLibrary.FloatValue(min.X, max.X),
				StaticRandomLibrary.FloatValue(min.Y, max.Y),
				StaticRandomLibrary.FloatValue(min.Z, max.Z));
			this.direction = max - min;
			this.velocity = direction * (1f / direction.Length()) * 0.02f;

			this.size = StaticRandomLibrary.FloatValue(1f) + 0.75f;
		}

		public void Render()
		{
			Matrix m = Matrix.Scaling(size, size, size) *
				Animation.LookAt(position, -direction, up);
			animation.Draw(m, state / slow);
		}

		#region IPeriod Members

		public void Period()
		{
			this.velocity = direction * (1f / direction.Length()) * 0.02f;
			int condition = 0;
			if(!Vector3.Minimize(position + velocity, min).Equals(min))
			{
				velocity = -velocity;
				direction = -direction;
				condition++;
			}
			if(!Vector3.Maximize(position + velocity, max).Equals(max))
			{
				velocity = -velocity;
				direction = -direction;
				condition++;
			}

			if(condition < 2) position += velocity;
			// else {} // TODO

			state++;
			if(state >= animation.MaxScenes * slow)
				state = 0;

			Vector3 change = new Vector3(
				StaticRandomLibrary.FloatValue(-ch, ch),
				StaticRandomLibrary.FloatValue(-ch, ch),
				0f);
			direction += change;
		}

		#endregion
	}

	public class LoadingTitle : IModel
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

}