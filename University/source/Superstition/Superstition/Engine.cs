// Everything that's about to be used
using System;
using System.Drawing;
using System.Threading;
using System.Collections;
using System.Windows.Forms;

using Microsoft.DirectX;
using Microsoft.DirectX.Direct3D;
using DInput = Microsoft.DirectX.DirectInput;

// Namespace of the Engine
namespace Engine
{
	using Superstition;
	using StateControl;

	/// <summary>
	/// Engine for Managed DirectX 3D.
	/// Has the functionality of the graphical presentation.
	///
	/// Controlled by the main application.
	/// </summary>
	public class Engine : IPeriod
	{
		// General attributes
		protected Device device = null; // Direct3D device
		private bool alive = false; // Engine intited and functional - used by Threads
		protected Parameters parameters;

		// Attributes for shadow volume algorithms
		private VertexBuffer volume; // Shadow volume buffer
		private Vector3 light; // Light source for Shadow volume 
		private bool isShadowRendering = false;
		private bool isLighted = false;
		private byte actualReferenceValue;
		private Effect shadowEffect;
		private Matrix view_matrix;

		// Engine Options
		protected EngineOptions options = new EngineOptions();
		public EngineOptions Options { get { return options; } }

		// State Controlling
		protected StateControl state;
		public StateControl State { get { return state; } }

		/// <summary>
		/// Actual rendering device
		/// </summary>
		public Device Device { get { return device; } }

		/// <summary>
		/// Is Engine Alive?
		/// </summary>
		public bool Alive { get { return alive; } }

		/// <summary>
		/// Get or set Light Position for Shadow Volume
		/// </summary>
		public Vector3 LightPosition
		{
			get{ return light; }
			set{ light = value; }
		}

		/// <summary>
		/// True, if shadow is to be rendered
		/// </summary>
		public bool IsShadowRendering { get{ return isShadowRendering; } }

		/// <summary>
		/// True, if map should be lighted
		/// </summary>
		public bool IsLighted { get{ return isLighted; } }

		// Lights
		private int NumberOfLights = 0;
		private Light[] lights;

		// For Disposing
		protected ArrayList DisposeList = null;

		// Font for FPS
		private Microsoft.DirectX.Direct3D.Font fpsFont = null;

		// Input Control
		private DInput.Device KeyDevice = null, MouseDevice = null; // Devices for Direct Input
		private AutoResetEvent MouseFire = null, KeyFire = null; // Synchronisation for Event Handler Threads
		private int bn_left, bn_right, bn_middle; // previous mouse button states

		// For rendering
		private Matrix camera;
		private Material initMaterial;
		private Vector3 Vup;

		/// <summary>
		/// Engine Pre-Intitialization.
		/// Only variable initialization.
		/// </summary>
		public Engine(Parameters parameters)
		{
			this.parameters = parameters;

			MainFrame.WriteConsole("Engine Constructor - Testing Console");
			
			DisposeList = new ArrayList(0);
			state = new StateControl(this, parameters.main);
		}

