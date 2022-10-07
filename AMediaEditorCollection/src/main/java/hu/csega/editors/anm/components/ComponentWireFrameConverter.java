package hu.csega.editors.anm.components;

import hu.csega.editors.anm.layer1.swing.wireframe.AnimatorWireFrame;
import hu.csega.editors.anm.layer4.data.model.AnimatorModel;
import hu.csega.games.common.CommonDataTransformer;
import hu.csega.games.common.CommonDrain;
import hu.csega.games.common.CommonSource;

public interface ComponentWireFrameConverter extends CommonDataTransformer<AnimatorModel, AnimatorWireFrame>,
CommonDrain<AnimatorModel>, CommonSource<AnimatorWireFrame> {
}
