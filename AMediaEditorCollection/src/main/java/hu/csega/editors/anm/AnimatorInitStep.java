package hu.csega.editors.anm;

import hu.csega.editors.anm.model.AnimatorModel;
import hu.csega.editors.anm.model.AnimatorPartPlacement;
import hu.csega.editors.anm.model.AnimatorPartPlacementChild;
import hu.csega.editors.anm.model.AnimatorScene;
import hu.csega.editors.anm.model.parts.AnimatorJoint;
import hu.csega.editors.anm.model.parts.AnimatorLocation;
import hu.csega.editors.anm.model.parts.AnimatorPart;
import hu.csega.editors.anm.model.parts.AnimatorPosition;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;

public class AnimatorInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		AnimatorModel model = new AnimatorModel();
		facade.setModel(model);

		initializeTestModel(model);

		return facade;
	}

	private void initializeTestModel(AnimatorModel model) {

		AnimatorPart base = new AnimatorPart();
		base.name = "base";
		base.visible = true;

		{
			AnimatorPosition position = new AnimatorPosition(0f, 0f, 0f);
			AnimatorPosition direction = new AnimatorPosition(0f, 1f, 0f);
			AnimatorPosition up = new AnimatorPosition(1f, 0f, 0f);
			AnimatorLocation location = new AnimatorLocation(position, direction, up);
			AnimatorJoint joint = new AnimatorJoint("origin", location);
			base.joints.add(joint);
		}

		{
			AnimatorPosition position = new AnimatorPosition(0f, 10f, 0f);
			AnimatorPosition direction = new AnimatorPosition(0f, 1f, 0f);
			AnimatorPosition up = new AnimatorPosition(1f, 0f, 0f);
			AnimatorLocation location = new AnimatorLocation(position, direction, up);
			AnimatorJoint joint = new AnimatorJoint("top", location);
			base.joints.add(joint);
		}

		AnimatorPart top = new AnimatorPart();
		top.name = "top";
		top.visible = true;

		{
			AnimatorPosition position = new AnimatorPosition(0f, 0f, 0f);
			AnimatorPosition direction = new AnimatorPosition(0f, 1f, 0f);
			AnimatorPosition up = new AnimatorPosition(1f, 0f, 0f);
			AnimatorLocation location = new AnimatorLocation(position, direction, up);
			AnimatorJoint joint = new AnimatorJoint("origin", location);
			top.joints.add(joint);
		}

		{
			AnimatorPosition position = new AnimatorPosition(0f, 5f, 0f);
			AnimatorPosition direction = new AnimatorPosition(0f, 1f, 0f);
			AnimatorPosition up = new AnimatorPosition(1f, 0f, 0f);
			AnimatorLocation location = new AnimatorLocation(position, direction, up);
			AnimatorJoint joint = new AnimatorJoint("top", location);
			top.joints.add(joint);
		}

		for(int i = 0; i < 5; i++) {
			AnimatorScene scene = generateTestScene(i, base, top);
			model.scenes.add(scene);
		}
	}

	private AnimatorScene generateTestScene(int i, AnimatorPart base, AnimatorPart top) {
		AnimatorPartPlacement basePlacement = new AnimatorPartPlacement();
		basePlacement.part = base.name;

		AnimatorPartPlacement topPlacement = new AnimatorPartPlacement();
		topPlacement.part = top.name;

		AnimatorPartPlacementChild child = new AnimatorPartPlacementChild();
		child.joint = "top";
		child.partPlacement = topPlacement;
		basePlacement.children.add(child);

		AnimatorScene scene = new AnimatorScene();
		scene.partPlacements.put(base.name, basePlacement);
		scene.partPlacements.put(top.name, topPlacement);
		scene.roots.add(base.name);
		return scene;
	}

}
