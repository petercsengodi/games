package hu.csega.klongun.model;

public class Player {

	public int x;
	public int y;
	public int shipSpriteIndex;
	public int healthPoints;
	public int fire;
	public int shootingDelay;
	public boolean cantBeHurt;
	public int cantBeHurtTime;
	public boolean defenseFire;
	public int[] weapons = new int[5]; // weapon statuses
	public int dyingAnimation;
	public int protection;
	public int numberOfLives;


}
