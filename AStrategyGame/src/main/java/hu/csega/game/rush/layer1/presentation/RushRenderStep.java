package hu.csega.game.rush.layer1.presentation;

import hu.csega.game.rush.layer4.data.RushGameModel;
import hu.csega.game.rush.layer4.data.RushTerrain;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectDirection;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.games.engine.intf.GameGraphics;

public class RushRenderStep implements GameEngineCallback {

	private GameObjectPosition cameraPosition = new GameObjectPosition(0f, 0f, 0f);
	private GameObjectPosition cameraTarget = new GameObjectPosition(0f, 0f, 0f);
	private GameObjectDirection cameraUp = new GameObjectDirection(0f, 1f, 0f);
	private GameObjectPlacement cameraPlacement = new GameObjectPlacement();

	private RushMouseController mouseController = null;

	public void setMouseController(RushMouseController mouseController) {
		this.mouseController = mouseController;
	}

	@Override
	public Object call(GameEngineFacade facade) {
		RushGameModel model = (RushGameModel) facade.model();
		if(model == null)
			return facade;

		GameGraphics g = facade.graphics();

		cameraPosition.x = 0f;
		cameraPosition.y = -1000f;
		cameraPosition.z = 1000f;

		cameraTarget.x = 0f;
		cameraTarget.y = 0f;
		cameraTarget.z = 0f;

		cameraUp.x = 0f;
		cameraUp.y = -1f;
		cameraUp.z = -1f;

		cameraPlacement.setPositionTargetUp(cameraPosition, cameraTarget, cameraUp);
		g.placeCamera(cameraPlacement);

		g.setBaseMatricesAndViewPort(model.getGameSelectionLine());

		RushTerrain[][] terrainTiles = model.getTerrainTiles();
		for(int x = 0; x < terrainTiles.length; x++) {
			RushTerrain[] terrainTilesColumn = terrainTiles[x];
			for(int y = 0; y < terrainTilesColumn.length; y++) {
				RushTerrain terrainTile = terrainTilesColumn[y];
				if(terrainTile == null)
					continue;

				GameObjectHandler obj = terrainTile.getGameObjectHandler();
				GameObjectPlacement placement = terrainTile.getPlacement();
				g.drawModel(obj, placement);
			}
		}


		return facade;
	}

}
