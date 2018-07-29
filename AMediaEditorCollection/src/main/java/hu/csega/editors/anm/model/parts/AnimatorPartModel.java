package hu.csega.editors.anm.model.parts;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class AnimatorPartModel implements JSONString {

	String filename;
	final AnimatorPosition scale;

	public AnimatorPartModel() {
		this.filename = null;
		this.scale = new AnimatorPosition();
	}

	public AnimatorPartModel(String filename, AnimatorPosition scale) {
		this.filename = filename;
		this.scale = new AnimatorPosition(scale);
	}

	public AnimatorPartModel(AnimatorPartModel other) {
		this();
		copyFrom(other);
	}

	public AnimatorPartModel(String s) throws JSONException {
		this(new JSONObject(s));
	}

	public AnimatorPartModel(JSONObject json) throws JSONException {
		this();
		copyFrom(json);
	}

	public void copyFrom(AnimatorPartModel other) {
		this.filename = other.filename;
		this.scale.copyFrom(other.scale);
	}

	public void copyFrom(JSONObject json) throws JSONException {
		this.filename = json.optString("filename");
		this.scale.copyFrom(json.getJSONObject("scale"));
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
		json.put("filename", filename);
		json.put("scale", scale.toJSONObject());
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
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((scale == null) ? 0 : scale.hashCode());
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
		AnimatorPartModel other = (AnimatorPartModel) obj;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (scale == null) {
			if (other.scale != null)
				return false;
		} else if (!scale.equals(other.scale))
			return false;
		return true;
	}

}
