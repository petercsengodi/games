package hu.csega.editors.ftm.util;

import hu.csega.editors.ftm.model.FreeTriangleMeshPoint;

/**
 * This helper class can make an intersection with a line and a sphere using the shapes' equations.
 */
public class FreeTriangleMeshSphereLineIntersection {

	// LINE EQUATION: (source: x1, y1, z1; target: x2, y2, z2)
	// x(t) = x1 + (x2 - x1) * t
	// y(t) = y1 + (y2 - y1) * t
	// z(t) = z1 + (z2 - z1) * t

	// SPHERE EQUATION: (sphere center: x0, y0, z0)
	// (x - x0)^2 + (y - y0)^2 + (z - z0)^2 = radius^2

	// Means:
	// (x1 + (x2 - x1) * t - x0)^2 + (y1 + (y2 - y1) * t - y0)^2 + (z1 + (z2 - z1) * t - z0)^2 = radius^2
	// ((x2 - x1) * t + x1 - x0)^2 + ((y2 - y1) * t + y1 - y0)^2 + ((z2 - z1) * t + z1 - z0)^2 = radius^2
	// (x2 - x1)^2 * t^2 + 2 * (x1 - x0) * (x2 - x1) * t + (x1 - x0)^2 + [...] - radius^2 = 0

	// a = (x2 - x1)^2 + (y2 - y1)^2 + (z2 - z1)^2
	// b = 2 * (x1 - x0) * (x2 - x1) + 2 * (y1 - y0) * (y2 - y1) + 2 * (z1 - z0) * (z2 - z1)
	// c = (x1 - x0)^2 + (y1 - y0)^2 + (z1 - z0)^2 - radius^2

	// det = b^2 - 4*a*c
	// t1;t2 = (-b +/- sqrt(det)) / (2*a);

	private double sourceX;
	private double sourceY;
	private double sourceZ;

	private double targetX;
	private double targetY;
	private double targetZ;

	private double sphereX;
	private double sphereY;
	private double sphereZ;
	private double radius;

	private double a;
	private double b;
	private double c;

	private double det;

	private boolean hasSolution;
	private boolean sameSolution;
	private double t1;
	private double t2;

	private FreeTriangleMeshPoint result1 = new FreeTriangleMeshPoint();
	private FreeTriangleMeshPoint result2 = new FreeTriangleMeshPoint();

	public void setLineSource(double x, double y, double z) {
		this.sourceX = x;
		this.sourceY = y;
		this.sourceZ = z;
	}

	public void setLineTarget(double x, double y, double z) {
		this.targetX = x;
		this.targetY = y;
		this.targetZ = z;
	}

	public void setSphereCenter(double x, double y, double z) {
		this.sphereX = x;
		this.sphereY = y;
		this.sphereZ = z;
	}

	public void setSphereRadius(double radius) {
		this.radius = radius;
	}

	/** Intersection point can't be calculated well, but we will know, if the intersection happend or not. */
	public void simulateAMovingAndAStandingSphere(
			double movingSourceX, double movingSourceY, double movingSourceZ,
			double movingTargetX, double movingTargetY, double movingTargetZ,
			double movingSphereRadius,
			double standingSphereX, double standingSphereY, double standingSphereZ,
			double standingSphereRadius) {

		this.radius = movingSphereRadius + standingSphereRadius;

		this.sourceX = movingSourceX;
		this.sourceY = movingSourceY;
		this.sourceZ = movingSourceZ;

		this.targetX = movingTargetX;
		this.targetY = movingTargetY;
		this.targetZ = movingTargetZ;

		this.sphereX = standingSphereX;
		this.sphereY = standingSphereY;
		this.sphereZ = standingSphereZ;
	}

	public void calculateConstants() {
		double targetMinusSourceX = targetX - sourceX;
		double targetMinusSourceY = targetY - sourceY;
		double targetMinusSourceZ = targetZ - sourceZ;

		double sourceMinusSphereX = sourceX - sphereX;
		double sourceMinusSphereY = sourceY - sphereY;
		double sourceMinusSphereZ = sourceZ - sphereZ;

		this.a = (targetMinusSourceX*targetMinusSourceX + targetMinusSourceY*targetMinusSourceY + targetMinusSourceZ*targetMinusSourceZ);

		this.b = 2.0 * (
				sourceMinusSphereX * targetMinusSourceX +
				sourceMinusSphereY * targetMinusSourceY +
				sourceMinusSphereZ * targetMinusSourceZ);

		this.c = (sourceMinusSphereX*sourceMinusSphereX + sourceMinusSphereY*sourceMinusSphereY + sourceMinusSphereZ*sourceMinusSphereZ - radius*radius);

		this.det = this.b*this.b - 4.0*this.a*this.c;
		this.hasSolution = this.det >= 0;
		this.sameSolution = this.hasSolution && this.det < 0.000001;
	}

