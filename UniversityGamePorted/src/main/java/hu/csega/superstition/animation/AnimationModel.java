package hu.csega.superstition.animation;

import java.util.ArrayList;
import java.util.List;

import hu.csega.superstition.gamelib.model.animation.SAnimation;
import hu.csega.superstition.tools.UpdateScope;

@SuppressWarnings("unused")
public class AnimationModel {

	private final AnimationFacade facade;

	private List<SAnimation> parts = new ArrayList<>();
	private List<AnimationView> views = new ArrayList<>();
	private Object selected = null;

	private int scene = 0;
	private int maxScenes = 1;

	private boolean snapToGrid = false;
	private double gridFrom = -1;
	private double gridTo = 1;
	private double gridStep = 0.1;
	private double gridError = 0.001;

	public AnimationModel(AnimationFacade facade) {
		this.facade = facade;
	}

	public AnimationModel addView(AnimationView view) {
		if(!views.contains(view))
			views.add(view);
		return this;
	}

	public boolean removeView(AnimationView view) {
		return views.remove(view);
	}

	public void updateViews() {
		updateViews(UpdateScope.FULL);
	}

	public void updateViews(UpdateScope updateScope) {
		for(AnimationView view : views)
			view.updateAnimationView(updateScope);
	}

	public void clear() {
		parts.clear();
		scene = 0;
		maxScenes = 1;
		updateViews();
	}

	public boolean isSnapToGrid() {
		return snapToGrid;
	}

	public void setSnapToGrid(boolean snapToGrid) {
		this.snapToGrid = snapToGrid;
	}

	public double getGridStep() {
		return gridStep;
	}

	public void setGridStep(float gridStep) {
		this.gridStep = gridStep;
	}

	public Object selected() {
		return selected;
	}

	public void setSelected(Object selected) {
		this.selected = selected;
		updateViews(UpdateScope.SELECTION_ONLY);
	}

	public void ClearSelection() {
		selected = null;
	}

}
