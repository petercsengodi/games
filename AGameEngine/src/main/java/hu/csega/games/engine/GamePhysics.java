package hu.csega.games.engine;

public interface GamePhysics {

	void call(long nanoTimeNow, long nanoTimeLastTime);
	void setGameControl(GameControl control);

}
