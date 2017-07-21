package hu.csega.superstition;

import hu.csega.superstition.animation.AnimationFacadeImpl;
import hu.csega.superstition.animation.AnimationModel;
import hu.csega.superstition.animation.AnimationTool;

public class AnimationToolStarter {

	public static void main(String[] args) throws Exception {
		AnimationFacadeImpl facade = new AnimationFacadeImpl();

		AnimationModel model = new AnimationModel(facade);
		facade.setModel(model);

		AnimationTool tool = new AnimationTool(facade);
		facade.setTool(tool);

		tool.initializeAndShwoTool();
	}

}
