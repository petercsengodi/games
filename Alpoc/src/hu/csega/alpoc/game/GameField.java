package hu.csega.alpoc.game;

import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;

import hu.csega.alpoc.game.general.Vector3d;
import hu.csega.alpoc.game.logic.GameObject;
import hu.csega.alpoc.game.logic.controllables.Demolisher;
import hu.csega.alpoc.game.logic.controllables.Scout;

public class GameField {

	public static Set<GameObject> gameObjects = new HashSet<>();
	
	static {
		gameObjects.add(new Scout(0, new Vector3d(50, 50, 0), 100.0));
		gameObjects.add(new Scout(0, new Vector3d(40, 50, 0), 100.0));
		gameObjects.add(new Scout(0, new Vector3d(30, 50, 0), 100.0));
		gameObjects.add(new Scout(0, new Vector3d(20, 50, 0), 80.0));
		gameObjects.add(new Demolisher(0, new Vector3d(10, 50, 0), 4500.0));
		gameObjects.add(new Demolisher(2, new Vector3d(-20, -20, 0), 5000.0));
		gameObjects.add(new Demolisher(2, new Vector3d(-40, -20, 0), 5000.0));
	}
	
	public static void drawWorld(Graphics2D g) {
		for(GameObject go : gameObjects) {
			g.translate(go.position.location.x, go.position.location.y);
			go.draw(g);
			g.translate(-go.position.location.x, -go.position.location.y);
		}
	}
}
