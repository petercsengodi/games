package hu.csega.superstition.t3dcreator;

import javax.swing.JPanel;

import hu.csega.superstition.tools.Updates;

public class FigureProperties extends JPanel {

	private T3DCreatorWindows.ColorDisplay colorDisplay1;
	private T3DCreatorWindows.ColorDisplay colorDisplay2;
	private T3DCreatorWindows.ColorDisplay colorDisplay3;
	private System.Windows.Forms.Label label2;
	private System.Windows.Forms.Button button1;
	private System.Windows.Forms.Button button3;

	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;

	public FigureProperties()
	{
		//
		// Required for Windows Form Designer support
		//
		InitializeComponent();

	}

	/// <summary>
	/// Clean up any resources being used.
	/// </summary>
	protected override void Dispose( bool disposing )
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
		this.colorDisplay1 = new T3DCreatorWindows.ColorDisplay();
		this.colorDisplay2 = new T3DCreatorWindows.ColorDisplay();
		this.colorDisplay3 = new T3DCreatorWindows.ColorDisplay();
		this.label2 = new System.Windows.Forms.Label();
		this.button1 = new System.Windows.Forms.Button();
		this.button3 = new System.Windows.Forms.Button();
		this.SuspendLayout();
		//
		// colorDisplay1
		//
		this.colorDisplay1.Color = System.Drawing.Color.White;
		this.colorDisplay1.Label = "Ambient";
		this.colorDisplay1.Location = new System.Drawing.Point(8, 8);
		this.colorDisplay1.Name = "colorDisplay1";
		this.colorDisplay1.Size = new System.Drawing.Size(144, 56);
		this.colorDisplay1.TabIndex = 0;
		this.colorDisplay1.ChangeEvent += new T3DCreatorWindows.ColorChanged(this.ChangeAmbientColor);
		//
		// colorDisplay2
		//
		this.colorDisplay2.Color = System.Drawing.Color.White;
		this.colorDisplay2.Label = "Diffuse";
		this.colorDisplay2.Location = new System.Drawing.Point(8, 72);
		this.colorDisplay2.Name = "colorDisplay2";
		this.colorDisplay2.Size = new System.Drawing.Size(144, 56);
		this.colorDisplay2.TabIndex = 1;
		this.colorDisplay2.ChangeEvent += new T3DCreatorWindows.ColorChanged(this.ChangeDiffuseColor);
		//
		// colorDisplay3
		//
		this.colorDisplay3.Color = System.Drawing.Color.Black;
		this.colorDisplay3.Label = "Emissive";
		this.colorDisplay3.Location = new System.Drawing.Point(8, 136);
		this.colorDisplay3.Name = "colorDisplay3";
		this.colorDisplay3.Size = new System.Drawing.Size(144, 56);
		this.colorDisplay3.TabIndex = 2;
		this.colorDisplay3.ChangeEvent += new T3DCreatorWindows.ColorChanged(this.ChangeEmissiveColor);
		//
		// label2
		//
		this.label2.Location = new System.Drawing.Point(160, 8);
		this.label2.Name = "label2";
		this.label2.Size = new System.Drawing.Size(64, 48);
		this.label2.TabIndex = 5;
		this.label2.Text = "Automatic Texture Arranging";
		//
		// button1
		//
		this.button1.Location = new System.Drawing.Point(160, 64);
		this.button1.Name = "button1";
		this.button1.Size = new System.Drawing.Size(56, 24);
		this.button1.TabIndex = 6;
		this.button1.Text = "Sphere";
		this.button1.Click += new System.EventHandler(this.button1_Click);
		//
		// button3
		//
		this.button3.Location = new System.Drawing.Point(160, 96);
		this.button3.Name = "button3";
		this.button3.Size = new System.Drawing.Size(64, 24);
		this.button3.TabIndex = 10;
		this.button3.Text = "Random";
		this.button3.Click += new System.EventHandler(this.button3_Click);
		//
		// FigureProperties
		//
		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		this.ClientSize = new System.Drawing.Size(232, 198);
		this.Controls.Add(this.button3);
		this.Controls.Add(this.button1);
		this.Controls.Add(this.label2);
		this.Controls.Add(this.colorDisplay3);
		this.Controls.Add(this.colorDisplay2);
		this.Controls.Add(this.colorDisplay1);
		this.Name = "FigureProperties";
		this.Text = "FigureProperties";
		this.ResumeLayout(false);

	}


	private CFigure figure;
	public CFigure Figure
	{
		get{ return figure; }
		set
		{
			figure = value;
			colorDisplay1.Color = Color.FromArgb(figure.ambient_color);
			colorDisplay2.Color = Color.FromArgb(figure.diffuse_color);
			colorDisplay3.Color = Color.FromArgb(figure.emissive_color);
		}
	}

	private CModel model;
	public CModel Model
	{
		get { return model; }
		set { model = value; }
	}

	private void ChangeAmbientColor(Color color)
	{
		Operation operation = new ChangeMaterial(figure,
				color.ToArgb(),
				figure.diffuse_color,
				figure.emissive_color);
		model.Memento.Push(operation);
		model.UpdateViews(Updates.Full);
	}

	private void ChangeDiffuseColor(Color color)
	{
		Operation operation = new ChangeMaterial(figure,
				figure.ambient_color,
				color.ToArgb(),
				figure.emissive_color);
		model.Memento.Push(operation);
		model.UpdateViews(Updates.Full);
	}

	private void ChangeEmissiveColor(Color color)
	{
		Operation operation = new ChangeMaterial(figure,
				figure.ambient_color,
				figure.diffuse_color,
				color.ToArgb());
		model.Memento.Push(operation);
		model.UpdateViews(Updates.Full);
	}

	protected override void OnClosing(CancelEventArgs e)
	{
		e.Cancel = true;
		this.Hide();
	}

	private void numericUpDown1_ValueChanged(object sender, System.EventArgs e)
	{
	}

	private void button1_Click(object sender, System.EventArgs e)
	{
		figure.SphereTexture();
		model.Memento.Clear();
		model.UpdateViews(Updates.Selection);
	}

	private void button3_Click(object sender, System.EventArgs e)
	{
		figure.RandomTexture();
		model.Memento.Clear();
		model.UpdateViews(Updates.Selection);
	}

}