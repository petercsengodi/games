package hu.csega.editors.anm.components.stubs;

import java.util.ArrayList;
import java.util.List;

import hu.csega.editors.anm.components.ComponentExtractPartList;
import hu.csega.editors.anm.components.ComponentPartListView;
import hu.csega.editors.anm.layer1.swing.components.partlist.AnimatorPartListItem;
import hu.csega.editors.anm.layer4.data.animation.Animation;
import hu.csega.games.units.UnitStore;

public class StubExtractPartList implements ComponentExtractPartList {

	private ComponentPartListView view;
	private List<AnimatorPartListItem> items;

	public StubExtractPartList() {
		this.items = new ArrayList<>();

		for(int i = 0; i < 100; i++) {
			String name = "Part " + i;

			AnimatorPartListItem item = new AnimatorPartListItem();
			item.setIndex(i);
			item.setName(name);
			this.items.add(item);
		}
	}

	@Override
	public List<AnimatorPartListItem> transform(Animation arg0) {
		return items;
	}

	@Override
	public List<AnimatorPartListItem> provide() {
		return items;
	}

	@Override
	public void accept(Animation arg0) {
		if(view == null) {
			view = UnitStore.instance(ComponentPartListView.class);
			if(view == null) {
				return;
			}
		}

		view.accept(items);
	}

}
