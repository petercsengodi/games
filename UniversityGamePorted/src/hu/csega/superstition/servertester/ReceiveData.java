package hu.csega.superstition.servertester;

import hu.csega.superstition.gamelib.network.GameObjectData;

public interface ReceiveData {
	public void call(GameObjectData data);
}
