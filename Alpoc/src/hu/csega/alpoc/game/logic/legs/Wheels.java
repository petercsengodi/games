package hu.csega.alpoc.game.logic.legs;

import hu.csega.alpoc.game.graphics.TeamColors;

import java.awt.Graphics2D;

public class Wheels extends Leg {

	@Override // O-O 
	          // O-O
	public void render(Graphics2D g, double t) {
		g.setColor(TeamColors.get(team));
		g.drawLine(-3, 5, 3, 5);
		g.drawLine(-3, -5, 3, -5);
		g.fillOval(-4, -7, 3, 5);
		g.fillOval(-4, 3, 3, 5);
		g.fillOval(2, -7, 3, 5);
		g.fillOval(2, 3, 3, 5);
	}

}
