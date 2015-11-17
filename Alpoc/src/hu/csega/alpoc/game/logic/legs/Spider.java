package hu.csega.alpoc.game.logic.legs;

import hu.csega.alpoc.game.graphics.TeamColors;

import java.awt.Graphics2D;

public class Spider extends Leg {

	@Override // X
	public void render(Graphics2D g, double t) {
		g.setColor(TeamColors.get(team));
		g.drawLine(-10 + (int)(-5 * Math.sin(t + Math.PI)), -10 + (int)(-5 * Math.sin(t)), 0, 0);
		g.drawLine(10 + (int)(5 * Math.sin(t + Math.PI / 2)), 10 + (int)(5 * Math.sin(t - Math.PI / 2)), 0, 0);
		g.drawLine(-10 + (int)(-5 * Math.cos(t + Math.PI / 4)), 10 + (int)(5 * Math.cos(t - 3 * Math.PI / 4)), 0, 0);
		g.drawLine(10 + (int)(5 * Math.cos(t + 3 * Math.PI / 4)), -10 + (int)(-5 * Math.cos(t - Math.PI / 4)), 0, 0);
	}

}
