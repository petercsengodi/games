package hu.csega.klongun.model;

import hu.csega.klongun.KlonGun;

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
	public float a1, a2, a3, a4, a5, a6, a7, a8;

	public void setHit(int x, int y, int dyingTime) {
		this.hit = true;
		this.x = x;
		this.y = y;
		this.dyingTime = dyingTime;

		a1 = KlonGun.RND.nextFloat() + 0.5f;
		a2 = KlonGun.RND.nextFloat() + 0.5f;
		a3 = KlonGun.RND.nextFloat() + 0.5f;
		a4 = KlonGun.RND.nextFloat() + 0.5f;
		a5 = KlonGun.RND.nextFloat() + 0.5f;
		a6 = KlonGun.RND.nextFloat() + 0.5f;
		a7 = KlonGun.RND.nextFloat() + 0.5f;
		a8 = KlonGun.RND.nextFloat() + 0.5f;
	}

	public static final int MAX_DYING_TIME = 50;
}
