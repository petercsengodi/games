package hu.csega.games.library.legacy.animationdata;

import java.util.List;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("Legacy.CModelData")
public class CModelData
{

	@XmlField("parts")
	public List<CPartData> getParts() {
		return parts;
	}

	@XmlField("parts")
	public void setParts(List<CPartData> parts) {
		this.parts = parts;
	}

	@XmlField("max_scenes")
	public int getMax_scenes() {
		return max_scenes;
	}

	@XmlField("max_scenes")
	public void setMax_scenes(int max_scenes) {
		this.max_scenes = max_scenes;
	}

	public List<CPartData> parts;
	public int max_scenes;
}