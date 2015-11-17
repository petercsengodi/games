package hu.csega.alpoc.game.logic.controllables;

import hu.csega.alpoc.game.general.Vector3d;
import hu.csega.alpoc.game.logic.Controllable;
import hu.csega.alpoc.game.logic.legs.Spider;
import hu.csega.alpoc.game.logic.weapons.Rifle;

public class Scout extends Controllable {

	public Scout() {
		this(0, new Vector3d(0, 0, 0), 0.0);
	}
	
	public Scout(int team, Vector3d location, double health) {
		super(new Spider(), new Rifle());
		this.team = team;
		this.position.location = location;
		this.health = health;
	}
	
}
