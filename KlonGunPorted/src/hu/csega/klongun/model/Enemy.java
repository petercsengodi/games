package hu.csega.klongun.model;

public class Enemy implements PoolItem {

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
		x = y = xSpeed = ySpeed = late = 0;
		kind = life = item = time = 0;
	}

	public int x;
	public int y;
	public int xSpeed;
	public int ySpeed;
	public int kind;
	public int life;
	public int late;
	public int item;
	public int time;

	private int index;
}
