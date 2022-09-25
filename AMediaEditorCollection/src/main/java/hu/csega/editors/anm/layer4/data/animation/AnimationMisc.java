package hu.csega.editors.anm.layer4.data.animation;

import java.io.Serializable;

public class AnimationMisc implements Serializable {

	private int currentScene;
	private AnimationPlacement camera;

	private boolean gridEnabled;
	private String filename;
	private boolean saved;

	private double[] zooming;
	private double[] grid;

	public int getCurrentScene() {
		return currentScene;
	}

	public void setCurrentScene(int currentScene) {
		this.currentScene = currentScene;
	}

	public AnimationPlacement getCamera() {
		return camera;
	}

	public void setCamera(AnimationPlacement camera) {
		this.camera = camera;
	}

	public boolean isGridEnabled() {
		return gridEnabled;
	}

	public void setGridEnabled(boolean gridEnabled) {
		this.gridEnabled = gridEnabled;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public double[] getZooming() {
		return zooming;
	}

	public void setZooming(double[] zooming) {
		this.zooming = zooming;
	}

	public double[] getGrid() {
		return grid;
	}

	public void setGrid(double[] grid) {
		this.grid = grid;
	}

	public AnimationMisc() {
		this.gridEnabled = true;
		this.saved = true;
	}

	private static final long serialVersionUID = 1L;

}
