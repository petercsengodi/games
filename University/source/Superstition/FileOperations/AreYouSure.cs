using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace FileOperations
{
	/// <summary>
	/// Summary description for AreYouSure.
	/// </summary>
	public class AreYouSure : System.Windows.Forms.Form
	{
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.Button yes_button;
		private System.Windows.Forms.Button no_button;
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public AreYouSure()
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

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.label1 = new System.Windows.Forms.Label();
			this.yes_button = new System.Windows.Forms.Button();
			this.no_button = new System.Windows.Forms.Button();
			this.SuspendLayout();
			// 
			// label1
			// 
			this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(238)));
			this.label1.Location = new System.Drawing.Point(8, 8);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(152, 40);
			this.label1.TabIndex = 0;
			this.label1.Text = "Do you want to save?";
			this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// yes_button
			// 
			this.yes_button.DialogResult = System.Windows.Forms.DialogResult.Yes;
			this.yes_button.Location = new System.Drawing.Point(8, 56);
			this.yes_button.Name = "yes_button";
			this.yes_button.Size = new System.Drawing.Size(72, 24);
			this.yes_button.TabIndex = 1;
			this.yes_button.Text = "Yes";
			// 
			// no_button
			// 
			this.no_button.DialogResult = System.Windows.Forms.DialogResult.No;
			this.no_button.Location = new System.Drawing.Point(88, 56);
			this.no_button.Name = "no_button";
			this.no_button.Size = new System.Drawing.Size(72, 24);
			this.no_button.TabIndex = 2;
			this.no_button.Text = "No";
			// 
			// AreYouSure
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(170, 88);
			this.Controls.Add(this.no_button);
			this.Controls.Add(this.yes_button);
			this.Controls.Add(this.label1);
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
			this.Name = "AreYouSure";
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
			this.Text = "Save";
			this.ResumeLayout(false);

		}
		#endregion
	}
}
