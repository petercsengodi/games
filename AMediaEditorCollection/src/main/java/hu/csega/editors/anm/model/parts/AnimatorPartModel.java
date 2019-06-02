package hu.csega.editors.anm.model.parts;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class AnimatorPartModel implements JSONString {

	public String filename = null;
	public List<AnimatorPartVertex> vertices = new ArrayList<>();
	public List<AnimatorPartTriangle> triangles = new ArrayList<>();

	public AnimatorPartModel() {
	}

	public AnimatorPartModel(String filename, List<AnimatorPartVertex> vertices, List<AnimatorPartTriangle> triangles) {
		this.filename = filename;
		this.vertices.addAll(vertices);
		this.triangles.addAll(triangles);
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
		this.vertices.clear();
		this.vertices.addAll(other.vertices);
		this.triangles.clear();
		this.triangles.addAll(other.triangles);
	}

	public void copyFrom(JSONObject json) throws JSONException {
		this.filename = json.optString("filename");
		// FIXME
	}

	@Override
	public String toJSONString() {
		try {
			JSONObject json = toJSONObject();
			return json.toString();
		} catch (JSONException ex) {
			throw new RuntimeException("Couldn't create JSON!", ex);
		}
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("filename", filename);
		// FIXME
		return json;
	}

	@Override
	public String toString() {
		return toJSONString();
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<AnimatorPartVertex> getVertices() {
		return vertices;
	}

	public void setVertices(List<AnimatorPartVertex> vertices) {
		this.vertices = vertices;
	}

	public List<AnimatorPartTriangle> getTriangles() {
		return triangles;
	}

	public void setTriangles(List<AnimatorPartTriangle> triangles) {
		this.triangles = triangles;
	}

}
