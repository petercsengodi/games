package hu.csega.editors.anm.components;

import java.util.List;

import hu.csega.editors.anm.model.Animation;
import hu.csega.editors.anm.ui.AnimatorPartListItem;
import hu.csega.games.common.CommonDataTransformer;
import hu.csega.games.common.CommonDrain;
import hu.csega.games.common.CommonSource;

public interface ComponentExtractPartList extends CommonDataTransformer<Animation, List<AnimatorPartListItem>>,
CommonSource<List<AnimatorPartListItem>>, CommonDrain<Animation> {

}
