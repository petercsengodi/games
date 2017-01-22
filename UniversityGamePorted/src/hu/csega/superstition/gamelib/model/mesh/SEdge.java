package hu.csega.superstition.gamelib.model.mesh;

import hu.csega.superstition.xml.XmlClass;
import hu.csega.superstition.xml.XmlField;

@XmlClass("Superstition.Edge")
public class SEdge {

	@XmlField("from")
	public SVertex getFrom() {
		return from;
	}

	@XmlField("from")
	public void setFrom(SVertex from) {
		this.from = from;
	}

	@XmlField("to")
	public SVertex getTo() {
		return to;
	}

	@XmlField("to")
	public void setTo(SVertex to) {
		this.to = to;
	}

	@XmlField("triangle")
	public STriangle getTriangle() {
		return triangle;
	}

	@XmlField("triangle")
	public void setTriangle(STriangle triangle) {
		this.triangle = triangle;
	}

	private SVertex from;
	private SVertex to;
	private STriangle triangle;

	@Override
	public String toString() {
		if(from == null || to == null)
			return super.toString();

		return "Edge (" +
			from.position.x + ";" + from.position.y + ";" + from.position.z + ") -> (" +
			to.position.x + ";" + to.position.y + ";" + to.position.z + ")";
	}

}