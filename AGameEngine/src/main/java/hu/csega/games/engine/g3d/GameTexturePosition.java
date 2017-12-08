package hu.csega.games.engine.g3d;

import java.io.Serializable;

public class GameTexturePosition implements Serializable {

	public float x;
	public float y;

	public GameTexturePosition() {
	}

	public GameTexturePosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void copyValuesFrom(GameTexturePosition other) {
		this.x = other.x;
		this.y = other.y;
	}

	@Override
	public String toString() {
		return "[" + x + ';' + y  + ']';
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
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
		GameTexturePosition other = (GameTexturePosition) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;
}
