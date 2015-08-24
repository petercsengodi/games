package hu.csega.alpoc.game.logic.controllables;

import java.awt.Graphics2D;

import hu.csega.alpoc.game.general.Vector3d;
import hu.csega.alpoc.game.graphics.TeamColors;
import hu.csega.alpoc.game.logic.Controllable;

public class Demolisher extends Controllable {

	public Demolisher() {
		this.health = 5000.0;
	}
	
	public Demolisher(int team, Vector3d location, double health) {
		this.team = team;
		this.position.location = location;
		this.health = health;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(TeamColors.get(team));
		g.fillOval(0, 0, 20, 20);
	}
	
}