		/// <summary>
		/// Engine Initialization.
		/// </summary>
		/// <param name="Main">Main frame uses DirectX</param>
		/// <param name="_model">Model that uses the Engine</param>
		/// <param name="windowed">Is Windowed?</param>
		/// <returns>Success</returns>
		public bool Start()
		{
			MainFrame.WriteConsole("Starting Engine Initialization");
			
			#region Screen Device

			// Setting up Direct3D Parameters
			// Common
			PresentParameters pparams = new PresentParameters();
			pparams.Windowed = !parameters.FullScreen;
			pparams.SwapEffect = SwapEffect.Discard;
			
			try
			{
				if(parameters.FullScreen) // Full Screen
				{
					// pparams.SwapEffect = SwapEffect.Copy;
					pparams.BackBufferFormat = Manager.Adapters.Default.CurrentDisplayMode.Format;

					switch(parameters.size)
					{
						case ScreenSize.W320H240:
							pparams.BackBufferWidth = 320;
							pparams.BackBufferHeight = 240;
							break;

						case ScreenSize.W640H480:
							pparams.BackBufferWidth = 640;
							pparams.BackBufferHeight = 480;
							break;

						case ScreenSize.W800H600:
							pparams.BackBufferWidth = 800;
							pparams.BackBufferHeight = 600;
							break;

						case ScreenSize.W1024H768:
							pparams.BackBufferWidth = 1024;
							pparams.BackBufferHeight = 768;
							break;

						default:
							pparams.BackBufferWidth = Manager.Adapters.Default.CurrentDisplayMode.Width;
							pparams.BackBufferHeight = Manager.Adapters.Default.CurrentDisplayMode.Height;
							break;
					}

					pparams.FullScreenRefreshRateInHz = Manager.Adapters.Default.CurrentDisplayMode.RefreshRate;
					pparams.PresentationInterval = PresentInterval.One;
				}

				// using Z-Window Algorythm - D24S8 - Stencil Buffer 8 bit
				pparams.EnableAutoDepthStencil = true;
				pparams.AutoDepthStencilFormat = DepthFormat.D24S8;

				device = new Device(0, DeviceType.Hardware, parameters.main, CreateFlags.SoftwareVertexProcessing, pparams);
				device.DeviceReset += new EventHandler(OnDeviceReset);
				OnDeviceReset(device, null);
				RenderInitializing();
				MainFrame.WriteConsole("Direct3D Inited.");
			}
			catch(Direct3DXException exception)
			{
				state.trigger(false); // Close();
				MainFrame.WriteConsole("FAILURE: " + exception.Message); // Create Report
				return false; // Do not start application
			}

			#endregion

			#region Shadow Effect Initialization

			// Shadow Volume Effect Initialization
			try
			{
				shadowEffect = Effect.FromFile(device, @"..\progs\shadow.fx",
					null, ShaderFlags.None, null);
				shadowEffect.Technique = "ShadowVolume";
			}
			catch(Exception)
			{
				string err;
				shadowEffect = Effect.FromFile(device, @"..\progs\shadow.fx",
					null, ShaderFlags.None, null, out err);
				throw new Exception(err);
			}

			#endregion

			alive = true; // Needed for good thread functionality

			#region Keyboard Device Initialization

			// Direct KeyBoard
			try
			{
				KeyDevice = new DInput.Device(DInput.SystemGuid.Keyboard); // Input Device
				KeyFire = new AutoResetEvent(false); // For Thread Synchronization
				KeyDevice.SetEventNotification(KeyFire); // Thread should be activated because of an Event
				(new Thread(new ThreadStart(HandleKeyboard))).Start(); // Starting Handle Thread 
				if(!parameters.FullScreen) KeyDevice.SetCooperativeLevel(parameters.main,  // If windowed
										 DInput.CooperativeLevelFlags.NonExclusive |   //          not Exclusive
										 DInput.CooperativeLevelFlags.Background);     //          not Foreground
				else KeyDevice.SetCooperativeLevel(parameters.main, 
						 DInput.CooperativeLevelFlags.Exclusive |
						 DInput.CooperativeLevelFlags.Foreground);
				KeyDevice.Properties.BufferSize = 8; // Enough? Too much? Buffer is read very repeatative
				KeyDevice.Acquire();
				MainFrame.WriteConsole("Keyboard Inited.");
			}
			catch(DInput.InputException exception)
			{
				state.trigger(false); // Close();
				MainFrame.WriteConsole("FAILURE: " + exception.Message); // Report
				return false; // Do not start application
			}

			#endregion

			#region Mouse Device Initialization

			// Direct Mouse
			try
			{
				MouseDevice = new DInput.Device(DInput.SystemGuid.Mouse); // Device
				MouseFire = new AutoResetEvent(false); // For Thread Synchronization
				(new Thread(new ThreadStart(HandleMouse))).Start(); // Starting Handle Thread
				if(!parameters.FullScreen) MouseDevice.SetCooperativeLevel(parameters.main, // If windowed
										 DInput.CooperativeLevelFlags.NonExclusive |    //    not Exclusive
										 DInput.CooperativeLevelFlags.Background);      //    not Foreground
				else MouseDevice.SetCooperativeLevel(parameters.main,
						 DInput.CooperativeLevelFlags.Exclusive |
						 DInput.CooperativeLevelFlags.Foreground);
				MouseDevice.SetEventNotification(MouseFire); // Setting Eventhandler
				MouseDevice.Acquire(); // Here?
				MainFrame.WriteConsole("Mouse Inited.");
			}
			catch(DInput.InputException exception)
			{
				state.trigger(false); // Close();
				MainFrame.WriteConsole("FAILURE: " + exception.Message); // Report
				return false; // Do not start application
			}

			#endregion

			#region Other Initializations

			// Other Initializations
			Library.Initialize(this);
			StaticVectorLibrary.StaticInitializer();
			state.trigger(true);
			return alive; // Success

			#endregion

		}

		/// <summary>
		/// Function that runs if Device is reset.
		/// </summary>
		/// <param name="sender">Reference for Device.</param>
		/// <param name="ea">Arguments, not used.</param>
		private void OnDeviceReset(object sender, EventArgs ea)
		{
			#region On Device Reset

			Device device = sender as Device;
			if(fpsFont != null) fpsFont.OnResetDevice();

			// For shadow volume
			volume = new VertexBuffer(typeof(CustomVertex.PositionOnly), 
				12, device, 0, CustomVertex.PositionOnly.Format, Pool.Managed);

			CustomVertex.PositionOnly[] array = (CustomVertex.PositionOnly[]) volume.Lock(0, 0);

			array[0].Position = new Vector3(0f, 0f, 0f);
			array[1].Position = new Vector3(1f, 0f, 0f);
			array[2].Position = new Vector3(1f, 0f, 1f);
			array[3].Position = new Vector3(0f, 1f, 0f);
			array[4].Position = new Vector3(0f, 1f, 1f);
			array[5].Position = new Vector3(0f, 0f, 0f);
			array[6].Position = new Vector3(0f, 0f, 1f);
			array[7].Position = new Vector3(1f, 0f, 1f);
			array[8].Position = new Vector3(0f, 1f, 1f);

			array[9].Position = new Vector3(0f, 0f, 0f);
			array[10].Position = new Vector3(0f, 1f, 0f);
			array[11].Position = new Vector3(1f, 0f, 0f);

			volume.Unlock();
			
			//			AddToDisposeList(volume); // Do not delete when disposing a model

			#endregion
		}

		/// <summary>
		/// Initializing Variables for Rendering;
		/// </summary>
		private void RenderInitializing()
		{
			#region Render Initialization
			
			camera = Matrix.PerspectiveFovLH( (float)(Math.PI / 4.0),
				1.5f, 0.125f /* 1.0f */, 1000.0f);		
			Vup = new Vector3(0f, 1f, 0f);
			initMaterial = new Material();
			initMaterial.Diffuse = Color.White;
			initMaterial.Ambient = Color.White;
			device.RenderState.Ambient = Color.FromArgb(20, 20, 20);
			device.RenderState.Lighting = true;
			device.RenderState.Clipping = true;

			fpsFont = new Microsoft.DirectX.Direct3D.Font(device, 20, 10,
				FontWeight.Bold, 1, false, CharacterSet.Ansi, Precision.Raster,
				FontQuality.Default, PitchAndFamily.FamilyDoNotCare | PitchAndFamily.DefaultPitch,
				"Comic Sans MS");
			
			// DisposeList.Add(fpsFont); // do not dispose when disposing model
			
			lights = new Light[8]; for(int i = 0; i < 8; i++) lights[i] = null;

			#endregion
		}

