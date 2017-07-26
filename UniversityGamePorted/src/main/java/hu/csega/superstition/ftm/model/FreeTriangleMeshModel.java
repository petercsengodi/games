package hu.csega.superstition.ftm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FreeTriangleMeshModel implements Serializable {

	private List<FreeTriangleMeshVertex> vertices = new ArrayList<>();
	private List<FreeTriangleMeshTriangle> triangles = new ArrayList<>();
	private List<Object> selectedObjects = new ArrayList<>();

	private double canvasXYTranslateX;
	private double canvasXYTranslateY;
	private double canvasXYZoom = 1.0;

	private double canvasXZTranslateX;
	private double canvasXZTranslateY;
	private double canvasXZZoom = 1.0;

	private double canvasZYTranslateX;
	private double canvasZYTranslateY;
	private double canvasZYZoom = 1.0;

	private double openGLTranslateX;
	private double openGLTranslateY;
	private double openGLAlpha;
	private double openGLBeta;
	private double openGLZoom = 1.0;

	public void select(FreeTriangleMeshCube cube) {
		selectedObjects.clear();
		for(FreeTriangleMeshVertex vertex : vertices) {
			if(cube.contains(vertex)) {
				selectedObjects.add(vertex);
			}
		}
	}

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

	public List<Object> getSelectedObjects() {
		return selectedObjects;
	}

	public void setSelectedObjects(List<Object> selectedObjects) {
		this.selectedObjects = selectedObjects;
	}

	public double getCanvasXYTranslateX() {
		return canvasXYTranslateX;
	}

	public void setCanvasXYTranslateX(double canvasXYTranslateX) {
		this.canvasXYTranslateX = canvasXYTranslateX;
	}

	public double getCanvasXYTranslateY() {
		return canvasXYTranslateY;
	}

	public void setCanvasXYTranslateY(double canvasXYTranslateY) {
		this.canvasXYTranslateY = canvasXYTranslateY;
	}

	public double getCanvasXYZoom() {
		return canvasXYZoom;
	}

	public void setCanvasXYZoom(double canvasXYZoom) {
		this.canvasXYZoom = canvasXYZoom;
	}

	public double getCanvasXZTranslateX() {
		return canvasXZTranslateX;
	}

	public void setCanvasXZTranslateX(double canvasXZTranslateX) {
		this.canvasXZTranslateX = canvasXZTranslateX;
	}

	public double getCanvasXZTranslateY() {
		return canvasXZTranslateY;
	}

	public void setCanvasXZTranslateY(double canvasXZTranslateY) {
		this.canvasXZTranslateY = canvasXZTranslateY;
	}

	public double getCanvasXZZoom() {
		return canvasXZZoom;
	}

	public void setCanvasXZZoom(double canvasXZZoom) {
		this.canvasXZZoom = canvasXZZoom;
	}

	public double getCanvasZYTranslateX() {
		return canvasZYTranslateX;
	}

	public void setCanvasZYTranslateX(double canvasZYTranslateX) {
		this.canvasZYTranslateX = canvasZYTranslateX;
	}

	public double getCanvasZYTranslateY() {
		return canvasZYTranslateY;
	}

	public void setCanvasZYTranslateY(double canvasZYTranslateY) {
		this.canvasZYTranslateY = canvasZYTranslateY;
	}

	public double getCanvasZYZoom() {
		return canvasZYZoom;
	}

	public void setCanvasZYZoom(double canvasZYZoom) {
		this.canvasZYZoom = canvasZYZoom;
	}

	public double getOpenGLTranslateX() {
		return openGLTranslateX;
	}

	public void setOpenGLTranslateX(double openGLTranslateX) {
		this.openGLTranslateX = openGLTranslateX;
	}

	public double getOpenGLTranslateY() {
		return openGLTranslateY;
	}

	public void setOpenGLTranslateY(double openGLTranslateY) {
		this.openGLTranslateY = openGLTranslateY;
	}

	public double getOpenGLAlpha() {
		return openGLAlpha;
	}

	public void setOpenGLAlpha(double openGLAlpha) {
		this.openGLAlpha = openGLAlpha;
	}

	public double getOpenGLBeta() {
		return openGLBeta;
	}

	public void setOpenGLBeta(double openGLBeta) {
		this.openGLBeta = openGLBeta;
	}

	public double getOpenGLZoom() {
		return openGLZoom;
	}

	public void setOpenGLZoom(double openGLZoom) {
		this.openGLZoom = openGLZoom;
	}

	private static final long serialVersionUID = 1L;
}
