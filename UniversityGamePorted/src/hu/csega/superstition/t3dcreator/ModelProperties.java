package hu.csega.superstition.t3dcreator;

import javax.swing.JPanel;

import hu.csega.superstition.tools.Updates;

public class ModelProperties extends JPanel {

	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;

	public ModelProperties()
	{
		//
		// Required for Windows Form Designer support
		//
		InitializeComponent();

		//
		// TODO: Add any constructor code after InitializeComponent call
		//
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
		this.label1 = new System.Windows.Forms.Label();
		this.bounding_box = new System.Windows.Forms.Label();
		this.button1 = new System.Windows.Forms.Button();
		this.numericUpDown1 = new System.Windows.Forms.NumericUpDown();
		((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).BeginInit();
		this.SuspendLayout();
		//
		// label1
		//
		this.label1.Location = new System.Drawing.Point(8, 8);
		this.label1.Name = "label1";
		this.label1.Size = new System.Drawing.Size(80, 16);
		this.label1.TabIndex = 0;
		this.label1.Text = "Bounding Box:";
		//
		// bounding_box
		//
		this.bounding_box.Location = new System.Drawing.Point(88, 8);
		this.bounding_box.Name = "bounding_box";
		this.bounding_box.Size = new System.Drawing.Size(200, 16);
		this.bounding_box.TabIndex = 2;
		this.bounding_box.Text = "[0.0; 0.0; 0.0]";
		//
		// button1
		//
		this.button1.Location = new System.Drawing.Point(144, 40);
		this.button1.Name = "button1";
		this.button1.Size = new System.Drawing.Size(144, 24);
		this.button1.TabIndex = 3;
		this.button1.Text = "Resize";
		this.button1.Click += new System.EventHandler(this.button1_Click);
		//
		// numericUpDown1
		//
		this.numericUpDown1.DecimalPlaces = 3;
		this.numericUpDown1.Location = new System.Drawing.Point(8, 40);
		this.numericUpDown1.Maximum = new System.Decimal(new int[] {
				1000,
				0,
				0,
				0});
		this.numericUpDown1.Name = "numericUpDown1";
		this.numericUpDown1.RightToLeft = System.Windows.Forms.RightToLeft.No;
		this.numericUpDown1.Size = new System.Drawing.Size(128, 20);
		this.numericUpDown1.TabIndex = 4;
		this.numericUpDown1.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
		this.numericUpDown1.Value = new System.Decimal(new int[] {
				100,
				0,
				0,
				0});
		//
		// ModelProperties
		//
		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		this.ClientSize = new System.Drawing.Size(292, 266);
		this.Controls.Add(this.numericUpDown1);
		this.Controls.Add(this.button1);
		this.Controls.Add(this.bounding_box);
		this.Controls.Add(this.label1);
		this.Name = "ModelProperties";
		this.Text = "ModelProperties";
		((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).EndInit();
		this.ResumeLayout(false);

	}



	protected override void OnClosing(CancelEventArgs e)
	{
		e.Cancel = true;
		this.Hide();
	}

	private System.Windows.Forms.Label label1;
	private System.Windows.Forms.Label bounding_box;
	private System.Windows.Forms.Button button1;
	private System.Windows.Forms.NumericUpDown numericUpDown1;

	protected CModel model;

	private void button1_Click(object sender, System.EventArgs e)
	{
		model.Resize((float)numericUpDown1.Value / 100f);
		model.Memento.Clear();
		model.UpdateViews(Updates.Move);
		CountBoundingBox();
	}

	public CModel Model
	{
		get{ return model; }
		set
		{
			model = value;
			CountBoundingBox();
		}
	}

	public void CountBoundingBox()
	{
		Vector3 box = model.CountBoundingBox();
		bounding_box.Text = "[" + box.X + "; " +
				box.Y + "; " + box.Z + "]";
	}

}