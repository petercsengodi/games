package hu.csega.klongun.model;

public class Bullet {
	public int x;
	public int y;
	public int xSpeed;
	public int ySpeed;
	public int kind;
	public int side;
	public int damage;
	public boolean hit;
	public int dyingTime;

	public void setHit(int x, int y, int dyingTime) {
		this.hit = true;
		this.x = x;
		this.y = y;
		this.dyingTime = dyingTime;
	}

	public static final int MAX_DYING_TIME = 50;
}
