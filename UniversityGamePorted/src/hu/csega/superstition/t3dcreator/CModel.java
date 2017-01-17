package hu.csega.superstition.t3dcreator;

public class CModel : IPart
{
	public ArrayList figures;
	protected ArrayList views;
	protected object selected;
	protected bool snap_to_grid;

	private Memento memento;
	private Matrix selected_matrix;
	private Vector2 selected_texture;

	public double grid_from = -3, grid_to = 3,
		grid_step = 1.0, grid_error = 0.001;

	public bool SnapToGrid
	{
		get{ return snap_to_grid; }
	}

	public void SetSnapToGrid(bool snap_to_grid)
	{
		this.snap_to_grid = snap_to_grid;
	}

	public object Selected
	{
		get { return selected; }
		set	{
			selected = value;
			selected_matrix = Matrix.Identity;
			selected_texture = new Vector2(0f, 0f);
			UpdateViews(Updates.Selection);
		}
	}

	public Memento Memento
	{
		get{ return memento; }
	}

	public Matrix SelectedMatrix
	{
		get { return selected_matrix; }
		set { selected_matrix = value; }
	}

	public Vector2 SelectedTexture
	{
		get { return selected_texture; }
		set { selected_texture = value; }
	}

	public void ClearSelection()
	{
		selected = "";
	}

	public CModel()
	{
		this.figures = new ArrayList();
		this.views = new ArrayList();
		this.selected = null;
		this.snap_to_grid = true;
		this.memento = new Memento();
	}

	public CModel(FileControl control)
	{
		this.figures = new ArrayList();
		this.views = new ArrayList();
		this.selected = null;
		this.snap_to_grid = true;
		this.memento = new Memento(control);
	}

	public void RegisterView(CView view)
	{
		views.Add(view);
		view.SetData(this);
		view.Invalidate();
	}

	public void RemoveView(CView view)
	{
		views.Remove(view);
	}

	public void UpdateViews()
	{
		UpdateViews(Updates.Full);
	}

	public void UpdateViews(Updates update)
	{
		foreach(CView view in views)
		{
			view.UpdateView(update);
		}
	}

	public void Resize(float factor)
	{
		Vector3 average = new Vector3(0f, 0f, 0f);
//		int num = 0;
//		foreach(CFigure f in figures)
//		{
//			num += f.vertices.Count;
//		}
//
//		float a = 1f / num;
//		foreach(CFigure f in figures)
//		{
//			foreach(CVertex v in f.vertices)
//			{
//				average += v.position * a;
//			}
//		}

		foreach(CFigure f in figures)
		{
			foreach(CVertex v in f.vertices)
			{
				v.position = (v.position - average) * factor
					+ average;
			}
		}
	}

	public Vector3 CountBoundingBox()
	{
		if(figures == null) return new Vector3(0f, 0f, 0f);
		if(figures.Count == 0) return new Vector3(0f, 0f, 0f);
		Vector3 ret = ((figures[0] as CFigure).vertices[0]
			as CVertex).position;
		Vector3 min = ret, max = ret;

		foreach(CFigure f in figures)
		{
			foreach(CVertex v in f.vertices)
			{
				min = Vector3.Minimize(min, v.position);
				max = Vector3.Maximize(max, v.position);
			}
		}

		ret = max - min;
		return ret;
	}

	public bool Verify()
	{
		bool ret = true;
		foreach(CFigure figure in figures)
		{
			ret &= figure.Verify();
		}
		return ret;
	}

	#region IPart Members

	public void move(Vector3 direction)
	{
		foreach(CFigure figure in figures)
			figure.move(direction);
	}

	public void moveTexture(Vector2 direction)
	{
		foreach(CFigure figure in figures)
			figure.moveTexture(direction);
	}

	public bool hasPart(IPart part)
	{
		if(part.Equals(this)) return true;
		bool ret = false;
		foreach(CFigure figure in figures)
			ret |= figure.hasPart(part);
		return ret;
	}

	public Vector3 centerPoint()
	{
		Vector3 ret = new Vector3(0f, 0f, 0f);
		float n = 1f / (float)figures.Count;
		foreach(CFigure figure in figures)
		{
			ret += figure.centerPoint() * n;
		}
		return ret;
	}

	public void scale(Matrix matrix)
	{
		foreach(CFigure figure in figures)
		{
			figure.scale(matrix);
		}
	}


	#endregion

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