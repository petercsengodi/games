package hu.csega.game.rush.model;

import hu.csega.game.rush.model.objects.BoulderDashDirt;
import hu.csega.game.rush.model.objects.BoulderDashFieldObject;
import hu.csega.game.rush.model.objects.BoulderDashPlayer;
import hu.csega.game.rush.model.objects.BoulderDashWall;
import hu.csega.toolshed.framework.ToolModel;

public class BoulderDashModel implements ToolModel {

	public void init() {
		for(int x = 0; x < MAP_WIDTH; x++) {
			for(int y = 0; y < MAP_HEIGHT; y++) {
				if(x == 1 && y == 1) {
					map[y * MAP_WIDTH + x] = new BoulderDashPlayer();
				} else if(x == 0 || y == 0 || x == MAP_WIDTH - 1 || y == MAP_HEIGHT - 1) {
					map[y * MAP_WIDTH + x] = new BoulderDashWall();
				} else {
					map[y * MAP_WIDTH + x] = new BoulderDashDirt();
				}
			}
		}
	}

	public static final int MAP_WIDTH = 50;
	public static final int MAP_HEIGHT = 20;
	public BoulderDashFieldObject[] map = new BoulderDashFieldObject[MAP_WIDTH * MAP_HEIGHT];

}
