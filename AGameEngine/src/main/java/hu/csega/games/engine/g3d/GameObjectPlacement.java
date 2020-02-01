package hu.csega.games.engine.g3d;

import java.io.Serializable;

import org.joml.Matrix4f;
import org.joml.Vector4f;

public class GameObjectPlacement implements Serializable {

	public GameObjectPosition position = new GameObjectPosition(0f, 0f, 0f);
	public GameObjectPosition target = new GameObjectPosition(0f, 0f, 1f);
	public GameObjectDirection up = new GameObjectDirection(0f, 1f, 0f);
	public GameObjectScale scale = new GameObjectScale(1f, 1f, 1f);

	public void setPositionDirectionUp(GameObjectPosition position, GameObjectDirection direction, GameObjectDirection up) {
		this.position.copyValuesFrom(position);
		this.up.copyValuesFrom(up);

		this.target.x = position.x + direction.x;
		this.target.y = position.y + direction.y;
		this.target.z = position.z + direction.z;
	}

	public void setPositionTargetUp(GameObjectPosition position, GameObjectPosition target, GameObjectDirection up) {
		this.position.copyValuesFrom(position);
		this.target.copyValuesFrom(target);
		this.up.copyValuesFrom(up);
	}

	public void setTargetDirectionUp(GameObjectPosition target, GameObjectDirection direction, GameObjectDirection up) {
		this.target.copyValuesFrom(target);
		this.up.copyValuesFrom(up);

		this.position.x = target.x - direction.x;
		this.position.y = target.y - direction.y;
		this.position.z = target.z - direction.z;
	}

	public void move(float x, float y, float z) {
		position.x += x;
		position.y += y;
		position.z += z;

		target.x += x;
		target.y += y;
		target.z += z;
	}

	public void moveTo(float x, float y, float z) {
		float dx = target.x - position.x;
		float dy = target.y - position.y;
		float dz = target.z - position.z;

		position.x = x;
		position.y = y;
		position.z = z;

		target.x = dx + position.x;
		target.y = dy + position.y;
		target.z = dz + position.z;
	}

	public void calculatePosition(GameObjectPosition position) {
		position.copyValuesFrom(this.position);
	}

	public void calculateEye(GameObjectPosition eye) {
		eye.copyValuesFrom(this.position);
	}

	public void calculateCenter(GameObjectPosition center) {
		center.copyValuesFrom(this.target);
	}

	public void calculateUp(GameObjectDirection up) {
		up.copyValuesFrom(this.up);
	}

	public void calculateTarget(GameObjectPosition target) {
		target.copyValuesFrom(this.target);
	}

	public void calculateDirection(GameObjectDirection direction) {
		direction.x = this.target.x - this.position.x;
		direction.y = this.target.y - this.position.y;
		direction.z = this.target.z - this.position.z;
	}

	public void calculateBasicLookAt(Matrix4f basicLookAt) {
		basicLookAt.setLookAt(this.position.x, this.position.y, this.position.z,
				this.target.x, this.target.y, this.target.z,
				this.up.x, this.up.y, this.up.z);
	}

	/**
	 * For some reason this algorithm results in a bit more precise matrix than simply calculating inverse.
	 * Gives the same result as calculating "inverseLookAt", but I'm in a hope (or delusion) that this approach
	 * may be a little faster. I have no evidence to support that.
	 */
	public void calculateInverseLookAt(Matrix4f basicLookAt, Vector4f tmpEye, Vector4f tmpCenter, Vector4f tmpUp, Matrix4f inverseLookAt) {
		tmpEye.set(0f, 0f, 0f, 1f);
		tmpCenter.set(0f, 0f, -1f, 1f); // I don't know why -1f.
		tmpUp.set(0f, 1f, 0f, 1f);

		// We convert the signature vectors based on the basicLookAt matrix.
		basicLookAt.transform(tmpEye);
		basicLookAt.transform(tmpCenter);
		basicLookAt.transform(tmpUp);

		// Building lookAt matrix with inverse directions.
		inverseLookAt.setLookAt(tmpEye.x, tmpEye.y, tmpEye.z,
				tmpCenter.x, tmpCenter.y, tmpCenter.z,
				tmpUp.x - tmpEye.x, tmpUp.y - tmpEye.y, tmpUp.z - tmpEye.z);
	}

	public void calculateBasicScaleMatrix(Matrix4f basicScaleMatrix) {
		basicScaleMatrix.scaling(scale.x, scale.y, scale.z);
	}

	public void calculateInverseScaleMatrix(Matrix4f inverseScaleMatrix) {
		inverseScaleMatrix.scaling(1f / scale.x, 1f / scale.y, 1f / scale.z);
	}

	public void copyValuesFrom(GameObjectPlacement other) {
		this.position.copyValuesFrom(other.position);
		this.target.copyValuesFrom(other.target);
		this.up.copyValuesFrom(other.up);
		this.scale.copyValuesFrom(other.scale);
	}

	private static final long serialVersionUID = 1L;

}
