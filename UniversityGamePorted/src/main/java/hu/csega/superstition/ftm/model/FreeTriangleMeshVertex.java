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

	public double getpX() {
		return pX;
	}

	public void setpX(double pX) {
		this.pX = pX;
	}

	public double getpY() {
		return pY;
	}

	public void setpY(double pY) {
		this.pY = pY;
	}

	public double getpZ() {
		return pZ;
	}

	public void setpZ(double pZ) {
		this.pZ = pZ;
	}

	public double getnX() {
		return nX;
	}

	public void setnX(double nX) {
		this.nX = nX;
	}

	public double getnY() {
		return nY;
	}

	public void setnY(double nY) {
		this.nY = nY;
	}

	public double getnZ() {
		return nZ;
	}

	public void setnZ(double nZ) {
		this.nZ = nZ;
	}

	public double gettX() {
		return tX;
	}

	public void settX(double tX) {
		this.tX = tX;
	}

	public double gettY() {
		return tY;
	}

	public void settY(double tY) {
		this.tY = tY;
	}

	private static final long serialVersionUID = 1L;
}
