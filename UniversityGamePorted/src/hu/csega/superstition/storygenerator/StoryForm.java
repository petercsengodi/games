package hu.csega.superstition.storygenerator;

import javax.swing.JPanel;

public class StoryForm extends JPanel
{
	private StoryGenerator.PanelDrawer GrView;
	private System.Windows.Forms.MainMenu mMenu;
	private System.Windows.Forms.MenuItem mFile;
	private System.Windows.Forms.MenuItem mOpen;
	private System.Windows.Forms.MenuItem mSave;
	private System.Windows.Forms.MenuItem mExit;
	private System.Windows.Forms.MenuItem mNew;
	private System.Windows.Forms.ToolBarButton tNode;
	private System.Windows.Forms.ToolBarButton tLink;
	private System.Windows.Forms.ToolBar tBar;
	private System.Windows.Forms.ImageList tImages;
	private System.ComponentModel.IContainer components;

	private System.Windows.Forms.ToolBarButton SelectedToolBar = null;

	private ArrayList nodes;
	private Node SelectedNode;
	private boolean LeftMouseDown, RightMouseDown;
	private int scrX, scrY, dX, dY;
	private System.Windows.Forms.ToolBarButton tDelNode;
	private System.Windows.Forms.PropertyGrid properties;
	private Brush brushBlack, brushRed;
	private Graphics paint;
	private System.Windows.Forms.ToolBarButton tUnlink;
	private Pen pen;

	private boolean changed;
	private System.Windows.Forms.MenuItem mSaveAs;
	private string workingFile;
	private string actualDir;
	private System.Windows.Forms.ToolBarButton tPreview;
	private System.Windows.Forms.ToolBarButton tDirectXP;
	private Bitmap backBuffer = null;

	public StoryForm()
	{
		//
		// Required for Windows Form Designer support
		//
		InitializeComponent();

		nodes = new ArrayList();
		LeftMouseDown = false;
		RightMouseDown = false;

		scrX = scrY = 0;
		changed = false;
		workingFile = "";
		actualDir = System.IO.Directory.GetCurrentDirectory();

		Node.StaticInitializer(0);

		backBuffer = new Bitmap(GrView.Bounds.Width, GrView.Bounds.Height);
	}

	/// <summary>
	/// Clean up any resources being used.
	/// </summary>
	protected void Dispose( boolean disposing )
	{
		if( disposing )
		{
			if (components != null)
			{
				components.Dispose();
			}

			if(backBuffer != null)
			{
				backBuffer.Dispose();
			}
		}
		base.Dispose( disposing );
	}

	// Not Panel, PanelDrawer !!!

