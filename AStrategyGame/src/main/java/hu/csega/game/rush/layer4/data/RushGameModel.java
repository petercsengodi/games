package hu.csega.game.rush.layer4.data;

import hu.csega.games.engine.g3d.GameHitBox;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.g3d.GameSelectionLine;
import hu.csega.toolshed.framework.ToolModel;

public class RushGameModel implements ToolModel {

	private GameSelectionLine selectionLine = new GameSelectionLine();

	public RushTerrain[][] getTerrainTiles() {
		return terrainTiles;
	}

	public GameSelectionLine getGameSelectionLine() {
		return selectionLine;
	}

	public void select() {
		float t = Float.POSITIVE_INFINITY;
		int selectedX = -1;
		int selectedZ = -1;

		for(int x = 0 ; x < TERRAIN_WIDTH; x++) {
			for(int z = 0; z < TERRAIN_HEIGHT; z++) {
				RushTerrain terrain = terrainTiles[x][z];
				GameHitBox hitBox = terrain.getHitBox();
				GameObjectPlacement placement = terrain.getPlacement();

				float tp = selectionLine.intersection(placement, hitBox);
				if(tp < t) {
					t = tp;
					selectedX = x;
					selectedZ = z;
				}
			}
		}

		System.out.println("T: " + t + " SelectedX: " + selectedX + " SelectedZ: " + selectedZ);
	}

	public void init(GameModelStore store) {
		GameHitBox hitBox = new GameHitBox(-50, 0, -50, 50, 10, 50);

		sand = RushBox.mesh(store, "res/rush/terrain/sand.jpg", -50, 0, -50, 50, 10, 50);
		stone = RushBox.mesh(store, "res/rush/terrain/stone.jpg", -50, 0, -50, 50, 10, 50);
		water = RushBox.mesh(store, "res/rush/terrain/water.jpg", -50, 0, -50, 50, 10, 50);

		for(int x = 0 ; x < TERRAIN_WIDTH; x++) {
			for(int z = 0; z < TERRAIN_HEIGHT; z++) {
				GameObjectHandler obj = ((x + z) % 2 == 0 ? sand : stone);
				terrainTiles[x][z] = new RushTerrain(obj, hitBox, x * 100, 0, z * 100);
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
