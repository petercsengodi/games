package hu.csega.games.engine.g3d;

public class GameObjectHandler {

	private GameObjectType type;
	private long identifier;

	public GameObjectHandler(GameObjectType type, long identifier) {
		this.type = type;
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return type.name() + '#' + identifier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (identifier ^ (identifier >>> 32));
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
		GameObjectHandler other = (GameObjectHandler) obj;
		if (identifier != other.identifier)
			return false;
		return true;
	}

}
