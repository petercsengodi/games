package hu.csega.games.libary.legacy.modeldata;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("T3DCreator.CEdge")
public class CEdge {

	@XmlField("from")
	public CVertex getFrom() {
		return from;
	}

	@XmlField("from")
	public void setFrom(CVertex from) {
		this.from = from;
	}

	@XmlField("to")
	public CVertex getTo() {
		return to;
	}

	@XmlField("to")
	public void setTo(CVertex to) {
		this.to = to;
	}

	@XmlField("triangle")
	public CTriangle getTriangle() {
		return triangle;
	}

	@XmlField("triangle")
	public void setTriangle(CTriangle triangle) {
		this.triangle = triangle;
	}

	public CVertex from, to;
	public CTriangle triangle;

	@Override
	public String toString() {
		if(from == null || to == null)
			return super.toString();

		return "Edge (" +
			from.position.x + ";" + from.position.y + ";" + from.position.z + ") -> (" +
			to.position.x + ";" + to.position.y + ";" + to.position.z + ")";
	}

}