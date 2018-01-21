package hu.csega.games.library.mwc.v1;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("vertex")
public class MWCVertex {

	private float px, py, pz;
	private float nx, ny, nz;
	private float tx, ty;

	public MWCVertex() {
	}

	public MWCVertex(float px, float py, float pz, float nx, float ny, float nz, float tx, float ty) {
		this.px = px;
		this.py = py;
		this.pz = pz;
		this.nx = nx;
		this.ny = ny;
		this.nz = nz;
		this.tx = tx;
		this.ty = ty;
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

	@XmlField("nx")
	public float getNx() {
		return nx;
	}

	@XmlField("nx")
	public void setNx(float nx) {
		this.nx = nx;
	}

	@XmlField("ny")
	public float getNy() {
		return ny;
	}

	@XmlField("ny")
	public void setNy(float ny) {
		this.ny = ny;
	}

	@XmlField("nz")
	public float getNz() {
		return nz;
	}

	@XmlField("nz")
	public void setNz(float nz) {
		this.nz = nz;
	}

	@XmlField("tx")
	public float getTx() {
		return tx;
	}

	@XmlField("tx")
	public void setTx(float tx) {
		this.tx = tx;
	}

	@XmlField("ty")
	public float getTy() {
		return ty;
	}

	@XmlField("ty")
	public void setTy(float ty) {
		this.ty = ty;
	}

}
