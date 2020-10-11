package hu.csega.game.rush.layer4.data;

import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.toolshed.framework.ToolModel;

public class RushGameModel implements ToolModel {

	public RushTerrain[][] getTerrainTiles() {
		return terrainTiles;
	}

	public void init(GameModelStore store) {
		sand = RushBox.mesh(store, "res/rush/terrain/sand.jpg", -50, 0, -50, 50, 10, 50);
		stone = RushBox.mesh(store, "res/rush/terrain/stone.jpg", -50, 0, -50, 50, 10, 50);
		water = RushBox.mesh(store, "res/rush/terrain/water.jpg", -50, 0, -50, 50, 10, 50);

		for(int x = 0 ; x < TERRAIN_WIDTH; x++) {
			for(int z = 0; z < TERRAIN_HEIGHT; z++) {
				GameObjectHandler obj = ((x + z) % 2 == 0 ? sand : stone);
				terrainTiles[x][z] = new RushTerrain(obj, x * 100, 0, z * 100);
			}
		}
	}

	private RushTerrain[][] terrainTiles = new RushTerrain[TERRAIN_WIDTH][TERRAIN_HEIGHT];

	private GameObjectHandler sand;
	private GameObjectHandler stone;
	private GameObjectHandler water;

	private static final int TERRAIN_WIDTH = 100;
	private static final int TERRAIN_HEIGHT = 100;

}
