package hu.csega.superstition.servertester;

import org.joml.Vector3f;

import hu.csega.superstition.gamelib.network.GameObjectData;

class TestMapObject extends GameObjectData {
	private Vector3f[] mem;

	public TestMapObject()
	{
		description = "Map";
		mem = new Vector3f[500];
	}

	private static final long serialVersionUID = 1L;
}