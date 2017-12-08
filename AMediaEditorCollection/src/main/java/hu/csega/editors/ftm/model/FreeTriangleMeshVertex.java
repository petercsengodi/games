package hu.csega.editors.ftm.model;

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

	public FreeTriangleMeshPoint positionToPoint() {
		return new FreeTriangleMeshPoint(pX, pY, pZ);
	}

	public void move(double x, double y, double z) {
		pX += x;
		pY += y;
		pZ += z;
	}

	public void moveTexture(double horizontalMove, double verticalMove) {
		tX += horizontalMove;
		tY += verticalMove;

		if(tX < 0.0)
			tX = 0.0;
		else if(tX > 1.0)
			tX = 1.0;

		if(tY < 0.0)
			tY = 0.0;
		else if(tY > 1.0)
			tY = 1.0;
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
