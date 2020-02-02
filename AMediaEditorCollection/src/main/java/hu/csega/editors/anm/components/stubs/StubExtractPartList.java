package hu.csega.editors.anm.components.stubs;

import java.util.ArrayList;
import java.util.List;

import hu.csega.editors.anm.components.ComponentExtractPartList;
import hu.csega.editors.anm.model.Animation;
import hu.csega.editors.anm.ui.AnimatorPartList;
import hu.csega.editors.anm.ui.AnimatorPartListItem;
import hu.csega.games.units.UnitStore;

public class StubExtractPartList implements ComponentExtractPartList {

	private AnimatorPartList partList;
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
		if(partList == null) {
			partList = UnitStore.instance(AnimatorPartList.class);
		}

		partList.accept(items);
	}

}
