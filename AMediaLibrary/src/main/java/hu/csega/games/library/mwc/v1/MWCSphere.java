package hu.csega.games.library.mwc.v1;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("Sphere")
public class MWCSphere {

	private float px, py, pz;
	private float r;

	public MWCSphere() {
	}

	public MWCSphere(float px, float py, float pz, float r) {
		this.px = px;
		this.py = py;
		this.pz = pz;
		this.r = r;
	}

	@XmlField("px")
	public float getPx() {
		return px;
	}

	@XmlField("px")
	public void setPx(float px) {
		this.px = px;
	}

	@XmlField("py")
	public float getPy() {
		return py;
	}

	@XmlField("py")
	public void setPy(float py) {
		this.py = py;
	}

	@XmlField("pz")
	public float getPz() {
		return pz;
	}

	@XmlField("pz")
	public void setPz(float pz) {
		this.pz = pz;
	}

	@XmlField("r")
	public float getR() {
		return r;
	}

	@XmlField("r")
	public void setR(float r) {
		this.r = r;
	}

}
