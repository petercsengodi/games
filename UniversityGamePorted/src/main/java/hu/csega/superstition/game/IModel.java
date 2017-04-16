package hu.csega.superstition.game;

import java.awt.Color;

import org.joml.Vector3f;

import hu.csega.superstition.common.Disposable;

public interface IModel extends IPeriod, Disposable {

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
	Vector3f GetViewPosition();

	/// <summary>
	/// Get View Direction for Rendering.
	/// </summary>
	/// <returns>View Direction.</returns>
	Vector3f GetViewDirection();

	/// <summary>
	/// Gets Up Vector for Rendering.
	/// </summary>
	/// <returns>Up Vector.</returns>
	Vector3f GetVUp();

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