		/// <summary>
		/// Adds object to Engine's Disposable List, so element will be disposed,
		/// when Engine's closed.
		/// </summary>
		/// <param name="disp">Disposable Object</param>
		public void AddToDisposeList(IDisposable disp)
		{
			DisposeList.Add(disp);
		}

		/// <summary>
		/// Remove object from Engine's Disposable List.
		/// </summary>
		/// <param name="disp">Registered Disposable Object</param>
		public void RemoveFromDisposeList(IDisposable disp)
		{
			DisposeList.Remove(disp);
		}

		/// <summary>
		/// Clears the dispose list.
		/// </summary>
		public void ClearDisposeList()
		{
			foreach(object o in DisposeList)
			{
				IDisposable disposable = o as IDisposable;
				disposable.Dispose();
				//				GC.SuppressFinalize(null);
			}

			DisposeList.Clear();
		}

		/// <summary>
		/// Closing the Engine. It is careful with half-intialized datas.
		/// </summary>
		public void Close()
		{
			alive = false; // Threads should stop

			#region Close Screen Device

			if(device != null)
			{ // Closing Direct3D
				device.Dispose();
				device = null;
			}

			#endregion

			#region Close Keyboard Device

			if(KeyDevice != null)
			{ // Closing Keyboard Handling and used Thread
				if(KeyFire != null) KeyFire.Set(); // Thread notifies that Engine stopped
				KeyDevice.Unacquire(); // Release Keyboard
				KeyDevice.Dispose();
				KeyDevice = null;
			}

			#endregion

			#region Close Mouse Device

			if(MouseDevice != null)
			{ // Closing Mouse Handling and used Thread
				if(MouseFire != null) MouseFire.Set(); // Thread notifies that Engine stopped
				MouseDevice.Unacquire(); // Release Mouse
				MouseDevice.Dispose();
				MouseDevice = null;
			}

			#endregion

			#region Dispose Other Things

			// Other Destructions
			Library.SDispose();

			if(fpsFont != null)
			{
				fpsFont.Dispose();
				fpsFont = null;
			}

			// Dispose Shadow Volume buffer
			if(volume != null)
			{
				volume.Dispose();
				volume = null;
			}

			if(shadowEffect != null)
			{
				shadowEffect.Dispose();
				shadowEffect = null;
			}

			if(DisposeList.Count > 0)
			{
				foreach(object o in DisposeList)
				{
					(o as IDisposable).Dispose();
					//					GC.SuppressFinalize(null);
				}
				DisposeList.Clear();
			}

			#endregion

			MainFrame.WriteConsole("Engine Closed.");
		}

		/// <summary>
		/// Mouse Handling Thread Function.
		/// Locks Model.
		/// </summary>
		private void HandleMouse()
		{
			MainFrame.WriteConsole("Mouse Handle Thread Started");

			#region Mouse Handling

			while(alive)
			{
				MouseFire.WaitOne(-1, false); // Waiting for an Event

				if(MouseDevice == null) continue;
				try{ MouseDevice.Poll(); } // Needs polling
				catch(DInput.InputException){ continue; }
				catch(System.NullReferenceException) { break; } // G���Z
				DInput.MouseState state;
				try{ state = MouseDevice.CurrentMouseState;} // Reading Mouse State
				catch(System.NullReferenceException) { break; } // G���Z
				byte[] buttons = state.GetMouseButtons();

				if(this.state.Model == null) continue; 
				if(parameters.main.WindowState == 
					FormWindowState.Minimized) continue;

				Monitor.Enter(this); // Allocating Engine

				if(buttons[0] != bn_left) // Checking Left Mouse Button
				{
					if(bn_left == 0) this.state.Model.OnButtonDown(0);
					else this.state.Model.OnButtonUp(0);
					bn_left = buttons[0];
				}

				if(buttons[1] != bn_right) // Checking Right Mouse Button
				{
					if(bn_right == 0) this.state.Model.OnButtonDown(1);
					else this.state.Model.OnButtonUp(1);
					bn_right = buttons[1];
				}

				if(buttons[2] != bn_middle) // Checking Middle Mouse Button
				{
					if(bn_middle == 0) this.state.Model.OnButtonDown(2);
					else this.state.Model.OnButtonUp(2);
					bn_middle = buttons[2];
				}

				if((state.X != 0) || (state.Y != 0)) this.state.Model.OnMovement(state.X, state.Y);
				// Checking Mouse Movement

				Monitor.Exit(this); // Releasing Model
				
				//				WriteConsole("MouseEvent");
			}
			
			#endregion

			MainFrame.WriteConsole("Mouse Handle Thread Closed.");
		}

