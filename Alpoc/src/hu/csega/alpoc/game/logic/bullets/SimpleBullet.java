package hu.csega.alpoc.game.logic.bullets;

import java.awt.Graphics2D;

import hu.csega.alpoc.game.graphics.TeamColors;

public class SimpleBullet extends Bullet {
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(TeamColors.get(team));
		g.fillOval(0, 0, 1, 1);
	}

}
