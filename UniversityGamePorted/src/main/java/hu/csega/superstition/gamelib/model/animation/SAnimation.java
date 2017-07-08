package hu.csega.superstition.gamelib.model.animation;

import java.util.List;

import hu.csega.superstition.gamelib.model.SObject;
import hu.csega.superstition.xml.XmlClass;
import hu.csega.superstition.xml.XmlField;

@XmlClass("Superstition.Animation")
public class SAnimation implements SObject {

	private String name;
	private List<SBodyPart> bodyParts;
	private int maxScenes;

	@XmlField("name")
	public String getName() {
		return name;
	}

	@XmlField("name")
	public void setName(String name) {
		this.name = name;
	}

	@XmlField("bodyParts")
	public List<SBodyPart> getBodyParts() {
		return bodyParts;
	}

	@XmlField("bodyParts")
	public void setBodyParts(List<SBodyPart> bodyParts) {
		this.bodyParts = bodyParts;
	}

	@XmlField("maxScenes")
	public int getMaxScenes() {
		return maxScenes;
	}

	@XmlField("maxScenes")
	public void setMaxScenes(int maxScenes) {
		this.maxScenes = maxScenes;
	}

}