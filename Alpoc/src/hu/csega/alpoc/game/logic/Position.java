package hu.csega.alpoc.game.logic;

import hu.csega.alpoc.game.general.Vector3d;

public class Position {

	public Vector3d location;
	public Vector3d vectorFront;
	public Vector3d vectorUp;
	
	public Position() {
		location = new Vector3d();
		vectorFront = new Vector3d();
		vectorUp = new Vector3d();
	}
	
}
