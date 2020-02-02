package hu.csega.games.engine.ftm;

import org.json.JSONException;
import org.json.JSONObject;

public class GameVertex {

	private float px;
	private float py;
	private float pz;

	private float nx;
	private float ny;
	private float nz;

	private float tx;
	private float ty;

	public float getPX() {
		return px;
	}

	public void setPX(float PX) {
		this.px = PX;
	}

	public float getPY() {
		return py;
	}

	public void setPY(float PY) {
		this.py = PY;
	}

	public float getPZ() {
		return pz;
	}

	public void setPZ(float PZ) {
		this.pz = PZ;
	}

	public float getNX() {
		return nx;
	}

	public void setNX(float NX) {
		this.nx = NX;
	}

	public float getNY() {
		return ny;
	}

	public void setNY(float NY) {
		this.ny = NY;
	}

	public float getNZ() {
		return nz;
	}

	public void setNZ(float NZ) {
		this.nz = NZ;
	}

	public float getTX() {
		return tx;
	}

	public void setTX(float TX) {
		this.tx = TX;
	}

	public float getTY() {
		return ty;
	}

	public void setTY(float TY) {
		this.ty = TY;
	}

	public JSONObject toJSONObject() {
		try {
			JSONObject json = new JSONObject();
			json.put("px", this.px);
			json.put("py", this.py);
			json.put("pz", this.pz);
			json.put("nx", this.nx);
			json.put("ny", this.ny);
			json.put("nz", this.nz);
			json.put("tx", this.tx);
			json.put("ty", this.ty);
			return json;
		} catch (JSONException ex) {
			throw new RuntimeException(ex);
		}
	}

	public void fromJSONObject(JSONObject json) {
		try {
			this.px = (float)json.getDouble("px");
			this.py = (float)json.getDouble("py");
			this.pz = (float)json.getDouble("pz");
			this.nx = (float)json.getDouble("nx");
			this.ny = (float)json.getDouble("ny");
			this.nz = (float)json.getDouble("nz");
			this.tx = (float)json.getDouble("tx");
			this.ty = (float)json.getDouble("ty");
		} catch (JSONException ex) {
			throw new RuntimeException(ex);
		}
	}
}
