package hu.csega.editors.anm.model.parts;

import org.json.JSONObject;
import org.json.JSONString;

public class AnimatorPartVertex implements JSONString {

	private double px;
	private double py;
	private double pz;

	private double nx;
	private double ny;
	private double nz;

	private double tx;
	private double ty;

	@Override
	public String toJSONString() {
		return toJSONObject().toString();
	}

	public JSONObject toJSONObject() {
		return new JSONObject(this);
	}

	public double getPx() {
		return px;
	}

	public void setPx(double px) {
		this.px = px;
	}

	public double getPy() {
		return py;
	}

	public void setPy(double py) {
		this.py = py;
	}

	public double getPz() {
		return pz;
	}

	public void setPz(double pz) {
		this.pz = pz;
	}

	public double getNx() {
		return nx;
	}

	public void setNx(double nx) {
		this.nx = nx;
	}

	public double getNy() {
		return ny;
	}

	public void setNy(double ny) {
		this.ny = ny;
	}

	public double getNz() {
		return nz;
	}

	public void setNz(double nz) {
		this.nz = nz;
	}

	public double getTx() {
		return tx;
	}

	public void setTx(double tx) {
		this.tx = tx;
	}

	public double getTy() {
		return ty;
	}

	public void setTy(double ty) {
		this.ty = ty;
	}

}
