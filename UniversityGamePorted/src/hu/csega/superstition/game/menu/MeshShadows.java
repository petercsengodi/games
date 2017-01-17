package hu.csega.superstition.game.menu;

class MeshShadows : MenuElement
{
	public MeshShadows(ModelParams param) : base(param)
	{
	}

	public override string getText()
	{
		return "Mesh Shadows";
	}

	public override IMenu DoItem()
	{
		param.engine.Options.renderMeshShadow = !param.engine.Options.renderMeshShadow;
		return null;
	}

	public override void Render()
	{
		bool val = param.engine.Options.renderMeshShadow;
		base.Render();
		param.onoff.Render(translation + OnOff.DEFAULT, val);
	}
}