	/// <summary>
	/// Required method for Designer support - do not modify
	/// the contents of this method with the code editor.
	/// </summary>
	private void InitializeComponent()
	{
		this.components = new System.ComponentModel.Container();
		System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(StoryForm));
		this.properties = new System.Windows.Forms.PropertyGrid();
		this.GrView = new StoryGenerator.PanelDrawer();
		this.mMenu = new System.Windows.Forms.MainMenu();
		this.mFile = new System.Windows.Forms.MenuItem();
		this.mNew = new System.Windows.Forms.MenuItem();
		this.mOpen = new System.Windows.Forms.MenuItem();
		this.mSave = new System.Windows.Forms.MenuItem();
		this.mSaveAs = new System.Windows.Forms.MenuItem();
		this.mExit = new System.Windows.Forms.MenuItem();
		this.tBar = new System.Windows.Forms.ToolBar();
		this.tNode = new System.Windows.Forms.ToolBarButton();
		this.tLink = new System.Windows.Forms.ToolBarButton();
		this.tDelNode = new System.Windows.Forms.ToolBarButton();
		this.tUnlink = new System.Windows.Forms.ToolBarButton();
		this.tPreview = new System.Windows.Forms.ToolBarButton();
		this.tImages = new System.Windows.Forms.ImageList(this.components);
		this.tDirectXP = new System.Windows.Forms.ToolBarButton();
		this.SuspendLayout();
		//
		// properties
		//
		this.properties.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
				| System.Windows.Forms.AnchorStyles.Right)));
		this.properties.CommandsVisibleIfAvailable = true;
		this.properties.LargeButtons = false;
		this.properties.LineColor = System.Drawing.SystemColors.ScrollBar;
		this.properties.Location = new System.Drawing.Point(400, 48);
		this.properties.Name = "properties";
		this.properties.Size = new System.Drawing.Size(232, 344);
		this.properties.TabIndex = 1;
		this.properties.Text = "Properties";
		this.properties.ViewBackColor = System.Drawing.SystemColors.Window;
		this.properties.ViewForeColor = System.Drawing.SystemColors.WindowText;
		this.properties.PropertyValueChanged += new System.Windows.Forms.PropertyValueChangedEventHandler(this.properties_PropertyValueChanged);
		//
		// GrView
		//
		this.GrView.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
				| System.Windows.Forms.AnchorStyles.Left)
				| System.Windows.Forms.AnchorStyles.Right)));
		this.GrView.BackColor = System.Drawing.Color.White;
		this.GrView.Location = new System.Drawing.Point(0, 48);
		this.GrView.Name = "GrView";
		this.GrView.Size = new System.Drawing.Size(400, 344);
		this.GrView.TabIndex = 2;
		this.GrView.Resize += new System.EventHandler(this.GrView_Resized);
		this.GrView.MouseUp += new System.Windows.Forms.MouseEventHandler(this.GrView_MouseUp);
		this.GrView.Paint += new System.Windows.Forms.PaintEventHandler(this.GrView_Paint);
		this.GrView.MouseMove += new System.Windows.Forms.MouseEventHandler(this.GrView_MouseMove);
		this.GrView.MouseDown += new System.Windows.Forms.MouseEventHandler(this.GrView_MouseDown);
		//
		// mMenu
		//
		this.mMenu.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
				this.mFile});
		//
		// mFile
		//
		this.mFile.Index = 0;
		this.mFile.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
				this.mNew,
				this.mOpen,
				this.mSave,
				this.mSaveAs,
				this.mExit});
		this.mFile.Text = "File";
		//
		// mNew
		//
		this.mNew.Index = 0;
		this.mNew.Text = "New";
		this.mNew.Click += new System.EventHandler(this.mNew_Click);
		//
		// mOpen
		//
		this.mOpen.Index = 1;
		this.mOpen.Text = "Open";
		this.mOpen.Click += new System.EventHandler(this.mOpen_Click);
		//
		// mSave
		//
		this.mSave.Index = 2;
		this.mSave.Text = "Save";
		this.mSave.Click += new System.EventHandler(this.mSave_Click);
		//
		// mSaveAs
		//
		this.mSaveAs.Index = 3;
		this.mSaveAs.Text = "Save As ";
		this.mSaveAs.Click += new System.EventHandler(this.mSaveAs_Click);
		//
		// mExit
		//
		this.mExit.Index = 4;
		this.mExit.Text = "Exit";
		this.mExit.Click += new System.EventHandler(this.mExit_Click);
		//
		// tBar
		//
		this.tBar.Buttons.AddRange(new System.Windows.Forms.ToolBarButton[] {
				this.tNode,
				this.tLink,
				this.tDelNode,
				this.tUnlink,
				this.tPreview,
				this.tDirectXP});
		this.tBar.DropDownArrows = true;
		this.tBar.ImageList = this.tImages;
		this.tBar.Location = new System.Drawing.Point(0, 0);
		this.tBar.Name = "tBar";
		this.tBar.ShowToolTips = true;
		this.tBar.Size = new System.Drawing.Size(632, 42);
		this.tBar.TabIndex = 3;
		this.tBar.ButtonClick += new System.Windows.Forms.ToolBarButtonClickEventHandler(this.tBar_ButtonClick);
		//
		// tNode
		//
		this.tNode.ImageIndex = 1;
		this.tNode.Style = System.Windows.Forms.ToolBarButtonStyle.ToggleButton;
		this.tNode.Text = "Node";
		this.tNode.ToolTipText = "Puts a Story Node on Screen.";
		//
		// tLink
		//
		this.tLink.ImageIndex = 2;
		this.tLink.Style = System.Windows.Forms.ToolBarButtonStyle.ToggleButton;
		this.tLink.Text = "Link";
		this.tLink.ToolTipText = "Creates a Link between Nodes.";
		//
		// tDelNode
		//
		this.tDelNode.ImageIndex = 3;
		this.tDelNode.Text = "Delete Node";
		this.tDelNode.ToolTipText = "Deletes selected Node";
		//
		// tUnlink
		//
		this.tUnlink.ImageIndex = 4;
		this.tUnlink.Text = "Unlink";
		this.tUnlink.ToolTipText = "Removes all links from Node";
		//
		// tPreview
		//
		this.tPreview.ImageIndex = 5;
		this.tPreview.Text = "Normal Preview";
		this.tPreview.ToolTipText = "Opens Normal Preview Window";
		//
		// tImages
		//
		this.tImages.ImageSize = new System.Drawing.Size(16, 16);
		this.tImages.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("tImages.ImageStream")));
		this.tImages.TransparentColor = System.Drawing.Color.White;
		//
		// tDirectXP
		//
		this.tDirectXP.ImageIndex = 5;
		this.tDirectXP.Text = "DirectX Preview";
		this.tDirectXP.ToolTipText = "Opens DirectX Preview Window";
		//
		// StoryForm
		//
		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		this.ClientSize = new System.Drawing.Size(632, 393);
		this.Controls.Add(this.tBar);
		this.Controls.Add(this.GrView);
		this.Controls.Add(this.properties);
		this.Menu = this.mMenu;
		this.Name = "StoryForm";
		this.Text = "Story Generator Program - Csengodi Peter (JOT2FY)";
		this.Closing += new System.ComponentModel.CancelEventHandler(this.StoryForm_Closing);
		this.ResumeLayout(false);

	}


	/// <summary>
	/// The main entry point for the application.
	/// </summary>
	[STAThread]
			static void Main()
	{
		Application.Run(new StoryForm());
	}

	private void GrView_Paint(Object sender, System.Windows.Forms.PaintEventArgs e)
	{
		paint = Graphics.FromImage(backBuffer);
		Node node;

		paint.Clear(Color.White);
		brushBlack = new SolidBrush(Color.Black);
		brushRed = new SolidBrush(Color.DarkRed);
		pen = new Pen(brushBlack, 1);
		Font font = new Font(FontFamily.GenericSansSerif, 10, FontStyle.Regular);

		for(Object o : nodes)
		{
			node = o as Node;
			node.Function = new Node.NodeFunction(DrawLink);
		}

		for(Object o : nodes)
		{
			node = o as Node;
			paint.DrawString(node.Text, font, brushBlack, node.X + scrX + 10, node.Y + scrY - 10);
		}

		for(Object o : nodes)
		{
			node = o as Node;
			if(node == SelectedNode) paint.FillRectangle(brushRed, node.X - 10 + scrX, node.Y - 10 + scrY, 20, 20);
			else paint.FillRectangle(brushBlack, node.X + scrX - 10, node.Y + scrY - 10, 20, 20);
			if(node.Map != null) paint.DrawImage(node.Map, node.X - 8 + scrX, node.Y - 8 + scrY, 16, 16);
		}

		pen.Dispose(); pen = null;
		brushBlack.Dispose(); brushBlack = null;
		brushRed.Dispose(); brushRed = null;
		paint = null;

		e.Graphics.DrawImage(backBuffer, 0, 0);
	}

	private void mExit_Click(Object sender, System.EventArgs e)
	{
		Close();
	}

	private void StoryForm_Closing(Object sender, System.ComponentModel.CancelEventArgs e)
	{
		Ask ask = new Ask();
		DialogResult result = ask.ShowDialog();
		ask.Dispose();
		if(result == DialogResult.Yes) Save(false);
		if(result == DialogResult.Cancel) e.Cancel = true;
	}

	private void Save(boolean newpath)
	{
		if(!changed) return;

		if((workingFile.CompareTo("") == 0) || newpath)
		{
			SaveFileDialog saveFile = new SaveFileDialog();
			saveFile.Filter = "Xml Files (*.xml)|*.xml|All Files (*.*)|*.*";
			saveFile.InitialDirectory = @"..\..\..\Superstition\bin\maps";
			if(saveFile.ShowDialog() == DialogResult.Cancel)
			{
				saveFile.Dispose();
				return;
			}

			workingFile = saveFile.FileName;
			saveFile.Dispose();
		}

		XmlDocument StoryDocument = new XmlDocument();
		StoryDocument.AppendChild(StoryDocument.CreateXmlDeclaration("1.0", "UTF-8", "yes"));
		XmlNode story = StoryDocument.AppendChild(StoryDocument.CreateElement("Story"));
		XmlAttribute ns = story.Attributes.Append(StoryDocument.CreateAttribute("xmlns"));
		ns.Value = "http://tempuri.org/StorySchema.xsd";

		Node node;
		for(Object o : nodes)
		{
			node = o as Node;
			node.WriteToXMLNode(story, StoryDocument, workingFile);
		}

		StoryDocument.Save(workingFile);
	}

	private void ClearToolBar()
	{
		SelectedToolBar = null;
		tNode.Pushed = false;
		tLink.Pushed = false;
	}

	private void tBar_ButtonClick(Object sender, System.Windows.Forms.ToolBarButtonClickEventArgs e)
	{
		if((e.Button == tNode) || (e.Button == tLink))
		{
			if(SelectedToolBar != null)
			{
				if(e.Button != SelectedToolBar) e.Button.Pushed = false;
				else SelectedToolBar = null;
			}
			else if(e.Button.Pushed) SelectedToolBar = e.Button;
			if(e.Button == tNode) DeSelect();
		}
		else
		{
			changed = true;
			ClearToolBar();

			if((e.Button == tDelNode) && (SelectedNode != null))
			{
				Node node;
				for(Object o : nodes)
				{
					node = o as Node;
					node.RemoveLink = SelectedNode;
				}
				nodes.Remove(SelectedNode);

				DeSelect();
				GrView.Invalidate();
			}
			else if((e.Button == tUnlink) && (SelectedNode != null))
			{
				Node node;
				for(Object o : nodes)
				{
					node = o as Node;
					node.RemoveLink = SelectedNode;
					SelectedNode.RemoveLink = node;
				}

				DeSelect();
				GrView.Invalidate();
			}
			else if(e.Button == tPreview)
			{
				NormalPreview preview = new NormalPreview(nodes);
				preview.ShowDialog();
				preview.Dispose();
			}
			else if(e.Button == tDirectXP)
			{
				DirectXPreview preview = new DirectXPreview(nodes);
				preview.ShowDialog();
				preview.Dispose();
			}
		}
	}

	public void AddNode(int x, int y)
	{
		changed = true;
		Node node = new Node();
		node.X = x; node.Y = y;
		nodes.Add(node);
		properties.SelectedObject = SelectedNode = node;
	}

	public boolean SelectNode(int x, int y)
	{
		Node node, preNode = SelectedNode;
		SelectedNode = null;
		for(Object o : nodes)
		{
			node = o as Node;
			if((x <= node.X + 10) && (x >= node.X - 10) && (y <= node.Y + 10) && (y >= node.Y - 10))
				SelectedNode = node;
		}

		properties.SelectedObject = SelectedNode;
		return (preNode != SelectedNode);
	}

	public void AddLink(int x, int y)
	{
		changed = true;
		if(SelectedNode == null)
		{
			SelectNode(x, y);
			if(SelectedNode == null)
			{
				ClearToolBar();
				properties.SelectedObject = null;
			} else properties.SelectedObject = SelectedNode;
			return;
		}

		Node node, node2 = null;
		for(Object o : nodes)
		{
			node = o as Node;
			if((x <= node.X + 10) && (x >= node.X - 10) && (y <= node.Y + 10) && (y >= node.Y - 10))
				node2 = node;
		}

		if(node2 == null) { ClearToolBar(); return;}

		SelectedNode.NewLink = node2;
		node2.NewLink = SelectedNode;
		ClearToolBar();
		DeSelect();
	}

	private void GrView_MouseDown(Object sender, System.Windows.Forms.MouseEventArgs e)
	{
		if(e.Button == MouseButtons.Right)
		{
			RightMouseDown = true;
			dX = e.X;
			dY = e.Y;
		}
		else if(e.Button == MouseButtons.Left)
		{
			LeftMouseDown = true;

			if(SelectedToolBar == tNode)
			{
				ClearToolBar();
				AddNode(e.X - scrX, e.Y - scrY);
				GrView.Invalidate();
			}
			else if(SelectedToolBar == null)
			{
				if(SelectNode(e.X - scrX, e.Y - scrY)) GrView.Invalidate();
				properties.SelectedObject = SelectedNode;
			}
			else if(SelectedToolBar == tLink)
			{
				AddLink(e.X - scrX, e.Y - scrY);
				properties.SelectedObject = null;
			}
		}
	}

	private void GrView_MouseMove(Object sender, System.Windows.Forms.MouseEventArgs e)
	{
		if(RightMouseDown)
		{
			scrX += (e.X - dX);
			scrY += (e.Y - dY);
			dX = e.X;
			dY = e.Y;
			GrView.Invalidate();
		}

		if(LeftMouseDown && (SelectedNode != null))
		{
			changed = true;
			SelectedNode.X = e.X - scrX;
			SelectedNode.Y = e.Y - scrY;
			GrView.Invalidate();
		}
	}

	private void GrView_MouseUp(Object sender, System.Windows.Forms.MouseEventArgs e)
	{
		if(e.Button == MouseButtons.Left) LeftMouseDown = false;
		if(e.Button == MouseButtons.Right) RightMouseDown = false;
	}

	private void DeSelect()
	{
		if(SelectedNode != null) GrView.Invalidate();
		SelectedNode = null;
		properties.SelectedObject = null;
	}

	private void properties_PropertyValueChanged(Object s, System.Windows.Forms.PropertyValueChangedEventArgs e)
	{
		GrView.Invalidate();
	}

	private void DrawLink(Node baseNode, Node linkNode)
	{
		paint.DrawLine(pen, baseNode.X + scrX, baseNode.Y + scrY,
				linkNode.X + scrX, linkNode.Y + scrY);
	}

	private void mSave_Click(Object sender, System.EventArgs e)
	{
		Save(false);
	}

	private void mNew_Click(Object sender, System.EventArgs e)
	{
		changed = false;
		nodes.Clear();
		GrView.Invalidate();
		workingFile = "";
		Node.StaticInitializer(0);
		scrX = scrY = 0;
	}

	private void mOpen_Click(Object sender, System.EventArgs e)
	{
		if(changed)
		{
			Ask ask = new Ask("Do you really want to open an other file?");
			DialogResult result = ask.ShowDialog();
			ask.Dispose();
			if(result == DialogResult.Yes) Save(false);
			if(result == DialogResult.Cancel) return;
		}

		OpenFileDialog openFile = new OpenFileDialog();
		openFile.Filter = "Xml Files (*.xml)|*.xml|All Files (*.*)|*.*";
		openFile.InitialDirectory = @"..\..\..\Superstition\bin\maps";
		openFile.CheckFileExists = true;

		if(openFile.ShowDialog() == DialogResult.Cancel)
		{
			openFile.Dispose();
			return;
		}

		nodes.Clear(); Node node;

		XmlDocument StoryDocument = new XmlDocument();

		StoryDocument.Load(workingFile = openFile.FileName);


		XmlTextReader textReader = new XmlTextReader(workingFile = openFile.FileName);
		XmlValidatingReader validator = new XmlValidatingReader(textReader);
		validator.Schemas.Add(null, new XmlTextReader(actualDir + "\\..\\..\\StorySchema.xsd"));
		validator.ValidationType = ValidationType.Schema;
		validator.ValidationEventHandler += new System.Xml.Schema.ValidationEventHandler(validator_ValidationEventHandler);
		StoryDocument.Load(validator);


		openFile.Dispose();
		GrView.Invalidate();

		XmlNode story = StoryDocument.DocumentElement, nodeXml;

		IEnumerator en = story.ChildNodes.GetEnumerator();
		while(en.MoveNext())
		{
			nodeXml = en.Current as XmlNode;
			nodes.Add(Node.NodeFromXml(nodeXml));
		}

		int i = 0;
		en = story.ChildNodes.GetEnumerator();
		while(en.MoveNext())
		{
			nodeXml = en.Current as XmlNode;
			node = nodes[i] as Node;
			node.SetUpLinksFromXml(nodeXml, nodes);
			i++;
		}

		changed = false;
		Node.StaticInitializer(nodes.Count);
		scrX = scrY = 0;
	}

	private void mSaveAs_Click(Object sender, System.EventArgs e)
	{
		Save(true);
	}

	private void validator_ValidationEventHandler(Object sender, System.Xml.Schema.ValidationEventArgs e)
	{
		if(e.Message != null) MessageBox.Show(this, "Error Validation Load File : \n" + e.Message);
		else MessageBox.Show(this, "Error Validation Load File");
	}

	private void GrView_Resized(Object sender, System.EventArgs e)
	{
		backBuffer.Dispose();
		backBuffer = new Bitmap(GrView.Width, GrView.Height);
	}
}