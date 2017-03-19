package hu.csega.superstition.animatool;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import hu.csega.superstition.tools.presentation.ToolView;

public class SceneEditor extends ToolView {

	private JButton change_button;
	private JLabel label1;
	private JLabel label2;
	private JTextField numericUpDown1;
	private JTextArea change_box;
	private JButton copy_button;
	private JTextField numericUpDown2;
	private JTextField lerp_start;
	private JTextField lerp_end;
	private JTextField lerp_from;
	private JTextField lerp_until;
	private JButton lerp_button;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;

	public SceneEditor()
	{
		// This call is required by the Windows.Forms Form Designer.
		InitializeComponent();

		// TODO: Add any initialization after the InitializeComponent call

	}

	/// <summary>
	/// Clean up any resources being used.
	/// </summary>
	protected void Dispose(boolean disposing )
	{
		if( disposing )
		{
			if(components != null)
			{
				components.Dispose();
			}
		}
		base.Dispose( disposing );
	}

	/// <summary>
	/// Required method for Designer support - do not modify
	/// the contents of this method with the code editor.
	/// </summary>
	private void InitializeComponent()
	{
		this.change_box = new System.Windows.Forms.TextBox();
		this.change_button = new JButton();
		this.label1 = new JLabel();
		this.label2 = new JLabel();
		this.numericUpDown1 = new JTextField();
		this.copy_button = new JButton();
		this.numericUpDown2 = new JTextField();
		this.lerp_start = new JTextField();
		this.lerp_end = new JTextField();
		this.lerp_from = new JTextField();
		this.lerp_until = new JTextField();
		this.lerp_button = new JButton();
		this.label3 = new JLabel();
		this.label4 = new JLabel();
		this.label5 = new JLabel();
		this.label6 = new JLabel();
		((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).BeginInit();
		((System.ComponentModel.ISupportInitialize)(this.numericUpDown2)).BeginInit();
		((System.ComponentModel.ISupportInitialize)(this.lerp_start)).BeginInit();
		((System.ComponentModel.ISupportInitialize)(this.lerp_end)).BeginInit();
		((System.ComponentModel.ISupportInitialize)(this.lerp_from)).BeginInit();
		((System.ComponentModel.ISupportInitialize)(this.lerp_until)).BeginInit();
		this.SuspendLayout();
		//
		// change_box
		//
		this.change_box.Location = new System.Drawing.Point(160, 16);
		this.change_box.Name = "change_box";
		this.change_box.Size = new System.Drawing.Size(56, 20);
		this.change_box.TabIndex = 0;
		this.change_box.Text = "1";
		this.change_box.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
		this.change_box.TextChanged += new System.EventHandler(this.change_box_TextChanged);
		//
		// change_button
		//
		this.change_button.Location = new System.Drawing.Point(224, 16);
		this.change_button.Name = "change_button";
		this.change_button.Size = new System.Drawing.Size(64, 24);
		this.change_button.TabIndex = 1;
		this.change_button.Text = "Change";
		this.change_button.Click += new System.EventHandler(this.change_button_Click);
		//
		// label1
		//
		this.label1.Location = new System.Drawing.Point(8, 16);
		this.label1.Name = "label1";
		this.label1.Size = new System.Drawing.Size(152, 24);
		this.label1.TabIndex = 2;
		this.label1.Text = "Maximum number of scenes:";
		this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// label2
		//
		this.label2.Location = new System.Drawing.Point(8, 48);
		this.label2.Name = "label2";
		this.label2.Size = new System.Drawing.Size(56, 24);
		this.label2.TabIndex = 3;
		this.label2.Text = "Scene:";
		this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// numericUpDown1
		//
		this.numericUpDown1.Location = new System.Drawing.Point(64, 48);
		this.numericUpDown1.Maximum = new System.Decimal(new int[] {
				10000,
				0,
				0,
				0});
		this.numericUpDown1.Minimum = new System.Decimal(new int[] {
				1,
				0,
				0,
				-2147483648});
		this.numericUpDown1.Name = "numericUpDown1";
		this.numericUpDown1.Size = new System.Drawing.Size(56, 20);
		this.numericUpDown1.TabIndex = 4;
		this.numericUpDown1.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
		this.numericUpDown1.ValueChanged += new System.EventHandler(this.numericUpDown1_ValueChanged);
		//
		// copy_button
		//
		this.copy_button.Location = new System.Drawing.Point(136, 48);
		this.copy_button.Name = "copy_button";
		this.copy_button.Size = new System.Drawing.Size(112, 24);
		this.copy_button.TabIndex = 5;
		this.copy_button.Text = "Copy from Scene:";
		this.copy_button.Click += new System.EventHandler(this.copy_button_Click);
		//
		// numericUpDown2
		//
		this.numericUpDown2.Location = new System.Drawing.Point(248, 48);
		this.numericUpDown2.Maximum = new System.Decimal(new int[] {
				10000,
				0,
				0,
				0});
		this.numericUpDown2.Name = "numericUpDown2";
		this.numericUpDown2.Size = new System.Drawing.Size(56, 20);
		this.numericUpDown2.TabIndex = 6;
		this.numericUpDown2.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
		//
		// lerp_start
		//
		this.lerp_start.Location = new System.Drawing.Point(80, 152);
		this.lerp_start.Maximum = new System.Decimal(new int[] {
				10000,
				0,
				0,
				0});
		this.lerp_start.Name = "lerp_start";
		this.lerp_start.Size = new System.Drawing.Size(56, 20);
		this.lerp_start.TabIndex = 7;
		this.lerp_start.ValueChanged += new System.EventHandler(this.lerp_start_ValueChanged);
		//
		// lerp_end
		//
		this.lerp_end.Location = new System.Drawing.Point(208, 152);
		this.lerp_end.Maximum = new System.Decimal(new int[] {
				10000,
				0,
				0,
				0});
		this.lerp_end.Name = "lerp_end";
		this.lerp_end.Size = new System.Drawing.Size(56, 20);
		this.lerp_end.TabIndex = 8;
		this.lerp_end.ValueChanged += new System.EventHandler(this.lerp_end_ValueChanged);
		//
		// lerp_from
		//
		this.lerp_from.Location = new System.Drawing.Point(72, 184);
		this.lerp_from.Maximum = new System.Decimal(new int[] {
				10000,
				0,
				0,
				0});
		this.lerp_from.Name = "lerp_from";
		this.lerp_from.Size = new System.Drawing.Size(56, 20);
		this.lerp_from.TabIndex = 9;
		this.lerp_from.ValueChanged += new System.EventHandler(this.lerp_from_ValueChanged);
		//
		// lerp_until
		//
		this.lerp_until.Location = new System.Drawing.Point(168, 184);
		this.lerp_until.Maximum = new System.Decimal(new int[] {
				10000,
				0,
				0,
				0});
		this.lerp_until.Name = "lerp_until";
		this.lerp_until.Size = new System.Drawing.Size(56, 20);
		this.lerp_until.TabIndex = 10;
		this.lerp_until.ValueChanged += new System.EventHandler(this.lerp_until_ValueChanged);
		//
		// lerp_button
		//
		this.lerp_button.Location = new System.Drawing.Point(232, 184);
		this.lerp_button.Name = "lerp_button";
		this.lerp_button.Size = new System.Drawing.Size(48, 24);
		this.lerp_button.TabIndex = 11;
		this.lerp_button.Text = "Lerp";
		this.lerp_button.Click += new System.EventHandler(this.lerp_button_Click);
		//
		// label3
		//
		this.label3.Location = new System.Drawing.Point(8, 152);
		this.label3.Name = "label3";
		this.label3.Size = new System.Drawing.Size(72, 16);
		this.label3.TabIndex = 12;
		this.label3.Text = "Start Scene:";
		//
		// label4
		//
		this.label4.Location = new System.Drawing.Point(144, 152);
		this.label4.Name = "label4";
		this.label4.Size = new System.Drawing.Size(64, 16);
		this.label4.TabIndex = 13;
		this.label4.Text = "End Scene:";
		//
		// label5
		//
		this.label5.Location = new System.Drawing.Point(8, 184);
		this.label5.Name = "label5";
		this.label5.Size = new System.Drawing.Size(64, 16);
		this.label5.TabIndex = 14;
		this.label5.Text = "Write from:";
		//
		// label6
		//
		this.label6.Location = new System.Drawing.Point(136, 184);
		this.label6.Name = "label6";
		this.label6.Size = new System.Drawing.Size(32, 16);
		this.label6.TabIndex = 15;
		this.label6.Text = "Until:";
		//
		// SceneEditor
		//
		this.Controls.Add(this.label6);
		this.Controls.Add(this.label5);
		this.Controls.Add(this.label4);
		this.Controls.Add(this.label3);
		this.Controls.Add(this.lerp_button);
		this.Controls.Add(this.lerp_until);
		this.Controls.Add(this.lerp_from);
		this.Controls.Add(this.lerp_end);
		this.Controls.Add(this.lerp_start);
		this.Controls.Add(this.numericUpDown2);
		this.Controls.Add(this.copy_button);
		this.Controls.Add(this.numericUpDown1);
		this.Controls.Add(this.label2);
		this.Controls.Add(this.label1);
		this.Controls.Add(this.change_button);
		this.Controls.Add(this.change_box);
		this.Name = "SceneEditor";
		this.Size = new System.Drawing.Size(320, 216);
		((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).EndInit();
		((System.ComponentModel.ISupportInitialize)(this.numericUpDown2)).EndInit();
		((System.ComponentModel.ISupportInitialize)(this.lerp_start)).EndInit();
		((System.ComponentModel.ISupportInitialize)(this.lerp_end)).EndInit();
		((System.ComponentModel.ISupportInitialize)(this.lerp_from)).EndInit();
		((System.ComponentModel.ISupportInitialize)(this.lerp_until)).EndInit();
		this.ResumeLayout(false);

	}

	public void UpdateView(Updates update)
	{
		CModel model = GetData() as CModel;
		this.change_box.Text = model.max_scenes.ToString();
		this.numericUpDown1.Value = model.scene;
	}

	protected void InitializeView()
	{

	}

	protected void CloseView()
	{

	}

	private void change_button_Click(Object sender, System.EventArgs e)
	{
		int max = int.Parse(change_box.Text);
		if(max < 1) max = 1;
		if(max > 10000) max = 10000;

		CModel model = GetData() as CModel;

		foreach(CPart part in model.parts)
		{
			Matrix[] matrices = new Matrix[max];
			Vector3[] center_points = new Vector3[max];
			for(int i = 0; i < max; i++)
			{
				if(i < model.max_scenes)
				{
					matrices[i] = part.model_transform[i];
					center_points[i] = part.center_point[i];
				}
				else
				{
					matrices[i] = part.model_transform[model.max_scenes-1];
					center_points[i] = part.center_point[model.max_scenes-1];
				}
			}
			part.model_transform = matrices;
			part.center_point = center_points;
		}

		model.max_scenes = max;
		change_box.Text = max.ToString();

		if(model.scene > max)
		{
			model.scene = 0;
			model.UpdateViews();
		}
	}

	private void numericUpDown1_ValueChanged(Object sender, System.EventArgs e)
	{
		int scene = (int)numericUpDown1.Value;
		CModel model = GetData() as CModel;
		if(scene < 0) scene = model.max_scenes - 1;
		if(scene >= model.max_scenes) scene = 0;
		numericUpDown1.Value = scene;
		model.scene = scene;
		model.UpdateViews();
	}

	private void copy_button_Click(Object sender, System.EventArgs e)
	{
		CModel model = GetData() as CModel;
		int dest = (int)numericUpDown1.Value;
		int source = (int)numericUpDown2.Value;

		if(source < 0)
		{
			numericUpDown2.Value = 0;
			return;
		}

		if(source >= model.max_scenes)
		{
			numericUpDown2.Value = model.max_scenes - 1;
			return;
		}

		if(source == dest) return;

		foreach(CPart part in model.parts)
		{
			part.model_transform[dest] =
					part.model_transform[source];
			part.center_point[dest] =
					part.center_point[source];
		}

		model.UpdateViews();
	}

	private void lerp_start_ValueChanged(Object sender, System.EventArgs e)
	{
		CModel model = GetData() as CModel;
		if((int)lerp_start.Value < 0) lerp_start.Value = 0;
		if((int)lerp_start.Value >= model.max_scenes)
			lerp_start.Value = model.max_scenes - 1;
	}

	private void lerp_end_ValueChanged(Object sender, System.EventArgs e)
	{
		CModel model = GetData() as CModel;
		if((int)lerp_end.Value < 0) lerp_end.Value = 0;
		if((int)lerp_end.Value >= model.max_scenes)
			lerp_end.Value = model.max_scenes - 1;
	}

	private void lerp_from_ValueChanged(Object sender, System.EventArgs e)
	{
		CModel model = GetData() as CModel;
		if((int)lerp_from.Value < 0) lerp_from.Value = 0;
		if(lerp_from.Value > lerp_until.Value)
			lerp_from.Value = lerp_until.Value;
	}

	private void lerp_until_ValueChanged(Object sender, System.EventArgs e)
	{
		CModel model = GetData() as CModel;
		if((int)lerp_until.Value < lerp_from.Value)
			lerp_until.Value = lerp_from.Value;
		if((int)lerp_until.Value >= model.max_scenes)
			lerp_until.Value = model.max_scenes - 1;
	}

	private void lerp_button_Click(Object sender, System.EventArgs e)
	{
		// Read data
		int start, from, until, end;
		start = (int)lerp_start.Value;
		end = (int)lerp_end.Value;
		from = (int)lerp_from.Value;
		until = (int)lerp_until.Value;
		CModel model = GetData() as CModel;

		// Modify each mesh matrix
		foreach(CPart p in model.parts)
		{
			// Reading from vectors
			Vector3 center_start = p.center_point[start];
			Vector3[] m_start = MatrixToVectors(p.model_transform[start]);

			// Reading end vectors
			Vector3 center_end = p.center_point[end];
			Vector3[] m_end = MatrixToVectors(p.model_transform[end]);

			// Actual vectors
			Vector3 center;
			Vector3[] m = new Vector3[m_start.Length];

			for(int i = from; i <= until; i++)
			{
				// Calculating appropriate vectors
				float act = (float)(i - from) / (float)(until - from);
				center = Vector3.Lerp(center_start, center_end, act);
				for(int j = 0; j < m.Length; j++)
				{
					m[j] = Vector3.Lerp(m_start[j], m_end[j], act);
					if(j < 2) m[j].Normalize();
				}

				p.center_point[i] = center;
				p.model_transform[i] = VectorsToMatrix(m);

				//				// Preserve connections
				//				model.scene = i;
				//				model.PreserveConnections(p);
			}
		}

		int scene = model.scene;
		for(int i = from; i <= until; i++)
		{
			model.scene = i;
			model.PreserveConnections();
		}
		model.scene = scene;
		model.UpdateViews();

	}

	private Vector3[] MatrixToVectors(Matrix m)
	{
		Matrix i = m;
		i.Invert();
		Vector3[] ret = new Vector3[3];

		// Position Vector: Zero

		// Target: Direction (maybe X = 1)
		ret[0] = Vector3.TransformCoordinate(
				new Vector3(0f, 0f, 1f), i);

		// Up: Y = 1
		ret[1] = Vector3.TransformCoordinate(
				new Vector3(0f, 1f, 0f), i);

		// Scaling Vector
		Matrix inv = Matrix.LookAtLH(
				new Vector3(0f, 0f, 0f), ret[0], ret[1]);
		inv.Invert();
		Matrix only_scale = m * inv;
		ret[2] = Vector3.TransformCoordinate(
				new Vector3(1f, 1f, 1f), only_scale);

		return ret;
	}

	private Matrix VectorsToMatrix(Vector3[] v)
	{
		Matrix ret = Matrix.LookAtLH(
				new Vector3(0f, 0f, 0f), v[0], v[1]);
		//		ret.Invert();
		ret = Matrix.Scaling(v[2].X, v[2].Y, v[2].Z) * ret;

		return ret;
	}

	private void change_box_TextChanged(Object sender, System.EventArgs e)
	{

	}

} // End of class SceneEditor