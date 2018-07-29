package hu.csega.editors.anm.model;

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

import hu.csega.editors.anm.model.parts.AnimatorPart;

public class AnimatorScene implements JSONString {

	double timeBefore;
	double timeAfter;
	final List<String> roots = new ArrayList<>();
	final Map<String, AnimatorPart> parts = new HashMap<>();

	public AnimatorScene() {
		this.timeBefore = 0.0;
		this.timeAfter = 0.0;
	}

	public AnimatorScene(double timeBefore, double timeAfter, List<String> roots, Map<String, AnimatorPart> parts) {
		this.timeBefore = timeBefore;
		this.timeAfter = timeAfter;
		this.roots.addAll(roots);

		for(Entry<String, AnimatorPart> entry : parts.entrySet())
			this.parts.put(entry.getKey(), new AnimatorPart(entry.getValue()));
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

		this.parts.clear();
		for(Entry<String, AnimatorPart> entry : other.parts.entrySet())
			this.parts.put(entry.getKey(), new AnimatorPart(entry.getValue()));
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

		this.parts.clear();
		JSONObject map = json.optJSONObject("parts");
		if(map != null && map.length() > 0) {
			Iterator<?> keys = map.keys();
			while(keys.hasNext()) {
				String key = keys.next().toString();
				JSONObject value = map.getJSONObject(key);
				this.parts.put(key, new AnimatorPart(value));
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
		for(Entry<String, AnimatorPart> entry : parts.entrySet())
			map.put(entry.getKey(), entry.getValue().toJSONObject());

		json.put("roots", array);
		json.put("parts", map);
		return json;
	}

	@Override
	public String toString() {
		return toJSONString();
	}

}
