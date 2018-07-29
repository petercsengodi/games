package hu.csega.editors.anm.model.parts;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class AnimatorPosition implements JSONString {

	public double x, y, z;

	public AnimatorPosition() {
	}

	public AnimatorPosition(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public AnimatorPosition(AnimatorPosition other) {
		this();
		copyFrom(other);
	}

	public AnimatorPosition(String s) throws JSONException {
		this(new JSONObject(s));
	}

	public AnimatorPosition(JSONObject json) {
		this();
		copyFrom(json);
	}

	public void copyFrom(AnimatorPosition other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	public void copyFrom(JSONObject json) {
		this.x = json.optDouble("x", 0.0);
		this.y = json.optDouble("y", 0.0);
		this.z = json.optDouble("z", 0.0);
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
		json.put("x", x);
		json.put("y", y);
		json.put("z", z);
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
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		AnimatorPosition other = (AnimatorPosition) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
}
