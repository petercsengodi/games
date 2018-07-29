package hu.csega.editors.anm.model.parts;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class AnimatorPart implements JSONString {

	String name;
	final AnimatorPartModel model;
	final AnimatorPosition origin;
	final List<AnimatorLocation> joints;

	public AnimatorPart() {
		this.name = "id-" + System.currentTimeMillis();
		this.model = new AnimatorPartModel();
		this.origin = new AnimatorPosition();
		this.joints = new ArrayList<>();
	}

	public AnimatorPart(String name, AnimatorPartModel model, AnimatorPosition origin, List<AnimatorLocation> joints) {
		this.name = name;
		this.model = new AnimatorPartModel(model);
		this.origin = new AnimatorPosition(origin);

		this.joints = new ArrayList<>();
		for(AnimatorLocation joint : joints)
			this.joints.add(new AnimatorLocation(joint));
	}

	public AnimatorPart(AnimatorPart other) {
		this();
		copyFrom(other);
	}

	public AnimatorPart(String s) throws JSONException {
		this(new JSONObject(s));
	}

	public AnimatorPart(JSONObject json) throws JSONException {
		this();
		copyFrom(json);
	}

	public void copyFrom(AnimatorPart other) {
		this.name = other.name;
		this.model.copyFrom(other.model);
		this.origin.copyFrom(other.origin);

		this.joints.clear();
		for(AnimatorLocation joint : other.joints)
			this.joints.add(new AnimatorLocation(joint));
	}

	public void copyFrom(JSONObject json) throws JSONException {
		this.name = json.optString("name");
		if(this.name == null || this.name.length() == 0)
			this.name = "id-" + System.currentTimeMillis();

		this.model.copyFrom(json.getJSONObject("model"));
		this.origin.copyFrom(json.getJSONObject("origin"));

		this.joints.clear();
		JSONArray array = json.getJSONArray("joints");
		if(array != null && array.length() > 0) {
			int len = array.length();
			for(int i = 0; i < len; i++)
				this.joints.add(new AnimatorLocation(array.getJSONObject(i)));
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
		json.put("name", name);
		json.put("model", model.toJSONObject());
		json.put("origin", origin.toJSONObject());

		JSONArray array = new JSONArray();
		for(AnimatorLocation joint : joints)
			array.put(joint.toJSONObject());

		json.put("jonts", array);
		return json;
	}

	@Override
	public String toString() {
		return toJSONString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnimatorPart other = (AnimatorPart) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
