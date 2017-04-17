package hu.csega.games.engine.g2d;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

	public void move(double delta) {
		rotationSpeed += rotationAcceleration;
		rotationAngle += rotationSpeed * delta;
		double accelerationX = movementAcceleration.x * delta;
		double accelerationY = movementAcceleration.y * delta;
		movementSpeed.x += accelerationX * Math.cos(rotationAngle) + accelerationY * Math.sin(rotationAngle);
		movementSpeed.y += accelerationX * Math.sin(rotationAngle) + accelerationY * Math.cos(rotationAngle);
		movementPosition.x += movementSpeed.x * delta;
		movementPosition.y += movementSpeed.y * delta;
	}

	public void checkConstraints() {
	}

	public List<GameHitShape> getHitShapes() {
		return hitShapes;
	}

	public GamePoint movementPosition = new GamePoint();
	public GameVector movementSpeed = new GameVector();
	public GameVector movementAcceleration = new GameVector();
	public double movementInertia;
	public double rotationAngle;
	public double rotationSpeed;
	public double rotationAcceleration;
	public double rotationInertia;

	public List<GameHitShape> hitShapes = new ArrayList<>();
	public GameHitBox outerBox = new GameHitBox();
}
