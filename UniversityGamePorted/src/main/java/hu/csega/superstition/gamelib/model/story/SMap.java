package hu.csega.superstition.gamelib.model.story;

import java.util.List;

import hu.csega.superstition.gamelib.model.SObject;
import hu.csega.superstition.xml.XmlClass;
import hu.csega.superstition.xml.XmlField;

@XmlClass("Superstition.Map")
public class SMap implements SObject {

	@XmlField("name")
	public String getName() {
		return name;
	}

	@XmlField("name")
	public void setName(String name) {
		this.name = name;
	}

	@XmlField("rooms")
	public List<SRoom> getCorridors() {
		return rooms;
	}

	@XmlField("rooms")
	public void setCorridors(List<SRoom> rooms) {
		this.rooms = rooms;
	}

	private String name;
	private List<SRoom> rooms;
}