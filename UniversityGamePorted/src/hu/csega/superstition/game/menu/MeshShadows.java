package hu.csega.superstition.game.menu;

public class MeshShadows extends MenuElement
{
	public MeshShadows(ModelParams param)
	{
		super(param);
	}

	@Override
	public String getText()
	{
		return "Mesh Shadows";
	}

	@Override
	public IMenu DoItem()
	{
		param.engine.Options.renderMeshShadow = !param.engine.Options.renderMeshShadow;
		return null;
	}

	@Override
	public void Render()
	{
		bool val = param.engine.Options.renderMeshShadow;
		super.Render();
		param.onoff.Render(translation + OnOff.DEFAULT, val);
	}
}