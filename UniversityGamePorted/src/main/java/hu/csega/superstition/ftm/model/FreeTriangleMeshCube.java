package hu.csega.superstition.ftm.model;

public class FreeTriangleMeshCube {

	private double x1;
	private double y1;
	private double z1;

	private double x2;
	private double y2;
	private double z2;

	public FreeTriangleMeshCube() {
	}

	public FreeTriangleMeshCube(double x1, double y1, double z1, double x2, double y2, double z2) {
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
	}

	public double getX1() {
		return x1;
	}
	public void setX1(double x1) {
		this.x1 = x1;
	}
	public double getY1() {
		return y1;
	}
	public void setY1(double y1) {
		this.y1 = y1;
	}
	public double getZ1() {
		return z1;
	}
	public void setZ1(double z1) {
		this.z1 = z1;
	}
	public double getX2() {
		return x2;
	}
	public void setX2(double x2) {
		this.x2 = x2;
	}
	public double getY2() {
		return y2;
	}
	public void setY2(double y2) {
		this.y2 = y2;
	}
	public double getZ2() {
		return z2;
	}
	public void setZ2(double z2) {
		this.z2 = z2;
	}

	public boolean contains(FreeTriangleMeshVertex vertex) {
		double x = vertex.getPX();
		if(x < x1 || x > x2)
			return false;

		double y = vertex.getPY();
		if(y < y1 || y > y2)
			return false;

		double z = vertex.getPZ();
		if(z < z1 || z > z2)
			return false;

		return true;
	}


}
