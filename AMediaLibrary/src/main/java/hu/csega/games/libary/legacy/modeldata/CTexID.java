package hu.csega.games.libary.legacy.modeldata;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("T3DCreator.TexID")
public class CTexID {

	public String name;

	@XmlField("name")
	public String getName() {
		return name;
	}

	@XmlField("name")
	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "Texture: " + name;
	}

}