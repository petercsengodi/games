package hu.csega.cube.editor.view;

public enum CubeEdge {

	C1(-1, -1, -1, 1, -1, -1),
	C2(-1, -1, -1, -1, -1, 1),
	C3(-1, -1, 1, 1, -1, 1),
	C4(1, -1, -1, 1, -1, 1),
	C5(-1, 1, -1, 1, 1, -1),
	C6(-1, 1, -1, -1, 1, 1),
	C7(-1, 1, 1, 1, 1, 1),
	C8(1, 1, -1, 1, 1, 1),
	C9(-1, -1, -1, -1, 1, -1),
	CA(-1, -1, 1, -1, 1, 1),
	CB(1, -1, -1, 1, 1, -1),
	CC(1, -1, 1, 1, 1, 1);

	private CubeEdge(int x1, int y1, int z1, int x2, int y2, int z2) {
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
	}

	public final int x1, y1, z1, x2, y2, z2;
}