		/// <summary>
		/// Keyboard Handling Thread Function.
		/// Locks Model.
		/// </summary>
		private void HandleKeyboard()
		{
			MainFrame.WriteConsole("Keyboard Handle Thread Started");

			#region Keyboard Handling

			while(alive)
			{			
				KeyFire.WaitOne(-1, false); // Waiting for an Event

				if(KeyDevice == null) continue;
				DInput.BufferedDataCollection buffer = null;

				try
				{ // Reading Keyboard Buffer
					buffer = KeyDevice.GetBufferedData();
				}
				catch(DInput.InputException /*exception*/)
				{ // Handling Direct Input Exceptions - Not Perfect

					//					WriteConsole(exception.ToString());

					MainFrame.WriteConsole("KEYBOARD ERROR!");

					bool TryToAcquire = true;
					do
					{
						try { KeyDevice.Acquire(); /* */ TryToAcquire = false; }
						catch(DInput.InputLostException){ }
						catch(DInput.OtherApplicationHasPriorityException){ return; }
						catch(DInput.NotAcquiredException){ return; }
						catch(DInput.InputException){ return; }
						catch(System.NullReferenceException) { break; }
					} while (TryToAcquire);
				}

				if(buffer == null) continue;
				if(state.Model == null) continue;
				if(parameters.main.WindowState == 
					FormWindowState.Minimized) continue;

				Monitor.Enter(this); // Allocating Engine
				foreach(DInput.BufferedData data in buffer)
				{
					// Char Code: data.Offset
					// Up: data.Data & 0x80 == 0
					// Down: data.Data & 0x80 != 0
				
					// Simple Quit: data.Offset == 1 (?)
					//					if(data.Offset == 1) // Statemachine Changes must be created with Thread.
					//						(new Thread(new ThreadStart(main.Close))).Start();

					if((data.Data & 0x80) == 0) state.Model.OnKeyUp(data.Offset);
					else state.Model.OnKeyDown(data.Offset); // Redirecting to Model
				}

				Monitor.Exit(this);
			}

			#endregion

			MainFrame.WriteConsole("Keyboard Handle Thread Closed.");
		}

		/// <summary>
		/// Creates a Tessellated Plane Primitive.
		/// </summary>
		/// <param name="Left">First corner in vector.</param>
		/// <param name="Right">Last corner in vector.</param>
		/// <param name="direction">Direction of its face.</param>
		/// <param name="face">Texture.</param>
		/// <returns>Plane Primitve.</returns>
		public Primitive Pr_Plane(Vector3 Left, Vector3 Right,
			StaticVectorLibrary.Direction direction, string face)
		{
			return new EPlane(this, Vector3.Minimize(Left, Right),
				Vector3.Maximize(Left, Right), direction, 
				Library.Textures().getTexture(face));
		}

		/// <summary>
		/// Creates a Tessellated Triangle Primitive
		/// </summary>
		/// <param name="a">A vector</param>
		/// <param name="b">B vector</param>
		/// <param name="c">C vector</param>
		/// <param name="face">Texture name</param>
		/// <returns>Triangle Primitive</returns>
		public Primitive Pr_TesselatedTriangle(Vector3 a, Vector3 b, Vector3 c, string face)
		{
			return new ETesselatedTriangle(this, a, b, c, 
				Library.Textures().getTexture(face));
		}

		/// <summary>
		/// Creates a Mesh from File.
		/// </summary>
		/// <param name="filename">File Name.</param>
		/// <returns>Mesh Element.</returns>
		public MeshElement GetMeshElement(string filename, EngineMeshFlags flag, Color color)
		{
			MeshElement ret = null;
			if((flag & EngineMeshFlags.NoShadow) > 0)
				ret =  new MeshElement(this, filename, false);
			else ret =  new MeshElement(this, filename, true);
			if((flag & EngineMeshFlags.Colored) > 0)
				ret.SetAttrib(color);
			return ret;
		}

		/// <summary>
		/// Gets a Mesh from a text.
		/// </summary>
		/// <param name="font">Text font.</param>
		/// <param name="text">Text string.</param>
		/// <param name="deviation">Bending of text.</param>
		/// <param name="extrusion">Width of text.</param>
		/// <returns>Mesh element from the Text.</returns>
		public MeshText GetTextMesh(System.Drawing.Font font, string text, float deviation, float extrusion)
		{
			return new MeshText(this, font, text, deviation, extrusion);
		}

