package hu.csega.editors.anm.components;

import hu.csega.editors.anm.model.AnimatorModel;
import hu.csega.games.common.CommonDataTransformer;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelBuilder;

public interface ComponentOpenGLTransformer extends CommonDataTransformer<AnimatorModel, GameModelBuilder> {

	void setGameEngineFacade(GameEngineFacade facade);

}
