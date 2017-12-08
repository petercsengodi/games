package hu.csega.games.library.story;

import java.util.List;

import hu.csega.games.library.model.SObject;
import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

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