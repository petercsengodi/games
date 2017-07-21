package hu.csega.superstition.tools.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import hu.csega.superstition.tools.UpdateScope;

public class WireFrameView extends ToolView {

	private static final double ZOOM_STEP = 0.1;
	private static final double MOVE_STEP = 1.0;
	private static final float rotation = (float)Math.PI / 18;

	private Matrix4f view, invert;
	private Vector4f translation;
	private double zoom;
	private ButtonControl[] bcontrol;
	private boolean show_grid;
	private Vector3f angle = new Vector3f();

	private JPanel buttons;
	private JButton button1;
	private JButton button2;
	private JLabel label1;
	private ToolCanvas canvas;

	public WireFrameView() {

		initializeComponent();

		view = new Matrix4f();
		translation = new Vector4f();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	private void initializeComponent() {
		this.setLayout(new BorderLayout());

		this.label1 = new JLabel();
		this.label1.setText("label1");

		this.button1 = new JButton();
		this.button1.setText("Hide Grid");
		// this.button1.Click += new System.EventHandler(this.button1_Click);

		this.button2 = new JButton();
		this.button2.setText("Center");
		// this.button2.Click += new System.EventHandler(this.button2_Click);

		this.buttons = new JPanel();
		this.buttons.setLayout(new FlowLayout());
		this.buttons.add(this.label1);
		this.buttons.add(this.button2);
		this.buttons.add(this.button1);
		//		this.MouseUp += new System.Windows.Forms.MouseEventHandler(this.WireFrameView_MouseUp);
		//		this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.WireFrameView_MouseMove);
		//		this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.WireFrameView_MouseDown);

		this.add(this.buttons, BorderLayout.NORTH);

		this.canvas = new ToolCanvas(this);
		this.add(this.canvas, BorderLayout.SOUTH);
	}

	public void setView(Perspectives perspective)
	{
		view = new Matrix4f();
		invert = new Matrix4f();

		switch(perspective) {

		case Left:
			view.m20(1f);
			view.m11(-1f);
			invert.m02(1f);
			invert.m11(-1f);
			label1.setText("Left");
			angle.x = rotation;
			break;

		case Top:
			view.m00(1f);
			view.m21(-1f);
			invert.m00(1f);
			invert.m12(-1f);
			label1.setText("Top");
			angle.y = rotation;
			break;

		default:
			view.m00(1f);
			view.m11(-1f);
			invert.m00(1f);
			invert.m11(-1f);
			label1.setText("Front");
			angle.z = -rotation;
			break;
		}

	}

	@Override
	public void updateView(UpdateScope update) {
		invalidate();
	}

	@Override
	public void initializeView() {
		bcontrol = new ButtonControl[3];
		for(int i = 0; i < 3; i++)
		{
			bcontrol[i] = new ButtonControl();
			bcontrol[i].down = false;
		}
		show_grid = true;
		zoom = 50.0;
	}

	@Override
	public void closeView() {
	}

	@Override
	public void paintCanvas(ToolCanvas canvas, Graphics2D g) {
		//		if(!isInitialized()) {
		//			super.paint(g);
		//			return;
		//		}

		//		Brush brush_normal = new SolidBrush(Color.Black);
		//		Pen pen_normal = new Pen(brush_normal);
		//		Brush brush_selected = new SolidBrush(Color.Red);
		//		Pen pen_selected = new Pen(brush_selected, 3f);
		//		Brush brush_grid = new SolidBrush(Color.LightGray);
		//		Pen pen_grid = new Pen(brush_grid);
		//		Brush brush_origo = new SolidBrush(Color.Orange);
		//		Pen pen_origo = new Pen(brush_origo, 2f);

		if(show_grid) {
			drawGrid(g);
		}

		g.setColor(Color.black);
		g.drawLine(-10, 0, 10, 0);
		g.drawLine(0, -10, 0, 10);

		//		// Drawing origo
		//		Vector3f vorigo = new Vector3f();
		//		Point porigo = Transform(vorigo);
		//		g.DrawLine(pen_origo, porigo.X - 10, porigo.Y,
		//				porigo.X + 10, porigo.Y);
		//		g.DrawLine(pen_origo, porigo.X, porigo.Y - 10,
		//				porigo.X, porigo.Y + 10);
		//
		//		Pen pen;
		//		Point pos;
		//
		//		Vector3f p1, p2;
		//
		//		CModel model = (CModel)GetData();
		//		IPart sel = model.Selected as IPart;
		//		for(CFigure f : model.figures)
		//		{
		//			for(CTriangle t : f.triangles)
		//			{
		//				/*
		//				// test // TODO: Delete
		//				Vector3f normal = Vector3.Cross(
		//					t.edges[1].from.position - t.edges[0].from.position,
		//					t.edges[2].from.position - t.edges[0].from.position);
		//				normal.Normalize();
		//				 */
		//				for(CEdge e : t.edges)
		//				{
		//					// Draw edge
		//					pen = pen_normal;
		//					p1 = e.from.position;
		//					p2 = e.to.position;
		//
		//					/*						// test // TODO: Delete
		//					float res;
		//					res = Vector3.Dot(new Vector3f(1f, 0f, 0f), normal);
		//					if(res > 0) p1 = p1*(1f + 1f/p1.Length());
		//					res = Vector3.Dot(new Vector3f(1f, 0f, 0f), normal);
		//					if(res > 0) p2 = p2*(1f + 1f/p2.Length());
		//					 */
		//					if(sel != null)
		//					{
		//						if(sel.hasPart(e)) pen = pen_selected;
		//						if(sel.hasPart(e.from))
		//							p1 = Vector3.TransformCoordinate(p1,
		//									model.SelectedMatrix);
		//						if(sel.hasPart(e.to))
		//							p2 = Vector3.TransformCoordinate(p2,
		//									model.SelectedMatrix);
		//					}
		//					g.DrawLine(pen, Transform(p1), Transform(p2));
		//
		//
		//					// Draw vertex
		//					pen = pen_normal;
		//					if(sel != null)
		//					{
		//						if(sel.hasPart(e.from))
		//						{
		//							pen = pen_selected;
		//						}
		//					}
		//					pos = Transform(p1);
		//					g.DrawRectangle(pen, pos.X - 4, pos.Y - 4, 9, 9);
		//
		//					/*
		//
		//					// test // TODO : delete
		//					pen = pen_normal;
		//					if(sel != null)
		//					{
		//						if(sel.hasPart(e.to))
		//						{
		//							pen = pen_selected;
		//						}
		//					}
		//					pos = Transform(p2);
		//					g.DrawRectangle(pen, pos.X - 4, pos.Y - 4, 9, 9);
		//
		//					// test // TODO: delete
		//					Brush brush_blue = new SolidBrush(Color.Blue);
		//					Pen pen_blue = new Pen(brush_blue, 3f);
		//					if((sel != null) && sel.hasPart(t))
		//					{
		//						for(int i = 0; i < 3; i++)
		//						{
		//							CTriangle tn = t.neighbours[i];
		//							Vector3f ps = new Vector3f(0f, 0f, 0f);
		//							int s = (i + 1) % 3;
		//							if(tn.edges[0].from.Equals(t.edges[s].from)) ps = tn.edges[0].from.position;
		//							if(tn.edges[1].from.Equals(t.edges[s].from)) ps = tn.edges[1].from.position;
		//							if(tn.edges[2].from.Equals(t.edges[s].from)) ps = tn.edges[2].from.position;
		//
		//							Vector3f normal2 = Vector3.Cross(
		//								tn.edges[1].from.position - tn.edges[0].from.position,
		//								tn.edges[2].from.position - tn.edges[0].from.position);
		//							normal.Normalize();
		//							res = Vector3.Dot(new Vector3f(1f, 0f, 0f), normal2);
		//							if(res > 0) ps = ps*(1f + 1f/ps.Length());
		//
		//							p1 = t.edges[i].from.position;
		//							p2 = t.edges[s].from.position;
		//
		//							// test // TODO: Delete
		//							res = Vector3.Dot(new Vector3f(1f, 0f, 0f), normal);
		//							if(res > 0) p1 = p1*(1f + 1f/p1.Length());
		//							res = Vector3.Dot(new Vector3f(1f, 0f, 0f), normal);
		//							if(res > 0) p2 = p2*(1f + 1f/p2.Length());
		//
		//							g.DrawLine(pen_blue, Transform(p1), Transform(ps));
		//							g.DrawLine(pen_blue, Transform(p2), Transform(ps));
		//						}
		//
		//					}
		//					brush_blue.dispose();
		//					pen_blue.dispose();
		//
		//					 */
		//
		//				} // end of for:Edge
		//			} // end of for:Triangle
		//		} // end of for:Figure
		//
		//		pen_grid.dispose();
		//		brush_grid.dispose();
		//		pen_selected.dispose();
		//		brush_selected.dispose();
		//		pen_normal.dispose();
		//		brush_normal.dispose();
		//		pen_origo.dispose();
		//		brush_origo.dispose();
	}

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

	private void drawGrid(Graphics g)
	{
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
	}

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
	//		if(e.Button == MouseButtons.Left)
	//		{
	//
	//			Point position;
	//			CModel model = (CModel)GetData();
	//			Point m = new Point(e.X - this.Width / 2,
	//					e.Y - this.Height / 2);
	//
	//			for(CFigure figure : model.figures)
	//			{
	//				for(CVertex vertex : figure.vertices)
	//				{
	//					position = Transform(vertex.position);
	//					if((m.X >= position.X - 4) && (m.X <= position.X + 4)
	//							&& (m.Y >= position.Y - 4) && (m.Y <= position.Y + 4))
	//					{
	//						model.Selected = vertex;
	//						return;
	//					}
	//				}
	//
	//			} // end of for each
	//
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
	//
	//		if(e.Button == MouseButtons.Left)
	//		{
	//			CModel model = GetData() as CModel;
	//			IPart part = model.Selected as IPart;
	//			if(part == null) return;
	//			Operation op = new ScaleSelected(part,
	//					model.SelectedMatrix);
	//			model.Memento.Push(op);
	//			model.SelectedMatrix = Matrix.Identity;
	//			model.UpdateViews(Updates.Move);
	//		}
	//	}
	//
	//	private void WireFrameView_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e)
	//	{
	//		if(!Initialized) return;
	//		boolean invalidated = false;
	//		int idx;
	//
	//		idx = (int)MButton.Left;
	//		if(bcontrol[idx].down)
	//		{
	//			CModel model = (CModel)GetData();
	//			IPart part = model.Selected as IPart;
	//			if(part != null)
	//			{
	//				Vector3f to_point = InverseTransform(e.X, e.Y);
	//				Point origin = new Point(bcontrol[idx].start_x, bcontrol[idx].start_y);
	//				Point difference = new Point(e.X - origin.X, e.Y - origin.Y);
	//				Vector3f from_point = InverseTransform(origin.X, origin.Y);
	//				Vector3f direction = to_point - from_point;
	//				//	Vector3f direction = InverseTransform(difference.X, difference.Y);
	//				Vector3f tr = part.centerPoint();
	//				Vector3f scale = new Vector3f(0f, 0f, 0f);
	//				if(Math.Abs(tr.X - from_point.X) > double.Epsilon)
	//					scale.X = (to_point.X - tr.X) / (from_point.X - tr.X);
	//				if(Math.Abs(tr.Y - from_point.Y) > double.Epsilon)
	//					scale.Y = (to_point.Y - tr.Y) / (from_point.Y - tr.Y);
	//				if(Math.Abs(tr.Z - from_point.Z) > double.Epsilon)
	//					scale.Z = (to_point.Z - tr.Z) / (from_point.Z - tr.Z);
	//
	//				if(model.SnapToGrid &&
	//						T3DCreator.GetOperatoion() == 0)
	//				{
	//					direction = SnapToGrid(direction);
	//				}
	//				if((direction.X != 0f) ||
	//						(direction.Y != 0f) ||
	//						(direction.Z != 0f))
	//				{
	//					switch(T3DCreator.GetOperatoion())
	//					{
	//					case MoveOperation.Rotate:
	//						model.SelectedMatrix = model.SelectedMatrix *
	//						Matrix.Translation(-tr) *
	//						Matrix.RotationX((difference.X + difference.Y) * angle.X * rotation) *
	//						Matrix.RotationY((difference.X + difference.Y) * angle.Y * rotation) *
	//						Matrix.RotationZ((difference.X + difference.Y) * angle.Z * rotation) *
	//						Matrix.Translation(tr);
	//						break;
	//
	//					case MoveOperation.Scale:
	//						model.SelectedMatrix = model.SelectedMatrix *
	//						Matrix.Translation(-tr) *
	//						Matrix.Scaling(scale.X, scale.Y, scale.Z) *
	//						Matrix.Translation(tr);
	//						break;
	//
	//					default:
	//						model.SelectedMatrix = model.SelectedMatrix
	//						* Matrix.Translation(direction);
	//						break;
	//					}
	//
	//					model.UpdateViews(Updates.Move);
	//					bcontrol[idx].start_x = e.X;
	//					bcontrol[idx].start_y = e.Y;
	//				}
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
