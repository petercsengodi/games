package hu.csega.superstition.animation;

import javax.swing.JButton;
import javax.swing.JLabel;

import hu.csega.superstition.tools.UpdateScope;
import hu.csega.superstition.tools.presentation.ToolView;

@SuppressWarnings("unused")
public class WireFrameView extends ToolView {

	private static final long serialVersionUID = 1L;

	private JButton button1;
	private JButton button2;
	private JLabel label1;
	@Override
	public void updateView(UpdateScope update) {
		// TODO Auto-generated method stub

	}

//	public WireFrameView()
//	{
//		// This call is required by the Windows.Forms Form Designer.
//		InitializeComponent();
//
//		view = Matrix.Identity;
//		translation = new Vector3f(0f, 0f, 0f);
//	}
//
//	/// <summary>
//	/// Clean up any resources being used.
//	/// </summary>
//	protected void dispose(boolean disposing)
//	{
//		if( disposing )
//		{
//			if(components != null)
//			{
//				components.dispose();
//			}
//		}
//		super.dispose( disposing );
//	}
//
//	/// <summary>
//	/// Required method for Designer support - do not modify
//	/// the contents of this method with the code editor.
//	/// </summary>
//	private void InitializeComponent()
//	{
//		this.label1 = new System.Windows.Forms.Label();
//		this.button1 = new System.Windows.Forms.Button();
//		this.button2 = new System.Windows.Forms.Button();
//		this.SuspendLayout();
//		//
//		// label1
//		//
//		this.label1.Location = new System.Drawing.Point(8, 8);
//		this.label1.Name = "label1";
//		this.label1.Size = new System.Drawing.Size(72, 16);
//		this.label1.TabIndex = 0;
//		this.label1.Text = "label1";
//		//
//		// button1
//		//
//		this.button1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
//		this.button1.Location = new System.Drawing.Point(8, 72);
//		this.button1.Name = "button1";
//		this.button1.Size = new System.Drawing.Size(80, 23);
//		this.button1.TabIndex = 1;
//		this.button1.Text = "Hide Grid";
//		this.button1.Click += new System.EventHandler(this.button1_Click);
//		//
//		// button2
//		//
//		this.button2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
//		this.button2.Location = new System.Drawing.Point(128, 72);
//		this.button2.Name = "button2";
//		this.button2.Size = new System.Drawing.Size(48, 24);
//		this.button2.TabIndex = 2;
//		this.button2.Text = "Center";
//		this.button2.Click += new System.EventHandler(this.button2_Click);
//		//
//		// WireFrameView
//		//
//		this.Controls.Add(this.button2);
//		this.Controls.Add(this.button1);
//		this.Controls.Add(this.label1);
//		this.Name = "WireFrameView";
//		this.Size = new System.Drawing.Size(184, 104);
//		this.MouseUp += new System.Windows.Forms.MouseEventHandler(this.WireFrameView_MouseUp);
//		this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.WireFrameView_MouseMove);
//		this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.WireFrameView_MouseDown);
//		this.ResumeLayout(false);
//
//	}
//
//	private static final double zoom_step = 0.3;
//	private static final double move_step = 1.0;
//
//	private Matrix view, invert;
//	private Vector3f translation;
//	private double zoom;
//	private ButtonControl[] bcontrol;
//	private boolean show_grid;
//	private Vector3f angle = new Vector3f(0f, 0f, 0f);
//	private static final float rotation =
//			(float)Math.PI / 18;
//
//	public void SetView(Perspectives perspective)
//	{
//		view = Matrix.Identity;
//		view.M11 = 0f;
//		view.M22 = 0f;
//		view.M33 = 0f;
//
//		invert = Matrix.Identity;
//		invert.M11 = 0f;
//		invert.M22 = 0f;
//		invert.M33 = 0f;
//
//		switch(perspective)
//		{
//		case Perspectives.Left:
//			view.M31 = 1f;
//			view.M22 = -1f;
//			invert.M13 = 1f;
//			invert.M22 = -1f;
//			label1.Text = "Left";
//			angle.X = rotation;
//			break;
//
//		case Perspectives.Top:
//			view.M11 = 1f;
//			view.M32 = -1f;
//			invert.M11 = 1f;
//			invert.M23 = -1f;
//			label1.Text = "Top";
//			angle.Y = rotation;
//			break;
//
//		default:
//			view.M11 = 1f;
//			view.M22 = -1f;
//			invert.M11 = 1f;
//			invert.M22 = -1f;
//			label1.Text = "Front";
//			angle.Z = -rotation;
//			break;
//		}
//
//	}
//
//	public void UpdateView(Updates update)
//	{
//		Invalidate();
//	}
//
//	protected void InitializeView()
//	{
//		bcontrol = new ButtonControl[3];
//		for(int i = 0; i < 3; i++)
//		{
//			bcontrol[i] = new ButtonControl();
//			bcontrol[i].down = false;
//		}
//		show_grid = true;
//		zoom = 200.0;
//	}
//
//	protected void CloseView()
//	{
//
//	}
//
//	protected void OnPaintBackground(PaintEventArgs pevent)
//	{
//		if(!Initialized) super.OnPaintBackground(pevent);
//	}
//
//	protected void OnPaint(PaintEventArgs args)
//	{
//		if(!Initialized)
//		{
//			super.OnPaint(args);
//			return;
//		}
//
//		Graphics g = args.Graphics;
//		g.Clear(Color.LightCyan);
//		g.TranslateTransform(
//				(float)this.Width / 2f,
//				(float)this.Height / 2f);
//
//		Brush brush_normal = new SolidBrush(Color.Black);
//		Pen pen_normal = new Pen(brush_normal);
//		Brush brush_selected = new SolidBrush(Color.Red);
//		Pen pen_selected = new Pen(brush_selected, 3f);
//		Brush brush_grid = new SolidBrush(Color.LightGray);
//		Pen pen_grid = new Pen(brush_grid);
//		Brush brush_yellow = new SolidBrush(Color.Yellow);
//		Pen pen_yellow = new Pen(brush_yellow, 2f);
//		Brush brush_violet = new SolidBrush(Color.Violet);
//		Pen pen_violet = new Pen(brush_violet, 2f);
//
//		// Drawing origo
//		Vector3f vorigo = new Vector3f(0f, 0f, 0f);
//		Point porigo = Transform(vorigo);
//		g.DrawLine(pen_yellow, porigo.X - 10, porigo.Y,
//				porigo.X + 10, porigo.Y);
//		g.DrawLine(pen_yellow, porigo.X, porigo.Y - 10,
//				porigo.X, porigo.Y + 10);
//
//		// Drawing Grid
//		if(show_grid) DrawGrid(pen_grid, g);
//
//		Pen pen;
//
//		CModel model = (CModel)GetData();
//
//		for(CPart part : model.parts)
//		{
//			if(part.mesh_file == null) continue;
//			if(part.GetMesh() == null) continue;
//			if(part.GetMesh().Mesh == null) continue;
//
//			if(part == model.Selected) pen = pen_selected;
//			else pen = pen_normal;
//
//			Vector3f pos1, pos2, pos3;
//			Point line1, line2, line3;
//
//			for(CConnection con : part.connections)
//			{
//				pos1 = con.point;
//				pos1 = Vector3.TransformCoordinate(pos1,
//						part.model_transform[model.scene]);
//				pos1 += part.center_point[model.scene];
//				line1 = Transform(pos1);
//				if(con == model.Selected)
//				{
//					g.DrawEllipse(pen_selected, line1.X - 5,
//							line1.Y - 5, 11, 11);
//				}
//				else
//				{
//					g.DrawEllipse(pen_violet, line1.X - 5,
//							line1.Y - 5, 11, 11);
//				}
//			}
//
//			Mesh mesh = part.GetMesh().SimpleMesh;
//			CustomVertex.PositionOnly[] vertices =
//					(CustomVertex.PositionOnly[])
//					mesh.LockVertexBuffer(
//							typeof(CustomVertex.PositionOnly),
//							LockFlags.ReadOnly, mesh.NumberVertices);
//			int[] indices = (int[])mesh.LockIndexBuffer(
//					typeof(System.Int32), LockFlags.ReadOnly,
//					mesh.NumberFaces * 3);
//
//			for(int i = 0; i < mesh.NumberFaces; i++)
//			{
//				pos1 = vertices[indices[i * 3    ]].Position;
//				pos2 = vertices[indices[i * 3 + 1]].Position;
//				pos3 = vertices[indices[i * 3 + 2]].Position;
//				pos1 = Vector3.TransformCoordinate(pos1,
//						part.model_transform[model.scene]);
//				pos2 = Vector3.TransformCoordinate(pos2,
//						part.model_transform[model.scene]);
//				pos3 = Vector3.TransformCoordinate(pos3,
//						part.model_transform[model.scene]);
//				pos1 += part.center_point[model.scene];
//				pos2 += part.center_point[model.scene];
//				pos3 += part.center_point[model.scene];
//				line1 = Transform(pos1);
//				line2 = Transform(pos2);
//				line3 = Transform(pos3);
//				g.DrawLine(pen, line1, line2);
//				g.DrawLine(pen, line2, line3);
//				g.DrawLine(pen, line3, line1);
//			}
//
//			mesh.UnlockIndexBuffer();
//			mesh.UnlockVertexBuffer();
//		}
//
//		pen_grid.dispose();
//		brush_grid.dispose();
//		pen_selected.dispose();
//		brush_selected.dispose();
//		pen_normal.dispose();
//		brush_normal.dispose();
//	}
//
//	private Point Transform(Vector3f vector)
//	{
//		vector.TransformCoordinate(view);
//		vector.Multiply((float)zoom);
//		vector.Add(translation);
//		Point ret = new Point((int)vector.X, (int)vector.Y);
//		return ret;
//	}
//
//	private Vector3f InverseTransform(float x, float y)
//	{
//		Vector3f ret = new Vector3f(x, y, 0f);
//		ret.Subtract(translation);
//		ret.Multiply(1f / (float)zoom);
//		ret.TransformCoordinate(invert);
//		return ret;
//	}
//
//	private Vector3f SnapToGrid(Vector3f vector)
//	{
//		CModel model = (CModel) GetData();
//		vector.X = (float)(Math.Round(vector.X / model.grid_step) * model.grid_step);
//		vector.Y = (float)(Math.Round(vector.Y / model.grid_step) * model.grid_step);
//		vector.Z = (float)(Math.Round(vector.Z / model.grid_step) * model.grid_step);
//		return vector;
//	}
//
//	private void DrawGrid(Pen pen, Graphics g)
//	{
//		CModel model = (CModel) GetData();
//		int fromX = (int)(model.grid_from * zoom + translation.X);
//		int fromY = (int)(model.grid_from * zoom + translation.Y);
//		int toX = (int)(model.grid_to * zoom + translation.X);
//		int toY = (int)(model.grid_to * zoom + translation.Y);
//		int gridI;
//		for(double grid = model.grid_from; grid < model.grid_to + model.grid_error; grid += model.grid_step)
//		{
//			gridI = (int)(grid * zoom + translation.X);
//			g.DrawLine(pen, gridI, fromY, gridI, toY);
//			gridI = (int)(grid * zoom + translation.Y);
//			g.DrawLine(pen, fromX, gridI, toX, gridI);
//		}
//	}
//
//	private void WireFrameView_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e)
//	{
//		if(!Initialized) return;
//		int idx = -1;
//		if(e.Button == MouseButtons.Left) idx = (int)MButton.Left;
//		if(e.Button == MouseButtons.Right) idx = (int)MButton.Right;
//		if(e.Button == MouseButtons.Middle) idx = (int)MButton.Middle;
//		if(idx == -1) return;
//		bcontrol[idx].down = true;
//		bcontrol[idx].start_x = e.X;
//		bcontrol[idx].start_y = e.Y;
//
//		if(e.Button != MouseButtons.Left) return;
//
//		CModel model = GetData() as CModel;
//		Vector3f pos; Point line; double d;
//		for(CPart p : model.parts)
//		{
//			for(CConnection c : p.connections)
//			{
//				pos = c.point;
//				pos = Vector3.TransformCoordinate(pos,
//						p.model_transform[model.scene]);
//				pos += p.center_point[model.scene];
//				line = Transform(pos);
//				d = (line.X - e.X + this.Width / 2f)*(line.X - e.X + this.Width / 2f)
//						+ (line.Y - e.Y + this.Height / 2f)*(line.Y - e.Y + this.Height / 2f);
//				if(d < 25) model.Selected = c;
//			}
//		}
//	} // End of Function
//
//	private void WireFrameView_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e)
//	{
//		if(!Initialized) return;
//		int idx = -1;
//		if(e.Button == MouseButtons.Left) idx = (int)MButton.Left;
//		if(e.Button == MouseButtons.Right) idx = (int)MButton.Right;
//		if(e.Button == MouseButtons.Middle) idx = (int)MButton.Middle;
//		if(idx != -1) bcontrol[idx].down = false;
//	}
//
//	private void WireFrameView_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e)
//	{
//		if(!Initialized) return;
//		boolean invalidated = false;
//		Matrix m = Matrix.Identity;
//		int idx;
//
//		idx = (int)MButton.Left;
//		if(bcontrol[idx].down)
//		{
//			CModel model = (CModel)GetData();
//			IPart part = model.Selected as IPart;
//			CConnection con = model.Selected as CConnection;
//
//			if((part == null) && (con == null)) return;
//
//			Vector3f to_point = InverseTransform(e.X, e.Y);
//			Point origin = new Point(bcontrol[idx].start_x, bcontrol[idx].start_y);
//			Point difference = new Point(e.X - origin.X, e.Y - origin.Y);
//			Vector3f from_point = InverseTransform(origin.X, origin.Y);
//			Vector3f direction = to_point - from_point;
//			//Vector3f direction = InverseTransform(difference.X, difference.Y);
//
//			if(model.SnapToGrid &&
//					AnimaTool.GetOperatoion() == 0)
//			{
//				direction = SnapToGrid(direction);
//			}
//
//			if((direction.X != 0f) ||
//					(direction.Y != 0f) ||
//					(direction.Z != 0f))
//			{
//
//				if(part != null)
//				{
//					Vector3f tr = part.centerPoint(model.scene);
//					Vector3f scale = new Vector3f(0f, 0f, 0f);
//					if(Math.Abs(tr.X - from_point.X) > double.Epsilon)
//						scale.X = (to_point.X - tr.X) / (from_point.X - tr.X);
//					if(Math.Abs(tr.Y - from_point.Y) > double.Epsilon)
//						scale.Y = (to_point.Y - tr.Y) / (from_point.Y - tr.Y);
//					if(Math.Abs(tr.Z - from_point.Z) > double.Epsilon)
//						scale.Z = (to_point.Z - tr.Z) / (from_point.Z - tr.Z);
//
//					switch(AnimaTool.GetOperatoion())
//					{
//					case MoveOperation.Rotate:
//						m = Matrix.Translation(-tr) *
//						Matrix.RotationX((difference.X + difference.Y) * angle.X * rotation) *
//						Matrix.RotationY((difference.X + difference.Y) * angle.Y * rotation) *
//						Matrix.RotationZ((difference.X + difference.Y) * angle.Z * rotation) *
//						Matrix.Translation(tr);
//						part.scale(m, model.scene);
//						break;
//
//					case MoveOperation.Scale:
//						m = Matrix.Translation(-tr) *
//						Matrix.Scaling(scale.X, scale.Y, scale.Z) *
//						Matrix.Translation(tr);
//						part.scale(m, model.scene);
//						break;
//
//					default:
//						part.move(direction, model.scene);
//						break;
//					}
//
//					model.PreserveConnections(part as CPart);
//				}
//				else if(con != null)
//				{
//					con.point += direction;
//				}
//
//				model.UpdateViews(Updates.Move);
//				bcontrol[idx].start_x = e.X;
//				bcontrol[idx].start_y = e.Y;
//			}
//
//		}
//
//		idx = (int)MButton.Middle;
//		if(bcontrol[idx].down)
//		{
//			zoom += (e.Y - bcontrol[idx].start_y) * zoom_step;
//			if(zoom < zoom_step) zoom = zoom_step;
//			bcontrol[idx].start_x = e.X;
//			bcontrol[idx].start_y = e.Y;
//			invalidated = true;
//		}
//
//		idx = (int)MButton.Right;
//		if(bcontrol[idx].down)
//		{
//			Vector3f tr = new Vector3f(
//					(float)((e.X - bcontrol[idx].start_x) * move_step),
//					(float)((e.Y - bcontrol[idx].start_y) * move_step),
//					0f);
//			translation += tr;
//			bcontrol[idx].start_x = e.X;
//			bcontrol[idx].start_y = e.Y;
//			invalidated = true;
//		}
//
//		if(invalidated) Invalidate();
//	}
//
//	private void button1_Click(Object sender, System.EventArgs e)
//	{
//		show_grid = !show_grid;
//		if(show_grid) button1.Text = "Hide Grid";
//		else button1.Text = "Show Grid";
//		Invalidate();
//	}
//
//	private void button2_Click(Object sender, System.EventArgs e)
//	{
//		translation = new Vector3f(0f, 0f, 0f);
//		zoom = 20.0;
//		Invalidate();
//	}

} // End of class Wire Frame View
