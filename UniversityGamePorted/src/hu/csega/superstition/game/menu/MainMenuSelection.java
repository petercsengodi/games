package hu.csega.superstition.game.menu;

public enum MainMenuSelection
{
	FAILURE = -1,
	NEW_GAME = 1, LOAD_MAP = 2, NETWORK_PLAY = 3,
	LOAD_GAME = 4, SAVE_GAME = 5,
	JOIN_HOST,
	PUBLISH_HOST,

	DENSE_MAP,

	QUIT_GAME, QUIT = 255 };