		/// <summary>
		/// Get an Animation form file;
		/// </summary>
		/// <param name="file_name">Animation file name.</param>
		/// <returns>Requested animation.</returns>
		public Animation GetAnimation(string file_name)
		{
			Animation ret = new Animation(this);
			ret.Load(@"..\anims\" + file_name + ".anm");
			return ret;
		}

		/// <summary>
		/// Gives a Point Light.
		/// </summary>
		/// <param name="range">Range of light</param>
		/// <param name="color">Color of light.</param>
		/// <param name="position">Position of light.</param>
		/// <returns>Light reference. null if failed.</returns>
		public PointLight GetPointLight(float range, Color color, Vector3 position)
		{
			return new PointLight(this, range, color, position);
		}

		/// <summary>
		/// Gives a Directed Light.
		/// </summary>
		/// <param name="color">Color of light.</param>
		/// <param name="direction">Direction of light.</param>
		/// <returns>Light reference. null if failed.</returns>
		public DirectedLight GetDirectedLight(Color color, Vector3 direction)
		{
			return new DirectedLight(this, color, direction);
		}

		/// <summary>
		/// Register a light. Used by Class Light.
		/// </summary>
		/// <param name="light">Light reference.</param>
		/// <returns>Index.</returns>
		public int RegisterLight(Light light)
		{
			if(NumberOfLights > 7) return -1;
			
			for(int i = 0; i < 8; i++)
			{
				if(lights[i] == null)
				{
					lights[i] = light;
					NumberOfLights++;
					return i;
				}
			}

			return -1;
		}

		/// <summary>
		/// Unregister a light.
		/// </summary>
		/// <param name="light">Light reference.</param>
		/// <param name="index">Index of light.</param>
		/// <returns>Success.</returns>
		public bool UnRegisterLight(Light light, int index)
		{
			if(lights[index] == light)
			{
				lights[index] = null;
				NumberOfLights--;
				return true;
			} 
			else return false;
		}

		/// <summary>
		/// Renders.
		/// Locks Model.
		/// </summary>
		public void Render()
		{
			Monitor.Enter(this);

			IModel model = state.Model;
			if(model == null) return;

			#region Initializing

			// Render Initialization
			device.Clear(ClearFlags.Target | ClearFlags.ZBuffer | ClearFlags.Stencil, Color.Black, 1.0f, 0x20);

			device.BeginScene();
			device.Material = initMaterial;
			device.RenderState.StencilMask = 0x7F; // 11111111�2
			actualReferenceValue = 0x20;
			device.RenderState.Ambient = model.GetAmbient();
//			device.RenderState.SpecularEnable = true;
//			device.RenderState.Wrap0 = WrapCoordinates.One;

//			device.SamplerState[0].MagFilter = TextureFilter.GaussianQuad;

			#endregion

//			device.RenderState.Lighting = false;

			#region Projection Matrix

			device.Transform.Projection = camera;

			#endregion

			#region View Matrix


			view_matrix = Matrix.LookAtLH(model.GetViewPosition(), 
					 Vector3.Add(model.GetViewPosition(), model.GetViewDirection()),
					 model.GetVUp());

			device.Transform.View = view_matrix;

			#endregion

//			device.RenderState.Lighting = false;

//			options.depth_algorythm = DepthAlgorythm.Pass;

			#region First Rendering: With Lights

			// First Rendering: With lights
			isLighted = true;
			isShadowRendering = false;
			device.RenderState.ColorWriteEnable = ColorWriteEnable.RedGreenBlueAlpha;
			device.RenderState.Lighting = true;
			model.Render();
			isLighted = false;

			#endregion

//			device.RenderState.Lighting = true;

			if(options.renderShadow)
			{
				#region Second Rendering: Volume

				// Second rendering: Two faces of volumes
				device.RenderState.Lighting = false;
				device.RenderState.StencilEnable = true;
				device.SetTexture(0, null);
				isShadowRendering = true;
				device.RenderState.ZBufferWriteEnable = false;
				device.RenderState.StencilFunction = Compare.Always;
				device.RenderState.ColorWriteEnable = 0;
				
				model.Render();

				device.RenderState.ColorWriteEnable = ColorWriteEnable.RedGreenBlueAlpha;
				device.RenderState.StencilZBufferFail = StencilOperation.Keep;
				device.RenderState.StencilPass = StencilOperation.Keep;
				device.RenderState.ColorVertex = true;
				isShadowRendering = false;
				device.RenderState.Lighting = true;

				#endregion

				#region Third Rendering: Without Lights

				// Third Rendering: Without lights
				device.RenderState.ReferenceStencil = actualReferenceValue;
				device.Clear(ClearFlags.ZBuffer, Color.Cyan, 1.0f, 0);
				device.RenderState.ZBufferWriteEnable = true;
//				device.RenderState.StencilFunction = Compare.Less;
				device.RenderState.StencilFunction = Compare.NotEqual;

				model.Render();
				device.RenderState.StencilEnable = false;

				#endregion
			}

			Monitor.Exit(this);

			#region Draw FPS

			if(options.fpsView)
			{
				fpsFont.DrawText(null, MainFrame.lastfps.ToString(), new Rectangle(0, 0, 100, 40),
					DrawTextFormat.NoClip, Color.WhiteSmoke);
			}

			#endregion

			for(int i = 0; i < 8; i++)
			{
				if(lights[i] != null) lights[i].DeActivate();
			}

			device.EndScene();
			device.Present();

			// Render Finishing
		}

		/// <summary>
		/// Renders a shadow volume for a single triangle.
		/// </summary>
		/// <param name="a"></param>
		/// <param name="b"></param>
		/// <param name="c"></param>
		public void RenderVolume(Vector3 va, Vector3 vb, Vector3 vc)
		{
			#region Initializing

			// For element shadows
			Vector3 a, b, c;
			Matrix world = device.Transform.World;
			a = Vector3.TransformCoordinate(va, world);
			b = Vector3.TransformCoordinate(vc, world); // Switched !!! 
			c = Vector3.TransformCoordinate(vb, world); // (Because some development error :-) )

			// This normal stands on the invisible side of the triangle
			Vector3 normal = Vector3.Cross(Vector3.Subtract(b, a), Vector3.Subtract(c, a));
			// This vector points toward the visible side of the triangle
			Vector3 dir = Vector3.Subtract(a, light); 
			// If the light-point is on the visible side of the triangle,
			// both normals are nearly same-directed, so Dot-Multiplying
			// results a positive float number
			float signum = Vector3.Dot(dir, normal);

			#endregion
			
			// Creating body
			if(signum >= 0f)
			{
				#region Drawing Volume

				Matrix other = new Matrix();
				float d = (light - a).Length();
				Vector3 i = (a - light) * (1f + 20f / d) + light;
				d = d / (20f + d);
				float 
					m1 = i.X * d - a.X,
					m2 = i.Y * d - a.Y,
					m3 = i.Z * d - a.Z;

				other.M11 = b.X - a.X; other.M12 = b.Y - a.Y; other.M13 = b.Z - a.Z; other.M14 = 0;
				other.M21 = c.X - a.X; other.M22 = c.Y - a.Y; other.M23 = c.Z - a.Z; other.M24 = 0;
				other.M31 = m1; other.M32 = m2; other.M33 = m3; other.M34 = d - 1;
				other.M41 = a.X; other.M42 = a.Y; other.M43 = a.Z; other.M44 = 1;

				device.Transform.World = other;
				
				// Rendering volume
				device.SetStreamSource(0, volume, 0);
				device.VertexFormat = CustomVertex.PositionOnly.Format;

				if(options.depth_algorythm == DepthAlgorythm.Pass)
				{
					// Depth Pass Technique

					// Rendering front faces
					device.RenderState.CullMode = Cull.CounterClockwise;
					device.RenderState.StencilPass = StencilOperation.Increment;
					device.DrawPrimitives(PrimitiveType.TriangleStrip, 0, 6);

					// Rendering back faces
					device.RenderState.CullMode = Cull.Clockwise;
					device.RenderState.StencilPass = StencilOperation.Decrement;
					device.DrawPrimitives(PrimitiveType.TriangleStrip, 0, 6);

					device.RenderState.CullMode = Cull.CounterClockwise;

					#region Testing if player inside in volume

					Vector3 p = state.Model.GetViewPosition();
					Vector3 dir2 = Vector3.Subtract(p, a);
					float signum2 = Vector3.Dot(dir2, normal);
									
					if(signum2 <= 0f) 
					{
						Vector3 dx = Vector3.TransformCoordinate(new Vector3(0f, 0f, 1f), device.Transform.World);
						Vector3 ex = Vector3.TransformCoordinate(new Vector3(1f, 0f, 1f), device.Transform.World);
						Vector3 fx = Vector3.TransformCoordinate(new Vector3(0f, 1f, 1f), device.Transform.World);
					
						if((Vector3.Dot(Vector3.Subtract(p, a), Vector3.Cross(Vector3.Subtract(b, a), Vector3.Subtract(dx, a))) > 0f) &&
							(Vector3.Dot(Vector3.Subtract(p, b), Vector3.Cross(Vector3.Subtract(c, b), Vector3.Subtract(ex, b))) > 0f) &&
							(Vector3.Dot(Vector3.Subtract(p, c), Vector3.Cross(Vector3.Subtract(a, c), Vector3.Subtract(fx, c))) > 0f))
						{
							actualReferenceValue--;
						} // end if
					} // end if

					#endregion

				} 
				else 
				{
					// Carmack's Reverse

					// Rendering back faces
					device.RenderState.CullMode = Cull.Clockwise;
					device.RenderState.StencilZBufferFail = StencilOperation.Increment;
					device.DrawPrimitives(PrimitiveType.TriangleStrip, 0, 7);
					device.RenderState.ZBufferFunction = Compare.Never;
					device.DrawPrimitives(PrimitiveType.TriangleList, 9, 1);
					device.RenderState.ZBufferFunction = Compare.LessEqual;

					// Rendering front faces
					device.RenderState.CullMode = Cull.CounterClockwise;
					device.RenderState.StencilZBufferFail = StencilOperation.Decrement;
					device.DrawPrimitives(PrimitiveType.TriangleStrip, 0, 7);
					device.RenderState.ZBufferFunction = Compare.Never;
					device.DrawPrimitives(PrimitiveType.TriangleList, 9, 1);
					device.RenderState.ZBufferFunction = Compare.LessEqual;
				}

				#endregion

			} // end if signum > 0f

			// For element shadows
			device.Transform.World = world;
		} // End of method

		/// <summary>
		/// Renders shadow volume for a mesh.
		/// </summary>
		/// <param name="volume_render">Rendering function of mesh's volume.</param>
		/// <param name="mesh">Original mesh.</param>
		/// <param name="subsets">Number of mesh's subsets.</param>
		/// <param name="inverz">Invert matrix of world transformation.</param>
		public void RenderVolume(VolumeRender volume_render,
			Mesh mesh, int subsets, Matrix inverz)
		{
			#region Setting up effect parameters - OLD : COMMENTED

			Matrix w = device.Transform.World,
				vp = device.Transform.View * device.Transform.Projection;
			shadowEffect.SetValue("World", w);
			shadowEffect.SetValue("WIT", Matrix.TransposeMatrix(Matrix.Invert(w)));
			shadowEffect.SetValue("ViewProj", vp);
			shadowEffect.SetValue("LightPos", new Vector4(light.X, light.Y, light.Z, 20f));

			#endregion

			#region Setting uo effect parameters - ACTUAL

//			Matrix w = device.Transform.World,
//				vp = device.Transform.View * device.Transform.Projection;
//			shadowEffect.SetValue("World", w);
//			shadowEffect.SetValue("ViewProj", vp);
//			shadowEffect.SetValue("LightPos", 
//				new Vector4(light.X, light.Y, light.Z, 20f));
//			Vector3 matrix_center = Vector3.TransformCoordinate(
//				new Vector3(0f, 0f, 0f), w);
//			shadowEffect.SetValue("mpos", new Vector4(
//				matrix_center.X, matrix_center.Y, matrix_center.Z, 1f));

			#endregion

			device.RenderState.Clipping = false;

			shadowEffect.Begin(0);
			shadowEffect.BeginPass(0);

			volume_render();

			shadowEffect.EndPass();
			shadowEffect.End();

			device.RenderState.Clipping = true;

			if(options.depth_algorythm == DepthAlgorythm.Pass)
			{

				#region Checking if player is inside of Shadow Volume

				//			inverz = device.Transform.World;
				//			inverz.Invert();

				Vector3 p = Vector3.TransformCoordinate(state.Model.GetViewPosition(), inverz);
				Vector3 l = Vector3.TransformCoordinate(this.light, inverz);
				Vector3 difference = l-p;
				Vector3 dir = Vector3.Normalize(difference);
				IntersectInformation inf;
				if(mesh.Intersect(p, dir, out inf) && (inf.Dist < difference.Length()))
				{
					actualReferenceValue--;
					//				if(actualReferenceValue <= 0) 
					//					Console.WriteLine(actualReferenceValue);
				}

				#endregion

			}

		} // End of method

		#region IPeriod Members

		public void Period()
		{
			IModel model = state.Model;
			if(model != null) model.Period();
		}

		#endregion

	} // End of Engine


