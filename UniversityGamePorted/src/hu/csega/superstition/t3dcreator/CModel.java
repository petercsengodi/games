package hu.csega.superstition.t3dcreator;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import hu.csega.superstition.fileoperations.FileControl;
import hu.csega.superstition.gamelib.legacy.modeldata.CVertex;
import hu.csega.superstition.tools.Updates;
import hu.csega.superstition.tools.presentation.ToolView;
import hu.csega.superstition.util.Vectors;
import hu.csega.superstition.xml.XmlClass;
import hu.csega.superstition.xml.XmlField;

@XmlClass("T3DCreator.CModel")
public class CModel implements IModelPart
{
	public List<CFigure> figures;
	protected List<ToolView> views;
	protected Object selected;
	protected boolean snap_to_grid;

	private Memento memento;
	private Matrix3f selected_matrix;
	private Vector2f selected_texture;

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

	public Object getSelected() {
		return selected;
	}

	public void setSelected(Object value) {
		selected = value;
		selected_matrix = new Matrix3f().identity();
		selected_texture = new Vector2f(0f, 0f);
		UpdateViews(Updates.Selection);
	}

	public Memento getMemento()	{
		return memento;
	}

	public Matrix3f getSelectedMatrix() {
		return selected_matrix;
	}

	public void setSelectedMatrix(Matrix3f value) {
		this.selected_matrix = value;
	}

	public Vector2f getSelectedTexture() {
		return selected_texture;
	}

	public void setSelectedTexture(Vector2f value) {
		this.selected_texture = value;
	}

	public void clearSelection() {
		selected = "";
	}

	public CModel() {
		this.figures = new ArrayList<>();
		this.views = new ArrayList<>();
		this.selected = null;
		this.snap_to_grid = true;
		this.memento = new Memento();
	}

	public CModel(FileControl control)
	{
		this.figures = new ArrayList<>();
		this.views = new ArrayList<>();
		this.selected = null;
		this.snap_to_grid = true;
		this.memento = new Memento(control);
	}

	public void RegisterView(ToolView view) {
		views.add(view);
		view.setData(this);
		view.invalidate();
	}

	public void RemoveView(ToolView view) {
		views.remove(view);
	}

	public void UpdateViews() {
		UpdateViews(Updates.Full);
	}

	public void UpdateViews(Updates update) {
		for(ToolView view : views) {
			view.updateView(update);
		}
	}

	public void Resize(float factor)
	{
		// TODO allocationless
		Vector3f average = new Vector3f(0f, 0f, 0f);
		Vector3f tmp = new Vector3f();
		//		int num = 0;
		//		for(CFigure f : figures)
		//		{
		//			num += f.vertices.Count;
		//		}
		//
		//		float a = 1f / num;
		//		for(CFigure f : figures)
		//		{
		//			for(CVertex v : f.vertices)
		//			{
		//				average += v.position * a;
		//			}
		//		}

		for(CFigure f : figures) {
			for(CVertex v : f.vertices) {
				v.position.sub(average, tmp);
				tmp.mul(factor, tmp);
				tmp.add(average, v.position);
			}
		}
	}

	public Vector3f countBoundingBox()
	{
		// TODO allocationless
		if(figures == null || figures.isEmpty())
			return new Vector3f(0f, 0f, 0f);

		Vector3f ret = new Vector3f();
		Vector3f min = new Vector3f();
		Vector3f max = new Vector3f();

		ret.set(figures.get(0).vertices.get(0).position);
		min.set(ret);
		max.set(ret);

		for(CFigure f : figures) {
			for(CVertex v : f.vertices) {
				Vectors.minimize(min, v.position, min);
				Vectors.maximize(max, v.position, max);
			}
		}

		max.sub(min, ret);
		return ret;
	}

	public boolean verify() {
		boolean ret = true;
		for(CFigure figure : figures) {
			ret &= figure.verify();
		}
		return ret;
	}

	@Override
	public void move(Vector3f direction) {
		for(CFigure figure : figures)
			figure.move(direction);
	}

	@Override
	public void moveTexture(Vector2f direction) {
		for(CFigure figure : figures)
			figure.moveTexture(direction);
	}

	@Override
	public boolean hasPart(IModelPart part) {
		if(part.equals(this))
			return true;

		boolean ret = false;
		for(CFigure figure : figures)
			ret |= figure.hasPart(part);

		return ret;
	}

	@Override
	public Vector3f centerPoint() {
		// TODO allocationless
		Vector3f ret = new Vector3f(0f, 0f, 0f);
		Vector3f tmp = new Vector3f();

		float n = 1f / figures.size();
		for(CFigure figure : figures) {
			figure.centerPoint().mul(n, tmp);
			ret.add(tmp, ret);
		}

		return ret;
	}

	@Override
	public void scale(Matrix3f matrix) {
		for(CFigure figure : figures) {
			figure.scale(matrix);
		}
	}

	public void AddMeshFigure(InitialFigure figure, Device device)
	{
		Mesh mesh = null;
		MeshUtils util = new MeshUtils(device);
		GraphicsStream stream = null;
		Operation operation = null;
		DialogResult res;
		CFigure f;

		switch(figure)
		{
		case InitialFigure.Cylinder:
			Sphere c_dialog = new Sphere("Cylinder");
			res = c_dialog.ShowDialog();
			if(res == DialogResult.OK)
			{
				mesh = Mesh.Cylinder(device, 1f, 1f, 2f,
						c_dialog.Slice, c_dialog.Stack, out stream);
				f = util.SubsetToFigures(mesh, device, stream);
				operation = new AddMeshOperation(this, f);
			}
			c_dialog.Dispose();
			break;

		case InitialFigure.Box:
			mesh = Mesh.Box(device, 1f, 1f, 1f, out stream);
			f = util.SubsetToFigures(mesh, device, stream);
			operation = new AddMeshOperation(this, f);
			break;

		case InitialFigure.Torus:
			Torus torus = new Torus();
			res = torus.ShowDialog();
			if(res == DialogResult.OK)
			{
				mesh = Mesh.Torus(device,
						torus.InnerRadius, torus.OuterRadius,
						torus.Sides, torus.Rings, out stream);
				f = util.SubsetToFigures(mesh, device, stream);
				operation = new AddMeshOperation(this, f);
			}
			torus.Dispose();
			break;

		case InitialFigure.TextMesh:
			MeshText t_dialog = new MeshText();
			res = t_dialog.ShowDialog();
			if(res == DialogResult.OK)
			{
				mesh = Mesh.TextFromFont(device,
						t_dialog.SelectedFont, t_dialog.Value,
						t_dialog.Deviation, 1f, out stream);
				f = util.SubsetToFigures(mesh, device, stream);
				operation = new AddMeshOperation(this, f);
			}
			t_dialog.Dispose();
			break;


		case InitialFigure.Teapot:
			mesh = Mesh.Teapot(device, out stream);
			f = util.SubsetToFigures(mesh, device, stream);
			operation = new AddMeshOperation(this, f);
			break;

		default:
			Sphere s_dialog = new Sphere();
			res = s_dialog.ShowDialog();
			if(res == DialogResult.OK)
			{
				mesh = Mesh.Sphere(device, 1f, s_dialog.Slice,
						s_dialog.Stack, out stream);
				f = util.SubsetToFigures(mesh, device, stream);
				operation = new AddMeshOperation(this, f);
			}
			s_dialog.Dispose();
			break;
		}

		util.Dispose();
		if(mesh != null) mesh.Dispose();
		if(operation != null)
		{
			memento.Push(operation);
			UpdateViews(Updates.Full);
		}
	}

} // End of class CModel