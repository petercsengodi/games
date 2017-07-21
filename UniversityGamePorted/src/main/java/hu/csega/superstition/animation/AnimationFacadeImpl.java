package hu.csega.superstition.animation;

public class AnimationFacadeImpl implements AnimationFacade {

	private AnimationModel model;
	private AnimationTool tool;

	@Override
	public AnimationModel getModel() {
		return model;
	}

	public void setModel(AnimationModel model) {
		this.model = model;
	}

	@Override
	public AnimationTool getTool() {
		return tool;
	}

	public void setTool(AnimationTool tool) {
		this.tool = tool;
	}

}