	/// <summary>
	/// Class for engine options.
	/// </summary>
	public class EngineOptions
	{
		
		public bool renderShadow = false;
		public bool renderMeshShadow = true;
		public bool fpsView = true;
		public DepthAlgorythm depth_algorythm = 
			DepthAlgorythm.Pass;

		public static float D_HIGH = 0.25f;
		public static float D_MID = 0.5f;
		public static float D_LOW = 1f;
		public float detail = D_LOW;

	}

	public enum DepthAlgorythm
	{
		Pass, Fail
	}

	/// <summary>
	/// Model interface that this Engine can handle.
	/// </summary>
	public interface IModel : IPeriod, IDisposable
	{
		/// <summary>
		/// Doing Initialization Functionalities.
		/// </summary>
		void Initialize(Engine _engine);

		/// <summary>
		/// Graphical Rendering.
		/// </summary>
		void Render();

		/// <summary>
		/// Get View Position for Rendering.
		/// </summary>
		/// <returns>View Position.</returns>
		Vector3 GetViewPosition();

		/// <summary>
		/// Get View Direction for Rendering.
		/// </summary>
		/// <returns>View Direction.</returns>
		Vector3 GetViewDirection();

		/// <summary>
		/// Gets Up Vector for Rendering.
		/// </summary>
		/// <returns>Up Vector.</returns>
		Vector3 GetVUp();

