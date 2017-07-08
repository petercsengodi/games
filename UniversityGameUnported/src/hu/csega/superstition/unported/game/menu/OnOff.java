package hu.csega.superstition.game.menu;

import org.joml.Vector3f;

public class OnOff
{
	protected Engine engine;
	protected Element meshOn, meshOff;
	public static Vector3f DEFAULT = new Vector3f(6.5f, 0f, 0f);

	public OnOff(Engine engine)
	{
		this.engine = engine;
		Font font = new Font("Comics Sans MS", MenuHelpClass.TextSize);
		meshOn = engine.GetTextMesh(font, "On", MenuHelpClass.TextBend, MenuHelpClass.TextExtr);
		meshOff = engine.GetTextMesh(font, "Off", MenuHelpClass.TextBend, MenuHelpClass.TextExtr);
	}

	public void Render(Vector3f translation, boolean val)
	{
		Element drawable = val?meshOn:meshOff;
		drawable.Render(translation);
	}
}