package hu.csega.editors.anm.model;

public class AnimationMisc {

	private int currentScene;
	private AnimationPlacement camera;

	private int maxPartIndex;

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

	public int getMaxPartIndex() {
		return maxPartIndex;
	}

	public void setMaxPartIndex(int maxPartIndex) {
		this.maxPartIndex = maxPartIndex;
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

}