		/// <summary>
		/// Handling event, when a Key is pressed.
		/// </summary>
		/// <param name="key">Pressed Key's Code.</param>
		void OnKeyDown(int key);

		/// <summary>
		/// Handling event, when a Key is released.
		/// </summary>
		/// <param name="key">Released Key's Code.</param>
		void OnKeyUp(int key);

		/// <summary>
		/// Handling event, when a Mouse Button is pressed.
		/// </summary>
		/// <param name="button">0 = Left, 1 = Right, 2 = Middle</param>
		void OnButtonDown(int button);

		/// <summary>
		/// Handling event, when a Mouse Button is released.
		/// </summary>
		/// <param name="button">0 = Left, 1 = Right, 2 = Middle</param>
		void OnButtonUp(int button);

		/// <summary>
		/// Handling Mouse Movement Event.
		/// </summary>
		/// <param name="x">X Movement.</param>
		/// <param name="y">Y Movement.</param>
		void OnMovement(int x, int y);

		/// <summary>
		/// Gets model's Ambient light
		/// </summary>
		/// <returns>Ambient light in Color format</returns>
		Color GetAmbient();
	

	} // End of IModel

	/// <summary>
	/// Interface for classes, who does a Periodic Functionality.
	/// </summary>
	public interface IPeriod
	{
		/// <summary>
		/// Periodic Functionality.
		/// </summary>
		void Period();
	}

	public delegate CustomVertex.PositionNormalTextured SquarePointFunction(Vector3 Min, Vector3 Max, int num);

	/// <summary>
	/// A static library for already allocated Vector3 objects.
	/// So using functions and operators won't allocate more
	/// memory avoiding Garbage Collector sweeping. Maybe.
	/// </summary>
	public class StaticVectorLibrary
	{
		static public bool inited = false;

		public class Direction
		{
			public Vector3 NormalVector;
			public SquarePointFunction SquarePoint;
		}

		static public Direction Left = null, Right = null, Front = null,
			Back = null, Top = null, Bottom = null;

		/// <summary>
		/// Initializing statical Vector3 variables.
		/// </summary>
		static public void StaticInitializer()
		{
			if(inited) return; else inited = true;

			Left = new Direction(); Left.NormalVector = new Vector3(-1f, 0f, 0f);
			Left.SquarePoint = new SquarePointFunction(LeftSquarePoint);
			Right = new Direction(); Right.NormalVector = new Vector3(1f, 0f, 0f);
			Right.SquarePoint = new SquarePointFunction(RightSquarePoint);
			Front = new Direction(); Front.NormalVector = new Vector3(0f, 0f, 1f);
			Front.SquarePoint = new SquarePointFunction(FrontSquarePoint);
			Back = new Direction(); Back.NormalVector = new Vector3(0f, 0f, -1f);
			Back.SquarePoint = new SquarePointFunction(BackSquarePoint);
			Top = new Direction(); Top.NormalVector = new Vector3(0f, 1f, 0f);
			Top.SquarePoint = new SquarePointFunction(TopSquarePoint);
			Bottom = new Direction(); Bottom.NormalVector = new Vector3(0f, -1f, 0f);
			Bottom.SquarePoint = new SquarePointFunction(BottomSquarePoint);
		}

		#region Square Point Function Definitions

		/// <summary>
		/// Left Square Point Function.
		/// </summary>
		/// <param name="Min">Minimum Vector.</param>
		/// <param name="Max">Maximum Vector.</param>
		/// <param name="num">Square Point number.</param>
		/// <returns>Direct 3D vertex for Square Point.</returns>
		static public CustomVertex.PositionNormalTextured LeftSquarePoint(Vector3 Min, Vector3 Max, int num)
		{
			switch(num)
			{
				case 1:
					return new CustomVertex.PositionNormalTextured(
						Min.X, Min.Y, Max.Z, -1f, 0f, 0f, -Max.Z, -Min.Y);

				case 2:
					return new CustomVertex.PositionNormalTextured(
						Min.X, Max.Y, Max.Z, -1f, 0f, 0f, -Max.Z, -Max.Y);

				case 3:
					return new CustomVertex.PositionNormalTextured(
						Min.X, Max.Y, Min.Z, -1f, 0f, 0f, -Min.Z, -Max.Y);

				case 0:
				default:
					return new CustomVertex.PositionNormalTextured(
						Min.X, Min.Y, Min.Z, -1f, 0f, 0f, -Min.Z, -Min.Y);
			}		
		}

