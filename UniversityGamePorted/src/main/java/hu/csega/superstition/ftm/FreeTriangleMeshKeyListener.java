package hu.csega.superstition.ftm;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameKeyListener;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;

public class FreeTriangleMeshKeyListener implements GameKeyListener {

	public FreeTriangleMeshKeyListener() {
	}

	@Override
	public void hit(GameEngineFacade facade, char key) {
		if(key == 27) { // escape
			FreeTriangleMeshModel model = (FreeTriangleMeshModel)facade.model();
			model.clearSelection();
			facade.window().repaintEverything();
		}

		if(key == 'd' || key == 'D' || key == 127) { // del
			FreeTriangleMeshModel model = (FreeTriangleMeshModel)facade.model();
			model.deleteVertices();
			facade.window().repaintEverything();
		}

		if(key == 't' || key == 'T') {
			FreeTriangleMeshModel model = (FreeTriangleMeshModel)facade.model();
			model.createTriangleStrip();
			facade.window().repaintEverything();
		}

		if(facade.control().isControlOn()) {
			if(key == 'z' || key == 'Z' || key == 26) {
				FreeTriangleMeshModel model = (FreeTriangleMeshModel)facade.model();
				model.undo();
				facade.window().repaintEverything();
			}

			if(key == 'y' || key == 'Y' || key == 25) {
				FreeTriangleMeshModel model = (FreeTriangleMeshModel)facade.model();
				model.redo();
				facade.window().repaintEverything();
			}
		}
	}

}
