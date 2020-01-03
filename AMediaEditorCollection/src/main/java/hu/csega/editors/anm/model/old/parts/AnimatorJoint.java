package hu.csega.editors.anm.model.old.parts;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class AnimatorJoint implements JSONString {

	public String name = null;
	public final AnimatorLocation location = new AnimatorLocation();

	public AnimatorJoint() {
	}

	public AnimatorJoint(String name, AnimatorLocation location) {
		this.name = name;
		this.location.copyFrom(location);
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
		this.location.copyFrom(other.location);
	}

	public void copyFrom(JSONObject json) throws JSONException {
		this.name = json.optString("name");
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
		return true;
	}

}
