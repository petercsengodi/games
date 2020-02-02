package hu.csega.editors.anm.model;

import java.util.List;

import java.util.Map;

public class Animation {

	private Map<Integer, String> textures;
	private Map<Integer, AnimationPart> parts;
	private List<AnimationScene> scenes;

	public Map<Integer, String> getTextures() {
		return textures;
	}

	public void setTextures(Map<Integer, String> textures) {
		this.textures = textures;
	}

	public Map<Integer, AnimationPart> getParts() {
		return parts;
	}

	public void setParts(Map<Integer, AnimationPart> parts) {
		this.parts = parts;
	}

	public List<AnimationScene> getScenes() {
		return scenes;
	}

	public void setScenes(List<AnimationScene> scenes) {
		this.scenes = scenes;
	}

}
