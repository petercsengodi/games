package hu.csega.editors.transformations.layer4.data;

import java.io.Serializable;

import hu.csega.editors.ftm.model.FreeTriangleMeshPoint;

public class TransformationTesterVertex implements Serializable {

	private double pX;
	private double pY;
	private double pZ;

	private double nX;
	private double nY;
	private double nZ;

	private double tX;
	private double tY;

	private int group;

	private TransformationTesterVertex() {
		this.group = 0;
	}

	public TransformationTesterVertex(double x, double y, double z) {
		this.pX = x;
		this.pY = y;
		this.pZ = z;
		this.group = 0;
	}

	public TransformationTesterVertex(double x, double y, double z, double tx, double ty) {
		this.pX = x;
		this.pY = y;
		this.pZ = z;
		this.tX = tx;
		this.tY = ty;
		this.group = 0;
	}

	public TransformationTesterVertex copy() {
		TransformationTesterVertex v = new TransformationTesterVertex();
		v.pX = pX;
		v.pY = pY;
		v.pZ = pZ;
		v.nX = nX;
		v.nY = nY;
		v.nZ = nZ;
		v.tX = tX;
		v.tY = tY;
		return v;
	}

	public void copyFrom(TransformationTesterVertex other) {
		this.pX = other.pX;
		this.pY = other.pY;
		this.pZ = other.pZ;
		this.nX = other.nX;
		this.nY = other.nY;
		this.nZ = other.nZ;
		this.tX = other.tX;
		this.tY = other.tY;
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

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	private static final long serialVersionUID = 1L;
}
