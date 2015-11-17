package hu.csega.alpoc.game.logic.controllables;

import java.awt.Graphics2D;

import hu.csega.alpoc.game.general.Vector3d;
import hu.csega.alpoc.game.graphics.TeamColors;
import hu.csega.alpoc.game.logic.Controllable;
import hu.csega.alpoc.game.logic.legs.Wheels;
import hu.csega.alpoc.game.logic.weapons.GranadeLauncher;

public class Demolisher extends Controllable {

	public Demolisher() {
		this(0, new Vector3d(0, 0, 0), 5000.0);
	}
	
	public Demolisher(int team, Vector3d location, double health) {
		super(new Wheels(), new GranadeLauncher());
		this.team = team;
		this.position.location = location;
		this.health = health;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(TeamColors.get(team));
		g.fillOval(-10, -10, 20, 20);
	}
	
}
