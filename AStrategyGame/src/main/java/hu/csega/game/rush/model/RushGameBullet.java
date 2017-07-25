package hu.csega.game.rush.model;

public class RushGameBullet extends RushGameDrawable {

	private double targetX;
	private double targetY;
	private double sourceX;
	private double sourceY;
	private double t;
	private double speed;

	public double getTargetX() {
		return targetX;
	}

	public void setTargetX(double targetX) {
		this.targetX = targetX;
	}

	public double getTargetY() {
		return targetY;
	}

	public void setTargetY(double targetY) {
		this.targetY = targetY;
	}

	public double getSourceX() {
		return sourceX;
	}

	public void setSourceX(double sourceX) {
		this.sourceX = sourceX;
	}

	public double getSourceY() {
		return sourceY;
	}

	public void setSourceY(double sourceY) {
		this.sourceY = sourceY;
	}

	public double getT() {
		return t;
	}

	public void setT(double t) {
		this.t = t;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
