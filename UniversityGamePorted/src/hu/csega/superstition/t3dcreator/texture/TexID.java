package hu.csega.superstition.t3dcreator.texture;

import java.awt.Image;

import com.jogamp.opengl.util.texture.Texture;

public class TexID {

	public String name;
	private Texture texture;
	private Image map;

	public Texture getTexture() {
		return texture;
	}

	public Image getMap() {
		return map;
	}

	public TexID(String name, Texture texture, Image map) {
		this.name = name;
		this.texture = texture;
		this.map = map;
	}

	private TexID() {
		this.name = null;
		this.texture = null;
		this.map = null;
	}

	public static Object GetNullInstance() {
		return new TexID();
	}

}