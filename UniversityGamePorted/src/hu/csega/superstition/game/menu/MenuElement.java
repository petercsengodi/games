package hu.csega.superstition.game.menu;

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

	public MenuElement(ModelParams param, String text)
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

	public abstract String getText();

	public void OnEnter(){}
	public void OnLeft(){}
	public void OnRight(){}

	public void Render()
	{
		if(text != null) text.Render(translation);
	}

	public abstract IMenu DoItem();

	public void Dispose()
	{
		if(text != null) text.Dispose();
	}

} // end of class MenuElement