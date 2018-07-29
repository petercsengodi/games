package hu.csega.editors.anm.model.parts;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class AnimatorLocation implements JSONString {

	public final AnimatorPosition position;
	public final AnimatorPosition direction;
	public final AnimatorPosition up;

	public AnimatorLocation() {
		this.position = new AnimatorPosition();
		this.direction = new AnimatorPosition();
		this.up = new AnimatorPosition();
	}

	public AnimatorLocation(AnimatorPosition position, AnimatorPosition direction, AnimatorPosition up) {
		this.position = new AnimatorPosition(position);
		this.direction = new AnimatorPosition(direction);
		this.up = new AnimatorPosition(up);
	}

	public AnimatorLocation(AnimatorLocation other) {
		this();
		copyFrom(other);
	}

	public AnimatorLocation(String s) throws JSONException {
		this(new JSONObject(s));
	}

	public AnimatorLocation(JSONObject json) throws JSONException {
		this();
		copyFrom(json);
	}

	public void copyFrom(AnimatorLocation other) {
		this.position.copyFrom(position);
		this.direction.copyFrom(direction);
		this.up.copyFrom(up);
	}

	public void copyFrom(JSONObject json) throws JSONException {
		this.position.copyFrom(json.getJSONObject("position"));
		this.direction.copyFrom(json.getJSONObject("direction"));
		this.up.copyFrom(json.getJSONObject("up"));
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
		json.put("position", position.toJSONObject());
		json.put("direction", direction.toJSONObject());
		json.put("up", up.toJSONObject());
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
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((up == null) ? 0 : up.hashCode());
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
		AnimatorLocation other = (AnimatorLocation) obj;
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (up == null) {
			if (other.up != null)
				return false;
		} else if (!up.equals(other.up))
			return false;
		return true;
	}

}
