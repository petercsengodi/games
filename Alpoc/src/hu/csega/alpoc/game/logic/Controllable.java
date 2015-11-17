package hu.csega.alpoc.game.logic;

import java.awt.Graphics2D;

import hu.csega.alpoc.game.logic.legs.Leg;

public class Controllable extends GameObject {

	public Controllable(Leg leg, Weapon weapon) {
		this.leg = leg;
		this.weapon = weapon;
	}
	
	@Override
	public void render(Graphics2D g, double t) {
		leg.render(g, t);
		weapon.render(g, t);
	}
	
	private Leg leg;
	private Weapon weapon;
	
}
