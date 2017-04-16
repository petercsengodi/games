package hu.csega.superstition.gamelib.model.mesh;

import java.util.ArrayList;
import java.util.List;

import hu.csega.superstition.gamelib.model.SObject;
import hu.csega.superstition.xml.XmlClass;
import hu.csega.superstition.xml.XmlField;

@XmlClass("Superstition.Mesh")
public class SMesh implements SObject {

	@XmlField("name")
	public String getName() {
		return name;
	}

	@XmlField("name")
	public void setName(String name) {
		this.name = name;
	}

	@XmlField("figures")
	public List<SShape> getFigures() {
		return figures;
	}

	@XmlField("figures")
	public void setFigures(List<SShape> figures) {
		this.figures = figures;
	}

	private String name;
	private List<SShape> figures = new ArrayList<>();
}
