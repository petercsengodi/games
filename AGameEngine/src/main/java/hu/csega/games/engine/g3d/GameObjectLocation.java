package hu.csega.games.engine.g3d;

import java.io.Serializable;

public class GameObjectLocation implements Serializable {

	public GameObjectPosition position = new GameObjectPosition();
	public GameObjectRotation rotation = new GameObjectRotation();
	public GameObjectScale scale = new GameObjectScale(1f, 1f, 1f);

	public void calculateEye(GameObjectPosition destination) {
		destination.copyValuesFrom(position);
	}

	public void calculateCenter(GameObjectPosition destination) {
		calculateCenterDirection(destination);
		destination.x += position.x;
		destination.y += position.y;
		destination.z += position.z;
	}

	public void calculateCenterDirection(GameObjectPosition destination) {
		double f0x = 0f;
		double f0y = 0f;
		double f0z = 1f;

		double f1x = f0x * Math.cos(rotation.y) - f0z * Math.sin(rotation.y);
		double f1y = f0y;
		double f1z = f0x * Math.sin(rotation.y) + f0z * Math.cos(rotation.y);

		double f2x = f1x;
		double f2y = f1y * Math.cos(rotation.x) - f1z * Math.sin(rotation.x);
		double f2z = f1y * Math.sin(rotation.x) + f1z * Math.cos(rotation.x);

		double f3x = f2x * Math.cos(rotation.z) - f2y * Math.sin(rotation.z);
		double f3y = f2x * Math.sin(rotation.z) + f2y * Math.cos(rotation.z);
		double f3z = f2z;

		destination.x = (float)f3x;
		destination.y = (float)f3y;
		destination.z = (float)f3z;
	}

	public void calculateUpTarget(GameObjectPosition destination) {
		calculateUp(destination);
		destination.x += position.x;
		destination.y += position.y;
		destination.z += position.z;
	}

	public void calculateUp(GameObjectPosition destination) {
		double u0x = 0f;
		double u0y = 1f;
		double u0z = 0f;

		// U doesn't need to be rotated along the Y axis.
		double u1x = u0x;
		double u1y = u0y;
		double u1z = u0z;

		double u2x = u1x;
		double u2y = u1y * Math.cos(rotation.x) - u1z * Math.sin(rotation.x);
		double u2z = u1y * Math.sin(rotation.x) + u1z * Math.cos(rotation.x);

		double u3x = u2x * Math.cos(rotation.z) - u2y * Math.sin(rotation.z);
		double u3y = u2x * Math.sin(rotation.z) + u2y * Math.cos(rotation.z);
		double u3z = u2z;

		destination.x = (float)u3x;
		destination.y = (float)u3y;
		destination.z = (float)u3z;
	}

	public void copyValuesFrom(GameObjectLocation other) {
		position.copyValuesFrom(other.position);
		rotation.copyValuesFrom(other.rotation);
		scale.copyValuesFrom(other.scale);
	}

	private static final long serialVersionUID = 1L;

}
