package hu.csega.editors.anm.layer4.data.animation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Animation implements Serializable {

	private Map<Integer, AnimationPart> parts;
	private List<AnimationScene> scenes;
	private int maxPartIndex;

	public Map<Integer, AnimationPart> getParts() {
		return parts;
	}

	public void setParts(Map<Integer, AnimationPart> parts) {
		this.parts = parts;
	}

	public List<AnimationScene> getScenes() {
		return scenes;
	}

	public void setScenes(List<AnimationScene> scenes) {
		this.scenes = scenes;
	}

	public int getMaxPartIndex() {
		return maxPartIndex;
	}

	public void setMaxPartIndex(int maxPartIndex) {
		this.maxPartIndex = maxPartIndex;
	}

	public void cleanUpScenes() {
		if(scenes == null) {
			scenes = new ArrayList<AnimationScene>();
		}

		if(scenes.size() == 0) {
			scenes.add(new AnimationScene());
		}

		for(AnimationScene scene : scenes) {
			Map<Integer, AnimationScenePart> map = scene.getSceneParts();
			if(map == null) {
				map = new TreeMap<Integer, AnimationScenePart>();
				scene.setSceneParts(map);
			}

			// Clear info for indexes that don't exist anymore.
			Iterator<Entry<Integer, AnimationScenePart>> entries = map.entrySet().iterator();
			while(entries.hasNext()) {
				Entry<Integer, AnimationScenePart> entry = entries.next();
				if(!parts.containsKey(entry.getKey())) {
					entries.remove();
				}
			}

			// Add info object for indexes that now exist, but not contained by the scenes.
			Iterator<Integer> indexes = parts.keySet().iterator();
			while(indexes.hasNext()) {
				Integer key = indexes.next();
				if(!map.containsKey(key)) {
					AnimationScenePart value = new AnimationScenePart();
					value.setModelTransformation(new AnimationTransformation());
					value.setPartTransformation(new AnimationTransformation());
					value.setVisible(true);
					map.put(key, value);
				}
			}

		}

	}

	private static final long serialVersionUID = 1L;

}
