package hu.csega.superstition.gamelib.model;

import hu.csega.superstition.xml.XmlClass;
import hu.csega.superstition.xml.XmlField;

@XmlClass("Superstition.TextureRef")
public class STextureRef implements SObject {

	@XmlField("name")
	public String getName() {
		return name;
	}

	@XmlField("name")
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TextureRef: " + name;
	}

	private String name;
}
