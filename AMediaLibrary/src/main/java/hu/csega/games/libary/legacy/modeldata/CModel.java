package hu.csega.games.libary.legacy.modeldata;

import java.util.ArrayList;
import java.util.List;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("T3DCreator.CModel")
public class CModel {

	public List<CFigure> figures = new ArrayList<>();
	protected boolean snap_to_grid;

	public double grid_from = -3, grid_to = 3,
		grid_step = 1.0, grid_error = 0.001;

	@XmlField("figures")
	public List<CFigure> getFigures() {
		return figures;
	}

	@XmlField("figures")
	public void setFigures(List<CFigure> figures) {
		this.figures = figures;
	}

	@XmlField("grid_from")
	public double getGrid_from() {
		return grid_from;
	}

	@XmlField("grid_from")
	public void setGrid_from(double grid_from) {
		this.grid_from = grid_from;
	}

	@XmlField("grid_to")
	public double getGrid_to() {
		return grid_to;
	}

	@XmlField("grid_to")
	public void setGrid_to(double grid_to) {
		this.grid_to = grid_to;
	}

	@XmlField("grid_step")
	public double getGrid_step() {
		return grid_step;
	}

	@XmlField("grid_step")
	public void setGrid_step(double grid_step) {
		this.grid_step = grid_step;
	}

	@XmlField("grid_error")
	public double getGrid_error() {
		return grid_error;
	}

	@XmlField("grid_error")
	public void setGrid_error(double grid_error) {
		this.grid_error = grid_error;
	}

	@XmlField("snap_to_grid")
	public boolean isSnapToGrid() {
		return snap_to_grid;
	}

	@XmlField("snap_to_grid")
	public void setSnapToGrid(boolean snap_to_grid) {
		this.snap_to_grid = snap_to_grid;
	}

} // End of class CModel