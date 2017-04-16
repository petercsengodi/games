package hu.csega.games.uncharted.play;

import java.util.ArrayList;
import java.util.List;

import hu.csega.games.engine.GameField;
import hu.csega.games.uncharted.objects.UnchartedEnemy;
import hu.csega.games.uncharted.objects.UnchartedEnemyBullet;
import hu.csega.games.uncharted.objects.UnchartedPlayer;
import hu.csega.games.uncharted.objects.UnchartedPlayerBullet;

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
