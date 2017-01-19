package hu.csega.superstition.game.menu;

public class MenuHelpClass {
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
			int n = elements.length;
			for(int i = 0; i < n; i++)
			{
				elements[i].Translation = new Vector3f(
						- LeftExtr,
						(float) ( (n / 2.0 - i) * 2 * Space - LowerExtr ),
						MenuHelpClass.TextExtr / 2f);
			}
		}
		else this.elements = new MenuElement[0];
	}

	public void RenderElements()
	{
		for(int i=0; i < elements.length; i ++)
		{
			elements[i].Render();
		}
	}

	public void Dispose()
	{
		for(int i = 0; i < elements.length; i++)
		{
			elements[i].Dispose();
		}
	}

}