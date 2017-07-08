package hu.csega.games.engine.intf;

/**
 * Steps defined here are the key points in the Game Engine's life cycle,
 * where the client application may interact with the engine.
 */
public enum GameEngineStep {
	INIT,
	BUILD,
	MODIFY,
	RENDER,
	CONTROL,
	DISPOSE
}
