package hu.csega.editors.ftm.layer1.presentation.swing.view;

import java.util.List;

import hu.csega.editors.ftm.model.FreeTriangleMeshGroup;
import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.games.engine.GameEngineFacade;

public class FreeTriangleMeshGroupTreeNode {

	private GameEngineFacade facade;
	private int groupIndex;

	public FreeTriangleMeshGroupTreeNode(GameEngineFacade facade, int groupIndex) {
		this.facade = facade;
		this.groupIndex = groupIndex;
	}

	@Override
	public String toString() {
		FreeTriangleMeshModel model = (FreeTriangleMeshModel) facade.model();
		if(model != null) {
			List<FreeTriangleMeshGroup> groups = model.getGroups();
			if(groups != null && !groups.isEmpty()) {
				FreeTriangleMeshGroup group = groups.get(groupIndex - 1);
				return group.toString();
			}
		}

		return "–";
	}
}
