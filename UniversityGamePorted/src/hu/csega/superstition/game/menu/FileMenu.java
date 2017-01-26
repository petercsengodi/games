package hu.csega.superstition.game.menu;

public class FileMenu implements IMenu {

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

	@Override
	public MenuElement[] getMenuElements()
	{
		return elements;
	}

	@Override
	public IMenu getParent()
	{
		return parent;
	}

	@Override
	public void RenderElements()
	{
		for(int i = 0; i < 6; i++)
		{
			elements[i].Render();
		}
	}

	@Override
	public IMenu DoEscape()
	{
		return parent.getParent();
	}

	@Override
	public void setLastIndex(int idx)
	{
		lastIndex = idx;
	}

	@Override
	public int getLastIndex()
	{
		return lastIndex;
	}

	public void Dispose()
	{
		foreach(object o in files)
		{
			(o as FileMenuElement).Dispose();
		}

		up.Dispose();
		down.Dispose();
	}



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