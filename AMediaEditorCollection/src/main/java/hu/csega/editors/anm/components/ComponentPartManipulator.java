package hu.csega.editors.anm.components;

import hu.csega.editors.anm.model.AnimatorModel;
import hu.csega.games.common.CommonDataManipulator;

public interface ComponentPartManipulator extends CommonDataManipulator<AnimatorModel> {

	void addNewPart(String filename);

}
