package hu.csega.editors.anm.transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hu.csega.editors.anm.components.ComponentExtractPartList;
import hu.csega.editors.anm.components.ComponentPartListView;
import hu.csega.editors.anm.model.Animation;
import hu.csega.editors.anm.model.AnimationPart;
import hu.csega.editors.anm.ui.AnimatorPartListItem;
import hu.csega.games.units.UnitStore;

public class AnimatorExtractPartList implements ComponentExtractPartList {

	private ComponentPartListView view;
	private List<AnimatorPartListItem> items;

	@Override
	public List<AnimatorPartListItem> transform(Animation animation) {
		if(animation == null)
			return null;

		Map<Integer, AnimationPart> parts = animation.getParts();
		if(parts == null || parts.size() == 0)
			return null;

		items = new ArrayList<>();
		for(Map.Entry<Integer, AnimationPart> entry : parts.entrySet()) {
			int index = entry.getKey();
			String name = entry.getValue().getName();

			AnimatorPartListItem item = new AnimatorPartListItem();
			item.setIndex(index);
			item.setName(name);

			items.add(item );
		}

		return items;
	}

	@Override
	public void accept(Animation animation) {
		if(view == null) {
			view = UnitStore.instance(ComponentPartListView.class);
			if(view == null) {
				return;
			}
		}

		items = transform(animation);
		view.accept(items);
	}

	@Override
	public List<AnimatorPartListItem> provide() {
		return items;
	}

}
