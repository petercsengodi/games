package hu.csega.games.engine.ftm;

import org.json.JSONException;
import org.json.JSONObject;

public class GameTriangle {

	private int v1;
	private int v2;
	private int v3;

	public GameTriangle() {
	}

	public GameTriangle(int v1, int v2, int v3) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}

	public int getV1() {
		return v1;
	}

	public void setV1(int v1) {
		this.v1 = v1;
	}

	public int getV2() {
		return v2;
	}

	public void setV2(int v2) {
		this.v2 = v2;
	}

	public int getV3() {
		return v3;
	}

	public void setV3(int v3) {
		this.v3 = v3;
	}

	public JSONObject toJSONObject() {
		try {
			JSONObject json = new JSONObject();
			json.put("v1", v1);
			json.put("v2", v2);
			json.put("v3", v3);
			return json;
		} catch (JSONException ex) {
			throw new RuntimeException(ex);
		}
	}

	public void fromJSONObject(JSONObject json) {
		try {
			this.v1 = json.getInt("v1");
			this.v2 = json.getInt("v2");
			this.v3 = json.getInt("v3");
		} catch (JSONException ex) {
			throw new RuntimeException(ex);
		}
	}
}
