package hu.csega.games.engine.ftm;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameMesh {

	private String name;
	private String texture;
	private List<GameVertex> vertices = new ArrayList<>();
	private List<GameTriangle> triangles = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public List<GameVertex> getVertices() {
		return vertices;
	}

	public void setVertices(List<GameVertex> vertices) {
		this.vertices = vertices;
	}

	public List<GameTriangle> getTriangles() {
		return triangles;
	}

	public void setTriangles(List<GameTriangle> triangles) {
		this.triangles = triangles;
	}

	public JSONObject toJSONObject() {
		try {
			JSONObject json = new JSONObject();
			json.put("name", this.name);
			json.put("texture", this.texture);

			JSONArray vertices = new JSONArray();
			if(this.vertices != null) {
				for(GameVertex vertex : this.vertices) {
					JSONObject v = vertex.toJSONObject();
					vertices.put(v);
				}
			}

			JSONArray triangles = new JSONArray();
			if(this.triangles != null) {
				for(GameTriangle triangle : this.triangles) {
					JSONObject t = triangle.toJSONObject();
					triangles.put(t);
				}
			}

			json.put("vertices", vertices);
			json.put("triangles", triangles);
			return json;
		} catch (JSONException ex) {
			throw new RuntimeException(ex);
		}
	}

	public void fromJSONObject(JSONObject json) {
		try {
			this.name = json.optString("name");
			this.texture = json.optString("texture");
			this.vertices = new ArrayList<>();
			this.triangles = new ArrayList<>();

			JSONArray vertices = json.optJSONArray("vertices");
			if(vertices != null && vertices.length() > 0) {
				int len = vertices.length();
				for(int i = 0; i < len; i++) {
					JSONObject j = vertices.getJSONObject(i);
					GameVertex vertex = new GameVertex();
					vertex.fromJSONObject(j);
					this.vertices.add(vertex);
				}
			}

			JSONArray triangles = json.optJSONArray("triangles");
			if(triangles != null && triangles.length() > 0) {
				int len = triangles.length();
				for(int i = 0; i < len; i++) {
					JSONObject j = triangles.getJSONObject(i);
					GameTriangle triangle = new GameTriangle();
					triangle.fromJSONObject(j);
					this.triangles.add(triangle);
				}
			}

		} catch (JSONException ex) {
			throw new RuntimeException(ex);
		}
	}
}
