package hu.csega.alpoc.game.logic;

import java.awt.Graphics2D;

public class Weapon extends GameObject {

	@Override
	public void render(Graphics2D g, double t) {
		draw(g);
	}
	
	public GameObject currentTarget; 
	public WeaponSchema schema;
}
