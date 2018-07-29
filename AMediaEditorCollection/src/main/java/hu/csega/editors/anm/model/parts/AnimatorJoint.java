package hu.csega.editors.anm.model.parts;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class AnimatorJoint implements JSONString {

	String name;
	String part;
	final AnimatorLocation location;

	public AnimatorJoint() {
		this.name = null;
		this.part = null;
		this.location = new AnimatorLocation();
	}

	public AnimatorJoint(String name, String filename, AnimatorLocation location) {
		this.name = name;
		this.part = filename;
		this.location = new AnimatorLocation(location);
	}

	public AnimatorJoint(AnimatorJoint other) {
		this();
		copyFrom(other);
	}

	public AnimatorJoint(String s) throws JSONException {
		this(new JSONObject(s));
	}

	public AnimatorJoint(JSONObject json) throws JSONException {
		this();
		copyFrom(json);
	}

	public void copyFrom(AnimatorJoint other) {
		this.name = other.name;
		this.part = other.part;
		this.location.copyFrom(other.location);
	}

	public void copyFrom(JSONObject json) throws JSONException {
		this.name = json.optString("name");
		this.part = json.optString("part");
		this.location.copyFrom(json.getJSONObject("location"));
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
		json.put("part", part);
		json.put("location", location.toJSONObject());
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
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((part == null) ? 0 : part.hashCode());
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
		AnimatorJoint other = (AnimatorJoint) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (part == null) {
			if (other.part != null)
				return false;
		} else if (!part.equals(other.part))
			return false;
		return true;
	}

}
