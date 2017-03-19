package hu.csega.superstition.storygenerator;

import javax.swing.JPanel;

public class Ask extends JPanel
{
	private System.Windows.Forms.Label askLabel;
	private System.Windows.Forms.Button bOK;
	private System.Windows.Forms.Button bCancel;
	private System.Windows.Forms.CheckBox bCheck;
	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;

	public Ask()
	{
		//
		// Required for Windows Form Designer support
		//
		InitializeComponent();
	}

	public Ask(String label)
	{
		//
		// Required for Windows Form Designer support
		//
		InitializeComponent();
		askLabel.Text = label;
	}
	/// <summary>
	/// Clean up any resources being used.
	/// </summary>
	protected void dispose( boolean disposing )
	{
		if( disposing )
		{
			if(components != null)
			{
				components.dispose();
			}
		}
		super.dispose( disposing );
	}


	/// <summary>
	/// Required method for Designer support - do not modify
	/// the contents of this method with the code editor.
	/// </summary>
	private void InitializeComponent()
	{
		this.askLabel = new System.Windows.Forms.Label();
		this.bOK = new System.Windows.Forms.Button();
		this.bCancel = new System.Windows.Forms.Button();
		this.bCheck = new System.Windows.Forms.CheckBox();
		this.SuspendLayout();
		//
		// askLabel
		//
		this.askLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(238)));
		this.askLabel.Location = new System.Drawing.Point(64, 8);
		this.askLabel.Name = "askLabel";
		this.askLabel.Size = new System.Drawing.Size(304, 32);
		this.askLabel.TabIndex = 0;
		this.askLabel.Text = "Do you really want to quit?";
		this.askLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// bOK
		//
		this.bOK.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(238)));
		this.bOK.Location = new System.Drawing.Point(184, 72);
		this.bOK.Name = "bOK";
		this.bOK.Size = new System.Drawing.Size(112, 32);
		this.bOK.TabIndex = 2;
		this.bOK.Text = "Yes, of course.";
		this.bOK.Click += new System.EventHandler(this.bOK_Click);
		//
		// bCancel
		//
		this.bCancel.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(238)));
		this.bCancel.Location = new System.Drawing.Point(312, 72);
		this.bCancel.Name = "bCancel";
		this.bCancel.Size = new System.Drawing.Size(112, 32);
		this.bCancel.TabIndex = 1;
		this.bCancel.Text = "No way, mistake.";
		this.bCancel.Click += new System.EventHandler(this.bCancel_Click);
		//
		// bCheck
		//
		this.bCheck.Checked = true;
		this.bCheck.CheckState = System.Windows.Forms.CheckState.Checked;
		this.bCheck.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(238)));
		this.bCheck.Location = new System.Drawing.Point(16, 72);
		this.bCheck.Name = "bCheck";
		this.bCheck.Size = new System.Drawing.Size(160, 32);
		this.bCheck.TabIndex = 3;
		this.bCheck.Text = "Save changes on exit.";
		//
		// Ask
		//
		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		this.ClientSize = new System.Drawing.Size(432, 110);
		this.ControlBox = false;
		this.Controls.Add(this.bCheck);
		this.Controls.Add(this.bCancel);
		this.Controls.Add(this.bOK);
		this.Controls.Add(this.askLabel);
		this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
		this.MaximumSize = new System.Drawing.Size(440, 144);
		this.MinimumSize = new System.Drawing.Size(440, 144);
		this.Name = "Ask";
		this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
		this.Text = "Ask";
		this.ResumeLayout(false);

	}


	private void bOK_Click(Object sender, System.EventArgs e)
	{

		if(this.bCheck.Checked) this.DialogResult = DialogResult.Yes;
		else this.DialogResult = DialogResult.No;
		this.Close();
	}

	private void bCancel_Click(Object sender, System.EventArgs e)
	{
		this.DialogResult = DialogResult.Cancel;
		this.Close();
	}
}
