package hu.csega.superstition.game;

public class EngineOptions {

	public boolean renderShadow = false;
	public boolean renderMeshShadow = true;
	public boolean fpsView = true;

	public DepthAlgorythm depth_algorythm =DepthAlgorythm.Pass;

	public static float D_HIGH = 0.25f;
	public static float D_MID = 0.5f;
	public static float D_LOW = 1f;
	public float detail = D_LOW;

}