	/** Call after setters then {@link #calculateConstants()}. */
	public boolean solutionExists() {
		return hasSolution;
	}

	/** Call after setters then {@link #calculateConstants()}. */
	public boolean thereAreTwoSolutions() {
		return hasSolution && !sameSolution;
	}

	/** Call after setters then {@link #calculateConstants()}. */
	public boolean thereIsOneSolution() {
		return hasSolution && sameSolution;
	}

	/** Call after setters then {@link #calculateConstants()}. */
	public boolean thereIsNoSolution() {
		return !hasSolution;
	}

	/** Call after setters then {@link #calculateConstants()}. */
	public void calculateResults() {
		if(hasSolution) {
			double sqrtDet = Math.sqrt(det);
			double twoA = 2.0 * this.a;
			this.t1 = (-this.b - sqrtDet) / twoA;
			this.t2 = (-this.b + sqrtDet) / twoA;

			double targetMinusSourceX = targetX - sourceX;
			double targetMinusSourceY = targetY - sourceY;
			double targetMinusSourceZ = targetZ - sourceZ;

			result1.setX(targetMinusSourceX * t1 + sourceX);
			result1.setY(targetMinusSourceY * t1 + sourceY);
			result1.setZ(targetMinusSourceZ * t1 + sourceZ);

			result2.setX(targetMinusSourceX * t2 + sourceX);
			result2.setY(targetMinusSourceY * t2 + sourceY);
			result2.setZ(targetMinusSourceZ * t2 + sourceZ);
		}
	}

	/** Call after setters then {@link #calculateConstants()} then {@link #calculateResults()}. */
	public FreeTriangleMeshPoint result1() {
		return this.result1;
	}

	/** Call after setters then {@link #calculateConstants()} then {@link #calculateResults()}. */
	public FreeTriangleMeshPoint result2() {
		return this.result2;
	}

	/** Call after setters then {@link #calculateConstants()} then {@link #calculateResults()}. */
	public double lowestT() {
		return (t1 < t2) ? t1 : t2;
	}

	/** Call after setters then {@link #calculateConstants()} then {@link #calculateResults()}. */
	public FreeTriangleMeshPoint nearestResult() {
		if(t1 < t2) {
			return result1;
		} else {
			return result2;
		}
	}

	/** Call after setters then {@link #calculateConstants()} then {@link #calculateResults()}. */
	public boolean isResult1BeforeSource() {
		return this.t1 < 0.0;
	}

	/** Call after setters then {@link #calculateConstants()} then {@link #calculateResults()}. */
	public boolean isResult1AfterTarget() {
		return this.t1 > 1.0;
	}

	/** Call after setters then {@link #calculateConstants()} then {@link #calculateResults()}. */
	public boolean isResult1InsidePatch() {
		return this.t1 >= 0 && this.t1 <= 1.0;
	}

	/** Call after setters then {@link #calculateConstants()} then {@link #calculateResults()}. */
	public boolean isResult2BeforeSource() {
		return this.t2 < 0.0;
	}

	/** Call after setters then {@link #calculateConstants()} then {@link #calculateResults()}. */
	public boolean isResult2AfterTarget() {
		return this.t2 > 1.0;
	}

	/** Call after setters then {@link #calculateConstants()} then {@link #calculateResults()}. */
	public boolean isResult2InsidePatch() {
		return this.t2 >= 0 && this.t2 <= 1.0;
	}

	/** Call after setters then {@link #calculateConstants()} then {@link #calculateResults()}. */
	public boolean areThereAnyResultsInsideThePatch() {
		return hasSolution && (this.t1 >= 0 && this.t1 <= 1.0 || this.t2 >= 0 && this.t2 <= 1.0);
	}
}
