// Everything that's about to be used.
using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using System.Threading;

namespace Superstition
{
	using Engine; // The Engine modul.
	using Model; // The Model modul.
	
	/// <summary>
	/// The Application.
	/// </summary>
	public class MainFrame : System.Windows.Forms.Form
	{
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		private Engine engine = null;
		private System.Threading.Timer timer = null;
		private Thread RenderThread = null; // Rendering Thread Identifier
		private bool ApplicationAlive = true;

		static int counter1 = 0, counter2 = 0;
		public static int lastfps = 0;
		static int TimerSetup = 10;

		// Console Output
		public delegate void OutPut(string text);
		public static OutPut WriteConsole;

		/// <summary>
		/// MainFrame Constructor.
		/// </summary>
		public MainFrame()
		{
			//
			// Required for Windows Form Designer support
			//
			InitializeComponent();

			// Console OutPut
			WriteConsole = new OutPut(Console.WriteLine);
			// WriteConsole = new OutPut(NullOutPut);

		}

		/// <summary>
		/// Starting Application, triggered by an Event.
		/// </summary>
		/// <param name="sender">Not used.</param>
		/// <param name="e">Not used.</param>
		public void ApplicationStart(object sender, System.EventArgs e)
		{
			InitWindow init = new InitWindow();
			DialogResult res = init.ShowDialog();

			if(res != DialogResult.OK) 
			{
				Close();
				return;
			}
			
			Parameters parameters = init.Parameters;
			parameters.main = this;
			init.Dispose();

			if(!parameters.FullScreen)
			{
				switch(parameters.size)
				{
					case ScreenSize.W320H240:
						Size = new Size(320, 240);
						break;

					case ScreenSize.W640H480:
						Size = new Size(640, 480);
						break;

					case ScreenSize.W800H600:
						Size = new Size(800, 600);
						break;

					case ScreenSize.W1024H768:
						Size = new Size(800, 600);
						break;

					default:
						Size = new Size(640, 480);
						break;
				}
			}

			this.CenterToScreen();

			engine = new Engine(parameters);
			if(engine.Start())
			{
				timer = new System.Threading.Timer(new TimerCallback(DoPeriod), engine, 0, TimerSetup);
				if(timer != null) WriteConsole("Periodic Timer Started.");
				(RenderThread = new Thread(new ThreadStart(CyclicThreadFunc))).Start();
			}
		}

		/// <summary>
		/// Thread Function for Rendering, with immediately repeating.
		/// </summary>
		public void CyclicThreadFunc()
		{
			WriteConsole("Rendering Thread Started.");
			while(ApplicationAlive)
			{
				engine.Render();

				/* Console Output for Testing*/
				counter1++;
				Thread.Sleep(0); // Repeat as soon as possible;
			}
			WriteConsole("Rendering Thread Closed.");
		}

		/// <summary>
		/// Does nothing.
		/// </summary>
		/// <param name="text">Input should written to Console</param>
		public void NullOutPut(string text){ }

		/// <summary>
		/// Closing process. Triggered by an Event.
		/// </summary>
		/// <param name="e">Given to parent class.</param>
		protected override void OnClosing(CancelEventArgs e)
		{
			Clean(); // The program seems to need this.
			base.OnClosing (e);
		}
		
		/// <summary>
		/// Closing Application.
		/// </summary>
		public void Clean()
		{
			WriteConsole("Cleaning Process Started.");

			if(timer != null)
			{
				timer.Dispose();
				timer = null;
				WriteConsole("Periodic Timer Finished.");
			}
			
			ApplicationAlive = false;
			if(RenderThread != null) RenderThread.Join();
			RenderThread = null;
			
			if(engine != null) engine.Close();
			engine = null;
			
			WriteConsole("Cleaning Process Finished.");
		}

		#region DISPOSE pattern

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if (components != null) 
				{
					components.Dispose();
				}
			}

			base.Dispose( disposing );
		}

		/// <summary>
		/// Destructor. There were no Dispose.
		/// </summary>
		~MainFrame()
		{
			// cleaning
			Dispose(false);
			Clean();
		}

		/// <summary>
		/// Cleaning. No need to do any further finalization.
		/// </summary>
		public new void Dispose() /* "new" kell??? */
		{
			// cleaning
			Dispose(true);
			Clean();
			// no Destructor called
			GC.SuppressFinalize(this);
		}

		#endregion

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			// 
			// MainFrame
			// 
			this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
			this.ClientSize = new System.Drawing.Size(232, 158);
			this.Name = "MainFrame";
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
			this.Text = "Superstition";
			this.Load += new System.EventHandler(this.ApplicationStart);

		}
		#endregion

		/// <summary>
		/// The main entry point for the application.
		/// </summary>
		[STAThread]
		static void Main() 
		{
			//			Application.Run(new MainFrame());
			using(MainFrame frame = new MainFrame())
			{ // Creating Main Window
				WriteConsole("Frame Started.");
				frame.Show(); // Window Appears.
				while(frame.Created) Application.DoEvents(); // Handling Window Events
			}
		}

		/// <summary>
		/// Callback Function for Periodic Functionalities. Locks given Object.
		/// </summary>
		/// <param name="_period">Periodic Functionality.</param>
		static void DoPeriod(object _period)
		{
			IPeriod period = _period as IPeriod;
			Monitor.Enter(period);
			period.Period();
			Monitor.Exit(period);
			
			/* Console Output for Testing*/

			counter2++;
			if(counter2 >= 1000 / TimerSetup)
			{
				//Console.WriteLine("FPS: " + counter1.ToString());
				lastfps = counter1;
				counter1 = counter2 = 0;
			}
		}

	}

}
