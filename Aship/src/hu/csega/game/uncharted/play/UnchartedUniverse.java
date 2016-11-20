package hu.csega.game.uncharted.play;

import java.util.ArrayList;
import java.util.List;

import hu.csega.game.engine.GameField;
import hu.csega.game.uncharted.objects.UnchartedEnemy;
import hu.csega.game.uncharted.objects.UnchartedEnemyBullet;
import hu.csega.game.uncharted.objects.UnchartedPlayer;
import hu.csega.game.uncharted.objects.UnchartedPlayerBullet;

public class UnchartedUniverse extends GameField {

	public UnchartedUniverse() {
		super(-399, 399, -299, 299);
	}

	public void init() {
		player = new UnchartedPlayer(this);

		UnchartedEnemy enemy1 = new UnchartedEnemy(this);
		enemy1.movementPosition.x = 200;
		enemy1.movementPosition.y = -200;
		enemies.add(enemy1);
	}

	public UnchartedPlayer player;
	public List<UnchartedPlayerBullet> playerBullets = new ArrayList<>();
	public List<UnchartedEnemy> enemies = new ArrayList<>();
	public List<UnchartedEnemyBullet> enemyBullets = new ArrayList<>();

}
