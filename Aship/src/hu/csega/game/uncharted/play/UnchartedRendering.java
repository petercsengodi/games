package hu.csega.game.uncharted.play;

import java.util.List;

import hu.csega.game.uncharted.objects.UnchartedEnemy;
import hu.csega.game.uncharted.objects.UnchartedEnemyBullet;
import hu.csega.game.uncharted.objects.UnchartedPlayerBullet;
import hu.csega.game.uncharted.pics.UnchartedSprites;
import hu.csega.games.engine.GameColor;
import hu.csega.games.engine.GameGraphics;
import hu.csega.games.engine.GameHitShape;
import hu.csega.games.engine.GameObject;
import hu.csega.games.engine.GamePoint;
import hu.csega.games.engine.GameRendering;

public class UnchartedRendering implements GameRendering {

	public UnchartedRendering(UnchartedRenderingOptions options) {
		this.options = options;
	}

	@Override
	public void render(GameGraphics g) {

		GameColor red = new GameColor(255, 0, 0);
		GameColor green = new GameColor(0, 255, 0);

		GameObject player = universe.player;
		GamePoint position = player.movementPosition;

		if(options.renderHitShapes) {
			g.drawHitShape(player.outerBox, position.x, position.y, green);
		}

		g.drawSprite(UnchartedSprites.SHIP, position.x, position.y);

		for(UnchartedEnemy enemy : universe.enemies) {
			g.drawSprite(UnchartedSprites.ENEMY, enemy.movementPosition.x, enemy.movementPosition.y);
		}

		for(UnchartedPlayerBullet playerBullet : universe.playerBullets) {
			g.drawSprite(UnchartedSprites.SHIP_BULLET, playerBullet.movementPosition.x, playerBullet.movementPosition.y);
		}

		for(UnchartedEnemyBullet enemyBullet : universe.enemyBullets) {
			g.drawSprite(UnchartedSprites.ENEMY_BULLET, enemyBullet.movementPosition.x, enemyBullet.movementPosition.y);
		}

		if(options.renderHitShapes) {

			List<GameHitShape> hitShapes = player.getHitShapes();
			if(hitShapes != null) {
				for(GameHitShape shape : hitShapes)
					g.drawHitShape(shape, position.x, position.y, red);
			}
		}
	}

	public UnchartedUniverse universe;

	private UnchartedRenderingOptions options;
}
