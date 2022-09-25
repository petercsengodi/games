package hu.csega.editors.anm.layer2.transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hu.csega.editors.anm.components.ComponentExtractPartList;
import hu.csega.editors.anm.components.ComponentPartListView;
import hu.csega.editors.anm.layer1.swing.components.partlist.AnimatorPartListItem;
import hu.csega.editors.anm.layer4.data.animation.Animation;
import hu.csega.editors.anm.layer4.data.animation.AnimationPart;
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
			AnimationPart value = entry.getValue();
			String name = value.getName();
			String mesh = value.getMesh();

			AnimatorPartListItem item = new AnimatorPartListItem();
			item.setIndex(index);
			item.setName(name);
			item.setMesh(mesh);

			items.add(item);
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
