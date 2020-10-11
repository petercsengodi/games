package hu.csega.games.engine.g3d;

public class GameHitBox {

	public GameHitBox() {
	}

	public GameHitBox(float x1, float y1, float z1, float x2, float y2, float z2) {
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;

		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
	}

	public float x1;
	public float y1;
	public float z1;

	public float x2;
	public float y2;
	public float z2;

}
