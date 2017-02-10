package hu.csega.klongun.model;

import hu.csega.klongun.KlonGun;

public class Bullet implements PoolItem {

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public void clear() {
		x = y = xSpeed = ySpeed = 0;
		kind = side = damage = dyingTime = 0;
		hit = false;
		a1 = a2 = a3 = a4 = a5 = a6 = a7 = a8 = 0f;
	}

	private int index;

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
