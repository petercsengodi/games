package hu.csega.alpoc.game.logic;

import java.awt.Graphics2D;

import hu.csega.alpoc.game.graphics.Animable;
import hu.csega.alpoc.game.graphics.TeamColors;

public abstract class GameObject extends Animable { 

	public Position position;
	public double health;
	public int team;
	
	public GameObject() {
		position = new Position();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(TeamColors.get(team));
		g.fillOval(-5, -5, 10, 10);
	}
	
}
