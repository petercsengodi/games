package hu.csega.alpoc.game.logic.controllables;

import java.awt.Graphics2D;

import hu.csega.alpoc.game.general.Vector3d;
import hu.csega.alpoc.game.graphics.TeamColors;
import hu.csega.alpoc.game.logic.Controllable;

public class Scout extends Controllable {

	public Scout() {
		this.health = 100.0;
	}
	
	public Scout(int team, Vector3d location, double health) {
		this.team = team;
		this.position.location = location;
		this.health = health;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(TeamColors.get(team));
		g.fillOval(0, 0, 10, 10);
	}
	
}
