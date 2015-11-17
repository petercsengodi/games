package hu.csega.alpoc.game.logic.legs;

import hu.csega.alpoc.game.graphics.TeamColors;

import java.awt.Graphics2D;

public class Marine extends Leg {

	@Override // oOo
	public void render(Graphics2D g, double t) {
		g.setColor(TeamColors.get(team));
		g.fillOval(-2, -2, 5, 5);
		g.fillOval(-3, -2, 2, 2);
		g.fillOval(3, -2, 2, 2);
	}

}
