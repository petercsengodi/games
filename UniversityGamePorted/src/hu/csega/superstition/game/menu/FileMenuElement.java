package hu.csega.superstition.game.menu;

public class FileMenuElement extends MenuElement
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

	public FileMenuElement(ModelParams param, IFileParent parent, string file)
	{
		super(param);

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

	@Override
	public override void Dispose()
	{
		disposed = true;
		filename.Dispose();
	}

	@Override
	public override void Render()
	{
		if(disposed) return;
		filename.Render(translation);
	}
}