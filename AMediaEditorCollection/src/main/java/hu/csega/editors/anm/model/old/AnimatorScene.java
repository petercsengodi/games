package hu.csega.editors.anm.model.old;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class AnimatorScene implements JSONString {

	public double timeBefore;
	public double timeAfter;
	public final List<String> roots = new ArrayList<>();
	public final Map<String, AnimatorPartPlacement> partPlacements = new HashMap<>();

	public AnimatorScene() {
		this.timeBefore = 0.0;
		this.timeAfter = 0.0;
	}

	public AnimatorScene(double timeBefore, double timeAfter, List<String> roots, Map<String, AnimatorPartPlacement> parts) {
		this.timeBefore = timeBefore;
		this.timeAfter = timeAfter;
		this.roots.addAll(roots);

		for(Entry<String, AnimatorPartPlacement> entry : parts.entrySet())
			this.partPlacements.put(entry.getKey(), new AnimatorPartPlacement(entry.getValue()));
	}

	public AnimatorScene(AnimatorScene other) {
		this();
		copyFrom(other);
	}

	public AnimatorScene(String s) throws JSONException {
		this(new JSONObject(s));
	}

	public AnimatorScene(JSONObject json) throws JSONException {
		this();
		copyFrom(json);
	}

	public void copyFrom(AnimatorScene other) {
		this.timeBefore = other.timeBefore;
		this.timeAfter = other.timeAfter;
		this.roots.clear();
		this.roots.addAll(other.roots);

		this.partPlacements.clear();
		for(Entry<String, AnimatorPartPlacement> entry : other.partPlacements.entrySet())
			this.partPlacements.put(entry.getKey(), new AnimatorPartPlacement(entry.getValue()));
	}

	public void copyFrom(JSONObject json) throws JSONException {
		this.timeBefore = json.optDouble("timeBefore", 0.0);
		this.timeAfter = json.optDouble("timeAfter", 0.0);

		this.roots.clear();
		JSONArray array = json.optJSONArray("roots");
		if(array != null && array.length() > 0) {
			int len = array.length();
			for(int i = 0; i < len; i++)
				this.roots.add(array.getString(i));
		}

		this.partPlacements.clear();
		JSONObject map = json.optJSONObject("parts");
		if(map != null && map.length() > 0) {
			Iterator<?> keys = map.keys();
			while(keys.hasNext()) {
				String key = keys.next().toString();
				JSONObject value = map.getJSONObject(key);
				this.partPlacements.put(key, /* new AnimatorPartPlacement(value) */ null);
			}
		}
	}

	@Override
	public String toJSONString() {
		try {
			JSONObject json = toJSONObject();
			return json.toString();
		} catch (JSONException e) {
			return "{ \"error\": \"toString\"}";
		}
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("timeBefore", timeBefore);
		json.put("timeAfter", timeAfter);

		JSONArray array = new JSONArray();
		for(String root : roots)
			array.put(root);

		JSONObject map = new JSONObject();
		for(Entry<String, AnimatorPartPlacement> entry : partPlacements.entrySet())
			/* map.put(entry.getKey(), entry.getValue().toJSONObject()) */;

		json.put("roots", array);
		json.put("parts", map);
		return json;
	}

	@Override
	public String toString() {
		return toJSONString();
	}

	public void add(String string, AnimatorPartPlacement animatorPart) {
		this.roots.add(string);
		AnimatorPartPlacement part = new AnimatorPartPlacement(animatorPart);
		// part.name = string;
		this.partPlacements.put(string, part);
	}
}
