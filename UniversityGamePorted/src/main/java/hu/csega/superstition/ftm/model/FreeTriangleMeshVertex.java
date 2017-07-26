package hu.csega.superstition.ftm.model;

import java.io.Serializable;

public class FreeTriangleMeshVertex implements Serializable {

	private double pX;
	private double pY;
	private double pZ;

	private double nX;
	private double nY;
	private double nZ;

	private double tX;
	private double tY;

	public FreeTriangleMeshVertex(double x, double y, double z) {
		this.pX = x;
		this.pY = y;
		this.pZ = z;
	}

	public double getPX() {
		return pX;
	}

	public void setPX(double pX) {
		this.pX = pX;
	}

	public double getPY() {
		return pY;
	}

	public void setPY(double pY) {
		this.pY = pY;
	}

	public double getPZ() {
		return pZ;
	}

	public void setPZ(double pZ) {
		this.pZ = pZ;
	}

	public double getNX() {
		return nX;
	}

	public void setNX(double nX) {
		this.nX = nX;
	}

	public double getNY() {
		return nY;
	}

	public void setNY(double nY) {
		this.nY = nY;
	}

	public double getNZ() {
		return nZ;
	}

	public void setNZ(double nZ) {
		this.nZ = nZ;
	}

	public double getTX() {
		return tX;
	}

	public void setTX(double tX) {
		this.tX = tX;
	}

	public double getTY() {
		return tY;
	}

	public void setTY(double tY) {
		this.tY = tY;
	}

	private static final long serialVersionUID = 1L;
}
