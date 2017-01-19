package hu.csega.superstition.game.menu;

import org.joml.Vector3f;

import hu.csega.superstition.game.Engine;
import hu.csega.superstition.game.elements.Element;

public class Slider {

	protected Engine engine;
	public static int LOW = 0;
	public static int MEDIUM = 1;
	public static int HIGH = 2;
	protected Element[] mesh;

	public static Vector3f[] DEFAULT = new Vector3f[]{
			new Vector3f(6f, 0f, 0f),
			new Vector3f(4.5f, 0f, 0f),
			new Vector3f(5.8f, 0f, 0f)
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

	public void Render(Vector3f translation, int val) {
		mesh[val].Render(translation);
	}
}