package hu.csega.games.engine.g3d;

import java.io.Serializable;

public class GameObjectDirection implements Serializable {

	public float x;
	public float y;
	public float z;

	public GameObjectDirection() {
	}

	public GameObjectDirection(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void copyValuesFrom(GameObjectDirection other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	@Override
	public String toString() {
		return "[" + x + ';' + y + ';' + z + ']';
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameObjectDirection other = (GameObjectDirection) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;

}
