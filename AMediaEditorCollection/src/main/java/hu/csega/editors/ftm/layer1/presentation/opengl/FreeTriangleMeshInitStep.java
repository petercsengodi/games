package hu.csega.editors.ftm.layer1.presentation.opengl;

import hu.csega.editors.FreeTriangleMeshToolStarter;
import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;

public class FreeTriangleMeshInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		FreeTriangleMeshModel model = new FreeTriangleMeshModel();
		model.setTextureFilename(FreeTriangleMeshToolStarter.DEFAULT_TEXTURE_FILE);
		facade.setModel(model);
		return facade;
	}

}
