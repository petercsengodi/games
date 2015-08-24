package hu.csega.alpoc.game.logic.bullets;

import java.awt.Graphics2D;

import hu.csega.alpoc.game.graphics.TeamColors;
import hu.csega.alpoc.game.logic.Bullet;

public class Granade extends Bullet {
	
	public void draw(Graphics2D g) {
		g.setColor(TeamColors.get(team));
		g.fillOval(0, 0, 3, 3);
	}
	
}
