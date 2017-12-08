package hu.csega.editors.ftm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FreeTriangleMeshMesh implements Serializable {

	private List<FreeTriangleMeshVertex> vertices = new ArrayList<>();
	private List<FreeTriangleMeshTriangle> triangles = new ArrayList<>();

	public List<FreeTriangleMeshVertex> getVertices() {
		return vertices;
	}

	public void setVertices(List<FreeTriangleMeshVertex> vertices) {
		this.vertices = vertices;
	}

	public List<FreeTriangleMeshTriangle> getTriangles() {
		return triangles;
	}

	public void setTriangles(List<FreeTriangleMeshTriangle> triangles) {
		this.triangles = triangles;
	}

	private static final long serialVersionUID = 1L;
}
