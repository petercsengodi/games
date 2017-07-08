package hu.csega.games.uncharted.play;

import java.util.ArrayList;
import java.util.List;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g2d.GameColor;
import hu.csega.games.engine.g2d.GameHitShape;
import hu.csega.games.engine.g2d.GameObject;
import hu.csega.games.engine.g2d.GamePoint;
import hu.csega.games.engine.intf.GameControl;
import hu.csega.games.engine.intf.GameGraphics;
import hu.csega.games.engine.intf.GameKeyListener;
import hu.csega.games.engine.intf.GameTimer;
import hu.csega.games.uncharted.objects.UnchartedEnemy;
import hu.csega.games.uncharted.objects.UnchartedEnemyBullet;
import hu.csega.games.uncharted.objects.UnchartedField;
import hu.csega.games.uncharted.objects.UnchartedPlayer;
import hu.csega.games.uncharted.objects.UnchartedPlayerBullet;
import hu.csega.games.uncharted.pics.UnchartedSprites;

public class UnchartedModel implements GameKeyListener {

	public UnchartedField field;

	private UnchartedRenderingOptions options;

	public UnchartedPlayer player;
	public List<UnchartedPlayerBullet> playerBullets = new ArrayList<>();
	public List<UnchartedEnemy> enemies = new ArrayList<>();
	public List<UnchartedEnemyBullet> enemyBullets = new ArrayList<>();

	private double verticalForce = 200;
	private double horizontalForce = 200;

	public UnchartedModel(UnchartedRenderingOptions options) {
		this.options = options;
	}

	public void init() {
		field = new UnchartedField();
		player = new UnchartedPlayer(field);

		UnchartedEnemy enemy1 = new UnchartedEnemy(field);
		enemy1.movementPosition.x = 200;
		enemy1.movementPosition.y = -200;
		enemies.add(enemy1);
	}

	@Override
	public void hit(GameEngineFacade facade, char key) {
	}

	public void call(GameEngineFacade facade) {
		GameTimer timer = facade.timer();
		long nanoTimeNow = timer.getNanoTimeNow();
		long nanoTimeLastTime = timer.getNanoTimeOnLastCall();

		GameControl control = facade.control();
		if(control == null)
			return;

		double delta = (nanoTimeNow - nanoTimeLastTime) / 1_000_000_000.0;

		if(control.isUpOn() && !control.isDownOn())
			player.movementAcceleration.y = -verticalForce;
		else if(control.isDownOn() && !control.isUpOn())
			player.movementAcceleration.y = verticalForce;
		else
			player.movementAcceleration.y = 0;

		if(control.isLeftOn() && !control.isRightOn())
			player.movementAcceleration.x = -horizontalForce;
		else if(control.isRightOn() && !control.isLeftOn())
			player.movementAcceleration.x = horizontalForce;
		else
			player.movementAcceleration.x = 0;

		player.move(delta);
		player.checkConstraints();

		for(GameObject obj : playerBullets) {
			obj.move(delta);
			obj.checkConstraints();
		}

		if(player.timeBeforeShoot > 0.0) {
			player.timeBeforeShoot -= delta;
		}

		if(player.timeBeforeShoot <= 0.0 && control.isControlOn()) {
			UnchartedPlayerBullet bullet = new UnchartedPlayerBullet(field);
			bullet.movementPosition.x = player.movementPosition.x + player.outerBox.maxX;
			bullet.movementPosition.y = player.movementPosition.y + (player.outerBox.minY + player.outerBox.maxY) / 2;
			bullet.movementSpeed.x = 400;
			playerBullets.add(bullet);
			player.timeBeforeShoot = 0.15;
		}
	}

	public void render(GameEngineFacade facade) {
		GameGraphics g = facade.graphics();

		GameColor red = new GameColor(255, 0, 0);
		GameColor green = new GameColor(0, 255, 0);

		GamePoint position = player.movementPosition;

		if(options.renderHitShapes) {
			g.drawHitShape(player.outerBox, position.x, position.y, green);
		}

		g.drawSprite(UnchartedSprites.SHIP, position.x, position.y);

		for(UnchartedEnemy enemy : enemies) {
			g.drawSprite(UnchartedSprites.ENEMY, enemy.movementPosition.x, enemy.movementPosition.y);
		}

		for(UnchartedPlayerBullet playerBullet : playerBullets) {
			g.drawSprite(UnchartedSprites.SHIP_BULLET, playerBullet.movementPosition.x, playerBullet.movementPosition.y);
		}

		for(UnchartedEnemyBullet enemyBullet : enemyBullets) {
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

}
