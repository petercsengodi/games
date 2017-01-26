package hu.csega.superstition.game;

public class InitWindow extends System.Windows.Forms.Form
{
	private System.Windows.Forms.Label label1;
	private System.Windows.Forms.ComboBox comboBox1;
	private System.Windows.Forms.CheckBox checkBox1;
	private System.Windows.Forms.Button button1;
	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;

	public InitWindow()
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
		this.label1 = new System.Windows.Forms.Label();
		this.comboBox1 = new System.Windows.Forms.ComboBox();
		this.checkBox1 = new System.Windows.Forms.CheckBox();
		this.button1 = new System.Windows.Forms.Button();
		this.SuspendLayout();
		//
		// label1
		//
		this.label1.Location = new System.Drawing.Point(8, 8);
		this.label1.Name = "label1";
		this.label1.Size = new System.Drawing.Size(80, 24);
		this.label1.TabIndex = 0;
		this.label1.Text = "Resolution:";
		this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// comboBox1
		//
		this.comboBox1.Items.AddRange(new object[] {
				"default",
				"1024 x 768",
				"800 x 600",
				"640 x 480",
		"320 x 240"});
		this.comboBox1.Location = new System.Drawing.Point(104, 8);
		this.comboBox1.Name = "comboBox1";
		this.comboBox1.Size = new System.Drawing.Size(128, 21);
		this.comboBox1.TabIndex = 1;
		this.comboBox1.Text = "default";
		//
		// checkBox1
		//
		this.checkBox1.Checked = true;
		this.checkBox1.CheckState = System.Windows.Forms.CheckState.Checked;
		this.checkBox1.Location = new System.Drawing.Point(8, 40);
		this.checkBox1.Name = "checkBox1";
		this.checkBox1.Size = new System.Drawing.Size(144, 24);
		this.checkBox1.TabIndex = 2;
		this.checkBox1.Text = "Full Screen";
		//
		// button1
		//
		this.button1.DialogResult = System.Windows.Forms.DialogResult.OK;
		this.button1.Location = new System.Drawing.Point(168, 40);
		this.button1.Name = "button1";
		this.button1.Size = new System.Drawing.Size(64, 24);
		this.button1.TabIndex = 3;
		this.button1.Text = "Start";
		//
		// InitWindow
		//
		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		this.ClientSize = new System.Drawing.Size(240, 72);
		this.Controls.Add(this.button1);
		this.Controls.Add(this.checkBox1);
		this.Controls.Add(this.comboBox1);
		this.Controls.Add(this.label1);
		this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
		this.Name = "InitWindow";
		this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
		this.Text = "Start Game";
		this.ResumeLayout(false);

	}


	public Parameters Parameters
	{
		get
		{
			Parameters ret = new Parameters();
			ret.size = (ScreenSize)comboBox1.SelectedIndex;
			ret.FullScreen = checkBox1.Checked;
			return ret;
		}
	}
}