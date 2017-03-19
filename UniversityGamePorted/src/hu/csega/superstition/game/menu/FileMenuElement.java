package hu.csega.superstition.game.menu;

public class FileMenuElement extends MenuElement
{
	protected String file;
	protected IFileParent parent;
	protected Element filename;
	protected boolean disposed = false;

	public IFileParent Parent
	{
		get { return parent; }
		set { parent = value; }
	}

	public FileMenuElement(ModelParams param, IFileParent parent, String file)
	{
		super(param);

		this.file = file;
		this.parent = parent;

		Font font = new Font("Times New Roman", MenuHelpClass.TextSize);
		filename = param.engine.GetTextMesh(font, file, MenuHelpClass.TextBend, MenuHelpClass.TextExtr);
	}

	public String File
	{
		get{ return file; }
	}

	@Override
	public IMenu DoItem()
	{
		return parent.DoChildrenItem(file);
	}

	@Override
	public String getText()
	{
		return null;
	}

	@Override
	public void Dispose()
	{
		disposed = true;
		filename.Dispose();
	}

	@Override
	public void Render()
	{
		if(disposed) return;
		filename.Render(translation);
	}
}