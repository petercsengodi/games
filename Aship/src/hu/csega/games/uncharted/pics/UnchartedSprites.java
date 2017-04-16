package hu.csega.games.uncharted.pics;

import hu.csega.games.engine.impl.GameSprite;
import hu.csega.games.engine.impl.GameSpriteLibrary;

public class UnchartedSprites {

	public static GameSprite SHIP = GameSpriteLibrary.load(UnchartedSprites.class, "ship.png");
	public static GameSprite SHIP_BULLET = GameSpriteLibrary.load(UnchartedSprites.class, "ship_bullet.png");
	public static GameSprite ENEMY = GameSpriteLibrary.load(UnchartedSprites.class, "enemy.png");
	public static GameSprite ENEMY_BULLET = GameSpriteLibrary.load(UnchartedSprites.class, "enemy_bullet.png");

}
