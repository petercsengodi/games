package hu.csega.editors.anm.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class AnimatorModel implements JSONString {

	String title;
	final List<AnimatorScene> scenes = new ArrayList<>();

	public AnimatorModel() {
	}

	public AnimatorModel(String title, List<AnimatorScene> scenes) {
		this.title = title;

		for(AnimatorScene scene : scenes)
			this.scenes.add(new AnimatorScene(scene));
	}

	public AnimatorModel(AnimatorModel other) {
		this();
		copyFrom(other);
	}

	public AnimatorModel(String s) throws JSONException {
		this(new JSONObject(s));
	}

	public AnimatorModel(JSONObject json) throws JSONException {
		this();
		copyFrom(json);
	}

	public void copyFrom(AnimatorModel other) {
		this.title = other.title;

		this.scenes.clear();
		for(AnimatorScene scene : other.scenes)
			this.scenes.add(new AnimatorScene(scene));
	}

	public void copyFrom(JSONObject json) throws JSONException {
		this.title = json.optString("title");

		this.scenes.clear();
		JSONArray array = json.optJSONArray("scenes");
		if(array != null && array.length() > 0) {
			int len = array.length();
			for(int i = 0; i < len; i++)
				this.scenes.add(new AnimatorScene(array.getJSONObject(i)));
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
		json.put("title", title);

		JSONArray array = new JSONArray();
		for(AnimatorScene scene : scenes)
			array.put(scene.toJSONObject());

		json.put("scenes", array);
		return json;
	}

	@Override
	public String toString() {
		return toJSONString();
	}

}