		/// <summary>
		/// Right Square Point Function.
		/// </summary>
		/// <param name="Min">Minimum Vector.</param>
		/// <param name="Max">Maximum Vector.</param>
		/// <param name="num">Square Point number.</param>
		/// <returns>Direct 3D vertex for Square Point.</returns>
		static public CustomVertex.PositionNormalTextured RightSquarePoint(Vector3 Min, Vector3 Max, int num)
		{
			switch(num)	
			{
				case 1:
					return new CustomVertex.PositionNormalTextured(
						Max.X, Max.Y, Min.Z, 1f, 0f, 0f, Min.Z, -Max.Y);

				case 2:
					return new CustomVertex.PositionNormalTextured(
						Max.X, Max.Y, Max.Z, 1f, 0f, 0f, Max.Z, -Max.Y);

				case 3:
					return new CustomVertex.PositionNormalTextured(
						Max.X, Min.Y, Max.Z, 1f, 0f, 0f, Max.Z, -Min.Y);

				case 0:
				default:
					return new CustomVertex.PositionNormalTextured(
						Max.X, Min.Y, Min.Z, 1f, 0f, 0f, Min.Z, -Min.Y);
			}		
		}
	
		/// <summary>
		/// Front Square Point Function.
		/// </summary>
		/// <param name="Min">Minimum Vector.</param>
		/// <param name="Max">Maximum Vector.</param>
		/// <param name="num">Square Point number.</param>
		/// <returns>Direct 3D vertex for Square Point.</returns>
		static public CustomVertex.PositionNormalTextured FrontSquarePoint(Vector3 Min, Vector3 Max, int num)
		{
			switch(num)
			{
				case 1:
					return new CustomVertex.PositionNormalTextured(
						Max.X, Min.Y, Max.Z, 0f, 0f, 1f, -Max.X, -Min.Y);

				case 2:
					return new CustomVertex.PositionNormalTextured(
						Max.X, Max.Y, Max.Z, 0f, 0f, 1f, -Max.X, -Max.Y);

				case 3:
					return new CustomVertex.PositionNormalTextured(
						Min.X, Max.Y, Max.Z, 0f, 0f, 1f, -Min.X, -Max.Y);

				case 0:
				default:
					return new CustomVertex.PositionNormalTextured(
						Min.X, Min.Y, Max.Z, 0f, 0f, 1f, -Min.X, -Min.Y);
			}		
		}
	
		/// <summary>
		/// Back Square Point Function.
		/// </summary>
		/// <param name="Min">Minimum Vector.</param>
		/// <param name="Max">Maximum Vector.</param>
		/// <param name="num">Square Point number.</param>
		/// <returns>Direct 3D vertex for Square Point.</returns>
		static public CustomVertex.PositionNormalTextured BackSquarePoint(Vector3 Min, Vector3 Max, int num)
		{
			switch(num)
			{
				case 1:
					return new CustomVertex.PositionNormalTextured(
						Min.X, Max.Y, Min.Z, 0f, 0f, -1f, Min.X, -Max.Y);

				case 2:
					return new CustomVertex.PositionNormalTextured(
						Max.X, Max.Y, Min.Z, 0f, 0f, -1f, Max.X, -Max.Y);

				case 3:
					return new CustomVertex.PositionNormalTextured(
						Max.X, Min.Y, Min.Z, 0f, 0f, -1f, Max.X, -Min.Y);

				case 0:
				default:
					return new CustomVertex.PositionNormalTextured(
						Min.X, Min.Y, Min.Z, 0f, 0f, -1f, Min.X, -Min.Y);			}				
		}
	
		/// <summary>
		/// Top Square Point Function.
		/// </summary>
		/// <param name="Min">Minimum Vector.</param>
		/// <param name="Max">Maximum Vector.</param>
		/// <param name="num">Square Point number.</param>
		/// <returns>Direct 3D vertex for Square Point.</returns>
		static public CustomVertex.PositionNormalTextured TopSquarePoint(Vector3 Min, Vector3 Max, int num)
		{
			switch(num)
			{
				case 1:
					return new CustomVertex.PositionNormalTextured(
						Min.X, Min.Y, Max.Z, 0f, 1f, 0f, Min.X, Max.Z);

				case 2:
					return new CustomVertex.PositionNormalTextured(
						Max.X, Min.Y, Max.Z, 0f, 1f, 0f, Max.X, Max.Z);

				case 3:
					return new CustomVertex.PositionNormalTextured(
						Max.X, Min.Y, Min.Z, 0f, 1f, 0f, Max.X, Min.Z);

				case 0:
				default:
					return new CustomVertex.PositionNormalTextured(
						Min.X, Min.Y, Min.Z, 0f, 1f, 0f, Min.X, Min.Z);
			}		
		}
	
		/// <summary>
		/// Bottom Square Point Function.
		/// </summary>
		/// <param name="Min">Minimum Vector.</param>
		/// <param name="Max">Maximum Vector.</param>
		/// <param name="num">Square Point number.</param>
		/// <returns>Direct 3D vertex for Square Point.</returns>
		static public CustomVertex.PositionNormalTextured BottomSquarePoint(Vector3 Min, Vector3 Max, int num)
		{
			switch(num)
			{
				case 1:
					return new CustomVertex.PositionNormalTextured(
						Max.X, Max.Y, Min.Z, 0f, -1f, 0f, Max.X, Min.Z);

				case 2:
					return new CustomVertex.PositionNormalTextured(
						Max.X, Max.Y, Max.Z, 0f, -1f, 0f, Max.X, Max.Z);

				case 3:
					return new CustomVertex.PositionNormalTextured(
						Min.X, Max.Y, Max.Z, 0f, -1f, 0f, Min.X, Max.Z);

				case 0:
				default:
					return new CustomVertex.PositionNormalTextured(
						Min.X, Max.Y, Min.Z, 0f, -1f, 0f, Min.X, Min.Z);
			}		
		}
	
		#endregion
	}


	/// <summary>
	/// Function for rendering a shadow volume
	/// </summary>
	public delegate void VolumeRender();

	public struct Parameters
	{
		public bool FullScreen;
		public ScreenSize size;
		public Form main;
	}

	public enum ScreenSize
	{
		Default, W1024H768, W800H600, W640H480, W320H240
	}

} // End of NameSpace
