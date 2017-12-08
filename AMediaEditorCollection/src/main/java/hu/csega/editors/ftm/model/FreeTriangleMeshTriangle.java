package hu.csega.editors.ftm.model;

import java.io.Serializable;

public class FreeTriangleMeshTriangle implements Serializable {

	private int vertex1;
	private int vertex2;
	private int vertex3;

	public FreeTriangleMeshTriangle(int i1, int i2, int i3) {
		this.vertex1 = i1;
		this.vertex2 = i2;
		this.vertex3 = i3;
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

	private static final long serialVersionUID = 1L;
}
