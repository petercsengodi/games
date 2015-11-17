package hu.csega.alpoc.game.logic.bullets;

import java.awt.Graphics2D;

import hu.csega.alpoc.game.graphics.TeamColors;

public class Granade extends Bullet {

	@Override
	public void draw(Graphics2D g) {
		g.setColor(TeamColors.get(team));
		g.fillOval(-1, -1, 3, 3);
	}
	
}
