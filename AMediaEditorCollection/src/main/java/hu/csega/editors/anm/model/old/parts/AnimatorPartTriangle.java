package hu.csega.editors.anm.model.old.parts;

import org.json.JSONObject;
import org.json.JSONString;

public class AnimatorPartTriangle implements JSONString {

	private int vertex1;
	private int vertex2;
	private int vertex3;

	@Override
	public String toJSONString() {
		return toJSONObject().toString();
	}

	public JSONObject toJSONObject() {
		return new JSONObject(this);
	}

	public int getVertex1() {
		return vertex1;
	}

	public void setVertex1(int vertex1) {
		this.vertex1 = vertex1;
	}

	public int getVertex2() {
		return vertex2;
	}

	public void setVertex2(int vertex2) {
		this.vertex2 = vertex2;
	}

	public int getVertex3() {
		return vertex3;
	}

	public void setVertex3(int vertex3) {
		this.vertex3 = vertex3;
	}

}
